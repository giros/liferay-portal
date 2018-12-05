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

package com.liferay.change.tracking.engine.model;

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
 * This class is a wrapper for {@link CTEEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEEntry
 * @generated
 */
@ProviderType
public class CTEEntryWrapper implements CTEEntry, ModelWrapper<CTEEntry> {
	public CTEEntryWrapper(CTEEntry cteEntry) {
		_cteEntry = cteEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return CTEEntry.class;
	}

	@Override
	public String getModelClassName() {
		return CTEEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("entryId", getEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("resourcePrimKey", getResourcePrimKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long resourcePrimKey = (Long)attributes.get("resourcePrimKey");

		if (resourcePrimKey != null) {
			setResourcePrimKey(resourcePrimKey);
		}
	}

	@Override
	public Object clone() {
		return new CTEEntryWrapper((CTEEntry)_cteEntry.clone());
	}

	@Override
	public int compareTo(CTEEntry cteEntry) {
		return _cteEntry.compareTo(cteEntry);
	}

	/**
	* Returns the fully qualified class name of this cte entry.
	*
	* @return the fully qualified class name of this cte entry
	*/
	@Override
	public String getClassName() {
		return _cteEntry.getClassName();
	}

	/**
	* Returns the class name ID of this cte entry.
	*
	* @return the class name ID of this cte entry
	*/
	@Override
	public long getClassNameId() {
		return _cteEntry.getClassNameId();
	}

	/**
	* Returns the class pk of this cte entry.
	*
	* @return the class pk of this cte entry
	*/
	@Override
	public long getClassPK() {
		return _cteEntry.getClassPK();
	}

	/**
	* Returns the company ID of this cte entry.
	*
	* @return the company ID of this cte entry
	*/
	@Override
	public long getCompanyId() {
		return _cteEntry.getCompanyId();
	}

	/**
	* Returns the create date of this cte entry.
	*
	* @return the create date of this cte entry
	*/
	@Override
	public Date getCreateDate() {
		return _cteEntry.getCreateDate();
	}

	/**
	* Returns the entry ID of this cte entry.
	*
	* @return the entry ID of this cte entry
	*/
	@Override
	public long getEntryId() {
		return _cteEntry.getEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cteEntry.getExpandoBridge();
	}

	/**
	* Returns the modified date of this cte entry.
	*
	* @return the modified date of this cte entry
	*/
	@Override
	public Date getModifiedDate() {
		return _cteEntry.getModifiedDate();
	}

	/**
	* Returns the primary key of this cte entry.
	*
	* @return the primary key of this cte entry
	*/
	@Override
	public long getPrimaryKey() {
		return _cteEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cteEntry.getPrimaryKeyObj();
	}

	/**
	* Returns the resource prim key of this cte entry.
	*
	* @return the resource prim key of this cte entry
	*/
	@Override
	public long getResourcePrimKey() {
		return _cteEntry.getResourcePrimKey();
	}

	/**
	* Returns the user ID of this cte entry.
	*
	* @return the user ID of this cte entry
	*/
	@Override
	public long getUserId() {
		return _cteEntry.getUserId();
	}

	/**
	* Returns the user name of this cte entry.
	*
	* @return the user name of this cte entry
	*/
	@Override
	public String getUserName() {
		return _cteEntry.getUserName();
	}

	/**
	* Returns the user uuid of this cte entry.
	*
	* @return the user uuid of this cte entry
	*/
	@Override
	public String getUserUuid() {
		return _cteEntry.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _cteEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _cteEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _cteEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _cteEntry.isNew();
	}

	@Override
	public boolean isResourceMain() {
		return _cteEntry.isResourceMain();
	}

	@Override
	public void persist() {
		_cteEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cteEntry.setCachedModel(cachedModel);
	}

	@Override
	public void setClassName(String className) {
		_cteEntry.setClassName(className);
	}

	/**
	* Sets the class name ID of this cte entry.
	*
	* @param classNameId the class name ID of this cte entry
	*/
	@Override
	public void setClassNameId(long classNameId) {
		_cteEntry.setClassNameId(classNameId);
	}

	/**
	* Sets the class pk of this cte entry.
	*
	* @param classPK the class pk of this cte entry
	*/
	@Override
	public void setClassPK(long classPK) {
		_cteEntry.setClassPK(classPK);
	}

	/**
	* Sets the company ID of this cte entry.
	*
	* @param companyId the company ID of this cte entry
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cteEntry.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this cte entry.
	*
	* @param createDate the create date of this cte entry
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cteEntry.setCreateDate(createDate);
	}

	/**
	* Sets the entry ID of this cte entry.
	*
	* @param entryId the entry ID of this cte entry
	*/
	@Override
	public void setEntryId(long entryId) {
		_cteEntry.setEntryId(entryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cteEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cteEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cteEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this cte entry.
	*
	* @param modifiedDate the modified date of this cte entry
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cteEntry.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_cteEntry.setNew(n);
	}

	/**
	* Sets the primary key of this cte entry.
	*
	* @param primaryKey the primary key of this cte entry
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cteEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cteEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the resource prim key of this cte entry.
	*
	* @param resourcePrimKey the resource prim key of this cte entry
	*/
	@Override
	public void setResourcePrimKey(long resourcePrimKey) {
		_cteEntry.setResourcePrimKey(resourcePrimKey);
	}

	/**
	* Sets the user ID of this cte entry.
	*
	* @param userId the user ID of this cte entry
	*/
	@Override
	public void setUserId(long userId) {
		_cteEntry.setUserId(userId);
	}

	/**
	* Sets the user name of this cte entry.
	*
	* @param userName the user name of this cte entry
	*/
	@Override
	public void setUserName(String userName) {
		_cteEntry.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cte entry.
	*
	* @param userUuid the user uuid of this cte entry
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cteEntry.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CTEEntry> toCacheModel() {
		return _cteEntry.toCacheModel();
	}

	@Override
	public CTEEntry toEscapedModel() {
		return new CTEEntryWrapper(_cteEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cteEntry.toString();
	}

	@Override
	public CTEEntry toUnescapedModel() {
		return new CTEEntryWrapper(_cteEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cteEntry.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CTEEntryWrapper)) {
			return false;
		}

		CTEEntryWrapper cteEntryWrapper = (CTEEntryWrapper)obj;

		if (Objects.equals(_cteEntry, cteEntryWrapper._cteEntry)) {
			return true;
		}

		return false;
	}

	@Override
	public CTEEntry getWrappedModel() {
		return _cteEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cteEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cteEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cteEntry.resetOriginalValues();
	}

	private final CTEEntry _cteEntry;
}