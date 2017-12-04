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

package com.liferay.exportimport.portlet.data.handler.helper;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerControl;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Element;

import javax.portlet.PortletPreferences;

/**
 * @author Mate Thurzo
 */
@ProviderType
public interface PortletDataHandlerHelper {

	public void addUncheckedModelAdditionCount(
		PortletDataContext portletDataContext,
		PortletDataHandlerControl portletDataHandlerControl);

	public void doAfterAddDefaultData(String portletId, long startTime);

	public void doAfterDelete(String portletId, long startTime);

	public void doAfterExport(
		PortletDataContext portletDataContext, Element rootElement,
		long startTime);

	public void doAfterImport(
		PortletDataContext portletDataContext, Element rootElement,
		long startTime);

	public long doBeforeAddDefaultData(String portletId);

	public long doBeforeDelete(String portletId);

	public long doBeforeExport(
		PortletDataContext portletDataContext, String portletId,
		Element rootElement, StagedModelType[] deletionSystemEvents,
		PortletDataHandlerControl[] exportControls,
		PortletPreferences portletPreferences);

	public void doBeforeImport(
			PortletDataContext portletDataContext, String portletId,
			Long startTime, Element rootElement, String data)
		throws DocumentException;

	public long getExportModelCount(
		ManifestSummary manifestSummary,
		PortletDataHandlerControl[] portletDataHandlerControls);

	public boolean validateSchemaVersion(
		String schemaVersion, String portletSchemaVersion);

}