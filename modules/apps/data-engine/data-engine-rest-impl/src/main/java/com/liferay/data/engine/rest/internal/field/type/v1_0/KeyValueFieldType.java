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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import com.liferay.data.engine.rest.dto.v1_0.DataDefinitionField;
import com.liferay.data.engine.rest.internal.field.type.v1_0.util.CustomPropertiesUtil;
import com.liferay.data.engine.rest.internal.util.LocalizationUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.template.soy.data.SoyDataFactory;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gabriel Albuquerque
 */
public class KeyValueFieldType extends BaseFieldType {

	public KeyValueFieldType(
		DataDefinitionField dataDefinitionField,
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		SoyDataFactory soyDataFactory) {

		super(
			dataDefinitionField, httpServletRequest, httpServletResponse,
			soyDataFactory);
	}

	@Override
	public DataDefinitionField deserialize(JSONObject jsonObject)
		throws Exception {

		DataDefinitionField dataDefinitionField = super.deserialize(jsonObject);

		dataDefinitionField.setCustomProperties(
			CustomPropertiesUtil.add(
				dataDefinitionField.getCustomProperties(), "autoFocus",
				jsonObject.getBoolean("autoFocus")));
		dataDefinitionField.setCustomProperties(
			CustomPropertiesUtil.add(
				dataDefinitionField.getCustomProperties(), "placeholder",
				LocalizationUtil.toLocalizedValues(
					jsonObject.getJSONObject("placeholder"))));
		dataDefinitionField.setCustomProperties(
			CustomPropertiesUtil.add(
				dataDefinitionField.getCustomProperties(), "tooltip",
				LocalizationUtil.toLocalizedValues(
					jsonObject.getJSONObject("tooltip"))));

		return dataDefinitionField;
	}

	@Override
	public JSONObject toJSONObject() throws Exception {
		JSONObject jsonObject = super.toJSONObject();

		return jsonObject.put(
			"autoFocus",
			CustomPropertiesUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "autoFocus", false)
		).put(
			"placeholder",
			LocalizationUtil.toJSONObject(
				CustomPropertiesUtil.getMap(
					dataDefinitionField.getCustomProperties(), "placeholder"))
		).put(
			"tooltip",
			LocalizationUtil.toJSONObject(
				CustomPropertiesUtil.getMap(
					dataDefinitionField.getCustomProperties(), "tooltip"))
		);
	}

	@Override
	protected void addContext(Map<String, Object> context) {
		context.put(
			"autoFocus",
			CustomPropertiesUtil.getBoolean(
				dataDefinitionField.getCustomProperties(), "autoFocus", false));
		context.put(
			"placeholder",
			LocalizationUtil.getLocalizedValue(
				httpServletRequest.getLocale(),
				CustomPropertiesUtil.getMap(
					dataDefinitionField.getCustomProperties(), "placeholder")));
		context.put("strings", _getStrings());
		context.put(
			"tooltip",
			LocalizationUtil.getLocalizedValue(
				httpServletRequest.getLocale(),
				CustomPropertiesUtil.getMap(
					dataDefinitionField.getCustomProperties(), "tooltip")));
	}

	private Map<String, String> _getStrings() {
		Map<String, String> values = new HashMap<>();

		values.put(
			"keyLabel", LanguageUtil.get(httpServletRequest, "field-name"));

		return values;
	}

}