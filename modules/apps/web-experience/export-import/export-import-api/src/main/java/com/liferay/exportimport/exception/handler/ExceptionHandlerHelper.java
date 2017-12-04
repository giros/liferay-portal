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

package com.liferay.exportimport.exception.handler;

import com.liferay.exportimport.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Zoltan Csaszi
 */
@Component(immediate = true, service = ExceptionHandlerHelper.class)
public class ExceptionHandlerHelper {

	public PortletDataException handleException(
		Exception e, int type, String portletId) {

		PortletDataException pde = null;

		if (e instanceof PortletDataException) {
			pde = (PortletDataException)e;
		}
		else {
			pde = new PortletDataException(e.getMessage(), e);
		}

		if (Validator.isNull(pde.getPortletId())) {
			pde.setPortletId(portletId);
		}

		if (pde.getType() != PortletDataException.DEFAULT) {
			return pde;
		}

		pde.setType(type);

		return pde;
	}

}