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

package com.liferay.portal.kernel.util;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.ContentProcessor;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;
import com.liferay.registry.util.StringPlus;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gergely Mathe
 */
@ProviderType
public class ContentProcessorRegistryUtil {

	public static ContentProcessor getContentProcessor(String className) {
		return _instance._getContentProcessor(className);
	}

	public static List<ContentProcessor> getContentProcessors() {
		return _instance._getContentProcessors();
	}

	public static void register(ContentProcessor contentProcessor) {
		_instance._register(contentProcessor);
	}

	public static void unregister(ContentProcessor contentProcessor) {
		_instance._unregister(contentProcessor);
	}

	public static void unregister(List<ContentProcessor> contentProcessors) {
		for (ContentProcessor contentProcessor : contentProcessors) {
			unregister(contentProcessor);
		}
	}

	private ContentProcessorRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<ContentProcessor>)(Class) ContentProcessor.class,
			new ContentProcessorServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private ContentProcessor _getContentProcessor(String className) {
		return _contentProcessors.get(className);
	}

	private List<ContentProcessor> _getContentProcessors() {
		Collection<ContentProcessor> values = _contentProcessors.values();

		return ListUtil.fromCollection(values);
	}

	private void _register(ContentProcessor contentProcessor) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<ContentProcessor>
			serviceRegistration = registry.registerService(
				(Class<ContentProcessor>)(Class) ContentProcessor.class,
				contentProcessor);

		_serviceRegistrations.put(contentProcessor, serviceRegistration);
	}

	private void _unregister(ContentProcessor contentProcessor) {
		ServiceRegistration<ContentProcessor> serviceRegistration =
			_serviceRegistrations.remove(contentProcessor);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final ContentProcessorRegistryUtil _instance =
		new ContentProcessorRegistryUtil();

	private final Map<String, ContentProcessor> _contentProcessors =
		new ConcurrentHashMap<>();
	private final ServiceRegistrationMap<ContentProcessor>
		_serviceRegistrations = new ServiceRegistrationMap<>();
	private final ServiceTracker<ContentProcessor, ContentProcessor>
		_serviceTracker;

	private class ContentProcessorServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<ContentProcessor, ContentProcessor> {

		@Override
		public ContentProcessor addingService(
			ServiceReference<ContentProcessor> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			ContentProcessor contentProcessor = registry.getService(
				serviceReference);

			List<String> modelClassNames = StringPlus.asList(
				serviceReference.getProperty("model.class.name"));

			for (String modelClassName : modelClassNames) {
				_contentProcessors.put(modelClassName, contentProcessor);
			}

			return contentProcessor;
		}

		@Override
		public void modifiedService(
			ServiceReference<ContentProcessor> serviceReference,
			ContentProcessor contentProcessor) {
		}

		@Override
		public void removedService(
			ServiceReference<ContentProcessor> serviceReference,
			ContentProcessor contentProcessor) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			List<String> modelClassNames = StringPlus.asList(
				serviceReference.getProperty("model.class.name"));

			for (String modelClassName : modelClassNames) {
				_contentProcessors.remove(modelClassName);
			}
		}

	}

}