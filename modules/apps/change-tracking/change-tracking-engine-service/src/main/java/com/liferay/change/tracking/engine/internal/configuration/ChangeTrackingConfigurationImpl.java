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
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.service.BaseLocalService;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Mate Thurzo
 */
@ProviderType
public class ChangeTrackingConfigurationImpl<T, U>
	implements ChangeTrackingConfiguration<T, U> {

	@Override
	public Integer[] getAllowedStatuses() {
		return _versionEntityInformation.getAllowedStatuses();
	}

	@Override
	public List<Supplier<? extends Collection<U>>> getBaselining() {
		return _baseliningSuppliers;
	}

	@Override
	public String getIdentifier() {
		return _identifier;
	}

	@Override
	public Indexer<U> getIndexer() {
		return _versionEntityInformation._indexerFunction.apply(
			_versionEntityInformation._class);
	}

	@Override
	public Class<T> getResourceEntityClass() {
		return _resouceEntityInformation.getEntityClass();
	}

	@Override
	public Function<Long, T> getResourceEntityFunction() {
		return _resouceEntityInformation.getResourceEntityFunction();
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
	public Function<U, Integer> getStatusFunction() {
		return _versionEntityInformation.getStatusFunction();
	}

	@Override
	public Class<U> getVersionEntityClass() {
		return _versionEntityInformation.getEntityClass();
	}

	public Function<Long, U> getVersionEntityFunction() {
		return _versionEntityInformation.getVersionEntityFunction();
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
	public Function<U, ? extends Serializable> getVersionFunction() {
		return _versionEntityInformation.getVersionFunction();
	}

	public static class BuilderImpl<T, U> implements Builder<T, U> {

		public BuilderImpl() {
			_changesetConfiguration = new ChangeTrackingConfigurationImpl<>();
		}

		public ResourceEntityStep<T, U> identifier(String identifier) {
			_changesetConfiguration._identifier = identifier;

			return new ResourceEntityStepImpl<>();
		}

		public class BaseliningStepImpl<T, U> implements BaseliningStep<T, U> {

			public IndexerStep baselining(
				Supplier<? extends Collection<U>> baseliningSupplier) {

				_changesetConfiguration._baseliningSuppliers.add(
					baseliningSupplier);

				return new IndexerStepImpl();
			}

		}

		public class BuildStepImpl implements BuildStep {

			@Override
			public ChangeTrackingConfiguration build() {
				return _changesetConfiguration;
			}

		}

		public class IndexerStepImpl implements IndexerStep {

			public BuildStep indexer(Function<Class, Indexer> indexerFunction) {
				_changesetConfiguration._versionEntityInformation.
					setIndexerFunction(indexerFunction);

				return new BuildStepImpl();
			}

		}

		public class ResourceEntityStepImpl<T, U>
			implements ResourceEntityStep<T, U> {

			public VersionEntityStep<T, U> addResourceEntity(
				Class<T> resourceEntityClass,
				Function<Long, T> resourceEntityFunction,
				Function<T, Serializable> resourceEntityIdFunction,
				Function<T, Serializable> versionEntityIdFunction,
				BaseLocalService resourceEntityLocalService) {

				_changesetConfiguration._resouceEntityInformation =
					new EntityInformation<>(
						resourceEntityClass, resourceEntityFunction,
						resourceEntityIdFunction, null, versionEntityIdFunction,
						resourceEntityLocalService, null, null);

				return new VersionEntityStepImpl<>();
			}

		}

		public class VersionEntityStepImpl<T, U>
			implements VersionEntityStep<T, U> {

			public BaseliningStep<T, U> addVersionEntity(
				Class<U> versionEntityClass,
				Function<U, Serializable> resourceEntityIdFunction,
				Function<Long, U> versionEntityFunction,
				Function<U, Serializable> versionEntityIdFunction,
				Function<U, ? extends Serializable>
					versionEntityVersionFunction,
				BaseLocalService versionEntityLocalService,
				Integer[] allowedStatuses,
				Function<U, Integer> statusFunction) {

				_changesetConfiguration._versionEntityInformation =
					new EntityInformation<>(
						versionEntityClass, null, resourceEntityIdFunction,
						versionEntityFunction, versionEntityIdFunction,
						versionEntityLocalService, allowedStatuses,
						statusFunction);

				_changesetConfiguration._versionEntityInformation.
					setVersionFunction(versionEntityVersionFunction);

				return new BaseliningStepImpl<>();
			}

		}

		private final ChangeTrackingConfigurationImpl _changesetConfiguration;

	}

	private ChangeTrackingConfigurationImpl() {
	}

	private final List<Supplier<? extends Collection<U>>> _baseliningSuppliers =
		new ArrayList<>();
	private String _identifier;
	private EntityInformation<T> _resouceEntityInformation;
	private EntityInformation<U> _versionEntityInformation;

	private static class EntityInformation<T> {

		public EntityInformation(
			Class<T> entityClass, Function<Long, T> resourceEntityFunction,
			Function<T, Serializable> resourceEntityIdFunction,
			Function<Long, T> versionEntityFunction,
			Function<T, Serializable> versionEntityIdFunction,
			BaseLocalService entityLocalService, Integer[] allowedStatuses,
			Function<T, Integer> statusFunction) {

			_class = entityClass;
			_resourceEntityFunction = resourceEntityFunction;
			_resourceIdFunction = resourceEntityIdFunction;
			_versionEntityFunction = versionEntityFunction;
			_versionIdFunction = versionEntityIdFunction;
			_baseLocalService = entityLocalService;
			_allowedStatuses = allowedStatuses;
			_statusFunction = statusFunction;
		}

		public Integer[] getAllowedStatuses() {
			return _allowedStatuses;
		}

		public BaseLocalService getBaseLocalService() {
			return _baseLocalService;
		}

		public Class<T> getEntityClass() {
			return _class;
		}

		public Function<Class<T>, Indexer<T>> getIndexerFunction() {
			return _indexerFunction;
		}

		public Function<Long, T> getResourceEntityFunction() {
			return _resourceEntityFunction;
		}

		public Function<T, Serializable> getResourceIdFunction() {
			return _resourceIdFunction;
		}

		public Function<T, Integer> getStatusFunction() {
			return _statusFunction;
		}

		public Function<Long, T> getVersionEntityFunction() {
			return _versionEntityFunction;
		}

		public Function<T, ? extends Serializable> getVersionFunction() {
			return _versionFunction;
		}

		public Function<T, Serializable> getVersionIdFunction() {
			return _versionIdFunction;
		}

		public void setIndexerFunction(
			Function<Class<T>, Indexer<T>> indexerFunction) {

			_indexerFunction = indexerFunction;
		}

		public void setVersionFunction(
			Function<T, ? extends Serializable> versionFunction) {

			_versionFunction = versionFunction;
		}

		private final Integer[] _allowedStatuses;
		private final BaseLocalService _baseLocalService;
		private final Class<T> _class;
		private Function<Class<T>, Indexer<T>> _indexerFunction;
		private final Function<Long, T> _resourceEntityFunction;
		private final Function<T, Serializable> _resourceIdFunction;
		private final Function<T, Integer> _statusFunction;
		private final Function<Long, T> _versionEntityFunction;
		private Function<T, ? extends Serializable> _versionFunction;
		private final Function<T, Serializable> _versionIdFunction;

	}

}