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

package com.liferay.portlet.asset.service.persistence.impl;

import com.liferay.asset.kernel.model.AssetEntryViewStats;
import com.liferay.asset.kernel.service.persistence.AssetEntryViewStatsFinder;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Gergely Mathe
 */
public class AssetEntryViewStatsFinderImpl
	extends AssetEntryViewStatsFinderBaseImpl
		implements AssetEntryViewStatsFinder {

	public static final String COUNT_BY_C_C_GTD_LTD =
		AssetEntryViewStatsFinder.class.getName() + ".countByC_C_LtD_GtD";

	public static final String FIND_BY_C_C_GTD_LTD =
		AssetEntryViewStatsFinder.class.getName() + ".findByC_C_LtD_GtD";

	public long countByC_C_LtD_GtD(
		long classNameId, long classPK, Date startDate, Date endDate) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_C_GTD_LTD);

			SQLQuery q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);
			qPos.add(classPK);
			qPos.add(startDate);
			qPos.add(endDate);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count;
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetEntryViewStats> findByC_C_LtD_GtD(
		long classNameId, long classPK, Date startDate, Date endDate) {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_C_GTD_LTD);

			SQLQuery q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(classNameId);
			qPos.add(classPK);
			qPos.add(startDate);
			qPos.add(endDate);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}
