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

import com.liferay.asset.categories.admin.web.lar.Element;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.service.AssetCategoryLocalService;
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
		"model.class.name=com.liferay.portlet.asset.model.AssetCategory;"
	},
	service = StagedModelRepository.class
)
public class AssetCategoryStagedModelRepository
	extends BaseStagedModelRepository<AssetCategory> {

	@Override
	public AssetCategory addStagedModel(
			PortletDataContext portletDataContext, AssetCategory assetCategory)
		throws PortalException {

		long userId = portletDataContext.getUserId(assetCategory.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetCategory);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(assetCategory.getUuid());
		}

		return _assetCategoryLocalService.addCategory(
			userId, portletDataContext.getScopeGroupId(),
			assetCategory.getParentCategoryId(), assetCategory.getTitleMap(),
			assetCategory.getDescriptionMap(), assetCategory.getVocabularyId(),
			getCategoryProperties(portletDataContext, assetCategory),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(AssetCategory assetCategory)
		throws PortalException {

		_assetCategoryLocalService.deleteCategory(assetCategory);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		AssetCategory assetCategory = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (assetCategory != null) {
			deleteStagedModel(assetCategory);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public AssetCategory fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _assetCategoryLocalService.fetchAssetCategoryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<AssetCategory> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _assetCategoryLocalService.getAssetCategorysByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<AssetCategory>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _assetCategoryLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public AssetCategory saveStagedModel(AssetCategory assetCategory)
		throws PortalException {

		return _assetCategoryLocalService.updateAssetCategory(assetCategory);
	}

	@Override
	public AssetCategory updateStagedModel(
			PortletDataContext portletDataContext, AssetCategory assetCategory)
		throws PortalException {

		long userId = portletDataContext.getUserId(assetCategory.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetCategory);

		return _assetCategoryLocalService.updateCategory(
			userId, assetCategory.getCategoryId(),
			assetCategory.getParentCategoryId(), assetCategory.getTitleMap(),
			assetCategory.getDescriptionMap(), assetCategory.getVocabularyId(),
			getCategoryProperties(portletDataContext, assetCategory),
			serviceContext);
	}

	protected String[] getCategoryProperties(
		PortletDataContext portletDataContext, AssetCategory assetCategory) {

		Element categoryElement = portletDataContext.getImportDataElement(
			assetCategory);

		List<Element> propertyElements = categoryElement.elements("property");

		String[] properties = new String[propertyElements.size()];

		for (int i = 0; i < propertyElements.size(); i++) {
			Element propertyElement = propertyElements.get(i);

			String key = propertyElement.attributeValue("key");
			String value = propertyElement.attributeValue("value");

			properties[i] = key.concat(
				AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR).concat(
					value);
		}

		return properties;
	}

	@Reference(unbind = "-")
	protected void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
	}

	private AssetCategoryLocalService _assetCategoryLocalService;

}