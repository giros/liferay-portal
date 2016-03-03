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

import com.liferay.journal.lar.JournalCreationStrategy;
import com.liferay.journal.lar.JournalCreationStrategyFactory;

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

		long userId = portletDataContext.getUserId(
			journalArticle.getUserUuid());
		
		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(
			portletDataContext, article);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}
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
	public JournalArticle saveStagedModel(JournalArticle journalArticle)
		throws PortalException {

		return _journalArticleLocalService.updateJournalArticle(journalArticle);
	}

	@Override
	public JournalArticle updateStagedModel(
			PortletDataContext portletDataContext, JournalArticle journalArticle)
		throws PortalException {

	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	private JournalArticleLocalService _journalArticleLocalService;

}