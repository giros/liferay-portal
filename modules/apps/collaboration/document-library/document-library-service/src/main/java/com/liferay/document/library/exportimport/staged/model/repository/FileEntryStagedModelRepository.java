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

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.lar.FileEntryStagedModelDataHandler;
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
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

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
		"model.class.name=com.liferay.portal.kernel.repository.model.FileEntry;"
	},
	service = StagedModelRepository.class
)
public class FileEntryStagedModelRepository
	extends BaseStagedModelRepository<FileEntry> {

	public FileEntry addStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortalException {

		long userId = portletDataContext.getUserId(fileEntry.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileEntry, DLFileEntry.class);

		serviceContext.setAttribute(
			"sourceFileName", "A." + fileEntry.getExtension());
		serviceContext.setUserId(userId);

		importMetaData(
			portletDataContext, fileEntryElement, fileEntry, serviceContext);

		FileVersion fileVersion = fileEntry.getFileVersion();

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setAttribute(
				"fileVersionUuid", fileVersion.getUuid());
			serviceContext.setUuid(fileEntry.getUuid());
		}

		String fileEntryTitle = _dlFileEntryLocalService.getUniqueTitle(
			portletDataContext.getScopeGroupId(), fileEntry.getFolderId(), 0,
			fileEntry.getTitle(), fileEntry.getExtension());

		return _dlAppLocalService.addFileEntry(
			userId, portletDataContext.getScopeGroupId(),
			fileEntry.getFolderId(), fileEntry.getFileName(),
			fileEntry.getMimeType(), fileEntryTitle, fileEntry.getDescription(),
			null, fileEntry.getInputStream(), fileEntry.getSize(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		FileEntry fileEntry = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (fileEntry != null) {
			deleteStagedModel(fileEntry);
		}
	}

	@Override
	public void deleteStagedModel(FileEntry fileEntry) throws PortalException {
		_dlAppLocalService.deleteFileEntry(fileEntry.getFileEntryId());
	}

	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public FileEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		try {
			return _dlAppLocalService.getFileEntryByUuidAndGroupId(
				uuid, groupId);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}

			return null;
		}
	}

	@Override
	public List<FileEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<DLFileEntry> dlFileEntries =
			_dlFileEntryLocalService.getDLFileEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DLFileEntry>());

		List<FileEntry> fileEntries = new ArrayList<>();

		for (DLFileEntry dlFileEntry : dlFileEntries) {
			fileEntries.add(new LiferayFileEntry(dlFileEntry));
		}

		return fileEntries;
	}

	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _dlFileEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	public void restoreStagedModel(
			PortletDataContext portletDataContext, FileEntry fileEntry)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(fileEntry.getUserUuid());

		FileEntry existingFileEntry = fetchStagedModelByUuidAndGroupId(
			fileEntry.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFileEntry == null) || !existingFileEntry.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			DLFileEntry.class.getName());

		try {
			if (trashHandler.isRestorable(existingFileEntry.getFileEntryId())) {
				trashHandler.restoreTrashEntry(
					userId, existingFileEntry.getFileEntryId());
			}
		}
		catch (PortalException pe) {
			throw new PortletDataException(pe);
		}
	}

	public FileEntry saveStagedModel(FileEntry fileEntry)
		throws PortalException {

		return null;
	}

	public T updateStagedModel(
			PortletDataContext portletDataContext, T stagedModel)
		throws PortalException;

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryStagedModelRepository.class);

	private DLAppLocalService _dlAppLocalService;
	private DLFileEntryLocalService _dlFileEntryLocalService;

}
