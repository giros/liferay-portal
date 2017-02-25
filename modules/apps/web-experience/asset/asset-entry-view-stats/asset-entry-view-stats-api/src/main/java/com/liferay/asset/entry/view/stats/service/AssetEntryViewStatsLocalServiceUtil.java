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

package com.liferay.asset.entry.view.stats.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for AssetEntryViewStats. This utility wraps
 * {@link com.liferay.asset.entry.view.stats.service.impl.AssetEntryViewStatsLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryViewStatsLocalService
 * @see com.liferay.asset.entry.view.stats.service.base.AssetEntryViewStatsLocalServiceBaseImpl
 * @see com.liferay.asset.entry.view.stats.service.impl.AssetEntryViewStatsLocalServiceImpl
 * @generated
 */
@ProviderType
public class AssetEntryViewStatsLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.asset.entry.view.stats.service.impl.AssetEntryViewStatsLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the asset entry view stats to the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntryViewStats the asset entry view stats
	* @return the asset entry view stats that was added
	*/
	public static com.liferay.asset.entry.view.stats.model.AssetEntryViewStats addAssetEntryViewStats(
		com.liferay.asset.entry.view.stats.model.AssetEntryViewStats assetEntryViewStats) {
		return getService().addAssetEntryViewStats(assetEntryViewStats);
	}

	/**
	* Creates a new asset entry view stats with the primary key. Does not add the asset entry view stats to the database.
	*
	* @param statsId the primary key for the new asset entry view stats
	* @return the new asset entry view stats
	*/
	public static com.liferay.asset.entry.view.stats.model.AssetEntryViewStats createAssetEntryViewStats(
		long statsId) {
		return getService().createAssetEntryViewStats(statsId);
	}

	/**
	* Deletes the asset entry view stats from the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntryViewStats the asset entry view stats
	* @return the asset entry view stats that was removed
	*/
	public static com.liferay.asset.entry.view.stats.model.AssetEntryViewStats deleteAssetEntryViewStats(
		com.liferay.asset.entry.view.stats.model.AssetEntryViewStats assetEntryViewStats) {
		return getService().deleteAssetEntryViewStats(assetEntryViewStats);
	}

	/**
	* Deletes the asset entry view stats with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsId the primary key of the asset entry view stats
	* @return the asset entry view stats that was removed
	* @throws PortalException if a asset entry view stats with the primary key could not be found
	*/
	public static com.liferay.asset.entry.view.stats.model.AssetEntryViewStats deleteAssetEntryViewStats(
		long statsId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteAssetEntryViewStats(statsId);
	}

	public static com.liferay.asset.entry.view.stats.model.AssetEntryViewStats fetchAssetEntryViewStats(
		long statsId) {
		return getService().fetchAssetEntryViewStats(statsId);
	}

	/**
	* Returns the asset entry view stats with the primary key.
	*
	* @param statsId the primary key of the asset entry view stats
	* @return the asset entry view stats
	* @throws PortalException if a asset entry view stats with the primary key could not be found
	*/
	public static com.liferay.asset.entry.view.stats.model.AssetEntryViewStats getAssetEntryViewStats(
		long statsId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAssetEntryViewStats(statsId);
	}

	/**
	* Updates the asset entry view stats in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param assetEntryViewStats the asset entry view stats
	* @return the asset entry view stats that was updated
	*/
	public static com.liferay.asset.entry.view.stats.model.AssetEntryViewStats updateAssetEntryViewStats(
		com.liferay.asset.entry.view.stats.model.AssetEntryViewStats assetEntryViewStats) {
		return getService().updateAssetEntryViewStats(assetEntryViewStats);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of asset entry view statses.
	*
	* @return the number of asset entry view statses
	*/
	public static int getAssetEntryViewStatsesCount() {
		return getService().getAssetEntryViewStatsesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.entry.view.stats.model.impl.AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.entry.view.stats.model.impl.AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns a range of all the asset entry view statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.asset.entry.view.stats.model.impl.AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry view statses
	* @param end the upper bound of the range of asset entry view statses (not inclusive)
	* @return the range of asset entry view statses
	*/
	public static java.util.List<com.liferay.asset.entry.view.stats.model.AssetEntryViewStats> getAssetEntryViewStatses(
		int start, int end) {
		return getService().getAssetEntryViewStatses(start, end);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static AssetEntryViewStatsLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<AssetEntryViewStatsLocalService, AssetEntryViewStatsLocalService> _serviceTracker =
		ServiceTrackerFactory.open(AssetEntryViewStatsLocalService.class);
}