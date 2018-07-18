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

package com.liferay.bookmarks.internal.exportimport.data.handler;

import com.liferay.bookmarks.constants.BookmarksPortletKeys;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + BookmarksPortletKeys.BOOKMARKS_ADMIN,
	service = PortletDataHandler.class
)
public class BookmarksAdminPortletDataHandler
	extends BookmarksPortletDataHandler {

	@Activate
	@Override
	protected void activate() {
		setDataLevel(DataLevel.SITE);
		setDeletionSystemEventStagedModelTypes(
			new StagedModelType(BookmarksEntry.class),
			new StagedModelType(BookmarksFolder.class));
		setExportControls(
			new PortletDataHandlerBoolean(
				NAMESPACE, "folders", true, false, null,
				BookmarksFolder.class.getName()),
			new PortletDataHandlerBoolean(
				NAMESPACE, "entries", true, false, null,
				BookmarksEntry.class.getName()));
		setStagingControls(getExportControls());
	}

}