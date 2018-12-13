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

import java.io.Serializable;

import java.util.function.Function;

/**
 * @author Gergely Mathe
 */
public interface ChangeTrackingConfigurationExtended<T, U>
	extends ChangeTrackingConfiguration<T, U> {

	public void setResourceEntityByResourceEntityIdFunction(
		Function<Long, T> resourceEntityByResourceEntityIdFunction);

	public void setResourceEntityClass(Class<T> resourceEntityClass);

	public void setResourceEntityIdFromResourceEntityFunction(
		Function<T, Serializable> resourceEntityIdFromResourceEntityFunction);

	public void setResourceEntityIdFromVersionEntityFunction(
		Function<U, Serializable> resourceEntityIdFromVersionEntityFunction);

	public void setVersionEntityAllowedStatuses(Integer[] allowedStatuses);

	public void setVersionEntityByVersionEntityIdFunction(
		Function<Long, U> versionEntityByVersionEntityIdFunction);

	public void setVersionEntityClass(Class<U> versionEntityClass);

	public void setVersionEntityIdFromResourceEntityFunction(
		Function<T, Serializable> versionEntityIdFromResourceEntityFunction);

	public void setVersionEntityIdFromVersionEntityFunction(
		Function<U, Serializable> versionEntityIdFromVersionEntityFunction);

	public void setVersionEntityStatusFunction(
		Function<U, Integer> versionEntityStatusFunction);

}