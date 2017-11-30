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

package com.liferay.portal.lar.test;

import com.liferay.exportimport.kernel.lar.DataLevel;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.PortletLocalServiceUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.PortletPreferencesImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Zsolt Berentey
 */
public abstract class BasePortletDataHandlerTestCase {

	@Before
	public void setUp() throws Exception {
		stagingGroup = GroupTestUtil.addGroup();

		portletId = getPortletId();

		portletDataHandler = getPortletDataHandler(portletId);
	}

	@Test
	public void testDataLevel() throws Exception {
		Assert.assertEquals(getDataLevel(), portletDataHandler.getDataLevel());
	}

	@Test
	public void testGetDataPortletPreferences() {
		Assert.assertArrayEquals(
			getDataPortletPreferences(),
			portletDataHandler.getDataPortletPreferences());
	}

	@Test
	public void testGetDeletionSystemEventStagedModelTypes() {
		Assert.assertArrayEquals(
			getDeletionSystemEventStagedModelTypes(),
			portletDataHandler.getDeletionSystemEventStagedModelTypes());
	}

	@Test
	public void testGetExportControls() throws PortletDataException {
		Assert.assertArrayEquals(
			getExportControls(), portletDataHandler.getExportControls());
	}

	@Test
	public void testGetExportMetadataControls() throws PortletDataException {
		Assert.assertArrayEquals(
			getExportMetadataControls(),
			portletDataHandler.getExportMetadataControls());
	}

	@Test
	public void testGetExportConfigurationControls() throws Exception {
		initExport();

		addStagedModels();

		Layout publicLayout = LayoutTestUtil.addLayout(stagingGroup, false);
		Layout privateLayout = LayoutTestUtil.addLayout(stagingGroup, true);

		Assert.assertArrayEquals(
			getExportConfigurationControls(
				stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
				PortletLocalServiceUtil.getPortletById(getPortletId()),
				publicLayout.getPlid(), false),
			portletDataHandler.getExportConfigurationControls(
				stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
				PortletLocalServiceUtil.getPortletById(getPortletId()),
				publicLayout.getPlid(), false));
		Assert.assertArrayEquals(
			getExportConfigurationControls(
				stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
				PortletLocalServiceUtil.getPortletById(getPortletId()),
				privateLayout.getPlid(), true),
			portletDataHandler.getExportConfigurationControls(
				stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
				PortletLocalServiceUtil.getPortletById(getPortletId()),
				privateLayout.getPlid(), true));

		Assert.assertArrayEquals(
			getExportConfigurationControls(
				stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
				PortletLocalServiceUtil.getPortletById(getPortletId()), -1,
				false),
			portletDataHandler.getExportConfigurationControls(
				stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
				PortletLocalServiceUtil.getPortletById(getPortletId()), -1,
				false));
		Assert.assertArrayEquals(
			getExportConfigurationControls(
				stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
				PortletLocalServiceUtil.getPortletById(getPortletId()), -1,
				true),
			portletDataHandler.getExportConfigurationControls(
				stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
				PortletLocalServiceUtil.getPortletById(getPortletId()), -1,
				true));
	}

	@Test
	public void testGetImportControls() throws PortletDataException {
		Assert.assertArrayEquals(
			getImportControls(), portletDataHandler.getImportControls());
	}

	@Test
	public void testGetImportMetadataControls() throws PortletDataException {
		Assert.assertArrayEquals(
			getImportMetadataControls(),
			portletDataHandler.getImportMetadataControls());
	}

	@Test
	public void testGetNamespace() {
		Assert.assertEquals(getNamespace(), portletDataHandler.getNamespace());
	}

	@Test
	public void testGetPortletId() {
		Assert.assertEquals(getPortletId(), portletDataHandler.getPortletId());
	}

	@Test
	public void testGetRank() {
		Assert.assertEquals(getRank(), portletDataHandler.getRank());
	}

	@Test
	public void testGetSchemaVersion() {
		Assert.assertEquals(
			getSchemaVersion(), portletDataHandler.getSchemaVersion());
	}

	@Test
	public void testGetServiceName() {
		Assert.assertEquals(
			getServiceName(), portletDataHandler.getServiceName());
	}

	@Test
	public void testGetStagingControls() {
		Assert.assertArrayEquals(
			getStagingControls(), portletDataHandler.getStagingControls());
	}

	@Test
	public void testIsDataAlwaysStaged() {
		Assert.assertEquals(
			isDataAlwaysStaged(), portletDataHandler.isDataAlwaysStaged());
	}

	@Test
	public void testIsDataLocalized() {
		Assert.assertEquals(
			isDataLocalized(), portletDataHandler.isDataLocalized());
	}

	@Test
	public void testIsDataPortalLevel() {
		Assert.assertEquals(
			isDataPortalLevel(), portletDataHandler.isDataPortalLevel());
	}

