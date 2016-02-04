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

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.persistence.AssetVocabularyUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.HashMap;
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
public class AssetVocabularyStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetVocabulary> {

	public static final String[] CLASS_NAMES =
		{AssetVocabulary.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(AssetVocabulary vocabulary) {
		return vocabulary.getTitleCurrentValue();
	}

	protected ServiceContext createServiceContext(
		PortletDataContext portletDataContext, AssetVocabulary vocabulary) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(vocabulary.getCreateDate());
		serviceContext.setModifiedDate(vocabulary.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		return serviceContext;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, AssetVocabulary vocabulary)
		throws Exception {

		Element vocabularyElement = portletDataContext.getExportDataElement(
			vocabulary);

		String vocabularyPath = ExportImportPathUtil.getModelPath(vocabulary);

		vocabularyElement.addAttribute("path", vocabularyPath);

		vocabulary.setUserUuid(vocabulary.getUserUuid());

		portletDataContext.addReferenceElement(
			vocabulary, vocabularyElement, vocabulary,
			PortletDataContext.REFERENCE_TYPE_DEPENDENCY, false);

		portletDataContext.addPermissions(
			AssetVocabulary.class, vocabulary.getVocabularyId());

		portletDataContext.addZipEntry(vocabularyPath, vocabulary);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long vocabularyId)
		throws Exception {

		AssetVocabulary existingVocabulary = fetchMissingReference(
			uuid, groupId);

		if (existingVocabulary == null) {
			return;
		}

		Map<Long, Long> vocabularyIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetVocabulary.class);

		vocabularyIds.put(vocabularyId, existingVocabulary.getVocabularyId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, AssetVocabulary vocabulary)
		throws Exception {

		AssetVocabulary importedVocabulary =
			(AssetVocabulary)vocabulary.clone();

		AssetVocabulary existingVocabulary =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				vocabulary.getUuid(), portletDataContext.getScopeGroupId());

		if (existingVocabulary == null) {
			String name = getVocabularyName(
				null, portletDataContext.getScopeGroupId(),
				vocabulary.getName(), 2);

			Map<Locale, String> titleMap = getVocabularyTitleMap(
				portletDataContext.getScopeGroupId(), vocabulary, name);

			importedVocabulary.setName(name);
			importedVocabulary.setTitleMap(titleMap);

			importedVocabulary = _stagedModelRepository.addStagedModel(
				portletDataContext, importedVocabulary);
		}
		else {
			importedVocabulary.setVocabularyId(vocabulary.getVocabularyId());

			String name = getVocabularyName(
				vocabulary.getUuid(), portletDataContext.getScopeGroupId(),
				vocabulary.getName(), 2);

			Map<Locale, String> titleMap = getVocabularyTitleMap(
				portletDataContext.getScopeGroupId(), vocabulary, name);

			importedVocabulary.setName(name);
			importedVocabulary.setTitleMap(titleMap);

			importedVocabulary = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedVocabulary);
		}

		Map<Long, Long> vocabularyIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				AssetVocabulary.class);

		vocabularyIds.put(
			vocabulary.getVocabularyId(), importedVocabulary.getVocabularyId());

		portletDataContext.importPermissions(
			AssetVocabulary.class, vocabulary.getVocabularyId(),
			importedVocabulary.getVocabularyId());
	}

	protected String getVocabularyName(
			String uuid, long groupId, String name, int count)
		throws Exception {

		AssetVocabulary vocabulary = AssetVocabularyUtil.fetchByG_N(
			groupId, name);

		if (vocabulary == null) {
			return name;
		}

		if (Validator.isNotNull(uuid) && uuid.equals(vocabulary.getUuid())) {
			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getVocabularyName(uuid, groupId, name, ++count);
	}

	protected Map<Locale, String> getVocabularyTitleMap(
			long groupId, AssetVocabulary vocabulary, String name)
		throws PortalException {

		Map<Locale, String> titleMap = vocabulary.getTitleMap();

		if (titleMap == null) {
			titleMap = new HashMap<>();
		}

		titleMap.put(PortalUtil.getSiteDefaultLocale(groupId), name);

		return titleMap;
	}

	@Reference(target ="com.liferay.portlet.asset.model.AssetTag", unbind = "-")
	protected void setStagedModelRepository(
		StagedModelRepository<AssetVocabulary> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<AssetVocabulary> _stagedModelRepository;

}