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

package com.liferay.portlet.asset.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.service.persistence.UserFinder;
import com.liferay.portal.service.persistence.UserPersistence;

import com.liferay.portlet.asset.model.AssetTagStats;
import com.liferay.portlet.asset.service.AssetTagStatsLocalService;
import com.liferay.portlet.asset.service.persistence.AssetCategoryFinder;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyFinder;
import com.liferay.portlet.asset.service.persistence.AssetCategoryPropertyPersistence;
import com.liferay.portlet.asset.service.persistence.AssetEntryFinder;
import com.liferay.portlet.asset.service.persistence.AssetEntryPersistence;
import com.liferay.portlet.asset.service.persistence.AssetLinkPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagFinder;
import com.liferay.portlet.asset.service.persistence.AssetTagPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagPropertyFinder;
import com.liferay.portlet.asset.service.persistence.AssetTagPropertyKeyFinder;
import com.liferay.portlet.asset.service.persistence.AssetTagPropertyPersistence;
import com.liferay.portlet.asset.service.persistence.AssetTagStatsPersistence;
import com.liferay.portlet.asset.service.persistence.AssetViewStatsFinder;
import com.liferay.portlet.asset.service.persistence.AssetViewStatsPersistence;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyFinder;
import com.liferay.portlet.asset.service.persistence.AssetVocabularyPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the asset tag stats local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portlet.asset.service.impl.AssetTagStatsLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.asset.service.impl.AssetTagStatsLocalServiceImpl
 * @see com.liferay.portlet.asset.service.AssetTagStatsLocalServiceUtil
 * @generated
 */
