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
 * This class is a wrapper for {@link CTECollection}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTECollection
 * @generated
 */
@ProviderType
public class CTECollectionWrapper implements CTECollection,
	ModelWrapper<CTECollection> {
	public CTECollectionWrapper(CTECollection cteCollection) {
		_cteCollection = cteCollection;
	}

	@Override
	public Class<?> getModelClass() {
		return CTECollection.class;
	}

	@Override
	public String getModelClassName() {
		return CTECollection.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("collectionId", getCollectionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());
		attributes.put("status", getStatus());
		attributes.put("statusByUserId", getStatusByUserId());
		attributes.put("statusByUserName", getStatusByUserName());
		attributes.put("statusDate", getStatusDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long collectionId = (Long)attributes.get("collectionId");

		if (collectionId != null) {
			setCollectionId(collectionId);
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

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		Long statusByUserId = (Long)attributes.get("statusByUserId");

		if (statusByUserId != null) {
			setStatusByUserId(statusByUserId);
		}

		String statusByUserName = (String)attributes.get("statusByUserName");

		if (statusByUserName != null) {
			setStatusByUserName(statusByUserName);
		}

		Date statusDate = (Date)attributes.get("statusDate");

		if (statusDate != null) {
			setStatusDate(statusDate);
		}
	}

	@Override
	public Object clone() {
		return new CTECollectionWrapper((CTECollection)_cteCollection.clone());
	}

	@Override
	public int compareTo(CTECollection cteCollection) {
		return _cteCollection.compareTo(cteCollection);
	}

	/**
	* Returns the collection ID of this cte collection.
	*
	* @return the collection ID of this cte collection
	*/
	@Override
	public long getCollectionId() {
		return _cteCollection.getCollectionId();
	}

	/**
	* Returns the company ID of this cte collection.
	*
	* @return the company ID of this cte collection
	*/
	@Override
	public long getCompanyId() {
		return _cteCollection.getCompanyId();
	}

	/**
	* Returns the create date of this cte collection.
	*
	* @return the create date of this cte collection
	*/
	@Override
	public Date getCreateDate() {
		return _cteCollection.getCreateDate();
	}

	/**
	* Returns the description of this cte collection.
	*
	* @return the description of this cte collection
	*/
	@Override
	public String getDescription() {
		return _cteCollection.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _cteCollection.getExpandoBridge();
	}

	/**
	* Returns the modified date of this cte collection.
	*
	* @return the modified date of this cte collection
	*/
	@Override
	public Date getModifiedDate() {
		return _cteCollection.getModifiedDate();
	}

	/**
	* Returns the name of this cte collection.
	*
	* @return the name of this cte collection
	*/
	@Override
	public String getName() {
		return _cteCollection.getName();
	}

	/**
	* Returns the primary key of this cte collection.
	*
	* @return the primary key of this cte collection
	*/
	@Override
	public long getPrimaryKey() {
		return _cteCollection.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cteCollection.getPrimaryKeyObj();
	}

	/**
	* Returns the status of this cte collection.
	*
	* @return the status of this cte collection
	*/
	@Override
	public int getStatus() {
		return _cteCollection.getStatus();
	}

	/**
	* Returns the status by user ID of this cte collection.
	*
	* @return the status by user ID of this cte collection
	*/
	@Override
	public long getStatusByUserId() {
		return _cteCollection.getStatusByUserId();
	}

	/**
	* Returns the status by user name of this cte collection.
	*
	* @return the status by user name of this cte collection
	*/
	@Override
	public String getStatusByUserName() {
		return _cteCollection.getStatusByUserName();
	}

	/**
	* Returns the status by user uuid of this cte collection.
	*
	* @return the status by user uuid of this cte collection
	*/
	@Override
	public String getStatusByUserUuid() {
		return _cteCollection.getStatusByUserUuid();
	}

	/**
	* Returns the status date of this cte collection.
	*
	* @return the status date of this cte collection
	*/
	@Override
	public Date getStatusDate() {
		return _cteCollection.getStatusDate();
	}

	/**
	* Returns the user ID of this cte collection.
	*
	* @return the user ID of this cte collection
	*/
	@Override
	public long getUserId() {
		return _cteCollection.getUserId();
	}

	/**
	* Returns the user name of this cte collection.
	*
	* @return the user name of this cte collection
	*/
	@Override
	public String getUserName() {
		return _cteCollection.getUserName();
	}

	/**
	* Returns the user uuid of this cte collection.
	*
	* @return the user uuid of this cte collection
	*/
	@Override
	public String getUserUuid() {
		return _cteCollection.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _cteCollection.hashCode();
	}

	/**
	* Returns <code>true</code> if this cte collection is approved.
	*
	* @return <code>true</code> if this cte collection is approved; <code>false</code> otherwise
	*/
	@Override
	public boolean isApproved() {
		return _cteCollection.isApproved();
	}

	@Override
	public boolean isCachedModel() {
		return _cteCollection.isCachedModel();
	}

	/**
	* Returns <code>true</code> if this cte collection is denied.
	*
	* @return <code>true</code> if this cte collection is denied; <code>false</code> otherwise
	*/
	@Override
	public boolean isDenied() {
		return _cteCollection.isDenied();
	}

	/**
	* Returns <code>true</code> if this cte collection is a draft.
	*
	* @return <code>true</code> if this cte collection is a draft; <code>false</code> otherwise
	*/
	@Override
	public boolean isDraft() {
		return _cteCollection.isDraft();
	}

	@Override
	public boolean isEscapedModel() {
		return _cteCollection.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this cte collection is expired.
	*
	* @return <code>true</code> if this cte collection is expired; <code>false</code> otherwise
	*/
	@Override
	public boolean isExpired() {
		return _cteCollection.isExpired();
	}

	/**
	* Returns <code>true</code> if this cte collection is inactive.
	*
	* @return <code>true</code> if this cte collection is inactive; <code>false</code> otherwise
	*/
	@Override
	public boolean isInactive() {
		return _cteCollection.isInactive();
	}

	/**
	* Returns <code>true</code> if this cte collection is incomplete.
	*
	* @return <code>true</code> if this cte collection is incomplete; <code>false</code> otherwise
	*/
	@Override
	public boolean isIncomplete() {
		return _cteCollection.isIncomplete();
	}

	@Override
	public boolean isNew() {
		return _cteCollection.isNew();
	}

	/**
	* Returns <code>true</code> if this cte collection is pending.
	*
	* @return <code>true</code> if this cte collection is pending; <code>false</code> otherwise
	*/
	@Override
	public boolean isPending() {
		return _cteCollection.isPending();
	}

	/**
	* Returns <code>true</code> if this cte collection is scheduled.
	*
	* @return <code>true</code> if this cte collection is scheduled; <code>false</code> otherwise
	*/
	@Override
	public boolean isScheduled() {
		return _cteCollection.isScheduled();
	}

	@Override
	public void persist() {
		_cteCollection.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_cteCollection.setCachedModel(cachedModel);
	}

	/**
	* Sets the collection ID of this cte collection.
	*
	* @param collectionId the collection ID of this cte collection
	*/
	@Override
	public void setCollectionId(long collectionId) {
		_cteCollection.setCollectionId(collectionId);
	}

	/**
	* Sets the company ID of this cte collection.
	*
	* @param companyId the company ID of this cte collection
	*/
	@Override
	public void setCompanyId(long companyId) {
		_cteCollection.setCompanyId(companyId);
	}

	/**
	* Sets the create date of this cte collection.
	*
	* @param createDate the create date of this cte collection
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_cteCollection.setCreateDate(createDate);
	}

	/**
	* Sets the description of this cte collection.
	*
	* @param description the description of this cte collection
	*/
	@Override
	public void setDescription(String description) {
		_cteCollection.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_cteCollection.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_cteCollection.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_cteCollection.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the modified date of this cte collection.
	*
	* @param modifiedDate the modified date of this cte collection
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_cteCollection.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this cte collection.
	*
	* @param name the name of this cte collection
	*/
	@Override
	public void setName(String name) {
		_cteCollection.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_cteCollection.setNew(n);
	}

	/**
	* Sets the primary key of this cte collection.
	*
	* @param primaryKey the primary key of this cte collection
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_cteCollection.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_cteCollection.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the status of this cte collection.
	*
	* @param status the status of this cte collection
	*/
	@Override
	public void setStatus(int status) {
		_cteCollection.setStatus(status);
	}

	/**
	* Sets the status by user ID of this cte collection.
	*
	* @param statusByUserId the status by user ID of this cte collection
	*/
	@Override
	public void setStatusByUserId(long statusByUserId) {
		_cteCollection.setStatusByUserId(statusByUserId);
	}

	/**
	* Sets the status by user name of this cte collection.
	*
	* @param statusByUserName the status by user name of this cte collection
	*/
	@Override
	public void setStatusByUserName(String statusByUserName) {
		_cteCollection.setStatusByUserName(statusByUserName);
	}

	/**
	* Sets the status by user uuid of this cte collection.
	*
	* @param statusByUserUuid the status by user uuid of this cte collection
	*/
	@Override
	public void setStatusByUserUuid(String statusByUserUuid) {
		_cteCollection.setStatusByUserUuid(statusByUserUuid);
	}

	/**
	* Sets the status date of this cte collection.
	*
	* @param statusDate the status date of this cte collection
	*/
	@Override
	public void setStatusDate(Date statusDate) {
		_cteCollection.setStatusDate(statusDate);
	}

	/**
	* Sets the user ID of this cte collection.
	*
	* @param userId the user ID of this cte collection
	*/
	@Override
	public void setUserId(long userId) {
		_cteCollection.setUserId(userId);
	}

	/**
	* Sets the user name of this cte collection.
	*
	* @param userName the user name of this cte collection
	*/
	@Override
	public void setUserName(String userName) {
		_cteCollection.setUserName(userName);
	}

	/**
	* Sets the user uuid of this cte collection.
	*
	* @param userUuid the user uuid of this cte collection
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_cteCollection.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<CTECollection> toCacheModel() {
		return _cteCollection.toCacheModel();
	}

	@Override
	public CTECollection toEscapedModel() {
		return new CTECollectionWrapper(_cteCollection.toEscapedModel());
	}

	@Override
	public String toString() {
		return _cteCollection.toString();
	}

	@Override
	public CTECollection toUnescapedModel() {
		return new CTECollectionWrapper(_cteCollection.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _cteCollection.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CTECollectionWrapper)) {
			return false;
		}

		CTECollectionWrapper cteCollectionWrapper = (CTECollectionWrapper)obj;

		if (Objects.equals(_cteCollection, cteCollectionWrapper._cteCollection)) {
			return true;
		}

		return false;
	}

	@Override
	public CTECollection getWrappedModel() {
		return _cteCollection;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _cteCollection.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _cteCollection.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_cteCollection.resetOriginalValues();
	}

	private final CTECollection _cteCollection;
}