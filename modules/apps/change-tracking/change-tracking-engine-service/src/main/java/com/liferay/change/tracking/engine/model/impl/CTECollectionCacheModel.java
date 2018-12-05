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

package com.liferay.change.tracking.engine.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.engine.model.CTECollection;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CTECollection in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see CTECollection
 * @generated
 */
@ProviderType
public class CTECollectionCacheModel implements CacheModel<CTECollection>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CTECollectionCacheModel)) {
			return false;
		}

		CTECollectionCacheModel cteCollectionCacheModel = (CTECollectionCacheModel)obj;

		if (collectionId == cteCollectionCacheModel.collectionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, collectionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{collectionId=");
		sb.append(collectionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTECollection toEntityModel() {
		CTECollectionImpl cteCollectionImpl = new CTECollectionImpl();

		cteCollectionImpl.setCollectionId(collectionId);
		cteCollectionImpl.setCompanyId(companyId);
		cteCollectionImpl.setUserId(userId);

		if (userName == null) {
			cteCollectionImpl.setUserName("");
		}
		else {
			cteCollectionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			cteCollectionImpl.setCreateDate(null);
		}
		else {
			cteCollectionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			cteCollectionImpl.setModifiedDate(null);
		}
		else {
			cteCollectionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			cteCollectionImpl.setName("");
		}
		else {
			cteCollectionImpl.setName(name);
		}

		if (description == null) {
			cteCollectionImpl.setDescription("");
		}
		else {
			cteCollectionImpl.setDescription(description);
		}

		cteCollectionImpl.setStatus(status);
		cteCollectionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			cteCollectionImpl.setStatusByUserName("");
		}
		else {
			cteCollectionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			cteCollectionImpl.setStatusDate(null);
		}
		else {
			cteCollectionImpl.setStatusDate(new Date(statusDate));
		}

		cteCollectionImpl.resetOriginalValues();

		return cteCollectionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		collectionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(collectionId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public long collectionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
}