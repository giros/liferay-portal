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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.ArrayList;
import java.util.List;

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
		return null;
	}

	public List<String> getPageNames(
		long groupId, boolean privateLayout, long[] selectedLayoutIds) {

		List<String> pageNames = new ArrayList<>();

		for (long selectedLayoutId :
				ArrayUtil.sortedUnique(selectedLayoutIds)) {

			String pageName = _getPageName(
				groupId, privateLayout, selectedLayoutId);

			if (pageName != null) {
				pageNames.add(pageName);
			}
		}

		return pageNames;
	}

	private String _getPageName(
		long groupId, boolean privateLayout, long selectedLayoutId) {

		Layout layout = LayoutLocalServiceUtil.fetchLayout(
			groupId, privateLayout, selectedLayoutId);

		if (layout == null) {
			return null;
		}

		List<String> layoutNames = new ArrayList<>();

		layoutNames.add(0, layout.getName());

		while (!layout.isRootLayout()) {
			layout = LayoutLocalServiceUtil.fetchLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getParentLayoutId());

			if (layout != null) {
				layoutNames.add(0, layout.getName() + StringPool.FORWARD_SLASH);
			}
			else {
				break;
			}
		}

		StringBundler pageNameSB = new StringBundler(
			ArrayUtil.toStringArray(layoutNames));

		return pageNameSB.toString();
	}

}