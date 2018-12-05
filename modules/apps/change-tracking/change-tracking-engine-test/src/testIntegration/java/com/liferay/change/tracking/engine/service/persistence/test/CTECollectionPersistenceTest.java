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

package com.liferay.change.tracking.engine.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import com.liferay.change.tracking.engine.exception.NoSuchCTECollectionException;
import com.liferay.change.tracking.engine.model.CTECollection;
import com.liferay.change.tracking.engine.service.CTECollectionLocalServiceUtil;
import com.liferay.change.tracking.engine.service.persistence.CTECollectionPersistence;
import com.liferay.change.tracking.engine.service.persistence.CTECollectionUtil;

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

import org.junit.runner.RunWith;

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
@RunWith(Arquillian.class)
public class CTECollectionPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.change.tracking.engine.service"));

	@Before
	public void setUp() {
		_persistence = CTECollectionUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CTECollection> iterator = _cteCollections.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTECollection cteCollection = _persistence.create(pk);

		Assert.assertNotNull(cteCollection);

		Assert.assertEquals(cteCollection.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CTECollection newCTECollection = addCTECollection();

		_persistence.remove(newCTECollection);

		CTECollection existingCTECollection = _persistence.fetchByPrimaryKey(newCTECollection.getPrimaryKey());

		Assert.assertNull(existingCTECollection);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCTECollection();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTECollection newCTECollection = _persistence.create(pk);

		newCTECollection.setCompanyId(RandomTestUtil.nextLong());

		newCTECollection.setUserId(RandomTestUtil.nextLong());

		newCTECollection.setUserName(RandomTestUtil.randomString());

		newCTECollection.setCreateDate(RandomTestUtil.nextDate());

		newCTECollection.setModifiedDate(RandomTestUtil.nextDate());

		newCTECollection.setName(RandomTestUtil.randomString());

		newCTECollection.setDescription(RandomTestUtil.randomString());

		newCTECollection.setStatus(RandomTestUtil.nextInt());

		newCTECollection.setStatusByUserId(RandomTestUtil.nextLong());

		newCTECollection.setStatusByUserName(RandomTestUtil.randomString());

		newCTECollection.setStatusDate(RandomTestUtil.nextDate());

		_cteCollections.add(_persistence.update(newCTECollection));

		CTECollection existingCTECollection = _persistence.findByPrimaryKey(newCTECollection.getPrimaryKey());

		Assert.assertEquals(existingCTECollection.getCollectionId(),
			newCTECollection.getCollectionId());
		Assert.assertEquals(existingCTECollection.getCompanyId(),
			newCTECollection.getCompanyId());
		Assert.assertEquals(existingCTECollection.getUserId(),
			newCTECollection.getUserId());
		Assert.assertEquals(existingCTECollection.getUserName(),
			newCTECollection.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCTECollection.getCreateDate()),
			Time.getShortTimestamp(newCTECollection.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCTECollection.getModifiedDate()),
			Time.getShortTimestamp(newCTECollection.getModifiedDate()));
		Assert.assertEquals(existingCTECollection.getName(),
			newCTECollection.getName());
		Assert.assertEquals(existingCTECollection.getDescription(),
			newCTECollection.getDescription());
		Assert.assertEquals(existingCTECollection.getStatus(),
			newCTECollection.getStatus());
		Assert.assertEquals(existingCTECollection.getStatusByUserId(),
			newCTECollection.getStatusByUserId());
		Assert.assertEquals(existingCTECollection.getStatusByUserName(),
			newCTECollection.getStatusByUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCTECollection.getStatusDate()),
			Time.getShortTimestamp(newCTECollection.getStatusDate()));
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CTECollection newCTECollection = addCTECollection();

		CTECollection existingCTECollection = _persistence.findByPrimaryKey(newCTECollection.getPrimaryKey());

		Assert.assertEquals(existingCTECollection, newCTECollection);
	}

	@Test(expected = NoSuchCTECollectionException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CTECollection> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CTECollection",
			"collectionId", true, "companyId", true, "userId", true,
			"userName", true, "createDate", true, "modifiedDate", true, "name",
			true, "description", true, "status", true, "statusByUserId", true,
			"statusByUserName", true, "statusDate", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CTECollection newCTECollection = addCTECollection();

		CTECollection existingCTECollection = _persistence.fetchByPrimaryKey(newCTECollection.getPrimaryKey());

		Assert.assertEquals(existingCTECollection, newCTECollection);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTECollection missingCTECollection = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCTECollection);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CTECollection newCTECollection1 = addCTECollection();
		CTECollection newCTECollection2 = addCTECollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTECollection1.getPrimaryKey());
		primaryKeys.add(newCTECollection2.getPrimaryKey());

		Map<Serializable, CTECollection> cteCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cteCollections.size());
		Assert.assertEquals(newCTECollection1,
			cteCollections.get(newCTECollection1.getPrimaryKey()));
		Assert.assertEquals(newCTECollection2,
			cteCollections.get(newCTECollection2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CTECollection> cteCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cteCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CTECollection newCTECollection = addCTECollection();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTECollection.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CTECollection> cteCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cteCollections.size());
		Assert.assertEquals(newCTECollection,
			cteCollections.get(newCTECollection.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CTECollection> cteCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cteCollections.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CTECollection newCTECollection = addCTECollection();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTECollection.getPrimaryKey());

		Map<Serializable, CTECollection> cteCollections = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cteCollections.size());
		Assert.assertEquals(newCTECollection,
			cteCollections.get(newCTECollection.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CTECollectionLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CTECollection>() {
				@Override
				public void performAction(CTECollection cteCollection) {
					Assert.assertNotNull(cteCollection);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CTECollection newCTECollection = addCTECollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CTECollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("collectionId",
				newCTECollection.getCollectionId()));

		List<CTECollection> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CTECollection existingCTECollection = result.get(0);

		Assert.assertEquals(existingCTECollection, newCTECollection);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CTECollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("collectionId",
				RandomTestUtil.nextLong()));

		List<CTECollection> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CTECollection newCTECollection = addCTECollection();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CTECollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"collectionId"));

		Object newCollectionId = newCTECollection.getCollectionId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("collectionId",
				new Object[] { newCollectionId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingCollectionId = result.get(0);

		Assert.assertEquals(existingCollectionId, newCollectionId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CTECollection.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"collectionId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("collectionId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CTECollection addCTECollection() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTECollection cteCollection = _persistence.create(pk);

		cteCollection.setCompanyId(RandomTestUtil.nextLong());

		cteCollection.setUserId(RandomTestUtil.nextLong());

		cteCollection.setUserName(RandomTestUtil.randomString());

		cteCollection.setCreateDate(RandomTestUtil.nextDate());

		cteCollection.setModifiedDate(RandomTestUtil.nextDate());

		cteCollection.setName(RandomTestUtil.randomString());

		cteCollection.setDescription(RandomTestUtil.randomString());

		cteCollection.setStatus(RandomTestUtil.nextInt());

		cteCollection.setStatusByUserId(RandomTestUtil.nextLong());

		cteCollection.setStatusByUserName(RandomTestUtil.randomString());

		cteCollection.setStatusDate(RandomTestUtil.nextDate());

		_cteCollections.add(_persistence.update(cteCollection));

		return cteCollection;
	}

	private List<CTECollection> _cteCollections = new ArrayList<CTECollection>();
	private CTECollectionPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}