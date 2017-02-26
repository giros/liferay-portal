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

package com.liferay.portlet.asset.service.impl;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetEntryViewStats;
import com.liferay.asset.kernel.model.AssetEntryViewStatsConstants;
import com.liferay.asset.kernel.model.AssetEntryViewStatsResult;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portlet.asset.service.base.AssetEntryViewStatsLocalServiceBaseImpl;

import aQute.bnd.annotation.ProviderType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The implementation of the asset entry view stats local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.asset.kernel.service.AssetEntryViewStatsLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryViewStatsLocalServiceBaseImpl
 * @see com.liferay.asset.kernel.service.AssetEntryViewStatsLocalServiceUtil
 */
@ProviderType
public class AssetEntryViewStatsLocalServiceImpl
	extends AssetEntryViewStatsLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link com.liferay.asset.kernel.service.AssetEntryViewStatsLocalServiceUtil} to access the asset entry view stats local service.
	 */

	public AssetEntryViewStats addAssetEntryViewStats(
		long userId, long companyId, long groupId, String className,
		long classPK, long assetEntryId) {

		long statsId = counterLocalService.increment();

		AssetEntryViewStats stats = assetEntryViewStatsPersistence.create(
			statsId);

		stats.setUserId(userId);
		stats.setGroupId(groupId);
		stats.setCompanyId(companyId);
		stats.setCreateDate(new Date());
		stats.setClassNameId(classNameLocalService.getClassNameId(className));
		stats.setClassPK(classPK);
		stats.setAssetEntryId(assetEntryId);

		assetEntryViewStatsPersistence.update(stats);

		return stats;
	}

	public long getViewCountBetweenDates(
		String className, long classPK, Date startDate, Date endDate) {

		return dynamicQueryCount(
			getDateRangeDynamicQuery(className, classPK, startDate, endDate));
	}

	public List<AssetEntryViewStatsResult> getViewCounts(
		String className, long classPK, int type) {

		AssetEntry assetEntry = assetEntryLocalService.fetchEntry(
			className, classPK);

		Calendar createDateCal = CalendarFactoryUtil.getCalendar();
		Calendar startDateCal = CalendarFactoryUtil.getCalendar();
		Calendar endDateCal = CalendarFactoryUtil.getCalendar();

		createDateCal.setTime(assetEntry.getCreateDate());

		int field = Calendar.DAY_OF_MONTH;

		if (type == AssetEntryViewStatsConstants.YEARLY) {
			startDateCal.set(Calendar.MONTH, Calendar.DECEMBER);
			startDateCal.set(Calendar.DAY_OF_MONTH, 31);

			field = Calendar.YEAR;
		}
		else if (type == AssetEntryViewStatsConstants.MONTHLY) {
			startDateCal.set(Calendar.DAY_OF_MONTH, 31);

			field = Calendar.MONTH;
		}
		else if (type == AssetEntryViewStatsConstants.WEEKLY) {
			startDateCal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

			field = Calendar.WEEK_OF_MONTH;
		}

		startDateCal.set(Calendar.HOUR_OF_DAY, 23);
		startDateCal.set(Calendar.MINUTE, 59);
		startDateCal.set(Calendar.SECOND, 59);
		startDateCal.set(Calendar.MILLISECOND, 999);

		endDateCal.setTime(startDateCal.getTime());
		endDateCal.add(field, -1);

		List<AssetEntryViewStatsResult> results = new ArrayList<>();

		while (createDateCal.before(startDateCal)) {
			long count = getViewCountBetweenDates(
				className, classPK, startDateCal.getTime(),
				endDateCal.getTime());

			results.add(
				new AssetEntryViewStatsResult(
					startDateCal.getTime(), count, type));

			startDateCal.add(field, -1);
			endDateCal.add(field, -1);
		}

		return results;
	}

	public List<AssetEntryViewStats> getViewsBetweenDates(
		String className, long classPK, Date startDate, Date endDate) {

		return dynamicQuery(
			getDateRangeDynamicQuery(className, classPK, startDate, endDate));
	}

	protected DynamicQuery getDateRangeDynamicQuery(
		String className, long classPK, Date startDate, Date endDate) {

		long classNameId = classNameLocalService.getClassNameId(className);

		DynamicQuery dateRangeDynamicQuery = dynamicQuery();

		Criterion criterion = RestrictionsFactoryUtil.eq(
			"classNameId", classNameId);

		criterion = RestrictionsFactoryUtil.and(
			criterion, RestrictionsFactoryUtil.eq("classPK", classPK));

		criterion = RestrictionsFactoryUtil.and(
			criterion, RestrictionsFactoryUtil.between(
				"createDate", startDate, endDate));

		dateRangeDynamicQuery.add(criterion);

		return dateRangeDynamicQuery;
	}

}