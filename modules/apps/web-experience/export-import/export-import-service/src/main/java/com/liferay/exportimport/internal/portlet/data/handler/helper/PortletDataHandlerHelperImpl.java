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

package com.liferay.exportimport.internal.portlet.data.handler.helper;

import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.exportimport.portlet.data.handler.helper.PortletDataHandlerHelper;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.Version;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;

import java.util.Objects;

import javax.portlet.PortletPreferences;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(immediate = true, service = PortletDataHandlerHelper.class)
public class PortletDataHandlerHelperImpl implements PortletDataHandlerHelper {

	@Override
	public void addUncheckedModelAdditionCount(
		PortletDataContext portletDataContext,
		PortletDataHandlerControl portletDataHandlerControl) {

		if (!(portletDataHandlerControl instanceof PortletDataHandlerBoolean)) {
			return;
		}

		PortletDataHandlerBoolean portletDataHandlerBoolean =
			(PortletDataHandlerBoolean)portletDataHandlerControl;

		PortletDataHandlerControl[] childPortletDataHandlerControls =
			portletDataHandlerBoolean.getChildren();

		if (childPortletDataHandlerControls != null) {
			for (PortletDataHandlerControl childPortletDataHandlerControl :
					childPortletDataHandlerControls) {

				addUncheckedModelAdditionCount(
					portletDataContext, childPortletDataHandlerControl);
			}
		}

		if (Validator.isNull(portletDataHandlerControl.getClassName())) {
			return;
		}

		boolean checkedControl = GetterUtil.getBoolean(
			portletDataContext.getBooleanParameter(
				portletDataHandlerControl.getNamespace(),
				portletDataHandlerControl.getControlName(), false));

		if (!checkedControl) {
			ManifestSummary manifestSummary =
				portletDataContext.getManifestSummary();

			StagedModelType stagedModelType = new StagedModelType(
				portletDataHandlerControl.getClassName(),
				portletDataHandlerBoolean.getReferrerClassName());

			String manifestSummaryKey = ManifestSummary.getManifestSummaryKey(
				stagedModelType);

			manifestSummary.addModelAdditionCount(manifestSummaryKey, 0);
		}
	}

	@Override
	public void doAfterAddDefaultData(String portletId, long startTime) {
		if (_log.isInfoEnabled()) {
			long duration = System.currentTimeMillis() - startTime;

			_log.info(
				"Added default data to portlet in " +
					Time.getDuration(duration));
		}
	}

	@Override
	public void doAfterDelete(String portletId, long startTime) {
		if (_log.isInfoEnabled()) {
			long duration = System.currentTimeMillis() - startTime;

			_log.info("Deleted portlet in " + Time.getDuration(duration));
		}
	}

	@Override
	public void doAfterExport(
		PortletDataContext portletDataContext, Element rootElement,
		long startTime) {

		portletDataContext.setExportDataRootElement(rootElement);

		if (_log.isInfoEnabled()) {
			long duration = System.currentTimeMillis() - startTime;

			_log.info("Exported portlet in " + Time.getDuration(duration));
		}
	}

	@Override
	public void doAfterImport(
		PortletDataContext portletDataContext, Element rootElement,
		long startTime) {

		long sourceGroupId = portletDataContext.getSourceGroupId();

		portletDataContext.setImportDataRootElement(rootElement);
		portletDataContext.setSourceGroupId(sourceGroupId);

		if (_log.isInfoEnabled()) {
			long duration = System.currentTimeMillis() - startTime;

			_log.info("Imported portlet in " + Time.getDuration(duration));
		}
	}

	@Override
	public long doBeforeAddDefaultData(String portletId) {
		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Adding default data to portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		return startTime;
	}

	@Override
	public long doBeforeDelete(String portletId) {
		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Deleting portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		return startTime;
	}

	@Override
	public long doBeforeExport(
		PortletDataContext portletDataContext, String portletId,
		Element rootElement, StagedModelType[] deletionSystemEvents,
		PortletDataHandlerControl[] exportControls,
		PortletPreferences portletPreferences) {

		long startTime = 0;

		if (_log.isInfoEnabled()) {
			_log.info("Exporting portlet " + portletId);

			startTime = System.currentTimeMillis();
		}

		rootElement = portletDataContext.getExportDataRootElement();

		portletDataContext.addDeletionSystemEventStagedModelTypes(
			deletionSystemEvents);

		for (PortletDataHandlerControl portletDataHandlerControl :
				exportControls) {

			addUncheckedModelAdditionCount(
				portletDataContext, portletDataHandlerControl);
		}

		return startTime;
	}

	@Override
	public void doBeforeImport(
			PortletDataContext portletDataContext, String portletId,
			Long startTime, Element rootElement, String data)
		throws DocumentException {

		if (_log.isInfoEnabled()) {
			_log.info("Importing portlet " + portletId);

			startTime = Long.valueOf(System.currentTimeMillis());
		}

		if (Validator.isXml(data)) {
			rootElement = portletDataContext.getImportDataRootElement();

			portletDataContext.addImportDataRootElement(data);
		}
	}

	@Override
	public long getExportModelCount(
		ManifestSummary manifestSummary,
		PortletDataHandlerControl[] portletDataHandlerControls) {

		long totalModelCount = -1;

		for (PortletDataHandlerControl portletDataHandlerControl :
				portletDataHandlerControls) {

			StagedModelType stagedModelType = new StagedModelType(
				portletDataHandlerControl.getClassName(),
				portletDataHandlerControl.getReferrerClassName());

			long modelAdditionCount = manifestSummary.getModelAdditionCount(
				stagedModelType);

			if (portletDataHandlerControl instanceof
					PortletDataHandlerBoolean) {

				PortletDataHandlerBoolean portletDataHandlerBoolean =
					(PortletDataHandlerBoolean)portletDataHandlerControl;

				PortletDataHandlerControl[] childPortletDataHandlerControls =
					portletDataHandlerBoolean.getChildren();

				if (childPortletDataHandlerControls != null) {
					long childModelCount = getExportModelCount(
						manifestSummary, childPortletDataHandlerControls);

					if (childModelCount != -1) {
						if (modelAdditionCount == -1) {
							modelAdditionCount = childModelCount;
						}
						else {
							modelAdditionCount += childModelCount;
						}
					}
				}
			}

			if (modelAdditionCount == -1) {
				continue;
			}

			if (totalModelCount == -1) {
				totalModelCount = modelAdditionCount;
			}
			else {
				totalModelCount += modelAdditionCount;
			}
		}

		return totalModelCount;
	}

	@Override
	public boolean validateSchemaVersion(
		String schemaVersion, String portletSchemaVersion) {

		// Major version has to be identical

		Version currentVersion = Version.getInstance(portletSchemaVersion);
		Version importedVersion = Version.getInstance(schemaVersion);

		if (!Objects.equals(
				currentVersion.getMajor(), importedVersion.getMajor())) {

			return false;
		}

		// Imported minor version should be less than or equal to the current
		// minor version

		int currentMinorVersion = GetterUtil.getInteger(
			currentVersion.getMinor(), -1);
		int importedMinorVersion = GetterUtil.getInteger(
			importedVersion.getMinor(), -1);

		if (((currentMinorVersion == -1) && (importedMinorVersion == -1)) ||
			(currentMinorVersion < importedMinorVersion)) {

			return false;
		}

		// Import should be compatible with all minor versions if previous
		// validations pass

		return true;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletDataHandlerHelperImpl.class);

}