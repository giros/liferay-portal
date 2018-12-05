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

package com.liferay.change.tracking.engine.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.engine.model.CTEEntry;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.PersistedResourcedModelLocalService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;

/**
 * Provides the local service interface for CTEEntry. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see CTEEntryLocalServiceUtil
 * @see com.liferay.change.tracking.engine.service.base.CTEEntryLocalServiceBaseImpl
 * @see com.liferay.change.tracking.engine.service.impl.CTEEntryLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface CTEEntryLocalService extends BaseLocalService,
	PersistedModelLocalService, PersistedResourcedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CTEEntryLocalServiceUtil} to access the cte entry local service. Add custom service methods to {@link com.liferay.change.tracking.engine.service.impl.CTEEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void addCTECollectionCTEEntries(long collectionId,
		List<CTEEntry> cteEntries);

	public void addCTECollectionCTEEntries(long collectionId, long[] entryIds);

	public void addCTECollectionCTEEntry(long collectionId, CTEEntry cteEntry);

	public void addCTECollectionCTEEntry(long collectionId, long entryId);

	/**
	* Adds the cte entry to the database. Also notifies the appropriate model listeners.
	*
	* @param cteEntry the cte entry
	* @return the cte entry that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CTEEntry addCTEEntry(CTEEntry cteEntry);

	public void clearCTECollectionCTEEntries(long collectionId);

	/**
	* Creates a new cte entry with the primary key. Does not add the cte entry to the database.
	*
	* @param entryId the primary key for the new cte entry
	* @return the new cte entry
	*/
	@Transactional(enabled = false)
	public CTEEntry createCTEEntry(long entryId);

	public void deleteCTECollectionCTEEntries(long collectionId,
		List<CTEEntry> cteEntries);

	public void deleteCTECollectionCTEEntries(long collectionId, long[] entryIds);

	public void deleteCTECollectionCTEEntry(long collectionId, CTEEntry cteEntry);

	public void deleteCTECollectionCTEEntry(long collectionId, long entryId);

	/**
	* Deletes the cte entry from the database. Also notifies the appropriate model listeners.
	*
	* @param cteEntry the cte entry
	* @return the cte entry that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public CTEEntry deleteCTEEntry(CTEEntry cteEntry);

	/**
	* Deletes the cte entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the cte entry
	* @return the cte entry that was removed
	* @throws PortalException if a cte entry with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public CTEEntry deleteCTEEntry(long entryId) throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.CTEEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.CTEEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CTEEntry fetchCTEEntry(long entryId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEEntry> getCTECollectionCTEEntries(long collectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEEntry> getCTECollectionCTEEntries(long collectionId,
		int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEEntry> getCTECollectionCTEEntries(long collectionId,
		int start, int end, OrderByComparator<CTEEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCTECollectionCTEEntriesCount(long collectionId);

	/**
	* Returns the collectionIds of the cte collections associated with the cte entry.
	*
	* @param entryId the entryId of the cte entry
	* @return long[] the collectionIds of cte collections associated with the cte entry
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long[] getCTECollectionPrimaryKeys(long entryId);

	/**
	* Returns a range of all the cte entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.change.tracking.engine.model.impl.CTEEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of cte entries
	* @param end the upper bound of the range of cte entries (not inclusive)
	* @return the range of cte entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<CTEEntry> getCTEEntries(int start, int end);

	/**
	* Returns the number of cte entries.
	*
	* @return the number of cte entries
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCTEEntriesCount();

	/**
	* Returns the cte entry with the primary key.
	*
	* @param entryId the primary key of the cte entry
	* @return the cte entry
	* @throws PortalException if a cte entry with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public CTEEntry getCTEEntry(long entryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<?extends PersistedModel> getPersistedModel(long resourcePrimKey)
		throws PortalException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasCTECollectionCTEEntries(long collectionId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasCTECollectionCTEEntry(long collectionId, long entryId);

	public void setCTECollectionCTEEntries(long collectionId, long[] entryIds);

	/**
	* Updates the cte entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cteEntry the cte entry
	* @return the cte entry that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public CTEEntry updateCTEEntry(CTEEntry cteEntry);
}