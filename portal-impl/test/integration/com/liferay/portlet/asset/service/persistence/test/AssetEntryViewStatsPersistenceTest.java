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

package com.liferay.portlet.asset.service.persistence.test;

import com.liferay.asset.kernel.exception.NoSuchEntryViewStatsException;
import com.liferay.asset.kernel.model.AssetEntryViewStats;
import com.liferay.asset.kernel.service.AssetEntryViewStatsLocalServiceUtil;
import com.liferay.asset.kernel.service.persistence.AssetEntryViewStatsPersistence;
import com.liferay.asset.kernel.service.persistence.AssetEntryViewStatsUtil;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PersistenceTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
public class AssetEntryViewStatsPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED));

	@Before
	public void setUp() {
		_persistence = AssetEntryViewStatsUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<AssetEntryViewStats> iterator = _assetEntryViewStatses.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryViewStats assetEntryViewStats = _persistence.create(pk);

		Assert.assertNotNull(assetEntryViewStats);

		Assert.assertEquals(assetEntryViewStats.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		AssetEntryViewStats newAssetEntryViewStats = addAssetEntryViewStats();

		_persistence.remove(newAssetEntryViewStats);

		AssetEntryViewStats existingAssetEntryViewStats = _persistence.fetchByPrimaryKey(newAssetEntryViewStats.getPrimaryKey());

		Assert.assertNull(existingAssetEntryViewStats);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addAssetEntryViewStats();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryViewStats newAssetEntryViewStats = _persistence.create(pk);

		newAssetEntryViewStats.setGroupId(RandomTestUtil.nextLong());

		newAssetEntryViewStats.setCompanyId(RandomTestUtil.nextLong());

		newAssetEntryViewStats.setUserId(RandomTestUtil.nextLong());

		newAssetEntryViewStats.setCreateDate(RandomTestUtil.nextDate());

		newAssetEntryViewStats.setClassNameId(RandomTestUtil.nextLong());

		newAssetEntryViewStats.setClassPK(RandomTestUtil.nextLong());

		newAssetEntryViewStats.setAssetEntryId(RandomTestUtil.nextLong());

		_assetEntryViewStatses.add(_persistence.update(newAssetEntryViewStats));

		AssetEntryViewStats existingAssetEntryViewStats = _persistence.findByPrimaryKey(newAssetEntryViewStats.getPrimaryKey());

		Assert.assertEquals(existingAssetEntryViewStats.getStatsId(),
			newAssetEntryViewStats.getStatsId());
		Assert.assertEquals(existingAssetEntryViewStats.getGroupId(),
			newAssetEntryViewStats.getGroupId());
		Assert.assertEquals(existingAssetEntryViewStats.getCompanyId(),
			newAssetEntryViewStats.getCompanyId());
		Assert.assertEquals(existingAssetEntryViewStats.getUserId(),
			newAssetEntryViewStats.getUserId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingAssetEntryViewStats.getCreateDate()),
			Time.getShortTimestamp(newAssetEntryViewStats.getCreateDate()));
		Assert.assertEquals(existingAssetEntryViewStats.getClassNameId(),
			newAssetEntryViewStats.getClassNameId());
		Assert.assertEquals(existingAssetEntryViewStats.getClassPK(),
			newAssetEntryViewStats.getClassPK());
		Assert.assertEquals(existingAssetEntryViewStats.getAssetEntryId(),
			newAssetEntryViewStats.getAssetEntryId());
	}

	@Test
	public void testCountByC_C() throws Exception {
		_persistence.countByC_C(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong());

		_persistence.countByC_C(0L, 0L);
	}

	@Test
	public void testCountByC_C_GtD() throws Exception {
		_persistence.countByC_C_GtD(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextDate());

		_persistence.countByC_C_GtD(0L, 0L, RandomTestUtil.nextDate());
	}

	@Test
	public void testCountByC_C_LeD() throws Exception {
		_persistence.countByC_C_LeD(RandomTestUtil.nextLong(),
			RandomTestUtil.nextLong(), RandomTestUtil.nextDate());

		_persistence.countByC_C_LeD(0L, 0L, RandomTestUtil.nextDate());
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetEntryViewStats newAssetEntryViewStats = addAssetEntryViewStats();

		AssetEntryViewStats existingAssetEntryViewStats = _persistence.findByPrimaryKey(newAssetEntryViewStats.getPrimaryKey());

		Assert.assertEquals(existingAssetEntryViewStats, newAssetEntryViewStats);
	}

	@Test(expected = NoSuchEntryViewStatsException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<AssetEntryViewStats> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("AssetEntryViewStats",
			"statsId", true, "groupId", true, "companyId", true, "userId",
			true, "createDate", true, "classNameId", true, "classPK", true,
			"assetEntryId", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetEntryViewStats newAssetEntryViewStats = addAssetEntryViewStats();

		AssetEntryViewStats existingAssetEntryViewStats = _persistence.fetchByPrimaryKey(newAssetEntryViewStats.getPrimaryKey());

		Assert.assertEquals(existingAssetEntryViewStats, newAssetEntryViewStats);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryViewStats missingAssetEntryViewStats = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingAssetEntryViewStats);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		AssetEntryViewStats newAssetEntryViewStats1 = addAssetEntryViewStats();
		AssetEntryViewStats newAssetEntryViewStats2 = addAssetEntryViewStats();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryViewStats1.getPrimaryKey());
		primaryKeys.add(newAssetEntryViewStats2.getPrimaryKey());

		Map<Serializable, AssetEntryViewStats> assetEntryViewStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, assetEntryViewStatses.size());
		Assert.assertEquals(newAssetEntryViewStats1,
			assetEntryViewStatses.get(newAssetEntryViewStats1.getPrimaryKey()));
		Assert.assertEquals(newAssetEntryViewStats2,
			assetEntryViewStatses.get(newAssetEntryViewStats2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, AssetEntryViewStats> assetEntryViewStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetEntryViewStatses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		AssetEntryViewStats newAssetEntryViewStats = addAssetEntryViewStats();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryViewStats.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, AssetEntryViewStats> assetEntryViewStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetEntryViewStatses.size());
		Assert.assertEquals(newAssetEntryViewStats,
			assetEntryViewStatses.get(newAssetEntryViewStats.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, AssetEntryViewStats> assetEntryViewStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(assetEntryViewStatses.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		AssetEntryViewStats newAssetEntryViewStats = addAssetEntryViewStats();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newAssetEntryViewStats.getPrimaryKey());

		Map<Serializable, AssetEntryViewStats> assetEntryViewStatses = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, assetEntryViewStatses.size());
		Assert.assertEquals(newAssetEntryViewStats,
			assetEntryViewStatses.get(newAssetEntryViewStats.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = AssetEntryViewStatsLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<AssetEntryViewStats>() {
				@Override
				public void performAction(
					AssetEntryViewStats assetEntryViewStats) {
					Assert.assertNotNull(assetEntryViewStats);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetEntryViewStats newAssetEntryViewStats = addAssetEntryViewStats();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntryViewStats.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsId",
				newAssetEntryViewStats.getStatsId()));

		List<AssetEntryViewStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		AssetEntryViewStats existingAssetEntryViewStats = result.get(0);

		Assert.assertEquals(existingAssetEntryViewStats, newAssetEntryViewStats);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntryViewStats.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("statsId",
				RandomTestUtil.nextLong()));

		List<AssetEntryViewStats> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		AssetEntryViewStats newAssetEntryViewStats = addAssetEntryViewStats();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntryViewStats.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsId"));

		Object newStatsId = newAssetEntryViewStats.getStatsId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsId",
				new Object[] { newStatsId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingStatsId = result.get(0);

		Assert.assertEquals(existingStatsId, newStatsId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetEntryViewStats.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("statsId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("statsId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected AssetEntryViewStats addAssetEntryViewStats()
		throws Exception {
		long pk = RandomTestUtil.nextLong();

		AssetEntryViewStats assetEntryViewStats = _persistence.create(pk);

		assetEntryViewStats.setGroupId(RandomTestUtil.nextLong());

		assetEntryViewStats.setCompanyId(RandomTestUtil.nextLong());

		assetEntryViewStats.setUserId(RandomTestUtil.nextLong());

		assetEntryViewStats.setCreateDate(RandomTestUtil.nextDate());

		assetEntryViewStats.setClassNameId(RandomTestUtil.nextLong());

		assetEntryViewStats.setClassPK(RandomTestUtil.nextLong());

		assetEntryViewStats.setAssetEntryId(RandomTestUtil.nextLong());

		_assetEntryViewStatses.add(_persistence.update(assetEntryViewStats));

		return assetEntryViewStats;
	}

	private List<AssetEntryViewStats> _assetEntryViewStatses = new ArrayList<AssetEntryViewStats>();
	private AssetEntryViewStatsPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}