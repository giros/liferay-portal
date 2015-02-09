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

package com.liferay.portal.kernel.lar.manifest;

import com.liferay.portal.kernel.lar.ExportImportClassedModelUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.StagedModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mate Thurzo
 * @author Daniel Kocsis
 */
public class ManifestTreeNode {

	public ManifestTreeNode(StagedModel stagedModel) {
		_className = ExportImportClassedModelUtil.getClassName(stagedModel);
		_classPK = ExportImportClassedModelUtil.getClassPK(stagedModel);
	}

	public ManifestTreeNode(String className, long classPK) {
		_className = className;
		_classPK = classPK;
	}

	public void addChild(ManifestTreeNode manifestTreeNode) {
		if (manifestTreeNode == null) {
			return;
		}

		_children.add(manifestTreeNode);
	}

	public List<ManifestTreeNode> getChildren() {
		return _children;
	}

	public String getClassName() {
		return _className;
	}

	public long getClassPK() {
		return _classPK;
	}

	public int getLabel() {
		return _label;
	}

	public List<ManifestTreeNode> getParents() {
		return _parents;
	}

	public boolean hasParent() {
		return !_parents.isEmpty();
	}

	public boolean missing() {
		return _missing;
	}

	public void setLabel(int label) {
		_label = label;
	}

	public void setMissing(boolean missing) {
		_missing = missing;
	}

	public void addParent(ManifestTreeNode manifestTreeNode) {
		if (manifestTreeNode == null) {
			return;
		}

		_parents.add(manifestTreeNode);
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ManifestTreeNode)) {
			return false;
		}

		ManifestTreeNode manifestTreeNode = (ManifestTreeNode)obj;

		if (!Validator.equals(_className, manifestTreeNode.getClassName())) {
			return false;
		}

		if (_classPK != manifestTreeNode.getClassPK()) {
			return false;
		}

		return true;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(8);

		sb.append("ManifestTreeNode {");
		sb.append(_className);
		sb.append(StringPool.COLON);
		sb.append(_classPK);
		sb.append(StringPool.SEMICOLON);
		sb.append(" label:");
		sb.append(_label);
		sb.append("}");

		return sb.toString();
	}

	private final List<ManifestTreeNode> _children = new ArrayList<>();
	private final String _className;
	private final long _classPK;
	private int _label;
	private boolean _missing;
	private List<ManifestTreeNode> _parents = new ArrayList<>();

}