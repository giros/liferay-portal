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

import com.liferay.portal.kernel.adapter.ModelAdapterUtil;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.manifest.ManifestTreeNode;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.DateRange;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.kernel.zip.ZipWriterFactoryUtil;
import com.liferay.portal.model.ExportImportConfiguration;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.sitesadmin.lar.StagedGroup;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.collections.ServiceTrackerMapFactory;
import com.liferay.registry.collections.ServiceTrackerMapFactoryUtil;

import java.io.File;
import java.io.OutputStream;
import java.io.Serializable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class ExportImportController {

	public static ExportImportController getInstance() {
		return _instance;
	}

	public File export(ExportImportConfiguration exportImportConfiguration)
		throws PortalException {

		Map<String, Serializable> settingsMap =
			exportImportConfiguration.getSettingsMap();

		Map<String, String[]> parameterMap =
			(Map<String, String[]>)settingsMap.get("parameterMap");

		DateRange dateRange = ExportImportDateUtil.getDateRange(
			exportImportConfiguration,
			ExportImportDateUtil.RANGE_FROM_LAST_PUBLISH_DATE);

		final PortletDataContext portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				exportImportConfiguration.getCompanyId(),
				exportImportConfiguration.getGroupId(), parameterMap,
				dateRange.getStartDate(), dateRange.getEndDate(),
				ZipWriterFactoryUtil.getZipWriter());

		// TODO prepare phase

		// 1, building the graph

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getGroupId());

		StagedModel rootStagedModel = ModelAdapterUtil.adapt(
			group, StagedGroup.class);

		ManifestSummary manifestSummary = portletDataContext.prepareInContext(
			rootStagedModel);

		// 2, do the filtering

		doFiltering(portletDataContext, manifestSummary);

		// 3.a., sorting

		ManifestTreeNode[] sortedTreeNodes = sortManifestTreeNodes(
			manifestSummary);

		// 3.b., do the manifest

		String manifest = StringPool.BLANK;

		try {
			manifest = getManifest(portletDataContext, sortedTreeNodes);
		}
		catch (Exception e) {
			throw new PortletDataException(
				"Unable to generate the lar manifest!", e);
		}

		// 4. exporting

		if (Validator.isNotNull(manifest)) {
			portletDataContext.addZipEntry("/manifest.xml", manifest);

			export(portletDataContext, manifest);
		}

		// return

		ZipWriter zipWriter = portletDataContext.getZipWriter();

		return zipWriter.getFile();
	}

	public void export(PortletDataContext portletDataContext, String manifest)
		throws PortletDataException {

		Element rootElement = null;

		try {
			Document document = SAXReaderUtil.read(manifest);

			rootElement = document.getRootElement();
		}
		catch (Exception e) {
			throw new PortletDataException("Unable to export models!", e);
		}

		List<Element> stagedModelsElements = rootElement.elements(
			"stagedModels");

		for (Element stagedModelsElement : stagedModelsElements) {
			List<Element> stagedModelElements = stagedModelsElement.elements(
				"stagedModel");

			for (Element stagedModelElement : stagedModelElements) {
				if (GetterUtil.getBoolean(
						stagedModelElement.attributeValue("missing"))) {

					continue;
				}

				String className = stagedModelElement.attributeValue(
					"className");
				long classPK = GetterUtil.getLong(
					stagedModelElement.attributeValue("classPK"));

				StagedModel stagedModel = getStagedModel(className, classPK);

				if (stagedModel == null) {
					_log.warn(
						"Unable to export staged model with className= " +
							className + ", classPK=" + classPK);

					continue;
				}

				export(portletDataContext, stagedModel);
			}
		}
	}

	public void export(
			PortletDataContext portletDataContext, StagedModel stagedModel)
		throws PortletDataException {

		ModelStager modelStager = getModelStager(stagedModel);

		modelStager.exportStagedModel(portletDataContext, stagedModel);
	}

	protected void doFiltering(
		PortletDataContext portletDataContext,
		ManifestSummary manifestSummary) {

		ManifestTreeNode rootManifestTreeNode =
			manifestSummary.getRootManifestTreeNode();

		doFiltering(portletDataContext, rootManifestTreeNode);
	}

	protected void doFiltering(
		PortletDataContext portletDataContext,
		ManifestTreeNode manifestTreeNode) {

		StagedModel stagedModel = getStagedModel(
			manifestTreeNode.getClassName(), manifestTreeNode.getClassPK());

		manifestTreeNode.setMissing(
			ExportImportHelperUtil.isReferenceWithinExportScope(
				portletDataContext, stagedModel));

		for (ManifestTreeNode childManifestTreeNode :
				manifestTreeNode.getChildren()) {

			doFiltering(portletDataContext, childManifestTreeNode);
		}
	}

	protected String getManifest(
			PortletDataContext portletDataContext,
			ManifestTreeNode[] sortedTreeNodes)
		throws Exception {

		Document document = SAXReaderUtil.createDocument();
		Element rootElement = document.addElement("root");

		String label = null;
		Element labelElement = null;

		for (ManifestTreeNode manifestTreeNode : sortedTreeNodes) {
			String curLabel = String.valueOf(manifestTreeNode.getLabel());

			if (!curLabel.equals(label)) {
				label = curLabel;

				labelElement = SAXReaderUtil.createElement("stagedModels");
				labelElement.addAttribute("group", label);

				rootElement.add(labelElement);
			}

			Element manifestElement = SAXReaderUtil.createElement(
				"stagedModel");

			manifestElement.addAttribute(
				"label", String.valueOf(manifestTreeNode.getLabel()));
			manifestElement.addAttribute(
				"className", manifestTreeNode.getClassName());
			manifestElement.addAttribute(
				"classPK", String.valueOf(manifestTreeNode.getClassPK()));

			StagedModel stagedModel = getStagedModel(
				manifestTreeNode.getClassName(), manifestTreeNode.getClassPK());

			manifestElement.addAttribute(
				"path", ExportImportPathUtil.getModelPath(stagedModel));
			manifestElement.addAttribute("uuid", stagedModel.getUuid());

			boolean missing = false;

			if (!ExportImportHelperUtil.isReferenceWithinExportScope(
					portletDataContext, stagedModel)) {

				missing = true;
			}

			manifestElement.addAttribute("missing", String.valueOf(missing));

			labelElement.add(manifestElement);
		}

		return document.formattedString();
	}

	protected void buildHeap(ManifestTreeNode[] nodes) {
		int n = nodes.length - 1;

		for (int i = n/2; i >= 0; i--) {
			maxHeap(n, nodes, i);
		}
	}

	protected void exchange(
		ManifestTreeNode[] manifestTreeNodes, int i, int j) {

		ManifestTreeNode node = manifestTreeNodes[i];
		manifestTreeNodes[i] = manifestTreeNodes[j];
		manifestTreeNodes[j] = node;
	}

	protected ManifestTreeNode[] heapSort(
		Collection<ManifestTreeNode> manifestTreeNodes) {

		ManifestTreeNode[] nodesArray = manifestTreeNodes.toArray(
			new ManifestTreeNode[manifestTreeNodes.size()]);

		buildHeap(nodesArray);

		int maxIndex = nodesArray.length - 1;

		for (int i = maxIndex; i > 0; i--) {
			exchange(nodesArray, 0, i);
			maxIndex--;
			maxHeap(maxIndex, nodesArray, 0);
		}

		return nodesArray;
	}

	protected void maxHeap(int n, ManifestTreeNode[] manifestTreeNodes, int i) {
		int left = 2 * i;

		int right = left + 1;
		int largest = -1;

		if ((left <= n) &&
			(manifestTreeNodes[left].getLabel() >
				manifestTreeNodes[i].getLabel())) {

			largest = left;
		}
		else {
			largest = i;
		}

		if ((right <= n) &&
			(manifestTreeNodes[right].getLabel() >
				manifestTreeNodes[largest].getLabel())) {

			largest = right;
		}

		if (largest != i) {
			exchange(manifestTreeNodes, i, largest);
			maxHeap(n, manifestTreeNodes, largest);
		}
	}

	public static StagedModelRepository getStagedModelRepository(
		StagedModel stagedModel) {

		if (stagedModel == null) {
			return null;
		}

		return getStagedModelRepository(
			ExportImportClassedModelUtil.getClassName(stagedModel));
	}

	public static StagedModelRepository getStagedModelRepository(
		String className) {

		if (Validator.isNull(className)) {
			return null;
		}

		ServiceTrackerMap<String, StagedModelRepository> serviceTrackerMap =
			_serviceTrackerMapFactory.singleValueMap(
				StagedModelRepository.class, "model.name");

		serviceTrackerMap.open();

		try {
			return serviceTrackerMap.getService(className);
		}
		finally {
			serviceTrackerMap.close();
		}
	}

	public static ModelStager getModelStager(StagedModel stagedModel) {
		if (stagedModel == null) {
			return null;
		}

		ServiceTrackerMap<String, ModelStager> serviceTrackerMap =
			_serviceTrackerMapFactory.singleValueMap(
				ModelStager.class, "model.name");

		serviceTrackerMap.open();

		try {
			return serviceTrackerMap.getService(
				ExportImportClassedModelUtil.getClassName(stagedModel));
		}
		finally {
			serviceTrackerMap.close();
		}
	}

	protected StagedModel getStagedModel(String className, long classPK) {
		if (Validator.isNull(className) || (classPK == 0)) {
			return null;
		}

		StagedModelRepository stagedModelRepository = getStagedModelRepository(
			className);

		if (stagedModelRepository != null) {
			return stagedModelRepository.fetchStagedModel(classPK);
		}

		return null;
	}

	protected ManifestTreeNode[] sortManifestTreeNodes(
		ManifestSummary manifestSummary) {

		ManifestTreeNode rootManifestTreeNode =
			manifestSummary.getRootManifestTreeNode();

		return heapSort(topologicalSort(rootManifestTreeNode));
	}

	protected List<ManifestTreeNode> topologicalSort(
		ManifestTreeNode rootNode) {

		Stack<ManifestTreeNode> sortedNodes = new Stack<>();
		Set<ManifestTreeNode> markedNodes = new HashSet<>();
		Set<ManifestTreeNode> tempMarkedNodes = new HashSet<>();

		visit(tempMarkedNodes, markedNodes, sortedNodes, rootNode, 0);

		return sortedNodes;
	}

	protected void visit(
		Set<ManifestTreeNode> tempMarkedNodes,
		Set<ManifestTreeNode> markedNodes, Stack<ManifestTreeNode> sortedNodes,
		ManifestTreeNode manifestTreeNode, int depth) {

		if (tempMarkedNodes.contains(manifestTreeNode)) {
			throw new SystemException("Not a DAG");
		}

		tempMarkedNodes.add(manifestTreeNode);

		if (markedNodes.contains(manifestTreeNode)) {
			return;
		}

		manifestTreeNode.setLabel(depth++);

		for (ManifestTreeNode dependentNode : manifestTreeNode.getChildren()) {
			visit(
				tempMarkedNodes, markedNodes, sortedNodes, dependentNode,
				depth);
		}

		markedNodes.add(manifestTreeNode);
		tempMarkedNodes.remove(manifestTreeNode);
		sortedNodes.push(manifestTreeNode);
	}

	private ExportImportController() {
	}

	private static final ExportImportController _instance =
		new ExportImportController();
	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportController.class);
	private static final ServiceTrackerMapFactory _serviceTrackerMapFactory =
		ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();


}