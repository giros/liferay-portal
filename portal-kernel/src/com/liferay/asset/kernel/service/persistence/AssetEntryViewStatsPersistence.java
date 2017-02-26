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

import com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException;
import com.liferay.asset.kernel.model.AssetEntryViewStats;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

/**
 * The persistence interface for the asset entry view stats service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.asset.service.persistence.impl.AssetEntryViewStatsPersistenceImpl
 * @see AssetEntryViewStatsUtil
 * @generated
 */
@ProviderType
public interface AssetEntryViewStatsPersistence extends BasePersistence<AssetEntryViewStats> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetEntryViewStatsUtil} to access the asset entry view stats persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the asset entry view statses where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the matching asset entry view statses
	*/
	public java.util.List<AssetEntryViewStats> findByC_C(long classNameId,
		long classPK);

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
	public java.util.List<AssetEntryViewStats> findByC_C(long classNameId,
		long classPK, int start, int end);

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
	public java.util.List<AssetEntryViewStats> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator);

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
	public java.util.List<AssetEntryViewStats> findByC_C(long classNameId,
		long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry view stats
	* @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	*/
	public AssetEntryViewStats findByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException;

	/**
	* Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	*/
	public AssetEntryViewStats fetchByC_C_First(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator);

	/**
	* Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry view stats
	* @throws NoSuchEntryViewStatsException if a matching asset entry view stats could not be found
	*/
	public AssetEntryViewStats findByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException;

	/**
	* Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	*/
	public AssetEntryViewStats fetchByC_C_Last(long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator);

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
	public AssetEntryViewStats[] findByC_C_PrevAndNext(long statsId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException;

	/**
	* Removes all the asset entry view statses where classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	*/
	public void removeByC_C(long classNameId, long classPK);

	/**
	* Returns the number of asset entry view statses where classNameId = &#63; and classPK = &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @return the number of matching asset entry view statses
	*/
	public int countByC_C(long classNameId, long classPK);

	/**
	* Returns all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @return the matching asset entry view statses
	*/
	public java.util.List<AssetEntryViewStats> findByC_C_GtD(long classNameId,
		long classPK, Date createDate);

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
	public java.util.List<AssetEntryViewStats> findByC_C_GtD(long classNameId,
		long classPK, Date createDate, int start, int end);

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
	public java.util.List<AssetEntryViewStats> findByC_C_GtD(long classNameId,
		long classPK, Date createDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator);

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
	public java.util.List<AssetEntryViewStats> findByC_C_GtD(long classNameId,
		long classPK, Date createDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean retrieveFromCache);

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
	public AssetEntryViewStats findByC_C_GtD_First(long classNameId,
		long classPK, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException;

	/**
	* Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	*/
	public AssetEntryViewStats fetchByC_C_GtD_First(long classNameId,
		long classPK, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator);

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
	public AssetEntryViewStats findByC_C_GtD_Last(long classNameId,
		long classPK, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException;

	/**
	* Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	*/
	public AssetEntryViewStats fetchByC_C_GtD_Last(long classNameId,
		long classPK, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator);

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
	public AssetEntryViewStats[] findByC_C_GtD_PrevAndNext(long statsId,
		long classNameId, long classPK, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException;

	/**
	* Removes all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	*/
	public void removeByC_C_GtD(long classNameId, long classPK, Date createDate);

	/**
	* Returns the number of asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &gt; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @return the number of matching asset entry view statses
	*/
	public int countByC_C_GtD(long classNameId, long classPK, Date createDate);

	/**
	* Returns all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @return the matching asset entry view statses
	*/
	public java.util.List<AssetEntryViewStats> findByC_C_LeD(long classNameId,
		long classPK, Date createDate);

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
	public java.util.List<AssetEntryViewStats> findByC_C_LeD(long classNameId,
		long classPK, Date createDate, int start, int end);

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
	public java.util.List<AssetEntryViewStats> findByC_C_LeD(long classNameId,
		long classPK, Date createDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator);

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
	public java.util.List<AssetEntryViewStats> findByC_C_LeD(long classNameId,
		long classPK, Date createDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean retrieveFromCache);

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
	public AssetEntryViewStats findByC_C_LeD_First(long classNameId,
		long classPK, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException;

	/**
	* Returns the first asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	*/
	public AssetEntryViewStats fetchByC_C_LeD_First(long classNameId,
		long classPK, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator);

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
	public AssetEntryViewStats findByC_C_LeD_Last(long classNameId,
		long classPK, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException;

	/**
	* Returns the last asset entry view stats in the ordered set where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching asset entry view stats, or <code>null</code> if a matching asset entry view stats could not be found
	*/
	public AssetEntryViewStats fetchByC_C_LeD_Last(long classNameId,
		long classPK, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator);

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
	public AssetEntryViewStats[] findByC_C_LeD_PrevAndNext(long statsId,
		long classNameId, long classPK, Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator)
		throws NoSuchEntryViewStatsException;

	/**
	* Removes all the asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63; from the database.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	*/
	public void removeByC_C_LeD(long classNameId, long classPK, Date createDate);

	/**
	* Returns the number of asset entry view statses where classNameId = &#63; and classPK = &#63; and createDate &le; &#63;.
	*
	* @param classNameId the class name ID
	* @param classPK the class pk
	* @param createDate the create date
	* @return the number of matching asset entry view statses
	*/
	public int countByC_C_LeD(long classNameId, long classPK, Date createDate);

	/**
	* Caches the asset entry view stats in the entity cache if it is enabled.
	*
	* @param assetEntryViewStats the asset entry view stats
	*/
	public void cacheResult(AssetEntryViewStats assetEntryViewStats);

	/**
	* Caches the asset entry view statses in the entity cache if it is enabled.
	*
	* @param assetEntryViewStatses the asset entry view statses
	*/
	public void cacheResult(
		java.util.List<AssetEntryViewStats> assetEntryViewStatses);

	/**
	* Creates a new asset entry view stats with the primary key. Does not add the asset entry view stats to the database.
	*
	* @param statsId the primary key for the new asset entry view stats
	* @return the new asset entry view stats
	*/
	public AssetEntryViewStats create(long statsId);

	/**
	* Removes the asset entry view stats with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param statsId the primary key of the asset entry view stats
	* @return the asset entry view stats that was removed
	* @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	*/
	public AssetEntryViewStats remove(long statsId)
		throws NoSuchEntryViewStatsException;

	public AssetEntryViewStats updateImpl(
		AssetEntryViewStats assetEntryViewStats);

	/**
	* Returns the asset entry view stats with the primary key or throws a {@link NoSuchEntryViewStatsException} if it could not be found.
	*
	* @param statsId the primary key of the asset entry view stats
	* @return the asset entry view stats
	* @throws NoSuchEntryViewStatsException if a asset entry view stats with the primary key could not be found
	*/
	public AssetEntryViewStats findByPrimaryKey(long statsId)
		throws NoSuchEntryViewStatsException;

	/**
	* Returns the asset entry view stats with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param statsId the primary key of the asset entry view stats
	* @return the asset entry view stats, or <code>null</code> if a asset entry view stats with the primary key could not be found
	*/
	public AssetEntryViewStats fetchByPrimaryKey(long statsId);

	@Override
	public java.util.Map<java.io.Serializable, AssetEntryViewStats> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the asset entry view statses.
	*
	* @return the asset entry view statses
	*/
	public java.util.List<AssetEntryViewStats> findAll();

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
	public java.util.List<AssetEntryViewStats> findAll(int start, int end);

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
	public java.util.List<AssetEntryViewStats> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator);

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
	public java.util.List<AssetEntryViewStats> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetEntryViewStats> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the asset entry view statses from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of asset entry view statses.
	*
	* @return the number of asset entry view statses
	*/
	public int countAll();
}