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

package com.liferay.portal.kernel.lar;

import com.liferay.portal.model.StagedModel;

import java.util.List;
import java.util.Map;

/**
 * @author Mate Thurzo
 */
public interface StagedModelRepository<T extends StagedModel> {

	public T addStagedModel(T stagedModel, Map<String, Object> attributes)
		throws Exception;

	public T updateStagedModel(
			T stagedModel, T existingStagedModel,
			Map<String, Object> attributes)
		throws Exception;

	public List<? extends StagedModel> fetchChildStagedModels(T stagedModel);

	public List<? extends StagedModel> fetchParentStagedModels(T stagedModel);

	public T fetchStagedModelByUuidAndCompanyId(String uuid, long companyId);

	public T fetchStagedModelByUuidAndGroupId(String uuid, long groupId);

	public T fetchStagedModelByCustomAttributes(Map<String, Object> attributes);

	public List<T> fetchStagedModels(long groupId);

	public List<T> fetchStagedModels(PortletDataContext portletDataContext);

}