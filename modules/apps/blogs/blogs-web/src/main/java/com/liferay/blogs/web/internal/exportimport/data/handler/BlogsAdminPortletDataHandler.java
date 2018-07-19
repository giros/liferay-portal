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

package com.liferay.blogs.web.internal.exportimport.data.handler;

import com.liferay.blogs.constants.BlogsPortletKeys;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.util.PropsValues;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "javax.portlet.name=" + BlogsPortletKeys.BLOGS_ADMIN,
	service = PortletDataHandler.class
)
public class BlogsAdminPortletDataHandler extends BlogsPortletDataHandler {

	@Activate
	@Override
	protected void activate() {
		setDataLevel(DataLevel.SITE);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(BlogsEntry.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "entries", true, false,
				new PortletDataHandlerControl[] {
					new PortletDataHandlerBoolean(
						NAMESPACE, "referenced-content")
				},
				BlogsEntry.class.getName()));
		setPublishToLiveByDefault(PropsValues.BLOGS_PUBLISH_TO_LIVE_BY_DEFAULT);
		setStagingControls(getExportControls());
	}

}