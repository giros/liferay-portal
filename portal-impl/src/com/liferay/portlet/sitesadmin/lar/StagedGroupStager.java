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

package com.liferay.portlet.sitesadmin.lar;

import com.liferay.portal.kernel.lar.BaseModelStager;
import com.liferay.portal.kernel.lar.ModelStager;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;

import java.io.OutputStream;

/**
 * @author Daniel Kocsis
 */
@OSGiBeanProperties(
	property = {"model.name=com.liferay.portlet.sitesadmin.lar.StagedGroup"}
)
public class StagedGroupStager extends BaseModelStager<StagedGroup> {

	@Override
	protected void doExportStagedModel(
			StagedGroup stagedGroup, OutputStream outputStream)
		throws Exception {

		super.doExportStagedModel(stagedGroup, outputStream);
	}

}