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

package com.liferay.journal.exportimport.staged.model.repository;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.StagedModelModifiedDateComparator;
import com.liferay.exportimport.staged.model.repository.StagedModelRepository;
import com.liferay.exportimport.staged.model.repository.base.BaseStagedModelRepository;
import com.liferay.journal.lar.JournalCreationStrategy;
import com.liferay.journal.lar.JournalCreationStrategyFactory;
import com.liferay.journal.model.adapter.StagedJournalArticle;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Element;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.journal.model.JournalArticle"},
	service = StagedModelRepository.class
)
public class JournalArticleStagedModelRepository
	extends BaseStagedModelRepository<JournalArticle> {

	@Override
	public JournalArticle addStagedModel(
			PortletDataContext portletDataContext,
			JournalArticle journalArticle)
		throws PortalException {

		return addStagedModel(
			portletDataContext, new StagedJournalArticle(journalArticle));
	}

	@Override
	public JournalArticle addStagedModel(
			PortletDataContext portletDataContext,
			StagedJournalArticle journalArticle)
		throws PortalException {

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long userId = getUserId(
			portletDataContext, journalArticle, creationStrategy);

		journalArticle.setContent(
			getNewContent(
				portletDataContext, journalArticle, creationStrategy));

		User user = _userLocalService.getUser(userId);

		Map<String, Integer> displayDateComponents = getDateComponents(
			journalArticle, user.getTimeZone(), "displayDate");

		Map<String, Integer> expirationDateComponents = getDateComponents(
			journalArticle, user.getTimeZone(), "expirationDate");
	
		Map<String, Integer> reviewDateComponents = getDateComponents(
			journalArticle, user.getTimeZone(), "reviewDate");

		boolean neverExpire = true;

		Date expirationDate = journalArticle.getExpirationDate();

		if (expirationDate != null) {
			neverExpire = false;
		}

		boolean neverReview = true;

		if (journalArticle.getReviewDate() != null) {
			neverReview = false;
		}

		if ((expirationDate != null) && expirationDate.before(new Date())) {
			journalArticle.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		ServiceContext serviceContext =
			getServiceContext(
				portletDataContext, journalArticle, creationStrategy);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(journalArticle.getUuid());
			serviceContext.setAttribute(
				"articleResourceUuid",
				journalArticle.getArticleResourceUuid());
			serviceContext.setAttribute(
				"urlTitle", journalArticle.getUrlTitle());
		}

		return _journalArticleLocalService.addArticle(
			userId, portletDataContext.getScopeGroupId(),
			journalArticle.getFolderId(), journalArticle.getClassNameId(),
			journalArticle.getClassPK(), journalArticle.getArticleId(),
			journalArticle.getAutoArticleId(), journalArticle.getVersion(),
			journalArticle.getTitleMap(), journalArticle.getDescriptionMap(),
			journalArticle.getContent(), journalArticle.getDDMStructureKey(),
			journalArticle.getDDMTemplateKey(), journalArticle.getLayoutUuid(),
			displayDateComponents.get("month"),
			displayDateComponents.get("day"), displayDateComponents.get("year"),
			displayDateComponents.get("hour"),
			displayDateComponents.get("minute"),
			expirationDateComponents.get("month"),
			expirationDateComponents.get("day"),
			expirationDateComponents.get("year"),
			expirationDateComponents.get("hour"),
			expirationDateComponents.get("minute"),
			neverExpire, reviewDateComponents.get("month"),
			reviewDateComponents.get("day"), reviewDateComponents.get("year"),
			reviewDateComponents.get("hour"),
			reviewDateComponents.get("minute"), neverReview,
			journalArticle.isIndexable(), journalArticle.isSmallImage(),
			journalArticle.getSmallImageURL(), journalArticle.getSmallFile(),
			journalArticle.getImages(), null, serviceContext);
	}

	@Override
	public void deleteStagedModel(JournalArticle journalArticle)
		throws PortalException {

		_journalArticleLocalService.deleteJournalArticle(journalArticle);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JournalArticle journalArticle =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (journalArticle != null) {
			deleteStagedModel(journalArticle);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {

		_journalArticleLocalService.deleteArticles(
			portletDataContext.getScopeGroupId());
	}
	
	public JournalArticle fetchExistingArticle(
		String articleResourceUuid, long groupId, String articleId,
		String newArticleId, boolean preloaded) {

		JournalArticle existingArticle = null;

		if (!preloaded) {
			JournalArticleResource journalArticleResource =
				_journalArticleResourceLocalService.
					fetchJournalArticleResourceByUuidAndGroupId(
						articleResourceUuid, groupId);

			if (journalArticleResource == null) {
				return null;
			}

			return _journalArticleLocalService.fetchArticle(
				groupId, journalArticleResource.getArticleId());
		}

		if (Validator.isNotNull(newArticleId)) {
			existingArticle = _journalArticleLocalService.fetchArticle(
				groupId, newArticleId);
		}

		if ((existingArticle == null) && Validator.isNull(newArticleId)) {
			existingArticle = _journalArticleLocalService.fetchArticle(
				groupId, articleId);
		}

		return existingArticle;
	}

	public JournalArticle fetchExistingArticle(
		String articleUuid, String articleResourceUuid, long groupId,
		String articleId, String newArticleId, double version,
		boolean preloaded) {

		JournalArticle article = fetchExistingArticle(
			articleResourceUuid, groupId, articleId, newArticleId, preloaded);

		if (article != null) {
			article = fetchExistingArticleVersion(
				articleUuid, groupId, article.getArticleId(), version);
		}

		return article;
	}

	public JournalArticle fetchExistingArticleVersion(
		String articleUuid, long groupId, String articleId, double version) {

		JournalArticle existingArticle = fetchStagedModelByUuidAndGroupId(
			articleUuid, groupId);

		if (existingArticle != null) {
			return existingArticle;
		}

		return _journalArticleLocalService.fetchArticle(
			groupId, articleId, version);
	}

	@Override
	public JournalArticle fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _journalArticleLocalService.fetchJournalArticleByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<JournalArticle> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _journalArticleLocalService.getJournalArticlesByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<JournalArticle>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _journalArticleLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public void restoreStagedModel(
			PortletDataContext portletDataContext,
			JournalArticle journalArticle)
		throws PortletDataException {
		
		long userId = portletDataContext.getUserId(
			journalArticle.getUserUuid());

		Element articleElement =
			portletDataContext.getImportDataStagedModelElement(journalArticle);

		String articleResourceUuid = articleElement.attributeValue(
			"article-resource-uuid");

		boolean preloaded = GetterUtil.getBoolean(
			articleElement.attributeValue("preloaded"));

		JournalArticle existingArticle = fetchExistingArticle(
			journalArticle.getUuid(), articleResourceUuid,
			portletDataContext.getScopeGroupId(), journalArticle.getArticleId(),
			journalArticle.getArticleId(), journalArticle.getVersion(),
			preloaded);

		if ((existingArticle == null) || !existingArticle.isInTrash()) {
			return;
		}

		TrashHandler trashHandler = existingArticle.getTrashHandler();

		if (trashHandler.isRestorable(existingArticle.getResourcePrimKey())) {
			trashHandler.restoreTrashEntry(
				userId, existingArticle.getResourcePrimKey());
		}
	}

	@Override
	public JournalArticle saveStagedModel(JournalArticle journalArticle)
		throws PortalException {

		return _journalArticleLocalService.updateJournalArticle(journalArticle);
	}

	@Override
	public JournalArticle updateStagedModel(
			PortletDataContext portletDataContext,
			JournalArticle journalArticle)
		throws PortalException {

		return updateStagedModel(
			portletDataContext, new StagedJournalArticle(journalArticle));
	}

	@Override
	public JournalArticle updateStagedModel(
			PortletDataContext portletDataContext,
			StagedJournalArticle journalArticle)
		throws PortalException {
		
		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long userId = getUserId(
			portletDataContext, journalArticle, creationStrategy);

		journalArticle.setContent(
			getNewContent(
				portletDataContext, journalArticle, creationStrategy));

		User user = _userLocalService.getUser(userId);

		Map<String, Integer> displayDateComponents = getDateComponents(
			journalArticle, user.getTimeZone(), "displayDate");

		Map<String, Integer> expirationDateComponents = getDateComponents(
			journalArticle, user.getTimeZone(), "expirationDate");
	
		Map<String, Integer> reviewDateComponents = getDateComponents(
			journalArticle, user.getTimeZone(), "reviewDate");

		boolean neverExpire = true;

		Date expirationDate = journalArticle.getExpirationDate();

		if (expirationDate != null) {
			neverExpire = false;
		}

		boolean neverReview = true;

		if (journalArticle.getReviewDate() != null) {
			neverReview = false;
		}

		if ((expirationDate != null) && expirationDate.before(new Date())) {
			journalArticle.setStatus(WorkflowConstants.STATUS_EXPIRED);
		}

		ServiceContext serviceContext =
			getServiceContext(
				portletDataContext, journalArticle, creationStrategy);

		return _journalArticleLocalService.updateArticle(
			userId, journalArticle.getGroupId(), journalArticle.getFolderId(),
			journalArticle.getArticleId(), journalArticle.getVersion(),
			journalArticle.getTitleMap(), journalArticle.getDescriptionMap(),
			journalArticle.getContent(), journalArticle.getDDMStructureKey(),
			journalArticle.getDDMTemplateKey(), journalArticle.getLayoutUuid(),
			displayDateComponents.get("month"),
			displayDateComponents.get("day"), displayDateComponents.get("year"),
			displayDateComponents.get("hour"),
			displayDateComponents.get("minute"),
			expirationDateComponents.get("month"),
			expirationDateComponents.get("day"),
			expirationDateComponents.get("year"),
			expirationDateComponents.get("hour"),
			expirationDateComponents.get("minute"),
			neverExpire, reviewDateComponents.get("month"),
			reviewDateComponents.get("day"), reviewDateComponents.get("year"),
			reviewDateComponents.get("hour"),
			reviewDateComponents.get("minute"), neverReview,
			journalArticle.isIndexable(), journalArticle.isSmallImage(),
			journalArticle.getSmallImageURL(), journalArticle.getSmallFile(),
			journalArticle.getImages(), null, serviceContext);
	}
	
	protected Map<String, Integer> getDateComponents(
		JournalArticle journalArticle, TimeZone timeZone,
		String requestedDate) {

		Map<String, Integer> dateComponents = new HashMap<>();

		Date date;

		if (requestedDate.equals("displayDate")) {
			date = journalArticle.getDisplayDate();
		}
		else if (requestedDate.equals("expirationDate")) {
			date = journalArticle.getExpirationDate();
		}
		else if (requestedDate.equals("reviewDate")) {
			date = journalArticle.getReviewDate();
		}
		else {
			return dateComponents;
		}

		int month = 0;
		int day = 0;
		int year = 0;
		int hour = 0;
		int minute = 0;

		if (date != null) {
			Calendar displayCal = CalendarFactoryUtil.getCalendar(timeZone);

			displayCal.setTime(date);

			month = displayCal.get(Calendar.MONTH);
			day = displayCal.get(Calendar.DATE);
			year = displayCal.get(Calendar.YEAR);
			hour = displayCal.get(Calendar.HOUR);
			minute = displayCal.get(Calendar.MINUTE);

			if (displayCal.get(Calendar.AM_PM) == Calendar.PM) {
				hour += 12;
			}
		}

		dateComponents.put("month", month);
		dateComponents.put("day", day);
		dateComponents.put("year", year);
		dateComponents.put("hour", hour);
		dateComponents.put("minute", minute);

		return dateComponents;
	}

	protected String getNewContent(
		PortletDataContext portletDataContext, JournalArticle journalArticle,
		JournalCreationStrategy creationStrategy) {

		String newContent = creationStrategy.getTransformedContent(
			portletDataContext, journalArticle);

		if (newContent != JournalCreationStrategy.ARTICLE_CONTENT_UNCHANGED) {
			return newContent;
		}

		return journalArticle.getContent();
	}
	
	protected ServiceContext getServiceContext(
		PortletDataContext portletDataContext, JournalArticle journalArticle,
		JournalCreationStrategy creationStrategy) {

		boolean addGroupPermissions = creationStrategy.addGroupPermissions(
			portletDataContext, journalArticle);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			portletDataContext, journalArticle);

		ServiceContext serviceContext =
			portletDataContext.createServiceContext(journalArticle);

		serviceContext.setAddGroupPermissions(addGroupPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);

		if ((journalArticle.getStatus() != WorkflowConstants.STATUS_APPROVED) &&
			(journalArticle.getStatus() !=
				WorkflowConstants.STATUS_SCHEDULED)) {

			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		return serviceContext;
	}

	protected long getUserId(
		PortletDataContext portletDataContext, JournalArticle journalArticle,
		JournalCreationStrategy creationStrategy) {

		long userId = portletDataContext.getUserId(
			journalArticle.getUserUuid());

		long authorId = creationStrategy.getAuthorUserId(
			portletDataContext, journalArticle);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		return userId;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalArticleResourceLocalService(
		JournalArticleResourceLocalService journalArticleResourceLocalService) {

		_journalArticleResourceLocalService =
			journalArticleResourceLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private JournalArticleLocalService _journalArticleLocalService;
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;
	private UserLocalService _userLocalService;

}