public abstract class AssetTagStatsLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements AssetTagStatsLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.portlet.asset.service.AssetTagStatsLocalServiceUtil} to access the asset tag stats local service.
	 */

	/**
	 * Adds the asset tag stats to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetTagStats the asset tag stats
	 * @return the asset tag stats that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetTagStats addAssetTagStats(AssetTagStats assetTagStats)
		throws SystemException {
		assetTagStats.setNew(true);

		return assetTagStatsPersistence.update(assetTagStats);
	}

	/**
	 * Creates a new asset tag stats with the primary key. Does not add the asset tag stats to the database.
	 *
	 * @param tagStatsId the primary key for the new asset tag stats
	 * @return the new asset tag stats
	 */
	@Override
	public AssetTagStats createAssetTagStats(long tagStatsId) {
		return assetTagStatsPersistence.create(tagStatsId);
	}

	/**
	 * Deletes the asset tag stats with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param tagStatsId the primary key of the asset tag stats
	 * @return the asset tag stats that was removed
	 * @throws PortalException if a asset tag stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AssetTagStats deleteAssetTagStats(long tagStatsId)
		throws PortalException, SystemException {
		return assetTagStatsPersistence.remove(tagStatsId);
	}

	/**
	 * Deletes the asset tag stats from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetTagStats the asset tag stats
	 * @return the asset tag stats that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AssetTagStats deleteAssetTagStats(AssetTagStats assetTagStats)
		throws SystemException {
		return assetTagStatsPersistence.remove(assetTagStats);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(AssetTagStats.class,
			clazz.getClassLoader());
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
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return assetTagStatsPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return assetTagStatsPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return assetTagStatsPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return assetTagStatsPersistence.countWithDynamicQuery(dynamicQuery);
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
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) throws SystemException {
		return assetTagStatsPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public AssetTagStats fetchAssetTagStats(long tagStatsId)
		throws SystemException {
		return assetTagStatsPersistence.fetchByPrimaryKey(tagStatsId);
	}

	/**
	 * Returns the asset tag stats with the primary key.
	 *
	 * @param tagStatsId the primary key of the asset tag stats
	 * @return the asset tag stats
	 * @throws PortalException if a asset tag stats with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public AssetTagStats getAssetTagStats(long tagStatsId)
		throws PortalException, SystemException {
		return assetTagStatsPersistence.findByPrimaryKey(tagStatsId);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return assetTagStatsPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the asset tag statses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.asset.model.impl.AssetTagStatsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of asset tag statses
	 * @param end the upper bound of the range of asset tag statses (not inclusive)
	 * @return the range of asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<AssetTagStats> getAssetTagStatses(int start, int end)
		throws SystemException {
		return assetTagStatsPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of asset tag statses.
	 *
	 * @return the number of asset tag statses
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getAssetTagStatsesCount() throws SystemException {
		return assetTagStatsPersistence.countAll();
	}

	/**
	 * Updates the asset tag stats in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetTagStats the asset tag stats
	 * @return the asset tag stats that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetTagStats updateAssetTagStats(AssetTagStats assetTagStats)
		throws SystemException {
		return assetTagStatsPersistence.update(assetTagStats);
	}

	/**
	 * Returns the asset category local service.
	 *
	 * @return the asset category local service
	 */
	public com.liferay.portlet.asset.service.AssetCategoryLocalService getAssetCategoryLocalService() {
		return assetCategoryLocalService;
	}

	/**
	 * Sets the asset category local service.
	 *
	 * @param assetCategoryLocalService the asset category local service
	 */
	public void setAssetCategoryLocalService(
		com.liferay.portlet.asset.service.AssetCategoryLocalService assetCategoryLocalService) {
		this.assetCategoryLocalService = assetCategoryLocalService;
	}

	/**
	 * Returns the asset category remote service.
	 *
	 * @return the asset category remote service
	 */
	public com.liferay.portlet.asset.service.AssetCategoryService getAssetCategoryService() {
		return assetCategoryService;
	}

	/**
	 * Sets the asset category remote service.
	 *
	 * @param assetCategoryService the asset category remote service
	 */
	public void setAssetCategoryService(
		com.liferay.portlet.asset.service.AssetCategoryService assetCategoryService) {
		this.assetCategoryService = assetCategoryService;
	}

	/**
	 * Returns the asset category persistence.
	 *
	 * @return the asset category persistence
	 */
	public AssetCategoryPersistence getAssetCategoryPersistence() {
		return assetCategoryPersistence;
	}

	/**
	 * Sets the asset category persistence.
	 *
	 * @param assetCategoryPersistence the asset category persistence
	 */
	public void setAssetCategoryPersistence(
		AssetCategoryPersistence assetCategoryPersistence) {
		this.assetCategoryPersistence = assetCategoryPersistence;
	}

	/**
	 * Returns the asset category finder.
	 *
	 * @return the asset category finder
	 */
	public AssetCategoryFinder getAssetCategoryFinder() {
		return assetCategoryFinder;
	}

	/**
	 * Sets the asset category finder.
	 *
	 * @param assetCategoryFinder the asset category finder
	 */
	public void setAssetCategoryFinder(AssetCategoryFinder assetCategoryFinder) {
		this.assetCategoryFinder = assetCategoryFinder;
	}

	/**
	 * Returns the asset category property local service.
	 *
	 * @return the asset category property local service
	 */
	public com.liferay.portlet.asset.service.AssetCategoryPropertyLocalService getAssetCategoryPropertyLocalService() {
		return assetCategoryPropertyLocalService;
	}

	/**
	 * Sets the asset category property local service.
	 *
	 * @param assetCategoryPropertyLocalService the asset category property local service
	 */
	public void setAssetCategoryPropertyLocalService(
		com.liferay.portlet.asset.service.AssetCategoryPropertyLocalService assetCategoryPropertyLocalService) {
		this.assetCategoryPropertyLocalService = assetCategoryPropertyLocalService;
	}

	/**
	 * Returns the asset category property remote service.
	 *
	 * @return the asset category property remote service
	 */
	public com.liferay.portlet.asset.service.AssetCategoryPropertyService getAssetCategoryPropertyService() {
		return assetCategoryPropertyService;
	}

	/**
	 * Sets the asset category property remote service.
	 *
	 * @param assetCategoryPropertyService the asset category property remote service
	 */
	public void setAssetCategoryPropertyService(
		com.liferay.portlet.asset.service.AssetCategoryPropertyService assetCategoryPropertyService) {
		this.assetCategoryPropertyService = assetCategoryPropertyService;
	}

	/**
	 * Returns the asset category property persistence.
	 *
	 * @return the asset category property persistence
	 */
	public AssetCategoryPropertyPersistence getAssetCategoryPropertyPersistence() {
		return assetCategoryPropertyPersistence;
	}

	/**
	 * Sets the asset category property persistence.
	 *
	 * @param assetCategoryPropertyPersistence the asset category property persistence
	 */
	public void setAssetCategoryPropertyPersistence(
		AssetCategoryPropertyPersistence assetCategoryPropertyPersistence) {
		this.assetCategoryPropertyPersistence = assetCategoryPropertyPersistence;
	}

	/**
	 * Returns the asset category property finder.
	 *
	 * @return the asset category property finder
	 */
	public AssetCategoryPropertyFinder getAssetCategoryPropertyFinder() {
		return assetCategoryPropertyFinder;
	}

	/**
	 * Sets the asset category property finder.
	 *
	 * @param assetCategoryPropertyFinder the asset category property finder
	 */
	public void setAssetCategoryPropertyFinder(
		AssetCategoryPropertyFinder assetCategoryPropertyFinder) {
		this.assetCategoryPropertyFinder = assetCategoryPropertyFinder;
	}

	/**
	 * Returns the asset entry local service.
	 *
	 * @return the asset entry local service
	 */
	public com.liferay.portlet.asset.service.AssetEntryLocalService getAssetEntryLocalService() {
		return assetEntryLocalService;
	}

	/**
	 * Sets the asset entry local service.
	 *
	 * @param assetEntryLocalService the asset entry local service
	 */
	public void setAssetEntryLocalService(
		com.liferay.portlet.asset.service.AssetEntryLocalService assetEntryLocalService) {
		this.assetEntryLocalService = assetEntryLocalService;
	}

	/**
	 * Returns the asset entry remote service.
	 *
	 * @return the asset entry remote service
	 */
	public com.liferay.portlet.asset.service.AssetEntryService getAssetEntryService() {
		return assetEntryService;
	}

	/**
	 * Sets the asset entry remote service.
	 *
	 * @param assetEntryService the asset entry remote service
	 */
	public void setAssetEntryService(
		com.liferay.portlet.asset.service.AssetEntryService assetEntryService) {
		this.assetEntryService = assetEntryService;
	}

	/**
	 * Returns the asset entry persistence.
	 *
	 * @return the asset entry persistence
	 */
	public AssetEntryPersistence getAssetEntryPersistence() {
		return assetEntryPersistence;
	}

	/**
	 * Sets the asset entry persistence.
	 *
	 * @param assetEntryPersistence the asset entry persistence
	 */
	public void setAssetEntryPersistence(
		AssetEntryPersistence assetEntryPersistence) {
		this.assetEntryPersistence = assetEntryPersistence;
	}

	/**
	 * Returns the asset entry finder.
	 *
	 * @return the asset entry finder
	 */
	public AssetEntryFinder getAssetEntryFinder() {
		return assetEntryFinder;
	}

	/**
	 * Sets the asset entry finder.
	 *
	 * @param assetEntryFinder the asset entry finder
	 */
	public void setAssetEntryFinder(AssetEntryFinder assetEntryFinder) {
		this.assetEntryFinder = assetEntryFinder;
	}

	/**
	 * Returns the asset link local service.
	 *
	 * @return the asset link local service
	 */
	public com.liferay.portlet.asset.service.AssetLinkLocalService getAssetLinkLocalService() {
		return assetLinkLocalService;
	}

	/**
	 * Sets the asset link local service.
	 *
	 * @param assetLinkLocalService the asset link local service
	 */
	public void setAssetLinkLocalService(
		com.liferay.portlet.asset.service.AssetLinkLocalService assetLinkLocalService) {
		this.assetLinkLocalService = assetLinkLocalService;
	}

	/**
	 * Returns the asset link persistence.
	 *
	 * @return the asset link persistence
	 */
	public AssetLinkPersistence getAssetLinkPersistence() {
		return assetLinkPersistence;
	}

	/**
	 * Sets the asset link persistence.
	 *
	 * @param assetLinkPersistence the asset link persistence
	 */
	public void setAssetLinkPersistence(
		AssetLinkPersistence assetLinkPersistence) {
		this.assetLinkPersistence = assetLinkPersistence;
	}

	/**
	 * Returns the asset tag local service.
	 *
	 * @return the asset tag local service
	 */
	public com.liferay.portlet.asset.service.AssetTagLocalService getAssetTagLocalService() {
		return assetTagLocalService;
	}

	/**
	 * Sets the asset tag local service.
	 *
	 * @param assetTagLocalService the asset tag local service
	 */
	public void setAssetTagLocalService(
		com.liferay.portlet.asset.service.AssetTagLocalService assetTagLocalService) {
		this.assetTagLocalService = assetTagLocalService;
	}

	/**
	 * Returns the asset tag remote service.
	 *
	 * @return the asset tag remote service
	 */
	public com.liferay.portlet.asset.service.AssetTagService getAssetTagService() {
		return assetTagService;
	}

	/**
	 * Sets the asset tag remote service.
	 *
	 * @param assetTagService the asset tag remote service
	 */
	public void setAssetTagService(
		com.liferay.portlet.asset.service.AssetTagService assetTagService) {
		this.assetTagService = assetTagService;
	}

	/**
	 * Returns the asset tag persistence.
	 *
	 * @return the asset tag persistence
	 */
	public AssetTagPersistence getAssetTagPersistence() {
		return assetTagPersistence;
	}

	/**
	 * Sets the asset tag persistence.
	 *
	 * @param assetTagPersistence the asset tag persistence
	 */
	public void setAssetTagPersistence(AssetTagPersistence assetTagPersistence) {
		this.assetTagPersistence = assetTagPersistence;
	}

	/**
	 * Returns the asset tag finder.
	 *
	 * @return the asset tag finder
	 */
	public AssetTagFinder getAssetTagFinder() {
		return assetTagFinder;
	}

	/**
	 * Sets the asset tag finder.
	 *
	 * @param assetTagFinder the asset tag finder
	 */
	public void setAssetTagFinder(AssetTagFinder assetTagFinder) {
		this.assetTagFinder = assetTagFinder;
	}

	/**
	 * Returns the asset tag property local service.
	 *
	 * @return the asset tag property local service
	 */
	public com.liferay.portlet.asset.service.AssetTagPropertyLocalService getAssetTagPropertyLocalService() {
		return assetTagPropertyLocalService;
	}

	/**
	 * Sets the asset tag property local service.
	 *
	 * @param assetTagPropertyLocalService the asset tag property local service
	 */
	public void setAssetTagPropertyLocalService(
		com.liferay.portlet.asset.service.AssetTagPropertyLocalService assetTagPropertyLocalService) {
		this.assetTagPropertyLocalService = assetTagPropertyLocalService;
	}

	/**
	 * Returns the asset tag property remote service.
	 *
	 * @return the asset tag property remote service
	 */
	public com.liferay.portlet.asset.service.AssetTagPropertyService getAssetTagPropertyService() {
		return assetTagPropertyService;
	}

	/**
	 * Sets the asset tag property remote service.
	 *
	 * @param assetTagPropertyService the asset tag property remote service
	 */
	public void setAssetTagPropertyService(
		com.liferay.portlet.asset.service.AssetTagPropertyService assetTagPropertyService) {
		this.assetTagPropertyService = assetTagPropertyService;
	}

	/**
	 * Returns the asset tag property persistence.
	 *
	 * @return the asset tag property persistence
	 */
	public AssetTagPropertyPersistence getAssetTagPropertyPersistence() {
		return assetTagPropertyPersistence;
	}

	/**
	 * Sets the asset tag property persistence.
	 *
	 * @param assetTagPropertyPersistence the asset tag property persistence
	 */
	public void setAssetTagPropertyPersistence(
		AssetTagPropertyPersistence assetTagPropertyPersistence) {
		this.assetTagPropertyPersistence = assetTagPropertyPersistence;
	}

	/**
	 * Returns the asset tag property finder.
	 *
	 * @return the asset tag property finder
	 */
	public AssetTagPropertyFinder getAssetTagPropertyFinder() {
		return assetTagPropertyFinder;
	}

	/**
	 * Sets the asset tag property finder.
	 *
	 * @param assetTagPropertyFinder the asset tag property finder
	 */
	public void setAssetTagPropertyFinder(
		AssetTagPropertyFinder assetTagPropertyFinder) {
		this.assetTagPropertyFinder = assetTagPropertyFinder;
	}

	/**
	 * Returns the asset tag property key finder.
	 *
	 * @return the asset tag property key finder
	 */
	public AssetTagPropertyKeyFinder getAssetTagPropertyKeyFinder() {
		return assetTagPropertyKeyFinder;
	}

	/**
	 * Sets the asset tag property key finder.
	 *
	 * @param assetTagPropertyKeyFinder the asset tag property key finder
	 */
	public void setAssetTagPropertyKeyFinder(
		AssetTagPropertyKeyFinder assetTagPropertyKeyFinder) {
		this.assetTagPropertyKeyFinder = assetTagPropertyKeyFinder;
	}

	/**
	 * Returns the asset tag stats local service.
	 *
	 * @return the asset tag stats local service
	 */
	public com.liferay.portlet.asset.service.AssetTagStatsLocalService getAssetTagStatsLocalService() {
		return assetTagStatsLocalService;
	}

	/**
	 * Sets the asset tag stats local service.
	 *
	 * @param assetTagStatsLocalService the asset tag stats local service
	 */
	public void setAssetTagStatsLocalService(
		com.liferay.portlet.asset.service.AssetTagStatsLocalService assetTagStatsLocalService) {
		this.assetTagStatsLocalService = assetTagStatsLocalService;
	}

	/**
	 * Returns the asset tag stats persistence.
	 *
	 * @return the asset tag stats persistence
	 */
	public AssetTagStatsPersistence getAssetTagStatsPersistence() {
		return assetTagStatsPersistence;
	}

	/**
	 * Sets the asset tag stats persistence.
	 *
	 * @param assetTagStatsPersistence the asset tag stats persistence
	 */
	public void setAssetTagStatsPersistence(
		AssetTagStatsPersistence assetTagStatsPersistence) {
		this.assetTagStatsPersistence = assetTagStatsPersistence;
	}

	/**
	 * Returns the asset view stats local service.
	 *
	 * @return the asset view stats local service
	 */
	public com.liferay.portlet.asset.service.AssetViewStatsLocalService getAssetViewStatsLocalService() {
		return assetViewStatsLocalService;
	}

	/**
	 * Sets the asset view stats local service.
	 *
	 * @param assetViewStatsLocalService the asset view stats local service
	 */
	public void setAssetViewStatsLocalService(
		com.liferay.portlet.asset.service.AssetViewStatsLocalService assetViewStatsLocalService) {
		this.assetViewStatsLocalService = assetViewStatsLocalService;
	}

	/**
	 * Returns the asset view stats persistence.
	 *
	 * @return the asset view stats persistence
	 */
	public AssetViewStatsPersistence getAssetViewStatsPersistence() {
		return assetViewStatsPersistence;
	}

	/**
	 * Sets the asset view stats persistence.
	 *
	 * @param assetViewStatsPersistence the asset view stats persistence
	 */
	public void setAssetViewStatsPersistence(
		AssetViewStatsPersistence assetViewStatsPersistence) {
		this.assetViewStatsPersistence = assetViewStatsPersistence;
	}

	/**
	 * Returns the asset view stats finder.
	 *
	 * @return the asset view stats finder
	 */
	public AssetViewStatsFinder getAssetViewStatsFinder() {
		return assetViewStatsFinder;
	}

	/**
	 * Sets the asset view stats finder.
	 *
	 * @param assetViewStatsFinder the asset view stats finder
	 */
	public void setAssetViewStatsFinder(
		AssetViewStatsFinder assetViewStatsFinder) {
		this.assetViewStatsFinder = assetViewStatsFinder;
	}

	/**
	 * Returns the asset vocabulary local service.
	 *
	 * @return the asset vocabulary local service
	 */
	public com.liferay.portlet.asset.service.AssetVocabularyLocalService getAssetVocabularyLocalService() {
		return assetVocabularyLocalService;
	}

	/**
	 * Sets the asset vocabulary local service.
	 *
	 * @param assetVocabularyLocalService the asset vocabulary local service
	 */
	public void setAssetVocabularyLocalService(
		com.liferay.portlet.asset.service.AssetVocabularyLocalService assetVocabularyLocalService) {
		this.assetVocabularyLocalService = assetVocabularyLocalService;
	}

	/**
	 * Returns the asset vocabulary remote service.
	 *
	 * @return the asset vocabulary remote service
	 */
	public com.liferay.portlet.asset.service.AssetVocabularyService getAssetVocabularyService() {
		return assetVocabularyService;
	}

	/**
	 * Sets the asset vocabulary remote service.
	 *
	 * @param assetVocabularyService the asset vocabulary remote service
	 */
	public void setAssetVocabularyService(
		com.liferay.portlet.asset.service.AssetVocabularyService assetVocabularyService) {
		this.assetVocabularyService = assetVocabularyService;
	}

	/**
	 * Returns the asset vocabulary persistence.
	 *
	 * @return the asset vocabulary persistence
	 */
	public AssetVocabularyPersistence getAssetVocabularyPersistence() {
		return assetVocabularyPersistence;
	}

	/**
	 * Sets the asset vocabulary persistence.
	 *
	 * @param assetVocabularyPersistence the asset vocabulary persistence
	 */
	public void setAssetVocabularyPersistence(
		AssetVocabularyPersistence assetVocabularyPersistence) {
		this.assetVocabularyPersistence = assetVocabularyPersistence;
	}

	/**
	 * Returns the asset vocabulary finder.
	 *
	 * @return the asset vocabulary finder
	 */
	public AssetVocabularyFinder getAssetVocabularyFinder() {
		return assetVocabularyFinder;
	}

	/**
	 * Sets the asset vocabulary finder.
	 *
	 * @param assetVocabularyFinder the asset vocabulary finder
	 */
	public void setAssetVocabularyFinder(
		AssetVocabularyFinder assetVocabularyFinder) {
		this.assetVocabularyFinder = assetVocabularyFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	/**
	 * Returns the user finder.
	 *
	 * @return the user finder
	 */
	public UserFinder getUserFinder() {
		return userFinder;
	}

	/**
	 * Sets the user finder.
	 *
	 * @param userFinder the user finder
	 */
	public void setUserFinder(UserFinder userFinder) {
		this.userFinder = userFinder;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.portlet.asset.model.AssetTagStats",
			assetTagStatsLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portlet.asset.model.AssetTagStats");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	protected Class<?> getModelClass() {
		return AssetTagStats.class;
	}

	protected String getModelClassName() {
		return AssetTagStats.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = assetTagStatsPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.portlet.asset.service.AssetCategoryLocalService.class)
	protected com.liferay.portlet.asset.service.AssetCategoryLocalService assetCategoryLocalService;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetCategoryService.class)
	protected com.liferay.portlet.asset.service.AssetCategoryService assetCategoryService;
	@BeanReference(type = AssetCategoryPersistence.class)
	protected AssetCategoryPersistence assetCategoryPersistence;
	@BeanReference(type = AssetCategoryFinder.class)
	protected AssetCategoryFinder assetCategoryFinder;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetCategoryPropertyLocalService.class)
	protected com.liferay.portlet.asset.service.AssetCategoryPropertyLocalService assetCategoryPropertyLocalService;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetCategoryPropertyService.class)
	protected com.liferay.portlet.asset.service.AssetCategoryPropertyService assetCategoryPropertyService;
	@BeanReference(type = AssetCategoryPropertyPersistence.class)
	protected AssetCategoryPropertyPersistence assetCategoryPropertyPersistence;
	@BeanReference(type = AssetCategoryPropertyFinder.class)
	protected AssetCategoryPropertyFinder assetCategoryPropertyFinder;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetEntryLocalService.class)
	protected com.liferay.portlet.asset.service.AssetEntryLocalService assetEntryLocalService;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetEntryService.class)
	protected com.liferay.portlet.asset.service.AssetEntryService assetEntryService;
	@BeanReference(type = AssetEntryPersistence.class)
	protected AssetEntryPersistence assetEntryPersistence;
	@BeanReference(type = AssetEntryFinder.class)
	protected AssetEntryFinder assetEntryFinder;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetLinkLocalService.class)
	protected com.liferay.portlet.asset.service.AssetLinkLocalService assetLinkLocalService;
	@BeanReference(type = AssetLinkPersistence.class)
	protected AssetLinkPersistence assetLinkPersistence;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetTagLocalService.class)
	protected com.liferay.portlet.asset.service.AssetTagLocalService assetTagLocalService;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetTagService.class)
	protected com.liferay.portlet.asset.service.AssetTagService assetTagService;
	@BeanReference(type = AssetTagPersistence.class)
	protected AssetTagPersistence assetTagPersistence;
	@BeanReference(type = AssetTagFinder.class)
	protected AssetTagFinder assetTagFinder;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetTagPropertyLocalService.class)
	protected com.liferay.portlet.asset.service.AssetTagPropertyLocalService assetTagPropertyLocalService;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetTagPropertyService.class)
	protected com.liferay.portlet.asset.service.AssetTagPropertyService assetTagPropertyService;
	@BeanReference(type = AssetTagPropertyPersistence.class)
	protected AssetTagPropertyPersistence assetTagPropertyPersistence;
	@BeanReference(type = AssetTagPropertyFinder.class)
	protected AssetTagPropertyFinder assetTagPropertyFinder;
	@BeanReference(type = AssetTagPropertyKeyFinder.class)
	protected AssetTagPropertyKeyFinder assetTagPropertyKeyFinder;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetTagStatsLocalService.class)
	protected com.liferay.portlet.asset.service.AssetTagStatsLocalService assetTagStatsLocalService;
	@BeanReference(type = AssetTagStatsPersistence.class)
	protected AssetTagStatsPersistence assetTagStatsPersistence;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetViewStatsLocalService.class)
	protected com.liferay.portlet.asset.service.AssetViewStatsLocalService assetViewStatsLocalService;
	@BeanReference(type = AssetViewStatsPersistence.class)
	protected AssetViewStatsPersistence assetViewStatsPersistence;
	@BeanReference(type = AssetViewStatsFinder.class)
	protected AssetViewStatsFinder assetViewStatsFinder;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetVocabularyLocalService.class)
	protected com.liferay.portlet.asset.service.AssetVocabularyLocalService assetVocabularyLocalService;
	@BeanReference(type = com.liferay.portlet.asset.service.AssetVocabularyService.class)
	protected com.liferay.portlet.asset.service.AssetVocabularyService assetVocabularyService;
	@BeanReference(type = AssetVocabularyPersistence.class)
	protected AssetVocabularyPersistence assetVocabularyPersistence;
	@BeanReference(type = AssetVocabularyFinder.class)
	protected AssetVocabularyFinder assetVocabularyFinder;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@BeanReference(type = UserFinder.class)
	protected UserFinder userFinder;
	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
	private String _beanIdentifier;
}