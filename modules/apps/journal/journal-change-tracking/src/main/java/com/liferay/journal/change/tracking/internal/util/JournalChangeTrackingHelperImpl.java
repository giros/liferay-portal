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

package com.liferay.journal.change.tracking.internal.util;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.engine.CTManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTCollectionModel;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryModel;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalChangeTrackingHelper;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.util.Portal;

import java.util.Optional;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(immediate = true, service = JournalChangeTrackingHelper.class)
public class JournalChangeTrackingHelperImpl
	implements JournalChangeTrackingHelper {

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public String getJournalArticleCTCollectionName(long userId, long classPK) {
		return getJournalArticleCTCollectionName(
			CompanyThreadLocal.getCompanyId(), userId, classPK);
	}

	@Override
	public String getJournalArticleCTCollectionName(
		long companyId, long userId, long id) {

		Optional<CTCollection> originalCTCollectionOptional =
			_getOriginalCTCollectionOptional(companyId, userId, id);

		return originalCTCollectionOptional.map(
			CTCollectionModel::getName
		).orElse(
			StringPool.BLANK
		);
	}

	@Override
	public PortletURL getJournalArticleCTCollectionURL(
		PortletRequest portletRequest, long companyId, long userId, long id) {

		Optional<CTCollection> originalCTCollectionOptional =
			_getOriginalCTCollectionOptional(companyId, userId, id);

		long originalCTCollectionId = originalCTCollectionOptional.map(
			CTCollectionModel::getCtCollectionId
		).orElse(
			0L
		);

		if (originalCTCollectionId == 0) {
			return null;
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			portletRequest, CTPortletKeys.CHANGE_LISTS_HISTORY,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/details.jsp");
		portletURL.setParameter(
			"ctCollectionId", String.valueOf(originalCTCollectionId));
		portletURL.setParameter("orderByCol", "title");
		portletURL.setParameter("orderByType", "desc");

		return portletURL;
	}

	@Override
	public boolean hasActiveCTCollection(long companyId, long userId) {
		Optional<CTCollection> ctCollectionOptional =
			_ctManager.getActiveCTCollectionOptional(companyId, userId);

		return ctCollectionOptional.map(
			ctCollection -> !ctCollection.isProduction()
		).orElse(
			false
		);
	}

	/**
	 * @deprecated As of Mueller (7.2.x)
	 */
	@Deprecated
	@Override
	public boolean isJournalArticleInChangeList(long userId, long classPK) {
		return isJournalArticleInChangeList(
			CompanyThreadLocal.getCompanyId(), userId, classPK);
	}

	@Override
	public boolean isJournalArticleInChangeList(
		long companyId, long userId, long id) {

		long classNameId = _portal.getClassNameId(
			JournalArticle.class.getName());

		long activeCTCollectionId = _getActiveCTCollectionId(companyId, userId);

		Optional<CTEntry> ctEntryOptional =
			_ctManager.getActiveCTCollectionCTEntryOptional(
				companyId, userId, classNameId, id);

		ctEntryOptional = ctEntryOptional.filter(
			ctEntry ->
				ctEntry.getOriginalCTCollectionId() == activeCTCollectionId);

		if (ctEntryOptional.isPresent()) {
			return true;
		}

		return false;
	}

	private long _getActiveCTCollectionId(long companyId, long userId) {
		Optional<CTCollection> ctCollectionOptional =
			_ctManager.getActiveCTCollectionOptional(companyId, userId);

		return ctCollectionOptional.filter(
			ctCollection -> !ctCollection.isProduction()
		).map(
			CTCollection::getCtCollectionId
		).orElse(
			0L
		);
	}

	private Optional<CTCollection> _getOriginalCTCollectionOptional(
		long companyId, long userId, long id) {

		Optional<CTEntry> ctEntryOptional =
			_ctManager.getModelChangeCTEntryOptional(
				companyId, userId,
				_portal.getClassNameId(JournalArticle.class.getName()), id);

		return ctEntryOptional.map(
			CTEntryModel::getOriginalCTCollectionId
		).map(
			_ctCollectionLocalService::fetchCTCollection
		);
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTManager _ctManager;

	@Reference
	private Portal _portal;

}