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

package com.liferay.exportimport.kernel.lar.file;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.StagedModel;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author Mate Thurzo
 */
@ProviderType
public interface LARFile {

	//public void addStagedModel(StagedModel stagedModel);

	public void endReadPortletData();

	public void endWriteCustom(String name);

	public void endWritePortletData(String portletId);

	public void endWriteReference();

	public void endWriteStagedModel(StagedModel stagedModel);

	public StagedModel getNextStagedModel();

	public boolean hasNextStagedModel();

	public String readPortletDataAttribute(String name);

	public String readStagedModelAttribute(String name);

	public Map<String, String> readStagedModelAttributes();

	public void readStagedModels();

	public void registerStagedModelConsumer(
		String className, Consumer<StagedModel> consumer);

	public void registerStagedModelFilter(
		String className, Predicate<?> filter);

	public void registerStagedModelPostAction(
		String className, Function<?, ?> action);

	public void registerStagedModelPreAction(
		String className, Function<?, ?> action);

	public void startReadPortletData(String data);

	public void startWriteCustom(String name);

	public void startWritePortletData(String portletId);

	public void startWriteReference();

	public void startWriteStagedModel(StagedModel stagedModel);

	public void writeCustomAttribute(
		String name, String attributeName, String attributeValue);

	public void writePortletDataAttribute(String name, String value);

	public void writePropertyElement(String userUuid, String key, String value);

	public void writeReferenceAttribute(String name, String value);

	public void writeReferenceStagedModel(
		StagedModel referrerStagedModel, StagedModel stagedModel,
		String referenceType, boolean missing);

	public void writeStagedModelAttribute(String name, String value);

	public void writeStructureFieldsElement(
		String ddmFormValuesPath, String structureUuid);

}