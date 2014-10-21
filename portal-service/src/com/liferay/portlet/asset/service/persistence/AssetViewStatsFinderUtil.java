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

package com.liferay.portlet.asset.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
@ProviderType
public class AssetViewStatsFinderUtil {
	public static int countByC_C_SD(long classNameId, long classPK,
		java.util.Date viewDateGT, java.util.Date viewDateLT)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByC_C_SD(classNameId, classPK, viewDateGT, viewDateLT);
	}

	public static AssetViewStatsFinder getFinder() {
		if (_finder == null) {
			_finder = (AssetViewStatsFinder)PortalBeanLocatorUtil.locate(AssetViewStatsFinder.class.getName());

			ReferenceRegistry.registerReference(AssetViewStatsFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(AssetViewStatsFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(AssetViewStatsFinderUtil.class,
			"_finder");
	}

	private static AssetViewStatsFinder _finder;
}