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

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.lar.FolderUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.portal.kernel.repository.model.Folder"
	},
	service = StagedModelRepository.class
)
public class FolderStagedModelRepository
	extends BaseStagedModelRepository<Folder> {

	public Folder addStagedModel(
			PortletDataContext portletDataContext, Folder folder)
		throws PortalException {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folder, DLFolder.class);

		serviceContext.setUserId(userId);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(folder.getUuid());
		}

		String name = getFolderName(
			null, portletDataContext.getScopeGroupId(),
			folder.getParentFolderId(), folder.getName(), 2);

		return _dlAppLocalService.addFolder(
			userId, portletDataContext.getScopeGroupId(),
			folder.getParentFolderId(), name, folder.getDescription(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Folder folder = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (folder != null) {
			deleteStagedModel(folder);
		}
	}

	@Override
	public void deleteStagedModel(Folder folder) throws PortalException {
		_dlAppLocalService.deleteFolder(folder.getFolderId());
	}

	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public Folder fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		return FolderUtil.fetchByUUID_R(uuid, groupId);
	}

	@Override
	public List<Folder> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<DLFolder> dlFolders =
			_dlFolderLocalService.getDLFoldersByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DLFolder>());

		List<Folder> folders = new ArrayList<>();

		for (DLFolder dlFolder : dlFolders) {
			folders.add(new LiferayFolder(dlFolder));
		}

		return folders;
	}

	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _dlFolderLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	public void restoreStagedModel(
			PortletDataContext portletDataContext, Folder folder)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Folder existingFolder = fetchStagedModelByUuidAndGroupId(
			folder.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFolder == null) ||
			!(existingFolder.getModel() instanceof DLFolder)) {

			return;
		}

		DLFolder dlFolder = (DLFolder)existingFolder.getModel();

		if (!dlFolder.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			DLFolder.class.getName());

		try {
			if (trashHandler.isRestorable(existingFolder.getFolderId())) {
				trashHandler.restoreTrashEntry(
					userId, existingFolder.getFolderId());
			}
		}
		catch (PortalException pe) {
			throw new PortletDataException(pe);
		}
	}

	public Folder saveStagedModel(Folder folder) throws PortalException {
		return null;
	}

	public Folder updateStagedModel(
			PortletDataContext portletDataContext, Folder folder)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folder);

		String name = getFolderName(
			folder.getUuid(), portletDataContext.getScopeGroupId(),
			folder.getParentFolderId(), folder.getName(), 2);

		return _dlAppLocalService.updateFolder(
			folder.getFolderId(), folder.getParentFolderId(), name,
			folder.getDescription(), serviceContext);
	}

	protected String getFolderName(
			String uuid, long groupId, long parentFolderId, String name,
			int count) {

		Folder folder = FolderUtil.fetchByR_P_N(groupId, parentFolderId, name);

		if (folder == null) {
			FileEntry fileEntry = FileEntryUtil.fetchByR_F_T(
				groupId, parentFolderId, name);

			if (fileEntry == null) {
				return name;
			}
		}
		else if (Validator.isNotNull(uuid) && uuid.equals(folder.getUuid())) {
			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getFolderName(uuid, groupId, parentFolderId, name, ++count);
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
	}

	private DLAppLocalService _dlAppLocalService;
	private DLFolderLocalService _dlFolderLocalService;

}
