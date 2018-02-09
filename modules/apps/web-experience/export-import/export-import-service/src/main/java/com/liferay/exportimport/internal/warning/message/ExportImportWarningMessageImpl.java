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

package com.liferay.exportimport.internal.warning.message;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.exportimport.kernel.lar.MissingReference;
import com.liferay.exportimport.warning.message.ExportImportWarningMessage;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;

import org.osgi.service.component.annotations.Component;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true)
@ProviderType
public class ExportImportWarningMessageImpl
	implements ExportImportWarningMessage {

	@Override
	public void addWarningMessage(String warningMessage) {
		Queue<String> warningMessages =
			ExportImportThreadLocal.getWarningMessages();

		if (warningMessages == null) {
			warningMessages = new LinkedList<>();
		}

		warningMessages.offer(warningMessage);

		ExportImportThreadLocal.setWarningMessages(warningMessages);
	}

	@Override
	public JSONArray getMissingReferenceWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences) {

		JSONArray warningMessagesJSONArray = JSONFactoryUtil.createJSONArray();

		if (MapUtil.isEmpty(missingReferences)) {
			return warningMessagesJSONArray;
		}

		for (Map.Entry<String, MissingReference> entry :
				missingReferences.entrySet()) {

			MissingReference missingReference = entry.getValue();

			Map<String, String> referrers = missingReference.getReferrers();

			JSONObject warningMessageJSONObject =
				JSONFactoryUtil.createJSONObject();

			if (Validator.isNotNull(missingReference.getClassName())) {
				warningMessageJSONObject.put(
					"info",
					LanguageUtil.format(
						locale,
						"the-original-x-does-not-exist-in-the-current-" +
							"environment",
						ResourceActionsUtil.getModelResource(
							locale, missingReference.getClassName()),
						false));
			}

			warningMessageJSONObject.put("size", referrers.size());
			warningMessageJSONObject.put(
				"type",
				ResourceActionsUtil.getModelResource(locale, entry.getKey()));

			warningMessagesJSONArray.put(warningMessageJSONObject);
		}

		return warningMessagesJSONArray;
	}

	@Override
	public String getNextWarningMessage() {
		Queue<String> warningMessages =
			ExportImportThreadLocal.getWarningMessages();

		if (warningMessages == null) {
			return null;
		}

		return warningMessages.poll();
	}

	@Override
	public JSONArray getRegularWarningMessagesJSONArray() {
		JSONArray warningMessagesJSONArray = JSONFactoryUtil.createJSONArray();

		String warningMessage = null;

		while ((warningMessage = getNextWarningMessage()) != null) {
			JSONObject warningMessageJSONObject =
				JSONFactoryUtil.createJSONObject();

			warningMessageJSONObject.put("info", warningMessage);

			warningMessagesJSONArray.put(warningMessageJSONObject);
		}

		return warningMessagesJSONArray;
	}

	public JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences) {

		JSONArray regularWarningMessagesJSONArray =
			getRegularWarningMessagesJSONArray();

		JSONArray missingReferenceWarningMessagesJSONArray =
			getMissingReferenceWarningMessagesJSONArray(
				locale, missingReferences);

		JSONArray warningMessagesJSONArray = JSONFactoryUtil.createJSONArray();

		try {
			warningMessagesJSONArray = JSONFactoryUtil.createJSONArray(
				regularWarningMessagesJSONArray.toString() +
					missingReferenceWarningMessagesJSONArray.toString());
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}
			else if (_log.isWarnEnabled()) {
				_log.warn(jsone, jsone);
			}
		}

		return warningMessagesJSONArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportWarningMessageImpl.class);

}