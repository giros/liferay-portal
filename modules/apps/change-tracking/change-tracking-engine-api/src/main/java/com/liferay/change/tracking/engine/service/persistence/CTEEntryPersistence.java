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

import com.liferay.change.tracking.engine.exception.NoSuchCTEEntryException;
import com.liferay.change.tracking.engine.model.CTEEntry;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cte entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.change.tracking.engine.service.persistence.impl.CTEEntryPersistenceImpl
 * @see CTEEntryUtil
 * @generated
 */
@ProviderType
public interface CTEEntryPersistence extends BasePersistence<CTEEntry> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTEEntryUtil} to access the cte entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the cte entries where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the matching cte entries
	*/
	public java.util.List<CTEEntry> findByResourcePrimKey(long resourcePrimKey);

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
	public java.util.List<CTEEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end);

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
	public java.util.List<CTEEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEEntry> orderByComparator);

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
	public java.util.List<CTEEntry> findByResourcePrimKey(
		long resourcePrimKey, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first cte entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cte entry
	* @throws NoSuchCTEEntryException if a matching cte entry could not be found
	*/
	public CTEEntry findByResourcePrimKey_First(long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<CTEEntry> orderByComparator)
		throws NoSuchCTEEntryException;

	/**
	* Returns the first cte entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching cte entry, or <code>null</code> if a matching cte entry could not be found
	*/
	public CTEEntry fetchByResourcePrimKey_First(long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<CTEEntry> orderByComparator);

	/**
	* Returns the last cte entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cte entry
	* @throws NoSuchCTEEntryException if a matching cte entry could not be found
	*/
	public CTEEntry findByResourcePrimKey_Last(long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<CTEEntry> orderByComparator)
		throws NoSuchCTEEntryException;

	/**
	* Returns the last cte entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching cte entry, or <code>null</code> if a matching cte entry could not be found
	*/
	public CTEEntry fetchByResourcePrimKey_Last(long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<CTEEntry> orderByComparator);

	/**
	* Returns the cte entries before and after the current cte entry in the ordered set where resourcePrimKey = &#63;.
	*
	* @param entryId the primary key of the current cte entry
	* @param resourcePrimKey the resource prim key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next cte entry
	* @throws NoSuchCTEEntryException if a cte entry with the primary key could not be found
	*/
	public CTEEntry[] findByResourcePrimKey_PrevAndNext(long entryId,
		long resourcePrimKey,
		com.liferay.portal.kernel.util.OrderByComparator<CTEEntry> orderByComparator)
		throws NoSuchCTEEntryException;

	/**
	* Removes all the cte entries where resourcePrimKey = &#63; from the database.
	*
	* @param resourcePrimKey the resource prim key
	*/
	public void removeByResourcePrimKey(long resourcePrimKey);

	/**
	* Returns the number of cte entries where resourcePrimKey = &#63;.
	*
	* @param resourcePrimKey the resource prim key
	* @return the number of matching cte entries
	*/
	public int countByResourcePrimKey(long resourcePrimKey);

	/**
	* Caches the cte entry in the entity cache if it is enabled.
	*
	* @param cteEntry the cte entry
	*/
	public void cacheResult(CTEEntry cteEntry);

	/**
	* Caches the cte entries in the entity cache if it is enabled.
	*
	* @param cteEntries the cte entries
	*/
	public void cacheResult(java.util.List<CTEEntry> cteEntries);

	/**
	* Creates a new cte entry with the primary key. Does not add the cte entry to the database.
	*
	* @param entryId the primary key for the new cte entry
	* @return the new cte entry
	*/
	public CTEEntry create(long entryId);

	/**
	* Removes the cte entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the cte entry
	* @return the cte entry that was removed
	* @throws NoSuchCTEEntryException if a cte entry with the primary key could not be found
	*/
	public CTEEntry remove(long entryId) throws NoSuchCTEEntryException;

	public CTEEntry updateImpl(CTEEntry cteEntry);

	/**
	* Returns the cte entry with the primary key or throws a {@link NoSuchCTEEntryException} if it could not be found.
	*
	* @param entryId the primary key of the cte entry
	* @return the cte entry
	* @throws NoSuchCTEEntryException if a cte entry with the primary key could not be found
	*/
	public CTEEntry findByPrimaryKey(long entryId)
		throws NoSuchCTEEntryException;

	/**
	* Returns the cte entry with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param entryId the primary key of the cte entry
	* @return the cte entry, or <code>null</code> if a cte entry with the primary key could not be found
	*/
	public CTEEntry fetchByPrimaryKey(long entryId);

	@Override
	public java.util.Map<java.io.Serializable, CTEEntry> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cte entries.
	*
	* @return the cte entries
	*/
	public java.util.List<CTEEntry> findAll();

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
	public java.util.List<CTEEntry> findAll(int start, int end);

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
	public java.util.List<CTEEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEEntry> orderByComparator);

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
	public java.util.List<CTEEntry> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTEEntry> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cte entries from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cte entries.
	*
	* @return the number of cte entries
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of cte collections associated with the cte entry.
	*
	* @param pk the primary key of the cte entry
	* @return long[] of the primaryKeys of cte collections associated with the cte entry
	*/
	public long[] getCTECollectionPrimaryKeys(long pk);

	/**
	* Returns all the cte collections associated with the cte entry.
	*
	* @param pk the primary key of the cte entry
	* @return the cte collections associated with the cte entry
	*/
	public java.util.List<com.liferay.change.tracking.engine.model.CTECollection> getCTECollections(
		long pk);

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
	public java.util.List<com.liferay.change.tracking.engine.model.CTECollection> getCTECollections(
		long pk, int start, int end);

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
	public java.util.List<com.liferay.change.tracking.engine.model.CTECollection> getCTECollections(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.engine.model.CTECollection> orderByComparator);

	/**
	* Returns the number of cte collections associated with the cte entry.
	*
	* @param pk the primary key of the cte entry
	* @return the number of cte collections associated with the cte entry
	*/
	public int getCTECollectionsSize(long pk);

	/**
	* Returns <code>true</code> if the cte collection is associated with the cte entry.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollectionPK the primary key of the cte collection
	* @return <code>true</code> if the cte collection is associated with the cte entry; <code>false</code> otherwise
	*/
	public boolean containsCTECollection(long pk, long cteCollectionPK);

	/**
	* Returns <code>true</code> if the cte entry has any cte collections associated with it.
	*
	* @param pk the primary key of the cte entry to check for associations with cte collections
	* @return <code>true</code> if the cte entry has any cte collections associated with it; <code>false</code> otherwise
	*/
	public boolean containsCTECollections(long pk);

	/**
	* Adds an association between the cte entry and the cte collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollectionPK the primary key of the cte collection
	*/
	public void addCTECollection(long pk, long cteCollectionPK);

	/**
	* Adds an association between the cte entry and the cte collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollection the cte collection
	*/
	public void addCTECollection(long pk,
		com.liferay.change.tracking.engine.model.CTECollection cteCollection);

	/**
	* Adds an association between the cte entry and the cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollectionPKs the primary keys of the cte collections
	*/
	public void addCTECollections(long pk, long[] cteCollectionPKs);

	/**
	* Adds an association between the cte entry and the cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollections the cte collections
	*/
	public void addCTECollections(long pk,
		java.util.List<com.liferay.change.tracking.engine.model.CTECollection> cteCollections);

	/**
	* Clears all associations between the cte entry and its cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry to clear the associated cte collections from
	*/
	public void clearCTECollections(long pk);

	/**
	* Removes the association between the cte entry and the cte collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollectionPK the primary key of the cte collection
	*/
	public void removeCTECollection(long pk, long cteCollectionPK);

	/**
	* Removes the association between the cte entry and the cte collection. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollection the cte collection
	*/
	public void removeCTECollection(long pk,
		com.liferay.change.tracking.engine.model.CTECollection cteCollection);

	/**
	* Removes the association between the cte entry and the cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollectionPKs the primary keys of the cte collections
	*/
	public void removeCTECollections(long pk, long[] cteCollectionPKs);

	/**
	* Removes the association between the cte entry and the cte collections. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollections the cte collections
	*/
	public void removeCTECollections(long pk,
		java.util.List<com.liferay.change.tracking.engine.model.CTECollection> cteCollections);

	/**
	* Sets the cte collections associated with the cte entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollectionPKs the primary keys of the cte collections to be associated with the cte entry
	*/
	public void setCTECollections(long pk, long[] cteCollectionPKs);

	/**
	* Sets the cte collections associated with the cte entry, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte entry
	* @param cteCollections the cte collections to be associated with the cte entry
	*/
	public void setCTECollections(long pk,
		java.util.List<com.liferay.change.tracking.engine.model.CTECollection> cteCollections);
}