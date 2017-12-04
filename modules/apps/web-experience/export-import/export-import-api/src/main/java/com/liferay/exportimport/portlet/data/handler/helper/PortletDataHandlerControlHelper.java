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

package com.liferay.exportimport.portlet.data.handler.helper;

import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortletKeys;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(immediate = true, service = PortletDataHandlerControlHelper.class)
public class PortletDataHandlerControlHelper {

	public PortletDataHandlerControl[] getExportConfigurationControls(
			long companyId, long groupId, Portlet portlet,
			boolean displayPortlet, PortletDataHandlerControl[] exportControls,
			long plid, boolean privateLayout)
		throws Exception {

		List<PortletDataHandlerBoolean> configurationControls =
			new ArrayList<>();

		// Setup

		if ((_portletPreferencesLocalService.getPortletPreferencesCount(
				PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portlet, false) >
					0) ||
			(_portletPreferencesLocalService.getPortletPreferencesCount(
				groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP,
				portlet.getRootPortletId(), false) > 0) ||
			(_portletPreferencesLocalService.getPortletPreferencesCount(
				companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				portlet.getRootPortletId(), false) > 0)) {

			PortletDataHandlerControl[] portletDataHandlerControls = null;

			if (displayPortlet) {
				portletDataHandlerControls = exportControls;
			}

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_SETUP, "setup", true,
					false, portletDataHandlerControls, null, null));
		}

		// Archived setups

		if (_portletPreferencesLocalService.getPortletPreferencesCount(
				-1, PortletKeys.PREFS_OWNER_TYPE_ARCHIVED,
				portlet.getRootPortletId(), false) > 0) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
					"configuration-templates", true, false, null, null, null));
		}

		// User preferences

		if ((_portletPreferencesLocalService.getPortletPreferencesCount(
				-1, PortletKeys.PREFS_OWNER_TYPE_USER, plid, portlet, false) >
					0) ||
			(_portletPreferencesLocalService.getPortletPreferencesCount(
				groupId, PortletKeys.PREFS_OWNER_TYPE_USER,
				PortletKeys.PREFS_PLID_SHARED, portlet, false) > 0)) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
					"user-preferences", true, false, null, null, null));
		}

		return configurationControls.toArray(
			new PortletDataHandlerBoolean[configurationControls.size()]);
	}

	public PortletDataHandlerControl[] getImportConfigurationControls(
		Portlet portlet, ManifestSummary manifestSummary,
		boolean displayPortlet, PortletDataHandlerControl[] exportControls) {

		String[] configurationPortletOptions =
			manifestSummary.getConfigurationPortletOptions(
				portlet.getRootPortletId());

		return getImportConfigurationControls(
			configurationPortletOptions, displayPortlet, exportControls);
	}

	public PortletDataHandlerControl[] getImportConfigurationControls(
		String[] configurationPortletOptions, boolean displayPortlet,
		PortletDataHandlerControl[] exportControls) {

		List<PortletDataHandlerBoolean> configurationControls =
			new ArrayList<>();

		// Setup

		if (ArrayUtil.contains(configurationPortletOptions, "setup")) {
			PortletDataHandlerControl[] portletDataHandlerControls = null;

			if (displayPortlet) {
				portletDataHandlerControls = exportControls;
			}

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_SETUP, "setup", true,
					false, portletDataHandlerControls, null, null));
		}

		// Archived setups

		if (ArrayUtil.contains(
				configurationPortletOptions, "archived-setups")) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
					"configuration-templates", true, false, null, null, null));
		}

		// User preferences

		if (ArrayUtil.contains(
				configurationPortletOptions, "user-preferences")) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
					"user-preferences", true, false, null, null, null));
		}

		return configurationControls.toArray(
			new PortletDataHandlerBoolean[configurationControls.size()]);
	}

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;

}