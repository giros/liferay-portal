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

package com.liferay.change.tracking.internal.configuration;

import com.liferay.change.tracking.configuration.ChangeTrackingConfiguration;
import com.liferay.change.tracking.configuration.ChangeTrackingConfigurationRegistrar;

import java.util.Hashtable;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true, service = ChangeTrackingConfigurationRegistrar.class
)
public class ChangeTrackingConfigurationRegistrarImpl
	implements ChangeTrackingConfigurationRegistrar {

	public void register(
		ChangeTrackingConfiguration<?, ?> changeTrackingConfiguration) {

		if (changeTrackingConfiguration == null) {
			return;
		}

		final Bundle bundle = FrameworkUtil.getBundle(
			ChangeTrackingConfigurationRegistrarImpl.class);

		final BundleContext bundleContext = bundle.getBundleContext();

		bundleContext.registerService(
			ChangeTrackingConfiguration.class, changeTrackingConfiguration,
			new Hashtable<>());
	}

}