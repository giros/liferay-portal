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

package com.liferay.asset.kernel.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.kernel.model.AssetEntryViewStats;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;

import java.util.Date;
import java.util.List;

/**
 * The persistence utility for the asset entry view stats service. This utility wraps {@link com.liferay.portlet.asset.service.persistence.impl.AssetEntryViewStatsPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryViewStatsPersistence
 * @see com.liferay.portlet.asset.service.persistence.impl.AssetEntryViewStatsPersistenceImpl
 * @generated
 */
@ProviderType
public class AssetEntryViewStatsUtil {
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
	public static void clearCache(AssetEntryViewStats assetEntryViewStats) {
		getPersistence().clearCache(assetEntryViewStats);
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
	public static List<AssetEntryViewStats> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AssetEntryViewStats> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AssetEntryViewStats> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static AssetEntryViewStats update(
		AssetEntryViewStats assetEntryViewStats) {
		return getPersistence().update(assetEntryViewStats);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static AssetEntryViewStats update(
		AssetEntryViewStats assetEntryViewStats, ServiceContext serviceContext) {
		return getPersistence().update(assetEntryViewStats, serviceContext);
	}

	/**
	* Returns all the asset entry view statses where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching asset entry view statses
	*/
	public static List<AssetEntryViewStats> findByC_C(long classNameId,
		long classPK) {
		return getPersistence().findByC_C(classNameId, classPK);
	}

	/**
	* Returns a range of all the asset entry view statses where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of asset entry view statses
	* @param end the upper bound of the range of asset entry view statses (not inclusive)
	* @return the range of matching asset entry view statses
	*/
	public static List<AssetEntryViewStats> findByC_C(long classNameId,
		long classPK, int start, int end) {
		return getPersistence().findByC_C(classNameId, classPK, start, end);
	}

	/**
	* Returns an ordered range of all the asset entry view statses where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of asset entry view statses
	* @param end the upper bound of the range of asset entry view statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entry view statses
	*/
	public static List<AssetEntryViewStats> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entry view statses where classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param start the lower bound of the range of asset entry view statses
	* @param end the upper bound of the range of asset entry view statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entry view statses
	*/
	public static List<AssetEntryViewStats> findByC_C(long classNameId,
		long classPK, int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C(classNameId, classPK, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry view stats
	* @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	*/
	public static AssetEntryViewStats findByC_C_First(long classNameId,
		long classPK, OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException {
		return getPersistence()
				   .findByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	*/
	public static AssetEntryViewStats fetchByC_C_First(long classNameId,
		long classPK, OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_First(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry view stats
	* @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	*/
	public static AssetEntryViewStats findByC_C_Last(long classNameId,
		long classPK, OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException {
		return getPersistence()
				   .findByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	*/
	public static AssetEntryViewStats fetchByC_C_Last(long classNameId,
		long classPK, OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_Last(classNameId, classPK, orderByComparator);
	}

	/**
	* Returns the asset entry view statses before and after the current asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param statsId the primary key of the current asset entry view stats
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry view stats
	* @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	*/
	public static AssetEntryViewStats[] findByC_C_PrevAndNext(long statsId,
		long classNameId, long classPK,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException {
		return getPersistence()
				   .findByC_C_PrevAndNext(statsId, classNameId, classPK,
			orderByComparator);
	}

	/**
	* Removes all the asset entry view statses where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public static void removeByC_C(long classNameId, long classPK) {
		getPersistence().removeByC_C(classNameId, classPK);
	}

	/**
	* Returns the number of asset entry view statses where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching asset entry view statses
	*/
	public static int countByC_C(long classNameId, long classPK) {
		return getPersistence().countByC_C(classNameId, classPK);
	}

	/**
	* Returns all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @return the matching asset entry view statses
	*/
	public static List<AssetEntryViewStats> findByC_C_GtD(long classNameId,
		long classPK, Date createDate) {
		return getPersistence().findByC_C_GtD(classNameId, classPK, createDate);
	}

	/**
	* Returns a range of all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param start the lower bound of the range of asset entry view statses
	* @param end the upper bound of the range of asset entry view statses (not inclusive)
	* @return the range of matching asset entry view statses
	*/
	public static List<AssetEntryViewStats> findByC_C_GtD(long classNameId,
		long classPK, Date createDate, int start, int end) {
		return getPersistence()
				   .findByC_C_GtD(classNameId, classPK, createDate, start, end);
	}

	/**
	* Returns an ordered range of all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param start the lower bound of the range of asset entry view statses
	* @param end the upper bound of the range of asset entry view statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entry view statses
	*/
	public static List<AssetEntryViewStats> findByC_C_GtD(long classNameId,
		long classPK, Date createDate, int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return getPersistence()
				   .findByC_C_GtD(classNameId, classPK, createDate, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param start the lower bound of the range of asset entry view statses
	* @param end the upper bound of the range of asset entry view statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entry view statses
	*/
	public static List<AssetEntryViewStats> findByC_C_GtD(long classNameId,
		long classPK, Date createDate, int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C_GtD(classNameId, classPK, createDate, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry view stats
	* @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	*/
	public static AssetEntryViewStats findByC_C_GtD_First(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException {
		return getPersistence()
				   .findByC_C_GtD_First(classNameId, classPK, createDate,
			orderByComparator);
	}

	/**
	* Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	*/
	public static AssetEntryViewStats fetchByC_C_GtD_First(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_GtD_First(classNameId, classPK, createDate,
			orderByComparator);
	}

	/**
	* Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry view stats
	* @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	*/
	public static AssetEntryViewStats findByC_C_GtD_Last(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException {
		return getPersistence()
				   .findByC_C_GtD_Last(classNameId, classPK, createDate,
			orderByComparator);
	}

	/**
	* Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	*/
	public static AssetEntryViewStats fetchByC_C_GtD_Last(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_GtD_Last(classNameId, classPK, createDate,
			orderByComparator);
	}

	/**
	* Returns the asset entry view statses before and after the current asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* @param statsId the primary key of the current asset entry view stats
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry view stats
	* @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	*/
	public static AssetEntryViewStats[] findByC_C_GtD_PrevAndNext(
		long statsId, long classNameId, long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException {
		return getPersistence()
				   .findByC_C_GtD_PrevAndNext(statsId, classNameId, classPK,
			createDate, orderByComparator);
	}

	/**
	* Removes all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	*/
	public static void removeByC_C_GtD(long classNameId, long classPK,
		Date createDate) {
		getPersistence().removeByC_C_GtD(classNameId, classPK, createDate);
	}

	/**
	* Returns the number of asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @return the number of matching asset entry view statses
	*/
	public static int countByC_C_GtD(long classNameId, long classPK,
		Date createDate) {
		return getPersistence().countByC_C_GtD(classNameId, classPK, createDate);
	}

	/**
	* Returns all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @return the matching asset entry view statses
	*/
	public static List<AssetEntryViewStats> findByC_C_LeD(long classNameId,
		long classPK, Date createDate) {
		return getPersistence().findByC_C_LeD(classNameId, classPK, createDate);
	}

	/**
	* Returns a range of all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param start the lower bound of the range of asset entry view statses
	* @param end the upper bound of the range of asset entry view statses (not inclusive)
	* @return the range of matching asset entry view statses
	*/
	public static List<AssetEntryViewStats> findByC_C_LeD(long classNameId,
		long classPK, Date createDate, int start, int end) {
		return getPersistence()
				   .findByC_C_LeD(classNameId, classPK, createDate, start, end);
	}

	/**
	* Returns an ordered range of all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param start the lower bound of the range of asset entry view statses
	* @param end the upper bound of the range of asset entry view statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching asset entry view statses
	*/
	public static List<AssetEntryViewStats> findByC_C_LeD(long classNameId,
		long classPK, Date createDate, int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return getPersistence()
				   .findByC_C_LeD(classNameId, classPK, createDate, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param start the lower bound of the range of asset entry view statses
	* @param end the upper bound of the range of asset entry view statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching asset entry view statses
	*/
	public static List<AssetEntryViewStats> findByC_C_LeD(long classNameId,
		long classPK, Date createDate, int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByC_C_LeD(classNameId, classPK, createDate, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry view stats
	* @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	*/
	public static AssetEntryViewStats findByC_C_LeD_First(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException {
		return getPersistence()
				   .findByC_C_LeD_First(classNameId, classPK, createDate,
			orderByComparator);
	}

	/**
	* Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	*/
	public static AssetEntryViewStats fetchByC_C_LeD_First(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_LeD_First(classNameId, classPK, createDate,
			orderByComparator);
	}

	/**
	* Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry view stats
	* @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	*/
	public static AssetEntryViewStats findByC_C_LeD_Last(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException {
		return getPersistence()
				   .findByC_C_LeD_Last(classNameId, classPK, createDate,
			orderByComparator);
	}

	/**
	* Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	*/
	public static AssetEntryViewStats fetchByC_C_LeD_Last(long classNameId,
		long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return getPersistence()
				   .fetchByC_C_LeD_Last(classNameId, classPK, createDate,
			orderByComparator);
	}

	/**
	* Returns the asset entry view statses before and after the current asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* @param statsId the primary key of the current asset entry view stats
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next asset entry view stats
	* @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	*/
	public static AssetEntryViewStats[] findByC_C_LeD_PrevAndNext(
		long statsId, long classNameId, long classPK, Date createDate,
		OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException {
		return getPersistence()
				   .findByC_C_LeD_PrevAndNext(statsId, classNameId, classPK,
			createDate, orderByComparator);
	}

	/**
	* Removes all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	*/
	public static void removeByC_C_LeD(long classNameId, long classPK,
		Date createDate) {
		getPersistence().removeByC_C_LeD(classNameId, classPK, createDate);
	}

	/**
	* Returns the number of asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @return the number of matching asset entry view statses
	*/
	public static int countByC_C_LeD(long classNameId, long classPK,
		Date createDate) {
		return getPersistence().countByC_C_LeD(classNameId, classPK, createDate);
	}

	/**
	* Caches the asset entry view stats in the entity cache if it is enabled.
	*
	* @param assetEntryViewStats the asset entry view stats
	*/
	public static void cacheResult(AssetEntryViewStats assetEntryViewStats) {
		getPersistence().cacheResult(assetEntryViewStats);
	}

	/**
	* Caches the asset entry view statses in the entity cache if it is enabled.
	*
	* @param assetEntryViewStatses the asset entry view statses
	*/
	public static void cacheResult(
		List<AssetEntryViewStats> assetEntryViewStatses) {
		getPersistence().cacheResult(assetEntryViewStatses);
	}

	/**
	* Creates a new asset entry view stats with the primary key. Does not add the asset entry view stats to the database.
	*
	* @param statsId the primary key for the new asset entry view stats
	* @return the new asset entry view stats
	*/
	public static AssetEntryViewStats create(long statsId) {
		return getPersistence().create(statsId);
	}

	/**
	* Removes the asset entry view stats with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsId the primary key of the asset entry view stats
	* @return the asset entry view stats that was removed
	* @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	*/
	public static AssetEntryViewStats remove(long statsId)
		throws com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException {
		return getPersistence().remove(statsId);
	}

	public static AssetEntryViewStats updateImpl(
		AssetEntryViewStats assetEntryViewStats) {
		return getPersistence().updateImpl(assetEntryViewStats);
	}

	/**
	* Returns the asset entry view stats with the primary key or throws a {@link NoSuchEntryViewStatsException} if it could not be found.
	*
	* @param statsId the primary key of the asset entry view stats
	* @return the asset entry view stats
	* @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	*/
	public static AssetEntryViewStats findByPrimaryKey(long statsId)
		throws com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException {
		return getPersistence().findByPrimaryKey(statsId);
	}

	/**
	* Returns the asset entry view stats with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param statsId the primary key of the asset entry view stats
	* @return the asset entry view stats, or <code>null</code> if a asset entry view stats with the primary key could not be found
	*/
	public static AssetEntryViewStats fetchByPrimaryKey(long statsId) {
		return getPersistence().fetchByPrimaryKey(statsId);
	}

	public static java.util.Map<java.io.Serializable, AssetEntryViewStats> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the asset entry view statses.
	*
	* @return the asset entry view statses
	*/
	public static List<AssetEntryViewStats> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the asset entry view statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry view statses
	* @param end the upper bound of the range of asset entry view statses (not inclusive)
	* @return the range of asset entry view statses
	*/
	public static List<AssetEntryViewStats> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the asset entry view statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry view statses
	* @param end the upper bound of the range of asset entry view statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of asset entry view statses
	*/
	public static List<AssetEntryViewStats> findAll(int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the asset entry view statses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link AssetEntryViewStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of asset entry view statses
	* @param end the upper bound of the range of asset entry view statses (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of asset entry view statses
	*/
	public static List<AssetEntryViewStats> findAll(int start, int end,
		OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the asset entry view statses from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of asset entry view statses.
	*
	* @return the number of asset entry view statses
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AssetEntryViewStatsPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (AssetEntryViewStatsPersistence)PortalBeanLocatorUtil.locate(AssetEntryViewStatsPersistence.class.getName());

			ReferenceRegistry.registerReference(AssetEntryViewStatsUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	private static AssetEntryViewStatsPersistence _persistence;
}