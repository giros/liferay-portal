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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.lar.ExportImportHelperUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.StagedModelRepository;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalService;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;

import java.io.File;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@OSGiBeanProperties(
	property = {"model.name=com.liferay.portlet.journal.model.JournalArticle"})
public class JournalArticleStagedModelRepository
	implements StagedModelRepository<JournalArticle> {

	@Override
	public JournalArticle addStagedModel(
			JournalArticle journalArticle, Map<String, Object> attributes)
		throws Exception {

		// prepareArticle is used to produce all the values the service add
		// method would need

		prepareArticle(journalArticle);

		long userId = 0;
		long groupId = 0; //portletDataContext.getScopeGroupId();
		long folderId = 0;
		long ddmStructureId = 0;
		String articleId = null;
		boolean autoArticleId = true;
		String parentDDMStructureKey = null;
		String parentDDMTemplateKey = null;

		int displayDateMonth = 0;
		int displayDateDay = 0;
		int displayDateYear = 0;
		int displayDateHour = 0;
		int displayDateMinute = 0;
		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;
		boolean neverExpire = true;
		int reviewDateMonth = 0;
		int reviewDateDay = 0;
		int reviewDateYear = 0;
		int reviewDateHour = 0;
		int reviewDateMinute = 0;
		boolean neverReview = true;
		File smallFile = null;
		Map<String, byte[]> images = null;
		String articleURL = null;
		ServiceContext serviceContext = null;

		try {
			return JournalArticleLocalServiceUtil.addArticle(
				userId, groupId, folderId, journalArticle.getClassNameId(),
				ddmStructureId, articleId, autoArticleId,
				journalArticle.getVersion(), journalArticle.getTitleMap(),
				journalArticle.getDescriptionMap(), journalArticle.getContent(),
				parentDDMStructureKey, parentDDMTemplateKey,
				journalArticle.getLayoutUuid(), displayDateMonth,
				displayDateDay, displayDateYear, displayDateHour,
				displayDateMinute, expirationDateMonth, expirationDateDay,
				expirationDateYear, expirationDateHour, expirationDateMinute,
				neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
				reviewDateHour, reviewDateMinute, neverReview,
				journalArticle.isIndexable(), journalArticle.isSmallImage(),
				journalArticle.getSmallImageURL(), smallFile, images,
				articleURL, serviceContext);
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<? extends StagedModel> fetchChildStagedModels(
		JournalArticle journalArticle) {

		return null;
	}

	@Override
	public List<? extends StagedModel> fetchParentStagedModels(
		JournalArticle journalArticle) {

		return null;
	}

	@Override
	public JournalArticle fetchStagedModelByCustomAttributes(
		Map<String, Object> attributes) {

		boolean preloaded = MapUtil.getBoolean(attributes, "preloaded", false);
		String articleUuid = null;
		String articleResourceUuid = null;
		long groupId = 0;
		double version = 0.0;
		String newArticleId = null;
		String articleId = null;

		JournalArticle existingArticle = null;

		if (!preloaded) {
			existingArticle = fetchStagedModelByUuidAndGroupId(
				articleUuid, groupId);

			if (existingArticle != null) {
				return existingArticle;
			}

			// Backwards compatibility

			JournalArticleResource journalArticleResource =
				JournalArticleResourceLocalServiceUtil.
					fetchJournalArticleResourceByUuidAndGroupId(
						articleResourceUuid, groupId);

			if (journalArticleResource == null) {
				return null;
			}

			return JournalArticleLocalServiceUtil.fetchArticle(
				groupId, journalArticleResource.getArticleId(), version);
		}

		if (Validator.isNotNull(newArticleId) && (version > 0.0)) {
			existingArticle = JournalArticleLocalServiceUtil.fetchArticle(
				groupId, newArticleId, version);
		}

		if ((existingArticle == null) && Validator.isNull(newArticleId)) {
			existingArticle = JournalArticleLocalServiceUtil.fetchArticle(
				groupId, articleId, version);
		}

		return existingArticle;
	}

	@Override
	public JournalArticle fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		return null;
	}

	@Override
	public JournalArticle fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return null;
	}

	@Override
	public List<JournalArticle> fetchStagedModels(long groupId) {
		return null;
	}

	@Override
	public List<JournalArticle> fetchStagedModels(
		PortletDataContext portletDataContext) {

		return null;
	}

	@Reference(unbind = "-")
	public void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	public void setJournalArticleResourceLocalService(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {

		_journalArticleResourceLocalService =
			journalArticleResourceLocalService;
	}

	@Override
	public JournalArticle updateStagedModel(
			JournalArticle journalArticle,
			JournalArticle existingJournalArticle,
			Map<String, Object> attributes)
		throws Exception {

		prepareArticle(journalArticle);

		long userId = 0;
		long groupId = 0; //portletDataContext.getScopeGroupId();
		long folderId = 0;
		long ddmStructureId = 0;
		String articleId = null;
		boolean autoArticleId = true;
		String parentDDMStructureKey = null;
		String parentDDMTemplateKey = null;

		int displayDateMonth = 0;
		int displayDateDay = 0;
		int displayDateYear = 0;
		int displayDateHour = 0;
		int displayDateMinute = 0;
		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;
		boolean neverExpire = true;
		int reviewDateMonth = 0;
		int reviewDateDay = 0;
		int reviewDateYear = 0;
		int reviewDateHour = 0;
		int reviewDateMinute = 0;
		boolean neverReview = true;
		File smallFile = null;
		Map<String, byte[]> images = null;
		String articleURL = null;
		ServiceContext serviceContext = null;

		return JournalArticleLocalServiceUtil.updateArticle(
			userId, existingJournalArticle.getGroupId(), folderId,
			existingJournalArticle.getArticleId(), journalArticle.getVersion(),
			journalArticle.getTitleMap(), journalArticle.getDescriptionMap(),
			journalArticle.getContent(), parentDDMStructureKey,
			parentDDMTemplateKey, journalArticle.getLayoutUuid(),
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview,
			journalArticle.isIndexable(), journalArticle.isSmallImage(),
			journalArticle.getSmallImageURL(), smallFile, images, articleURL,
			serviceContext);
	}

	protected void calculateDate(Date date, TimeZone timeZone) {
		int month = 0;
		int day = 0;
		int year = 0;
		int hour = 0;
		int minute = 0;

		if (date != null) {
			Calendar calendar = CalendarFactoryUtil.getCalendar(timeZone);

			calendar.setTime(date);

			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DATE);
			year = calendar.get(Calendar.YEAR);
			hour = calendar.get(Calendar.HOUR);
			minute = calendar.get(Calendar.MINUTE);

			if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
				hour += 12;
			}
		}
	}

	protected void prepareArticle(JournalArticle journalArticle)
		throws Exception {

		PortletDataContext portletDataContext = null;

		long userId = portletDataContext.getUserId(
			journalArticle.getUserUuid());

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(
			portletDataContext, journalArticle);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		User user = UserLocalServiceUtil.fetchUser(userId);

		String content = journalArticle.getContent();

		try {
			content = ExportImportHelperUtil.replaceImportContentReferences(
				portletDataContext, journalArticle, content);
		}
		catch (Exception e) {
			journalArticle.setContent(content);
		}

		String newContent = creationStrategy.getTransformedContent(
			portletDataContext, journalArticle);

		if (newContent != JournalCreationStrategy.ARTICLE_CONTENT_UNCHANGED) {
			journalArticle.setContent(newContent);
		}

		long folderId = portletDataContext.getNewPrimaryKey(
			JournalFolder.class, journalArticle.getFolderId());

		String articleId = journalArticle.getArticleId();

		boolean autoArticleId = false;

		if (Validator.isNumber(articleId) ||
			(JournalArticleLocalServiceUtil.fetchArticle(
				portletDataContext.getScopeGroupId(), articleId,
				JournalArticleConstants.VERSION_DEFAULT) != null)) {

			autoArticleId = true;
		}

		Map<String, String> articleIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class + ".articleId");

		String newArticleId = articleIds.get(articleId);

		if (Validator.isNotNull(newArticleId)) {

			// A sibling of a different version was already assigned a new
			// article id

			articleId = newArticleId;
			autoArticleId = false;
		}

		calculateDate(journalArticle.getDisplayDate(), user.getTimeZone());

		Date expirationDate = journalArticle.getExpirationDate();
		boolean neverExpire = true;

		if (expirationDate != null) {
			calculateDate(
				journalArticle.getExpirationDate(), user.getTimeZone());

			neverExpire = false;
		}

		Date reviewDate = journalArticle.getReviewDate();
		boolean neverReview = true;

		if (reviewDate != null) {
			calculateDate(journalArticle.getReviewDate(), user.getTimeZone());

			neverReview = false;
		}

		String parentDDMStructureKey = portletDataContext.getNewPrimaryKey(
			DDMStructure.class + ".ddmStructureKey",
			journalArticle.getDDMStructureKey());

		Map<String, Long> ddmStructureIds =
			(Map<String, Long>)portletDataContext.getNewPrimaryKeysMap(
				DDMStructure.class);

		long ddmStructureId = 0;

		if (journalArticle.getClassNameId() != 0) {
			ddmStructureId = ddmStructureIds.get(journalArticle.getClassPK());
		}

		String parentDDMTemplateKey = portletDataContext.getNewPrimaryKey(
			DDMTemplate.class + ".ddmTemplateKey",
			journalArticle.getDDMTemplateKey());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			journalArticle);

		File smallFile = null;
		Map<String, byte[]> images = null;
	}

	private JournalArticleLocalService _journalArticleLocalService;
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

}