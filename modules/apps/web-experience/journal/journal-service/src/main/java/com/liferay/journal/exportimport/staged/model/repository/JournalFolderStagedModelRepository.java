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

package com.liferay.journal.exportimport.staged.model.repository;

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.journal.model.JournalFolder"},
	service = StagedModelRepository.class
)
public class JournalFolderStagedModelRepository
	extends BaseStagedModelRepository<JournalFolder> {

	@Override
	public JournalFolder addStagedModel(
			PortletDataContext portletDataContext,
			JournalFolder journalFolder)
		throws PortalException {

		long userId = portletDataContext.getUserId(journalFolder.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			journalFolder);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(journalFolder.getUuid());
		}

		return _journalFolderLocalService.addFolder(
			userId, journalFolder.getGroupId(),
			journalFolder.getParentFolderId(), journalFolder.getName(),
			journalFolder.getDescription(), serviceContext);
	}

	@Override
	public void deleteStagedModel(JournalFolder journalFolder)
		throws PortalException {

		_journalFolderLocalService.deleteFolder(journalFolder);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JournalFolder journalFolder =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (journalFolder != null) {
			deleteStagedModel(journalFolder);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_journalFolderLocalService.deleteFolders(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public JournalFolder fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _journalFolderLocalService.fetchJournalFolderByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<JournalFolder> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _journalFolderLocalService.getJournalFoldersByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<JournalFolder>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _journalFolderLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			JournalFolder journalFolder)
		throws PortletDataException {

		long userId = portletDataContext.getUserId(journalFolder.getUserUuid());

		JournalFolder existingFolder = fetchStagedModelByUuidAndGroupId(
			journalFolder.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFolder == null) || !existingFolder.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingFolder.getTrashHandler();

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

	@Override
	public JournalFolder saveStagedModel(JournalFolder journalFolder)
		throws PortalException {

		return _journalFolderLocalService.updateJournalFolder(journalFolder);
	}

	@Override
	public JournalFolder updateStagedModel(
			PortletDataContext portletDataContext, JournalFolder journalFolder)
		throws PortalException {

		long userId = portletDataContext.getUserId(journalFolder.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			journalFolder);

		return _journalFolderLocalService.updateFolder(
			userId, serviceContext.getScopeGroupId(),
			journalFolder.getFolderId(), journalFolder.getParentFolderId(),
			journalFolder.getName(), journalFolder.getDescription(), false,
			serviceContext);
	}

	@Reference(unbind = "-")
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	private JournalFolderLocalService _journalFolderLocalService;

}