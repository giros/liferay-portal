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

import com.liferay.change.tracking.engine.exception.NoSuchCTEEntryException;
import com.liferay.change.tracking.engine.model.CTEEntry;
import com.liferay.change.tracking.engine.service.CTEEntryLocalServiceUtil;
import com.liferay.change.tracking.engine.service.persistence.CTEEntryPersistence;
import com.liferay.change.tracking.engine.service.persistence.CTEEntryUtil;

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
public class CTEEntryPersistenceTest {
	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule = new AggregateTestRule(new LiferayIntegrationTestRule(),
			PersistenceTestRule.INSTANCE,
			new TransactionalTestRule(Propagation.REQUIRED,
				"com.liferay.change.tracking.engine.service"));

	@Before
	public void setUp() {
		_persistence = CTEEntryUtil.getPersistence();

		Class<?> clazz = _persistence.getClass();

		_dynamicQueryClassLoader = clazz.getClassLoader();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<CTEEntry> iterator = _cteEntries.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTEEntry cteEntry = _persistence.create(pk);

		Assert.assertNotNull(cteEntry);

		Assert.assertEquals(cteEntry.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		CTEEntry newCTEEntry = addCTEEntry();

		_persistence.remove(newCTEEntry);

		CTEEntry existingCTEEntry = _persistence.fetchByPrimaryKey(newCTEEntry.getPrimaryKey());

		Assert.assertNull(existingCTEEntry);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addCTEEntry();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTEEntry newCTEEntry = _persistence.create(pk);

		newCTEEntry.setCompanyId(RandomTestUtil.nextLong());

		newCTEEntry.setUserId(RandomTestUtil.nextLong());

		newCTEEntry.setUserName(RandomTestUtil.randomString());

		newCTEEntry.setCreateDate(RandomTestUtil.nextDate());

		newCTEEntry.setModifiedDate(RandomTestUtil.nextDate());

		newCTEEntry.setClassNameId(RandomTestUtil.nextLong());

		newCTEEntry.setClassPK(RandomTestUtil.nextLong());

		newCTEEntry.setResourcePrimKey(RandomTestUtil.nextLong());

		_cteEntries.add(_persistence.update(newCTEEntry));

		CTEEntry existingCTEEntry = _persistence.findByPrimaryKey(newCTEEntry.getPrimaryKey());

		Assert.assertEquals(existingCTEEntry.getEntryId(),
			newCTEEntry.getEntryId());
		Assert.assertEquals(existingCTEEntry.getCompanyId(),
			newCTEEntry.getCompanyId());
		Assert.assertEquals(existingCTEEntry.getUserId(),
			newCTEEntry.getUserId());
		Assert.assertEquals(existingCTEEntry.getUserName(),
			newCTEEntry.getUserName());
		Assert.assertEquals(Time.getShortTimestamp(
				existingCTEEntry.getCreateDate()),
			Time.getShortTimestamp(newCTEEntry.getCreateDate()));
		Assert.assertEquals(Time.getShortTimestamp(
				existingCTEEntry.getModifiedDate()),
			Time.getShortTimestamp(newCTEEntry.getModifiedDate()));
		Assert.assertEquals(existingCTEEntry.getClassNameId(),
			newCTEEntry.getClassNameId());
		Assert.assertEquals(existingCTEEntry.getClassPK(),
			newCTEEntry.getClassPK());
		Assert.assertEquals(existingCTEEntry.getResourcePrimKey(),
			newCTEEntry.getResourcePrimKey());
	}

	@Test
	public void testCountByResourcePrimKey() throws Exception {
		_persistence.countByResourcePrimKey(RandomTestUtil.nextLong());

		_persistence.countByResourcePrimKey(0L);
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		CTEEntry newCTEEntry = addCTEEntry();

		CTEEntry existingCTEEntry = _persistence.findByPrimaryKey(newCTEEntry.getPrimaryKey());

		Assert.assertEquals(existingCTEEntry, newCTEEntry);
	}

	@Test(expected = NoSuchCTEEntryException.class)
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		_persistence.findByPrimaryKey(pk);
	}

