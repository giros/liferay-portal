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

package com.liferay.change.tracking.engine.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.engine.exception.NoSuchCTEEntryException;
import com.liferay.change.tracking.engine.model.CTEEntry;
import com.liferay.change.tracking.engine.model.impl.CTEEntryImpl;
import com.liferay.change.tracking.engine.model.impl.CTEEntryModelImpl;
import com.liferay.change.tracking.engine.service.persistence.CTECollectionPersistence;
import com.liferay.change.tracking.engine.service.persistence.CTEEntryPersistence;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the cte entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEEntryPersistence
 * @see com.liferay.change.tracking.engine.service.persistence.CTEEntryUtil
 * @generated
 */
@ProviderType
public class CTEEntryPersistenceImpl extends BasePersistenceImpl<CTEEntry>
	implements CTEEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CTEEntryUtil} to access the cte entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CTEEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CTEEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEEntryModelImpl.FINDER_CACHE_ENABLED, CTEEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CTEEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEEntryModelImpl.FINDER_CACHE_ENABLED, CTEEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CTEEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_RESOURCEPRIMKEY =
		new FinderPath(CTEEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEEntryModelImpl.FINDER_CACHE_ENABLED, CTEEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByResourcePrimKey",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY =
		new FinderPath(CTEEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEEntryModelImpl.FINDER_CACHE_ENABLED, CTEEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByResourcePrimKey",
			new String[] { Long.class.getName() },
			CTEEntryModelImpl.RESOURCEPRIMKEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY = new FinderPath(CTEEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEEntryModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByResourcePrimKey", new String[] { Long.class.getName() });

	/**
	 * Returns all the cte entries where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the matching cte entries
	 */
	@Override
	public List<CTEEntry> findByResourcePrimKey(long resourcePrimKey) {
		return findByResourcePrimKey(resourcePrimKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cte entries where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CTEEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of cte entries
	 * @param end the upper bound of the range of cte entries (not inclusive)
	 * @return the range of matching cte entries
	 */
	@Override
	public List<CTEEntry> findByResourcePrimKey(long resourcePrimKey,
		int start, int end) {
		return findByResourcePrimKey(resourcePrimKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cte entries where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CTEEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of cte entries
	 * @param end the upper bound of the range of cte entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cte entries
	 */
	@Override
	public List<CTEEntry> findByResourcePrimKey(long resourcePrimKey,
		int start, int end, OrderByComparator<CTEEntry> orderByComparator) {
		return findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cte entries where resourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CTEEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param start the lower bound of the range of cte entries
	 * @param end the upper bound of the range of cte entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching cte entries
	 */
	@Override
	public List<CTEEntry> findByResourcePrimKey(long resourcePrimKey,
		int start, int end, OrderByComparator<CTEEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY;
			finderArgs = new Object[] { resourcePrimKey };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_RESOURCEPRIMKEY;
			finderArgs = new Object[] {
					resourcePrimKey,
					
					start, end, orderByComparator
				};
		}

		List<CTEEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTEEntry cteEntry : list) {
					if ((resourcePrimKey != cteEntry.getResourcePrimKey())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CTEENTRY_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CTEEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

				if (!pagination) {
					list = (List<CTEEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Returns the first cte entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cte entry
	 * @throws NoSuchCTEEntryException if a matching cte entry could not be found
	 */
	@Override
	public CTEEntry findByResourcePrimKey_First(long resourcePrimKey,
		OrderByComparator<CTEEntry> orderByComparator)
		throws NoSuchCTEEntryException {
		CTEEntry cteEntry = fetchByResourcePrimKey_First(resourcePrimKey,
				orderByComparator);

		if (cteEntry != null) {
			return cteEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append("}");

		throw new NoSuchCTEEntryException(msg.toString());
	}

	/**
	 * Returns the first cte entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cte entry, or <code>null</code> if a matching cte entry could not be found
	 */
	@Override
	public CTEEntry fetchByResourcePrimKey_First(long resourcePrimKey,
		OrderByComparator<CTEEntry> orderByComparator) {
		List<CTEEntry> list = findByResourcePrimKey(resourcePrimKey, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cte entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cte entry
	 * @throws NoSuchCTEEntryException if a matching cte entry could not be found
	 */
	@Override
	public CTEEntry findByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator<CTEEntry> orderByComparator)
		throws NoSuchCTEEntryException {
		CTEEntry cteEntry = fetchByResourcePrimKey_Last(resourcePrimKey,
				orderByComparator);

		if (cteEntry != null) {
			return cteEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("resourcePrimKey=");
		msg.append(resourcePrimKey);

		msg.append("}");

		throw new NoSuchCTEEntryException(msg.toString());
	}

	/**
	 * Returns the last cte entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cte entry, or <code>null</code> if a matching cte entry could not be found
	 */
	@Override
	public CTEEntry fetchByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator<CTEEntry> orderByComparator) {
		int count = countByResourcePrimKey(resourcePrimKey);

		if (count == 0) {
			return null;
		}

		List<CTEEntry> list = findByResourcePrimKey(resourcePrimKey, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cte entries before and after the current cte entry in the ordered set where resourcePrimKey = &#63;.
	 *
	 * @param entryId the primary key of the current cte entry
	 * @param resourcePrimKey the resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cte entry
	 * @throws NoSuchCTEEntryException if a cte entry with the primary key could not be found
	 */
	@Override
	public CTEEntry[] findByResourcePrimKey_PrevAndNext(long entryId,
		long resourcePrimKey, OrderByComparator<CTEEntry> orderByComparator)
		throws NoSuchCTEEntryException {
		CTEEntry cteEntry = findByPrimaryKey(entryId);

		Session session = null;

		try {
			session = openSession();

			CTEEntry[] array = new CTEEntryImpl[3];

			array[0] = getByResourcePrimKey_PrevAndNext(session, cteEntry,
					resourcePrimKey, orderByComparator, true);

			array[1] = cteEntry;

			array[2] = getByResourcePrimKey_PrevAndNext(session, cteEntry,
					resourcePrimKey, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEEntry getByResourcePrimKey_PrevAndNext(Session session,
		CTEEntry cteEntry, long resourcePrimKey,
		OrderByComparator<CTEEntry> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CTEENTRY_WHERE);

		query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

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
			query.append(CTEEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(resourcePrimKey);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(cteEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CTEEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cte entries where resourcePrimKey = &#63; from the database.
	 *
	 * @param resourcePrimKey the resource prim key
	 */
	@Override
	public void removeByResourcePrimKey(long resourcePrimKey) {
		for (CTEEntry cteEntry : findByResourcePrimKey(resourcePrimKey,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(cteEntry);
		}
	}

	/**
	 * Returns the number of cte entries where resourcePrimKey = &#63;.
	 *
	 * @param resourcePrimKey the resource prim key
	 * @return the number of matching cte entries
	 */
	@Override
	public int countByResourcePrimKey(long resourcePrimKey) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY;

		Object[] finderArgs = new Object[] { resourcePrimKey };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CTEENTRY_WHERE);

			query.append(_FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(resourcePrimKey);

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

	private static final String _FINDER_COLUMN_RESOURCEPRIMKEY_RESOURCEPRIMKEY_2 =
		"cteEntry.resourcePrimKey = ?";

	public CTEEntryPersistenceImpl() {
		setModelClass(CTEEntry.class);

		setModelImplClass(CTEEntryImpl.class);
		setEntityCacheEnabled(CTEEntryModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the cte entry in the entity cache if it is enabled.
	 *
	 * @param cteEntry the cte entry
	 */
	@Override
	public void cacheResult(CTEEntry cteEntry) {
		entityCache.putResult(CTEEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEEntryImpl.class, cteEntry.getPrimaryKey(), cteEntry);

		cteEntry.resetOriginalValues();
	}

	/**
	 * Caches the cte entries in the entity cache if it is enabled.
	 *
	 * @param cteEntries the cte entries
	 */
	@Override
	public void cacheResult(List<CTEEntry> cteEntries) {
		for (CTEEntry cteEntry : cteEntries) {
			if (entityCache.getResult(CTEEntryModelImpl.ENTITY_CACHE_ENABLED,
						CTEEntryImpl.class, cteEntry.getPrimaryKey()) == null) {
				cacheResult(cteEntry);
			}
			else {
				cteEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cte entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTEEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cte entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTEEntry cteEntry) {
		entityCache.removeResult(CTEEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEEntryImpl.class, cteEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CTEEntry> cteEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CTEEntry cteEntry : cteEntries) {
			entityCache.removeResult(CTEEntryModelImpl.ENTITY_CACHE_ENABLED,
				CTEEntryImpl.class, cteEntry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new cte entry with the primary key. Does not add the cte entry to the database.
	 *
	 * @param entryId the primary key for the new cte entry
	 * @return the new cte entry
	 */
	@Override
	public CTEEntry create(long entryId) {
		CTEEntry cteEntry = new CTEEntryImpl();

		cteEntry.setNew(true);
		cteEntry.setPrimaryKey(entryId);

		cteEntry.setCompanyId(companyProvider.getCompanyId());

		return cteEntry;
	}

	/**
	 * Removes the cte entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param entryId the primary key of the cte entry
	 * @return the cte entry that was removed
	 * @throws NoSuchCTEEntryException if a cte entry with the primary key could not be found
	 */
	@Override
	public CTEEntry remove(long entryId) throws NoSuchCTEEntryException {
		return remove((Serializable)entryId);
	}

	/**
	 * Removes the cte entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cte entry
	 * @return the cte entry that was removed
	 * @throws NoSuchCTEEntryException if a cte entry with the primary key could not be found
	 */
	@Override
	public CTEEntry remove(Serializable primaryKey)
		throws NoSuchCTEEntryException {
		Session session = null;

		try {
			session = openSession();

			CTEEntry cteEntry = (CTEEntry)session.get(CTEEntryImpl.class,
					primaryKey);

			if (cteEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCTEEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cteEntry);
		}
		catch (NoSuchCTEEntryException nsee) {
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
	protected CTEEntry removeImpl(CTEEntry cteEntry) {
		cteEntryToCTECollectionTableMapper.deleteLeftPrimaryKeyTableMappings(cteEntry.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cteEntry)) {
				cteEntry = (CTEEntry)session.get(CTEEntryImpl.class,
						cteEntry.getPrimaryKeyObj());
			}

			if (cteEntry != null) {
				session.delete(cteEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cteEntry != null) {
			clearCache(cteEntry);
		}

		return cteEntry;
	}

	@Override
	public CTEEntry updateImpl(CTEEntry cteEntry) {
		boolean isNew = cteEntry.isNew();

		if (!(cteEntry instanceof CTEEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cteEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(cteEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cteEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTEEntry implementation " +
				cteEntry.getClass());
		}

		CTEEntryModelImpl cteEntryModelImpl = (CTEEntryModelImpl)cteEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cteEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				cteEntry.setCreateDate(now);
			}
			else {
				cteEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!cteEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cteEntry.setModifiedDate(now);
			}
			else {
				cteEntry.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cteEntry.isNew()) {
				session.save(cteEntry);

				cteEntry.setNew(false);
			}
			else {
				cteEntry = (CTEEntry)session.merge(cteEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CTEEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { cteEntryModelImpl.getResourcePrimKey() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((cteEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						cteEntryModelImpl.getOriginalResourcePrimKey()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
					args);

				args = new Object[] { cteEntryModelImpl.getResourcePrimKey() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_RESOURCEPRIMKEY,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_RESOURCEPRIMKEY,
					args);
			}
		}

		entityCache.putResult(CTEEntryModelImpl.ENTITY_CACHE_ENABLED,
			CTEEntryImpl.class, cteEntry.getPrimaryKey(), cteEntry, false);

		cteEntry.resetOriginalValues();

		return cteEntry;
	}

	/**
	 * Returns the cte entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cte entry
	 * @return the cte entry
	 * @throws NoSuchCTEEntryException if a cte entry with the primary key could not be found
	 */
	@Override
	public CTEEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCTEEntryException {
		CTEEntry cteEntry = fetchByPrimaryKey(primaryKey);

		if (cteEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCTEEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cteEntry;
	}

	/**
	 * Returns the cte entry with the primary key or throws a {@link NoSuchCTEEntryException} if it could not be found.
	 *
	 * @param entryId the primary key of the cte entry
	 * @return the cte entry
	 * @throws NoSuchCTEEntryException if a cte entry with the primary key could not be found
	 */
	@Override
	public CTEEntry findByPrimaryKey(long entryId)
		throws NoSuchCTEEntryException {
		return findByPrimaryKey((Serializable)entryId);
	}

	/**
	 * Returns the cte entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param entryId the primary key of the cte entry
	 * @return the cte entry, or <code>null</code> if a cte entry with the primary key could not be found
	 */
	@Override
	public CTEEntry fetchByPrimaryKey(long entryId) {
		return fetchByPrimaryKey((Serializable)entryId);
	}

	@Override
	public Map<Serializable, CTEEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CTEEntry> map = new HashMap<Serializable, CTEEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CTEEntry cteEntry = fetchByPrimaryKey(primaryKey);

			if (cteEntry != null) {
				map.put(primaryKey, cteEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CTEEntryModelImpl.ENTITY_CACHE_ENABLED,
					CTEEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CTEEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CTEENTRY_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (CTEEntry cteEntry : (List<CTEEntry>)q.list()) {
				map.put(cteEntry.getPrimaryKeyObj(), cteEntry);

				cacheResult(cteEntry);

				uncachedPrimaryKeys.remove(cteEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CTEEntryModelImpl.ENTITY_CACHE_ENABLED,
					CTEEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the cte entries.
	 *
	 * @return the cte entries
	 */
	@Override
	public List<CTEEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cte entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CTEEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cte entries
	 * @param end the upper bound of the range of cte entries (not inclusive)
	 * @return the range of cte entries
	 */
	@Override
	public List<CTEEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cte entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CTEEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cte entries
	 * @param end the upper bound of the range of cte entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cte entries
	 */
	@Override
	public List<CTEEntry> findAll(int start, int end,
		OrderByComparator<CTEEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cte entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CTEEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cte entries
	 * @param end the upper bound of the range of cte entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cte entries
	 */
	@Override
	public List<CTEEntry> findAll(int start, int end,
		OrderByComparator<CTEEntry> orderByComparator, boolean retrieveFromCache) {
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

		List<CTEEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CTEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CTEENTRY;

				if (pagination) {
					sql = sql.concat(CTEEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CTEEntry>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEEntry>)QueryUtil.list(q, getDialect(),
							start, end);
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
	 * Removes all the cte entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTEEntry cteEntry : findAll()) {
			remove(cteEntry);
		}
	}

	/**
	 * Returns the number of cte entries.
	 *
	 * @return the number of cte entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CTEENTRY);

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

	/**
	 * Returns the primaryKeys of cte collections associated with the cte entry.
	 *
	 * @param pk the primary key of the cte entry
	 * @return long[] of the primaryKeys of cte collections associated with the cte entry
	 */
	@Override
	public long[] getCTECollectionPrimaryKeys(long pk) {
		long[] pks = cteEntryToCTECollectionTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the cte collections associated with the cte entry.
	 *
	 * @param pk the primary key of the cte entry
	 * @return the cte collections associated with the cte entry
	 */
	@Override
	public List<com.liferay.change.tracking.engine.model.CTECollection> getCTECollections(
		long pk) {
		return getCTECollections(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the cte collections associated with the cte entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CTEEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the cte entry
	 * @param start the lower bound of the range of cte entries
	 * @param end the upper bound of the range of cte entries (not inclusive)
	 * @return the range of cte collections associated with the cte entry
	 */
	@Override
	public List<com.liferay.change.tracking.engine.model.CTECollection> getCTECollections(
		long pk, int start, int end) {
		return getCTECollections(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cte collections associated with the cte entry.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CTEEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the cte entry
	 * @param start the lower bound of the range of cte entries
	 * @param end the upper bound of the range of cte entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cte collections associated with the cte entry
	 */
	@Override
	public List<com.liferay.change.tracking.engine.model.CTECollection> getCTECollections(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.engine.model.CTECollection> orderByComparator) {
		return cteEntryToCTECollectionTableMapper.getRightBaseModels(pk, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of cte collections associated with the cte entry.
	 *
	 * @param pk the primary key of the cte entry
	 * @return the number of cte collections associated with the cte entry
	 */
	@Override
	public int getCTECollectionsSize(long pk) {
		long[] pks = cteEntryToCTECollectionTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the cte collection is associated with the cte entry.
	 *
	 * @param pk the primary key of the cte entry
	 * @param cteCollectionPK the primary key of the cte collection
	 * @return <code>true</code> if the cte collection is associated with the cte entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTECollection(long pk, long cteCollectionPK) {
		return cteEntryToCTECollectionTableMapper.containsTableMapping(pk,
			cteCollectionPK);
	}

	/**
	 * Returns <code>true</code> if the cte entry has any cte collections associated with it.
	 *
	 * @param pk the primary key of the cte entry to check for associations with cte collections
	 * @return <code>true</code> if the cte entry has any cte collections associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTECollections(long pk) {
		if (getCTECollectionsSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the cte entry and the cte collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte entry
	 * @param cteCollectionPK the primary key of the cte collection
	 */
	@Override
	public void addCTECollection(long pk, long cteCollectionPK) {
		CTEEntry cteEntry = fetchByPrimaryKey(pk);

		if (cteEntry == null) {
			cteEntryToCTECollectionTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, cteCollectionPK);
		}
		else {
			cteEntryToCTECollectionTableMapper.addTableMapping(cteEntry.getCompanyId(),
				pk, cteCollectionPK);
		}
	}

	/**
	 * Adds an association between the cte entry and the cte collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte entry
	 * @param cteCollection the cte collection
	 */
	@Override
	public void addCTECollection(long pk,
		com.liferay.change.tracking.engine.model.CTECollection cteCollection) {
		CTEEntry cteEntry = fetchByPrimaryKey(pk);

		if (cteEntry == null) {
			cteEntryToCTECollectionTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, cteCollection.getPrimaryKey());
		}
		else {
			cteEntryToCTECollectionTableMapper.addTableMapping(cteEntry.getCompanyId(),
				pk, cteCollection.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the cte entry and the cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte entry
	 * @param cteCollectionPKs the primary keys of the cte collections
	 */
	@Override
	public void addCTECollections(long pk, long[] cteCollectionPKs) {
		long companyId = 0;

		CTEEntry cteEntry = fetchByPrimaryKey(pk);

		if (cteEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = cteEntry.getCompanyId();
		}

		cteEntryToCTECollectionTableMapper.addTableMappings(companyId, pk,
			cteCollectionPKs);
	}

	/**
	 * Adds an association between the cte entry and the cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte entry
	 * @param cteCollections the cte collections
	 */
	@Override
	public void addCTECollections(long pk,
		List<com.liferay.change.tracking.engine.model.CTECollection> cteCollections) {
		addCTECollections(pk,
			ListUtil.toLongArray(cteCollections,
				com.liferay.change.tracking.engine.model.CTECollection.COLLECTION_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the cte entry and its cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte entry to clear the associated cte collections from
	 */
	@Override
	public void clearCTECollections(long pk) {
		cteEntryToCTECollectionTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the cte entry and the cte collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte entry
	 * @param cteCollectionPK the primary key of the cte collection
	 */
	@Override
	public void removeCTECollection(long pk, long cteCollectionPK) {
		cteEntryToCTECollectionTableMapper.deleteTableMapping(pk,
			cteCollectionPK);
	}

	/**
	 * Removes the association between the cte entry and the cte collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte entry
	 * @param cteCollection the cte collection
	 */
	@Override
	public void removeCTECollection(long pk,
		com.liferay.change.tracking.engine.model.CTECollection cteCollection) {
		cteEntryToCTECollectionTableMapper.deleteTableMapping(pk,
			cteCollection.getPrimaryKey());
	}

	/**
	 * Removes the association between the cte entry and the cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte entry
	 * @param cteCollectionPKs the primary keys of the cte collections
	 */
	@Override
	public void removeCTECollections(long pk, long[] cteCollectionPKs) {
		cteEntryToCTECollectionTableMapper.deleteTableMappings(pk,
			cteCollectionPKs);
	}

	/**
	 * Removes the association between the cte entry and the cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte entry
	 * @param cteCollections the cte collections
	 */
	@Override
	public void removeCTECollections(long pk,
		List<com.liferay.change.tracking.engine.model.CTECollection> cteCollections) {
		removeCTECollections(pk,
			ListUtil.toLongArray(cteCollections,
				com.liferay.change.tracking.engine.model.CTECollection.COLLECTION_ID_ACCESSOR));
	}

	/**
	 * Sets the cte collections associated with the cte entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte entry
	 * @param cteCollectionPKs the primary keys of the cte collections to be associated with the cte entry
	 */
	@Override
	public void setCTECollections(long pk, long[] cteCollectionPKs) {
		Set<Long> newCTECollectionPKsSet = SetUtil.fromArray(cteCollectionPKs);
		Set<Long> oldCTECollectionPKsSet = SetUtil.fromArray(cteEntryToCTECollectionTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeCTECollectionPKsSet = new HashSet<Long>(oldCTECollectionPKsSet);

		removeCTECollectionPKsSet.removeAll(newCTECollectionPKsSet);

		cteEntryToCTECollectionTableMapper.deleteTableMappings(pk,
			ArrayUtil.toLongArray(removeCTECollectionPKsSet));

		newCTECollectionPKsSet.removeAll(oldCTECollectionPKsSet);

		long companyId = 0;

		CTEEntry cteEntry = fetchByPrimaryKey(pk);

		if (cteEntry == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = cteEntry.getCompanyId();
		}

		cteEntryToCTECollectionTableMapper.addTableMappings(companyId, pk,
			ArrayUtil.toLongArray(newCTECollectionPKsSet));
	}

	/**
	 * Sets the cte collections associated with the cte entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte entry
	 * @param cteCollections the cte collections to be associated with the cte entry
	 */
	@Override
	public void setCTECollections(long pk,
		List<com.liferay.change.tracking.engine.model.CTECollection> cteCollections) {
		try {
			long[] cteCollectionPKs = new long[cteCollections.size()];

			for (int i = 0; i < cteCollections.size(); i++) {
				com.liferay.change.tracking.engine.model.CTECollection cteCollection =
					cteCollections.get(i);

				cteCollectionPKs[i] = cteCollection.getPrimaryKey();
			}

			setCTECollections(pk, cteCollectionPKs);
		}
		catch (Exception e) {
			throw processException(e);
		}
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTEEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cte entry persistence.
	 */
	public void afterPropertiesSet() {
		cteEntryToCTECollectionTableMapper = TableMapperFactory.getTableMapper("Collections_Entries",
				"companyId", "entryId", "collectionId", this,
				cteCollectionPersistence);
	}

	public void destroy() {
		entityCache.removeCache(CTEEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper("Collections_Entries");
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	@BeanReference(type = CTECollectionPersistence.class)
	protected CTECollectionPersistence cteCollectionPersistence;
	protected TableMapper<CTEEntry, com.liferay.change.tracking.engine.model.CTECollection> cteEntryToCTECollectionTableMapper;
	private static final String _SQL_SELECT_CTEENTRY = "SELECT cteEntry FROM CTEEntry cteEntry";
	private static final String _SQL_SELECT_CTEENTRY_WHERE_PKS_IN = "SELECT cteEntry FROM CTEEntry cteEntry WHERE entryId IN (";
	private static final String _SQL_SELECT_CTEENTRY_WHERE = "SELECT cteEntry FROM CTEEntry cteEntry WHERE ";
	private static final String _SQL_COUNT_CTEENTRY = "SELECT COUNT(cteEntry) FROM CTEEntry cteEntry";
	private static final String _SQL_COUNT_CTEENTRY_WHERE = "SELECT COUNT(cteEntry) FROM CTEEntry cteEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cteEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CTEEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CTEEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CTEEntryPersistenceImpl.class);
}