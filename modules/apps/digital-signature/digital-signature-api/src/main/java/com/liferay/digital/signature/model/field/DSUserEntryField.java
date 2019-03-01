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

package com.liferay.digital.signature.model.field;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface DSUserEntryField<T extends DSField> extends DSStyledField<T> {

	public Boolean getConcealValue();

	public Boolean getDisableAutoSize();

	public Integer getMaxLength();

	public String getOriginalValue();

	public Boolean getRequireInitialOnSharedChange();

	public String getValidationMessage();

	public String getValidationRegex();

	public String getValue();

}