	@Test
	public void testIsDataPortletInstanceLevel() {
		Assert.assertEquals(
			isDataPortletInstanceLevel(),
			portletDataHandler.isDataPortletInstanceLevel());
	}

	@Test
	public void testIsDataSiteLevel() {
		Assert.assertEquals(
			isDataSiteLevel(), portletDataHandler.isDataSiteLevel());
	}

	@Test
	public void testIsDisplayPortlet() {
		Assert.assertEquals(
			isDisplayPortlet(), portletDataHandler.isDisplayPortlet());
	}

	@Test
	public void testIsPublishToLiveByDefault() {
		Assert.assertEquals(
			isPublishToLiveByDefault(),
			portletDataHandler.isPublishToLiveByDefault());
	}

	@Test
	public void testIsRollbackOnException() {
		Assert.assertEquals(
			isRollbackOnException(),
			portletDataHandler.isRollbackOnException());
	}

	@Test
	public void testIsSupportsDataStrategyCopyAsNew() {
		Assert.assertEquals(
			isSupportsDataStrategyCopyAsNew(),
			portletDataHandler.isSupportsDataStrategyCopyAsNew());
	}

	@Test
	public void testIsSupportsDataStrategyMirrorWithOverwriting() {
		Assert.assertEquals(
			isSupportsDataStrategyMirrorWithOverwriting(),
			portletDataHandler.isSupportsDataStrategyMirrorWithOverwriting());
	}

	@Test
	public void testPrepareManifestSummary() throws Exception {
		initExport();

		addStagedModels();

		portletDataContext.setEndDate(getEndDate());

		portletDataHandler.prepareManifestSummary(portletDataContext);

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		ManifestSummary expectedManifestSummary =
			(ManifestSummary)manifestSummary.clone();

		manifestSummary.resetCounters();

		portletDataHandler.exportData(
			portletDataContext, portletId, new PortletPreferencesImpl());

		checkManifestSummary(expectedManifestSummary);
	}

	protected void addBooleanParameter(
		Map<String, String[]> parameterMap, String namespace, String name,
		boolean value) {

		PortletDataHandlerBoolean portletDataHandlerBoolean =
			new PortletDataHandlerBoolean(namespace, name);

		parameterMap.put(
			portletDataHandlerBoolean.getNamespacedControlName(),
			new String[] {String.valueOf(value)});
	}

	protected void addParameters(Map<String, String[]> parameterMap) {
	}

	protected abstract void addStagedModels() throws Exception;

	protected void checkManifestSummary(
		ManifestSummary expectedManifestSummary) {

		ManifestSummary manifestSummary =
			portletDataContext.getManifestSummary();

		for (String manifestSummaryKey :
				manifestSummary.getManifestSummaryKeys()) {

			Assert.assertFalse(
				manifestSummaryKey.endsWith(
					StagedModelType.REFERRER_CLASS_NAME_ALL));
			Assert.assertFalse(
				manifestSummaryKey.endsWith(
					StagedModelType.REFERRER_CLASS_NAME_ANY));
		}

		for (String manifestSummaryKey :
				expectedManifestSummary.getManifestSummaryKeys()) {

			String[] keyParts = StringUtil.split(
				manifestSummaryKey, StringPool.POUND);

			long expectedModelAdditionCount =
				expectedManifestSummary.getModelAdditionCount(
					manifestSummaryKey);

			StagedModelType stagedModelType = new StagedModelType(keyParts[0]);

			if (keyParts.length > 1) {
				stagedModelType = new StagedModelType(keyParts[0], keyParts[1]);
			}

			long modelAdditionCount = manifestSummary.getModelAdditionCount(
				stagedModelType);

			if (expectedModelAdditionCount == 0) {
				Assert.assertFalse(modelAdditionCount > 0);
			}
			else {
				Assert.assertEquals(
					expectedModelAdditionCount, modelAdditionCount);
			}
		}
	}

	protected DataLevel getDataLevel() {
		return DataLevel.SITE;
	}

	protected String[] getDataPortletPreferences() {
		return StringPool.EMPTY_ARRAY;
	}

	protected StagedModelType[] getDeletionSystemEventStagedModelTypes() {
		return new StagedModelType[0];
	}

	protected Date getEndDate() {
		return new Date();
	}

	protected PortletDataHandlerControl[] getExportConfigurationControls(
		long companyId, long groupId, Portlet portlet,
		boolean privateLayout)
		throws Exception {

		return getExportConfigurationControls(
			companyId, groupId, portlet, -1, privateLayout);
	}

