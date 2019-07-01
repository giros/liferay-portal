/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.change.tracking.internal.background.task;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.engine.exception.CTEngineException;
import com.liferay.change.tracking.engine.exception.CTEntryCollisionCTEngineException;
import com.liferay.change.tracking.engine.exception.CTProcessCTEngineException;
import com.liferay.change.tracking.internal.adapter.CTAdapterBag;
import com.liferay.change.tracking.internal.adapter.CTAdapterHelper;
import com.liferay.change.tracking.internal.background.task.display.CTPublishBackgroundTaskDisplay;
import com.liferay.change.tracking.internal.process.log.CTProcessLog;
import com.liferay.change.tracking.internal.process.util.CTProcessMessageSenderUtil;
import com.liferay.change.tracking.internal.util.CTEntryCollisionUtil;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryAggregate;
import com.liferay.change.tracking.model.CTProcess;
import com.liferay.change.tracking.service.CTCollectionLocalServiceUtil;
import com.liferay.change.tracking.service.CTEntryAggregateLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTProcessLocalServiceUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskManagerUtil;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.change.tracking.model.CTModelAdapter;
import com.liferay.portal.kernel.change.tracking.service.CTServiceAdapter;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.IOException;
import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Zoltan Csaszi
 * @author Daniel Kocsis
 */
