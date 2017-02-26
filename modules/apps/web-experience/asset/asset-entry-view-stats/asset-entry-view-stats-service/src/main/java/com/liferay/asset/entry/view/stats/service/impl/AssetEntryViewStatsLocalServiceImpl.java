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

package com.liferay.asset.entry.view.stats.service.impl;

import com.liferay.asset.entry.view.stats.model.AssetEntryViewStats;
import com.liferay.asset.entry.view.stats.service.base.AssetEntryViewStatsLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.PortalUtil;

import aQute.bnd.annotation.ProviderType;

import java.util.Date;

/**
 * The implementation of the asset entry view stats local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.asset.entry.view.stats.service.AssetEntryViewStatsLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryViewStatsLocalServiceBaseImpl
 * @see com.liferay.asset.entry.view.stats.service.AssetEntryViewStatsLocalServiceUtil
 */
@ProviderType
public class AssetEntryViewStatsLocalServiceImpl
	extends AssetEntryViewStatsLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.asset.entry.view.stats.service.AssetEntryViewStatsLocalServiceUtil} to access the asset entry view stats local service.
	 */

	public AssetEntryViewStats addAssetEntryViewStats(
			long userId, long groupId, String className, long classPK,
			long assetEntryId)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long statsId = counterLocalService.increment();

		AssetEntryViewStats stats = assetEntryViewStatsPersistence.create(
			statsId);

		stats.setUserId(userId);
		stats.setGroupId(groupId);
		stats.setCompanyId(user.getCompanyId());
		stats.setCreateDate(new Date());
		stats.setClassNameId(PortalUtil.getClassNameId(className));
		stats.setClassPK(classPK);
		stats.setAssetEntryId(assetEntryId);

		assetEntryViewStatsPersistence.update(stats);

		return stats;
	}

}