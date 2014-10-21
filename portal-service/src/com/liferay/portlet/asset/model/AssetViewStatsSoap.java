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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AssetViewStatsSoap implements Serializable {
	public static AssetViewStatsSoap toSoapModel(AssetViewStats model) {
		AssetViewStatsSoap soapModel = new AssetViewStatsSoap();

		soapModel.setViewStatsId(model.getViewStatsId());
		soapModel.setUserId(model.getUserId());
		soapModel.setViewDate(model.getViewDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());

		return soapModel;
	}

	public static AssetViewStatsSoap[] toSoapModels(AssetViewStats[] models) {
		AssetViewStatsSoap[] soapModels = new AssetViewStatsSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static AssetViewStatsSoap[][] toSoapModels(AssetViewStats[][] models) {
		AssetViewStatsSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new AssetViewStatsSoap[models.length][models[0].length];
		}
		else {
			soapModels = new AssetViewStatsSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static AssetViewStatsSoap[] toSoapModels(List<AssetViewStats> models) {
		List<AssetViewStatsSoap> soapModels = new ArrayList<AssetViewStatsSoap>(models.size());

		for (AssetViewStats model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new AssetViewStatsSoap[soapModels.size()]);
	}

	public AssetViewStatsSoap() {
	}

	public long getPrimaryKey() {
		return _viewStatsId;
	}

	public void setPrimaryKey(long pk) {
		setViewStatsId(pk);
	}

	public long getViewStatsId() {
		return _viewStatsId;
	}

	public void setViewStatsId(long viewStatsId) {
		_viewStatsId = viewStatsId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public Date getViewDate() {
		return _viewDate;
	}

	public void setViewDate(Date viewDate) {
		_viewDate = viewDate;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	private long _viewStatsId;
	private long _userId;
	private Date _viewDate;
	private long _classNameId;
	private long _classPK;
}