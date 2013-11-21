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
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.service.AssetVocabularyLocalServiceUtil;

/**
 * @author Zsolt Berentey
 * @author Gergely Mathe
 */
public class AssetVocabularyStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetVocabulary> {

	public static final String[] CLASS_NAMES =
		{AssetVocabulary.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws SystemException {

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.
				fetchAssetVocabularyByUuidAndGroupId(uuid, groupId);

		if (vocabulary != null) {
			AssetVocabularyLocalServiceUtil.deleteAssetVocabulary(vocabulary);
		}
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(AssetVocabulary vocabulary) {
		return vocabulary.getTitleCurrentValue();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, AssetVocabulary vocabulary)
		throws Exception {

		Element vocabularyElement = portletDataContext.getExportDataElement(
			vocabulary);

		portletDataContext.addClassedModel(
			vocabularyElement, ExportImportPathUtil.getModelPath(vocabulary),
			vocabulary);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, AssetVocabulary vocabulary)
		throws Exception {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			vocabulary);

		AssetVocabulary importedVocabulary = null;

		long userId = portletDataContext.getUserId(vocabulary.getUserUuid());

		if (portletDataContext.isDataStrategyMirror()) {
			AssetVocabulary existingVocabulary =
				AssetVocabularyLocalServiceUtil.
					fetchAssetVocabularyByUuidAndGroupId(
						vocabulary.getUuid(),
						portletDataContext.getScopeGroupId());

			if (existingVocabulary == null) {
				serviceContext.setUuid(vocabulary.getUuid());

				importedVocabulary =
					AssetVocabularyLocalServiceUtil.addVocabulary(
						userId, vocabulary.getTitle(), vocabulary.getTitleMap(),
						vocabulary.getDescriptionMap(),
						vocabulary.getSettings(), serviceContext);
			}
			else {
				importedVocabulary =
					AssetVocabularyLocalServiceUtil.updateVocabulary(
						existingVocabulary.getVocabularyId(),
						vocabulary.getTitle(), vocabulary.getTitleMap(),
						vocabulary.getDescriptionMap(),
						vocabulary.getSettings(), serviceContext);
			}
		}
		else {
			importedVocabulary =
				AssetVocabularyLocalServiceUtil.addVocabulary(
					userId, vocabulary.getTitle(), vocabulary.getTitleMap(),
					vocabulary.getDescriptionMap(), vocabulary.getSettings(),
					serviceContext);
		}

		portletDataContext.importClassedModel(vocabulary, importedVocabulary);
	}

	@Override
	protected boolean validateMissingReference(
			String uuid, long companyId, long groupId)
		throws Exception {

		AssetVocabulary vocabulary =
			AssetVocabularyLocalServiceUtil.
				fetchAssetVocabularyByUuidAndGroupId(uuid, groupId);

		if (vocabulary == null) {
			return false;
		}

		return true;
	}

}