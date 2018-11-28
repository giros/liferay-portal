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

package com.liferay.change.tracking.configuration;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.BaseLocalService;

import java.io.Serializable;

import java.util.function.Function;

/**
 * @author Mate Thurzo
 */
@ProviderType
public interface ChangeTrackingConfiguration<T, U> {

	public Function<Long, T> getResourceEntityByResourceEntityIdFunction();

	public Class<T> getResourceEntityClass();

	public Function<T, Serializable>
		getResourceEntityIdFromResourceEntityFunction();

	public Function<U, Serializable>
		getResourceEntityIdFromVersionEntityFunction();

	public Integer[] getVersionEntityAllowedStatuses();

	public Function<Long, U> getVersionEntityByVersionEntityIdFunction();

	public Class<U> getVersionEntityClass();

	public Function<T, Serializable>
		getVersionEntityIdFromResourceEntityFunction();

	public Function<U, Serializable>
		getVersionEntityIdFromVersionEntityFunction();

	public Function<U, Integer> getVersionEntityStatusFunction();

	public interface Builder<T, U> {

		public VersionEntityStep<T, U> addResourceEntity(
			Class<T> resourceEntityClass,
			Function<Long, T> resourceEntityByResourceEntityIdFunction,
			Function<T, Serializable>
				resourceEntityIdFromResourceEntityFunction,
			Function<T, Serializable> versionEntityIdFromResourceEntityFunction,
			BaseLocalService resourceEntityLocalService);

	}

	public interface BuildStep {

		public ChangeTrackingConfiguration build();

	}

	public interface VersionEntityStep<T, U> {

		public BuildStep addVersionEntity(
			Class<U> versionEntityClass,
			Function<Long, U> versionEntityByVersionEntityIdFunction,
			Function<U, Serializable> resourceEntityIdFromVersionEntityFunction,
			Function<U, Serializable> versionEntityIdFromVersionEntityFunction,
			BaseLocalService versionEntityLocalService,
			Integer[] versionEntityAllowedStatuses,
			Function<U, Integer> versionEntityStatusFunction);

	}

}