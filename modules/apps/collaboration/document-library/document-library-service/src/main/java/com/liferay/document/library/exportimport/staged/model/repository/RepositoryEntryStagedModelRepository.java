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

import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.portal.kernel.model.RepositoryEntry"
	},
	service = StagedModelRepository.class
)
public class RepositoryEntryStagedModelRepository
	extends BaseStagedModelRepository<RepositoryEntry> {

	public RepositoryEntry addStagedModel(
			PortletDataContext portletDataContext,
			RepositoryEntry repositoryEntry)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			repositoryEntry.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
				repositoryEntry);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(repositoryEntry.getUuid());
		}

		return _repositoryEntryLocalService.addRepositoryEntry(
			userId, portletDataContext.getScopeGroupId(),
			repositoryEntry.getRepositoryId(), repositoryEntry.getMappedId(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		RepositoryEntry repositoryEntry = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (repositoryEntry != null) {
			deleteStagedModel(repositoryEntry);
		}
	}

	@Override
	public void deleteStagedModel(RepositoryEntry repositoryEntry)
		throws PortalException {

		_repositoryEntryLocalService.deleteRepositoryEntry(repositoryEntry);
	}

	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public RepositoryEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _repositoryEntryLocalService.
			fetchRepositoryEntryByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<RepositoryEntry> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _repositoryEntryLocalService.
			getRepositoryEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<RepositoryEntry>());
	}

	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _repositoryEntryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	public RepositoryEntry saveStagedModel(RepositoryEntry repositoryEntry)
		throws PortalException {

		return _repositoryEntryLocalService.updateRepositoryEntry(
			repositoryEntry);
	}

	public RepositoryEntry updateStagedModel(
			PortletDataContext portletDataContext,
			RepositoryEntry repositoryEntry)
		throws PortalException {

		return _repositoryEntryLocalService.updateRepositoryEntry(
			repositoryEntry.getRepositoryEntryId(),
			repositoryEntry.getMappedId());
	}

	@Reference(unbind = "-")
	protected void setRepositoryEntryLocalService(
		RepositoryEntryLocalService repositoryEntryLocalService) {

		_repositoryEntryLocalService = repositoryEntryLocalService;
	}

	private RepositoryEntryLocalService _repositoryEntryLocalService;

}
