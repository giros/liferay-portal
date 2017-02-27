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

package com.liferay.portlet.asset.service.persistence.impl;

import com.liferay.asset.kernel.model.AssetEntryViewStats;
import com.liferay.asset.kernel.service.persistence.AssetEntryViewStatsPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetEntryViewStatsFinderBaseImpl extends BasePersistenceImpl<AssetEntryViewStats> {
	/**
	 * Returns the asset entry view stats persistence.
	 *
	 * @return the asset entry view stats persistence
	 */
	public AssetEntryViewStatsPersistence getAssetEntryViewStatsPersistence() {
		return assetEntryViewStatsPersistence;
	}

	/**
	 * Sets the asset entry view stats persistence.
	 *
	 * @param assetEntryViewStatsPersistence the asset entry view stats persistence
	 */
	public void setAssetEntryViewStatsPersistence(
		AssetEntryViewStatsPersistence assetEntryViewStatsPersistence) {
		this.assetEntryViewStatsPersistence = assetEntryViewStatsPersistence;
	}

	@BeanReference(type = AssetEntryViewStatsPersistence.class)
	protected AssetEntryViewStatsPersistence assetEntryViewStatsPersistence;
}