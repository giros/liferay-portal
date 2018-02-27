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

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gergely Mathe
 */
public class BackgroundTaskStatusMessage implements Serializable {

	public Serializable getStatusMessage(String key) {
		return _statusMessages.get(key);
	}

	public Map<String, Serializable> getStatusMessages() {
		return Collections.unmodifiableMap(_statusMessages);
	}

	public String getStatusMessagesJSON() {
		return JSONFactoryUtil.serialize(_statusMessages);
	}

	public void setStatusMessage(String key, Serializable value) {
		_statusMessages.put(key, value);
	}

	private final Map<String, Serializable> _statusMessages =
		new ConcurrentHashMap<>();

}