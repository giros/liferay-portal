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

package com.liferay.portal.background.task.internal;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessage;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageRegistry;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true, service = BackgroundTaskStatusMessageRegistry.class
)
public class BackgroundTaskStatusMessageRegistryImpl
	implements BackgroundTaskStatusMessageRegistry {

	@Override
	public BackgroundTaskStatusMessage getBackgroundTaskStatusMessage(
		long backgroundTaskId) {

		return _backgroundTaskStatusMessages.get(backgroundTaskId);
	}

	@Override
	public BackgroundTaskStatusMessage registerBackgroundTaskStatusMessage(
		long backgroundTaskId) {

		BackgroundTaskStatusMessage backgroundTaskStatusMessage =
			_backgroundTaskStatusMessages.get(backgroundTaskId);

		if (backgroundTaskStatusMessage == null) {
			backgroundTaskStatusMessage = new BackgroundTaskStatusMessage();

			_backgroundTaskStatusMessages.put(
				backgroundTaskId, backgroundTaskStatusMessage);
		}

		return backgroundTaskStatusMessage;
	}

	@Override
	public BackgroundTaskStatusMessage unRegisterBackgroundTaskStatusMessage(
		long backgroundTaskId) {

		return _backgroundTaskStatusMessages.remove(backgroundTaskId);
	}

	private final Map<Long, BackgroundTaskStatusMessage>
		_backgroundTaskStatusMessages = new HashMap<>();

}