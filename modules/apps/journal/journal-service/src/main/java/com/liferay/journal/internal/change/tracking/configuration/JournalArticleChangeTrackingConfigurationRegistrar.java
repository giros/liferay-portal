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

package com.liferay.journal.internal.change.tracking.configuration;

import com.liferay.change.tracking.configuration.ChangeTrackingConfigurationRegistrar;
import com.liferay.change.tracking.engine.configuration.ChangeTrackingConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleResourceLocalService;
import com.liferay.journal.service.persistence.JournalArticleResourceUtil;
import com.liferay.journal.service.persistence.JournalArticleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true, service = ChangeTrackingConfigurationRegistrar.class
)
public class JournalArticleChangeTrackingConfigurationRegistrar
	implements ChangeTrackingConfigurationRegistrar
		<JournalArticleResource, JournalArticle> {

	public ChangeTrackingConfiguration changeTrackingConfiguration(
		ChangeTrackingConfiguration.Builder
			<JournalArticleResource, JournalArticle> builder) {

		return builder.addResourceEntity(
			JournalArticleResource.class,
			JournalArticleResourceUtil::fetchByPrimaryKey,
			JournalArticleResource::getResourcePrimKey,
			JournalArticleResource::getLatestArticlePK,
			_journalArticleResourceLocalService
		).addVersionEntity(
			JournalArticle.class, JournalArticleUtil::fetchByPrimaryKey,
			JournalArticle::getResourcePrimKey, JournalArticle::getId,
			_journalArticleLocalService,
			new Integer[]
				{
					WorkflowConstants.STATUS_APPROVED,
					WorkflowConstants.STATUS_DRAFT
				},
			JournalArticle::getStatus
		).build();
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private JournalArticleResourceLocalService
		_journalArticleResourceLocalService;

}