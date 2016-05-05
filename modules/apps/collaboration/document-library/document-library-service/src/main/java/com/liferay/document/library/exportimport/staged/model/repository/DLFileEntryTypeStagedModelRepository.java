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

package com.liferay.document.library.exportimport.staged.model.repository;

import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = {
		"model.class.name=com.liferay.document.library.kernel.model.DLFileEntryType"
	},
	service = StagedModelRepository.class
)
public class DLFileEntryTypeStagedModelRepository
	extends BaseStagedModelRepository<DLFileEntryType> {

	public DLFileEntryType addStagedModel(
			PortletDataContext portletDataContext,
			DLFileEntryType fileEntryType)
		throws PortalException {

		long userId = portletDataContext.getUserId(fileEntryType.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileEntryType);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(fileEntryType.getUuid());
		}

		return _dlFileEntryTypeLocalService.addFileEntryType(
			userId, portletDataContext.getScopeGroupId(),
			fileEntryType.getFileEntryTypeKey(), fileEntryType.getNameMap(),
			fileEntryType.getDescriptionMap(),
			fileEntryType.getDDMStructureIdsArray(), serviceContext);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		DLFileEntryType dlFileEntryType = fetchStagedModelByUuidAndGroupId(
			uuid, groupId);

		if (dlFileEntryType != null) {
			deleteStagedModel(dlFileEntryType);
		}
	}

	@Override
	public void deleteStagedModel(DLFileEntryType fileEntryType)
		throws PortalException {

		_dlFileEntryTypeLocalService.deleteFileEntryType(fileEntryType);
	}

	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_dlFileEntryTypeLocalService.deleteFileEntryTypes(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public DLFileEntryType fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _dlFileEntryTypeLocalService.
			fetchDLFileEntryTypeByUuidAndGroupId(uuid, groupId);
	}

	@Override
	public List<DLFileEntryType> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _dlFileEntryTypeLocalService.
			getDLFileEntryTypesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<DLFileEntryType>());
	}

	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _dlFileEntryTypeLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	public DLFileEntryType saveStagedModel(DLFileEntryType fileEntryType)
		throws PortalException {

		return _dlFileEntryTypeLocalService.updateDLFileEntryType(
			fileEntryType);
	}

	public DLFileEntryType updateStagedModel(
			PortletDataContext portletDataContext,
			DLFileEntryType fileEntryType)
		throws PortalException {

		long userId = portletDataContext.getUserId(fileEntryType.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			fileEntryType);

		_dlFileEntryTypeLocalService.updateFileEntryType(
			userId, fileEntryType.getFileEntryTypeId(),
			fileEntryType.getNameMap(), fileEntryType.getDescriptionMap(),
			fileEntryType.getDDMStructureIdsArray(), serviceContext);

		return _dlFileEntryTypeLocalService.fetchDLFileEntryType(
			fileEntryType.getFileEntryTypeId());;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

}
