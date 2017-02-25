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

package com.liferay.asset.entry.view.stats.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.asset.entry.view.stats.model.AssetEntryViewStats;
import com.liferay.asset.entry.view.stats.service.AssetEntryViewStatsLocalService;
import com.liferay.asset.entry.view.stats.service.persistence.AssetEntryViewStatsPersistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the asset entry view stats local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.asset.entry.view.stats.service.impl.AssetEntryViewStatsLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.asset.entry.view.stats.service.impl.AssetEntryViewStatsLocalServiceImpl
 * @see com.liferay.asset.entry.view.stats.service.AssetEntryViewStatsLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class AssetEntryViewStatsLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements AssetEntryViewStatsLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.asset.entry.view.stats.service.AssetEntryViewStatsLocalServiceUtil} to access the asset entry view stats local service.
	 */

	/**
	 * Adds the asset entry view stats to the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryViewStats the asset entry view stats
	 * @return the asset entry view stats that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetEntryViewStats addAssetEntryViewStats(
		AssetEntryViewStats assetEntryViewStats) {
		assetEntryViewStats.setNew(true);

		return assetEntryViewStatsPersistence.update(assetEntryViewStats);
	}

	/**
	 * Creates a new asset entry view stats with the primary key. Does not add the asset entry view stats to the database.
	 *
	 * @param statsId the primary key for the new asset entry view stats
	 * @return the new asset entry view stats
	 */
	@Override
	public AssetEntryViewStats createAssetEntryViewStats(long statsId) {
		return assetEntryViewStatsPersistence.create(statsId);
	}

	/**
	 * Deletes the asset entry view stats with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param statsId the primary key of the asset entry view stats
	 * @return the asset entry view stats that was removed
	 * @throws PortalException if a asset entry view stats with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AssetEntryViewStats deleteAssetEntryViewStats(long statsId)
		throws PortalException {
		return assetEntryViewStatsPersistence.remove(statsId);
	}

	/**
	 * Deletes the asset entry view stats from the database. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryViewStats the asset entry view stats
	 * @return the asset entry view stats that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public AssetEntryViewStats deleteAssetEntryViewStats(
		AssetEntryViewStats assetEntryViewStats) {
		return assetEntryViewStatsPersistence.remove(assetEntryViewStats);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(AssetEntryViewStats.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return assetEntryViewStatsPersistence.findWithDynamicQuery(dynamicQuery);
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
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return assetEntryViewStatsPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
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
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return assetEntryViewStatsPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return assetEntryViewStatsPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return assetEntryViewStatsPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public AssetEntryViewStats fetchAssetEntryViewStats(long statsId) {
		return assetEntryViewStatsPersistence.fetchByPrimaryKey(statsId);
	}

	/**
	 * Returns the asset entry view stats with the primary key.
	 *
	 * @param statsId the primary key of the asset entry view stats
	 * @return the asset entry view stats
	 * @throws PortalException if a asset entry view stats with the primary key could not be found
	 */
	@Override
	public AssetEntryViewStats getAssetEntryViewStats(long statsId)
		throws PortalException {
		return assetEntryViewStatsPersistence.findByPrimaryKey(statsId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(assetEntryViewStatsLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(AssetEntryViewStats.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("statsId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(assetEntryViewStatsLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(AssetEntryViewStats.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("statsId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(assetEntryViewStatsLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(AssetEntryViewStats.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("statsId");
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return assetEntryViewStatsLocalService.deleteAssetEntryViewStats((AssetEntryViewStats)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return assetEntryViewStatsPersistence.findByPrimaryKey(primaryKeyObj);
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
	@Override
	public List<AssetEntryViewStats> getAssetEntryViewStatses(int start, int end) {
		return assetEntryViewStatsPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of asset entry view statses.
	 *
	 * @return the number of asset entry view statses
	 */
	@Override
	public int getAssetEntryViewStatsesCount() {
		return assetEntryViewStatsPersistence.countAll();
	}

	/**
	 * Updates the asset entry view stats in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param assetEntryViewStats the asset entry view stats
	 * @return the asset entry view stats that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public AssetEntryViewStats updateAssetEntryViewStats(
		AssetEntryViewStats assetEntryViewStats) {
		return assetEntryViewStatsPersistence.update(assetEntryViewStats);
	}

	/**
	 * Returns the asset entry view stats local service.
	 *
	 * @return the asset entry view stats local service
	 */
	public AssetEntryViewStatsLocalService getAssetEntryViewStatsLocalService() {
		return assetEntryViewStatsLocalService;
	}

	/**
	 * Sets the asset entry view stats local service.
	 *
	 * @param assetEntryViewStatsLocalService the asset entry view stats local service
	 */
	public void setAssetEntryViewStatsLocalService(
		AssetEntryViewStatsLocalService assetEntryViewStatsLocalService) {
		this.assetEntryViewStatsLocalService = assetEntryViewStatsLocalService;
	}

	/**
	 * Returns the asset entry view stats persistence.
	 *
	 * @return the asset entry view stats persistence
	 */
	public AssetEntryViewStatsPersistence getAssetEntryViewStatsPersistence() {
		return assetEntryViewStatsPersistence;
	}

	/**
	 * Sets the asset entry view stats persistence.
	 *
	 * @param assetEntryViewStatsPersistence the asset entry view stats persistence
	 */
	public void setAssetEntryViewStatsPersistence(
		AssetEntryViewStatsPersistence assetEntryViewStatsPersistence) {
		this.assetEntryViewStatsPersistence = assetEntryViewStatsPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
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

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.asset.entry.view.stats.model.AssetEntryViewStats",
			assetEntryViewStatsLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.asset.entry.view.stats.model.AssetEntryViewStats");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return AssetEntryViewStatsLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return AssetEntryViewStats.class;
	}

	protected String getModelClassName() {
		return AssetEntryViewStats.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = assetEntryViewStatsPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = AssetEntryViewStatsLocalService.class)
	protected AssetEntryViewStatsLocalService assetEntryViewStatsLocalService;
	@BeanReference(type = AssetEntryViewStatsPersistence.class)
	protected AssetEntryViewStatsPersistence assetEntryViewStatsPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}