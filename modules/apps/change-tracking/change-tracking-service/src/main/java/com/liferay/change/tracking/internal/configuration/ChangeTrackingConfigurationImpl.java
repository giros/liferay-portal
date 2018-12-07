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

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.configuration.ChangeTrackingConfiguration;
import com.liferay.portal.kernel.service.BaseLocalService;

import java.io.Serializable;

import java.util.function.Function;

/**
 * @author Mate Thurzo
 */
@ProviderType
public class ChangeTrackingConfigurationImpl<T, U>
	implements ChangeTrackingConfiguration<T, U> {

	@Override
	public Function<Long, T> getResourceEntityByResourceEntityIdFunction() {
		return _resouceEntityInformation.getEntityFunction();
	}

	@Override
	public Class<T> getResourceEntityClass() {
		return _resouceEntityInformation.getEntityClass();
	}

	@Override
	public Function<T, Serializable>
		getResourceEntityIdFromResourceEntityFunction() {

		return _resouceEntityInformation.getResourceIdFunction();
	}

	@Override
	public Function<U, Serializable>
		getResourceEntityIdFromVersionEntityFunction() {

		return _versionEntityInformation.getResourceIdFunction();
	}

	@Override
	public Integer[] getVersionEntityAllowedStatuses() {
		return _versionEntityInformation.getAllowedStatuses();
	}

	@Override
	public Function<Long, U> getVersionEntityByVersionEntityIdFunction() {
		return _versionEntityInformation.getEntityFunction();
	}

	@Override
	public Class<U> getVersionEntityClass() {
		return _versionEntityInformation.getEntityClass();
	}

	@Override
	public Function<T, Serializable>
		getVersionEntityIdFromResourceEntityFunction() {

		return _resouceEntityInformation.getVersionIdFunction();
	}

	@Override
	public Function<U, Serializable>
		getVersionEntityIdFromVersionEntityFunction() {

		return _versionEntityInformation.getVersionIdFunction();
	}

	@Override
	public Function<U, Integer> getVersionEntityStatusFunction() {
		return _versionEntityInformation.getStatusFunction();
	}

	public static class BuilderImpl<T, U> implements Builder<T, U> {

		public BuilderImpl() {
			_changeTrackingConfiguration =
				new ChangeTrackingConfigurationImpl<>();
		}

		public VersionEntityStep<U> addResourceEntity(
			Class<T> resourceEntityClass,
			Function<Long, T> resourceEntityByResourceEntityIdFunction,
			Function<T, Serializable>
				resourceEntityIdFromResourceEntityFunction,
			Function<T, Serializable> versionEntityIdFromResourceEntityFunction) {

			_changeTrackingConfiguration._resouceEntityInformation =
				new EntityInformation<>(
					resourceEntityClass,
					resourceEntityByResourceEntityIdFunction,
					resourceEntityIdFromResourceEntityFunction,
					versionEntityIdFromResourceEntityFunction, null, null);

			return new VersionEntityStepImpl<>();
		}

		public class BuildStepImpl implements BuildStep {

			@Override
			public ChangeTrackingConfiguration build() {
				return _changeTrackingConfiguration;
			}

		}

		public class VersionEntityStepImpl<U> implements VersionEntityStep<U> {

			public BuildStep addVersionEntity(
				Class<U> versionEntityClass,
				Function<Long, U> versionEntityByVersionEntityIdFunction,
				Function<U, Serializable>
					resourceEntityIdFromVersionEntityFunction,
				Function<U, Serializable>
					versionEntityIdFromVersionEntityFunction,
				Integer[] versionEntityAllowedStatuses,
				Function<U, Integer> versionEntityStatusFunction) {

				_changeTrackingConfiguration._versionEntityInformation =
					new EntityInformation<>(
						versionEntityClass,
						versionEntityByVersionEntityIdFunction,
						resourceEntityIdFromVersionEntityFunction,
						versionEntityIdFromVersionEntityFunction,
						versionEntityAllowedStatuses,
						versionEntityStatusFunction);

				return new BuildStepImpl();
			}

		}

		private final ChangeTrackingConfigurationImpl
			_changeTrackingConfiguration;

	}

	private ChangeTrackingConfigurationImpl() {
	}

	private EntityInformation<T> _resouceEntityInformation;
	private EntityInformation<U> _versionEntityInformation;

	private static class EntityInformation<T> {

		public EntityInformation(
			Class<T> entityClass, Function<Long, T> entityFunction,
			Function<T, Serializable> resourceEntityIdFunction,
			Function<T, Serializable> versionEntityIdFunction,
			Integer[] allowedStatuses, Function<T, Integer> statusFunction) {

			_entityClass = entityClass;
			_entityFunction = entityFunction;
			_resourceEntityIdFunction = resourceEntityIdFunction;
			_versionEntityIdFunction = versionEntityIdFunction;
			_allowedStatuses = allowedStatuses;
			_statusFunction = statusFunction;
		}

		public Integer[] getAllowedStatuses() {
			return _allowedStatuses;
		}

		public Class<T> getEntityClass() {
			return _entityClass;
		}

		public Function<Long, T> getEntityFunction() {
			return _entityFunction;
		}

		public Function<T, Serializable> getResourceIdFunction() {
			return _resourceEntityIdFunction;
		}

		public Function<T, Integer> getStatusFunction() {
			return _statusFunction;
		}

		public Function<T, Serializable> getVersionIdFunction() {
			return _versionEntityIdFunction;
		}

		private final Integer[] _allowedStatuses;
		private final Class<T> _entityClass;
		private final Function<Long, T> _entityFunction;
		private final Function<T, Serializable> _resourceEntityIdFunction;
		private final Function<T, Integer> _statusFunction;
		private final Function<T, Serializable> _versionEntityIdFunction;

	}

}