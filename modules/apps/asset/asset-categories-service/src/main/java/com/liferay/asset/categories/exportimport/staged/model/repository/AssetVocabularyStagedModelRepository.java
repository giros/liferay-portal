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

package com.liferay.asset.categories.exportimport.staged.model.repository;

import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalService;
import com.liferay.portlet.exportimport.lar.PortletDataContext;
import com.liferay.portlet.exportimport.lar.StagedModelModifiedDateComparator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.portlet.asset.model.AssetVocabulary;"
	},
	service = StagedModelRepository.class
)
public class AssetVocabularyStagedModelRepository
	extends BaseStagedModelRepository<AssetVocabulary> {

	@Override
	public AssetVocabulary addStagedModel(
			PortletDataContext portletDataContext,
			AssetVocabulary assetVocabulary)
		throws PortalException {

		long userId = portletDataContext.getUserId(
			assetVocabulary.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetVocabulary);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(assetVocabulary.getUuid());
		}

		return _assetVocabularyLocalService.addVocabulary(
			userId, portletDataContext.getScopeGroupId(),
			assetVocabulary.getTitle(), assetVocabulary.getTitleMap(),
			assetVocabulary.getDescriptionMap(), assetVocabulary.getSettings(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(AssetVocabulary assetVocabulary)
		throws PortalException {

		_assetVocabularyLocalService.deleteVocabulary(assetVocabulary);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		AssetVocabulary assetVocabulary = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (assetVocabulary != null) {
			deleteStagedModel(assetVocabulary);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_assetVocabularyLocalService.deleteVocabularies(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public AssetVocabulary fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return
			_assetVocabularyLocalService.fetchAssetVocabularyByUuidAndGroupId(
				uuid, groupId);
	}

	@Override
	public List<AssetVocabulary> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return
			_assetVocabularyLocalService.getAssetVocabulariesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<AssetVocabulary>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _assetVocabularyLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public AssetVocabulary saveStagedModel(AssetVocabulary assetVocabulary)
		throws PortalException {

		return _assetVocabularyLocalService.updateAssetVocabulary(
			assetVocabulary);
	}

	@Override
	public AssetVocabulary updateStagedModel(
			PortletDataContext portletDataContext,
			AssetVocabulary assetVocabulary)
		throws PortalException {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetVocabulary);

		return _assetVocabularyLocalService.updateVocabulary(
			assetVocabulary.getVocabularyId(), assetVocabulary.getTitle(),
			assetVocabulary.getTitleMap(), assetVocabulary.getDescriptionMap(),
			assetVocabulary.getSettings(), serviceContext);
	}

	@Reference(unbind = "-")
	protected void setAssetVocabularyLocalService(
		AssetVocabularyLocalService assetVocabularyLocalService) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
	}

	private AssetVocabularyLocalService _assetVocabularyLocalService;

}