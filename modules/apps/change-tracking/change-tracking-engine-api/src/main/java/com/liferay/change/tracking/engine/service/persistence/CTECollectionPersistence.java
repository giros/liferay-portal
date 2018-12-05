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

import com.liferay.change.tracking.engine.exception.NoSuchCTECollectionException;
import com.liferay.change.tracking.engine.model.CTECollection;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the cte collection service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.change.tracking.engine.service.persistence.impl.CTECollectionPersistenceImpl
 * @see CTECollectionUtil
 * @generated
 */
@ProviderType
public interface CTECollectionPersistence extends BasePersistence<CTECollection> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTECollectionUtil} to access the cte collection persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the cte collection in the entity cache if it is enabled.
	*
	* @param cteCollection the cte collection
	*/
	public void cacheResult(CTECollection cteCollection);

	/**
	* Caches the cte collections in the entity cache if it is enabled.
	*
	* @param cteCollections the cte collections
	*/
	public void cacheResult(java.util.List<CTECollection> cteCollections);

	/**
	* Creates a new cte collection with the primary key. Does not add the cte collection to the database.
	*
	* @param collectionId the primary key for the new cte collection
	* @return the new cte collection
	*/
	public CTECollection create(long collectionId);

	/**
	* Removes the cte collection with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param collectionId the primary key of the cte collection
	* @return the cte collection that was removed
	* @throws NoSuchCTECollectionException if a cte collection with the primary key could not be found
	*/
	public CTECollection remove(long collectionId)
		throws NoSuchCTECollectionException;

	public CTECollection updateImpl(CTECollection cteCollection);

	/**
	* Returns the cte collection with the primary key or throws a {@link NoSuchCTECollectionException} if it could not be found.
	*
	* @param collectionId the primary key of the cte collection
	* @return the cte collection
	* @throws NoSuchCTECollectionException if a cte collection with the primary key could not be found
	*/
	public CTECollection findByPrimaryKey(long collectionId)
		throws NoSuchCTECollectionException;

	/**
	* Returns the cte collection with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param collectionId the primary key of the cte collection
	* @return the cte collection, or <code>null</code> if a cte collection with the primary key could not be found
	*/
	public CTECollection fetchByPrimaryKey(long collectionId);

	@Override
	public java.util.Map<java.io.Serializable, CTECollection> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the cte collections.
	*
	* @return the cte collections
	*/
	public java.util.List<CTECollection> findAll();

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
	public java.util.List<CTECollection> findAll(int start, int end);

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
	public java.util.List<CTECollection> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTECollection> orderByComparator);

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
	public java.util.List<CTECollection> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CTECollection> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the cte collections from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of cte collections.
	*
	* @return the number of cte collections
	*/
	public int countAll();

	/**
	* Returns the primaryKeys of cte entries associated with the cte collection.
	*
	* @param pk the primary key of the cte collection
	* @return long[] of the primaryKeys of cte entries associated with the cte collection
	*/
	public long[] getCTEEntryPrimaryKeys(long pk);

	/**
	* Returns all the cte entries associated with the cte collection.
	*
	* @param pk the primary key of the cte collection
	* @return the cte entries associated with the cte collection
	*/
	public java.util.List<com.liferay.change.tracking.engine.model.CTEEntry> getCTEEntries(
		long pk);

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
	public java.util.List<com.liferay.change.tracking.engine.model.CTEEntry> getCTEEntries(
		long pk, int start, int end);

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
	public java.util.List<com.liferay.change.tracking.engine.model.CTEEntry> getCTEEntries(
		long pk, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.engine.model.CTEEntry> orderByComparator);

	/**
	* Returns the number of cte entries associated with the cte collection.
	*
	* @param pk the primary key of the cte collection
	* @return the number of cte entries associated with the cte collection
	*/
	public int getCTEEntriesSize(long pk);

	/**
	* Returns <code>true</code> if the cte entry is associated with the cte collection.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntryPK the primary key of the cte entry
	* @return <code>true</code> if the cte entry is associated with the cte collection; <code>false</code> otherwise
	*/
	public boolean containsCTEEntry(long pk, long cteEntryPK);

	/**
	* Returns <code>true</code> if the cte collection has any cte entries associated with it.
	*
	* @param pk the primary key of the cte collection to check for associations with cte entries
	* @return <code>true</code> if the cte collection has any cte entries associated with it; <code>false</code> otherwise
	*/
	public boolean containsCTEEntries(long pk);

	/**
	* Adds an association between the cte collection and the cte entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntryPK the primary key of the cte entry
	*/
	public void addCTEEntry(long pk, long cteEntryPK);

	/**
	* Adds an association between the cte collection and the cte entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntry the cte entry
	*/
	public void addCTEEntry(long pk,
		com.liferay.change.tracking.engine.model.CTEEntry cteEntry);

	/**
	* Adds an association between the cte collection and the cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntryPKs the primary keys of the cte entries
	*/
	public void addCTEEntries(long pk, long[] cteEntryPKs);

	/**
	* Adds an association between the cte collection and the cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntries the cte entries
	*/
	public void addCTEEntries(long pk,
		java.util.List<com.liferay.change.tracking.engine.model.CTEEntry> cteEntries);

	/**
	* Clears all associations between the cte collection and its cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection to clear the associated cte entries from
	*/
	public void clearCTEEntries(long pk);

	/**
	* Removes the association between the cte collection and the cte entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntryPK the primary key of the cte entry
	*/
	public void removeCTEEntry(long pk, long cteEntryPK);

	/**
	* Removes the association between the cte collection and the cte entry. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntry the cte entry
	*/
	public void removeCTEEntry(long pk,
		com.liferay.change.tracking.engine.model.CTEEntry cteEntry);

	/**
	* Removes the association between the cte collection and the cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntryPKs the primary keys of the cte entries
	*/
	public void removeCTEEntries(long pk, long[] cteEntryPKs);

	/**
	* Removes the association between the cte collection and the cte entries. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntries the cte entries
	*/
	public void removeCTEEntries(long pk,
		java.util.List<com.liferay.change.tracking.engine.model.CTEEntry> cteEntries);

	/**
	* Sets the cte entries associated with the cte collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntryPKs the primary keys of the cte entries to be associated with the cte collection
	*/
	public void setCTEEntries(long pk, long[] cteEntryPKs);

	/**
	* Sets the cte entries associated with the cte collection, removing and adding associations as necessary. Also notifies the appropriate model listeners and clears the mapping table finder cache.
	*
	* @param pk the primary key of the cte collection
	* @param cteEntries the cte entries to be associated with the cte collection
	*/
	public void setCTEEntries(long pk,
		java.util.List<com.liferay.change.tracking.engine.model.CTEEntry> cteEntries);
}