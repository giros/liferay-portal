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

package com.liferay.change.tracking.engine.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.engine.model.CTEEntry;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cte entry service. This utility wraps {@link com.liferay.change.tracking.engine.service.persistence.impl.CTEEntryPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTEEntryPersistence
 * @see com.liferay.change.tracking.engine.service.persistence.impl.CTEEntryPersistenceImpl
 * @generated
 */
@ProviderType
public class CTEEntryUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(CTEEntry cteEntry) {
		getPersistence().clearCache(cteEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CTEEntry> findWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTEEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTEEntry> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTEEntry> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTEEntry update(CTEEntry cteEntry) {
		return getPersistence().update(cteEntry);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTEEntry update(CTEEntry cteEntry,
		ServiceContext serviceContext) {
		return getPersistence().update(cteEntry, serviceContext);
	}

	/**
	* Returns all the cte entries where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the matching cte entries
	*/
	public static List<CTEEntry> findByResourcePrimKey(long resourcePrimKey) {
		return getPersistence().findByResourcePrimKey(resourcePrimKey);
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
	public static List<CTEEntry> findByResourcePrimKey(long resourcePrimKey,
		int start, int end) {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end);
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
	public static List<CTEEntry> findByResourcePrimKey(long resourcePrimKey,
		int start, int end, OrderByComparator<CTEEntry> orderByComparator) {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator);
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
	public static List<CTEEntry> findByResourcePrimKey(long resourcePrimKey,
		int start, int end, OrderByComparator<CTEEntry> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByResourcePrimKey(resourcePrimKey, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first cte entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cte entry
	* @throws NoSuchCTEEntryException if a matching cte entry could not be found
	*/
	public static CTEEntry findByResourcePrimKey_First(long resourcePrimKey,
		OrderByComparator<CTEEntry> orderByComparator)
		throws com.liferay.change.tracking.engine.exception.NoSuchCTEEntryException {
		return getPersistence()
				   .findByResourcePrimKey_First(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the first cte entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cte entry, or <code>null</code> if a matching cte entry could not be found
	*/
	public static CTEEntry fetchByResourcePrimKey_First(long resourcePrimKey,
		OrderByComparator<CTEEntry> orderByComparator) {
		return getPersistence()
				   .fetchByResourcePrimKey_First(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the last cte entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cte entry
	* @throws NoSuchCTEEntryException if a matching cte entry could not be found
	*/
	public static CTEEntry findByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator<CTEEntry> orderByComparator)
		throws com.liferay.change.tracking.engine.exception.NoSuchCTEEntryException {
		return getPersistence()
				   .findByResourcePrimKey_Last(resourcePrimKey,
			orderByComparator);
	}

	/**
	* Returns the last cte entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cte entry, or <code>null</code> if a matching cte entry could not be found
	*/
	public static CTEEntry fetchByResourcePrimKey_Last(long resourcePrimKey,
		OrderByComparator<CTEEntry> orderByComparator) {
		return getPersistence()
				   .fetchByResourcePrimKey_Last(resourcePrimKey,
			orderByComparator);
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
	public static CTEEntry[] findByResourcePrimKey_PrevAndNext(long entryId,
		long resourcePrimKey, OrderByComparator<CTEEntry> orderByComparator)
		throws com.liferay.change.tracking.engine.exception.NoSuchCTEEntryException {
		return getPersistence()
				   .findByResourcePrimKey_PrevAndNext(entryId, resourcePrimKey,
			orderByComparator);
	}

	/**
	* Removes all the cte entries where resourcePrimKey = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	*/
	public static void removeByResourcePrimKey(long resourcePrimKey) {
		getPersistence().removeByResourcePrimKey(resourcePrimKey);
	}

	/**
	* Returns the number of cte entries where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the number of matching cte entries
	*/
	public static int countByResourcePrimKey(long resourcePrimKey) {
		return getPersistence().countByResourcePrimKey(resourcePrimKey);
	}

	/**
	* Caches the cte entry in the entity cache if it is enabled.
	*
	* @param cteEntry the cte entry
	*/
	public static void cacheResult(CTEEntry cteEntry) {
		getPersistence().cacheResult(cteEntry);
	}

	/**
	* Caches the cte entries in the entity cache if it is enabled.
	*
	* @param cteEntries the cte entries
	*/
	public static void cacheResult(List<CTEEntry> cteEntries) {
		getPersistence().cacheResult(cteEntries);
	}

	/**
	* Creates a new cte entry with the primary key. Does not add the cte entry to the database.
	*
	* @param entryId the primary key for the new cte entry
	* @return the new cte entry
	*/
	public static CTEEntry create(long entryId) {
		return getPersistence().create(entryId);
	}

	/**
	* Removes the cte entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the cte entry
	* @return the cte entry that was removed
	* @throws NoSuchCTEEntryException if a cte entry with the primary key could not be found
	*/
	public static CTEEntry remove(long entryId)
		throws com.liferay.change.tracking.engine.exception.NoSuchCTEEntryException {
		return getPersistence().remove(entryId);
	}

	public static CTEEntry updateImpl(CTEEntry cteEntry) {
		return getPersistence().updateImpl(cteEntry);
	}

	/**
	* Returns the cte entry with the primary key or throws a {@link NoSuchCTEEntryException} if it could not be found.
	*
	* @param entryId the primary key of the cte entry
	* @return the cte entry
	* @throws NoSuchCTEEntryException if a cte entry with the primary key could not be found
	*/
	public static CTEEntry findByPrimaryKey(long entryId)
		throws com.liferay.change.tracking.engine.exception.NoSuchCTEEntryException {
		return getPersistence().findByPrimaryKey(entryId);
	}

	/**
	* Returns the cte entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the cte entry
	* @return the cte entry, or <code>null</code> if a cte entry with the primary key could not be found
	*/
	public static CTEEntry fetchByPrimaryKey(long entryId) {
		return getPersistence().fetchByPrimaryKey(entryId);
	}

	public static java.util.Map<java.io.Serializable, CTEEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cte entries.
	*
	* @return the cte entries
	*/
	public static List<CTEEntry> findAll() {
		return getPersistence().findAll();
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
	public static List<CTEEntry> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
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
	public static List<CTEEntry> findAll(int start, int end,
		OrderByComparator<CTEEntry> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<CTEEntry> findAll(int start, int end,
		OrderByComparator<CTEEntry> orderByComparator, boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cte entries from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cte entries.
	*
	* @return the number of cte entries
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of cte collections associated with the cte entry.
	*
	* @param pk the primary key of the cte entry
	* @return long[] of the primaryKeys of cte collections associated with the cte entry
	*/
	public static long[] getCTECollectionPrimaryKeys(long pk) {
		return getPersistence().getCTECollectionPrimaryKeys(pk);
	}

	/**
	* Returns all the cte collections associated with the cte entry.
	*
	* @param pk the primary key of the cte entry
	* @return the cte collections associated with the cte entry
	*/
	public static List<com.liferay.change.tracking.engine.model.CTECollection> getCTECollections(
		long pk) {
		return getPersistence().getCTECollections(pk);
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
	public static List<com.liferay.change.tracking.engine.model.CTECollection> getCTECollections(
		long pk, int start, int end) {
		return getPersistence().getCTECollections(pk, start, end);
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
	public static List<com.liferay.change.tracking.engine.model.CTECollection> getCTECollections(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.engine.model.CTECollection> orderByComparator) {
		return getPersistence()
				   .getCTECollections(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of cte collections associated with the cte entry.
	*
	* @param pk the primary key of the cte entry
	* @return the number of cte collections associated with the cte entry
	*/
	public static int getCTECollectionsSize(long pk) {
		return getPersistence().getCTECollectionsSize(pk);
	}

	/**
	* Returns <code>true</code> if the cte collection is associated with the cte entry.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollectionPK the primary key of the cte collection
	* @return <code>true</code> if the cte collection is associated with the cte entry; <code>false</code> otherwise
	*/
	public static boolean containsCTECollection(long pk, long cteCollectionPK) {
		return getPersistence().containsCTECollection(pk, cteCollectionPK);
	}

	/**
	* Returns <code>true</code> if the cte entry has any cte collections associated with it.
	*
	* @param pk the primary key of the cte entry to check for associations with cte collections
	* @return <code>true</code> if the cte entry has any cte collections associated with it; <code>false</code> otherwise
	*/
	public static boolean containsCTECollections(long pk) {
		return getPersistence().containsCTECollections(pk);
	}

	/**
	* Adds an association between the cte entry and the cte collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollectionPK the primary key of the cte collection
	*/
	public static void addCTECollection(long pk, long cteCollectionPK) {
		getPersistence().addCTECollection(pk, cteCollectionPK);
	}

	/**
	* Adds an association between the cte entry and the cte collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollection the cte collection
	*/
	public static void addCTECollection(long pk,
		com.liferay.change.tracking.engine.model.CTECollection cteCollection) {
		getPersistence().addCTECollection(pk, cteCollection);
	}

	/**
	* Adds an association between the cte entry and the cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollectionPKs the primary keys of the cte collections
	*/
	public static void addCTECollections(long pk, long[] cteCollectionPKs) {
		getPersistence().addCTECollections(pk, cteCollectionPKs);
	}

	/**
	* Adds an association between the cte entry and the cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollections the cte collections
	*/
	public static void addCTECollections(long pk,
		List<com.liferay.change.tracking.engine.model.CTECollection> cteCollections) {
		getPersistence().addCTECollections(pk, cteCollections);
	}

	/**
	* Clears all associations between the cte entry and its cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry to clear the associated cte collections from
	*/
	public static void clearCTECollections(long pk) {
		getPersistence().clearCTECollections(pk);
	}

	/**
	* Removes the association between the cte entry and the cte collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollectionPK the primary key of the cte collection
	*/
	public static void removeCTECollection(long pk, long cteCollectionPK) {
		getPersistence().removeCTECollection(pk, cteCollectionPK);
	}

	/**
	* Removes the association between the cte entry and the cte collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollection the cte collection
	*/
	public static void removeCTECollection(long pk,
		com.liferay.change.tracking.engine.model.CTECollection cteCollection) {
		getPersistence().removeCTECollection(pk, cteCollection);
	}

	/**
	* Removes the association between the cte entry and the cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollectionPKs the primary keys of the cte collections
	*/
	public static void removeCTECollections(long pk, long[] cteCollectionPKs) {
		getPersistence().removeCTECollections(pk, cteCollectionPKs);
	}

	/**
	* Removes the association between the cte entry and the cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollections the cte collections
	*/
	public static void removeCTECollections(long pk,
		List<com.liferay.change.tracking.engine.model.CTECollection> cteCollections) {
		getPersistence().removeCTECollections(pk, cteCollections);
	}

	/**
	* Sets the cte collections associated with the cte entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollectionPKs the primary keys of the cte collections to be associated with the cte entry
	*/
	public static void setCTECollections(long pk, long[] cteCollectionPKs) {
		getPersistence().setCTECollections(pk, cteCollectionPKs);
	}

	/**
	* Sets the cte collections associated with the cte entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollections the cte collections to be associated with the cte entry
	*/
	public static void setCTECollections(long pk,
		List<com.liferay.change.tracking.engine.model.CTECollection> cteCollections) {
		getPersistence().setCTECollections(pk, cteCollections);
	}

	public static CTEEntryPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CTEEntryPersistence, CTEEntryPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTEEntryPersistence.class);

		ServiceTracker<CTEEntryPersistence, CTEEntryPersistence> serviceTracker = new ServiceTracker<CTEEntryPersistence, CTEEntryPersistence>(bundle.getBundleContext(),
				CTEEntryPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}