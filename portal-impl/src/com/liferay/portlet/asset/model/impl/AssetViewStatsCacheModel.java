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

package com.liferay.portlet.asset.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.asset.model.AssetViewStats;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetViewStats in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetViewStats
 * @generated
 */
public class AssetViewStatsCacheModel implements CacheModel<AssetViewStats>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{viewStatsId=");
		sb.append(viewStatsId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", viewDate=");
		sb.append(viewDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetViewStats toEntityModel() {
		AssetViewStatsImpl assetViewStatsImpl = new AssetViewStatsImpl();

		assetViewStatsImpl.setViewStatsId(viewStatsId);
		assetViewStatsImpl.setUserId(userId);

		if (viewDate == Long.MIN_VALUE) {
			assetViewStatsImpl.setViewDate(null);
		}
		else {
			assetViewStatsImpl.setViewDate(new Date(viewDate));
		}

		assetViewStatsImpl.setClassNameId(classNameId);
		assetViewStatsImpl.setClassPK(classPK);

		assetViewStatsImpl.resetOriginalValues();

		return assetViewStatsImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		viewStatsId = objectInput.readLong();
		userId = objectInput.readLong();
		viewDate = objectInput.readLong();
		classNameId = objectInput.readLong();
		classPK = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(viewStatsId);
		objectOutput.writeLong(userId);
		objectOutput.writeLong(viewDate);
		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(classPK);
	}

	public long viewStatsId;
	public long userId;
	public long viewDate;
	public long classNameId;
	public long classPK;
}