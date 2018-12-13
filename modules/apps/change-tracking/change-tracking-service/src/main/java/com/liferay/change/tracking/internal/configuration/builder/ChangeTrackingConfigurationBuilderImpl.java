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

package com.liferay.change.tracking.internal.configuration.builder;

import com.liferay.change.tracking.configuration.ChangeTrackingConfiguration;
import com.liferay.change.tracking.configuration.builder.ChangeTrackingConfigurationBuilder;
import com.liferay.change.tracking.internal.configuration.ChangeTrackingConfigurationImpl;

import java.io.Serializable;

import java.util.function.Function;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = ChangeTrackingConfigurationBuilder.class)
public class ChangeTrackingConfigurationBuilderImpl<T, U>
	implements ChangeTrackingConfigurationBuilder<T, U> {

	@Activate
	public void activate() {
		_changeTrackingConfiguration = new ChangeTrackingConfigurationImpl<>();
	}

	@Override
	public ResourceEntityByResourceEntityIdStep<T, U> setEntityClasses(
		Class<T> resourceEntityClass, Class<U> versionEntityClass) {

		_changeTrackingConfiguration.setResourceEntityClass(
			resourceEntityClass);
		_changeTrackingConfiguration.setVersionEntityClass(versionEntityClass);

		return new ResourceEntityByResourceEntityIdStepImpl<>();
	}

	public class BuildStepImpl implements BuildStep {

		@Override
		public ChangeTrackingConfiguration build() {
			return _changeTrackingConfiguration;
		}

	}

	public class EntityIdsFromResourceEntityStepImpl<T, U>
		implements EntityIdsFromResourceEntityStep<T, U> {

		@Override
		public VersionEntityByVersionEntityIdStep<U>
			setEntityIdsFromResourceEntityFunctions(
				Function<T, Serializable>
					resourceEntityIdFromResourceEntityFunction,
				Function<T, Serializable>
					versionEntityIdFromResourceEntityFunction) {

			_changeTrackingConfiguration.
				setResourceEntityIdFromResourceEntityFunction(
					resourceEntityIdFromResourceEntityFunction);
			_changeTrackingConfiguration.
				setVersionEntityIdFromResourceEntityFunction(
					versionEntityIdFromResourceEntityFunction);

			return new VersionEntityByVersionEntityIdStepImpl<>();
		}

	}

	public class EntityIdsFromVersionEntityStepImpl<U>
		implements EntityIdsFromVersionEntityStep<U> {

		@Override
		public VersionEntityStatusInfoStep<U>
			setEntityIdsFromVersionEntityFunctions(
				Function<U, Serializable>
					resourceEntityIdFromVersionEntityFunction,
				Function<U, Serializable>
					versionEntityIdFromVersionEntityFunction) {

			_changeTrackingConfiguration.
				setResourceEntityIdFromVersionEntityFunction(
					resourceEntityIdFromVersionEntityFunction);
			_changeTrackingConfiguration.
				setVersionEntityIdFromVersionEntityFunction(
					versionEntityIdFromVersionEntityFunction);

			return new VersionEntityStatusInfoStepImpl<>();
		}

	}

	public class ResourceEntityByResourceEntityIdStepImpl<T, U>
		implements ResourceEntityByResourceEntityIdStep<T, U> {

		@Override
		public EntityIdsFromResourceEntityStep<T, U>
			setResourceEntityByResourceEntityIdFunction(
				Function<Long, T> resourceEntityByResourceEntityIdFunction) {

			_changeTrackingConfiguration.
				setResourceEntityByResourceEntityIdFunction(
					resourceEntityByResourceEntityIdFunction);

			return new EntityIdsFromResourceEntityStepImpl<>();
		}

	}

	public class VersionEntityByVersionEntityIdStepImpl<U>
		implements VersionEntityByVersionEntityIdStep<U> {

		@Override
		public EntityIdsFromVersionEntityStep<U>
			setversionEntityByVersionEntityIdFunction(
				Function<Long, U> versionEntityByVersionEntityIdFunction) {

			_changeTrackingConfiguration.
				setVersionEntityByVersionEntityIdFunction(
					versionEntityByVersionEntityIdFunction);

			return new EntityIdsFromVersionEntityStepImpl<>();
		}

	}

	public class VersionEntityStatusInfoStepImpl<U>
		implements VersionEntityStatusInfoStep<U> {

		@Override
		public BuildStep setVersionEntityStatusInfo(
			Integer[] versionEntityAllowedStatuses,
			Function<U, Integer> versionEntityStatusFunction) {

			_changeTrackingConfiguration.setVersionEntityAllowedStatuses(
				versionEntityAllowedStatuses);
			_changeTrackingConfiguration.setVersionEntityStatusFunction(
				versionEntityStatusFunction);

			return new BuildStepImpl();
		}

	}

	private ChangeTrackingConfigurationImpl _changeTrackingConfiguration;

}