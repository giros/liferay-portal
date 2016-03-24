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
import com.liferay.journal.model.JournalFeed;
import com.liferay.journal.service.JournalFeedLocalService;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.journal.model.JournalFeed"},
	service = StagedModelRepository.class
)
public class JournalFeedStagedModelRepository
	extends BaseStagedModelRepository<JournalFeed> {

	@Override
	public JournalFeed addStagedModel(
			PortletDataContext portletDataContext,
			JournalFeed journalFeed)
		throws PortalException {

		long userId = portletDataContext.getUserId(journalFeed.getUserUuid());

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = 0;

		try {
			authorId = creationStrategy.getAuthorUserId(
				portletDataContext, journalFeed);
		}
		catch (Exception e) {
			throw new PortalException(e);
		}

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		String feedId = journalFeed.getFeedId();

		boolean autoFeedId = false;

		if (Validator.isNumber(feedId) ||
			(_journalFeedLocalService.fetchFeed(
				portletDataContext.getScopeGroupId(), feedId) != null)) {

			autoFeedId = true;
		}

		ServiceContext serviceContext = getServiceContext(
			portletDataContext, journalFeed);

		if (portletDataContext.isDataStrategyMirror()) {
			serviceContext.setUuid(journalFeed.getUuid());
		}

		return _journalFeedLocalService.addFeed(
			userId, portletDataContext.getScopeGroupId(), feedId, autoFeedId,
			journalFeed.getName(), journalFeed.getDescription(),
			journalFeed.getDDMStructureKey(), journalFeed.getDDMTemplateKey(),
			journalFeed.getDDMRendererTemplateKey(), journalFeed.getDelta(),
			journalFeed.getOrderByCol(), journalFeed.getOrderByType(),
			journalFeed.getTargetLayoutFriendlyUrl(),
			journalFeed.getTargetPortletId(), journalFeed.getContentField(),
			journalFeed.getFeedFormat(), journalFeed.getFeedVersion(),
			serviceContext);
	}

	@Override
	public void deleteStagedModel(JournalFeed journalFeed)
		throws PortalException {

		_journalFeedLocalService.deleteFeed(journalFeed);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		JournalFeed journalFeed =
			fetchStagedModelByUuidAndGroupId(uuid, groupId);

		if (journalFeed != null) {
			deleteStagedModel(journalFeed);
		}
	}

	@Override
	public void deleteStagedModels(PortletDataContext portletDataContext)
		throws PortalException {
	}

	@Override
	public JournalFeed fetchStagedModelByUuidAndGroupId(
		String uuid, long groupId) {

		return _journalFeedLocalService.fetchJournalFeedByUuidAndGroupId(
			uuid, groupId);
	}

	@Override
	public List<JournalFeed> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		return _journalFeedLocalService.getJournalFeedsByUuidAndCompanyId(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new StagedModelModifiedDateComparator<JournalFeed>());
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		PortletDataContext portletDataContext) {

		return _journalFeedLocalService.getExportActionableDynamicQuery(
			portletDataContext);
	}

	@Override
	public JournalFeed saveStagedModel(JournalFeed journalFeed)
		throws PortalException {

		return _journalFeedLocalService.updateJournalFeed(journalFeed);
	}

	@Override
	public JournalFeed updateStagedModel(
			PortletDataContext portletDataContext, JournalFeed journalFeed)
		throws PortalException {

		ServiceContext serviceContext = getServiceContext(
			portletDataContext, journalFeed);

		return _journalFeedLocalService.updateFeed(
			journalFeed.getGroupId(), journalFeed.getFeedId(),
			journalFeed.getName(), journalFeed.getDescription(),
			journalFeed.getDDMStructureKey(), journalFeed.getDDMTemplateKey(),
			journalFeed.getDDMRendererTemplateKey(), journalFeed.getDelta(),
			journalFeed.getOrderByCol(), journalFeed.getOrderByType(),
			journalFeed.getTargetLayoutFriendlyUrl(),
			journalFeed.getTargetPortletId(), journalFeed.getContentField(),
			journalFeed.getFeedFormat(), journalFeed.getFeedVersion(),
			serviceContext);
	}

	protected ServiceContext getServiceContext(
		PortletDataContext portletDataContext, JournalFeed journalFeed) {

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			journalFeed);

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		boolean addGroupPermissions = creationStrategy.addGroupPermissions(
			portletDataContext, journalFeed);

		serviceContext.setAddGroupPermissions(addGroupPermissions);

		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			portletDataContext, journalFeed);

		serviceContext.setAddGuestPermissions(addGuestPermissions);

		return serviceContext;
	}

	@Reference(unbind = "-")
	protected void setJournalFeedLocalService(
		JournalFeedLocalService journalFeedLocalService) {

		_journalFeedLocalService = journalFeedLocalService;
	}

	private JournalFeedLocalService _journalFeedLocalService;

}