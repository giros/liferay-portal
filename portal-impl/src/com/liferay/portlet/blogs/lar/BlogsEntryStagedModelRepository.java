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

package com.liferay.portlet.blogs.lar;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.lar.StagedModelDataHandlerRegistryUtil;
import com.liferay.portal.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.portal.kernel.lar.StagedModelRepository;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalService;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@OSGiBeanProperties(
	property = {"model.name=com.liferay.portlet.blogs.model.BlogsEntry"})
public class BlogsEntryStagedModelRepository
	implements StagedModelRepository<BlogsEntry> {

	@Override
	public BlogsEntry addStagedModel(
			BlogsEntry entry, Map<String, Object> attributes)
		throws Exception {

		return doAddUpdateEntry(entry, null, attributes);
	}

	@Override
	public List<? extends StagedModel> fetchChildStagedModels(
		BlogsEntry entry) {

		return Collections.emptyList();
	}

	@Override
	public List<? extends StagedModel> fetchDependentStagedModels(
		BlogsEntry entry) {

		List<StagedModel> dependentStageModels = new ArrayList<>();

		if (entry.getSmallImageFileEntryId() != 0) {
			FileEntry fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				entry.getSmallImageFileEntryId());

			dependentStageModels.add(fileEntry);
		}

		return dependentStageModels;
	}

	@Override
	public BlogsEntry fetchStagedModelByCustomAttributes(
		Map<String, Object> attributes) {

		return null;
	}

	@Override
	public BlogsEntry fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		List<BlogsEntry> entries =
			_blogsEntryLocalService.getBlogsEntriesByUuidAndCompanyId(
				uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new StagedModelModifiedDateComparator<BlogsEntry>());

		if (ListUtil.isEmpty(entries)) {
			return null;
		}

		return entries.get(0);
	}

	@Override
	public BlogsEntry fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _blogsEntryLocalService.fetchBlogsEntryByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<BlogsEntry> fetchStagedModels(long groupId) {
		DynamicQuery dynamicQuery = _blogsEntryLocalService.dynamicQuery();

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(groupId));

		return _blogsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	@Override
	public List<BlogsEntry> fetchStagedModels(
		PortletDataContext portletDataContext) {

		DynamicQuery dynamicQuery = _blogsEntryLocalService.dynamicQuery();

		portletDataContext.addDateRangeCriteria(dynamicQuery, "modifiedDate");

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(
			companyIdProperty.eq(portletDataContext.getCompanyId()));

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(portletDataContext.getGroupId()));

		StagedModelDataHandler<BlogsEntry> stagedModelDataHandler =
			(StagedModelDataHandler<BlogsEntry>)
				StagedModelDataHandlerRegistryUtil.getStagedModelDataHandler(
					BlogsEntry.class.getName());

		Property workflowStatusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(
			workflowStatusProperty.in(
				stagedModelDataHandler.getExportableStatuses()));

		return _blogsEntryLocalService.dynamicQuery(dynamicQuery);
	}

	@Reference(unbind = "-")
	public void setBlogsEntryLocalService(
		BlogsEntryLocalService blogsEntryLocalService) {

		_blogsEntryLocalService = blogsEntryLocalService;
	}

	@Override
	public BlogsEntry updateStagedModel(
			BlogsEntry entry, BlogsEntry existingEntry,
			Map<String, Object> attributes)
		throws Exception {

		return doAddUpdateEntry(entry, existingEntry, attributes);
	}

	protected InputStream getSmallImageInputStream(
		PortletDataContext portletDataContext, Element attachmentElement) {

		InputStream inputStream = null;

		String path = attachmentElement.attributeValue("path");

		FileEntry fileEntry = (FileEntry)portletDataContext.getZipEntryAsObject(
			path);

		String binPath = attachmentElement.attributeValue("bin-path");

		if (Validator.isNull(binPath) &&
			portletDataContext.isPerformDirectBinaryImport()) {

			try {
				inputStream = FileEntryUtil.getContentStream(fileEntry);
			}
			catch (Exception e) {
			}
		}
		else {
			inputStream = portletDataContext.getZipEntryAsInputStream(binPath);
		}

		if (inputStream == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to import small image file entry " +
						fileEntry.getFileEntryId());
			}
		}

		return inputStream;
	}

	protected long getSmallImageFileEntryId(
			BlogsEntry entry, PortletDataContext portletDataContext,
			ServiceContext serviceContext) 
		throws Exception {

		long smallImageFileEntryId = 0;

		Element entryElement =
			portletDataContext.getImportDataStagedModelElement(entry);

		long userId = portletDataContext.getUserId(entry.getUserUuid());
		
		if (entry.isSmallImage()) {
			String smallImagePath = entryElement.attributeValue(
				"small-image-path");

			if (Validator.isNotNull(entry.getSmallImageURL())) {
				String smallImageURL =
					ExportImportHelperUtil.replaceImportContentReferences(
						portletDataContext, entry, entry.getSmallImageURL());

				entry.setSmallImageURL(smallImageURL);
			}
			else if (Validator.isNotNull(smallImagePath)) {
				String smallImageFileName =
					entry.getSmallImageId() + StringPool.PERIOD +
						entry.getSmallImageType();

				InputStream inputStream = null;

				try {
					inputStream = portletDataContext.getZipEntryAsInputStream(
						smallImagePath);

					FileEntry fileEntry = TempFileEntryUtil.addTempFileEntry(
						serviceContext.getScopeGroupId(), userId,
						BlogsEntry.class.getName(), smallImageFileName,
						inputStream,
						MimeTypesUtil.getContentType(smallImageFileName));

					smallImageFileEntryId = fileEntry.getFileEntryId();
				}
				finally {
					StreamUtil.cleanUp(inputStream);
				}
			}
		}

		if (smallImageFileEntryId == 0) {
			List<Element> attachmentElements =
				portletDataContext.getReferenceDataElements(
					entry, DLFileEntry.class,
					PortletDataContext.REFERENCE_TYPE_WEAK);

			for (Element attachmentElement : attachmentElements) {
				InputStream inputStream = getSmallImageInputStream(
					portletDataContext, attachmentElement);

				if (inputStream != null) {
					String path = attachmentElement.attributeValue("path");

					FileEntry fileEntry =
						(FileEntry)portletDataContext.getZipEntryAsObject(path);

					FileEntry smallImageFileEntry =
						TempFileEntryUtil.addTempFileEntry(
							serviceContext.getScopeGroupId(), userId,
							BlogsEntry.class.getName(), fileEntry.getTitle(),
							inputStream, fileEntry.getMimeType());

					if (fileEntry != null) {
						smallImageFileEntryId =
							smallImageFileEntry.getFileEntryId();
					}
				}
			}
		}

		return smallImageFileEntryId;
	}

	protected BlogsEntry doAddUpdateEntry(
			BlogsEntry entry, BlogsEntry existingEntry,
			Map<String, Object> attributes)
		throws Exception {

		PortletDataContext portletDataContext =
			(PortletDataContext)attributes.get("portletDataContext");

		String content = ExportImportHelperUtil.replaceImportContentReferences(
			portletDataContext, entry, entry.getContent());

		entry.setContent(content);

		long userId = portletDataContext.getUserId(entry.getUserUuid());

		Calendar displayDateCal = CalendarFactoryUtil.getCalendar();

		displayDateCal.setTime(entry.getDisplayDate());

		int displayDateMonth = displayDateCal.get(Calendar.MONTH);
		int displayDateDay = displayDateCal.get(Calendar.DATE);
		int displayDateYear = displayDateCal.get(Calendar.YEAR);
		int displayDateHour = displayDateCal.get(Calendar.HOUR);
		int displayDateMinute = displayDateCal.get(Calendar.MINUTE);

		if (displayDateCal.get(Calendar.AM_PM) == Calendar.PM) {
			displayDateHour += 12;
		}

		boolean allowPingbacks = entry.isAllowPingbacks();
		boolean allowTrackbacks = entry.isAllowTrackbacks();
		String[] trackbacks = StringUtil.split(entry.getTrackbacks());

		ServiceContext serviceContext =
			(ServiceContext)attributes.get("serviceContext");

		long smallImageFileEntryId = getSmallImageFileEntryId(
			entry, portletDataContext, serviceContext);

		ImageSelector coverImageImageSelector = new ImageSelector(
			smallImageFileEntryId, entry.getCoverImageURL(), null);

		ImageSelector smallImageImageSelector = null;

		if (!entry.isSmallImage()) {
			smallImageImageSelector = new ImageSelector(0);
		}
		else {
			smallImageImageSelector = new ImageSelector(
				smallImageFileEntryId, entry.getSmallImageURL(), null);
		}

		if (existingEntry == null) {
			return BlogsEntryLocalServiceUtil.addEntry(
				userId, entry.getTitle(), entry.getSubtitle(),
				entry.getDescription(), entry.getContent(),
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, allowPingbacks,
				allowTrackbacks, trackbacks, entry.getCoverImageCaption(),
				coverImageImageSelector, smallImageImageSelector,
				serviceContext);
		}
		else {
			return BlogsEntryLocalServiceUtil.updateEntry(
				userId, existingEntry.getEntryId(), entry.getTitle(),
				entry.getSubtitle(), entry.getDescription(),
				entry.getContent(), displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				allowPingbacks, allowTrackbacks, trackbacks,
				entry.getCoverImageCaption(), coverImageImageSelector,
				smallImageImageSelector, serviceContext);
		}
	}

	private BlogsEntryLocalService _blogsEntryLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
			BlogsEntryStagedModelRepository.class);
}
