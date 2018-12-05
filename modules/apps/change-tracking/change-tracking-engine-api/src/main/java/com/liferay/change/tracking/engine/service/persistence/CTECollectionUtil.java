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

import com.liferay.change.tracking.engine.model.CTECollection;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the cte collection service. This utility wraps {@link com.liferay.change.tracking.engine.service.persistence.impl.CTECollectionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTECollectionPersistence
 * @see com.liferay.change.tracking.engine.service.persistence.impl.CTECollectionPersistenceImpl
 * @generated
 */
@ProviderType
public class CTECollectionUtil {
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
	public static void clearCache(CTECollection cteCollection) {
		getPersistence().clearCache(cteCollection);
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
	public static List<CTECollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CTECollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CTECollection> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CTECollection> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CTECollection update(CTECollection cteCollection) {
		return getPersistence().update(cteCollection);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CTECollection update(CTECollection cteCollection,
		ServiceContext serviceContext) {
		return getPersistence().update(cteCollection, serviceContext);
	}

	/**
	* Caches the cte collection in the entity cache if it is enabled.
	*
	* @param cteCollection the cte collection
	*/
	public static void cacheResult(CTECollection cteCollection) {
		getPersistence().cacheResult(cteCollection);
	}

	/**
	* Caches the cte collections in the entity cache if it is enabled.
	*
	* @param cteCollections the cte collections
	*/
	public static void cacheResult(List<CTECollection> cteCollections) {
		getPersistence().cacheResult(cteCollections);
	}

	/**
	* Creates a new cte collection with the primary key. Does not add the cte collection to the database.
	*
	* @param collectionId the primary key for the new cte collection
	* @return the new cte collection
	*/
	public static CTECollection create(long collectionId) {
		return getPersistence().create(collectionId);
	}

	/**
	* Removes the cte collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param collectionId the primary key of the cte collection
	* @return the cte collection that was removed
	* @throws NoSuchCTECollectionException if a cte collection with the primary key could not be found
	*/
	public static CTECollection remove(long collectionId)
		throws com.liferay.change.tracking.engine.exception.NoSuchCTECollectionException {
		return getPersistence().remove(collectionId);
	}

	public static CTECollection updateImpl(CTECollection cteCollection) {
		return getPersistence().updateImpl(cteCollection);
	}

	/**
	* Returns the cte collection with the primary key or throws a {@link NoSuchCTECollectionException} if it could not be found.
	*
	* @param collectionId the primary key of the cte collection
	* @return the cte collection
	* @throws NoSuchCTECollectionException if a cte collection with the primary key could not be found
	*/
	public static CTECollection findByPrimaryKey(long collectionId)
		throws com.liferay.change.tracking.engine.exception.NoSuchCTECollectionException {
		return getPersistence().findByPrimaryKey(collectionId);
	}

	/**
	* Returns the cte collection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param collectionId the primary key of the cte collection
	* @return the cte collection, or <code>null</code> if a cte collection with the primary key could not be found
	*/
	public static CTECollection fetchByPrimaryKey(long collectionId) {
		return getPersistence().fetchByPrimaryKey(collectionId);
	}

	public static java.util.Map<java.io.Serializable, CTECollection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the cte collections.
	*
	* @return the cte collections
	*/
	public static List<CTECollection> findAll() {
		return getPersistence().findAll();
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
	public static List<CTECollection> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
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
	public static List<CTECollection> findAll(int start, int end,
		OrderByComparator<CTECollection> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<CTECollection> findAll(int start, int end,
		OrderByComparator<CTECollection> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the cte collections from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of cte collections.
	*
	* @return the number of cte collections
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	/**
	* Returns the primaryKeys of cte entries associated with the cte collection.
	*
	* @param pk the primary key of the cte collection
	* @return long[] of the primaryKeys of cte entries associated with the cte collection
	*/
	public static long[] getCTEEntryPrimaryKeys(long pk) {
		return getPersistence().getCTEEntryPrimaryKeys(pk);
	}

	/**
	* Returns all the cte entries associated with the cte collection.
	*
	* @param pk the primary key of the cte collection
	* @return the cte entries associated with the cte collection
	*/
	public static List<com.liferay.change.tracking.engine.model.CTEEntry> getCTEEntries(
		long pk) {
		return getPersistence().getCTEEntries(pk);
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
	public static List<com.liferay.change.tracking.engine.model.CTEEntry> getCTEEntries(
		long pk, int start, int end) {
		return getPersistence().getCTEEntries(pk, start, end);
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
	public static List<com.liferay.change.tracking.engine.model.CTEEntry> getCTEEntries(
		long pk, int start, int end,
		OrderByComparator<com.liferay.change.tracking.engine.model.CTEEntry> orderByComparator) {
		return getPersistence().getCTEEntries(pk, start, end, orderByComparator);
	}

	/**
	* Returns the number of cte entries associated with the cte collection.
	*
	* @param pk the primary key of the cte collection
	* @return the number of cte entries associated with the cte collection
	*/
	public static int getCTEEntriesSize(long pk) {
		return getPersistence().getCTEEntriesSize(pk);
	}

	/**
	* Returns <code>true</code> if the cte entry is associated with the cte collection.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntryPK the primary key of the cte entry
	* @return <code>true</code> if the cte entry is associated with the cte collection; <code>false</code> otherwise
	*/
	public static boolean containsCTEEntry(long pk, long cteEntryPK) {
		return getPersistence().containsCTEEntry(pk, cteEntryPK);
	}

	/**
	* Returns <code>true</code> if the cte collection has any cte entries associated with it.
	*
	* @param pk the primary key of the cte collection to check for associations with cte entries
	* @return <code>true</code> if the cte collection has any cte entries associated with it; <code>false</code> otherwise
	*/
	public static boolean containsCTEEntries(long pk) {
		return getPersistence().containsCTEEntries(pk);
	}

	/**
	* Adds an association between the cte collection and the cte entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntryPK the primary key of the cte entry
	*/
	public static void addCTEEntry(long pk, long cteEntryPK) {
		getPersistence().addCTEEntry(pk, cteEntryPK);
	}

	/**
	* Adds an association between the cte collection and the cte entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntry the cte entry
	*/
	public static void addCTEEntry(long pk,
		com.liferay.change.tracking.engine.model.CTEEntry cteEntry) {
		getPersistence().addCTEEntry(pk, cteEntry);
	}

	/**
	* Adds an association between the cte collection and the cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntryPKs the primary keys of the cte entries
	*/
	public static void addCTEEntries(long pk, long[] cteEntryPKs) {
		getPersistence().addCTEEntries(pk, cteEntryPKs);
	}

	/**
	* Adds an association between the cte collection and the cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntries the cte entries
	*/
	public static void addCTEEntries(long pk,
		List<com.liferay.change.tracking.engine.model.CTEEntry> cteEntries) {
		getPersistence().addCTEEntries(pk, cteEntries);
	}

	/**
	* Clears all associations between the cte collection and its cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection to clear the associated cte entries from
	*/
	public static void clearCTEEntries(long pk) {
		getPersistence().clearCTEEntries(pk);
	}

	/**
	* Removes the association between the cte collection and the cte entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntryPK the primary key of the cte entry
	*/
	public static void removeCTEEntry(long pk, long cteEntryPK) {
		getPersistence().removeCTEEntry(pk, cteEntryPK);
	}

	/**
	* Removes the association between the cte collection and the cte entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntry the cte entry
	*/
	public static void removeCTEEntry(long pk,
		com.liferay.change.tracking.engine.model.CTEEntry cteEntry) {
		getPersistence().removeCTEEntry(pk, cteEntry);
	}

	/**
	* Removes the association between the cte collection and the cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntryPKs the primary keys of the cte entries
	*/
	public static void removeCTEEntries(long pk, long[] cteEntryPKs) {
		getPersistence().removeCTEEntries(pk, cteEntryPKs);
	}

	/**
	* Removes the association between the cte collection and the cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntries the cte entries
	*/
	public static void removeCTEEntries(long pk,
		List<com.liferay.change.tracking.engine.model.CTEEntry> cteEntries) {
		getPersistence().removeCTEEntries(pk, cteEntries);
	}

	/**
	* Sets the cte entries associated with the cte collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntryPKs the primary keys of the cte entries to be associated with the cte collection
	*/
	public static void setCTEEntries(long pk, long[] cteEntryPKs) {
		getPersistence().setCTEEntries(pk, cteEntryPKs);
	}

	/**
	* Sets the cte entries associated with the cte collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntries the cte entries to be associated with the cte collection
	*/
	public static void setCTEEntries(long pk,
		List<com.liferay.change.tracking.engine.model.CTEEntry> cteEntries) {
		getPersistence().setCTEEntries(pk, cteEntries);
	}

	public static CTECollectionPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CTECollectionPersistence, CTECollectionPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CTECollectionPersistence.class);

		ServiceTracker<CTECollectionPersistence, CTECollectionPersistence> serviceTracker =
			new ServiceTracker<CTECollectionPersistence, CTECollectionPersistence>(bundle.getBundleContext(),
				CTECollectionPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}