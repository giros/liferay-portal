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

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.xml.Element;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class AssetTagStagedModelDataHandler
	extends BaseStagedModelDataHandler<AssetTag> {

	public static final String[] CLASS_NAMES = {AssetTag.class.getName()};

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(AssetTag assetTag) {
		return assetTag.getName();
	}

	protected ServiceContext createServiceContext(
		PortletDataContext portletDataContext, AssetTag assetTag) {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCreateDate(assetTag.getCreateDate());
		serviceContext.setModifiedDate(assetTag.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		return serviceContext;
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, AssetTag assetTag)
		throws Exception {

		Element assetTagElement = portletDataContext.getExportDataElement(
			assetTag);

		portletDataContext.addClassedModel(
			assetTagElement, ExportImportPathUtil.getModelPath(assetTag),
			assetTag);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, AssetTag assetTag)
		throws Exception {

		AssetTag importedAssetTag = (AssetTag)assetTag.clone();

		AssetTag existingAssetTag =
			_stagedModelRepository.fetchStagedModelByUuidAndGroupId(
				assetTag.getUuid(), portletDataContext.getScopeGroupId());

		if (existingAssetTag == null) {
			importedAssetTag = _stagedModelRepository.addStagedModel(
				portletDataContext, importedAssetTag);
		}
		else {
			importedAssetTag.setTagId(assetTag.getTagId());

			importedAssetTag = _stagedModelRepository.updateStagedModel(
				portletDataContext, importedAssetTag);
		}

		portletDataContext.importClassedModel(assetTag, importedAssetTag);
	}

	@Reference(target ="com.liferay.portlet.asset.model.AssetTag", unbind = "-")
	protected void setStagedModelRepository(
		StagedModelRepository<AssetTag> stagedModelRepository) {

		_stagedModelRepository = stagedModelRepository;
	}

	private StagedModelRepository<AssetTag> _stagedModelRepository;

}