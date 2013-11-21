/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.lar;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetCategoryLocalServiceUtil;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

import java.util.Map;

/**
 * @author Zsolt Berentey
 * @author Gergely Mathe
 */
public class AssetCategoryStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetCategory> {

	public static final String[] CLASS_NAMES = {AssetCategory.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws SystemException {

		AssetCategory category =
			AssetCategoryLocalServiceUtil.fetchAssetCategoryByUuidAndGroupId(
				uuid, groupId);

		if (category != null) {
			AssetCategoryLocalServiceUtil.deleteAssetCategory(category);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(AssetCategory category) {
		return category.getTitleCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, AssetCategory category)
		throws Exception {

		if (category.getParentCategoryId() !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			AssetCategory parentCategory =
				AssetCategoryLocalServiceUtil.fetchAssetCategory(
					category.getParentCategoryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, category, parentCategory,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}
		else {
			AssetVocabulary assetVocabulary =
				AssetVocabularyLocalServiceUtil.fetchAssetVocabulary(
					category.getVocabularyId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, category, assetVocabulary,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element categoryElement = portletDataContext.getExportDataElement(
			category);

		portletDataContext.addClassedModel(
			categoryElement, ExportImportPathUtil.getModelPath(category),
			category);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, AssetCategory category)
		throws Exception {

		if (category.getParentCategoryId() !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, category, AssetCategory.class,
				category.getParentCategoryId());
		}
		else {
			StagedModelDataHandlerUtil.importReferenceStagedModel(
				portletDataContext, category, AssetVocabulary.class,
				category.getVocabularyId());
		}

		Map<Long, Long> vocabularyIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetVocabulary.class);

		long vocabularyId = MapUtil.getLong(
			vocabularyIds, category.getVocabularyId(),
			category.getVocabularyId());

		Map <Long, Long> categoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetCategory.class);

		long parentCategoryId = MapUtil.getLong(
			categoryIds, category.getParentCategoryId(),
			category.getParentCategoryId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			category);

		AssetCategory importedCategory = null;

		long userId = portletDataContext.getUserId(category.getUserUuid());

		if (portletDataContext.isDataStrategyMirror()) {
			AssetCategory existingCategory =
				AssetCategoryLocalServiceUtil.
					fetchAssetCategoryByUuidAndGroupId(
						category.getUuid(),
						portletDataContext.getScopeGroupId());

			if (existingCategory == null) {
				serviceContext.setUuid(category.getUuid());

				importedCategory = AssetCategoryLocalServiceUtil.addCategory(
					userId, parentCategoryId, category.getTitleMap(),
					category.getDescriptionMap(), vocabularyId, null,
					serviceContext);
			}
			else {
				importedCategory = AssetCategoryLocalServiceUtil.updateCategory(
					userId, existingCategory.getCategoryId(), parentCategoryId,
					category.getTitleMap(), category.getDescriptionMap(),
					vocabularyId, null, serviceContext);
			}
		}
		else {
			importedCategory = AssetCategoryLocalServiceUtil.addCategory(
				userId, parentCategoryId, category.getTitleMap(),
				category.getDescriptionMap(), vocabularyId, null,
				serviceContext);
		}

		portletDataContext.importClassedModel(category, importedCategory);
	}

	@Override
	protected boolean validateMissingReference(
			String uuid, long companyId, long groupId)
		throws Exception {

		AssetCategory category =
			AssetCategoryLocalServiceUtil.fetchAssetCategoryByUuidAndGroupId(
				uuid, groupId);

		if (category == null) {
			return false;
		}

		return true;
	}

}