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

package com.liferay.document.library.exportimport.staged.model.repository;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.document.library.lar.FileShortcutStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileShortcut;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.portal.kernel.repository.model.FileShortcut"
	},
	service = StagedModelRepository.class
)
public class FileShortcutStagedModelRepository
	extends BaseStagedModelRepository<FileShortcut> {

	public FileShortcut addStagedModel(
			PortletDataContext portletDataContext, FileShortcut fileShortcut)
		throws PortalException {

		long userId = portletDataContext.getUserId(fileShortcut.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
				fileShortcut);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(fileShortcut.getUuid());
		}

		return _dlAppLocalService.addFileShortcut(
			userId, fileShortcut.getGroupId(), fileShortcut.getFolderId(),
			fileShortcut.getToFileEntryId(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		FileShortcut fileShortcut = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (fileShortcut != null) {
			deleteStagedModel(fileShortcut);
		}
	}

	@Override
	public void deleteStagedModel(FileShortcut fileShortcut)
		throws PortalException {

		_dlFileShortcutLocalService.deleteFileShortcut(
			fileShortcut.getFileShortcutId());
	}

	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public FileShortcut fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		try {
			DLFileShortcut dlFileShortcut =
				_dlFileShortcutLocalService.getDLFileShortcutByUuidAndGroupId(
					uuid, groupId);

			return new LiferayFileShortcut(dlFileShortcut);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return null;
		}
	}

	@Override
	public List<FileShortcut> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<DLFileShortcut> dlFileShortcuts =
			_dlFileShortcutLocalService.getDLFileShortcutsByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DLFileShortcut>());

		List<FileShortcut> fileShortcuts = new ArrayList<>();

		for (DLFileShortcut dlFileShortcut : dlFileShortcuts) {
			fileShortcuts.add(new LiferayFileShortcut(dlFileShortcut));
		}

		return fileShortcuts;
	}

	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _dlFileShortcutLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	public void restoreStagedModel(
			PortletDataContext portletDataContext, FileShortcut fileShortcut)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(fileShortcut.getUserUuid());

		FileShortcut existingFileShortcut = fetchStagedModelByUuidAndGroupId(
			fileShortcut.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFileShortcut == null) ||
			!(existingFileShortcut.getModel() instanceof DLFileShortcut)) {

			return;
		}

		DLFileShortcut dlFileShortcut =
			(DLFileShortcut)existingFileShortcut.getModel();

		if (!dlFileShortcut.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			DLFileShortcut.class.getName());

		try {
			if (trashHandler.isRestorable(
					existingFileShortcut.getFileShortcutId())) {
	
				trashHandler.restoreTrashEntry(
					userId, existingFileShortcut.getFileShortcutId());
			}
		}
		catch (PortalException pe) {
			throw new PortletDataException(pe);
		}
	}

	public FileShortcut saveStagedModel(FileShortcut fileShortcut)
		throws PortalException {

		return null;
	}

	public FileShortcut updateStagedModel(
			PortletDataContext portletDataContext, FileShortcut fileShortcut)
		throws PortalException {

		long userId = portletDataContext.getUserId(fileShortcut.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
				fileShortcut);

		return _dlAppLocalService.updateFileShortcut(
			userId, fileShortcut.getFileShortcutId(),
			fileShortcut.getFolderId(), fileShortcut.getToFileEntryId(),
			serviceContext);
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileShortcutLocalService(
		DLFileShortcutLocalService dlFileShortcutLocalService) {

		_dlFileShortcutLocalService = dlFileShortcutLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileShortcutStagedModelDataHandler.class);

	private DLAppLocalService _dlAppLocalService;
	private DLFileShortcutLocalService _dlFileShortcutLocalService;

}
