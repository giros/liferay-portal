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

package com.liferay.asset.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class AssetEntryViewStatsFinderUtil {
	public static AssetEntryViewStatsFinder getFinder() {
		if (_finder == null) {
			_finder = (AssetEntryViewStatsFinder)PortalBeanLocatorUtil.locate(AssetEntryViewStatsFinder.class.getName());

			ReferenceRegistry.registerReference(AssetEntryViewStatsFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(AssetEntryViewStatsFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(AssetEntryViewStatsFinderUtil.class,
			"_finder");
	}

	private static AssetEntryViewStatsFinder _finder;
}