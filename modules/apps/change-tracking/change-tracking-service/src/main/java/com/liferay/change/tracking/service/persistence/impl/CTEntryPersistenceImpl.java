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

package com.liferay.change.tracking.service.persistence.impl;

import com.liferay.change.tracking.exception.NoSuchEntryException;
import com.liferay.change.tracking.model.CTEntry;
import com.liferay.change.tracking.model.CTEntryAggregate;
import com.liferay.change.tracking.model.impl.CTEntryImpl;
import com.liferay.change.tracking.model.impl.CTEntryModelImpl;
import com.liferay.change.tracking.service.persistence.CTEntryPersistence;
import com.liferay.change.tracking.service.persistence.impl.constants.CTPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.service.persistence.impl.TableMapper;
import com.liferay.portal.kernel.service.persistence.impl.TableMapperFactory;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.annotation.versioning.ProviderType;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the ct entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = CTEntryPersistence.class)
@ProviderType
public class CTEntryPersistenceImpl
	extends BasePersistenceImpl<CTEntry> implements CTEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CTEntryUtil</code> to access the ct entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CTEntryImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCTCollectionId;
	private FinderPath _finderPathWithoutPaginationFindByCTCollectionId;
	private FinderPath _finderPathCountByCTCollectionId;

	/**
	 * Returns all the ct entries where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByCTCollectionId(long ctCollectionId) {
		return findByCTCollectionId(
			ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByCTCollectionId(
		long ctCollectionId, int start, int end) {

		return findByCTCollectionId(ctCollectionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return findByCTCollectionId(
			ctCollectionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByCTCollectionId(
		long ctCollectionId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByCTCollectionId;
			finderArgs = new Object[] {ctCollectionId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByCTCollectionId;
			finderArgs = new Object[] {
				ctCollectionId, start, end, orderByComparator
			};
		}

		List<CTEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if ((ctCollectionId != ctEntry.getCtCollectionId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				if (!pagination) {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByCTCollectionId_First(
			long ctCollectionId, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByCTCollectionId_First(
			ctCollectionId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByCTCollectionId_First(
		long ctCollectionId, OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByCTCollectionId(
			ctCollectionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByCTCollectionId_Last(
			long ctCollectionId, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByCTCollectionId_Last(
			ctCollectionId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByCTCollectionId_Last(
		long ctCollectionId, OrderByComparator<CTEntry> orderByComparator) {

		int count = countByCTCollectionId(ctCollectionId);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByCTCollectionId(
			ctCollectionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByCTCollectionId_PrevAndNext(
			long ctEntryId, long ctCollectionId,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByCTCollectionId_PrevAndNext(
				session, ctEntry, ctCollectionId, orderByComparator, true);

			array[1] = ctEntry;

			array[2] = getByCTCollectionId_PrevAndNext(
				session, ctEntry, ctCollectionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntry getByCTCollectionId_PrevAndNext(
		Session session, CTEntry ctEntry, long ctCollectionId,
		OrderByComparator<CTEntry> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CTENTRY_WHERE);

		query.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ctCollectionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 */
	@Override
	public void removeByCTCollectionId(long ctCollectionId) {
		for (CTEntry ctEntry :
				findByCTCollectionId(
					ctCollectionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByCTCollectionId(long ctCollectionId) {
		FinderPath finderPath = _finderPathCountByCTCollectionId;

		Object[] finderArgs = new Object[] {ctCollectionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

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

	private static final String _FINDER_COLUMN_CTCOLLECTIONID_CTCOLLECTIONID_2 =
		"ctEntry.ctCollectionId = ?";

	private FinderPath _finderPathWithPaginationFindByModelClassNameId;
	private FinderPath _finderPathWithoutPaginationFindByModelClassNameId;
	private FinderPath _finderPathCountByModelClassNameId;

	/**
	 * Returns all the ct entries where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByModelClassNameId(long modelClassNameId) {
		return findByModelClassNameId(
			modelClassNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByModelClassNameId(
		long modelClassNameId, int start, int end) {

		return findByModelClassNameId(modelClassNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByModelClassNameId(
		long modelClassNameId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return findByModelClassNameId(
			modelClassNameId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries where modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByModelClassNameId(
		long modelClassNameId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByModelClassNameId;
			finderArgs = new Object[] {modelClassNameId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByModelClassNameId;
			finderArgs = new Object[] {
				modelClassNameId, start, end, orderByComparator
			};
		}

		List<CTEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if ((modelClassNameId != ctEntry.getModelClassNameId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_MODELCLASSNAMEID_MODELCLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modelClassNameId);

				if (!pagination) {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByModelClassNameId_First(
			long modelClassNameId, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByModelClassNameId_First(
			modelClassNameId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("modelClassNameId=");
		msg.append(modelClassNameId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByModelClassNameId_First(
		long modelClassNameId, OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByModelClassNameId(
			modelClassNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByModelClassNameId_Last(
			long modelClassNameId, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByModelClassNameId_Last(
			modelClassNameId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("modelClassNameId=");
		msg.append(modelClassNameId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByModelClassNameId_Last(
		long modelClassNameId, OrderByComparator<CTEntry> orderByComparator) {

		int count = countByModelClassNameId(modelClassNameId);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByModelClassNameId(
			modelClassNameId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where modelClassNameId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByModelClassNameId_PrevAndNext(
			long ctEntryId, long modelClassNameId,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByModelClassNameId_PrevAndNext(
				session, ctEntry, modelClassNameId, orderByComparator, true);

			array[1] = ctEntry;

			array[2] = getByModelClassNameId_PrevAndNext(
				session, ctEntry, modelClassNameId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntry getByModelClassNameId_PrevAndNext(
		Session session, CTEntry ctEntry, long modelClassNameId,
		OrderByComparator<CTEntry> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CTENTRY_WHERE);

		query.append(_FINDER_COLUMN_MODELCLASSNAMEID_MODELCLASSNAMEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(modelClassNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entries where modelClassNameId = &#63; from the database.
	 *
	 * @param modelClassNameId the model class name ID
	 */
	@Override
	public void removeByModelClassNameId(long modelClassNameId) {
		for (CTEntry ctEntry :
				findByModelClassNameId(
					modelClassNameId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where modelClassNameId = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByModelClassNameId(long modelClassNameId) {
		FinderPath finderPath = _finderPathCountByModelClassNameId;

		Object[] finderArgs = new Object[] {modelClassNameId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_MODELCLASSNAMEID_MODELCLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modelClassNameId);

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

	private static final String
		_FINDER_COLUMN_MODELCLASSNAMEID_MODELCLASSNAMEID_2 =
			"ctEntry.modelClassNameId = ?";

	private FinderPath _finderPathWithPaginationFindByC_MCNI;
	private FinderPath _finderPathWithoutPaginationFindByC_MCNI;
	private FinderPath _finderPathCountByC_MCNI;

	/**
	 * Returns all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId) {

		return findByC_MCNI(
			ctCollectionId, modelClassNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId, int start, int end) {

		return findByC_MCNI(ctCollectionId, modelClassNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return findByC_MCNI(
			ctCollectionId, modelClassNameId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MCNI(
		long ctCollectionId, long modelClassNameId, int start, int end,
		OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_MCNI;
			finderArgs = new Object[] {ctCollectionId, modelClassNameId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_MCNI;
			finderArgs = new Object[] {
				ctCollectionId, modelClassNameId, start, end, orderByComparator
			};
		}

		List<CTEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if ((ctCollectionId != ctEntry.getCtCollectionId()) ||
						(modelClassNameId != ctEntry.getModelClassNameId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_MCNI_CTCOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_MCNI_MODELCLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				qPos.add(modelClassNameId);

				if (!pagination) {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_MCNI_First(
			long ctCollectionId, long modelClassNameId,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_MCNI_First(
			ctCollectionId, modelClassNameId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append(", modelClassNameId=");
		msg.append(modelClassNameId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MCNI_First(
		long ctCollectionId, long modelClassNameId,
		OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByC_MCNI(
			ctCollectionId, modelClassNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_MCNI_Last(
			long ctCollectionId, long modelClassNameId,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_MCNI_Last(
			ctCollectionId, modelClassNameId, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append(", modelClassNameId=");
		msg.append(modelClassNameId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MCNI_Last(
		long ctCollectionId, long modelClassNameId,
		OrderByComparator<CTEntry> orderByComparator) {

		int count = countByC_MCNI(ctCollectionId, modelClassNameId);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByC_MCNI(
			ctCollectionId, modelClassNameId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByC_MCNI_PrevAndNext(
			long ctEntryId, long ctCollectionId, long modelClassNameId,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByC_MCNI_PrevAndNext(
				session, ctEntry, ctCollectionId, modelClassNameId,
				orderByComparator, true);

			array[1] = ctEntry;

			array[2] = getByC_MCNI_PrevAndNext(
				session, ctEntry, ctCollectionId, modelClassNameId,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntry getByC_MCNI_PrevAndNext(
		Session session, CTEntry ctEntry, long ctCollectionId,
		long modelClassNameId, OrderByComparator<CTEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CTENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_MCNI_CTCOLLECTIONID_2);

		query.append(_FINDER_COLUMN_C_MCNI_MODELCLASSNAMEID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ctCollectionId);

		qPos.add(modelClassNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 */
	@Override
	public void removeByC_MCNI(long ctCollectionId, long modelClassNameId) {
		for (CTEntry ctEntry :
				findByC_MCNI(
					ctCollectionId, modelClassNameId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelClassNameId = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByC_MCNI(long ctCollectionId, long modelClassNameId) {
		FinderPath finderPath = _finderPathCountByC_MCNI;

		Object[] finderArgs = new Object[] {ctCollectionId, modelClassNameId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_MCNI_CTCOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_MCNI_MODELCLASSNAMEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				qPos.add(modelClassNameId);

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

	private static final String _FINDER_COLUMN_C_MCNI_CTCOLLECTIONID_2 =
		"ctEntry.ctCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_C_MCNI_MODELCLASSNAMEID_2 =
		"ctEntry.modelClassNameId = ?";

	private FinderPath _finderPathWithPaginationFindByC_MRPK;
	private FinderPath _finderPathWithoutPaginationFindByC_MRPK;
	private FinderPath _finderPathCountByC_MRPK;

	/**
	 * Returns all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MRPK(
		long ctCollectionId, long modelResourcePrimKey) {

		return findByC_MRPK(
			ctCollectionId, modelResourcePrimKey, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MRPK(
		long ctCollectionId, long modelResourcePrimKey, int start, int end) {

		return findByC_MRPK(
			ctCollectionId, modelResourcePrimKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MRPK(
		long ctCollectionId, long modelResourcePrimKey, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return findByC_MRPK(
			ctCollectionId, modelResourcePrimKey, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MRPK(
		long ctCollectionId, long modelResourcePrimKey, int start, int end,
		OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_MRPK;
			finderArgs = new Object[] {ctCollectionId, modelResourcePrimKey};
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_MRPK;
			finderArgs = new Object[] {
				ctCollectionId, modelResourcePrimKey, start, end,
				orderByComparator
			};
		}

		List<CTEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if ((ctCollectionId != ctEntry.getCtCollectionId()) ||
						(modelResourcePrimKey !=
							ctEntry.getModelResourcePrimKey())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_MRPK_CTCOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_MRPK_MODELRESOURCEPRIMKEY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				qPos.add(modelResourcePrimKey);

				if (!pagination) {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_MRPK_First(
			long ctCollectionId, long modelResourcePrimKey,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_MRPK_First(
			ctCollectionId, modelResourcePrimKey, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append(", modelResourcePrimKey=");
		msg.append(modelResourcePrimKey);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MRPK_First(
		long ctCollectionId, long modelResourcePrimKey,
		OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByC_MRPK(
			ctCollectionId, modelResourcePrimKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_MRPK_Last(
			long ctCollectionId, long modelResourcePrimKey,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_MRPK_Last(
			ctCollectionId, modelResourcePrimKey, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append(", modelResourcePrimKey=");
		msg.append(modelResourcePrimKey);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MRPK_Last(
		long ctCollectionId, long modelResourcePrimKey,
		OrderByComparator<CTEntry> orderByComparator) {

		int count = countByC_MRPK(ctCollectionId, modelResourcePrimKey);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByC_MRPK(
			ctCollectionId, modelResourcePrimKey, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByC_MRPK_PrevAndNext(
			long ctEntryId, long ctCollectionId, long modelResourcePrimKey,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByC_MRPK_PrevAndNext(
				session, ctEntry, ctCollectionId, modelResourcePrimKey,
				orderByComparator, true);

			array[1] = ctEntry;

			array[2] = getByC_MRPK_PrevAndNext(
				session, ctEntry, ctCollectionId, modelResourcePrimKey,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntry getByC_MRPK_PrevAndNext(
		Session session, CTEntry ctEntry, long ctCollectionId,
		long modelResourcePrimKey, OrderByComparator<CTEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CTENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_MRPK_CTCOLLECTIONID_2);

		query.append(_FINDER_COLUMN_C_MRPK_MODELRESOURCEPRIMKEY_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ctCollectionId);

		qPos.add(modelResourcePrimKey);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 */
	@Override
	public void removeByC_MRPK(long ctCollectionId, long modelResourcePrimKey) {
		for (CTEntry ctEntry :
				findByC_MRPK(
					ctCollectionId, modelResourcePrimKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByC_MRPK(long ctCollectionId, long modelResourcePrimKey) {
		FinderPath finderPath = _finderPathCountByC_MRPK;

		Object[] finderArgs = new Object[] {
			ctCollectionId, modelResourcePrimKey
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_MRPK_CTCOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_MRPK_MODELRESOURCEPRIMKEY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				qPos.add(modelResourcePrimKey);

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

	private static final String _FINDER_COLUMN_C_MRPK_CTCOLLECTIONID_2 =
		"ctEntry.ctCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_C_MRPK_MODELRESOURCEPRIMKEY_2 =
		"ctEntry.modelResourcePrimKey = ?";

	private FinderPath _finderPathWithPaginationFindByC_S;
	private FinderPath _finderPathWithoutPaginationFindByC_S;
	private FinderPath _finderPathCountByC_S;

	/**
	 * Returns all the ct entries where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_S(long ctCollectionId, int status) {
		return findByC_S(
			ctCollectionId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_S(
		long ctCollectionId, int status, int start, int end) {

		return findByC_S(ctCollectionId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_S(
		long ctCollectionId, int status, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return findByC_S(
			ctCollectionId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_S(
		long ctCollectionId, int status, int start, int end,
		OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_S;
			finderArgs = new Object[] {ctCollectionId, status};
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_S;
			finderArgs = new Object[] {
				ctCollectionId, status, start, end, orderByComparator
			};
		}

		List<CTEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if ((ctCollectionId != ctEntry.getCtCollectionId()) ||
						(status != ctEntry.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_S_CTCOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				qPos.add(status);

				if (!pagination) {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_S_First(
			long ctCollectionId, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_S_First(
			ctCollectionId, status, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_S_First(
		long ctCollectionId, int status,
		OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByC_S(
			ctCollectionId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_S_Last(
			long ctCollectionId, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_S_Last(
			ctCollectionId, status, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_S_Last(
		long ctCollectionId, int status,
		OrderByComparator<CTEntry> orderByComparator) {

		int count = countByC_S(ctCollectionId, status);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByC_S(
			ctCollectionId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByC_S_PrevAndNext(
			long ctEntryId, long ctCollectionId, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByC_S_PrevAndNext(
				session, ctEntry, ctCollectionId, status, orderByComparator,
				true);

			array[1] = ctEntry;

			array[2] = getByC_S_PrevAndNext(
				session, ctEntry, ctCollectionId, status, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntry getByC_S_PrevAndNext(
		Session session, CTEntry ctEntry, long ctCollectionId, int status,
		OrderByComparator<CTEntry> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CTENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_S_CTCOLLECTIONID_2);

		query.append(_FINDER_COLUMN_C_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ctCollectionId);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; and status = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 */
	@Override
	public void removeByC_S(long ctCollectionId, int status) {
		for (CTEntry ctEntry :
				findByC_S(
					ctCollectionId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param status the status
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByC_S(long ctCollectionId, int status) {
		FinderPath finderPath = _finderPathCountByC_S;

		Object[] finderArgs = new Object[] {ctCollectionId, status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_S_CTCOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_S_CTCOLLECTIONID_2 =
		"ctEntry.ctCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_C_S_STATUS_2 =
		"ctEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByMCNI_MCPK;
	private FinderPath _finderPathWithoutPaginationFindByMCNI_MCPK;
	private FinderPath _finderPathCountByMCNI_MCPK;

	/**
	 * Returns all the ct entries where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByMCNI_MCPK(
		long modelClassNameId, long modelClassPK) {

		return findByMCNI_MCPK(
			modelClassNameId, modelClassPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByMCNI_MCPK(
		long modelClassNameId, long modelClassPK, int start, int end) {

		return findByMCNI_MCPK(
			modelClassNameId, modelClassPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByMCNI_MCPK(
		long modelClassNameId, long modelClassPK, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return findByMCNI_MCPK(
			modelClassNameId, modelClassPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the ct entries where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByMCNI_MCPK(
		long modelClassNameId, long modelClassPK, int start, int end,
		OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByMCNI_MCPK;
			finderArgs = new Object[] {modelClassNameId, modelClassPK};
		}
		else {
			finderPath = _finderPathWithPaginationFindByMCNI_MCPK;
			finderArgs = new Object[] {
				modelClassNameId, modelClassPK, start, end, orderByComparator
			};
		}

		List<CTEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if ((modelClassNameId != ctEntry.getModelClassNameId()) ||
						(modelClassPK != ctEntry.getModelClassPK())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_MCNI_MCPK_MODELCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_MCNI_MCPK_MODELCLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modelClassNameId);

				qPos.add(modelClassPK);

				if (!pagination) {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first ct entry in the ordered set where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByMCNI_MCPK_First(
			long modelClassNameId, long modelClassPK,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByMCNI_MCPK_First(
			modelClassNameId, modelClassPK, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("modelClassNameId=");
		msg.append(modelClassNameId);

		msg.append(", modelClassPK=");
		msg.append(modelClassPK);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByMCNI_MCPK_First(
		long modelClassNameId, long modelClassPK,
		OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByMCNI_MCPK(
			modelClassNameId, modelClassPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByMCNI_MCPK_Last(
			long modelClassNameId, long modelClassPK,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByMCNI_MCPK_Last(
			modelClassNameId, modelClassPK, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("modelClassNameId=");
		msg.append(modelClassNameId);

		msg.append(", modelClassPK=");
		msg.append(modelClassPK);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByMCNI_MCPK_Last(
		long modelClassNameId, long modelClassPK,
		OrderByComparator<CTEntry> orderByComparator) {

		int count = countByMCNI_MCPK(modelClassNameId, modelClassPK);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByMCNI_MCPK(
			modelClassNameId, modelClassPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByMCNI_MCPK_PrevAndNext(
			long ctEntryId, long modelClassNameId, long modelClassPK,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByMCNI_MCPK_PrevAndNext(
				session, ctEntry, modelClassNameId, modelClassPK,
				orderByComparator, true);

			array[1] = ctEntry;

			array[2] = getByMCNI_MCPK_PrevAndNext(
				session, ctEntry, modelClassNameId, modelClassPK,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntry getByMCNI_MCPK_PrevAndNext(
		Session session, CTEntry ctEntry, long modelClassNameId,
		long modelClassPK, OrderByComparator<CTEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_CTENTRY_WHERE);

		query.append(_FINDER_COLUMN_MCNI_MCPK_MODELCLASSNAMEID_2);

		query.append(_FINDER_COLUMN_MCNI_MCPK_MODELCLASSPK_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(modelClassNameId);

		qPos.add(modelClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entries where modelClassNameId = &#63; and modelClassPK = &#63; from the database.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 */
	@Override
	public void removeByMCNI_MCPK(long modelClassNameId, long modelClassPK) {
		for (CTEntry ctEntry :
				findByMCNI_MCPK(
					modelClassNameId, modelClassPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByMCNI_MCPK(long modelClassNameId, long modelClassPK) {
		FinderPath finderPath = _finderPathCountByMCNI_MCPK;

		Object[] finderArgs = new Object[] {modelClassNameId, modelClassPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_MCNI_MCPK_MODELCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_MCNI_MCPK_MODELCLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(modelClassNameId);

				qPos.add(modelClassPK);

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

	private static final String _FINDER_COLUMN_MCNI_MCPK_MODELCLASSNAMEID_2 =
		"ctEntry.modelClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_MCNI_MCPK_MODELCLASSPK_2 =
		"ctEntry.modelClassPK = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId_MCNI_MCPK;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId_MCNI_MCPK;
	private FinderPath _finderPathCountByCompanyId_MCNI_MCPK;

	/**
	 * Returns all the ct entries where companyId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByCompanyId_MCNI_MCPK(
		long companyId, long modelClassNameId, long modelClassPK) {

		return findByCompanyId_MCNI_MCPK(
			companyId, modelClassNameId, modelClassPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where companyId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByCompanyId_MCNI_MCPK(
		long companyId, long modelClassNameId, long modelClassPK, int start,
		int end) {

		return findByCompanyId_MCNI_MCPK(
			companyId, modelClassNameId, modelClassPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where companyId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByCompanyId_MCNI_MCPK(
		long companyId, long modelClassNameId, long modelClassPK, int start,
		int end, OrderByComparator<CTEntry> orderByComparator) {

		return findByCompanyId_MCNI_MCPK(
			companyId, modelClassNameId, modelClassPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries where companyId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByCompanyId_MCNI_MCPK(
		long companyId, long modelClassNameId, long modelClassPK, int start,
		int end, OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByCompanyId_MCNI_MCPK;
			finderArgs = new Object[] {
				companyId, modelClassNameId, modelClassPK
			};
		}
		else {
			finderPath = _finderPathWithPaginationFindByCompanyId_MCNI_MCPK;
			finderArgs = new Object[] {
				companyId, modelClassNameId, modelClassPK, start, end,
				orderByComparator
			};
		}

		List<CTEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if ((companyId != ctEntry.getCompanyId()) ||
						(modelClassNameId != ctEntry.getModelClassNameId()) ||
						(modelClassPK != ctEntry.getModelClassPK())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_MCNI_MCPK_COMPANYID_2);

			query.append(_FINDER_COLUMN_COMPANYID_MCNI_MCPK_MODELCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_COMPANYID_MCNI_MCPK_MODELCLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(modelClassNameId);

				qPos.add(modelClassPK);

				if (!pagination) {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first ct entry in the ordered set where companyId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByCompanyId_MCNI_MCPK_First(
			long companyId, long modelClassNameId, long modelClassPK,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByCompanyId_MCNI_MCPK_First(
			companyId, modelClassNameId, modelClassPK, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", modelClassNameId=");
		msg.append(modelClassNameId);

		msg.append(", modelClassPK=");
		msg.append(modelClassPK);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where companyId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByCompanyId_MCNI_MCPK_First(
		long companyId, long modelClassNameId, long modelClassPK,
		OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByCompanyId_MCNI_MCPK(
			companyId, modelClassNameId, modelClassPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where companyId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByCompanyId_MCNI_MCPK_Last(
			long companyId, long modelClassNameId, long modelClassPK,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByCompanyId_MCNI_MCPK_Last(
			companyId, modelClassNameId, modelClassPK, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", modelClassNameId=");
		msg.append(modelClassNameId);

		msg.append(", modelClassPK=");
		msg.append(modelClassPK);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where companyId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByCompanyId_MCNI_MCPK_Last(
		long companyId, long modelClassNameId, long modelClassPK,
		OrderByComparator<CTEntry> orderByComparator) {

		int count = countByCompanyId_MCNI_MCPK(
			companyId, modelClassNameId, modelClassPK);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByCompanyId_MCNI_MCPK(
			companyId, modelClassNameId, modelClassPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where companyId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param companyId the company ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByCompanyId_MCNI_MCPK_PrevAndNext(
			long ctEntryId, long companyId, long modelClassNameId,
			long modelClassPK, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByCompanyId_MCNI_MCPK_PrevAndNext(
				session, ctEntry, companyId, modelClassNameId, modelClassPK,
				orderByComparator, true);

			array[1] = ctEntry;

			array[2] = getByCompanyId_MCNI_MCPK_PrevAndNext(
				session, ctEntry, companyId, modelClassNameId, modelClassPK,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntry getByCompanyId_MCNI_MCPK_PrevAndNext(
		Session session, CTEntry ctEntry, long companyId, long modelClassNameId,
		long modelClassPK, OrderByComparator<CTEntry> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_CTENTRY_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_MCNI_MCPK_COMPANYID_2);

		query.append(_FINDER_COLUMN_COMPANYID_MCNI_MCPK_MODELCLASSNAMEID_2);

		query.append(_FINDER_COLUMN_COMPANYID_MCNI_MCPK_MODELCLASSPK_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(modelClassNameId);

		qPos.add(modelClassPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entries where companyId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 */
	@Override
	public void removeByCompanyId_MCNI_MCPK(
		long companyId, long modelClassNameId, long modelClassPK) {

		for (CTEntry ctEntry :
				findByCompanyId_MCNI_MCPK(
					companyId, modelClassNameId, modelClassPK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where companyId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param companyId the company ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByCompanyId_MCNI_MCPK(
		long companyId, long modelClassNameId, long modelClassPK) {

		FinderPath finderPath = _finderPathCountByCompanyId_MCNI_MCPK;

		Object[] finderArgs = new Object[] {
			companyId, modelClassNameId, modelClassPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_MCNI_MCPK_COMPANYID_2);

			query.append(_FINDER_COLUMN_COMPANYID_MCNI_MCPK_MODELCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_COMPANYID_MCNI_MCPK_MODELCLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(modelClassNameId);

				qPos.add(modelClassPK);

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

	private static final String _FINDER_COLUMN_COMPANYID_MCNI_MCPK_COMPANYID_2 =
		"ctEntry.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_COMPANYID_MCNI_MCPK_MODELCLASSNAMEID_2 =
			"ctEntry.modelClassNameId = ? AND ";

	private static final String
		_FINDER_COLUMN_COMPANYID_MCNI_MCPK_MODELCLASSPK_2 =
			"ctEntry.modelClassPK = ?";

	private FinderPath _finderPathFetchByC_MCNI_MCPK;
	private FinderPath _finderPathCountByC_MCNI_MCPK;

	/**
	 * Returns the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_MCNI_MCPK(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK);

		if (ctEntry == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("ctCollectionId=");
			msg.append(ctCollectionId);

			msg.append(", modelClassNameId=");
			msg.append(modelClassNameId);

			msg.append(", modelClassPK=");
			msg.append(modelClassPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryException(msg.toString());
		}

		return ctEntry;
	}

	/**
	 * Returns the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		return fetchByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK, true);
	}

	/**
	 * Returns the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK,
		boolean retrieveFromCache) {

		Object[] finderArgs = new Object[] {
			ctCollectionId, modelClassNameId, modelClassPK
		};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_MCNI_MCPK, finderArgs, this);
		}

		if (result instanceof CTEntry) {
			CTEntry ctEntry = (CTEntry)result;

			if ((ctCollectionId != ctEntry.getCtCollectionId()) ||
				(modelClassNameId != ctEntry.getModelClassNameId()) ||
				(modelClassPK != ctEntry.getModelClassPK())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_MCNI_MCPK_CTCOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_MCNI_MCPK_MODELCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_MCNI_MCPK_MODELCLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				qPos.add(modelClassNameId);

				qPos.add(modelClassPK);

				List<CTEntry> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(
						_finderPathFetchByC_MCNI_MCPK, finderArgs, list);
				}
				else {
					CTEntry ctEntry = list.get(0);

					result = ctEntry;

					cacheResult(ctEntry);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathFetchByC_MCNI_MCPK, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CTEntry)result;
		}
	}

	/**
	 * Removes the ct entry where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the ct entry that was removed
	 */
	@Override
	public CTEntry removeByC_MCNI_MCPK(
			long ctCollectionId, long modelClassNameId, long modelClassPK)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByC_MCNI_MCPK(
			ctCollectionId, modelClassNameId, modelClassPK);

		return remove(ctEntry);
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and modelClassPK = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param modelClassPK the model class pk
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByC_MCNI_MCPK(
		long ctCollectionId, long modelClassNameId, long modelClassPK) {

		FinderPath finderPath = _finderPathCountByC_MCNI_MCPK;

		Object[] finderArgs = new Object[] {
			ctCollectionId, modelClassNameId, modelClassPK
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_MCNI_MCPK_CTCOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_MCNI_MCPK_MODELCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_MCNI_MCPK_MODELCLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				qPos.add(modelClassNameId);

				qPos.add(modelClassPK);

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

	private static final String _FINDER_COLUMN_C_MCNI_MCPK_CTCOLLECTIONID_2 =
		"ctEntry.ctCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_C_MCNI_MCPK_MODELCLASSNAMEID_2 =
		"ctEntry.modelClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_MCNI_MCPK_MODELCLASSPK_2 =
		"ctEntry.modelClassPK = ?";

	private FinderPath _finderPathWithPaginationFindByC_MCNI_S;
	private FinderPath _finderPathWithoutPaginationFindByC_MCNI_S;
	private FinderPath _finderPathCountByC_MCNI_S;

	/**
	 * Returns all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MCNI_S(
		long ctCollectionId, long modelClassNameId, int status) {

		return findByC_MCNI_S(
			ctCollectionId, modelClassNameId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MCNI_S(
		long ctCollectionId, long modelClassNameId, int status, int start,
		int end) {

		return findByC_MCNI_S(
			ctCollectionId, modelClassNameId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MCNI_S(
		long ctCollectionId, long modelClassNameId, int status, int start,
		int end, OrderByComparator<CTEntry> orderByComparator) {

		return findByC_MCNI_S(
			ctCollectionId, modelClassNameId, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MCNI_S(
		long ctCollectionId, long modelClassNameId, int status, int start,
		int end, OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_MCNI_S;
			finderArgs = new Object[] {
				ctCollectionId, modelClassNameId, status
			};
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_MCNI_S;
			finderArgs = new Object[] {
				ctCollectionId, modelClassNameId, status, start, end,
				orderByComparator
			};
		}

		List<CTEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if ((ctCollectionId != ctEntry.getCtCollectionId()) ||
						(modelClassNameId != ctEntry.getModelClassNameId()) ||
						(status != ctEntry.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_MCNI_S_CTCOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_MCNI_S_MODELCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_MCNI_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				qPos.add(modelClassNameId);

				qPos.add(status);

				if (!pagination) {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_MCNI_S_First(
			long ctCollectionId, long modelClassNameId, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_MCNI_S_First(
			ctCollectionId, modelClassNameId, status, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append(", modelClassNameId=");
		msg.append(modelClassNameId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MCNI_S_First(
		long ctCollectionId, long modelClassNameId, int status,
		OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByC_MCNI_S(
			ctCollectionId, modelClassNameId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_MCNI_S_Last(
			long ctCollectionId, long modelClassNameId, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_MCNI_S_Last(
			ctCollectionId, modelClassNameId, status, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append(", modelClassNameId=");
		msg.append(modelClassNameId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MCNI_S_Last(
		long ctCollectionId, long modelClassNameId, int status,
		OrderByComparator<CTEntry> orderByComparator) {

		int count = countByC_MCNI_S(ctCollectionId, modelClassNameId, status);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByC_MCNI_S(
			ctCollectionId, modelClassNameId, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByC_MCNI_S_PrevAndNext(
			long ctEntryId, long ctCollectionId, long modelClassNameId,
			int status, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByC_MCNI_S_PrevAndNext(
				session, ctEntry, ctCollectionId, modelClassNameId, status,
				orderByComparator, true);

			array[1] = ctEntry;

			array[2] = getByC_MCNI_S_PrevAndNext(
				session, ctEntry, ctCollectionId, modelClassNameId, status,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntry getByC_MCNI_S_PrevAndNext(
		Session session, CTEntry ctEntry, long ctCollectionId,
		long modelClassNameId, int status,
		OrderByComparator<CTEntry> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_CTENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_MCNI_S_CTCOLLECTIONID_2);

		query.append(_FINDER_COLUMN_C_MCNI_S_MODELCLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_MCNI_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ctCollectionId);

		qPos.add(modelClassNameId);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 */
	@Override
	public void removeByC_MCNI_S(
		long ctCollectionId, long modelClassNameId, int status) {

		for (CTEntry ctEntry :
				findByC_MCNI_S(
					ctCollectionId, modelClassNameId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelClassNameId = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelClassNameId the model class name ID
	 * @param status the status
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByC_MCNI_S(
		long ctCollectionId, long modelClassNameId, int status) {

		FinderPath finderPath = _finderPathCountByC_MCNI_S;

		Object[] finderArgs = new Object[] {
			ctCollectionId, modelClassNameId, status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_MCNI_S_CTCOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_MCNI_S_MODELCLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_MCNI_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				qPos.add(modelClassNameId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_MCNI_S_CTCOLLECTIONID_2 =
		"ctEntry.ctCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_C_MCNI_S_MODELCLASSNAMEID_2 =
		"ctEntry.modelClassNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_MCNI_S_STATUS_2 =
		"ctEntry.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_MRPK_S;
	private FinderPath _finderPathWithoutPaginationFindByC_MRPK_S;
	private FinderPath _finderPathCountByC_MRPK_S;

	/**
	 * Returns all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @return the matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MRPK_S(
		long ctCollectionId, long modelResourcePrimKey, int status) {

		return findByC_MRPK_S(
			ctCollectionId, modelResourcePrimKey, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MRPK_S(
		long ctCollectionId, long modelResourcePrimKey, int status, int start,
		int end) {

		return findByC_MRPK_S(
			ctCollectionId, modelResourcePrimKey, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MRPK_S(
		long ctCollectionId, long modelResourcePrimKey, int status, int start,
		int end, OrderByComparator<CTEntry> orderByComparator) {

		return findByC_MRPK_S(
			ctCollectionId, modelResourcePrimKey, status, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ct entries
	 */
	@Override
	public List<CTEntry> findByC_MRPK_S(
		long ctCollectionId, long modelResourcePrimKey, int status, int start,
		int end, OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_MRPK_S;
			finderArgs = new Object[] {
				ctCollectionId, modelResourcePrimKey, status
			};
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_MRPK_S;
			finderArgs = new Object[] {
				ctCollectionId, modelResourcePrimKey, status, start, end,
				orderByComparator
			};
		}

		List<CTEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CTEntry ctEntry : list) {
					if ((ctCollectionId != ctEntry.getCtCollectionId()) ||
						(modelResourcePrimKey !=
							ctEntry.getModelResourcePrimKey()) ||
						(status != ctEntry.getStatus())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_MRPK_S_CTCOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_MRPK_S_MODELRESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_C_MRPK_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(CTEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				qPos.add(modelResourcePrimKey);

				qPos.add(status);

				if (!pagination) {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_MRPK_S_First(
			long ctCollectionId, long modelResourcePrimKey, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_MRPK_S_First(
			ctCollectionId, modelResourcePrimKey, status, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append(", modelResourcePrimKey=");
		msg.append(modelResourcePrimKey);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MRPK_S_First(
		long ctCollectionId, long modelResourcePrimKey, int status,
		OrderByComparator<CTEntry> orderByComparator) {

		List<CTEntry> list = findByC_MRPK_S(
			ctCollectionId, modelResourcePrimKey, status, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry
	 * @throws NoSuchEntryException if a matching ct entry could not be found
	 */
	@Override
	public CTEntry findByC_MRPK_S_Last(
			long ctCollectionId, long modelResourcePrimKey, int status,
			OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByC_MRPK_S_Last(
			ctCollectionId, modelResourcePrimKey, status, orderByComparator);

		if (ctEntry != null) {
			return ctEntry;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ctCollectionId=");
		msg.append(ctCollectionId);

		msg.append(", modelResourcePrimKey=");
		msg.append(modelResourcePrimKey);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ct entry, or <code>null</code> if a matching ct entry could not be found
	 */
	@Override
	public CTEntry fetchByC_MRPK_S_Last(
		long ctCollectionId, long modelResourcePrimKey, int status,
		OrderByComparator<CTEntry> orderByComparator) {

		int count = countByC_MRPK_S(
			ctCollectionId, modelResourcePrimKey, status);

		if (count == 0) {
			return null;
		}

		List<CTEntry> list = findByC_MRPK_S(
			ctCollectionId, modelResourcePrimKey, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ct entries before and after the current ct entry in the ordered set where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctEntryId the primary key of the current ct entry
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry[] findByC_MRPK_S_PrevAndNext(
			long ctEntryId, long ctCollectionId, long modelResourcePrimKey,
			int status, OrderByComparator<CTEntry> orderByComparator)
		throws NoSuchEntryException {

		CTEntry ctEntry = findByPrimaryKey(ctEntryId);

		Session session = null;

		try {
			session = openSession();

			CTEntry[] array = new CTEntryImpl[3];

			array[0] = getByC_MRPK_S_PrevAndNext(
				session, ctEntry, ctCollectionId, modelResourcePrimKey, status,
				orderByComparator, true);

			array[1] = ctEntry;

			array[2] = getByC_MRPK_S_PrevAndNext(
				session, ctEntry, ctCollectionId, modelResourcePrimKey, status,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CTEntry getByC_MRPK_S_PrevAndNext(
		Session session, CTEntry ctEntry, long ctCollectionId,
		long modelResourcePrimKey, int status,
		OrderByComparator<CTEntry> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_CTENTRY_WHERE);

		query.append(_FINDER_COLUMN_C_MRPK_S_CTCOLLECTIONID_2);

		query.append(_FINDER_COLUMN_C_MRPK_S_MODELRESOURCEPRIMKEY_2);

		query.append(_FINDER_COLUMN_C_MRPK_S_STATUS_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

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
			query.append(CTEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ctCollectionId);

		qPos.add(modelResourcePrimKey);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(ctEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<CTEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63; from the database.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 */
	@Override
	public void removeByC_MRPK_S(
		long ctCollectionId, long modelResourcePrimKey, int status) {

		for (CTEntry ctEntry :
				findByC_MRPK_S(
					ctCollectionId, modelResourcePrimKey, status,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries where ctCollectionId = &#63; and modelResourcePrimKey = &#63; and status = &#63;.
	 *
	 * @param ctCollectionId the ct collection ID
	 * @param modelResourcePrimKey the model resource prim key
	 * @param status the status
	 * @return the number of matching ct entries
	 */
	@Override
	public int countByC_MRPK_S(
		long ctCollectionId, long modelResourcePrimKey, int status) {

		FinderPath finderPath = _finderPathCountByC_MRPK_S;

		Object[] finderArgs = new Object[] {
			ctCollectionId, modelResourcePrimKey, status
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_CTENTRY_WHERE);

			query.append(_FINDER_COLUMN_C_MRPK_S_CTCOLLECTIONID_2);

			query.append(_FINDER_COLUMN_C_MRPK_S_MODELRESOURCEPRIMKEY_2);

			query.append(_FINDER_COLUMN_C_MRPK_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ctCollectionId);

				qPos.add(modelResourcePrimKey);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_C_MRPK_S_CTCOLLECTIONID_2 =
		"ctEntry.ctCollectionId = ? AND ";

	private static final String _FINDER_COLUMN_C_MRPK_S_MODELRESOURCEPRIMKEY_2 =
		"ctEntry.modelResourcePrimKey = ? AND ";

	private static final String _FINDER_COLUMN_C_MRPK_S_STATUS_2 =
		"ctEntry.status = ?";

	public CTEntryPersistenceImpl() {
		setModelClass(CTEntry.class);

		setModelImplClass(CTEntryImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the ct entry in the entity cache if it is enabled.
	 *
	 * @param ctEntry the ct entry
	 */
	@Override
	public void cacheResult(CTEntry ctEntry) {
		entityCache.putResult(
			entityCacheEnabled, CTEntryImpl.class, ctEntry.getPrimaryKey(),
			ctEntry);

		finderCache.putResult(
			_finderPathFetchByC_MCNI_MCPK,
			new Object[] {
				ctEntry.getCtCollectionId(), ctEntry.getModelClassNameId(),
				ctEntry.getModelClassPK()
			},
			ctEntry);

		ctEntry.resetOriginalValues();
	}

	/**
	 * Caches the ct entries in the entity cache if it is enabled.
	 *
	 * @param ctEntries the ct entries
	 */
	@Override
	public void cacheResult(List<CTEntry> ctEntries) {
		for (CTEntry ctEntry : ctEntries) {
			if (entityCache.getResult(
					entityCacheEnabled, CTEntryImpl.class,
					ctEntry.getPrimaryKey()) == null) {

				cacheResult(ctEntry);
			}
			else {
				ctEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ct entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ct entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTEntry ctEntry) {
		entityCache.removeResult(
			entityCacheEnabled, CTEntryImpl.class, ctEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CTEntryModelImpl)ctEntry, true);
	}

	@Override
	public void clearCache(List<CTEntry> ctEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CTEntry ctEntry : ctEntries) {
			entityCache.removeResult(
				entityCacheEnabled, CTEntryImpl.class, ctEntry.getPrimaryKey());

			clearUniqueFindersCache((CTEntryModelImpl)ctEntry, true);
		}
	}

	protected void cacheUniqueFindersCache(CTEntryModelImpl ctEntryModelImpl) {
		Object[] args = new Object[] {
			ctEntryModelImpl.getCtCollectionId(),
			ctEntryModelImpl.getModelClassNameId(),
			ctEntryModelImpl.getModelClassPK()
		};

		finderCache.putResult(
			_finderPathCountByC_MCNI_MCPK, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_MCNI_MCPK, args, ctEntryModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CTEntryModelImpl ctEntryModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				ctEntryModelImpl.getCtCollectionId(),
				ctEntryModelImpl.getModelClassNameId(),
				ctEntryModelImpl.getModelClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_MCNI_MCPK, args);
			finderCache.removeResult(_finderPathFetchByC_MCNI_MCPK, args);
		}

		if ((ctEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_MCNI_MCPK.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				ctEntryModelImpl.getOriginalCtCollectionId(),
				ctEntryModelImpl.getOriginalModelClassNameId(),
				ctEntryModelImpl.getOriginalModelClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_MCNI_MCPK, args);
			finderCache.removeResult(_finderPathFetchByC_MCNI_MCPK, args);
		}
	}

	/**
	 * Creates a new ct entry with the primary key. Does not add the ct entry to the database.
	 *
	 * @param ctEntryId the primary key for the new ct entry
	 * @return the new ct entry
	 */
	@Override
	public CTEntry create(long ctEntryId) {
		CTEntry ctEntry = new CTEntryImpl();

		ctEntry.setNew(true);
		ctEntry.setPrimaryKey(ctEntryId);

		ctEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return ctEntry;
	}

	/**
	 * Removes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry remove(long ctEntryId) throws NoSuchEntryException {
		return remove((Serializable)ctEntryId);
	}

	/**
	 * Removes the ct entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ct entry
	 * @return the ct entry that was removed
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry remove(Serializable primaryKey) throws NoSuchEntryException {
		Session session = null;

		try {
			session = openSession();

			CTEntry ctEntry = (CTEntry)session.get(
				CTEntryImpl.class, primaryKey);

			if (ctEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(ctEntry);
		}
		catch (NoSuchEntryException nsee) {
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
	protected CTEntry removeImpl(CTEntry ctEntry) {
		ctEntryToCTEntryAggregateTableMapper.deleteLeftPrimaryKeyTableMappings(
			ctEntry.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ctEntry)) {
				ctEntry = (CTEntry)session.get(
					CTEntryImpl.class, ctEntry.getPrimaryKeyObj());
			}

			if (ctEntry != null) {
				session.delete(ctEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ctEntry != null) {
			clearCache(ctEntry);
		}

		return ctEntry;
	}

	@Override
	public CTEntry updateImpl(CTEntry ctEntry) {
		boolean isNew = ctEntry.isNew();

		if (!(ctEntry instanceof CTEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(ctEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(ctEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in ctEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTEntry implementation " +
					ctEntry.getClass());
		}

		CTEntryModelImpl ctEntryModelImpl = (CTEntryModelImpl)ctEntry;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (ctEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				ctEntry.setCreateDate(now);
			}
			else {
				ctEntry.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!ctEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				ctEntry.setModifiedDate(now);
			}
			else {
				ctEntry.setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctEntry.isNew()) {
				session.save(ctEntry);

				ctEntry.setNew(false);
			}
			else {
				ctEntry = (CTEntry)session.merge(ctEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {ctEntryModelImpl.getCtCollectionId()};

			finderCache.removeResult(_finderPathCountByCTCollectionId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCTCollectionId, args);

			args = new Object[] {ctEntryModelImpl.getModelClassNameId()};

			finderCache.removeResult(_finderPathCountByModelClassNameId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByModelClassNameId, args);

			args = new Object[] {
				ctEntryModelImpl.getCtCollectionId(),
				ctEntryModelImpl.getModelClassNameId()
			};

			finderCache.removeResult(_finderPathCountByC_MCNI, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_MCNI, args);

			args = new Object[] {
				ctEntryModelImpl.getCtCollectionId(),
				ctEntryModelImpl.getModelResourcePrimKey()
			};

			finderCache.removeResult(_finderPathCountByC_MRPK, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_MRPK, args);

			args = new Object[] {
				ctEntryModelImpl.getCtCollectionId(),
				ctEntryModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByC_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_S, args);

			args = new Object[] {
				ctEntryModelImpl.getModelClassNameId(),
				ctEntryModelImpl.getModelClassPK()
			};

			finderCache.removeResult(_finderPathCountByMCNI_MCPK, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByMCNI_MCPK, args);

			args = new Object[] {
				ctEntryModelImpl.getCompanyId(),
				ctEntryModelImpl.getModelClassNameId(),
				ctEntryModelImpl.getModelClassPK()
			};

			finderCache.removeResult(
				_finderPathCountByCompanyId_MCNI_MCPK, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCompanyId_MCNI_MCPK, args);

			args = new Object[] {
				ctEntryModelImpl.getCtCollectionId(),
				ctEntryModelImpl.getModelClassNameId(),
				ctEntryModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByC_MCNI_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_MCNI_S, args);

			args = new Object[] {
				ctEntryModelImpl.getCtCollectionId(),
				ctEntryModelImpl.getModelResourcePrimKey(),
				ctEntryModelImpl.getStatus()
			};

			finderCache.removeResult(_finderPathCountByC_MRPK_S, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_MRPK_S, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((ctEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCTCollectionId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ctEntryModelImpl.getOriginalCtCollectionId()
				};

				finderCache.removeResult(
					_finderPathCountByCTCollectionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCTCollectionId, args);

				args = new Object[] {ctEntryModelImpl.getCtCollectionId()};

				finderCache.removeResult(
					_finderPathCountByCTCollectionId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCTCollectionId, args);
			}

			if ((ctEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByModelClassNameId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ctEntryModelImpl.getOriginalModelClassNameId()
				};

				finderCache.removeResult(
					_finderPathCountByModelClassNameId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByModelClassNameId, args);

				args = new Object[] {ctEntryModelImpl.getModelClassNameId()};

				finderCache.removeResult(
					_finderPathCountByModelClassNameId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByModelClassNameId, args);
			}

			if ((ctEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_MCNI.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ctEntryModelImpl.getOriginalCtCollectionId(),
					ctEntryModelImpl.getOriginalModelClassNameId()
				};

				finderCache.removeResult(_finderPathCountByC_MCNI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_MCNI, args);

				args = new Object[] {
					ctEntryModelImpl.getCtCollectionId(),
					ctEntryModelImpl.getModelClassNameId()
				};

				finderCache.removeResult(_finderPathCountByC_MCNI, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_MCNI, args);
			}

			if ((ctEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_MRPK.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ctEntryModelImpl.getOriginalCtCollectionId(),
					ctEntryModelImpl.getOriginalModelResourcePrimKey()
				};

				finderCache.removeResult(_finderPathCountByC_MRPK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_MRPK, args);

				args = new Object[] {
					ctEntryModelImpl.getCtCollectionId(),
					ctEntryModelImpl.getModelResourcePrimKey()
				};

				finderCache.removeResult(_finderPathCountByC_MRPK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_MRPK, args);
			}

			if ((ctEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					ctEntryModelImpl.getOriginalCtCollectionId(),
					ctEntryModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByC_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_S, args);

				args = new Object[] {
					ctEntryModelImpl.getCtCollectionId(),
					ctEntryModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByC_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_S, args);
			}

			if ((ctEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByMCNI_MCPK.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ctEntryModelImpl.getOriginalModelClassNameId(),
					ctEntryModelImpl.getOriginalModelClassPK()
				};

				finderCache.removeResult(_finderPathCountByMCNI_MCPK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByMCNI_MCPK, args);

				args = new Object[] {
					ctEntryModelImpl.getModelClassNameId(),
					ctEntryModelImpl.getModelClassPK()
				};

				finderCache.removeResult(_finderPathCountByMCNI_MCPK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByMCNI_MCPK, args);
			}

			if ((ctEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId_MCNI_MCPK.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ctEntryModelImpl.getOriginalCompanyId(),
					ctEntryModelImpl.getOriginalModelClassNameId(),
					ctEntryModelImpl.getOriginalModelClassPK()
				};

				finderCache.removeResult(
					_finderPathCountByCompanyId_MCNI_MCPK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId_MCNI_MCPK,
					args);

				args = new Object[] {
					ctEntryModelImpl.getCompanyId(),
					ctEntryModelImpl.getModelClassNameId(),
					ctEntryModelImpl.getModelClassPK()
				};

				finderCache.removeResult(
					_finderPathCountByCompanyId_MCNI_MCPK, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCompanyId_MCNI_MCPK,
					args);
			}

			if ((ctEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_MCNI_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ctEntryModelImpl.getOriginalCtCollectionId(),
					ctEntryModelImpl.getOriginalModelClassNameId(),
					ctEntryModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByC_MCNI_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_MCNI_S, args);

				args = new Object[] {
					ctEntryModelImpl.getCtCollectionId(),
					ctEntryModelImpl.getModelClassNameId(),
					ctEntryModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByC_MCNI_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_MCNI_S, args);
			}

			if ((ctEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_MRPK_S.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					ctEntryModelImpl.getOriginalCtCollectionId(),
					ctEntryModelImpl.getOriginalModelResourcePrimKey(),
					ctEntryModelImpl.getOriginalStatus()
				};

				finderCache.removeResult(_finderPathCountByC_MRPK_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_MRPK_S, args);

				args = new Object[] {
					ctEntryModelImpl.getCtCollectionId(),
					ctEntryModelImpl.getModelResourcePrimKey(),
					ctEntryModelImpl.getStatus()
				};

				finderCache.removeResult(_finderPathCountByC_MRPK_S, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_MRPK_S, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, CTEntryImpl.class, ctEntry.getPrimaryKey(),
			ctEntry, false);

		clearUniqueFindersCache(ctEntryModelImpl, false);
		cacheUniqueFindersCache(ctEntryModelImpl);

		ctEntry.resetOriginalValues();

		return ctEntry;
	}

	/**
	 * Returns the ct entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ct entry
	 * @return the ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		CTEntry ctEntry = fetchByPrimaryKey(primaryKey);

		if (ctEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return ctEntry;
	}

	/**
	 * Returns the ct entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry
	 * @throws NoSuchEntryException if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry findByPrimaryKey(long ctEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)ctEntryId);
	}

	/**
	 * Returns the ct entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param ctEntryId the primary key of the ct entry
	 * @return the ct entry, or <code>null</code> if a ct entry with the primary key could not be found
	 */
	@Override
	public CTEntry fetchByPrimaryKey(long ctEntryId) {
		return fetchByPrimaryKey((Serializable)ctEntryId);
	}

	/**
	 * Returns all the ct entries.
	 *
	 * @return the ct entries
	 */
	@Override
	public List<CTEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @return the range of ct entries
	 */
	@Override
	public List<CTEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries
	 */
	@Override
	public List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ct entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ct entries
	 * @param end the upper bound of the range of ct entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ct entries
	 */
	@Override
	public List<CTEntry> findAll(
		int start, int end, OrderByComparator<CTEntry> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CTEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CTEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CTENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CTENTRY;

				if (pagination) {
					sql = sql.concat(CTEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTEntry>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Removes all the ct entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTEntry ctEntry : findAll()) {
			remove(ctEntry);
		}
	}

	/**
	 * Returns the number of ct entries.
	 *
	 * @return the number of ct entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CTENTRY);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Returns the primaryKeys of ct entry aggregates associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return long[] of the primaryKeys of ct entry aggregates associated with the ct entry
	 */
	@Override
	public long[] getCTEntryAggregatePrimaryKeys(long pk) {
		long[] pks = ctEntryToCTEntryAggregateTableMapper.getRightPrimaryKeys(
			pk);

		return pks.clone();
	}

	/**
	 * Returns all the ct entry associated with the ct entry aggregate.
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @return the ct entries associated with the ct entry aggregate
	 */
	@Override
	public List<CTEntry> getCTEntryAggregateCTEntries(long pk) {
		return getCTEntryAggregateCTEntries(
			pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns all the ct entry associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @return the range of ct entries associated with the ct entry aggregate
	 */
	@Override
	public List<CTEntry> getCTEntryAggregateCTEntries(
		long pk, int start, int end) {

		return getCTEntryAggregateCTEntries(pk, start, end, null);
	}

	/**
	 * Returns all the ct entry associated with the ct entry aggregate.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CTEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the ct entry aggregate
	 * @param start the lower bound of the range of ct entry aggregates
	 * @param end the upper bound of the range of ct entry aggregates (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ct entries associated with the ct entry aggregate
	 */
	@Override
	public List<CTEntry> getCTEntryAggregateCTEntries(
		long pk, int start, int end,
		OrderByComparator<CTEntry> orderByComparator) {

		return ctEntryToCTEntryAggregateTableMapper.getLeftBaseModels(
			pk, start, end, orderByComparator);
	}

	/**
	 * Returns the number of ct entry aggregates associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @return the number of ct entry aggregates associated with the ct entry
	 */
	@Override
	public int getCTEntryAggregatesSize(long pk) {
		long[] pks = ctEntryToCTEntryAggregateTableMapper.getRightPrimaryKeys(
			pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the ct entry aggregate is associated with the ct entry.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 * @return <code>true</code> if the ct entry aggregate is associated with the ct entry; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEntryAggregate(long pk, long ctEntryAggregatePK) {
		return ctEntryToCTEntryAggregateTableMapper.containsTableMapping(
			pk, ctEntryAggregatePK);
	}

	/**
	 * Returns <code>true</code> if the ct entry has any ct entry aggregates associated with it.
	 *
	 * @param pk the primary key of the ct entry to check for associations with ct entry aggregates
	 * @return <code>true</code> if the ct entry has any ct entry aggregates associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEntryAggregates(long pk) {
		if (getCTEntryAggregatesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 */
	@Override
	public void addCTEntryAggregate(long pk, long ctEntryAggregatePK) {
		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			ctEntryToCTEntryAggregateTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk, ctEntryAggregatePK);
		}
		else {
			ctEntryToCTEntryAggregateTableMapper.addTableMapping(
				ctEntry.getCompanyId(), pk, ctEntryAggregatePK);
		}
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	@Override
	public void addCTEntryAggregate(
		long pk, CTEntryAggregate ctEntryAggregate) {

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			ctEntryToCTEntryAggregateTableMapper.addTableMapping(
				CompanyThreadLocal.getCompanyId(), pk,
				ctEntryAggregate.getPrimaryKey());
		}
		else {
			ctEntryToCTEntryAggregateTableMapper.addTableMapping(
				ctEntry.getCompanyId(), pk, ctEntryAggregate.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates
	 */
	@Override
	public void addCTEntryAggregates(long pk, long[] ctEntryAggregatePKs) {
		long companyId = 0;

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = ctEntry.getCompanyId();
		}

		ctEntryToCTEntryAggregateTableMapper.addTableMappings(
			companyId, pk, ctEntryAggregatePKs);
	}

	/**
	 * Adds an association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	@Override
	public void addCTEntryAggregates(
		long pk, List<CTEntryAggregate> ctEntryAggregates) {

		addCTEntryAggregates(
			pk,
			ListUtil.toLongArray(
				ctEntryAggregates,
				CTEntryAggregate.CT_ENTRY_AGGREGATE_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the ct entry and its ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry to clear the associated ct entry aggregates from
	 */
	@Override
	public void clearCTEntryAggregates(long pk) {
		ctEntryToCTEntryAggregateTableMapper.deleteLeftPrimaryKeyTableMappings(
			pk);
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePK the primary key of the ct entry aggregate
	 */
	@Override
	public void removeCTEntryAggregate(long pk, long ctEntryAggregatePK) {
		ctEntryToCTEntryAggregateTableMapper.deleteTableMapping(
			pk, ctEntryAggregatePK);
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregate. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregate the ct entry aggregate
	 */
	@Override
	public void removeCTEntryAggregate(
		long pk, CTEntryAggregate ctEntryAggregate) {

		ctEntryToCTEntryAggregateTableMapper.deleteTableMapping(
			pk, ctEntryAggregate.getPrimaryKey());
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates
	 */
	@Override
	public void removeCTEntryAggregates(long pk, long[] ctEntryAggregatePKs) {
		ctEntryToCTEntryAggregateTableMapper.deleteTableMappings(
			pk, ctEntryAggregatePKs);
	}

	/**
	 * Removes the association between the ct entry and the ct entry aggregates. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates
	 */
	@Override
	public void removeCTEntryAggregates(
		long pk, List<CTEntryAggregate> ctEntryAggregates) {

		removeCTEntryAggregates(
			pk,
			ListUtil.toLongArray(
				ctEntryAggregates,
				CTEntryAggregate.CT_ENTRY_AGGREGATE_ID_ACCESSOR));
	}

	/**
	 * Sets the ct entry aggregates associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregatePKs the primary keys of the ct entry aggregates to be associated with the ct entry
	 */
	@Override
	public void setCTEntryAggregates(long pk, long[] ctEntryAggregatePKs) {
		Set<Long> newCTEntryAggregatePKsSet = SetUtil.fromArray(
			ctEntryAggregatePKs);
		Set<Long> oldCTEntryAggregatePKsSet = SetUtil.fromArray(
			ctEntryToCTEntryAggregateTableMapper.getRightPrimaryKeys(pk));

		Set<Long> removeCTEntryAggregatePKsSet = new HashSet<Long>(
			oldCTEntryAggregatePKsSet);

		removeCTEntryAggregatePKsSet.removeAll(newCTEntryAggregatePKsSet);

		ctEntryToCTEntryAggregateTableMapper.deleteTableMappings(
			pk, ArrayUtil.toLongArray(removeCTEntryAggregatePKsSet));

		newCTEntryAggregatePKsSet.removeAll(oldCTEntryAggregatePKsSet);

		long companyId = 0;

		CTEntry ctEntry = fetchByPrimaryKey(pk);

		if (ctEntry == null) {
			companyId = CompanyThreadLocal.getCompanyId();
		}
		else {
			companyId = ctEntry.getCompanyId();
		}

		ctEntryToCTEntryAggregateTableMapper.addTableMappings(
			companyId, pk, ArrayUtil.toLongArray(newCTEntryAggregatePKsSet));
	}

	/**
	 * Sets the ct entry aggregates associated with the ct entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the ct entry
	 * @param ctEntryAggregates the ct entry aggregates to be associated with the ct entry
	 */
	@Override
	public void setCTEntryAggregates(
		long pk, List<CTEntryAggregate> ctEntryAggregates) {

		try {
			long[] ctEntryAggregatePKs = new long[ctEntryAggregates.size()];

			for (int i = 0; i < ctEntryAggregates.size(); i++) {
				CTEntryAggregate ctEntryAggregate = ctEntryAggregates.get(i);

				ctEntryAggregatePKs[i] = ctEntryAggregate.getPrimaryKey();
			}

			setCTEntryAggregates(pk, ctEntryAggregatePKs);
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
	protected String getPKDBName() {
		return "ctEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CTENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CTEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ct entry persistence.
	 */
	@Activate
	public void activate() {
		CTEntryModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		CTEntryModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		ctEntryToCTEntryAggregateTableMapper =
			TableMapperFactory.getTableMapper(
				"CTEntryAggregates_CTEntries#ctEntryId",
				"CTEntryAggregates_CTEntries", "companyId", "ctEntryId",
				"ctEntryAggregateId", this, CTEntryAggregate.class);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCTCollectionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCTCollectionId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCTCollectionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCTCollectionId",
			new String[] {Long.class.getName()},
			CTEntryModelImpl.CTCOLLECTIONID_COLUMN_BITMASK);

		_finderPathCountByCTCollectionId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCTCollectionId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByModelClassNameId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByModelClassNameId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByModelClassNameId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByModelClassNameId",
			new String[] {Long.class.getName()},
			CTEntryModelImpl.MODELCLASSNAMEID_COLUMN_BITMASK);

		_finderPathCountByModelClassNameId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByModelClassNameId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByC_MCNI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_MCNI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_MCNI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_MCNI",
			new String[] {Long.class.getName(), Long.class.getName()},
			CTEntryModelImpl.CTCOLLECTIONID_COLUMN_BITMASK |
			CTEntryModelImpl.MODELCLASSNAMEID_COLUMN_BITMASK);

		_finderPathCountByC_MCNI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_MCNI",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_MRPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_MRPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_MRPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_MRPK",
			new String[] {Long.class.getName(), Long.class.getName()},
			CTEntryModelImpl.CTCOLLECTIONID_COLUMN_BITMASK |
			CTEntryModelImpl.MODELRESOURCEPRIMKEY_COLUMN_BITMASK);

		_finderPathCountByC_MRPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_MRPK",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			CTEntryModelImpl.CTCOLLECTIONID_COLUMN_BITMASK |
			CTEntryModelImpl.STATUS_COLUMN_BITMASK);

		_finderPathCountByC_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_S",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByMCNI_MCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByMCNI_MCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByMCNI_MCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByMCNI_MCPK",
			new String[] {Long.class.getName(), Long.class.getName()},
			CTEntryModelImpl.MODELCLASSNAMEID_COLUMN_BITMASK |
			CTEntryModelImpl.MODELCLASSPK_COLUMN_BITMASK);

		_finderPathCountByMCNI_MCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByMCNI_MCPK",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByCompanyId_MCNI_MCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId_MCNI_MCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId_MCNI_MCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId_MCNI_MCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			CTEntryModelImpl.COMPANYID_COLUMN_BITMASK |
			CTEntryModelImpl.MODELCLASSNAMEID_COLUMN_BITMASK |
			CTEntryModelImpl.MODELCLASSPK_COLUMN_BITMASK);

		_finderPathCountByCompanyId_MCNI_MCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId_MCNI_MCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathFetchByC_MCNI_MCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_MCNI_MCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			CTEntryModelImpl.CTCOLLECTIONID_COLUMN_BITMASK |
			CTEntryModelImpl.MODELCLASSNAMEID_COLUMN_BITMASK |
			CTEntryModelImpl.MODELCLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_MCNI_MCPK = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_MCNI_MCPK",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByC_MCNI_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_MCNI_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_MCNI_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_MCNI_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			CTEntryModelImpl.CTCOLLECTIONID_COLUMN_BITMASK |
			CTEntryModelImpl.MODELCLASSNAMEID_COLUMN_BITMASK |
			CTEntryModelImpl.STATUS_COLUMN_BITMASK);

		_finderPathCountByC_MCNI_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_MCNI_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByC_MRPK_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_MRPK_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_MRPK_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CTEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_MRPK_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			CTEntryModelImpl.CTCOLLECTIONID_COLUMN_BITMASK |
			CTEntryModelImpl.MODELRESOURCEPRIMKEY_COLUMN_BITMASK |
			CTEntryModelImpl.STATUS_COLUMN_BITMASK);

		_finderPathCountByC_MRPK_S = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_MRPK_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CTEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		TableMapperFactory.removeTableMapper(
			"CTEntryAggregates_CTEntries#ctEntryId");
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.change.tracking.model.CTEntry"),
			true);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CTPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	protected TableMapper<CTEntry, CTEntryAggregate>
		ctEntryToCTEntryAggregateTableMapper;

	private static final String _SQL_SELECT_CTENTRY =
		"SELECT ctEntry FROM CTEntry ctEntry";

	private static final String _SQL_SELECT_CTENTRY_WHERE =
		"SELECT ctEntry FROM CTEntry ctEntry WHERE ";

	private static final String _SQL_COUNT_CTENTRY =
		"SELECT COUNT(ctEntry) FROM CTEntry ctEntry";

	private static final String _SQL_COUNT_CTENTRY_WHERE =
		"SELECT COUNT(ctEntry) FROM CTEntry ctEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "ctEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CTEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CTEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CTEntryPersistenceImpl.class);

}