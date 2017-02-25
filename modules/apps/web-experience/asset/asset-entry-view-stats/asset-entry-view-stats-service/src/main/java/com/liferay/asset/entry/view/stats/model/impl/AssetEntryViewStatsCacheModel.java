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

package com.liferay.asset.entry.view.stats.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.entry.view.stats.model.AssetEntryViewStats;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AssetEntryViewStats in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryViewStats
 * @generated
 */
@ProviderType
public class AssetEntryViewStatsCacheModel implements CacheModel<AssetEntryViewStats>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryViewStatsCacheModel)) {
			return false;
		}

		AssetEntryViewStatsCacheModel assetEntryViewStatsCacheModel = (AssetEntryViewStatsCacheModel)obj;

		if (statsId == assetEntryViewStatsCacheModel.statsId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, statsId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{statsId=");
		sb.append(statsId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", assetEntryId=");
		sb.append(assetEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public AssetEntryViewStats toEntityModel() {
		AssetEntryViewStatsImpl assetEntryViewStatsImpl = new AssetEntryViewStatsImpl();

		assetEntryViewStatsImpl.setStatsId(statsId);
		assetEntryViewStatsImpl.setGroupId(groupId);
		assetEntryViewStatsImpl.setCompanyId(companyId);
		assetEntryViewStatsImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			assetEntryViewStatsImpl.setCreateDate(null);
		}
		else {
			assetEntryViewStatsImpl.setCreateDate(new Date(createDate));
		}

		assetEntryViewStatsImpl.setClassNameId(classNameId);
		assetEntryViewStatsImpl.setClassPK(classPK);
		assetEntryViewStatsImpl.setAssetEntryId(assetEntryId);

		assetEntryViewStatsImpl.resetOriginalValues();

		return assetEntryViewStatsImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		statsId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		assetEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(statsId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(assetEntryId);
	}

	public long statsId;
	public long groupId;
	public long companyId;
	public long userId;
	public long createDate;
	public long classNameId;
	public long classPK;
	public long assetEntryId;
}