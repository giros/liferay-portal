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

package com.liferay.layout.prototype.service.permission;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.security.permission.PermissionUpdateHandler;
import com.liferay.portal.service.LayoutPrototypeLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {"model.class.name=com.liferay.portal.model.LayoutPrototype"},
	service = PermissionUpdateHandler.class
)
public class LayoutPrototypePermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		LayoutPrototype layoutPrototype =
			_layoutPrototypeLocalService.fetchLayoutPrototype(
				GetterUtil.getLong(primKey));

		if (layoutPrototype == null) {
			return;
		}

		layoutPrototype.setModifiedDate(new Date());

		_layoutPrototypeLocalService.updateLayoutPrototype(layoutPrototype);
	}

	@Reference
	protected void setLayoutPrototypeSetLocalService(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {

		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	private LayoutPrototypeLocalService _layoutPrototypeLocalService;

}