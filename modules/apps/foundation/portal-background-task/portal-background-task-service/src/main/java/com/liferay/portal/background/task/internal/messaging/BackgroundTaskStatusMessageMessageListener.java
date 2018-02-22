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

package com.liferay.portal.background.task.internal.messaging;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskConstants;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessage;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageMessageTranslator;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageRegistry;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.StringBundler;

/**
 * @author Gergely Mathe
 */
public class BackgroundTaskStatusMessageMessageListener
	extends BaseMessageListener {

	public BackgroundTaskStatusMessageMessageListener(
		long backgroundTaskId,
		BackgroundTaskStatusMessageMessageTranslator
			backgroundTaskStatusMessageMessageTranslator,
		BackgroundTaskStatusMessageRegistry
			backgroundTaskStatusMessageRegistry) {

		_backgroundTaskId = backgroundTaskId;
		_backgroundTaskStatusMessageMessageTranslator =
			backgroundTaskStatusMessageMessageTranslator;
		_backgroundTaskStatusMessageRegistry =
			backgroundTaskStatusMessageRegistry;
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		long backgroundTaskId = message.getLong(
			BackgroundTaskConstants.BACKGROUND_TASK_ID);

		if (backgroundTaskId != _backgroundTaskId) {
			return;
		}

		BackgroundTaskStatusMessage backgroundTaskStatusMessage =
			_backgroundTaskStatusMessageRegistry.getBackgroundTaskStatusMessage(
				backgroundTaskId);

		if (backgroundTaskStatusMessage == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Unable to locate status message for background task ",
						String.valueOf(backgroundTaskId), " to process ",
						String.valueOf(message)));
			}

			return;
		}

		_backgroundTaskStatusMessageMessageTranslator.translate(
			backgroundTaskStatusMessage, message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BackgroundTaskStatusMessageMessageListener.class);

	private final long _backgroundTaskId;
	private final BackgroundTaskStatusMessageMessageTranslator
		_backgroundTaskStatusMessageMessageTranslator;
	private final BackgroundTaskStatusMessageRegistry
		_backgroundTaskStatusMessageRegistry;

}