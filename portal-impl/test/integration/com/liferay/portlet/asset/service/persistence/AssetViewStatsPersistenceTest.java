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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.persistence.BasePersistence;
import com.liferay.portal.service.persistence.PersistenceExecutionTestListener;
import com.liferay.portal.test.LiferayPersistenceIntegrationJUnitTestRunner;
import com.liferay.portal.test.persistence.TransactionalPersistenceAdvice;

import com.liferay.portlet.asset.NoSuchViewStatsException;
import com.liferay.portlet.asset.model.AssetViewStats;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
@ExecutionTestListeners(listeners =  {
	PersistenceExecutionTestListener.class})
@RunWith(LiferayPersistenceIntegrationJUnitTestRunner.class)
public class AssetViewStatsPersistenceTest {
	@After
	public void tearDown() throws Exception {
		Map<Serializable, BasePersistence<?>> basePersistences = _transactionalPersistenceAdvice.getBasePersistences();

		Set<Serializable> primaryKeys = basePersistences.keySet();

		for (Serializable primaryKey : primaryKeys) {
			BasePersistence<?> basePersistence = basePersistences.get(primaryKey);

			try {
				basePersistence.remove(primaryKey);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug("The model with primary key " + primaryKey +
						" was already deleted");
				}
			}
		}

		_transactionalPersistenceAdvice.reset();
	}

	@Test
	public void testCreate() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetViewStats assetViewStats = _persistence.create(pk);

		Assert.assertNotNull(assetViewStats);

		Assert.assertEquals(assetViewStats.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetViewStats newAssetViewStats = addAssetViewStats();

		_persistence.remove(newAssetViewStats);

		AssetViewStats existingAssetViewStats = _persistence.fetchByPrimaryKey(newAssetViewStats.getPrimaryKey());

		Assert.assertNull(existingAssetViewStats);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetViewStats();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetViewStats newAssetViewStats = _persistence.create(pk);

		newAssetViewStats.setUserId(ServiceTestUtil.nextLong());

		newAssetViewStats.setViewDate(ServiceTestUtil.nextDate());

		newAssetViewStats.setClassNameId(ServiceTestUtil.nextLong());

		newAssetViewStats.setClassPK(ServiceTestUtil.nextLong());

		_persistence.update(newAssetViewStats);

		AssetViewStats existingAssetViewStats = _persistence.findByPrimaryKey(newAssetViewStats.getPrimaryKey());

		Assert.assertEquals(existingAssetViewStats.getViewStatsId(),
			newAssetViewStats.getViewStatsId());
		Assert.assertEquals(existingAssetViewStats.getUserId(),
			newAssetViewStats.getUserId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetViewStats.getViewDate()),
			Time.getShortTimestamp(newAssetViewStats.getViewDate()));
		Assert.assertEquals(existingAssetViewStats.getClassNameId(),
			newAssetViewStats.getClassNameId());
		Assert.assertEquals(existingAssetViewStats.getClassPK(),
			newAssetViewStats.getClassPK());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetViewStats newAssetViewStats = addAssetViewStats();

		AssetViewStats existingAssetViewStats = _persistence.findByPrimaryKey(newAssetViewStats.getPrimaryKey());

		Assert.assertEquals(existingAssetViewStats, newAssetViewStats);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchViewStatsException");
		}
		catch (NoSuchViewStatsException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetViewStats",
			"viewStatsId", true, "userId", true, "viewDate", true,
			"classNameId", true, "classPK", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetViewStats newAssetViewStats = addAssetViewStats();

		AssetViewStats existingAssetViewStats = _persistence.fetchByPrimaryKey(newAssetViewStats.getPrimaryKey());

		Assert.assertEquals(existingAssetViewStats, newAssetViewStats);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetViewStats missingAssetViewStats = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetViewStats);
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = new AssetViewStatsActionableDynamicQuery() {
				@Override
				protected void performAction(Object object) {
					AssetViewStats assetViewStats = (AssetViewStats)object;

					Assert.assertNotNull(assetViewStats);

					count.increment();
				}
			};

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetViewStats newAssetViewStats = addAssetViewStats();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetViewStats.class,
				AssetViewStats.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("viewStatsId",
				newAssetViewStats.getViewStatsId()));

		List<AssetViewStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetViewStats existingAssetViewStats = result.get(0);

		Assert.assertEquals(existingAssetViewStats, newAssetViewStats);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetViewStats.class,
				AssetViewStats.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("viewStatsId",
				ServiceTestUtil.nextLong()));

		List<AssetViewStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetViewStats newAssetViewStats = addAssetViewStats();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetViewStats.class,
				AssetViewStats.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("viewStatsId"));

		Object newViewStatsId = newAssetViewStats.getViewStatsId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("viewStatsId",
				new Object[] { newViewStatsId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingViewStatsId = result.get(0);

		Assert.assertEquals(existingViewStatsId, newViewStatsId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetViewStats.class,
				AssetViewStats.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("viewStatsId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("viewStatsId",
				new Object[] { ServiceTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected AssetViewStats addAssetViewStats() throws Exception {
		long pk = ServiceTestUtil.nextLong();

		AssetViewStats assetViewStats = _persistence.create(pk);

		assetViewStats.setUserId(ServiceTestUtil.nextLong());

		assetViewStats.setViewDate(ServiceTestUtil.nextDate());

		assetViewStats.setClassNameId(ServiceTestUtil.nextLong());

		assetViewStats.setClassPK(ServiceTestUtil.nextLong());

		_persistence.update(assetViewStats);

		return assetViewStats;
	}

	private static Log _log = LogFactoryUtil.getLog(AssetViewStatsPersistenceTest.class);
	private AssetViewStatsPersistence _persistence = (AssetViewStatsPersistence)PortalBeanLocatorUtil.locate(AssetViewStatsPersistence.class.getName());
	private TransactionalPersistenceAdvice _transactionalPersistenceAdvice = (TransactionalPersistenceAdvice)PortalBeanLocatorUtil.locate(TransactionalPersistenceAdvice.class.getName());
}