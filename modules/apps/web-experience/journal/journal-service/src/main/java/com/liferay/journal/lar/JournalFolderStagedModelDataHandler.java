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

package com.liferay.journal.lar;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.journal.service.persistence.JournalFolderUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class JournalFolderStagedModelDataHandler
	extends BaseStagedModelDataHandler<JournalFolder> {

	public static final String[] CLASS_NAMES = {JournalFolder.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(JournalFolder folder) {
		return folder.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		if (folder.getParentFolderId() !=
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, folder, folder.getParentFolder(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		Element folderElement = portletDataContext.getExportDataElement(folder);

		exportFolderDDMStructures(portletDataContext, folder);

		portletDataContext.addClassedModel(
			folderElement, ExportImportPathUtil.getModelPath(folder), folder);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				JournalFolder.class);

		long parentFolderId = MapUtil.getLong(
			folderIds, folder.getParentFolderId(), folder.getParentFolderId());

		JournalFolder importedFolder = (JournalFolder)folder.clone();

		importedFolder.setParentFolderId(parentFolderId);

		long groupId = portletDataContext.getScopeGroupId();

		JournalFolder existingFolder =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				folder.getUuid(), groupId);

		if (existingFolder == null) {
			String name = getFolderName(
				null, groupId, parentFolderId, folder.getName(), 2);

			importedFolder.setName(name);

			importedFolder = _stagedModelRepository.addStagedModel(
				portletDataContext, importedFolder);
		}
		else {
			importedFolder.setFolderId(existingFolder.getFolderId());

			String name = getFolderName(
				folder.getUuid(), groupId, parentFolderId, folder.getName(), 2);

			importedFolder.setName(name);

			importedFolder = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedFolder);
		}

		importFolderDDMStructures(portletDataContext, folder, importedFolder);

		portletDataContext.importClassedModel(folder, importedFolder);
	}

	protected void exportFolderDDMStructures(
			PortletDataContext portletDataContext, JournalFolder folder)
		throws Exception {

		List<DDMStructure> ddmStructures =
			_journalFolderLocalService.getDDMStructures(
				new long[] {
					portletDataContext.getCompanyGroupId(),
					portletDataContext.getScopeGroupId()
				},
				folder.getFolderId(),
				JournalFolderConstants.
					RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW
			);

		for (DDMStructure ddmStructure : ddmStructures) {
			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, folder, ddmStructure,
				PortletDataContext.REFERENCE_TYPE_STRONG);
		}
	}

	protected String getFolderName(
			String uuid, long groupId, long parentFolderId, String name,
			int count)
		throws Exception {

		JournalFolder folder = JournalFolderUtil.fetchByG_P_N(
			groupId, parentFolderId, name);

		if (folder == null) {
			return name;
		}

		if (Validator.isNotNull(uuid) && uuid.equals(folder.getUuid())) {
			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getFolderName(uuid, groupId, parentFolderId, name, ++count);
	}

	protected void importFolderDDMStructures(
			PortletDataContext portletDataContext, JournalFolder folder,
			JournalFolder importedFolder)
		throws Exception {

		List<Long> currentFolderDDMStructureIds = new ArrayList<>();

		List<Element> referenceElements =
			portletDataContext.getReferenceElements(folder, DDMStructure.class);

		for (Element referenceElement : referenceElements) {
			long referenceDDMStructureId = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));

			Map<Long, Long> ddmStructureIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DDMStructure.class);

			long ddmStructureId = MapUtil.getLong(
				ddmStructureIds, referenceDDMStructureId,
				referenceDDMStructureId);

			DDMStructure existingDDMStructure =
				_ddmStructureLocalService.fetchDDMStructure(ddmStructureId);

			if (existingDDMStructure == null) {
				continue;
			}

			currentFolderDDMStructureIds.add(
				existingDDMStructure.getStructureId());
		}

		if (!currentFolderDDMStructureIds.isEmpty()) {
			importedFolder.setRestrictionType(
				JournalFolderConstants.
					RESTRICTION_TYPE_DDM_STRUCTURES_AND_WORKFLOW);

			_stagedModelRepository.saveStagedModel(importedFolder);

			_journalFolderLocalService.updateFolderDDMStructures(
				importedFolder,
				ArrayUtil.toLongArray(currentFolderDDMStructureIds));
		}
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {

		_ddmStructureLocalService = ddmStructureLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	@Reference(
		target =
			"(model.class.name=com.liferay.journal.model.JournalFolder)",
		unbind = "-"
	)
	protected void setStagedModelRepository(
		StagedModelRepository<JournalFolder> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private DDMStructureLocalService _ddmStructureLocalService;
	private JournalFolderLocalService _journalFolderLocalService;
	private StagedModelRepository<JournalFolder> _stagedModelRepository;

}