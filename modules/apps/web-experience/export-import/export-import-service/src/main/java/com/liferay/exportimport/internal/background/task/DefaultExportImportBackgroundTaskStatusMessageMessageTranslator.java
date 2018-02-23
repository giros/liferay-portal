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

package com.liferay.exportimport.internal.background.task;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessage;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusMessageMessageTranslator;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Gergely Mathe
 */
public class DefaultExportImportBackgroundTaskStatusMessageMessageTranslator
	implements BackgroundTaskStatusMessageMessageTranslator {

	public void translate(
		BackgroundTaskStatusMessage backgroundTaskStatusMessage,
		Message message) {

		translateErrorMessage(backgroundTaskStatusMessage, message);
		translateWarningMessage(backgroundTaskStatusMessage, message);
	}

	protected void translateErrorMessage(
		BackgroundTaskStatusMessage backgroundTaskStatusMessage,
		Message message) {

		String errorMessage = message.getString("errorMessage");

		if (Validator.isNull(errorMessage)) {
			return;
		}

		JSONArray errorMessagesJSONArray =
			(JSONArray)backgroundTaskStatusMessage.getStatusMessage(
				"errorMessages");

		if (errorMessagesJSONArray == null) {
			errorMessagesJSONArray = JSONFactoryUtil.createJSONArray();
		}

		JSONObject errorMessageJSONObject = JSONFactoryUtil.createJSONObject();

		errorMessageJSONObject.put("errorMessage", errorMessage);

		errorMessagesJSONArray.put(errorMessageJSONObject);

		backgroundTaskStatusMessage.setStatusMessage(
			"errorMessages", errorMessagesJSONArray);
	}

	protected void translateWarningMessage(
		BackgroundTaskStatusMessage backgroundTaskStatusMessage,
		Message message) {

		String warningMessage = message.getString("warningMessage");

		if (Validator.isNull(warningMessage)) {
			return;
		}

		JSONArray warningMessagesJSONArray =
			(JSONArray)backgroundTaskStatusMessage.getStatusMessage(
				"warningMessages");

		if (warningMessagesJSONArray == null) {
			warningMessagesJSONArray = JSONFactoryUtil.createJSONArray();
		}

		JSONObject warningMessageJSONObject =
			JSONFactoryUtil.createJSONObject();

		warningMessageJSONObject.put("warningMessage", warningMessage);

		warningMessagesJSONArray.put(warningMessageJSONObject);

		backgroundTaskStatusMessage.setStatusMessage(
			"warningMessages", warningMessagesJSONArray);
	}

}