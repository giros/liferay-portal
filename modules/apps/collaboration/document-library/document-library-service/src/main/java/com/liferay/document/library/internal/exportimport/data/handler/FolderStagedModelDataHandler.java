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

package com.liferay.document.library.internal.exportimport.data.handler;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.kernel.lar.file.LARFile;
import com.liferay.exportimport.kernel.lar.file.LARFileFactoryUtil;
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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.repository.portletrepository.PortletRepository;
import com.liferay.portal.util.RepositoryUtil;
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
	public void deleteStagedModel(Folder folder) throws PortalException {
		_dlAppLocalService.deleteFolder(folder.getFolderId());
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Folder folder = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (folder != null) {
			deleteStagedModel(folder);
		}
	}

	@Override
	public Folder fetchStagedModelByUuidAndGroupId(String uuid, long groupId) {
		DLFolder dlFolder = _dlFolderLocalService.fetchFolder(uuid, groupId);

		if (dlFolder != null) {
			return new LiferayFolder(dlFolder);
		}

		return null;
	}

	@Override
	public List<Folder> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<DLFolder> dlFolders =
			_dlFolderLocalService.getDLFoldersByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DLFolder>());

		List<Folder> folders = new ArrayList<>();

		for (DLFolder dlFolder : dlFolders) {
			folders.add(new LiferayFolder(dlFolder));
		}

		return folders;
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(Folder folder) {
		return folder.getName();
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext, Folder stagedModel)
		throws PortletDataException {

		try {
			doRestoreStagedModel(portletDataContext, stagedModel);
		}
		catch (PortletDataException pde) {
			throw pde;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Folder folder)
		throws Exception {

		LARFile larFile = LARFileFactoryUtil.getLARFile(portletDataContext);

		larFile.startWriteStagedModel(folder);

		if (!folder.isDefaultRepository()) {
			Repository repository = _repositoryLocalService.getRepository(
				folder.getRepositoryId());

			StagedModelDataHandlerUtil.exportReferenceStagedModelStream(
				portletDataContext, folder, repository,
				PortletDataContext.REFERENCE_TYPE_STRONG);

			portletDataContext.addStagedModel(folder);

			boolean rootFolder = false;

			if (folder.getFolderId() == repository.getDlFolderId()) {
				rootFolder = true;
			}

			larFile.writeStagedModelAttribute(
				"rootFolder", String.valueOf(rootFolder));

			long portletRepositoryClassNameId = _portal.getClassNameId(
				PortletRepository.class.getName());

			if (repository.getClassNameId() != portletRepositoryClassNameId) {
				return;
			}
		}

		if (folder.getParentFolderId() !=
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			StagedModelDataHandlerUtil.exportReferenceStagedModelStream(
				portletDataContext, folder, folder.getParentFolder(),
				PortletDataContext.REFERENCE_TYPE_PARENT);
		}

		exportFolderFileEntryTypes(portletDataContext, folder);

		portletDataContext.addStagedModel(folder);

		portletDataContext.addReferences(folder);

		larFile.endWriteStagedModel(folder);
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

		if (RepositoryUtil.isExternalRepository(folder.getRepositoryId())) {
			Map<Long, Long> repositoryEntryIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					RepositoryEntry.class);

			folderIdsAndRepositoryEntryIds.put(
				folder.getFolderId(),
				repositoryEntryIds.get(folder.getFolderId()));

			return;
		}

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Map<Long, Long> repositoryIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Repository.class);

		long repositoryId = MapUtil.getLong(
			repositoryIds, folder.getRepositoryId(),
			portletDataContext.getScopeGroupId());

		Map<Long, Long> folderIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				Folder.class);

		long parentFolderId = MapUtil.getLong(
			folderIds, folder.getParentFolderId(), folder.getParentFolderId());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			folder, DLFolder.class);

		serviceContext.setUserId(userId);

		Element folderElement = portletDataContext.getImportDataElement(folder);

		Folder importedFolder = null;

		if (portletDataContext.isDataStrategyMirror()) {
			boolean rootFolder = GetterUtil.getBoolean(
				folderElement.attributeValue("rootFolder"));

			if (rootFolder) {
				Repository repository = _repositoryLocalService.getRepository(
					repositoryId);

				importedFolder = _dlAppLocalService.getFolder(
					repository.getDlFolderId());
			}
			else {
				Folder existingFolder = fetchStagedModelByUuidAndGroupId(
					folder.getUuid(), portletDataContext.getScopeGroupId());

				if (existingFolder == null) {
					String name = getFolderName(
						null, portletDataContext.getScopeGroupId(),
						parentFolderId, folder.getName(), 2);

					serviceContext.setUuid(folder.getUuid());

					importedFolder = _dlAppLocalService.addFolder(
						userId, repositoryId, parentFolderId, name,
						folder.getDescription(), serviceContext);
				}
				else {
					String name = getFolderName(
						folder.getUuid(), portletDataContext.getScopeGroupId(),
						parentFolderId, folder.getName(), 2);

					importedFolder = _dlAppLocalService.updateFolder(
						existingFolder.getFolderId(), parentFolderId, name,
						folder.getDescription(), serviceContext);
				}
			}
		}
		else {
			String name = getFolderName(
				null, portletDataContext.getScopeGroupId(), parentFolderId,
				folder.getName(), 2);

			importedFolder = _dlAppLocalService.addFolder(
				userId, repositoryId, parentFolderId, name,
				folder.getDescription(), serviceContext);
		}

		importFolderFileEntryTypes(
			portletDataContext, folderElement, folder, importedFolder,
			serviceContext);

		portletDataContext.importClassedModel(
			folder, importedFolder, DLFolder.class);

		folderIds.put(folder.getFolderId(), importedFolder.getFolderId());
		folderIdsAndRepositoryEntryIds.put(
			folder.getFolderId(), importedFolder.getFolderId());
	}

	@Override
	protected void doRestoreStagedModel(
			PortletDataContext portletDataContext, Folder folder)
		throws Exception {

		long userId = portletDataContext.getUserId(folder.getUserUuid());

		Folder existingFolder = fetchStagedModelByUuidAndGroupId(
			folder.getUuid(), portletDataContext.getScopeGroupId());

		if ((existingFolder == null) ||
			!(existingFolder.getModel() instanceof DLFolder)) {

			return;
		}

		DLFolder dlFolder = (DLFolder)existingFolder.getModel();

		if (!dlFolder.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = TrashHandlerRegistryUtil.getTrashHandler(
			DLFolder.class.getName());

		if (trashHandler.isRestorable(existingFolder.getFolderId())) {
			trashHandler.restoreTrashEntry(
				userId, existingFolder.getFolderId());
		}
	}

	protected void exportFolderFileEntryTypes(
			PortletDataContext portletDataContext, Folder folder)
		throws Exception {

		if (!folder.isDefaultRepository()) {
			return;
		}

		LARFile larFile = LARFileFactoryUtil.getLARFile(portletDataContext);

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

		boolean basicDocument = false;

		for (DLFileEntryType dlFileEntryType : dlFileEntryTypes) {
			if (dlFileEntryType.getFileEntryTypeId() ==
					DLFileEntryTypeConstants.
						FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT) {

				basicDocument = true;

				continue;
			}

			if (defaultFileEntryTypeId ==
					dlFileEntryType.getFileEntryTypeId()) {

				defaultFileEntryTypeUuid = dlFileEntryType.getUuid();
			}

			if (dlFileEntryType.isExportable()) {
				StagedModelDataHandlerUtil.exportReferenceStagedModelStream(
					portletDataContext, folder, dlFileEntryType,
					PortletDataContext.REFERENCE_TYPE_STRONG);
			}
		}

		if (basicDocument) {
			larFile.writeStagedModelAttribute("basic-document", "true");
		}

		larFile.writeStagedModelAttribute(
			"defaultFileEntryTypeUuid", defaultFileEntryTypeUuid);
	}

	protected String getFolderName(
			String uuid, long groupId, long parentFolderId, String name,
			int count)
		throws Exception {

		DLFolder dlFolder = _dlFolderLocalService.fetchFolder(
			groupId, parentFolderId, name);

		if (dlFolder == null) {
			FileEntry fileEntry = FileEntryUtil.fetchByR_F_T(
				groupId, parentFolderId, name);

			if (fileEntry == null) {
				return name;
			}
		}
		else if (Validator.isNotNull(uuid) && uuid.equals(dlFolder.getUuid())) {
			return name;
		}

		name = StringUtil.appendParentheticalSuffix(name, count);

		return getFolderName(uuid, groupId, parentFolderId, name, ++count);
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
			long referenceDLFileEntryTypeId = GetterUtil.getLong(
				referenceElement.attributeValue("class-pk"));
			String referenceDLFileEntryTypeUuid =
				referenceElement.attributeValue("uuid");

			Map<Long, Long> dlFileEntryTypeIds =
				(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
					DLFileEntryType.class);

			long dlFileEntryTypeId = MapUtil.getLong(
				dlFileEntryTypeIds, referenceDLFileEntryTypeId,
				referenceDLFileEntryTypeId);

			DLFileEntryType existingDLFileEntryType =
				_dlFileEntryTypeLocalService.fetchDLFileEntryType(
					dlFileEntryTypeId);

			if (existingDLFileEntryType == null) {
				continue;
			}

			currentFolderFileEntryTypeIds.add(
				existingDLFileEntryType.getFileEntryTypeId());

			if (defaultFileEntryTypeUuid.equals(referenceDLFileEntryTypeUuid)) {
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

	@Override
	protected void validateExport(
			PortletDataContext portletDataContext, Folder folder)
		throws PortletDataException {

		if ((folder.getGroupId() != portletDataContext.getGroupId()) &&
			(folder.getGroupId() != portletDataContext.getScopeGroupId())) {

			PortletDataException pde = new PortletDataException(
				PortletDataException.INVALID_GROUP);

			pde.setStagedModelDisplayName(folder.getName());
			pde.setStagedModelClassName(folder.getModelClassName());
			pde.setStagedModelClassPK(
				GetterUtil.getString(folder.getFolderId()));

			throw pde;
		}

		if (folder instanceof LiferayFolder) {
			LiferayFolder liferayFolder = (LiferayFolder)folder;

			DLFolder dlFolder = (DLFolder)liferayFolder.getModel();

			if (dlFolder.isInTrash() || dlFolder.isInTrashContainer()) {
				PortletDataException pde = new PortletDataException(
					PortletDataException.STATUS_IN_TRASH);

				pde.setStagedModelDisplayName(folder.getName());
				pde.setStagedModelClassName(folder.getModelClassName());
				pde.setStagedModelClassPK(
					GetterUtil.getString(folder.getFolderId()));

				throw pde;
			}
		}
	}

	private DLAppLocalService _dlAppLocalService;
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private Portal _portal;

	private RepositoryLocalService _repositoryLocalService;

}