	@Test
	public void testFindAll() throws Exception {
		_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			getOrderByComparator());
	}

	protected OrderByComparator<CTEEntry> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("CTEEntry", "entryId", true,
			"companyId", true, "userId", true, "userName", true, "createDate",
			true, "modifiedDate", true, "classNameId", true, "classPK", true,
			"resourcePrimKey", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		CTEEntry newCTEEntry = addCTEEntry();

		CTEEntry existingCTEEntry = _persistence.fetchByPrimaryKey(newCTEEntry.getPrimaryKey());

		Assert.assertEquals(existingCTEEntry, newCTEEntry);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTEEntry missingCTEEntry = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingCTEEntry);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		CTEEntry newCTEEntry1 = addCTEEntry();
		CTEEntry newCTEEntry2 = addCTEEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTEEntry1.getPrimaryKey());
		primaryKeys.add(newCTEEntry2.getPrimaryKey());

		Map<Serializable, CTEEntry> cteEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, cteEntries.size());
		Assert.assertEquals(newCTEEntry1,
			cteEntries.get(newCTEEntry1.getPrimaryKey()));
		Assert.assertEquals(newCTEEntry2,
			cteEntries.get(newCTEEntry2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, CTEEntry> cteEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cteEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		CTEEntry newCTEEntry = addCTEEntry();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTEEntry.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, CTEEntry> cteEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cteEntries.size());
		Assert.assertEquals(newCTEEntry,
			cteEntries.get(newCTEEntry.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, CTEEntry> cteEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(cteEntries.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		CTEEntry newCTEEntry = addCTEEntry();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newCTEEntry.getPrimaryKey());

		Map<Serializable, CTEEntry> cteEntries = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, cteEntries.size());
		Assert.assertEquals(newCTEEntry,
			cteEntries.get(newCTEEntry.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = CTEEntryLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<CTEEntry>() {
				@Override
				public void performAction(CTEEntry cteEntry) {
					Assert.assertNotNull(cteEntry);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		CTEEntry newCTEEntry = addCTEEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CTEEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				newCTEEntry.getEntryId()));

		List<CTEEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		CTEEntry existingCTEEntry = result.get(0);

		Assert.assertEquals(existingCTEEntry, newCTEEntry);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CTEEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.add(RestrictionsFactoryUtil.eq("entryId",
				RandomTestUtil.nextLong()));

		List<CTEEntry> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		CTEEntry newCTEEntry = addCTEEntry();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CTEEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		Object newEntryId = newCTEEntry.getEntryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { newEntryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingEntryId = result.get(0);

		Assert.assertEquals(existingEntryId, newEntryId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CTEEntry.class,
				_dynamicQueryClassLoader);

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("entryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("entryId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected CTEEntry addCTEEntry() throws Exception {
		long pk = RandomTestUtil.nextLong();

		CTEEntry cteEntry = _persistence.create(pk);

		cteEntry.setCompanyId(RandomTestUtil.nextLong());

		cteEntry.setUserId(RandomTestUtil.nextLong());

		cteEntry.setUserName(RandomTestUtil.randomString());

		cteEntry.setCreateDate(RandomTestUtil.nextDate());

		cteEntry.setModifiedDate(RandomTestUtil.nextDate());

		cteEntry.setClassNameId(RandomTestUtil.nextLong());

		cteEntry.setClassPK(RandomTestUtil.nextLong());

		cteEntry.setResourcePrimKey(RandomTestUtil.nextLong());

		_cteEntries.add(_persistence.update(cteEntry));

		return cteEntry;
	}

	private List<CTEEntry> _cteEntries = new ArrayList<CTEEntry>();
	private CTEEntryPersistence _persistence;
	private ClassLoader _dynamicQueryClassLoader;
}