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

package com.liferay.change.tracking.engine.configuration;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.service.BaseLocalService;

import java.io.Serializable;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Mate Thurzo
 */
@ProviderType
public interface ChangeTrackingConfiguration<T, U> {

	public Integer[] getAllowedStatuses();

	public List<Supplier<? extends Collection<U>>> getBaselining();

	public String getIdentifier();

	public Indexer getIndexer();

	public Class<T> getResourceEntityClass();

	public Function<Long, T> getResourceEntityFunction();

	public Function<T, Serializable>
		getResourceEntityIdFromResourceEntityFunction();

	public Function<U, Serializable>
		getResourceEntityIdFromVersionEntityFunction();

	public Function<U, Integer> getStatusFunction();

	public Class<U> getVersionEntityClass();

	public Function<Long, U> getVersionEntityFunction();

	public Function<T, Serializable>
		getVersionEntityIdFromResourceEntityFunction();

	public Function<U, Serializable>
		getVersionEntityIdFromVersionEntityFunction();

	public Function<U, ? extends Serializable> getVersionFunction();

	public interface BaseliningStep<T, U> {

		public IndexerStep baselining(
			Supplier<? extends Collection<U>> baseliningSupplier);

	}

	public interface Builder<T, U> {

		public ResourceEntityStep<T, U> identifier(String identifier);

	}

	public interface BuildStep {

		public ChangeTrackingConfiguration build();

	}

	public interface IndexerStep {

		public BuildStep indexer(Function<Class, Indexer> indexerFunction);

	}

	public interface ResourceEntityStep<T, U> {

		public VersionEntityStep<T, U> addResourceEntity(
			Class<T> resourceEntityClass,
			Function<Long, T> resourceEntityFunction,
			Function<T, Serializable> resourceEntityIdFunction,
			Function<T, Serializable> versionEntityIdFunction,
			BaseLocalService resourceEntityLocalService);

	}

	public interface VersionEntityStep<T, U> {

		public BaseliningStep<T, U> addVersionEntity(
			Class<U> versionEntityClass,
			Function<U, Serializable> resourceEntityIdFunction,
			Function<Long, U> versionEntityFunction,
			Function<U, Serializable> versionEntityIdFunction,
			Function<U, ? extends Serializable> versionEntityVersionFunction,
			BaseLocalService versionEntityLocalService,
			Integer[] allowedStatuses, Function<U, Integer> statusFunction);

	}

}