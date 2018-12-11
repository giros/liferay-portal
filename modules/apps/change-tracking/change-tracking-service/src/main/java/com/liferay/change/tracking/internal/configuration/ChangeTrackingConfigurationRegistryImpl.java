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
import com.liferay.change.tracking.configuration.ChangeTrackingConfigurationRegistry;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Gergely Mathe
 */
@Component(
	immediate = true, service = ChangeTrackingConfigurationRegistry.class
)
public class ChangeTrackingConfigurationRegistryImpl
	implements ChangeTrackingConfigurationRegistry {

	public Optional<ChangeTrackingConfiguration<?, ?>>
		getChangeTrackingConfigurationByResourceClass(Class<?> clazz) {

		return getChangeTrackingConfigurationByResourceClassName(
			clazz.getName());
	}

	public Optional<ChangeTrackingConfiguration<?, ?>>
		getChangeTrackingConfigurationByResourceClassName(String className) {

		return Optional.ofNullable(
			_configurationsByResourceClassName.get(className));
	}

	public Optional<ChangeTrackingConfiguration<?, ?>>
		getChangeTrackingConfigurationByVersionClass(Class<?> clazz) {

		return getChangeTrackingConfigurationByVersionClassName(
			clazz.getName());
	}

	public Optional<ChangeTrackingConfiguration<?, ?>>
		getChangeTrackingConfigurationByVersionClassName(String className) {

		return Optional.ofNullable(
			_configurationsByVersionClassName.get(className));
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		unbind = "_removeChangeTrackingConfiguration"
	)
	private void _addChangeTrackingConfiguration(
		ChangeTrackingConfiguration<?, ?> changeTrackingConfiguration) {

		Class<?> resourceEntityClass =
			changeTrackingConfiguration.getResourceEntityClass();
		Class<?> versionEntityClass =
			changeTrackingConfiguration.getVersionEntityClass();

		_configurationsByResourceClassName.put(
			resourceEntityClass.getName(), changeTrackingConfiguration);
		_configurationsByVersionClassName.put(
			versionEntityClass.getName(), changeTrackingConfiguration);
	}

	private void _removeChangeTrackingConfiguration(
		ChangeTrackingConfiguration<?, ?> changeTrackingConfiguration) {

		Class<?> resourceEntityClass =
			changeTrackingConfiguration.getResourceEntityClass();
		Class<?> versionEntityClass =
			changeTrackingConfiguration.getVersionEntityClass();

		_configurationsByResourceClassName.remove(
			resourceEntityClass.getName());
		_configurationsByVersionClassName.remove(versionEntityClass.getName());
	}

	private final Map<String, ChangeTrackingConfiguration<?, ?>>
		_configurationsByResourceClassName = new HashMap<>();
	private final Map<String, ChangeTrackingConfiguration<?, ?>>
		_configurationsByVersionClassName = new HashMap<>();

}