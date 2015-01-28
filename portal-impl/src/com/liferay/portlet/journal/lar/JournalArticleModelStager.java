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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.lar.BaseModelStager;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portlet.journal.model.JournalArticle;

import java.io.InputStream;
import java.io.OutputStream;

import org.osgi.service.component.annotations.Reference;

/**
 * @author Mate Thurzo
 */
@OSGiBeanProperties(
	property = {"model.name=com.liferay.portlet.journal.model.JournalArticle"})
public class JournalArticleModelStager extends BaseModelStager<JournalArticle> {

	@Reference(unbind = "-")
	public void setJournalArticleStagedModelRepository(
		JournalArticleStagedModelRepository
			journalArticleStagedModelRepository) {

		_journalArticleStagedModelRepository =
			journalArticleStagedModelRepository;
	}

	@Override
	protected void doExportStagedModel(
			JournalArticle stagedModel, OutputStream outputStream)
		throws Exception {

		super.doExportStagedModel(stagedModel, outputStream);
	}

	@Override
	protected JournalArticle doImportStagedModel(InputStream inputStream)
		throws Exception {

		return null;
	}

	private JournalArticleStagedModelRepository
		_journalArticleStagedModelRepository;

}