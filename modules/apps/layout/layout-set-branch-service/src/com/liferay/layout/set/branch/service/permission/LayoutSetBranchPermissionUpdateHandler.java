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

package com.liferay.layout.set.branch.service.permission;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.LayoutSetBranch;
import com.liferay.portal.security.permission.PermissionUpdateHandler;
import com.liferay.portal.service.LayoutSetBranchLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {"model.class.name=com.liferay.portal.model.LayoutSetBranch"},
	service = PermissionUpdateHandler.class
)
public class LayoutSetBranchPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		LayoutSetBranch layoutSetBranch =
			_layoutSetBranchLocalService.fetchLayoutSetBranch(
				GetterUtil.getLong(primKey));

		if (layoutSetBranch == null) {
			return;
		}

		layoutSetBranch.setModifiedDate(new Date());

		_layoutSetBranchLocalService.updateLayoutSetBranch(layoutSetBranch);
	}

	@Reference
	protected void setLayoutSetBranchSetLocalService(
		LayoutSetBranchLocalService layoutSetBranchLocalService) {

		_layoutSetBranchLocalService = layoutSetBranchLocalService;
	}

	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

}