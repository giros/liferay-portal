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

package com.liferay.exportimport.warning.message;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.MissingReference;
import com.liferay.portal.kernel.json.JSONArray;

import java.util.Locale;
import java.util.Map;

/**
 * @author Gergely Mathe
 */
@ProviderType
public interface ExportImportWarningMessage {

	public void addWarningMessage(String warningMessage);

	public JSONArray getMissingReferenceWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences);

	public String getNextWarningMessage();

	public JSONArray getRegularWarningMessagesJSONArray();

	public JSONArray getWarningMessagesJSONArray(
		Locale locale, Map<String, MissingReference> missingReferences);

}