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

package com.liferay.portlet.asset.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AssetViewStats}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetViewStats
 * @generated
 */
@ProviderType
public class AssetViewStatsWrapper implements AssetViewStats,
	ModelWrapper<AssetViewStats> {
	public AssetViewStatsWrapper(AssetViewStats assetViewStats) {
		_assetViewStats = assetViewStats;
	}

	@Override
	public Class<?> getModelClass() {
		return AssetViewStats.class;
	}

	@Override
	public String getModelClassName() {
		return AssetViewStats.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("viewStatsId", getViewStatsId());
		attributes.put("userId", getUserId());
		attributes.put("viewDate", getViewDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long viewStatsId = (Long)attributes.get("viewStatsId");

		if (viewStatsId != null) {
			setViewStatsId(viewStatsId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Date viewDate = (Date)attributes.get("viewDate");

		if (viewDate != null) {
			setViewDate(viewDate);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	/**
	* Returns the primary key of this asset view stats.
	*
	* @return the primary key of this asset view stats
	*/
	@Override
	public long getPrimaryKey() {
		return _assetViewStats.getPrimaryKey();
	}

	/**
	* Sets the primary key of this asset view stats.
	*
	* @param primaryKey the primary key of this asset view stats
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_assetViewStats.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the view stats ID of this asset view stats.
	*
	* @return the view stats ID of this asset view stats
	*/
	@Override
	public long getViewStatsId() {
		return _assetViewStats.getViewStatsId();
	}

	/**
	* Sets the view stats ID of this asset view stats.
	*
	* @param viewStatsId the view stats ID of this asset view stats
	*/
	@Override
	public void setViewStatsId(long viewStatsId) {
		_assetViewStats.setViewStatsId(viewStatsId);
	}

	/**
	* Returns the user ID of this asset view stats.
	*
	* @return the user ID of this asset view stats
	*/
	@Override
	public long getUserId() {
		return _assetViewStats.getUserId();
	}

	/**
	* Sets the user ID of this asset view stats.
	*
	* @param userId the user ID of this asset view stats
	*/
	@Override
	public void setUserId(long userId) {
		_assetViewStats.setUserId(userId);
	}

	/**
	* Returns the user uuid of this asset view stats.
	*
	* @return the user uuid of this asset view stats
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStats.getUserUuid();
	}

	/**
	* Sets the user uuid of this asset view stats.
	*
	* @param userUuid the user uuid of this asset view stats
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_assetViewStats.setUserUuid(userUuid);
	}

	/**
	* Returns the view date of this asset view stats.
	*
	* @return the view date of this asset view stats
	*/
	@Override
	public java.util.Date getViewDate() {
		return _assetViewStats.getViewDate();
	}

	/**
	* Sets the view date of this asset view stats.
	*
	* @param viewDate the view date of this asset view stats
	*/
	@Override
	public void setViewDate(java.util.Date viewDate) {
		_assetViewStats.setViewDate(viewDate);
	}

	/**
	* Returns the fully qualified class name of this asset view stats.
	*
	* @return the fully qualified class name of this asset view stats
	*/
	@Override
	public java.lang.String getClassName() {
		return _assetViewStats.getClassName();
	}

	@Override
	public void setClassName(java.lang.String className) {
		_assetViewStats.setClassName(className);
	}

	/**
	* Returns the class name ID of this asset view stats.
	*
	* @return the class name ID of this asset view stats
	*/
	@Override
	public long getClassNameId() {
		return _assetViewStats.getClassNameId();
	}

	/**
	* Sets the class name ID of this asset view stats.
	*
	* @param classNameId the class name ID of this asset view stats
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_assetViewStats.setClassNameId(classNameId);
	}

	/**
	* Returns the class p k of this asset view stats.
	*
	* @return the class p k of this asset view stats
	*/
	@Override
	public long getClassPK() {
		return _assetViewStats.getClassPK();
	}

	/**
	* Sets the class p k of this asset view stats.
	*
	* @param classPK the class p k of this asset view stats
	*/
	@Override
	public void setClassPK(long classPK) {
		_assetViewStats.setClassPK(classPK);
	}

	@Override
	public boolean isNew() {
		return _assetViewStats.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_assetViewStats.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _assetViewStats.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_assetViewStats.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _assetViewStats.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _assetViewStats.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_assetViewStats.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetViewStats.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_assetViewStats.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_assetViewStats.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetViewStats.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new AssetViewStatsWrapper((AssetViewStats)_assetViewStats.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portlet.asset.model.AssetViewStats assetViewStats) {
		return _assetViewStats.compareTo(assetViewStats);
	}

	@Override
	public int hashCode() {
		return _assetViewStats.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.liferay.portlet.asset.model.AssetViewStats> toCacheModel() {
		return _assetViewStats.toCacheModel();
	}

	@Override
	public com.liferay.portlet.asset.model.AssetViewStats toEscapedModel() {
		return new AssetViewStatsWrapper(_assetViewStats.toEscapedModel());
	}

	@Override
	public com.liferay.portlet.asset.model.AssetViewStats toUnescapedModel() {
		return new AssetViewStatsWrapper(_assetViewStats.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _assetViewStats.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _assetViewStats.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetViewStats.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof AssetViewStatsWrapper)) {
			return false;
		}

		AssetViewStatsWrapper assetViewStatsWrapper = (AssetViewStatsWrapper)obj;

		if (Validator.equals(_assetViewStats,
					assetViewStatsWrapper._assetViewStats)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public AssetViewStats getWrappedAssetViewStats() {
		return _assetViewStats;
	}

	@Override
	public AssetViewStats getWrappedModel() {
		return _assetViewStats;
	}

	@Override
	public void resetOriginalValues() {
		_assetViewStats.resetOriginalValues();
	}

	private AssetViewStats _assetViewStats;
}