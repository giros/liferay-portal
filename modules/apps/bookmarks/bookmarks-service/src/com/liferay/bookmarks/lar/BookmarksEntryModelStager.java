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

package com.liferay.bookmarks.lar;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.portal.kernel.lar.BaseModelStager;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Mate Thurzo
 */
public class BookmarksEntryModelStager extends BaseModelStager<BookmarksEntry> {

	@Override
	protected void doExportStagedModel(
			BookmarksEntry bookmarksEntry, OutputStream outputStream)
		throws Exception {

		// TODO serialize

		//outputStream.writeObject();
	}

	@Override
	protected BookmarksEntry doImportStagedModel(InputStream inputStream)
		throws Exception {

		return super.doImportStagedModel(inputStream);
	}

}