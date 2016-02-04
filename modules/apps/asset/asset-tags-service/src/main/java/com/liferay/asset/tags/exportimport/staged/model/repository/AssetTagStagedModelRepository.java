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

package com.liferay.asset.tags.exportimport.staged.model.repository;

import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetTagLocalService;
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
	property = {"model.class.name=com.liferay.portlet.asset.model.AssetTag;"},
	service = StagedModelRepository.class
)
public class AssetTagStagedModelRepository
	extends BaseStagedModelRepository<AssetTag> {

	@Override
	public AssetTag addStagedModel(
			PortletDataContext portletDataContext, AssetTag assetTag)
		throws PortalException {

		long userId = portletDataContext.getUserId(assetTag.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetTag);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(assetTag.getUuid());
		}

		return _assetTagLocalService.addTag(
			userId, portletDataContext.getScopeGroupId(), assetTag.getName(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(AssetTag assetTag) throws PortalException {
		_assetTagLocalService.deleteTag(assetTag);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		AssetTag assetTag = fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (assetTag != null) {
			deleteStagedModel(assetTag);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_assetTagLocalService.deleteGroupTags(
			portletDataContext.getScopeGroupId());
	}

	@Override
	public AssetTag fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _assetTagLocalService.fetchAssetTagByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<AssetTag> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _assetTagLocalService.getAssetTagsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<AssetTag>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _assetTagLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public AssetTag saveStagedModel(AssetTag assetTag) throws PortalException {
		return _assetTagLocalService.updateAssetTag(assetTag);
	}

	@Override
	public AssetTag updateStagedModel(
			PortletDataContext portletDataContext, AssetTag assetTag)
		throws PortalException {

		long userId = portletDataContext.getUserId(assetTag.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			assetTag);

		return _assetTagLocalService.updateTag(
			userId, assetTag.getTagId(), assetTag.getName(), serviceContext);
	}

	@Reference(unbind = "-")
	protected void setAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {

		_assetTagLocalService = assetTagLocalService;
	}

	private AssetTagLocalService _assetTagLocalService;

}