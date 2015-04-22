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
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.lar.StagedModelRepository;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.model.StagedModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@Component(property = {"model.name=com.liferay.bookmarks.model.BookmarksEntry"})
public class BookmarksEntryStagedModelRepository
	implements StagedModelRepository<BookmarksEntry> {

	@Override
	public BookmarksEntry addStagedModel(
			BookmarksEntry bookmarksEntry, Map<String, Object> attributes)
		throws Exception {

		return null;
	}

	@Override
	public List<? extends StagedModel> fetchChildStagedModels(
		BookmarksEntry bookmarksEntry) {

		return Collections.emptyList();
	}

	@Override
	public List<? extends StagedModel> fetchDependentStagedModels(
		BookmarksEntry bookmarksEntry) {

		List<BookmarksFolder> folders = new ArrayList<>();

		if (bookmarksEntry.getFolderId() !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			try {
				folders.add(bookmarksEntry.getFolder());
			}
			catch (PortalException pe) {
			}
		}

		return folders;
	}

	@Override
	public BookmarksEntry fetchStagedModelByCustomAttributes(
		Map<String, Object> attributes) {

		return null;
	}

	@Override
	public BookmarksEntry fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<BookmarksEntry> entries =
			_bookmarksEntryLocalService.getBookmarksEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<BookmarksEntry>());

		if (ListUtil.isEmpty(entries)) {
			return null;
		}

		return entries.get(0);
	}

	@Override
	public BookmarksEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _bookmarksEntryLocalService.fetchBookmarksEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<BookmarksEntry> fetchStagedModels(long groupId) {
		DynamicQuery dynamicQuery = _bookmarksEntryLocalService.dynamicQuery();

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(groupId));

		return _bookmarksEntryLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public List<BookmarksEntry> fetchStagedModels(
		PortletDataContext portletDataContext) {

		DynamicQuery dynamicQuery = _bookmarksEntryLocalService.dynamicQuery();

		portletDataContext.addDateRangeCriteria(dynamicQuery, "modifiedDate");

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(
			companyIdProperty.eq(portletDataContext.getCompanyId()));

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(portletDataContext.getGroupId()));

		StagedModelDataHandler<BookmarksEntry> stagedModelDataHandler =
			(StagedModelDataHandler<BookmarksEntry>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					BookmarksEntry.class.getName());

		Property workflowStatusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(
			workflowStatusProperty.in(
				stagedModelDataHandler.getExportableStatuses()));

		return _bookmarksEntryLocalService.dynamicQuery(dynamicQuery);
	}

	@Reference(unbind = "-")
	public void setBookmarksEntryLocalService(
		BookmarksEntryLocalService bookmarksEntryLocalService) {

		_bookmarksEntryLocalService = bookmarksEntryLocalService;
	}

	@Override
	public BookmarksEntry updateStagedModel(
			BookmarksEntry bookmarksEntry,
			BookmarksEntry existingBookmarksEntry,
			Map<String, Object> attributes)
		throws Exception {

		return null;
	}

	private BookmarksEntryLocalService _bookmarksEntryLocalService;

}