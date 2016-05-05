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

package com.liferay.document.library.lar;

import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileShortcutConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileShortcut;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class FileShortcutStagedModelDataHandler
	extends BaseStagedModelDataHandler<FileShortcut> {

	public static final String[] CLASS_NAMES = {
		DLFileShortcutConstants.getClassName(), FileShortcut.class.getName(),
		LiferayFileShortcut.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(FileShortcut fileShortcut) {
		return fileShortcut.getUuid();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, FileShortcut fileShortcut)
		throws Exception {

		if (fileShortcut.getFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, fileShortcut, fileShortcut.getFolder(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		FileEntry fileEntry = _dlAppLocalService.getFileEntry(
			fileShortcut.getToFileEntryId());

		StagedModelDataHandlerUtil.exportReferenceStagedModel(
			portletDataContext, fileShortcut, fileEntry,
			PortletDataContext.REFERENCE_TYPE_STRONG);

		Element fileShortcutElement = portletDataContext.getExportDataElement(
			fileShortcut);

		portletDataContext.addClassedModel(
			fileShortcutElement,
			ExportImportPathUtil.getModelPath(fileShortcut), fileShortcut);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, FileShortcut fileShortcut)
		throws Exception {

		FileShortcut importedFileShortcut = (FileShortcut)fileShortcut.clone();

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Folder.class);

		long folderId = MapUtil.getLong(
			folderIds, fileShortcut.getFolderId(), fileShortcut.getFolderId());

		importedFileShortcut.setFolderId(folderId);

		long groupId = portletDataContext.getScopeGroupId();

		if (folderId != DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			Folder folder = FolderUtil.findByPrimaryKey(folderId);

			groupId = folder.getRepositoryId();
		}

		importedFileShortcut.setGroupId(groupId);

		Map<Long, Long> fileEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FileEntry.class);

		long fileEntryId = MapUtil.getLong(
			fileEntryIds, fileShortcut.getToFileEntryId(),
			fileShortcut.getToFileEntryId());

		FileEntry importedFileEntry = null;

		try {
			importedFileEntry = _dlAppLocalService.getFileEntry(fileEntryId);
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to fetch file entry " + fileEntryId);
			}

			return;
		}

		importedFileShortcut.setToFileEntryId(
			importedFileEntry.getFileEntryId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileShortcut);

		FileShortcut existingFileShortcut =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				fileShortcut.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFileShortcut == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedFileShortcut = _stagedModelRepository.addStagedModel(
				portletDataContext, importedFileShortcut);
		}
		else {
			importedFileShortcut.setFileShortcutId(
				existingFileShortcut.getFileShortcutId());

			importedFileShortcut = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedFileShortcut);
		}

		portletDataContext.importClassedModel(
			fileShortcut, importedFileShortcut);
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setStagedModelRepository(
		StagedModelRepository<FileShortcut> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileShortcutStagedModelDataHandler.class);

	private DLAppLocalService _dlAppLocalService;
	private StagedModelRepository<FileShortcut> _stagedModelRepository;

}