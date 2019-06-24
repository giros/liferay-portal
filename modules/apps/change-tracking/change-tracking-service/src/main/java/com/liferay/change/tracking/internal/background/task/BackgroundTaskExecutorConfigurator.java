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

package com.liferay.change.tracking.internal.background.task;

import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.internal.adapter.CTAdapterHelper;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Zoltan Csaszi
 */
@Component(immediate = true, service = BackgroundTaskExecutorConfigurator.class)
public class BackgroundTaskExecutorConfigurator {

	@Activate
	protected void activate(BundleContext bundleContext) {
		BackgroundTaskExecutor ctPublishBackgroundTaskExecutor =
			new CTPublishBackgroundTaskExecutor(
				_ctAdapterHelper, _ctEngineManager);

		_registerBackgroundTaskExecutor(
			bundleContext, ctPublishBackgroundTaskExecutor);
	}

	@Deactivate
	protected void deactivate() {
		for (ServiceRegistration<BackgroundTaskExecutor> serviceRegistration :
				_serviceRegistrations) {

			serviceRegistration.unregister();
		}
	}

	private void _registerBackgroundTaskExecutor(
		BundleContext bundleContext,
		BackgroundTaskExecutor backgroundTaskExecutor) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		Class<?> clazz = backgroundTaskExecutor.getClass();

		properties.put("background.task.executor.class.name", clazz.getName());

		ServiceRegistration<BackgroundTaskExecutor> serviceRegistration =
			bundleContext.registerService(
				BackgroundTaskExecutor.class, backgroundTaskExecutor,
				properties);

		_serviceRegistrations.add(serviceRegistration);
	}

	@Reference
	private CTAdapterHelper _ctAdapterHelper;

	@Reference
	private CTEngineManager _ctEngineManager;

	private final Set<ServiceRegistration<BackgroundTaskExecutor>>
		_serviceRegistrations = new HashSet<>();

}