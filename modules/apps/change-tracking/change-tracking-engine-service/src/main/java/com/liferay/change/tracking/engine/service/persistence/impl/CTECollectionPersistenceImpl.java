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

import com.liferay.change.tracking.engine.exception.NoSuchCTECollectionException;
import com.liferay.change.tracking.engine.model.CTECollection;
import com.liferay.change.tracking.engine.model.impl.CTECollectionImpl;
import com.liferay.change.tracking.engine.model.impl.CTECollectionModelImpl;
import com.liferay.change.tracking.engine.service.persistence.CTECollectionPersistence;
import com.liferay.change.tracking.engine.service.persistence.CTEEntryPersistence;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
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
 * The persistence implementation for the cte collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTECollectionPersistence
 * @see com.liferay.change.tracking.engine.service.persistence.CTECollectionUtil
 * @generated
 */
@ProviderType
public class CTECollectionPersistenceImpl extends BasePersistenceImpl<CTECollection>
	implements CTECollectionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CTECollectionUtil} to access the cte collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CTECollectionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CTECollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTECollectionModelImpl.FINDER_CACHE_ENABLED,
			CTECollectionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CTECollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTECollectionModelImpl.FINDER_CACHE_ENABLED,
			CTECollectionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CTECollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTECollectionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public CTECollectionPersistenceImpl() {
		setModelClass(CTECollection.class);

		setModelImplClass(CTECollectionImpl.class);
		setEntityCacheEnabled(CTECollectionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the cte collection in the entity cache if it is enabled.
	 *
	 * @param cteCollection the cte collection
	 */
	@Override
	public void cacheResult(CTECollection cteCollection) {
		entityCache.putResult(CTECollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTECollectionImpl.class, cteCollection.getPrimaryKey(),
			cteCollection);

		cteCollection.resetOriginalValues();
	}

	/**
	 * Caches the cte collections in the entity cache if it is enabled.
	 *
	 * @param cteCollections the cte collections
	 */
	@Override
	public void cacheResult(List<CTECollection> cteCollections) {
		for (CTECollection cteCollection : cteCollections) {
			if (entityCache.getResult(
						CTECollectionModelImpl.ENTITY_CACHE_ENABLED,
						CTECollectionImpl.class, cteCollection.getPrimaryKey()) == null) {
				cacheResult(cteCollection);
			}
			else {
				cteCollection.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cte collections.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CTECollectionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cte collection.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CTECollection cteCollection) {
		entityCache.removeResult(CTECollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTECollectionImpl.class, cteCollection.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CTECollection> cteCollections) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CTECollection cteCollection : cteCollections) {
			entityCache.removeResult(CTECollectionModelImpl.ENTITY_CACHE_ENABLED,
				CTECollectionImpl.class, cteCollection.getPrimaryKey());
		}
	}

	/**
	 * Creates a new cte collection with the primary key. Does not add the cte collection to the database.
	 *
	 * @param collectionId the primary key for the new cte collection
	 * @return the new cte collection
	 */
	@Override
	public CTECollection create(long collectionId) {
		CTECollection cteCollection = new CTECollectionImpl();

		cteCollection.setNew(true);
		cteCollection.setPrimaryKey(collectionId);

		cteCollection.setCompanyId(companyProvider.getCompanyId());

		return cteCollection;
	}

	/**
	 * Removes the cte collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param collectionId the primary key of the cte collection
	 * @return the cte collection that was removed
	 * @throws NoSuchCTECollectionException if a cte collection with the primary key could not be found
	 */
	@Override
	public CTECollection remove(long collectionId)
		throws NoSuchCTECollectionException {
		return remove((Serializable)collectionId);
	}

	/**
	 * Removes the cte collection with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cte collection
	 * @return the cte collection that was removed
	 * @throws NoSuchCTECollectionException if a cte collection with the primary key could not be found
	 */
	@Override
	public CTECollection remove(Serializable primaryKey)
		throws NoSuchCTECollectionException {
		Session session = null;

		try {
			session = openSession();

			CTECollection cteCollection = (CTECollection)session.get(CTECollectionImpl.class,
					primaryKey);

			if (cteCollection == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCTECollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(cteCollection);
		}
		catch (NoSuchCTECollectionException nsee) {
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
	protected CTECollection removeImpl(CTECollection cteCollection) {
		cteCollectionToCTEEntryTableMapper.deleteLeftPrimaryKeyTableMappings(cteCollection.getPrimaryKey());

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cteCollection)) {
				cteCollection = (CTECollection)session.get(CTECollectionImpl.class,
						cteCollection.getPrimaryKeyObj());
			}

			if (cteCollection != null) {
				session.delete(cteCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cteCollection != null) {
			clearCache(cteCollection);
		}

		return cteCollection;
	}

	@Override
	public CTECollection updateImpl(CTECollection cteCollection) {
		boolean isNew = cteCollection.isNew();

		if (!(cteCollection instanceof CTECollectionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cteCollection.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(cteCollection);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cteCollection proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CTECollection implementation " +
				cteCollection.getClass());
		}

		CTECollectionModelImpl cteCollectionModelImpl = (CTECollectionModelImpl)cteCollection;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (cteCollection.getCreateDate() == null)) {
			if (serviceContext == null) {
				cteCollection.setCreateDate(now);
			}
			else {
				cteCollection.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!cteCollectionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				cteCollection.setModifiedDate(now);
			}
			else {
				cteCollection.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (cteCollection.isNew()) {
				session.save(cteCollection);

				cteCollection.setNew(false);
			}
			else {
				cteCollection = (CTECollection)session.merge(cteCollection);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(CTECollectionModelImpl.ENTITY_CACHE_ENABLED,
			CTECollectionImpl.class, cteCollection.getPrimaryKey(),
			cteCollection, false);

		cteCollection.resetOriginalValues();

		return cteCollection;
	}

	/**
	 * Returns the cte collection with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the cte collection
	 * @return the cte collection
	 * @throws NoSuchCTECollectionException if a cte collection with the primary key could not be found
	 */
	@Override
	public CTECollection findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCTECollectionException {
		CTECollection cteCollection = fetchByPrimaryKey(primaryKey);

		if (cteCollection == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCTECollectionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return cteCollection;
	}

	/**
	 * Returns the cte collection with the primary key or throws a {@link NoSuchCTECollectionException} if it could not be found.
	 *
	 * @param collectionId the primary key of the cte collection
	 * @return the cte collection
	 * @throws NoSuchCTECollectionException if a cte collection with the primary key could not be found
	 */
	@Override
	public CTECollection findByPrimaryKey(long collectionId)
		throws NoSuchCTECollectionException {
		return findByPrimaryKey((Serializable)collectionId);
	}

	/**
	 * Returns the cte collection with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param collectionId the primary key of the cte collection
	 * @return the cte collection, or <code>null</code> if a cte collection with the primary key could not be found
	 */
	@Override
	public CTECollection fetchByPrimaryKey(long collectionId) {
		return fetchByPrimaryKey((Serializable)collectionId);
	}

	@Override
	public Map<Serializable, CTECollection> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CTECollection> map = new HashMap<Serializable, CTECollection>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CTECollection cteCollection = fetchByPrimaryKey(primaryKey);

			if (cteCollection != null) {
				map.put(primaryKey, cteCollection);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CTECollectionModelImpl.ENTITY_CACHE_ENABLED,
					CTECollectionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CTECollection)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_CTECOLLECTION_WHERE_PKS_IN);

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

			for (CTECollection cteCollection : (List<CTECollection>)q.list()) {
				map.put(cteCollection.getPrimaryKeyObj(), cteCollection);

				cacheResult(cteCollection);

				uncachedPrimaryKeys.remove(cteCollection.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CTECollectionModelImpl.ENTITY_CACHE_ENABLED,
					CTECollectionImpl.class, primaryKey, nullModel);
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
	 * Returns all the cte collections.
	 *
	 * @return the cte collections
	 */
	@Override
	public List<CTECollection> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cte collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CTECollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cte collections
	 * @param end the upper bound of the range of cte collections (not inclusive)
	 * @return the range of cte collections
	 */
	@Override
	public List<CTECollection> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cte collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CTECollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cte collections
	 * @param end the upper bound of the range of cte collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cte collections
	 */
	@Override
	public List<CTECollection> findAll(int start, int end,
		OrderByComparator<CTECollection> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cte collections.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CTECollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cte collections
	 * @param end the upper bound of the range of cte collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of cte collections
	 */
	@Override
	public List<CTECollection> findAll(int start, int end,
		OrderByComparator<CTECollection> orderByComparator,
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

		List<CTECollection> list = null;

		if (retrieveFromCache) {
			list = (List<CTECollection>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CTECOLLECTION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CTECOLLECTION;

				if (pagination) {
					sql = sql.concat(CTECollectionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CTECollection>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CTECollection>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the cte collections from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CTECollection cteCollection : findAll()) {
			remove(cteCollection);
		}
	}

	/**
	 * Returns the number of cte collections.
	 *
	 * @return the number of cte collections
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CTECOLLECTION);

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
	 * Returns the primaryKeys of cte entries associated with the cte collection.
	 *
	 * @param pk the primary key of the cte collection
	 * @return long[] of the primaryKeys of cte entries associated with the cte collection
	 */
	@Override
	public long[] getCTEEntryPrimaryKeys(long pk) {
		long[] pks = cteCollectionToCTEEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.clone();
	}

	/**
	 * Returns all the cte entries associated with the cte collection.
	 *
	 * @param pk the primary key of the cte collection
	 * @return the cte entries associated with the cte collection
	 */
	@Override
	public List<com.liferay.change.tracking.engine.model.CTEEntry> getCTEEntries(
		long pk) {
		return getCTEEntries(pk, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	/**
	 * Returns a range of all the cte entries associated with the cte collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CTECollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the cte collection
	 * @param start the lower bound of the range of cte collections
	 * @param end the upper bound of the range of cte collections (not inclusive)
	 * @return the range of cte entries associated with the cte collection
	 */
	@Override
	public List<com.liferay.change.tracking.engine.model.CTEEntry> getCTEEntries(
		long pk, int start, int end) {
		return getCTEEntries(pk, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cte entries associated with the cte collection.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CTECollectionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param pk the primary key of the cte collection
	 * @param start the lower bound of the range of cte collections
	 * @param end the upper bound of the range of cte collections (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cte entries associated with the cte collection
	 */
	@Override
	public List<com.liferay.change.tracking.engine.model.CTEEntry> getCTEEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.engine.model.CTEEntry> orderByComparator) {
		return cteCollectionToCTEEntryTableMapper.getRightBaseModels(pk, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of cte entries associated with the cte collection.
	 *
	 * @param pk the primary key of the cte collection
	 * @return the number of cte entries associated with the cte collection
	 */
	@Override
	public int getCTEEntriesSize(long pk) {
		long[] pks = cteCollectionToCTEEntryTableMapper.getRightPrimaryKeys(pk);

		return pks.length;
	}

	/**
	 * Returns <code>true</code> if the cte entry is associated with the cte collection.
	 *
	 * @param pk the primary key of the cte collection
	 * @param cteEntryPK the primary key of the cte entry
	 * @return <code>true</code> if the cte entry is associated with the cte collection; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEEntry(long pk, long cteEntryPK) {
		return cteCollectionToCTEEntryTableMapper.containsTableMapping(pk,
			cteEntryPK);
	}

	/**
	 * Returns <code>true</code> if the cte collection has any cte entries associated with it.
	 *
	 * @param pk the primary key of the cte collection to check for associations with cte entries
	 * @return <code>true</code> if the cte collection has any cte entries associated with it; <code>false</code> otherwise
	 */
	@Override
	public boolean containsCTEEntries(long pk) {
		if (getCTEEntriesSize(pk) > 0) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Adds an association between the cte collection and the cte entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte collection
	 * @param cteEntryPK the primary key of the cte entry
	 */
	@Override
	public void addCTEEntry(long pk, long cteEntryPK) {
		CTECollection cteCollection = fetchByPrimaryKey(pk);

		if (cteCollection == null) {
			cteCollectionToCTEEntryTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, cteEntryPK);
		}
		else {
			cteCollectionToCTEEntryTableMapper.addTableMapping(cteCollection.getCompanyId(),
				pk, cteEntryPK);
		}
	}

	/**
	 * Adds an association between the cte collection and the cte entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte collection
	 * @param cteEntry the cte entry
	 */
	@Override
	public void addCTEEntry(long pk,
		com.liferay.change.tracking.engine.model.CTEEntry cteEntry) {
		CTECollection cteCollection = fetchByPrimaryKey(pk);

		if (cteCollection == null) {
			cteCollectionToCTEEntryTableMapper.addTableMapping(companyProvider.getCompanyId(),
				pk, cteEntry.getPrimaryKey());
		}
		else {
			cteCollectionToCTEEntryTableMapper.addTableMapping(cteCollection.getCompanyId(),
				pk, cteEntry.getPrimaryKey());
		}
	}

	/**
	 * Adds an association between the cte collection and the cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte collection
	 * @param cteEntryPKs the primary keys of the cte entries
	 */
	@Override
	public void addCTEEntries(long pk, long[] cteEntryPKs) {
		long companyId = 0;

		CTECollection cteCollection = fetchByPrimaryKey(pk);

		if (cteCollection == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = cteCollection.getCompanyId();
		}

		cteCollectionToCTEEntryTableMapper.addTableMappings(companyId, pk,
			cteEntryPKs);
	}

	/**
	 * Adds an association between the cte collection and the cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte collection
	 * @param cteEntries the cte entries
	 */
	@Override
	public void addCTEEntries(long pk,
		List<com.liferay.change.tracking.engine.model.CTEEntry> cteEntries) {
		addCTEEntries(pk,
			ListUtil.toLongArray(cteEntries,
				com.liferay.change.tracking.engine.model.CTEEntry.ENTRY_ID_ACCESSOR));
	}

	/**
	 * Clears all associations between the cte collection and its cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte collection to clear the associated cte entries from
	 */
	@Override
	public void clearCTEEntries(long pk) {
		cteCollectionToCTEEntryTableMapper.deleteLeftPrimaryKeyTableMappings(pk);
	}

	/**
	 * Removes the association between the cte collection and the cte entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte collection
	 * @param cteEntryPK the primary key of the cte entry
	 */
	@Override
	public void removeCTEEntry(long pk, long cteEntryPK) {
		cteCollectionToCTEEntryTableMapper.deleteTableMapping(pk, cteEntryPK);
	}

	/**
	 * Removes the association between the cte collection and the cte entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte collection
	 * @param cteEntry the cte entry
	 */
	@Override
	public void removeCTEEntry(long pk,
		com.liferay.change.tracking.engine.model.CTEEntry cteEntry) {
		cteCollectionToCTEEntryTableMapper.deleteTableMapping(pk,
			cteEntry.getPrimaryKey());
	}

	/**
	 * Removes the association between the cte collection and the cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte collection
	 * @param cteEntryPKs the primary keys of the cte entries
	 */
	@Override
	public void removeCTEEntries(long pk, long[] cteEntryPKs) {
		cteCollectionToCTEEntryTableMapper.deleteTableMappings(pk, cteEntryPKs);
	}

	/**
	 * Removes the association between the cte collection and the cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte collection
	 * @param cteEntries the cte entries
	 */
	@Override
	public void removeCTEEntries(long pk,
		List<com.liferay.change.tracking.engine.model.CTEEntry> cteEntries) {
		removeCTEEntries(pk,
			ListUtil.toLongArray(cteEntries,
				com.liferay.change.tracking.engine.model.CTEEntry.ENTRY_ID_ACCESSOR));
	}

	/**
	 * Sets the cte entries associated with the cte collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte collection
	 * @param cteEntryPKs the primary keys of the cte entries to be associated with the cte collection
	 */
	@Override
	public void setCTEEntries(long pk, long[] cteEntryPKs) {
		Set<Long> newCTEEntryPKsSet = SetUtil.fromArray(cteEntryPKs);
		Set<Long> oldCTEEntryPKsSet = SetUtil.fromArray(cteCollectionToCTEEntryTableMapper.getRightPrimaryKeys(
					pk));

		Set<Long> removeCTEEntryPKsSet = new HashSet<Long>(oldCTEEntryPKsSet);

		removeCTEEntryPKsSet.removeAll(newCTEEntryPKsSet);

		cteCollectionToCTEEntryTableMapper.deleteTableMappings(pk,
			ArrayUtil.toLongArray(removeCTEEntryPKsSet));

		newCTEEntryPKsSet.removeAll(oldCTEEntryPKsSet);

		long companyId = 0;

		CTECollection cteCollection = fetchByPrimaryKey(pk);

		if (cteCollection == null) {
			companyId = companyProvider.getCompanyId();
		}
		else {
			companyId = cteCollection.getCompanyId();
		}

		cteCollectionToCTEEntryTableMapper.addTableMappings(companyId, pk,
			ArrayUtil.toLongArray(newCTEEntryPKsSet));
	}

	/**
	 * Sets the cte entries associated with the cte collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	 *
	 * @param pk the primary key of the cte collection
	 * @param cteEntries the cte entries to be associated with the cte collection
	 */
	@Override
	public void setCTEEntries(long pk,
		List<com.liferay.change.tracking.engine.model.CTEEntry> cteEntries) {
		try {
			long[] cteEntryPKs = new long[cteEntries.size()];

			for (int i = 0; i < cteEntries.size(); i++) {
				com.liferay.change.tracking.engine.model.CTEEntry cteEntry = cteEntries.get(i);

				cteEntryPKs[i] = cteEntry.getPrimaryKey();
			}

			setCTEEntries(pk, cteEntryPKs);
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
		return CTECollectionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cte collection persistence.
	 */
	public void afterPropertiesSet() {
		cteCollectionToCTEEntryTableMapper = TableMapperFactory.getTableMapper("Collections_Entries",
				"companyId", "collectionId", "entryId", this,
				cteEntryPersistence);
	}

	public void destroy() {
		entityCache.removeCache(CTECollectionImpl.class.getName());
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
	@BeanReference(type = CTEEntryPersistence.class)
	protected CTEEntryPersistence cteEntryPersistence;
	protected TableMapper<CTECollection, com.liferay.change.tracking.engine.model.CTEEntry> cteCollectionToCTEEntryTableMapper;
	private static final String _SQL_SELECT_CTECOLLECTION = "SELECT cteCollection FROM CTECollection cteCollection";
	private static final String _SQL_SELECT_CTECOLLECTION_WHERE_PKS_IN = "SELECT cteCollection FROM CTECollection cteCollection WHERE collectionId IN (";
	private static final String _SQL_COUNT_CTECOLLECTION = "SELECT COUNT(cteCollection) FROM CTECollection cteCollection";
	private static final String _ORDER_BY_ENTITY_ALIAS = "cteCollection.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CTECollection exists with the primary key ";
	private static final Log _log = LogFactoryUtil.getLog(CTECollectionPersistenceImpl.class);
}