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

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.RepositoryEntry;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.repository.portletrepository.PortletRepository;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class FolderStagedModelDataHandler
	extends BaseStagedModelDataHandler<Folder> {

	public static final String[] CLASS_NAMES = {
		DLFolder.class.getName(), Folder.class.getName(),
		LiferayFolder.class.getName()
	};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(Folder folder) {
		return folder.getName();
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Folder folder)
		throws Exception {

		Element folderElement = portletDataContext.getExportDataElement(folder);

		String folderPath = ExportImportPathUtil.getModelPath(folder);

		if (!folder.isDefaultRepository()) {
			Repository repository = _repositoryLocalService.getRepository(
				folder.getRepositoryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, folder, repository,
				PortletDataContext.REFERENCE_TYPE_STRONG);

			portletDataContext.addClassedModel(
				folderElement, folderPath, folder);

			long portletRepositoryClassNameId = PortalUtil.getClassNameId(
				PortletRepository.class.getName());

			if (repository.getClassNameId() != portletRepositoryClassNameId) {
				return;
			}
		}

		if (folder.getParentFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModel(
				portletDataContext, folder, folder.getParentFolder(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		exportFolderFileEntryTypes(portletDataContext, folderElement, folder);

		portletDataContext.addClassedModel(
			folderElement, folderPath, folder, DLFolder.class);
	}

	@Override
	protected void doImportMissingReference(
			PortletDataContext portletDataContext, String uuid, long groupId,
			long folderId)
		throws Exception {

		Folder existingFolder = fetchMissingReference(uuid, groupId);

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Folder.class);

		folderIds.put(folderId, existingFolder.getFolderId());
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Folder folder)
		throws Exception {

		Map<Long, Long> folderIdsAndRepositoryEntryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Folder.class + ".folderIdsAndRepositoryEntryIds");

		if (!folder.isDefaultRepository()) {
			Map<Long, Long> repositoryEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					RepositoryEntry.class);

			folderIdsAndRepositoryEntryIds.put(
				folder.getFolderId(),
				repositoryEntryIds.get(folder.getFolderId()));

			return;
		}

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Folder.class);

		long parentFolderId = MapUtil.getLong(
			folderIds, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folder, DLFolder.class);

		serviceContext.setUserId(userId);

		Folder importedFolder = (Folder)folder.clone();

		importedFolder.setParentFolderId();

		Folder existingFolder =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				folder.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFolder == null) ||
			!portletDataContext.isDataStrategyMirror()) {

			importedFolder = _stagedModelRepository.addStagedModel(
				portletDataContext, importedFolder);
		}
		else {
			importedFolder.setFolderId(existingFolder.getFolderId());

			importedFolder = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedFolder);
		}

		Element folderElement = portletDataContext.getImportDataElement(folder);

		importFolderFileEntryTypes(
			portletDataContext, folderElement, folder, importedFolder,
			serviceContext);

		portletDataContext.importClassedModel(
			folder, importedFolder, DLFolder.class);

		folderIds.put(folder.getFolderId(), importedFolder.getFolderId());
		folderIdsAndRepositoryEntryIds.put(
			folder.getFolderId(), importedFolder.getFolderId());
	}

	protected void exportFolderFileEntryTypes(
			PortletDataContext portletDataContext, Element folderElement,
			Folder folder)
		throws Exception {

		if (!folder.isDefaultRepository()) {
			return;
		}

		List<DLFileEntryType> dlFileEntryTypes =
			_dlFileEntryTypeLocalService.getFolderFileEntryTypes(
				new long[] {
					portletDataContext.getCompanyGroupId(),
					portletDataContext.getScopeGroupId()
				},
				folder.getFolderId(), false);

		long defaultFileEntryTypeId =
			_dlFileEntryTypeLocalService.getDefaultFileEntryTypeId(
				folder.getFolderId());

		String defaultFileEntryTypeUuid = StringPool.BLANK;

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			if (dlFileEntryType.getFileEntryTypeId() ==
					DLFileEntryTypeConstants.
						FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

				folderElement.addAttribute("basic-document", "true");

				continue;
			}

			if (defaultFileEntryTypeId ==
					dlFileEntryType.getFileEntryTypeId()) {

				defaultFileEntryTypeUuid = dlFileEntryType.getUuid();
			}

			if (dlFileEntryType.isExportable()) {
				StagedModelDataHandlerUtil.exportReferenceStagedModel(
					portletDataContext, folder, dlFileEntryType,
					PortletDataContext.REFERENCE_TYPE_STRONG);
			}
		}

		folderElement.addAttribute(
			"defaultFileEntryTypeUuid", defaultFileEntryTypeUuid);
	}

	protected void importFolderFileEntryTypes(
			PortletDataContext portletDataContext, Element folderElement,
			Folder folder, Folder importedFolder, ServiceContext serviceContext)
		throws Exception {

		if (!folder.isDefaultRepository()) {
			return;
		}

		List<Long> currentFolderFileEntryTypeIds = new ArrayList<>();

		String defaultFileEntryTypeUuid = GetterUtil.getString(
			folderElement.attributeValue("defaultFileEntryTypeUuid"));

		long defaultFileEntryTypeId = 0;

		List<Element> referenceElements =
			portletDataContext.getReferenceElements(
				folder, DLFileEntryType.class);

		for (Element referenceElement : referenceElements) {
			long referenceDlFileEntryTypeId = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));
			String referenceDlFileEntryTypeUuid =
				referenceElement.attributeValue("uuid");

			Map<Long, Long> dlFileEntryTypeIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DLFileEntryType.class);

			long dlFileEntryTypeId = MapUtil.getLong(
				dlFileEntryTypeIds, referenceDlFileEntryTypeId,
				referenceDlFileEntryTypeId);

			DLFileEntryType existingDLFileEntryType =
				_dlFileEntryTypeLocalService.fetchDLFileEntryType(
					dlFileEntryTypeId);

			if (existingDLFileEntryType == null) {
				continue;
			}

			currentFolderFileEntryTypeIds.add(
				existingDLFileEntryType.getFileEntryTypeId());

			if (defaultFileEntryTypeUuid.equals(referenceDlFileEntryTypeUuid)) {
				defaultFileEntryTypeId =
					existingDLFileEntryType.getFileEntryTypeId();
			}
		}

		if (GetterUtil.getBoolean(
				folderElement.attributeValue("basic-document"))) {

			currentFolderFileEntryTypeIds.add(
				DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT);
		}

		if (!currentFolderFileEntryTypeIds.isEmpty()) {
			DLFolder dlFolder = (DLFolder)importedFolder.getModel();

			dlFolder.setDefaultFileEntryTypeId(defaultFileEntryTypeId);
			dlFolder.setRestrictionType(
				DLFolderConstants.
					RESTRICTION_TYPE_FILE_ENTRY_TYPES_AND_WORKFLOW);

			_dlFolderLocalService.updateDLFolder(dlFolder);

			_dlFileEntryTypeLocalService.updateFolderFileEntryTypes(
				dlFolder, currentFolderFileEntryTypeIds, defaultFileEntryTypeId,
				serviceContext);
		}
	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Reference(unbind = "-")
	protected void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
	}

	@Reference(unbind = "-")
	protected void setRepositoryLocalService(
		RepositoryLocalService repositoryLocalService) {

		_repositoryLocalService = repositoryLocalService;
	}

	@Reference(unbind = "-")
	protected void setStagedModelRepository(
		StagedModelRepository<Folder> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	@Override
	protected void validateExport(
			PortletDataContext portletDataContext, Folder folder)
		throws PortletDataException {

		if ((folder.getGroupId() != portletDataContext.getGroupId()) &&
			(folder.getGroupId() != portletDataContext.getScopeGroupId())) {

			PortletDataException pde = new PortletDataException(
				PortletDataException.INVALID_GROUP);

			pde.setStagedModel(folder);

			throw pde;
		}

		if (folder instanceof LiferayFolder) {
			LiferayFolder liferayFolder = (LiferayFolder)folder;

			DLFolder dlFolder = (DLFolder)liferayFolder.getModel();

			if (dlFolder.isInTrash() || dlFolder.isInTrashContainer()) {
				PortletDataException pde = new PortletDataException(
					PortletDataException.STATUS_IN_TRASH);

				pde.setStagedModel(folder);

				throw pde;
			}
		}
	}

	private DLAppLocalService _dlAppLocalService;
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private DLFolderLocalService _dlFolderLocalService;
	private RepositoryLocalService _repositoryLocalService;
	private StagedModelRepository<Folder> _stagedModelRepository;

}