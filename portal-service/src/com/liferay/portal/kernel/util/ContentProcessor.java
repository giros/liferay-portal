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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.StagedModel;
import com.liferay.portlet.exportimport.lar.PortletDataContext;

import java.util.regex.Pattern;

/**
 * @author Gergely Mathe
 */
public interface ContentProcessor {

	public static final String DATA_HANDLER_COMPANY_SECURE_URL =
		"@data_handler_company_secure_url@";

	public static final String DATA_HANDLER_COMPANY_URL =
		"@data_handler_company_url@";

	public static final String DATA_HANDLER_GROUP_FRIENDLY_URL =
		"@data_handler_group_friendly_url@";

	public static final String DATA_HANDLER_PATH_CONTEXT =
		"@data_handler_path_context@";

	public static final String DATA_HANDLER_PRIVATE_GROUP_SERVLET_MAPPING =
		"@data_handler_private_group_servlet_mapping@";

	public static final String DATA_HANDLER_PRIVATE_LAYOUT_SET_SECURE_URL =
		"@data_handler_private_layout_set_secure_url@";

	public static final String DATA_HANDLER_PRIVATE_LAYOUT_SET_URL =
		"@data_handler_private_layout_set_url@";

	public static final String DATA_HANDLER_PRIVATE_USER_SERVLET_MAPPING =
		"@data_handler_private_user_servlet_mapping@";

	public static final String DATA_HANDLER_PUBLIC_LAYOUT_SET_SECURE_URL =
		"@data_handler_public_layout_set_secure_url@";

	public static final String DATA_HANDLER_PUBLIC_LAYOUT_SET_URL =
		"@data_handler_public_layout_set_url@";

	public static final String DATA_HANDLER_PUBLIC_SERVLET_MAPPING =
		"@data_handler_public_servlet_mapping@";

	public static final char[] DL_REFERENCE_LEGACY_STOP_CHARS = {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.QUOTE, CharPool.SPACE
	};

	public static final char[] DL_REFERENCE_STOP_CHARS = {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.QUESTION, CharPool.QUOTE, CharPool.SPACE
	};

	public static final char[] LAYOUT_REFERENCE_STOP_CHARS = {
		CharPool.APOSTROPHE, CharPool.CLOSE_BRACKET, CharPool.CLOSE_CURLY_BRACE,
		CharPool.CLOSE_PARENTHESIS, CharPool.GREATER_THAN, CharPool.LESS_THAN,
		CharPool.PIPE, CharPool.QUESTION, CharPool.QUOTE, CharPool.SPACE
	};

	public static final String PRIVATE_GROUP_SERVLET_MAPPING =
		PropsUtil.get(
			PropsKeys.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING) +
			StringPool.SLASH;

	public static final String PRIVATE_USER_SERVLET_MAPPING =
		PropsUtil.get(
			PropsKeys.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING) +
			StringPool.SLASH;

	public static final String PUBLIC_GROUP_SERVLET_MAPPING =
		PropsUtil.get(
			PropsKeys.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING) +
			StringPool.SLASH;

	public static final Pattern exportLinksToLayoutPattern = Pattern.compile(
		"\\[([\\d]+)@(private(-group|-user)?|public)(@([\\d]+))?\\]");
	public static final Pattern importLinksToLayoutPattern = Pattern.compile(
		"\\[([\\d]+)@(private(-group|-user)?|public)@([\\d]+)(@([\\d]+))?\\]");

	public String replaceExportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content,
			boolean exportReferencedContent, boolean escapeContent)
		throws Exception;

	public String replaceImportContentReferences(
			PortletDataContext portletDataContext,
			StagedModel entityStagedModel, String content)
		throws Exception;

	public void validateContentReferences(
			PortletDataContext portletDataContext, String content)
		throws Exception;

}