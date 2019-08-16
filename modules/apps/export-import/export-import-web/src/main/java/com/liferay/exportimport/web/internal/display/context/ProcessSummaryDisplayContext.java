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

package com.liferay.exportimport.web.internal.display.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Péter Alius
 */
public class ProcessSummaryDisplayContext {

	public ProcessSummaryDisplayContext(ThemeDisplay themeDisplay) {
		_themeDisplay = themeDisplay;
	}

	public List<String> getPageNames(JSONArray layoutsJSONArray) {
		List<String> pageNames = new ArrayList<>();

		for (int i = 0; i < layoutsJSONArray.length(); ++i) {
			JSONObject layoutJSONObject = layoutsJSONArray.getJSONObject(i);

			String pageName = layoutJSONObject.getString("name");

			long layoutId = layoutJSONObject.getLong("layoutId");

			if (_isPropagated(
					layoutId, layoutJSONObject.getBoolean("privateLayout"))) {

				pageNames.add(pageName);
			}

			if (layoutJSONObject.getBoolean("hasChildren")) {
				List<String> childPageNames = _getChildPageNames(
					pageName, layoutJSONObject.getJSONObject("children"));

				pageNames.addAll(childPageNames);
			}
		}

		return pageNames;
	}

	private List<String> _getChildPageNames(
		String basePageName, JSONObject childLayoutsJSONObject) {

		List<String> pageNames = new ArrayList<>();

		JSONArray childLayoutsJSONArray = childLayoutsJSONObject.getJSONArray(
			"layouts");

		for (int i = 0; i < childLayoutsJSONArray.length(); ++i) {
			JSONObject childLayoutJSONObject =
				childLayoutsJSONArray.getJSONObject(i);

			String childPageName =
				basePageName + StringPool.FORWARD_SLASH +
					childLayoutJSONObject.getString("name");

			long layoutId = childLayoutJSONObject.getLong("layoutId");

			if (_isPropagated(
					layoutId,
					childLayoutJSONObject.getBoolean("privateLayout"))) {

				pageNames.add(childPageName);
			}

			if (childLayoutJSONObject.getBoolean("hasChildren")) {
				List<String> childPageNames = _getChildPageNames(
					childPageName,
					childLayoutJSONObject.getJSONObject("children"));

				pageNames.addAll(childPageNames);
			}
		}

		return pageNames;
	}

	private boolean _isPropagated(long layoutId, boolean privateLayout) {
		Group scopeGroup = _themeDisplay.getScopeGroup();

		Group liveGroup = scopeGroup.getLiveGroup();

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			liveGroup.getGroupId(), privateLayout, layoutId);

		if (layout != null) {
			return true;
		}

		return false;
	}

	private final ThemeDisplay _themeDisplay;

}