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

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.model.adapter.StagedRepository;
import com.liferay.document.library.model.adapter.impl.StagedRepositoryImpl;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.portal.kernel.model.Repository"},
	service = StagedModelRepository.class
)
public class RepositoryStagedModelRepository 
	extends BaseStagedModelRepository<Repository> {

	@Override
	public Repository addStagedModel(
			PortletDataContext portletDataContext, Repository repository)
		throws PortalException {

		StagedRepository stagedRepository =
			new StagedRepositoryImpl(repository);

		return addStagedModel(portletDataContext, stagedRepository);
	}

	public Repository addStagedModel(
			PortletDataContext portletDataContext, StagedRepository repository)
		throws PortalException {

		long userId = portletDataContext.getUserId(repository.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			repository);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(repository.getUuid());
		}

		return _repositoryLocalService.addRepository(
			userId, portletDataContext.getScopeGroupId(),
			repository.getClassNameId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			repository.getName(), repository.getDescription(),
			repository.getPortletId(),
			repository.getTypeSettingsProperties(), repository.getHidden(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Repository repository = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (repository != null) {
			deleteStagedModel(repository);
		}
	}

	@Override
	public void deleteStagedModel(Repository repository)
		throws PortalException {

		_repositoryLocalService.deleteRepository(repository.getRepositoryId());
	}

	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_repositoryLocalService.deleteRepositories(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public Repository fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _repositoryLocalService.fetchRepositoryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<Repository> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _repositoryLocalService.getRepositoriesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<Repository>());
	}

	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _repositoryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	public Repository saveStagedModel(Repository repository)
		throws PortalException {

		return _repositoryLocalService.updateRepository(repository);
	}

	@Override
	public Repository updateStagedModel(
			PortletDataContext portletDataContext, Repository repository)
		throws PortalException {

		_repositoryLocalService.updateRepository(
			repository.getRepositoryId(),
			repository.getName(), repository.getDescription());

		return repository;
	}

	@Reference(unbind = "-")
	protected void setRepositoryLocalService(
		RepositoryLocalService repositoryLocalService) {

		_repositoryLocalService = repositoryLocalService;
	}

	private RepositoryLocalService _repositoryLocalService;

}
