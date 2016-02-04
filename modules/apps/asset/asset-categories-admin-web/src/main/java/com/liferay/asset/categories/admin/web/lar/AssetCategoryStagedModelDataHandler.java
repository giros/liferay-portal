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

package com.liferay.asset.categories.admin.web.lar;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetCategoryProperty;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetCategoryPropertyLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.kernel.service.persistence.AssetCategoryUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zsolt Berentey
 * @author Gergely Mathe
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class AssetCategoryStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetCategory> {

	public static final String[] CLASS_NAMES = {AssetCategory.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(AssetCategory category) {
		return category.getTitleCurrentValue();
	}

	protected ServiceContext createServiceContext(
		PortletDataContext portletDataContext, AssetCategory category) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(category.getCreateDate());
		serviceContext.setModifiedDate(category.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		return serviceContext;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, AssetCategory category)
		throws Exception {

		if (category.getParentCategoryId() !=
				AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

			AssetCategory parentCategory =
				_assetCategoryLocalService.fetchAssetCategory(
					category.getParentCategoryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, category, parentCategory,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}
		else {
			AssetVocabulary vocabulary =
				_assetVocabularyLocalService.fetchAssetVocabulary(
					category.getVocabularyId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, category, vocabulary,
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element categoryElement = portletDataContext.getExportDataElement(
			category);

		category.setUserUuid(category.getUserUuid());

		List<AssetCategoryProperty> categoryProperties =
			_assetCategoryPropertyLocalService.getCategoryProperties(
				category.getCategoryId());

		for (AssetCategoryProperty categoryProperty : categoryProperties) {
			Element propertyElement = categoryElement.addElement("property");

			propertyElement.addAttribute(
				"userUuid", categoryProperty.getUserUuid());
			propertyElement.addAttribute("key", categoryProperty.getKey());
			propertyElement.addAttribute("value", categoryProperty.getValue());
		}

		String categoryPath = ExportImportPathUtil.getModelPath(category);

		categoryElement.addAttribute("path", categoryPath);

		portletDataContext.addReferenceElement(
			category, categoryElement, category,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, false);

		portletDataContext.addPermissions(
			AssetCategory.class, category.getCategoryId());

		portletDataContext.addZipEntry(categoryPath, category);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long categoryId)
		throws Exception {

		AssetCategory existingCategory = fetchMissingReference(uuid, groupId);

		if (existingCategory == null) {
			return;
		}

		Map<Long, Long> categoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetCategory.class);

		categoryIds.put(categoryId, existingCategory.getCategoryId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, AssetCategory category)
		throws Exception {

		Map<Long, Long> vocabularyIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetVocabulary.class);

		long vocabularyId = MapUtil.getLong(
			vocabularyIds, category.getVocabularyId(),
			category.getVocabularyId());

		Map<Long, Long> categoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetCategory.class);

		long parentCategoryId = MapUtil.getLong(
			categoryIds, category.getParentCategoryId(),
			category.getParentCategoryId());

		AssetCategory importedCategory = (AssetCategory)category.clone();

		importedCategory.setVocabularyId(vocabularyId);
		importedCategory.setParentCategoryId(parentCategoryId);

		AssetCategory existingCategory =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				category.getUuid(), portletDataContext.getScopeGroupId());

		if (existingCategory == null) {
			String name = getCategoryName(
				null, portletDataContext.getScopeGroupId(), parentCategoryId,
				category.getName(), vocabularyId, 2);

			Map<Locale, String> titleMap = getCategoryTitleMap(
				portletDataContext.getScopeGroupId(), category, name);

			importedCategory.setName(name);
			importedCategory.setTitleMap(titleMap);

			importedCategory = _stagedModelRepository.addStagedModel(
				portletDataContext, importedCategory);
		}
		else {
			importedCategory.setCategoryId(category.getCategoryId());

			String name = getCategoryName(
				category.getUuid(), portletDataContext.getScopeGroupId(),
				parentCategoryId, category.getName(), vocabularyId, 2);

			Map<Locale, String> titleMap = getCategoryTitleMap(
				portletDataContext.getScopeGroupId(), category, name);

			importedCategory.setName(name);
			importedCategory.setTitleMap(titleMap);

			importedCategory = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedCategory);
		}

		categoryIds.put(
			category.getCategoryId(), importedCategory.getCategoryId());

		Map<String, String> categoryUuids =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				AssetCategory.class + ".uuid");

		categoryUuids.put(category.getUuid(), importedCategory.getUuid());

		portletDataContext.importPermissions(
			AssetCategory.class, category.getCategoryId(),
			importedCategory.getCategoryId());
	}

	protected String getCategoryName(
			String uuid, long groupId, long parentCategoryId, String name,
			long vocabularyId, int count)
		throws Exception {

		AssetCategory category = AssetCategoryUtil.fetchByG_P_N_V_First(
			groupId, parentCategoryId, name, vocabularyId, null);

		if ((category == null) ||
			(Validator.isNotNull(uuid) && uuid.equals(category.getUuid()))) {

			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getCategoryName(
			uuid, groupId, parentCategoryId, name, vocabularyId, ++count);
	}

	protected Map<Locale, String> getCategoryTitleMap(
			long groupId, AssetCategory category, String name)
		throws PortalException {

		Map<Locale, String> titleMap = category.getTitleMap();

		if (titleMap == null) {
			titleMap = new HashMap<>();
		}

		titleMap.put(PortalUtil.getSiteDefaultLocale(groupId), name);

		return titleMap;
	}

	@Reference(unbind = "-")
	protected void setAssetCategoryLocalService(
		AssetCategoryLocalService assetCategoryLocalService) {

		_assetCategoryLocalService = assetCategoryLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetCategoryPropertyLocalService(
		AssetCategoryPropertyLocalService assetCategoryPropertyLocalService) {

		_assetCategoryPropertyLocalService = assetCategoryPropertyLocalService;
	}

	@Reference(unbind = "-")
	protected void setAssetVocabularyLocalService(
		AssetVocabularyLocalService assetVocabularyLocalService) {

		_assetVocabularyLocalService = assetVocabularyLocalService;
	}

	@Reference(
		target ="com.liferay.portlet.asset.model.AssetCategory", unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<AssetCategory> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private AssetCategoryLocalService _assetCategoryLocalService;
	private AssetCategoryPropertyLocalService
		_assetCategoryPropertyLocalService;
	private AssetVocabularyLocalService _assetVocabularyLocalService;
	private StagedModelRepository<AssetCategory> _stagedModelRepository;

}