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

package com.liferay.asset.kernel.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link AssetEntryViewStats}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryViewStats
 * @generated
 */
@ProviderType
public class AssetEntryViewStatsWrapper implements AssetEntryViewStats,
	ModelWrapper<AssetEntryViewStats> {
	public AssetEntryViewStatsWrapper(AssetEntryViewStats assetEntryViewStats) {
		_assetEntryViewStats = assetEntryViewStats;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetEntryViewStats.class;
	}

	@Override
	public String getModelClassName() {
		return AssetEntryViewStats.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("statsId", getStatsId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("createDate", getCreateDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("assetEntryId", getAssetEntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long statsId = (Long)attributes.get("statsId");

		if (statsId != null) {
			setStatsId(statsId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long assetEntryId = (Long)attributes.get("assetEntryId");

		if (assetEntryId != null) {
			setAssetEntryId(assetEntryId);
		}
	}

	@Override
	public AssetEntryViewStats toEscapedModel() {
		return new AssetEntryViewStatsWrapper(_assetEntryViewStats.toEscapedModel());
	}

	@Override
	public AssetEntryViewStats toUnescapedModel() {
		return new AssetEntryViewStatsWrapper(_assetEntryViewStats.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _assetEntryViewStats.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _assetEntryViewStats.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _assetEntryViewStats.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _assetEntryViewStats.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<AssetEntryViewStats> toCacheModel() {
		return _assetEntryViewStats.toCacheModel();
	}

	@Override
	public int compareTo(AssetEntryViewStats assetEntryViewStats) {
		return _assetEntryViewStats.compareTo(assetEntryViewStats);
	}

	@Override
	public int hashCode() {
		return _assetEntryViewStats.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _assetEntryViewStats.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new AssetEntryViewStatsWrapper((AssetEntryViewStats)_assetEntryViewStats.clone());
	}

	/**
	* Returns the fully qualified class name of this asset entry view stats.
	*
	* @return the fully qualified class name of this asset entry view stats
	*/
	@Override
	public java.lang.String getClassName() {
		return _assetEntryViewStats.getClassName();
	}

	/**
	* Returns the user uuid of this asset entry view stats.
	*
	* @return the user uuid of this asset entry view stats
	*/
	@Override
	public java.lang.String getUserUuid() {
		return _assetEntryViewStats.getUserUuid();
	}

	@Override
	public java.lang.String toString() {
		return _assetEntryViewStats.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _assetEntryViewStats.toXmlString();
	}

	/**
	* Returns the create date of this asset entry view stats.
	*
	* @return the create date of this asset entry view stats
	*/
	@Override
	public Date getCreateDate() {
		return _assetEntryViewStats.getCreateDate();
	}

	/**
	* Returns the asset entry ID of this asset entry view stats.
	*
	* @return the asset entry ID of this asset entry view stats
	*/
	@Override
	public long getAssetEntryId() {
		return _assetEntryViewStats.getAssetEntryId();
	}

	/**
	* Returns the class name ID of this asset entry view stats.
	*
	* @return the class name ID of this asset entry view stats
	*/
	@Override
	public long getClassNameId() {
		return _assetEntryViewStats.getClassNameId();
	}

	/**
	* Returns the class pk of this asset entry view stats.
	*
	* @return the class pk of this asset entry view stats
	*/
	@Override
	public long getClassPK() {
		return _assetEntryViewStats.getClassPK();
	}

	/**
	* Returns the company ID of this asset entry view stats.
	*
	* @return the company ID of this asset entry view stats
	*/
	@Override
	public long getCompanyId() {
		return _assetEntryViewStats.getCompanyId();
	}

	/**
	* Returns the group ID of this asset entry view stats.
	*
	* @return the group ID of this asset entry view stats
	*/
	@Override
	public long getGroupId() {
		return _assetEntryViewStats.getGroupId();
	}

	/**
	* Returns the primary key of this asset entry view stats.
	*
	* @return the primary key of this asset entry view stats
	*/
	@Override
	public long getPrimaryKey() {
		return _assetEntryViewStats.getPrimaryKey();
	}

	/**
	* Returns the stats ID of this asset entry view stats.
	*
	* @return the stats ID of this asset entry view stats
	*/
	@Override
	public long getStatsId() {
		return _assetEntryViewStats.getStatsId();
	}

	/**
	* Returns the user ID of this asset entry view stats.
	*
	* @return the user ID of this asset entry view stats
	*/
	@Override
	public long getUserId() {
		return _assetEntryViewStats.getUserId();
	}

	@Override
	public void persist() {
		_assetEntryViewStats.persist();
	}

	/**
	* Sets the asset entry ID of this asset entry view stats.
	*
	* @param assetEntryId the asset entry ID of this asset entry view stats
	*/
	@Override
	public void setAssetEntryId(long assetEntryId) {
		_assetEntryViewStats.setAssetEntryId(assetEntryId);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetEntryViewStats.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(java.lang.String className) {
		_assetEntryViewStats.setClassName(className);
	}

	/**
	* Sets the class name ID of this asset entry view stats.
	*
	* @param classNameId the class name ID of this asset entry view stats
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_assetEntryViewStats.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this asset entry view stats.
	*
	* @param classPK the class pk of this asset entry view stats
	*/
	@Override
	public void setClassPK(long classPK) {
		_assetEntryViewStats.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this asset entry view stats.
	*
	* @param companyId the company ID of this asset entry view stats
	*/
	@Override
	public void setCompanyId(long companyId) {
		_assetEntryViewStats.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this asset entry view stats.
	*
	* @param createDate the create date of this asset entry view stats
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_assetEntryViewStats.setCreateDate(createDate);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_assetEntryViewStats.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_assetEntryViewStats.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_assetEntryViewStats.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the group ID of this asset entry view stats.
	*
	* @param groupId the group ID of this asset entry view stats
	*/
	@Override
	public void setGroupId(long groupId) {
		_assetEntryViewStats.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_assetEntryViewStats.setNew(n);
	}

	/**
	* Sets the primary key of this asset entry view stats.
	*
	* @param primaryKey the primary key of this asset entry view stats
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetEntryViewStats.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_assetEntryViewStats.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the stats ID of this asset entry view stats.
	*
	* @param statsId the stats ID of this asset entry view stats
	*/
	@Override
	public void setStatsId(long statsId) {
		_assetEntryViewStats.setStatsId(statsId);
	}

	/**
	* Sets the user ID of this asset entry view stats.
	*
	* @param userId the user ID of this asset entry view stats
	*/
	@Override
	public void setUserId(long userId) {
		_assetEntryViewStats.setUserId(userId);
	}

	/**
	* Sets the user uuid of this asset entry view stats.
	*
	* @param userUuid the user uuid of this asset entry view stats
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_assetEntryViewStats.setUserUuid(userUuid);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetEntryViewStatsWrapper)) {
			return false;
		}

		AssetEntryViewStatsWrapper assetEntryViewStatsWrapper = (AssetEntryViewStatsWrapper)obj;

		if (Objects.equals(_assetEntryViewStats,
					assetEntryViewStatsWrapper._assetEntryViewStats)) {
			return true;
		}

		return false;
	}

	@Override
	public AssetEntryViewStats getWrappedModel() {
		return _assetEntryViewStats;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _assetEntryViewStats.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _assetEntryViewStats.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_assetEntryViewStats.resetOriginalValues();
	}

	private final AssetEntryViewStats _assetEntryViewStats;
}