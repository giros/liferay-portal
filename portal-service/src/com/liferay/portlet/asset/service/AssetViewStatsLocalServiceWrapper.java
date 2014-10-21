/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetViewStatsLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetViewStatsLocalService
 * @generated
 */
@ProviderType
public class AssetViewStatsLocalServiceWrapper
	implements AssetViewStatsLocalService,
		ServiceWrapper<AssetViewStatsLocalService> {
	public AssetViewStatsLocalServiceWrapper(
		AssetViewStatsLocalService assetViewStatsLocalService) {
		_assetViewStatsLocalService = assetViewStatsLocalService;
	}

	/**
	* Adds the asset view stats to the database. Also notifies the appropriate model listeners.
	*
	* @param assetViewStats the asset view stats
	* @return the asset view stats that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetViewStats addAssetViewStats(
		com.liferay.portlet.asset.model.AssetViewStats assetViewStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.addAssetViewStats(assetViewStats);
	}

	/**
	* Creates a new asset view stats with the primary key. Does not add the asset view stats to the database.
	*
	* @param viewStatsId the primary key for the new asset view stats
	* @return the new asset view stats
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetViewStats createAssetViewStats(
		long viewStatsId) {
		return _assetViewStatsLocalService.createAssetViewStats(viewStatsId);
	}

	/**
	* Deletes the asset view stats with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param viewStatsId the primary key of the asset view stats
	* @return the asset view stats that was removed
	* @throws PortalException if a asset view stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetViewStats deleteAssetViewStats(
		long viewStatsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.deleteAssetViewStats(viewStatsId);
	}

	/**
	* Deletes the asset view stats from the database. Also notifies the appropriate model listeners.
	*
	* @param assetViewStats the asset view stats
	* @return the asset view stats that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetViewStats deleteAssetViewStats(
		com.liferay.portlet.asset.model.AssetViewStats assetViewStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.deleteAssetViewStats(assetViewStats);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _assetViewStatsLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetViewStats fetchAssetViewStats(
		long viewStatsId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.fetchAssetViewStats(viewStatsId);
	}

	/**
	* Returns the asset view stats with the primary key.
	*
	* @param viewStatsId the primary key of the asset view stats
	* @return the asset view stats
	* @throws PortalException if a asset view stats with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetViewStats getAssetViewStats(
		long viewStatsId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.getAssetViewStats(viewStatsId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the asset view statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset view statses
	* @param end the upper bound of the range of asset view statses (not inclusive)
	* @return the range of asset view statses
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.liferay.portlet.asset.model.AssetViewStats> getAssetViewStatses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.getAssetViewStatses(start, end);
	}

	/**
	* Returns the number of asset view statses.
	*
	* @return the number of asset view statses
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getAssetViewStatsesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.getAssetViewStatsesCount();
	}

	/**
	* Updates the asset view stats in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetViewStats the asset view stats
	* @return the asset view stats that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.liferay.portlet.asset.model.AssetViewStats updateAssetViewStats(
		com.liferay.portlet.asset.model.AssetViewStats assetViewStats)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.updateAssetViewStats(assetViewStats);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _assetViewStatsLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_assetViewStatsLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portlet.asset.model.AssetViewStats addAssetViewStats(
		long userId, long classNameId, long classPK, java.util.Date viewDate)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetViewStatsLocalService.addAssetViewStats(userId,
			classNameId, classPK, viewDate);
	}

	@Override
	public java.util.Map<java.util.Date, java.lang.Long> countViewsByDay(
		long classNameId, long classPK) {
		return _assetViewStatsLocalService.countViewsByDay(classNameId, classPK);
	}

	@Override
	public java.util.Map<java.util.Date, java.lang.Long> countViewsByWeek(
		long classNameId, long classPK) {
		return _assetViewStatsLocalService.countViewsByWeek(classNameId, classPK);
	}

	@Override
	public java.util.Map<java.util.Date, java.lang.Long> countViewsByMonth(
		long classNameId, long classPK) {
		return _assetViewStatsLocalService.countViewsByMonth(classNameId,
			classPK);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public AssetViewStatsLocalService getWrappedAssetViewStatsLocalService() {
		return _assetViewStatsLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedAssetViewStatsLocalService(
		AssetViewStatsLocalService assetViewStatsLocalService) {
		_assetViewStatsLocalService = assetViewStatsLocalService;
	}

	@Override
	public AssetViewStatsLocalService getWrappedService() {
		return _assetViewStatsLocalService;
	}

	@Override
	public void setWrappedService(
		AssetViewStatsLocalService assetViewStatsLocalService) {
		_assetViewStatsLocalService = assetViewStatsLocalService;
	}

	private AssetViewStatsLocalService _assetViewStatsLocalService;
}