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

package com.liferay.journal.model.adapter;

import java.io.File;
import java.util.Map;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.StagedModel;

/**
 * @author Gergely Mathe
 */
public interface StagedJournalArticle extends JournalArticle, StagedModel {

	public String getArticleResourceUuid();

	public boolean getAutoArticleId();
	
	public Map<String, byte[]> getImages();

	public boolean getPreloaded();

	public File getSmallFile();

	public void setArticleResourceUuid(String articleResourceUuid);

	public void setAutoArticleId(boolean autoArticleId);

	public void setImage(Map<String, byte[]> images);

	public void setPreloaded(boolean preloaded);

	public void setSmallFile(File smallFile);

}
