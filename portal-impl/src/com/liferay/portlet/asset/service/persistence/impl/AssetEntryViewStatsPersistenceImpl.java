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

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException;
import com.liferay.asset.kernel.model.AssetEntryViewStats;
import com.liferay.asset.kernel.service.persistence.AssetEntryViewStatsPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.asset.model.impl.AssetEntryViewStatsImpl;
import com.liferay.portlet.asset.model.impl.AssetEntryViewStatsModelImpl;

import java.io.Serializable;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the asset entry view stats service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryViewStatsPersistence
 * @see com.liferay.asset.kernel.service.persistence.AssetEntryViewStatsUtil
 * @generated
 */
@ProviderType
public class AssetEntryViewStatsPersistenceImpl extends BasePersistenceImpl<AssetEntryViewStats>
	implements AssetEntryViewStatsPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link AssetEntryViewStatsUtil} to access the asset entry view stats persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = AssetEntryViewStatsImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryViewStatsModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryViewStatsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryViewStatsModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryViewStatsImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryViewStatsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryViewStatsModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryViewStatsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryViewStatsModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryViewStatsImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			AssetEntryViewStatsModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			AssetEntryViewStatsModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryViewStatsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the asset entry view statses where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findByC_C(long classNameId, long classPK) {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry view statses where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of asset entry view statses
	 * @param end the upper bound of the range of asset entry view statses (not inclusive)
	 * @return the range of matching asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findByC_C(long classNameId, long classPK,
		int start, int end) {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry view statses where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of asset entry view statses
	 * @param end the upper bound of the range of asset entry view statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findByC_C(long classNameId, long classPK,
		int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return findByC_C(classNameId, classPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the asset entry view statses where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of asset entry view statses
	 * @param end the upper bound of the range of asset entry view statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findByC_C(long classNameId, long classPK,
		int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<AssetEntryViewStats> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryViewStats>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryViewStats assetEntryViewStats : list) {
					if ((classNameId != assetEntryViewStats.getClassNameId()) ||
							(classPK != assetEntryViewStats.getClassPK())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ASSETENTRYVIEWSTATS_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetEntryViewStatsModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<AssetEntryViewStats>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryViewStats>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry view stats
	 * @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	 */
	@Override
	public AssetEntryViewStats findByC_C_First(long classNameId, long classPK,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException {
		AssetEntryViewStats assetEntryViewStats = fetchByC_C_First(classNameId,
				classPK, orderByComparator);

		if (assetEntryViewStats != null) {
			return assetEntryViewStats;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryViewStatsException(msg.toString());
	}

	/**
	 * Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	 */
	@Override
	public AssetEntryViewStats fetchByC_C_First(long classNameId, long classPK,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		List<AssetEntryViewStats> list = findByC_C(classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry view stats
	 * @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	 */
	@Override
	public AssetEntryViewStats findByC_C_Last(long classNameId, long classPK,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException {
		AssetEntryViewStats assetEntryViewStats = fetchByC_C_Last(classNameId,
				classPK, orderByComparator);

		if (assetEntryViewStats != null) {
			return assetEntryViewStats;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryViewStatsException(msg.toString());
	}

	/**
	 * Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	 */
	@Override
	public AssetEntryViewStats fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<AssetEntryViewStats> list = findByC_C(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry view statses before and after the current asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param statsId the primary key of the current asset entry view stats
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry view stats
	 * @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	 */
	@Override
	public AssetEntryViewStats[] findByC_C_PrevAndNext(long statsId,
		long classNameId, long classPK,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException {
		AssetEntryViewStats assetEntryViewStats = findByPrimaryKey(statsId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryViewStats[] array = new AssetEntryViewStatsImpl[3];

			array[0] = getByC_C_PrevAndNext(session, assetEntryViewStats,
					classNameId, classPK, orderByComparator, true);

			array[1] = assetEntryViewStats;

			array[2] = getByC_C_PrevAndNext(session, assetEntryViewStats,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntryViewStats getByC_C_PrevAndNext(Session session,
		AssetEntryViewStats assetEntryViewStats, long classNameId,
		long classPK, OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_ASSETENTRYVIEWSTATS_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetEntryViewStatsModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetEntryViewStats);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetEntryViewStats> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry view statses where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (AssetEntryViewStats assetEntryViewStats : findByC_C(classNameId,
				classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(assetEntryViewStats);
		}
	}

	/**
	 * Returns the number of asset entry view statses where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching asset entry view statses
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ASSETENTRYVIEWSTATS_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "assetEntryViewStats.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "assetEntryViewStats.classPK = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_GTD = new FinderPath(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryViewStatsModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryViewStatsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_GtD",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_C_GTD = new FinderPath(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryViewStatsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C_GtD",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			});

	/**
	 * Returns all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @return the matching asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findByC_C_GtD(long classNameId,
		long classPK, Date createDate) {
		return findByC_C_GtD(classNameId, classPK, createDate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param start the lower bound of the range of asset entry view statses
	 * @param end the upper bound of the range of asset entry view statses (not inclusive)
	 * @return the range of matching asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findByC_C_GtD(long classNameId,
		long classPK, Date createDate, int start, int end) {
		return findByC_C_GtD(classNameId, classPK, createDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param start the lower bound of the range of asset entry view statses
	 * @param end the upper bound of the range of asset entry view statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findByC_C_GtD(long classNameId,
		long classPK, Date createDate, int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return findByC_C_GtD(classNameId, classPK, createDate, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param start the lower bound of the range of asset entry view statses
	 * @param end the upper bound of the range of asset entry view statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findByC_C_GtD(long classNameId,
		long classPK, Date createDate, int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_GTD;
		finderArgs = new Object[] {
				classNameId, classPK, createDate,
				
				start, end, orderByComparator
			};

		List<AssetEntryViewStats> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryViewStats>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryViewStats assetEntryViewStats : list) {
					if ((classNameId != assetEntryViewStats.getClassNameId()) ||
							(classPK != assetEntryViewStats.getClassPK()) ||
							(createDate.getTime() >= assetEntryViewStats.getCreateDate()
																			.getTime())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_ASSETENTRYVIEWSTATS_WHERE);

			query.append(_FINDER_COLUMN_C_C_GTD_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_GTD_CLASSPK_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_C_C_GTD_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_C_C_GTD_CREATEDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetEntryViewStatsModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindCreateDate) {
					qPos.add(new Timestamp(createDate.getTime()));
				}

				if (!pagination) {
					list = (List<AssetEntryViewStats>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryViewStats>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry view stats
	 * @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	 */
	@Override
	public AssetEntryViewStats findByC_C_GtD_First(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException {
		AssetEntryViewStats assetEntryViewStats = fetchByC_C_GtD_First(classNameId,
				classPK, createDate, orderByComparator);

		if (assetEntryViewStats != null) {
			return assetEntryViewStats;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", createDate=");
		msg.append(createDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryViewStatsException(msg.toString());
	}

	/**
	 * Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	 */
	@Override
	public AssetEntryViewStats fetchByC_C_GtD_First(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		List<AssetEntryViewStats> list = findByC_C_GtD(classNameId, classPK,
				createDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry view stats
	 * @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	 */
	@Override
	public AssetEntryViewStats findByC_C_GtD_Last(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException {
		AssetEntryViewStats assetEntryViewStats = fetchByC_C_GtD_Last(classNameId,
				classPK, createDate, orderByComparator);

		if (assetEntryViewStats != null) {
			return assetEntryViewStats;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", createDate=");
		msg.append(createDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryViewStatsException(msg.toString());
	}

	/**
	 * Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	 */
	@Override
	public AssetEntryViewStats fetchByC_C_GtD_Last(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		int count = countByC_C_GtD(classNameId, classPK, createDate);

		if (count == 0) {
			return null;
		}

		List<AssetEntryViewStats> list = findByC_C_GtD(classNameId, classPK,
				createDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry view statses before and after the current asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	 *
	 * @param statsId the primary key of the current asset entry view stats
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry view stats
	 * @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	 */
	@Override
	public AssetEntryViewStats[] findByC_C_GtD_PrevAndNext(long statsId,
		long classNameId, long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException {
		AssetEntryViewStats assetEntryViewStats = findByPrimaryKey(statsId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryViewStats[] array = new AssetEntryViewStatsImpl[3];

			array[0] = getByC_C_GtD_PrevAndNext(session, assetEntryViewStats,
					classNameId, classPK, createDate, orderByComparator, true);

			array[1] = assetEntryViewStats;

			array[2] = getByC_C_GtD_PrevAndNext(session, assetEntryViewStats,
					classNameId, classPK, createDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntryViewStats getByC_C_GtD_PrevAndNext(Session session,
		AssetEntryViewStats assetEntryViewStats, long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_ASSETENTRYVIEWSTATS_WHERE);

		query.append(_FINDER_COLUMN_C_C_GTD_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_GTD_CLASSPK_2);

		boolean bindCreateDate = false;

		if (createDate == null) {
			query.append(_FINDER_COLUMN_C_C_GTD_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			query.append(_FINDER_COLUMN_C_C_GTD_CREATEDATE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetEntryViewStatsModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (bindCreateDate) {
			qPos.add(new Timestamp(createDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetEntryViewStats);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetEntryViewStats> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 */
	@Override
	public void removeByC_C_GtD(long classNameId, long classPK, Date createDate) {
		for (AssetEntryViewStats assetEntryViewStats : findByC_C_GtD(
				classNameId, classPK, createDate, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(assetEntryViewStats);
		}
	}

	/**
	 * Returns the number of asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @return the number of matching asset entry view statses
	 */
	@Override
	public int countByC_C_GtD(long classNameId, long classPK, Date createDate) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_C_GTD;

		Object[] finderArgs = new Object[] { classNameId, classPK, createDate };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ASSETENTRYVIEWSTATS_WHERE);

			query.append(_FINDER_COLUMN_C_C_GTD_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_GTD_CLASSPK_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_C_C_GTD_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_C_C_GTD_CREATEDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindCreateDate) {
					qPos.add(new Timestamp(createDate.getTime()));
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_GTD_CLASSNAMEID_2 = "assetEntryViewStats.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_GTD_CLASSPK_2 = "assetEntryViewStats.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_GTD_CREATEDATE_1 = "assetEntryViewStats.createDate IS NULL";
	private static final String _FINDER_COLUMN_C_C_GTD_CREATEDATE_2 = "assetEntryViewStats.createDate > ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_LED = new FinderPath(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryViewStatsModelImpl.FINDER_CACHE_ENABLED,
			AssetEntryViewStatsImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C_LeD",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_C_LED = new FinderPath(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryViewStatsModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_C_LeD",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			});

	/**
	 * Returns all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @return the matching asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findByC_C_LeD(long classNameId,
		long classPK, Date createDate) {
		return findByC_C_LeD(classNameId, classPK, createDate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param start the lower bound of the range of asset entry view statses
	 * @param end the upper bound of the range of asset entry view statses (not inclusive)
	 * @return the range of matching asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findByC_C_LeD(long classNameId,
		long classPK, Date createDate, int start, int end) {
		return findByC_C_LeD(classNameId, classPK, createDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param start the lower bound of the range of asset entry view statses
	 * @param end the upper bound of the range of asset entry view statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findByC_C_LeD(long classNameId,
		long classPK, Date createDate, int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return findByC_C_LeD(classNameId, classPK, createDate, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param start the lower bound of the range of asset entry view statses
	 * @param end the upper bound of the range of asset entry view statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findByC_C_LeD(long classNameId,
		long classPK, Date createDate, int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C_LED;
		finderArgs = new Object[] {
				classNameId, classPK, createDate,
				
				start, end, orderByComparator
			};

		List<AssetEntryViewStats> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryViewStats>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (AssetEntryViewStats assetEntryViewStats : list) {
					if ((classNameId != assetEntryViewStats.getClassNameId()) ||
							(classPK != assetEntryViewStats.getClassPK()) ||
							(createDate.getTime() < assetEntryViewStats.getCreateDate()
																		   .getTime())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_ASSETENTRYVIEWSTATS_WHERE);

			query.append(_FINDER_COLUMN_C_C_LED_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_LED_CLASSPK_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_C_C_LED_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_C_C_LED_CREATEDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(AssetEntryViewStatsModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindCreateDate) {
					qPos.add(new Timestamp(createDate.getTime()));
				}

				if (!pagination) {
					list = (List<AssetEntryViewStats>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryViewStats>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry view stats
	 * @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	 */
	@Override
	public AssetEntryViewStats findByC_C_LeD_First(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException {
		AssetEntryViewStats assetEntryViewStats = fetchByC_C_LeD_First(classNameId,
				classPK, createDate, orderByComparator);

		if (assetEntryViewStats != null) {
			return assetEntryViewStats;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", createDate=");
		msg.append(createDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryViewStatsException(msg.toString());
	}

	/**
	 * Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	 */
	@Override
	public AssetEntryViewStats fetchByC_C_LeD_First(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		List<AssetEntryViewStats> list = findByC_C_LeD(classNameId, classPK,
				createDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry view stats
	 * @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	 */
	@Override
	public AssetEntryViewStats findByC_C_LeD_Last(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException {
		AssetEntryViewStats assetEntryViewStats = fetchByC_C_LeD_Last(classNameId,
				classPK, createDate, orderByComparator);

		if (assetEntryViewStats != null) {
			return assetEntryViewStats;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", createDate=");
		msg.append(createDate);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchEntryViewStatsException(msg.toString());
	}

	/**
	 * Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	 */
	@Override
	public AssetEntryViewStats fetchByC_C_LeD_Last(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		int count = countByC_C_LeD(classNameId, classPK, createDate);

		if (count == 0) {
			return null;
		}

		List<AssetEntryViewStats> list = findByC_C_LeD(classNameId, classPK,
				createDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the asset entry view statses before and after the current asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	 *
	 * @param statsId the primary key of the current asset entry view stats
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next asset entry view stats
	 * @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	 */
	@Override
	public AssetEntryViewStats[] findByC_C_LeD_PrevAndNext(long statsId,
		long classNameId, long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException {
		AssetEntryViewStats assetEntryViewStats = findByPrimaryKey(statsId);

		Session session = null;

		try {
			session = openSession();

			AssetEntryViewStats[] array = new AssetEntryViewStatsImpl[3];

			array[0] = getByC_C_LeD_PrevAndNext(session, assetEntryViewStats,
					classNameId, classPK, createDate, orderByComparator, true);

			array[1] = assetEntryViewStats;

			array[2] = getByC_C_LeD_PrevAndNext(session, assetEntryViewStats,
					classNameId, classPK, createDate, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected AssetEntryViewStats getByC_C_LeD_PrevAndNext(Session session,
		AssetEntryViewStats assetEntryViewStats, long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_ASSETENTRYVIEWSTATS_WHERE);

		query.append(_FINDER_COLUMN_C_C_LED_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_LED_CLASSPK_2);

		boolean bindCreateDate = false;

		if (createDate == null) {
			query.append(_FINDER_COLUMN_C_C_LED_CREATEDATE_1);
		}
		else {
			bindCreateDate = true;

			query.append(_FINDER_COLUMN_C_C_LED_CREATEDATE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(AssetEntryViewStatsModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (bindCreateDate) {
			qPos.add(new Timestamp(createDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(assetEntryViewStats);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<AssetEntryViewStats> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 */
	@Override
	public void removeByC_C_LeD(long classNameId, long classPK, Date createDate) {
		for (AssetEntryViewStats assetEntryViewStats : findByC_C_LeD(
				classNameId, classPK, createDate, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(assetEntryViewStats);
		}
	}

	/**
	 * Returns the number of asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param createDate the create date
	 * @return the number of matching asset entry view statses
	 */
	@Override
	public int countByC_C_LeD(long classNameId, long classPK, Date createDate) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_C_C_LED;

		Object[] finderArgs = new Object[] { classNameId, classPK, createDate };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_ASSETENTRYVIEWSTATS_WHERE);

			query.append(_FINDER_COLUMN_C_C_LED_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_LED_CLASSPK_2);

			boolean bindCreateDate = false;

			if (createDate == null) {
				query.append(_FINDER_COLUMN_C_C_LED_CREATEDATE_1);
			}
			else {
				bindCreateDate = true;

				query.append(_FINDER_COLUMN_C_C_LED_CREATEDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (bindCreateDate) {
					qPos.add(new Timestamp(createDate.getTime()));
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_LED_CLASSNAMEID_2 = "assetEntryViewStats.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_LED_CLASSPK_2 = "assetEntryViewStats.classPK = ? AND ";
	private static final String _FINDER_COLUMN_C_C_LED_CREATEDATE_1 = "assetEntryViewStats.createDate IS NULL";
	private static final String _FINDER_COLUMN_C_C_LED_CREATEDATE_2 = "assetEntryViewStats.createDate <= ?";

	public AssetEntryViewStatsPersistenceImpl() {
		setModelClass(AssetEntryViewStats.class);
	}

	/**
	 * Caches the asset entry view stats in the entity cache if it is enabled.
	 *
	 * @param assetEntryViewStats the asset entry view stats
	 */
	@Override
	public void cacheResult(AssetEntryViewStats assetEntryViewStats) {
		entityCache.putResult(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryViewStatsImpl.class, assetEntryViewStats.getPrimaryKey(),
			assetEntryViewStats);

		assetEntryViewStats.resetOriginalValues();
	}

	/**
	 * Caches the asset entry view statses in the entity cache if it is enabled.
	 *
	 * @param assetEntryViewStatses the asset entry view statses
	 */
	@Override
	public void cacheResult(List<AssetEntryViewStats> assetEntryViewStatses) {
		for (AssetEntryViewStats assetEntryViewStats : assetEntryViewStatses) {
			if (entityCache.getResult(
						AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
						AssetEntryViewStatsImpl.class,
						assetEntryViewStats.getPrimaryKey()) == null) {
				cacheResult(assetEntryViewStats);
			}
			else {
				assetEntryViewStats.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all asset entry view statses.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(AssetEntryViewStatsImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the asset entry view stats.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(AssetEntryViewStats assetEntryViewStats) {
		entityCache.removeResult(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryViewStatsImpl.class, assetEntryViewStats.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<AssetEntryViewStats> assetEntryViewStatses) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (AssetEntryViewStats assetEntryViewStats : assetEntryViewStatses) {
			entityCache.removeResult(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntryViewStatsImpl.class,
				assetEntryViewStats.getPrimaryKey());
		}
	}

	/**
	 * Creates a new asset entry view stats with the primary key. Does not add the asset entry view stats to the database.
	 *
	 * @param statsId the primary key for the new asset entry view stats
	 * @return the new asset entry view stats
	 */
	@Override
	public AssetEntryViewStats create(long statsId) {
		AssetEntryViewStats assetEntryViewStats = new AssetEntryViewStatsImpl();

		assetEntryViewStats.setNew(true);
		assetEntryViewStats.setPrimaryKey(statsId);

		assetEntryViewStats.setCompanyId(companyProvider.getCompanyId());

		return assetEntryViewStats;
	}

	/**
	 * Removes the asset entry view stats with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param statsId the primary key of the asset entry view stats
	 * @return the asset entry view stats that was removed
	 * @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	 */
	@Override
	public AssetEntryViewStats remove(long statsId)
		throws NoSuchEntryViewStatsException {
		return remove((Serializable)statsId);
	}

	/**
	 * Removes the asset entry view stats with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the asset entry view stats
	 * @return the asset entry view stats that was removed
	 * @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	 */
	@Override
	public AssetEntryViewStats remove(Serializable primaryKey)
		throws NoSuchEntryViewStatsException {
		Session session = null;

		try {
			session = openSession();

			AssetEntryViewStats assetEntryViewStats = (AssetEntryViewStats)session.get(AssetEntryViewStatsImpl.class,
					primaryKey);

			if (assetEntryViewStats == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryViewStatsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(assetEntryViewStats);
		}
		catch (NoSuchEntryViewStatsException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected AssetEntryViewStats removeImpl(
		AssetEntryViewStats assetEntryViewStats) {
		assetEntryViewStats = toUnwrappedModel(assetEntryViewStats);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(assetEntryViewStats)) {
				assetEntryViewStats = (AssetEntryViewStats)session.get(AssetEntryViewStatsImpl.class,
						assetEntryViewStats.getPrimaryKeyObj());
			}

			if (assetEntryViewStats != null) {
				session.delete(assetEntryViewStats);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (assetEntryViewStats != null) {
			clearCache(assetEntryViewStats);
		}

		return assetEntryViewStats;
	}

	@Override
	public AssetEntryViewStats updateImpl(
		AssetEntryViewStats assetEntryViewStats) {
		assetEntryViewStats = toUnwrappedModel(assetEntryViewStats);

		boolean isNew = assetEntryViewStats.isNew();

		AssetEntryViewStatsModelImpl assetEntryViewStatsModelImpl = (AssetEntryViewStatsModelImpl)assetEntryViewStats;

		Session session = null;

		try {
			session = openSession();

			if (assetEntryViewStats.isNew()) {
				session.save(assetEntryViewStats);

				assetEntryViewStats.setNew(false);
			}
			else {
				assetEntryViewStats = (AssetEntryViewStats)session.merge(assetEntryViewStats);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!AssetEntryViewStatsModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					assetEntryViewStatsModelImpl.getClassNameId(),
					assetEntryViewStatsModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((assetEntryViewStatsModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						assetEntryViewStatsModelImpl.getOriginalClassNameId(),
						assetEntryViewStatsModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						assetEntryViewStatsModelImpl.getClassNameId(),
						assetEntryViewStatsModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}
		}

		entityCache.putResult(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
			AssetEntryViewStatsImpl.class, assetEntryViewStats.getPrimaryKey(),
			assetEntryViewStats, false);

		assetEntryViewStats.resetOriginalValues();

		return assetEntryViewStats;
	}

	protected AssetEntryViewStats toUnwrappedModel(
		AssetEntryViewStats assetEntryViewStats) {
		if (assetEntryViewStats instanceof AssetEntryViewStatsImpl) {
			return assetEntryViewStats;
		}

		AssetEntryViewStatsImpl assetEntryViewStatsImpl = new AssetEntryViewStatsImpl();

		assetEntryViewStatsImpl.setNew(assetEntryViewStats.isNew());
		assetEntryViewStatsImpl.setPrimaryKey(assetEntryViewStats.getPrimaryKey());

		assetEntryViewStatsImpl.setStatsId(assetEntryViewStats.getStatsId());
		assetEntryViewStatsImpl.setGroupId(assetEntryViewStats.getGroupId());
		assetEntryViewStatsImpl.setCompanyId(assetEntryViewStats.getCompanyId());
		assetEntryViewStatsImpl.setUserId(assetEntryViewStats.getUserId());
		assetEntryViewStatsImpl.setCreateDate(assetEntryViewStats.getCreateDate());
		assetEntryViewStatsImpl.setClassNameId(assetEntryViewStats.getClassNameId());
		assetEntryViewStatsImpl.setClassPK(assetEntryViewStats.getClassPK());
		assetEntryViewStatsImpl.setAssetEntryId(assetEntryViewStats.getAssetEntryId());

		return assetEntryViewStatsImpl;
	}

	/**
	 * Returns the asset entry view stats with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry view stats
	 * @return the asset entry view stats
	 * @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	 */
	@Override
	public AssetEntryViewStats findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryViewStatsException {
		AssetEntryViewStats assetEntryViewStats = fetchByPrimaryKey(primaryKey);

		if (assetEntryViewStats == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryViewStatsException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return assetEntryViewStats;
	}

	/**
	 * Returns the asset entry view stats with the primary key or throws a {@link NoSuchEntryViewStatsException} if it could not be found.
	 *
	 * @param statsId the primary key of the asset entry view stats
	 * @return the asset entry view stats
	 * @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	 */
	@Override
	public AssetEntryViewStats findByPrimaryKey(long statsId)
		throws NoSuchEntryViewStatsException {
		return findByPrimaryKey((Serializable)statsId);
	}

	/**
	 * Returns the asset entry view stats with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the asset entry view stats
	 * @return the asset entry view stats, or <code>null</code> if a asset entry view stats with the primary key could not be found
	 */
	@Override
	public AssetEntryViewStats fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
				AssetEntryViewStatsImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		AssetEntryViewStats assetEntryViewStats = (AssetEntryViewStats)serializable;

		if (assetEntryViewStats == null) {
			Session session = null;

			try {
				session = openSession();

				assetEntryViewStats = (AssetEntryViewStats)session.get(AssetEntryViewStatsImpl.class,
						primaryKey);

				if (assetEntryViewStats != null) {
					cacheResult(assetEntryViewStats);
				}
				else {
					entityCache.putResult(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
						AssetEntryViewStatsImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntryViewStatsImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return assetEntryViewStats;
	}

	/**
	 * Returns the asset entry view stats with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param statsId the primary key of the asset entry view stats
	 * @return the asset entry view stats, or <code>null</code> if a asset entry view stats with the primary key could not be found
	 */
	@Override
	public AssetEntryViewStats fetchByPrimaryKey(long statsId) {
		return fetchByPrimaryKey((Serializable)statsId);
	}

	@Override
	public Map<Serializable, AssetEntryViewStats> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, AssetEntryViewStats> map = new HashMap<Serializable, AssetEntryViewStats>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			AssetEntryViewStats assetEntryViewStats = fetchByPrimaryKey(primaryKey);

			if (assetEntryViewStats != null) {
				map.put(primaryKey, assetEntryViewStats);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntryViewStatsImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (AssetEntryViewStats)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ASSETENTRYVIEWSTATS_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (AssetEntryViewStats assetEntryViewStats : (List<AssetEntryViewStats>)q.list()) {
				map.put(assetEntryViewStats.getPrimaryKeyObj(),
					assetEntryViewStats);

				cacheResult(assetEntryViewStats);

				uncachedPrimaryKeys.remove(assetEntryViewStats.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(AssetEntryViewStatsModelImpl.ENTITY_CACHE_ENABLED,
					AssetEntryViewStatsImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the asset entry view statses.
	 *
	 * @return the asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the asset entry view statses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry view statses
	 * @param end the upper bound of the range of asset entry view statses (not inclusive)
	 * @return the range of asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the asset entry view statses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry view statses
	 * @param end the upper bound of the range of asset entry view statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findAll(int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the asset entry view statses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset entry view statses
	 * @param end the upper bound of the range of asset entry view statses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of asset entry view statses
	 */
	@Override
	public List<AssetEntryViewStats> findAll(int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<AssetEntryViewStats> list = null;

		if (retrieveFromCache) {
			list = (List<AssetEntryViewStats>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ASSETENTRYVIEWSTATS);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ASSETENTRYVIEWSTATS;

				if (pagination) {
					sql = sql.concat(AssetEntryViewStatsModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<AssetEntryViewStats>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<AssetEntryViewStats>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the asset entry view statses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (AssetEntryViewStats assetEntryViewStats : findAll()) {
			remove(assetEntryViewStats);
		}
	}

	/**
	 * Returns the number of asset entry view statses.
	 *
	 * @return the number of asset entry view statses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ASSETENTRYVIEWSTATS);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return AssetEntryViewStatsModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the asset entry view stats persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(AssetEntryViewStatsImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	protected EntityCache entityCache = EntityCacheUtil.getEntityCache();
	protected FinderCache finderCache = FinderCacheUtil.getFinderCache();
	private static final String _SQL_SELECT_ASSETENTRYVIEWSTATS = "SELECT assetEntryViewStats FROM AssetEntryViewStats assetEntryViewStats";
	private static final String _SQL_SELECT_ASSETENTRYVIEWSTATS_WHERE_PKS_IN = "SELECT assetEntryViewStats FROM AssetEntryViewStats assetEntryViewStats WHERE statsId IN (";
	private static final String _SQL_SELECT_ASSETENTRYVIEWSTATS_WHERE = "SELECT assetEntryViewStats FROM AssetEntryViewStats assetEntryViewStats WHERE ";
	private static final String _SQL_COUNT_ASSETENTRYVIEWSTATS = "SELECT COUNT(assetEntryViewStats) FROM AssetEntryViewStats assetEntryViewStats";
	private static final String _SQL_COUNT_ASSETENTRYVIEWSTATS_WHERE = "SELECT COUNT(assetEntryViewStats) FROM AssetEntryViewStats assetEntryViewStats WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "assetEntryViewStats.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No AssetEntryViewStats exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No AssetEntryViewStats exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(AssetEntryViewStatsPersistenceImpl.class);
}