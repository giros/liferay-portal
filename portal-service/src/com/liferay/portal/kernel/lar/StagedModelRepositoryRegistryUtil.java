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

package com.liferay.portal.kernel.lar;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceRegistrationMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Provides a utility facade to the staged model repository registry framework.
 *
 * @author Gergely Mathe
 */
@ProviderType
public class StagedModelRepositoryRegistryUtil {

	/**
	 * Returns the registered staged model repository with the class name.
	 *
	 * @param  className the name of the staged model class
	 * @return the registered staged model repository with the class name, or
	 *         <code>null</code> if none are registered with the class name
	 */
	public static StagedModelRepository<?> getStagedModelRepository(
		String className) {

		return _instance._getStagedModelRepository(className);
	}

	/**
	 * Returns all the registered staged model repositories.
	 *
	 * @return the registered staged model repositories
	 */
	public static List<StagedModelRepository<?>> getStagedModelRepositories() {
		return _instance._getStagedModelRepositories();
	}

	/**
	 * Registers the staged model repository.
	 *
	 * @param stagedModelRepository the staged model repository to register
	 */
	public static void register(
		StagedModelRepository<?> stagedModelRepository) {

		_instance._register(stagedModelRepository);
	}

	/**
	 * Unregisters the staged model repositories.
	 *
	 * @param stagedModelRepositories the staged model repositories to
	 *        unregister
	 */
	public static void unregister(
		List<StagedModelRepository<?>> stagedModelRepositories) {

		for (StagedModelRepository<?> stagedModelRepository :
				stagedModelRepositories) {

			unregister(stagedModelRepository);
		}
	}

	/**
	 * Unregisters the staged model repository.
	 *
	 * @param stagedModelRepository the staged model repository to unregister
	 */
	public static void unregister(
		StagedModelRepository<?> stagedModelRepository) {

		_instance._unregister(stagedModelRepository);
	}

	private StagedModelRepositoryRegistryUtil() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			(Class<StagedModelRepository<?>>)(Class<?>)
				StagedModelRepository.class,
			new StagedModelRepositoryServiceTrackerCustomizer());

		_serviceTracker.open();
	}

	private StagedModelRepository<?> _getStagedModelRepository(
		String className) {

		return _stagedModelRepositories.get(className);
	}

	private List<StagedModelRepository<?>> _getStagedModelRepositories() {
		Collection<StagedModelRepository<?>> values =
			_stagedModelRepositories.values();

		return ListUtil.fromCollection(values);
	}

	private void _register(StagedModelRepository<?> stagedModelRepository) {
		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<StagedModelRepository<?>> serviceRegistration =
			registry.registerService(
				(Class<StagedModelRepository<?>>)(Class<?>)
					StagedModelRepository.class, stagedModelRepository);

		_serviceRegistrations.put(stagedModelRepository, serviceRegistration);
	}

	private void _unregister(StagedModelRepository<?> stagedModelRepository) {
		ServiceRegistration<StagedModelRepository<?>> serviceRegistration =
			_serviceRegistrations.remove(stagedModelRepository);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private static final StagedModelRepositoryRegistryUtil _instance =
		new StagedModelRepositoryRegistryUtil();

	private final ServiceRegistrationMap<StagedModelRepository<?>>
		_serviceRegistrations = new ServiceRegistrationMap<>();
	private final
		ServiceTracker<StagedModelRepository<?>, StagedModelRepository<?>>
			_serviceTracker;
	private final Map<String, StagedModelRepository<?>>
		_stagedModelRepositories = new ConcurrentHashMap<>();

	private class StagedModelRepositoryServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<StagedModelRepository<?>, StagedModelRepository<?>> {

		@Override
		public StagedModelRepository<?> addingService(
			ServiceReference<StagedModelRepository<?>> serviceReference) {

			Registry registry = RegistryUtil.getRegistry();

			StagedModelRepository<?> stagedModelRepository =
				registry.getService(serviceReference);

			for (String className : stagedModelRepository.getClassNames()) {
				_stagedModelRepositories.put(className, stagedModelRepository);
			}

			return stagedModelRepository;
		}

		@Override
		public void modifiedService(
			ServiceReference<StagedModelRepository<?>> serviceReference,
			StagedModelRepository<?> stagedModelRepository) {
		}

		@Override
		public void removedService(
			ServiceReference<StagedModelRepository<?>> serviceReference,
			StagedModelRepository<?> stagedModelRepository) {

			Registry registry = RegistryUtil.getRegistry();

			registry.ungetService(serviceReference);

			for (String className : stagedModelRepository.getClassNames()) {
				_stagedModelRepositories.remove(className);
			}
		}

	}

}