	protected PortletDataHandlerControl[] getExportConfigurationControls(
		long companyId, long groupId, Portlet portlet, long plid,
		boolean privateLayout)
		throws Exception {

		List<PortletDataHandlerBoolean> configurationControls =
			new ArrayList<>();

		// Setup

		if ((PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
			PortletKeys.PREFS_OWNER_ID_DEFAULT,
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT, plid, portlet, false) >
			 0) ||
			(PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				groupId, PortletKeys.PREFS_OWNER_TYPE_GROUP,
				portlet.getRootPortletId(), false) > 0) ||
			(PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
				portlet.getRootPortletId(), false) > 0)) {

			PortletDataHandlerControl[] portletDataHandlerControls = null;

			if (isDisplayPortlet()) {
				portletDataHandlerControls = getExportControls();
			}

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_SETUP, "setup", true,
					false, portletDataHandlerControls, null, null));
		}

		// Archived setups

		if (PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
			-1, PortletKeys.PREFS_OWNER_TYPE_ARCHIVED,
			portlet.getRootPortletId(), false) > 0) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_ARCHIVED_SETUPS,
					"configuration-templates", true, false, null, null, null));
		}

		// User preferences

		if ((PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
			-1, PortletKeys.PREFS_OWNER_TYPE_USER, plid, portlet, false) >
			 0) ||
			(PortletPreferencesLocalServiceUtil.getPortletPreferencesCount(
				groupId, PortletKeys.PREFS_OWNER_TYPE_USER,
				PortletKeys.PREFS_PLID_SHARED, portlet, false) > 0)) {

			configurationControls.add(
				new PortletDataHandlerBoolean(
					null, PortletDataHandlerKeys.PORTLET_USER_PREFERENCES,
					"user-preferences", true, false, null, null, null));
		}

		return configurationControls.toArray(
			new PortletDataHandlerBoolean[configurationControls.size()]);
	}

	protected PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[0];
	}

	protected PortletDataHandlerControl[] getExportMetadataControls() {
		return new PortletDataHandlerControl[0];
	}

	protected PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[0];
	}

	protected PortletDataHandlerControl[] getImportMetadataControls() {
		return new PortletDataHandlerControl[0];
	}

	protected String getNamespace() {
		return StringPool.BLANK;
	}

	protected PortletDataHandler getPortletDataHandler(String portletId) {
		try {
			Registry registry = RegistryUtil.getRegistry();

			Collection<PortletDataHandler> portletDataHandlers =
				registry.getServices(
					PortletDataHandler.class,
					"(javax.portlet.name=" + portletId + ")");

			Iterator<PortletDataHandler> iterator =
				portletDataHandlers.iterator();

			return iterator.next();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected abstract String getPortletId();

	protected int getRank() {
		return 100;
	}

	protected String getSchemaVersion() {
		return "1.0.0";
	}

	protected String getServiceName() {
		return null;
	}

	protected Date getStartDate() {
		return new Date(System.currentTimeMillis() - Time.HOUR);
	}

	protected PortletDataHandlerControl[] getStagingControls() {
		return new PortletDataHandlerControl[0];
	}

	protected void initExport() throws Exception {
		Map<String, String[]> parameterMap = new LinkedHashMap<>();

		addParameters(parameterMap);

		zipWriter = ZipWriterFactoryUtil.getZipWriter();

		portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				stagingGroup.getCompanyId(), stagingGroup.getGroupId(),
				parameterMap, getStartDate(), getEndDate(), zipWriter);

		rootElement = SAXReaderUtil.createElement("root");

		portletDataContext.setExportDataRootElement(rootElement);

		missingReferencesElement = SAXReaderUtil.createElement(
			"missing-references");

		portletDataContext.setMissingReferencesElement(
			missingReferencesElement);
	}

	protected boolean isDataAlwaysStaged() {
		return false;
	}

	protected boolean isDataLocalized() {
		return false;
	}

	protected boolean isDataPortalLevel() {
		DataLevel dataLevel = getDataLevel();

		return dataLevel.equals(DataLevel.PORTAL);
	}

	protected boolean isDataPortletInstanceLevel() {
		DataLevel dataLevel = getDataLevel();

		return dataLevel.equals(DataLevel.PORTLET_INSTANCE);
	}

	protected boolean isDataSiteLevel() {
		DataLevel dataLevel = getDataLevel();

		return dataLevel.equals(DataLevel.SITE);
	}

	protected boolean isDisplayPortlet() {
		if (isDataPortletInstanceLevel() &&
			!ArrayUtil.isEmpty(getDataPortletPreferences())) {

			return true;
		}

		return false;
	}

	protected boolean isPublishToLiveByDefault() {
		return false;
	}

	protected boolean isRollbackOnException() {
		return true;
	}

	protected boolean isSupportsDataStrategyCopyAsNew() {
		return true;
	}

	protected boolean isSupportsDataStrategyMirrorWithOverwriting() {
		return true;
	}

	protected Element missingReferencesElement;
	protected PortletDataContext portletDataContext;
	protected PortletDataHandler portletDataHandler;
	protected String portletId;
	protected Element rootElement;

	@DeleteAfterTestRun
	protected Group stagingGroup;

	protected ZipWriter zipWriter;

}