public class CTPublishBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	public CTPublishBackgroundTaskExecutor(
		CTAdapterHelper ctAdapterHelper, CTEngineManager ctEngineManager,
		CTEntryLocalService ctEntryLocalService,
		CTEntryAggregateLocalService ctEntryAggregateLocalService) {

		setBackgroundTaskStatusMessageTranslator(
			new CTPublishBackgroundTaskStatusMessageTranslator());
		setIsolationLevel(BackgroundTaskConstants.ISOLATION_LEVEL_COMPANY);

		_ctAdapterHelper = ctAdapterHelper;
		_ctEngineManager = ctEngineManager;
		_ctEntryLocalService = ctEntryLocalService;
		_ctEntryAggregateLocalService = ctEntryAggregateLocalService;
	}

	@Override
	public BackgroundTaskExecutor clone() {
		return new CTPublishBackgroundTaskExecutor(
			_ctAdapterHelper, _ctEngineManager, _ctEntryLocalService,
			_ctEntryAggregateLocalService);
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		long ctProcessId = GetterUtil.getLong(
			taskContextMap.get("ctProcessId"));
		long ctCollectionId = GetterUtil.getLong(
			taskContextMap.get("ctCollectionId"));
		boolean ignoreCollision = GetterUtil.getBoolean(
			taskContextMap.get("ignoreCollision"));

		try {
			TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> {
					_publishCTCollection(
						backgroundTask, ctProcessId, ctCollectionId,
						ignoreCollision);

					return null;
				});
		}
		catch (Throwable t) {
			CTProcessMessageSenderUtil.logCTProcessFailed();

			throw new CTProcessCTEngineException(
				backgroundTask.getCompanyId(), ctProcessId,
				"Unable to publish change tracking collection " +
					ctCollectionId,
				t);
		}

		CTProcessMessageSenderUtil.logCTProcessFinished();

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return new CTPublishBackgroundTaskDisplay(backgroundTask);
	}

	private void _attachLogs(BackgroundTask backgroundTask)
		throws IOException, PortalException {

		BackgroundTaskStatus backgroundTaskStatus =
			BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(
				backgroundTask.getBackgroundTaskId());

		CTProcessLog ctProcessLog =
			(CTProcessLog)backgroundTaskStatus.getAttribute("ctProcessLog");

		String ctProcessLogJSON = ctProcessLog.toString();

		BackgroundTaskManagerUtil.addBackgroundTaskAttachment(
			backgroundTask.getUserId(), backgroundTask.getBackgroundTaskId(),
			"log", FileUtil.createTempFile(ctProcessLogJSON.getBytes()));
	}

	private void _checkExistingCollisions(
			CTEntry ctEntry, boolean ignoreCollision)
		throws CTEntryCollisionCTEngineException {

		if (!ctEntry.isCollision()) {
			return;
		}

		CTProcessMessageSenderUtil.logCTEntryCollision(
			ctEntry, ignoreCollision);

		if (!ignoreCollision) {
			throw new CTEntryCollisionCTEngineException(
				ctEntry.getCompanyId(), ctEntry.getCtEntryId());
		}
	}

	private void _publishCTCollection(
			BackgroundTask backgroundTask, long ctProcessId,
			long ctCollectionId, boolean ignoreCollision)
		throws Exception {

		CTProcess ctProcess = CTProcessLocalServiceUtil.getCTProcess(
			ctProcessId);

		CTProcessMessageSenderUtil.logCTProcessStarted(ctProcess);

		List<CTEntry> ctEntries = _ctEngineManager.getCTEntries(ctCollectionId);

		if (ListUtil.isEmpty(ctEntries)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to find change tracking entries with change " +
						"tracking collection ID " + ctCollectionId);
			}

			return;
		}

		List<CTEntryAggregate> ctEntryAggregates =
			_ctEngineManager.getCTEntryAggregates(ctCollectionId);

		_ctAdapterHelper.getInProduction(
			() -> {
				_publishCTEntriesAndCTEntryAggregates(
					backgroundTask.getUserId(), ctCollectionId, ctEntries,
					ctEntryAggregates, ignoreCollision);

				return null;
			});

		_attachLogs(backgroundTask);
	}

	private void _publishCTEntriesAndCTEntryAggregates(
			long userId, long ctCollectionId, List<CTEntry> ctEntries,
			List<CTEntryAggregate> ctEntryAggregates, boolean ignoreCollision)
		throws Exception {

		User user = UserLocalServiceUtil.getUser(userId);

		for (CTEntry ctEntry : ctEntries) {
			_checkExistingCollisions(ctEntry, ignoreCollision);

			_publishCTEntry(ctCollectionId, ctEntry);

			_ctEntryLocalService.updateStatus(
				ctEntry.getCtEntryId(), WorkflowConstants.STATUS_APPROVED);

			if (ctEntry.getModelResourcePrimKey() == 0) {
				_ctEntryLocalService.updateCollisions(
					ctEntry.getCompanyId(), ctEntry.getModelClassNameId(),
					ctEntry.getModelClassPK());
			}
			else {
				CTEntryCollisionUtil.checkCollidingCTEntries(
					ctEntry.getCompanyId(), ctEntry.getModelClassPK(),
					ctEntry.getModelResourcePrimKey());
			}

			CTProcessMessageSenderUtil.logCTEntryPublished(ctEntry);
		}

		if (ListUtil.isNotEmpty(ctEntryAggregates)) {
			Stream<CTEntryAggregate> ctEntryAggregatesStream =
				ctEntryAggregates.stream();

			ctEntryAggregatesStream.forEach(
				ctEntryAggregate -> _ctEntryAggregateLocalService.updateStatus(
					ctEntryAggregate.getCtEntryAggregateId(),
					WorkflowConstants.STATUS_APPROVED));
		}

		Optional<CTCollection> ctCollectionOptional =
			_ctEngineManager.getCTCollectionOptional(
				user.getCompanyId(), ctCollectionId);

		CTCollection ctCollection = ctCollectionOptional.orElseThrow(
			() -> new CTEngineException(
				user.getCompanyId(),
				"Unable to find change tracking collection " + ctCollectionId));

		try {
			CTCollectionLocalServiceUtil.updateStatus(
				userId, ctCollection, WorkflowConstants.STATUS_APPROVED,
				new ServiceContext());
		}
		catch (PortalException pe) {
			_log.error(
				"Unable to update the status of the published change " +
					"tracking collection",
				pe);

			throw pe;
		}
	}

	private <T extends BaseModel<T>, C extends BaseModel<C>> void
		_publishCTEntry(long ctCollectionId, CTEntry ctEntry) {

		CTAdapterBag<T, C> ctAdapterBag = _ctAdapterHelper.getCTAdapterBag(
			ctEntry.getModelClassNameId());

		if (ctAdapterBag == null) {
			return;
		}

		CTModelAdapter<T, C> ctModelAdapter = ctAdapterBag.getCTModelAdapter();

		CTServiceAdapter<T, C> ctServiceAdapter =
			ctAdapterBag.getCTServiceAdapter();

		T model = ctServiceAdapter.fetchByPrimaryKey(ctEntry.getModelClassPK());

		if (CTConstants.CT_CHANGE_TYPE_ADDITION == ctEntry.getChangeType()) {
			ctModelAdapter.setModelCTCollectionId(model, 0);

			ctServiceAdapter.updateModel(model);
		}
		else if (CTConstants.CT_CHANGE_TYPE_MODIFICATION ==
					ctEntry.getChangeType()) {

			C modelCT = ctServiceAdapter.fetchModelCT(
				ctModelAdapter.getPrimaryKey(model), ctCollectionId);

			if (modelCT == null) {
				return;
			}

			C tempModelCT = ctServiceAdapter.createModelCT(
				ctModelAdapter.getPrimaryKey(model), 0);

			ctModelAdapter.populateModelCT(model, tempModelCT);

			ctModelAdapter.populateModel(model, modelCT);

			ctServiceAdapter.updateModel(model);

			ctModelAdapter.populateModel(model, tempModelCT);

			ctModelAdapter.populateModelCT(model, modelCT);

			ctModelAdapter.setModelCTCTCollectionId(modelCT, ctCollectionId);

			ctServiceAdapter.updateModelCT(modelCT);
		}
		else if (CTConstants.CT_CHANGE_TYPE_DELETION ==
					ctEntry.getChangeType()) {

			ctModelAdapter.setModelCTCollectionId(model, ctCollectionId);

			ctServiceAdapter.updateModel(model);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CTPublishBackgroundTaskExecutor.class);

	private final CTAdapterHelper _ctAdapterHelper;
	private final CTEngineManager _ctEngineManager;
	private final CTEntryAggregateLocalService _ctEntryAggregateLocalService;
	private final CTEntryLocalService _ctEntryLocalService;
	private final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

}