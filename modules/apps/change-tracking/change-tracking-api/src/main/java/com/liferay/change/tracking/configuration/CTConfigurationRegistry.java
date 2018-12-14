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

import java.util.Optional;

/**
 * @author Gergely Mathe
 */
@ProviderType
public interface CTConfigurationRegistry {

	/**
	 * Returns an Optional of the CTConfiguration registered for the given
	 * resource class
	 *
	 * @param clazz The entity's resource class
	 * @return An Optional of the CTConfiguration registered for the given
	 *         resource class
	 */
	public Optional<CTConfiguration<?, ?>>
		getCTConfigurationByResourceClass(Class<?> clazz);

	/**
	 * Returns an Optional of the CTConfiguration registered for the given
	 * resource class' name
	 *
	 * @param className The class name of the entity's resource class
	 * @return An Optional of the CTConfiguration registered for the
	 *         given resource class' name
	 */
	public Optional<CTConfiguration<?, ?>>
		getCTConfigurationByResourceClassName(String className);

	/**
	 * Returns an Optional of the CTConfiguration registered for the given
	 * version class
	 *
	 * @param clazz The entity's version class
	 * @return An Optional of the CTConfiguration registered for the given
	 *         version class
	 */
	public Optional<CTConfiguration<?, ?>>
		getCTConfigurationByVersionClass(Class<?> clazz);

	/**
	 * Returns an Optional of the CTConfiguration registered for the given
	 * version class' name
	 *
	 * @param className The class name of the entity's version class
	 * @return An Optional of the CTConfiguration registered for the
	 *         given version class' name
	 */
	public Optional<CTConfiguration<?, ?>>
		getCTConfigurationByVersionClassName(String className);

}