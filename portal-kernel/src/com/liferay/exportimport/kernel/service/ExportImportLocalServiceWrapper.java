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

package com.liferay.exportimport.kernel.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExportImportLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportLocalService
 * @generated
 */
public class ExportImportLocalServiceWrapper
	implements ExportImportLocalService,
			   ServiceWrapper<ExportImportLocalService> {

	public ExportImportLocalServiceWrapper(
		ExportImportLocalService exportImportLocalService) {

		_exportImportLocalService = exportImportLocalService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExportImportLocalServiceUtil} to access the export import local service. Add custom service methods to <code>com.liferay.portlet.exportimport.service.impl.ExportImportLocalServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public java.io.File exportLayoutsAsFile(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.exportLayoutsAsFile(
			exportImportConfiguration);
	}

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	@Override
	public java.io.File exportLayoutsAsFile(
			long userId, long groupId, boolean privateLayout,
			java.util.Map<String, String[]> parameterMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.exportLayoutsAsFile(
			userId, groupId, privateLayout, parameterMap);
	}

	@Override
	public long exportLayoutsAsFileInBackground(
			long userId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.exportLayoutsAsFileInBackground(
			userId, exportImportConfiguration);
	}

	@Override
	public long exportLayoutsAsFileInBackground(
			long userId, long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.exportLayoutsAsFileInBackground(
			userId, exportImportConfigurationId);
	}

	@Override
	public java.io.File exportPortletInfoAsFile(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.exportPortletInfoAsFile(
			exportImportConfiguration);
	}

	@Override
	public long exportPortletInfoAsFileInBackground(
			long userId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.exportPortletInfoAsFileInBackground(
			userId, exportImportConfiguration);
	}

	@Override
	public long exportPortletInfoAsFileInBackground(
			long userId, long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.exportPortletInfoAsFileInBackground(
			userId, exportImportConfigurationId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _exportImportLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public void importLayouts(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, file);
	}

	@Override
	public void importLayouts(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		_exportImportLocalService.importLayouts(
			exportImportConfiguration, inputStream);
	}

	/**
	 * @deprecated As of Judson (7.1.x)
	 */
	@Deprecated
	@Override
	public void importLayouts(
			long userId, long groupId, boolean privateLayout,
			java.util.Map<String, String[]> parameterMap, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		_exportImportLocalService.importLayouts(
			userId, groupId, privateLayout, parameterMap, file);
	}

	@Override
	public void importLayoutsDataDeletions(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		_exportImportLocalService.importLayoutsDataDeletions(
			exportImportConfiguration, file);
	}

	@Override
	public long importLayoutsInBackground(
			long userId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importLayoutsInBackground(
			userId, exportImportConfiguration, file);
	}

	@Override
	public long importLayoutsInBackground(
			long userId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importLayoutsInBackground(
			userId, exportImportConfiguration, inputStream);
	}

	@Override
	public long importLayoutsInBackground(
			long userId, long exportImportConfigurationId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importLayoutsInBackground(
			userId, exportImportConfigurationId, file);
	}

	@Override
	public long importLayoutsInBackground(
			long userId, long exportImportConfigurationId,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importLayoutsInBackground(
			userId, exportImportConfigurationId, inputStream);
	}

	@Override
	public void importPortletDataDeletions(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		_exportImportLocalService.importPortletDataDeletions(
			exportImportConfiguration, file);
	}

	@Override
	public void importPortletInfo(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		_exportImportLocalService.importPortletInfo(
			exportImportConfiguration, file);
	}

	@Override
	public void importPortletInfo(
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		_exportImportLocalService.importPortletInfo(
			exportImportConfiguration, inputStream);
	}

	@Override
	public long importPortletInfoInBackground(
			long userId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importPortletInfoInBackground(
			userId, exportImportConfiguration, file);
	}

	@Override
	public long importPortletInfoInBackground(
			long userId,
			com.liferay.exportimport.kernel.model.ExportImportConfiguration
				exportImportConfiguration,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importPortletInfoInBackground(
			userId, exportImportConfiguration, inputStream);
	}

	@Override
	public long importPortletInfoInBackground(
			long userId, long exportImportConfigurationId, java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importPortletInfoInBackground(
			userId, exportImportConfigurationId, file);
	}

	@Override
	public long importPortletInfoInBackground(
			long userId, long exportImportConfigurationId,
			java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.importPortletInfoInBackground(
			userId, exportImportConfigurationId, inputStream);
	}

	@Override
	public com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportLayoutsFile(
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.validateImportLayoutsFile(
			exportImportConfiguration, file);
	}

	@Override
	public com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportLayoutsFile(
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.validateImportLayoutsFile(
			exportImportConfiguration, inputStream);
	}

	@Override
	public com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportPortletInfo(
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.File file)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.validateImportPortletInfo(
			exportImportConfiguration, file);
	}

	@Override
	public com.liferay.exportimport.kernel.lar.MissingReferences
			validateImportPortletInfo(
				com.liferay.exportimport.kernel.model.ExportImportConfiguration
					exportImportConfiguration,
				java.io.InputStream inputStream)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _exportImportLocalService.validateImportPortletInfo(
			exportImportConfiguration, inputStream);
	}

	@Override
	public ExportImportLocalService getWrappedService() {
		return _exportImportLocalService;
	}

	@Override
	public void setWrappedService(
		ExportImportLocalService exportImportLocalService) {

		_exportImportLocalService = exportImportLocalService;
	}

	private ExportImportLocalService _exportImportLocalService;

}