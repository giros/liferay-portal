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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.asset.model.AssetViewStats;
import com.liferay.portlet.asset.service.base.AssetViewStatsLocalServiceBaseImpl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The implementation of the asset view stats local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.asset.service.AssetViewStatsLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.asset.service.base.AssetViewStatsLocalServiceBaseImpl
 * @see com.liferay.portlet.asset.service.AssetViewStatsLocalServiceUtil
 */
public class AssetViewStatsLocalServiceImpl
	extends AssetViewStatsLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.liferay.portlet.asset.service.AssetViewStatsLocalServiceUtil} to access the asset view stats local service.
	 */

	public AssetViewStats addAssetViewStats(
			long userId, long classNameId, long classPK, Date viewDate) 
		throws SystemException {

		AssetViewStats assetViewStats = assetViewStatsPersistence.create(
			counterLocalService.increment());

		assetViewStats.setUserId(userId);
		assetViewStats.setClassNameId(classNameId);
		assetViewStats.setClassPK(classPK);
		assetViewStats.setViewDate(viewDate);

		assetViewStatsPersistence.update(assetViewStats);

		return assetViewStats;
	}

	public Map<Date, Long> countViewsByDay(long classNameId, long classPK) {
		return new HashMap<Date, Long>();
	}

	public Map<Date, Long> countViewsByWeek(long classNameId, long classPK) {
		return new HashMap<Date, Long>();
	}

	public Map<Date, Long> countViewsByMonth(long classNameId, long classPK) {
		return new HashMap<Date, Long>();
	}
}