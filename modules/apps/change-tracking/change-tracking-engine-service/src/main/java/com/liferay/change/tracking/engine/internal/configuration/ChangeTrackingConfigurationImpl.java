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

package com.liferay.change.tracking.engine.internal.configuration;

import aQute.bnd.annotation.ProviderType;

import com.liferay.change.tracking.engine.configuration.ChangeTrackingConfiguration;

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

		public ResourceEntityByResourceEntityIdStep<T, U> setEntityClasses(
			Class<T> resourceEntityClass, Class<U> versionEntityClass) {

			_changeTrackingConfiguration._resouceEntityInformation =
				new EntityInformation<>(resourceEntityClass);
			_changeTrackingConfiguration._versionEntityInformation =
				new EntityInformation<>(versionEntityClass);

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
					_resouceEntityInformation.setResourceEntityIdFunction(
						resourceEntityIdFromResourceEntityFunction);
				_changeTrackingConfiguration.
					_resouceEntityInformation.setVersionEntityIdFunction(
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
					_versionEntityInformation.setResourceEntityIdFunction(
						resourceEntityIdFromVersionEntityFunction);
				_changeTrackingConfiguration.
					_versionEntityInformation.setVersionEntityIdFunction(
						versionEntityIdFromVersionEntityFunction);

				return new VersionEntityStatusInfoStepImpl<>();
			}

		}

		public class ResourceEntityByResourceEntityIdStepImpl<T, U>
			implements ResourceEntityByResourceEntityIdStep<T, U> {

			@Override
			public EntityIdsFromResourceEntityStep<T, U>
				setResourceEntityByResourceEntityIdFunction(
					Function<Long, T>
						resourceEntityByResourceEntityIdFunction) {

				_changeTrackingConfiguration.
					_resouceEntityInformation.setEntityFunction(
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
					_versionEntityInformation.setEntityFunction(
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

				_changeTrackingConfiguration.
					_versionEntityInformation.setAllowedStatuses(
						versionEntityAllowedStatuses);
				_changeTrackingConfiguration.
					_versionEntityInformation.setStatusFunction(
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

		public EntityInformation(Class<T> entityClass) {
			_entityClass = entityClass;
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

		public void setAllowedStatuses(Integer[] allowedStatuses) {
			_allowedStatuses = allowedStatuses;
		}

		public void setEntityFunction(Function<Long, T> entityFunction) {
			_entityFunction = entityFunction;
		}

		public void setResourceEntityIdFunction(
			Function<T, Serializable> resourceEntityIdFunction) {

			_resourceEntityIdFunction = resourceEntityIdFunction;
		}

		public void setStatusFunction(Function<T, Integer> statusFunction) {
			_statusFunction = statusFunction;
		}

		public void setVersionEntityIdFunction(
			Function<T, Serializable> versionEntityIdFunction) {

			_versionEntityIdFunction = versionEntityIdFunction;
		}

		private Integer[] _allowedStatuses;
		private final Class<T> _entityClass;
		private Function<Long, T> _entityFunction;
		private Function<T, Serializable> _resourceEntityIdFunction;
		private Function<T, Integer> _statusFunction;
		private Function<T, Serializable> _versionEntityIdFunction;

	}

}