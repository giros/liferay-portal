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

package com.liferay.wiki.lar;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.lar.StagedModelRepository;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.StagedModel;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@OSGiBeanProperties(
	property = {"model.name=com.liferay.wiki.model.WikiPage"})
public class WikiPageStagedModelRepository
	implements StagedModelRepository<WikiPage> {

	@Override
	public T addStagedModel(T stagedModel, Map<String, Object> attributes)
		throws Exception;

	@Override
	public List<? extends StagedModel> fetchChildStagedModels(
		WikiPage wikiPage) {

		return Collections.emptyList();
	}

	@Override
	public List<? extends StagedModel> fetchDependentStagedModels(
		WikiPage wikiPage) {

		List<StagedModel> dependentStageModels = new ArrayList<>();

		dependentStageModels.add(wikiPage.getNode());

		if (wikiPage.isHead()) {
			for (FileEntry fileEntry : wikiPage.getAttachmentsFileEntries()) {
				dependentStageModels.add(fileEntry);
			}
		}

		return dependentStageModels;
	}

	@Override
	public WikiPage fetchStagedModelByCustomAttributes(
		Map<String, Object> attributes) {

		return null;
	}

	@Override
	public WikiPage fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<WikiPage> pages =
			_wikiPageLocalService.getWikiPagesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<WikiPage>());

		if (ListUtil.isEmpty(pages)) {
			return null;
		}

		return pages.get(0);
	}

	@Override
	public WikiPage fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _wikiPageLocalService.fetchWikiPageByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<WikiPage> fetchStagedModels(long groupId) {
		DynamicQuery dynamicQuery = _wikiPageLocalService.dynamicQuery();

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(groupId));

		return _wikiPageLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public List<WikiPage> fetchStagedModels(
		PortletDataContext portletDataContext) {

		DynamicQuery dynamicQuery = _wikiPageLocalService.dynamicQuery();

		portletDataContext.addDateRangeCriteria(dynamicQuery, "modifiedDate");

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(
			companyIdProperty.eq(portletDataContext.getCompanyId()));

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(portletDataContext.getGroupId()));

		StagedModelDataHandler<WikiPage> stagedModelDataHandler =
			(StagedModelDataHandler<WikiPage>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					WikiPage.class.getName());

		Property workflowStatusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(
			workflowStatusProperty.in(
				stagedModelDataHandler.getExportableStatuses()));

		return _wikiPageLocalService.dynamicQuery(dynamicQuery);
	}

	@Reference(unbind = "-")
	public void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	public T updateStagedModel(
			T stagedModel, T existingStagedModel,
			Map<String, Object> attributes)
		throws Exception;

	private WikiPageLocalService _wikiPageLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		WikiPageStagedModelRepository.class);
}
