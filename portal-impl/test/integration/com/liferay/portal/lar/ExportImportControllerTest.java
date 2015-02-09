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

package com.liferay.portal.lar;

import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Daniel Kocsis
 */
@Sync
public class ExportImportControllerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void buildSampleData() throws Exception {
		Group group = GroupTestUtil.addGroup();

		group.setName("Bookmarks Test Group");

		GroupLocalServiceUtil.updateGroup(group);

		for(int i = 0; i < _FOLDER_NUMBER; i++) {
			BookmarksFolder folder = bookmarksFolderLocalService.addFolder(
				TestPropsValues.getUserId(),
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				"folder_" + i, StringPool.BLANK, new ServiceContext());

			for (int j = 0; j < _ENTRY_NUMBER; j++) {
				bookmarksEntryLocalService.addEntry(
					TestPropsValues.getUserId(), group.getGroupId(),
					folder.getFolderId(), "entry_" + j, "http://www.test.com",
					StringPool.BLANK, new ServiceContext());
			}
		}

		for (int j = 0; j < _ENTRY_NUMBER; j++) {
			bookmarksEntryLocalService.addEntry(
				TestPropsValues.getUserId(), group.getGroupId(),
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID, "entry_" + j,
				"http://www.test.com", StringPool.BLANK, new ServiceContext());
		}

	}

	@BeanReference(type = com.liferay.bookmarks.service.BookmarksEntryLocalService.class)
	protected com.liferay.bookmarks.service.BookmarksEntryLocalService bookmarksEntryLocalService;
	@BeanReference(type = com.liferay.bookmarks.service.BookmarksFolderLocalService.class)
	protected com.liferay.bookmarks.service.BookmarksFolderLocalService bookmarksFolderLocalService;

	private static final int _ENTRY_NUMBER = 100;
	private static final int _FOLDER_NUMBER = 1000;

}
