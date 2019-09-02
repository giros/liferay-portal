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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Péter Alius
 * @author Zoltan Csaszi
 */
public class ProcessSummaryDisplayContext {

	public ProcessSummaryDisplayContext() {
	}

	/**
	 * @deprecated As of Mueller (7.2.x), as of 7.3, with no replacement
	 */
	@Deprecated
	public List<String> getPageNames(JSONArray layoutsJSONArray) {
		List<String> pageNames = new ArrayList<>();

		for (int i = 0; i < layoutsJSONArray.length(); ++i) {
			JSONObject layoutJSONObject = layoutsJSONArray.getJSONObject(i);

			String pageName = layoutJSONObject.getString("name");

			pageNames.add(pageName);

			if (layoutJSONObject.getBoolean("hasChildren")) {
				List<String> childPageNames = _getChildPageNames(
					pageName, layoutJSONObject.getJSONObject("children"));

				pageNames.addAll(childPageNames);
			}
		}

		return pageNames;
	}

	public List<String> getPageNames(
		long groupId, boolean privateLayout, long[] selectedLayoutIds) {

		Set<String> pageNames = new LinkedHashSet<>();

		Arrays.sort(selectedLayoutIds);

		for (long selectedLayoutId : selectedLayoutIds) {
			_addPageNames(groupId, privateLayout, selectedLayoutId, pageNames);
		}

		return new ArrayList<>(pageNames);
	}

	private void _addPageNames(
		long groupId, boolean privateLayout, long selectedLayoutId,
		Set<String> pageNames) {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			groupId, privateLayout, selectedLayoutId);

		if (layout == null) {
			return;
		}

		StringBuilder sb = new StringBuilder(layout.getName());

		while (layout.getParentLayoutId() !=
					LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			try {
				layout = LayoutLocalServiceUtil.getParentLayout(layout);

				_addPageNames(
					groupId, privateLayout, layout.getLayoutId(), pageNames);

				sb.insert(0, layout.getName() + StringPool.FORWARD_SLASH);
			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(pe, pe);
				}
			}
		}

		pageNames.add(sb.toString());
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

			pageNames.add(childPageName);

			if (childLayoutJSONObject.getBoolean("hasChildren")) {
				List<String> childPageNames = _getChildPageNames(
					childPageName,
					childLayoutJSONObject.getJSONObject("children"));

				pageNames.addAll(childPageNames);
			}
		}

		return pageNames;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProcessSummaryDisplayContext.class);

}