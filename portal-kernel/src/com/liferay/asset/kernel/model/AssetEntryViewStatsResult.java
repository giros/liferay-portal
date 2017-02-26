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

package com.liferay.asset.kernel.model;

import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Gergely Mathe
 */
public class AssetEntryViewStatsResult {

	public AssetEntryViewStatsResult() {
	}

	public AssetEntryViewStatsResult(Date date, long viewCount, int type) {
		_date = date;
		_type = type;
		_viewCount = viewCount;
	}

	public Date getDate() {
		return _date;
	}

	public String getDisplayDate() {
		Calendar calendar = CalendarFactoryUtil.getCalendar();

		calendar.setTime(_date);

		StringBundler sb = new StringBundler(15);

		sb.append(String.valueOf(calendar.get(Calendar.YEAR)));

		if ((_type == AssetEntryViewStatsConstants.MONTHLY) ||
			(_type == AssetEntryViewStatsConstants.DAILY)) {

			sb.append(StringPool.PERIOD + StringPool.SPACE);
			sb.append(getMonthName(calendar.get(Calendar.MONTH)));
		}

		if (_type == AssetEntryViewStatsConstants.WEEKLY) {
			sb.append(StringPool.PERIOD + StringPool.SPACE);
			sb.append(String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)));
			sb.append(StringPool.PERIOD);
		}

		if (_type == AssetEntryViewStatsConstants.DAILY) {
			sb.append(StringPool.SPACE);
			sb.append(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
			sb.append(StringPool.PERIOD);
		}

		return sb.toString();
	}

	public int getType() {
		return _type;
	}

	public long getViewCount() {
		return _viewCount;
	}

	public void setDate(Date date) {
		_date = date;
	}

	public void setType(int type) {
		_type = type;
	}

	public void setViewCount(long viewCount) {
		_viewCount = viewCount;
	}

	protected String getMonthName(int monthNumber) {
		switch (monthNumber) {
			case Calendar.JANUARY: return "Jan";
			case Calendar.FEBRUARY: return "Feb";
			case Calendar.MARCH: return "Mar";
			case Calendar.APRIL: return "Apr";
			case Calendar.MAY: return "May";
			case Calendar.JUNE: return "Jun";
			case Calendar.JULY: return "Jul";
			case Calendar.AUGUST: return "Aug";
			case Calendar.SEPTEMBER: return "Sep";
			case Calendar.OCTOBER: return "Oct";
			case Calendar.NOVEMBER: return "Nov";
			case Calendar.DECEMBER: return "Dec";
			default: return "???";
		}
	}

	private Date _date;
	private int _type;
	private long _viewCount;

}
