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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CTEEntryLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see CTEEntryLocalService
 * @generated
 */
@ProviderType
public class CTEEntryLocalServiceWrapper implements CTEEntryLocalService,
	ServiceWrapper<CTEEntryLocalService> {
	public CTEEntryLocalServiceWrapper(
		CTEEntryLocalService cteEntryLocalService) {
		_cteEntryLocalService = cteEntryLocalService;
	}

	@Override
	public void addCTECollectionCTEEntries(long collectionId,
		java.util.List<com.liferay.change.tracking.engine.model.CTEEntry> cteEntries) {
		_cteEntryLocalService.addCTECollectionCTEEntries(collectionId,
			cteEntries);
	}

	@Override
	public void addCTECollectionCTEEntries(long collectionId, long[] entryIds) {
		_cteEntryLocalService.addCTECollectionCTEEntries(collectionId, entryIds);
	}

	@Override
	public void addCTECollectionCTEEntry(long collectionId,
		com.liferay.change.tracking.engine.model.CTEEntry cteEntry) {
		_cteEntryLocalService.addCTECollectionCTEEntry(collectionId, cteEntry);
	}

	@Override
	public void addCTECollectionCTEEntry(long collectionId, long entryId) {
		_cteEntryLocalService.addCTECollectionCTEEntry(collectionId, entryId);
	}

	/**
	* Adds the cte entry to the database. Also notifies the appropriate model listeners.
	*
	* @param cteEntry the cte entry
	* @return the cte entry that was added
	*/
	@Override
	public com.liferay.change.tracking.engine.model.CTEEntry addCTEEntry(
		com.liferay.change.tracking.engine.model.CTEEntry cteEntry) {
		return _cteEntryLocalService.addCTEEntry(cteEntry);
	}

	@Override
	public void clearCTECollectionCTEEntries(long collectionId) {
		_cteEntryLocalService.clearCTECollectionCTEEntries(collectionId);
	}

	/**
	* Creates a new cte entry with the primary key. Does not add the cte entry to the database.
	*
	* @param entryId the primary key for the new cte entry
	* @return the new cte entry
	*/
	@Override
	public com.liferay.change.tracking.engine.model.CTEEntry createCTEEntry(
		long entryId) {
		return _cteEntryLocalService.createCTEEntry(entryId);
	}

	@Override
	public void deleteCTECollectionCTEEntries(long collectionId,
		java.util.List<com.liferay.change.tracking.engine.model.CTEEntry> cteEntries) {
		_cteEntryLocalService.deleteCTECollectionCTEEntries(collectionId,
			cteEntries);
	}

	@Override
	public void deleteCTECollectionCTEEntries(long collectionId, long[] entryIds) {
		_cteEntryLocalService.deleteCTECollectionCTEEntries(collectionId,
			entryIds);
	}

	@Override
	public void deleteCTECollectionCTEEntry(long collectionId,
		com.liferay.change.tracking.engine.model.CTEEntry cteEntry) {
		_cteEntryLocalService.deleteCTECollectionCTEEntry(collectionId, cteEntry);
	}

	@Override
	public void deleteCTECollectionCTEEntry(long collectionId, long entryId) {
		_cteEntryLocalService.deleteCTECollectionCTEEntry(collectionId, entryId);
	}

	/**
	* Deletes the cte entry from the database. Also notifies the appropriate model listeners.
	*
	* @param cteEntry the cte entry
	* @return the cte entry that was removed
	*/
	@Override
	public com.liferay.change.tracking.engine.model.CTEEntry deleteCTEEntry(
		com.liferay.change.tracking.engine.model.CTEEntry cteEntry) {
		return _cteEntryLocalService.deleteCTEEntry(cteEntry);
	}

	/**
	* Deletes the cte entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the cte entry
	* @return the cte entry that was removed
	* @throws PortalException if a cte entry with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.engine.model.CTEEntry deleteCTEEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cteEntryLocalService.deleteCTEEntry(entryId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cteEntryLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _cteEntryLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _cteEntryLocalService.dynamicQuery(dynamicQuery);
	}

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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _cteEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _cteEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _cteEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _cteEntryLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.liferay.change.tracking.engine.model.CTEEntry fetchCTEEntry(
		long entryId) {
		return _cteEntryLocalService.fetchCTEEntry(entryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _cteEntryLocalService.getActionableDynamicQuery();
	}

	@Override
	public java.util.List<com.liferay.change.tracking.engine.model.CTEEntry> getCTECollectionCTEEntries(
		long collectionId) {
		return _cteEntryLocalService.getCTECollectionCTEEntries(collectionId);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.engine.model.CTEEntry> getCTECollectionCTEEntries(
		long collectionId, int start, int end) {
		return _cteEntryLocalService.getCTECollectionCTEEntries(collectionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.change.tracking.engine.model.CTEEntry> getCTECollectionCTEEntries(
		long collectionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.change.tracking.engine.model.CTEEntry> orderByComparator) {
		return _cteEntryLocalService.getCTECollectionCTEEntries(collectionId,
			start, end, orderByComparator);
	}

	@Override
	public int getCTECollectionCTEEntriesCount(long collectionId) {
		return _cteEntryLocalService.getCTECollectionCTEEntriesCount(collectionId);
	}

	/**
	* Returns the collectionIds of the cte collections associated with the cte entry.
	*
	* @param entryId the entryId of the cte entry
	* @return long[] the collectionIds of cte collections associated with the cte entry
	*/
	@Override
	public long[] getCTECollectionPrimaryKeys(long entryId) {
		return _cteEntryLocalService.getCTECollectionPrimaryKeys(entryId);
	}

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
	@Override
	public java.util.List<com.liferay.change.tracking.engine.model.CTEEntry> getCTEEntries(
		int start, int end) {
		return _cteEntryLocalService.getCTEEntries(start, end);
	}

	/**
	* Returns the number of cte entries.
	*
	* @return the number of cte entries
	*/
	@Override
	public int getCTEEntriesCount() {
		return _cteEntryLocalService.getCTEEntriesCount();
	}

	/**
	* Returns the cte entry with the primary key.
	*
	* @param entryId the primary key of the cte entry
	* @return the cte entry
	* @throws PortalException if a cte entry with the primary key could not be found
	*/
	@Override
	public com.liferay.change.tracking.engine.model.CTEEntry getCTEEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cteEntryLocalService.getCTEEntry(entryId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _cteEntryLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cteEntryLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<?extends com.liferay.portal.kernel.model.PersistedModel> getPersistedModel(
		long resourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cteEntryLocalService.getPersistedModel(resourcePrimKey);
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cteEntryLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public boolean hasCTECollectionCTEEntries(long collectionId) {
		return _cteEntryLocalService.hasCTECollectionCTEEntries(collectionId);
	}

	@Override
	public boolean hasCTECollectionCTEEntry(long collectionId, long entryId) {
		return _cteEntryLocalService.hasCTECollectionCTEEntry(collectionId,
			entryId);
	}

	@Override
	public void setCTECollectionCTEEntries(long collectionId, long[] entryIds) {
		_cteEntryLocalService.setCTECollectionCTEEntries(collectionId, entryIds);
	}

	/**
	* Updates the cte entry in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param cteEntry the cte entry
	* @return the cte entry that was updated
	*/
	@Override
	public com.liferay.change.tracking.engine.model.CTEEntry updateCTEEntry(
		com.liferay.change.tracking.engine.model.CTEEntry cteEntry) {
		return _cteEntryLocalService.updateCTEEntry(cteEntry);
	}

	@Override
	public CTEEntryLocalService getWrappedService() {
		return _cteEntryLocalService;
	}

	@Override
	public void setWrappedService(CTEEntryLocalService cteEntryLocalService) {
		_cteEntryLocalService = cteEntryLocalService;
	}

	private CTEEntryLocalService _cteEntryLocalService;
}