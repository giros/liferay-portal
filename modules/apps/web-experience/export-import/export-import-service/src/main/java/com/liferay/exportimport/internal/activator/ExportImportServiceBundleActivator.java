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

package com.liferay.exportimport.internal.activator;

import com.liferay.exportimport.warning.message.ExportImportWarningMessage;
import com.liferay.exportimport.warning.message.ExportImportWarningMessageUtil;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Gergely Mathe
 */
public class ExportImportServiceBundleActivator implements BundleActivator {

	@Override
	public void start(BundleContext bundleContext) throws Exception {
		_exportImportWarningMessageServiceTracker =
			new ServiceTracker
				<ExportImportWarningMessage, ExportImportWarningMessage>(
					bundleContext, ExportImportWarningMessage.class.getName(),
					null) {

				@Override
				public ExportImportWarningMessage addingService(
					ServiceReference
						<ExportImportWarningMessage> serviceReference) {

					ExportImportWarningMessage exportImportWarningMessage =
						bundleContext.getService(serviceReference);

					ExportImportWarningMessageUtil.
						setExportImportWarningMessage(
							exportImportWarningMessage);

					return exportImportWarningMessage;
				}

			};

		_exportImportWarningMessageServiceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		_exportImportWarningMessageServiceTracker.close();
	}

	private ServiceTracker
		<ExportImportWarningMessage, ExportImportWarningMessage>
			_exportImportWarningMessageServiceTracker;

}