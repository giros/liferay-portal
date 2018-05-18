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

package com.liferay.document.library.internal.exportimport.data.handler;

import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.data.handler.base.BaseStagedModelDataHandler;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileVersion;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.Map;

/**
 * @author Gergely Mathe
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class FileVersionStagedModelDataHandler
	extends BaseStagedModelDataHandler<FileVersion> {

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, FileVersion fileVersion)
		throws Exception {

		Element fileVersionElement = portletDataContext.getExportDataElement(
			fileVersion);

		String fileVersionPath = ExportImportPathUtil.getModelPath(fileVersion);

		LiferayFileVersion liferayFileVersion = (LiferayFileVersion)fileVersion;

		portletDataContext.addClassedModel(
			fileVersionElement, fileVersionPath, liferayFileVersion,
			DLFileVersion.class);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, FileVersion fileVersion)
		throws Exception {

		Element fileVersionElement = portletDataContext.getImportDataElement(
			fileVersion);

		Map<Long, Long> fileVersionIds =
			(Map<Long, Long>)portletDataContext.getNewPrimaryKeysMap(
				FileVersion.class);

		long fileVersionId = MapUtil.getLong(
			fileVersionIds, fileVersion.getFileVersionId());

		if (fileVersionId > 0) {
			FileVersion existingFileVersion = _dlAppLocalService.getFileVersion(
				fileVersionId);


		}

	}

	@Reference(unbind = "-")
	protected void setDLAppLocalService(DLAppLocalService dlAppLocalService) {
		_dlAppLocalService = dlAppLocalService;
	}

	private DLAppLocalService _dlAppLocalService;

}
