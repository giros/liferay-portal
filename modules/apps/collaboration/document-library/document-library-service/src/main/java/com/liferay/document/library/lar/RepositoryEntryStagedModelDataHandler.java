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

import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class RepositoryEntryStagedModelDataHandler
	extends BaseStagedModelDataHandler<RepositoryEntry> {

	public static final String[] CLASS_NAMES =
		{RepositoryEntry.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext,
			RepositoryEntry repositoryEntry)
		throws Exception {

		Element repositoryEntryElement =
			portletDataContext.getExportDataElement(repositoryEntry);

		portletDataContext.addClassedModel(
			repositoryEntryElement,
			ExportImportPathUtil.getModelPath(repositoryEntry),
			repositoryEntry);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext,
			RepositoryEntry repositoryEntry)
		throws Exception {

		long userId = portletDataContext.getUserId(
			repositoryEntry.getUserUuid());

		Map<Long, Long> repositoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Repository.class);

		long repositoryId = MapUtil.getLong(
			repositoryIds, repositoryEntry.getRepositoryId(),
			repositoryEntry.getRepositoryId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			repositoryEntry);

		RepositoryEntry importedRepositoryEntry =
			(RepositoryEntry)repositoryEntry.clone();

		RepositoryEntry existingRepositoryEntry =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				repositoryEntry.getUuid(),
				portletDataContext.getScopeGroupId());

		if ((existingRepositoryEntry == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedRepositoryEntry = _stagedModelRepository.addStagedModel(
				portletDataContext, importedRepositoryEntry);
		}
		else {
			importedRepositoryEntry.setRepositoryId(
				existingRepositoryEntry.getRepositoryId());

			importedRepositoryEntry = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedRepositoryEntry);
		}

		portletDataContext.importClassedModel(
			repositoryEntry, importedRepositoryEntry);
	}

	@Reference(unbind = "-")
	protected void setStagedModelRepository(
		StagedModelRepository<RepositoryEntry> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<RepositoryEntry> _stagedModelRepository;

}