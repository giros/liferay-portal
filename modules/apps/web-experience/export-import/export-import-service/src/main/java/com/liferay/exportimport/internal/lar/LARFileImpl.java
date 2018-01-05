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

package com.liferay.exportimport.internal.lar;

import com.liferay.exportimport.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.file.LARFile;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.NoSuchLayoutException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.AttachedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.StagedGroupedModel;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.zip.ZipWriter;

import java.io.OutputStream;
import java.io.StringReader;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import jodd.bean.BeanUtil;

/**
 * @author Mate Thurzo
 */
public class LARFileImpl implements LARFile {

	public LARFileImpl(PortletDataContext portletDataContext) {
		_portletDataContext = portletDataContext;
	}

	@Override
	public void endReadPortletData() {
	}

	@Override
	public void endWriteCustom(String name) {
		try {

			// check event stack

			_xmlStreamWriter.writeEndElement();

			// push event to stack

		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	@Override
	public void endWritePortletData(String portletId) {
		if (_isWriteEventCompleted(_LAR_EVENT_PORTLET_DATA_START)) {
			try {
				_xmlStreamWriter.writeEndElement();

				_xmlStreamWriter.flush();

				_xmlStreamWriter.close();

				_eventCompleted(_LAR_EVENT_PORTLET_DATA_END, portletId);

				_outputStream.flush();
				_outputStream.close();

				_portletDataContext.setStreamProcessSupport(false);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void endWriteReference() {
		if (_isWriteEventCompleted(_LAR_EVENT_REFERENCE_START)) {
			try {
				_xmlStreamWriter.writeEndElement();

				//closeReferenceXMLWriter();

				_eventCompleted(_LAR_EVENT_REFERENCE_END, "ref");
				_writeEventReset(_LAR_EVENT_REFERENCE_START);
			}
			catch (Exception e) {
				e.printStackTrace();

				_writeEventReset(_LAR_EVENT_REFERENCE_END);
			}
		}
	}

	@Override
	public void endWriteStagedModel(StagedModel stagedModel) {
		if (_isWriteEventCompleted(_LAR_EVENT_STAGED_MODEL_START)) {
			try {
				_xmlStreamWriter.writeEndElement();

				_eventCompleted(
					_LAR_EVENT_STAGED_MODEL_END, stagedModel.getUuid());
				_writeEventReset(_LAR_EVENT_STAGED_MODEL_START);
			}
			catch (Exception e) {
				e.printStackTrace();

				_writeEventReset(_LAR_EVENT_STAGED_MODEL_END);
			}
		}
	}

	@Override
	public StagedModel getNextStagedModel() {
		return null;
	}

	@Override
	public boolean hasNextStagedModel() {
		return false;
	}

	@Override
	public String readPortletDataAttribute(String name) {
		return null;
	}

	@Override
	public String readStagedModelAttribute(String name) {
		return null;
	}

	@Override
	public Map<String, String> readStagedModelAttributes() {
		return null;
	}

	@Override
	public void readStagedModels() {
		try {
			while (_xmlStreamReader.hasNext()) {
				_xmlStreamReader.next();

				int eventType = _xmlStreamReader.getEventType();

				if (eventType == XMLStreamConstants.START_ELEMENT) {
					if ("staged-model".equals(
						_xmlStreamReader.getLocalName())) {

						String path = _xmlStreamReader.getAttributeValue(
							null, "path");

						StagedModel stagedModel =
							(StagedModel)
								_portletDataContext.getZipEntryAsObject(
									path);

						String classNameAttribute =
							_xmlStreamReader.getAttributeValue(
								null, "attached-class-name");

						if (Validator.isNotNull(classNameAttribute)) {
							BeanPropertiesUtil.setProperty(
								stagedModel, "className", classNameAttribute);
							BeanPropertiesUtil.setProperty(
								stagedModel, "classNameId",
								PortalUtil.getClassNameId(classNameAttribute));
						}

						StagedModelDataHandlerUtil.importStagedModel(
							_portletDataContext, stagedModel);
					}
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void registerStagedModelConsumer(
		String className, Consumer<StagedModel> consumer) {
	}

	@Override
	public void registerStagedModelFilter(
		String className, Predicate<?> filter) {
	}

	@Override
	public void registerStagedModelPostAction(
		String className, Function<?, ?> action) {
	}

	@Override
	public void registerStagedModelPreAction(
		String className, Function<?, ?> action) {
	}

	@Override
	public void startReadPortletData(String data) {
		try {
			XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

			_xmlStreamReader = xmlInputFactory.createXMLStreamReader(
				new StringReader(data));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startWriteCustom(String name) {
		try {
			_xmlStreamWriter.writeStartElement(name);

			// push event to stack

		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	@Override
	public void startWritePortletData(String portletId) {
		try {
			XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

			ZipWriter zipWriter = _portletDataContext.getZipWriter();

			_outputStream = zipWriter.addEntry(
				ExportImportPathUtil.getPortletDataPath(_portletDataContext));

			_xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(
				_outputStream);

			_xmlStreamWriter.writeStartDocument();

			_xmlStreamWriter.writeStartElement(portletId);

			_portletDataContext.setStreamProcessSupport(true);
		}
		catch (Exception e) {
			_writeEventReset(_LAR_EVENT_PORTLET_DATA_START);

			e.printStackTrace();
		}
	}

	@Override
	public void startWriteReference() {
		if (!_isWriteEventCompleted(_LAR_EVENT_STAGED_MODEL_START)) {
			return;
		}

		if (!_isWriteEventCompleted(_LAR_EVENT_REFERENCE_START)) {
			try {
				_xmlStreamWriter.writeStartElement("references");

				_eventCompleted(_LAR_EVENT_REFERENCE_START, "ref");
				_writeEventReset(_LAR_EVENT_REFERENCE_END);
			}
			catch (Exception e) {
				e.printStackTrace();

				_writeEventReset(_LAR_EVENT_REFERENCE_START);
			}
		}
	}

	@Override
	public void startWriteStagedModel(StagedModel stagedModel) {
		if (!_isWriteEventCompleted(_LAR_EVENT_PORTLET_DATA_START)) {
			return;
		}

		try {
			_xmlStreamWriter.writeStartElement("staged-model");

			_xmlStreamWriter.writeAttribute("uuid", stagedModel.getUuid());

			if (stagedModel instanceof StagedGroupedModel) {
				StagedGroupedModel stagedGroupedModel =
					(StagedGroupedModel)stagedModel;

				_xmlStreamWriter.writeAttribute(
					"group-id",
					String.valueOf(stagedGroupedModel.getGroupId()));
			}

			_eventCompleted(
				_LAR_EVENT_STAGED_MODEL_START, stagedModel.getUuid());
		}
		catch (Exception e) {
			e.printStackTrace();

			_writeEventReset(_LAR_EVENT_STAGED_MODEL_START);
		}
	}

	@Override
	public void writeCustomAttribute(
		String name, String attributeName, String attributeValue) {

		try {

			// check event stack

			_xmlStreamWriter.writeAttribute(attributeName, attributeValue);

			// push event to stack

		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	@Override
	public void writePortletDataAttribute(String name, String value) {
		if (_isWriteEventCompleted(_LAR_EVENT_PORTLET_DATA_START)) {
			try {
				_xmlStreamWriter.writeAttribute(name, value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void writePropertyElement(
		String userUuid, String key, String value) {

		endWriteReference();

		try {
			_xmlStreamWriter.writeStartElement("property");
			_xmlStreamWriter.writeAttribute("userUuid", userUuid);
			_xmlStreamWriter.writeAttribute("key", key);
			_xmlStreamWriter.writeAttribute("value", value);
			_xmlStreamWriter.writeEndElement();
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	@Override
	public void writeReferenceAttribute(String name, String value) {
		if (_isWriteEventCompleted(_LAR_EVENT_REFERENCE_START)) {
			try {
				_xmlStreamWriter.writeAttribute(name, value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void writeReferenceStagedModel(
		StagedModel referrerStagedModel, StagedModel stagedModel,
		String referenceType, boolean missing) {

		if (!_isWriteEventCompleted(_LAR_EVENT_REFERENCE_START)) {
			return;
		}

		try {
			_xmlStreamWriter.writeStartElement("reference");

			String className = ExportImportClassedModelUtil.getClassName(
				stagedModel);

			_xmlStreamWriter.writeAttribute("class-name", className);

			_xmlStreamWriter.writeAttribute(
				"class-pk", String.valueOf(stagedModel.getPrimaryKeyObj()));

			if (stagedModel instanceof StagedGroupedModel) {
				StagedGroupedModel stagedGroupedModel =
					(StagedGroupedModel)stagedModel;

				_xmlStreamWriter.writeAttribute(
					"group-id",
					String.valueOf(stagedGroupedModel.getGroupId()));

				try {
					Group group = GroupLocalServiceUtil.getGroup(
						stagedGroupedModel.getGroupId());

					long liveGroupId = group.getLiveGroupId();

					if (group.isStagedRemotely()) {
						liveGroupId = group.getRemoteLiveGroupId();
					}

					if (liveGroupId == GroupConstants.DEFAULT_LIVE_GROUP_ID) {
						liveGroupId = group.getGroupId();
					}

					_xmlStreamWriter.writeAttribute(
						"live-group-id", String.valueOf(liveGroupId));

					if (group.isLayout()) {
						try {
							Layout scopeLayout =
								LayoutLocalServiceUtil.getLayout(
									group.getClassPK());

							_xmlStreamWriter.writeAttribute(
								"scope-layout-uuid", scopeLayout.getUuid());
						}
						catch (NoSuchLayoutException nsle) {
							_log.error(nsle.getMessage(), nsle);
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}

				_xmlStreamWriter.writeAttribute("type", referenceType);

				_xmlStreamWriter.writeAttribute("uuid", stagedModel.getUuid());
				_xmlStreamWriter.writeAttribute(
					"company-id", String.valueOf(stagedModel.getCompanyId()));

				Map<String, String> referenceAttributes =
					StagedModelDataHandlerUtil.getReferenceAttributes(
						_portletDataContext, stagedModel);

				for (Map.Entry<String, String> referenceAttribute :
						referenceAttributes.entrySet()) {

					_xmlStreamWriter.writeAttribute(
						referenceAttribute.getKey(),
						referenceAttribute.getValue());
				}
			}

			_xmlStreamWriter.writeEndElement();
		}
		catch (Exception e) {
			_log.error(e.getMessage(), e);
		}
	}

	@Override
	public void writeStagedModelAttribute(String name, String value) {
		if (_isWriteEventCompleted(_LAR_EVENT_STAGED_MODEL_START)) {
			try {
				_xmlStreamWriter.writeAttribute(name, value);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void writeStructureFieldsElement(
		String ddmFormValuesPath, String structureUuid) {

		if (_isWriteEventCompleted(_LAR_EVENT_STAGED_MODEL_START)) {
			try {
				_xmlStreamWriter.writeStartElement("structure-fields");
				_xmlStreamWriter.writeAttribute(
					"ddmFormValuesPath", ddmFormValuesPath);
				_xmlStreamWriter.writeAttribute("structureUuid", structureUuid);
				_xmlStreamWriter.writeEndElement();
			}
			catch (Exception e) {
				_log.error(e.getMessage(), e);
			}
		}
	}

	private void _closeReferenceXMLWriter() {
		if (_referenceXmlStreamWriter == null) {
			return;
		}

		try {
			_referenceXmlStreamWriter.writeEndDocument();

			_referenceXmlStreamWriter.flush();

			_referenceXmlStreamWriter.close();

			_referenceXmlStreamWriter = null;

			_referenceOutputStream.flush();
			_referenceOutputStream.close();
		}
		catch (Exception e) {
			_log.error("Error during closing reference xml writer: ", e);
		}
	}

	private void _eventCompleted(int event, String name) {
		_eventStack.push(new LAREvent(event, name));
	}

	private void _initReferenceXmlStreamWriter(
		StagedModel referrerStagedModel) {

		XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();

		ZipWriter zipWriter = _portletDataContext.getZipWriter();

		String path = ExportImportPathUtil.getModelPath(
			referrerStagedModel, "ref");

		try {
			_referenceOutputStream = zipWriter.addEntry(path);

			_referenceXmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(
				_referenceOutputStream);

			_referenceXmlStreamWriter.writeStartDocument();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean _isWriteEventCompleted(int event) {
		return true;
	}

	private void _populateClassNameAttribute(StagedModel stagedModel) {
		String attachedClassName = null;

		if (stagedModel instanceof AttachedModel) {
			AttachedModel attachedModel = (AttachedModel)stagedModel;

			attachedClassName = attachedModel.getClassName();
		}
		else if (BeanUtil.hasProperty(stagedModel, "className")) {
			attachedClassName = BeanPropertiesUtil.getStringSilent(
				stagedModel, "className");
		}

		if (Validator.isNotNull(attachedClassName)) {
			try {
				_xmlStreamWriter.writeAttribute(
					"attached-class-portletId", attachedClassName);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void _writeEventReset(int event) {
		_writeEventArray[event] = false;
	}

	private static final int _LAR_EVENT_CUSTOM_END = 2;

	private static final int _LAR_EVENT_CUSTOM_START = 1;

	private static final int _LAR_EVENT_PORTLET_DATA_END = 4;

	private static final int _LAR_EVENT_PORTLET_DATA_START = 3;

	private static final int _LAR_EVENT_REFERENCE_END = 8;

	private static final int _LAR_EVENT_REFERENCE_START = 7;

	private static final int _LAR_EVENT_STAGED_MODEL_END = 6;

	private static final int _LAR_EVENT_STAGED_MODEL_START = 5;

	private static final Log _log = LogFactoryUtil.getLog(LARFileImpl.class);

	private Deque<LAREvent> _eventStack = new ArrayDeque<>();
	private OutputStream _outputStream;
	private final PortletDataContext _portletDataContext;
	private OutputStream _referenceOutputStream;
	private XMLStreamWriter _referenceXmlStreamWriter;
	private boolean[] _writeEventArray = new boolean[6];
	private XMLStreamReader _xmlStreamReader;
	private XMLStreamWriter _xmlStreamWriter;

	private class LAREvent {

		public LAREvent(int code, String name) {
			_code = code;
			_name = name;
		}

		private final int _code;
		private final String _name;

	}

}