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

package com.liferay.portal.model.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.PortletPreferencesCT;
import com.liferay.portal.kernel.model.PortletPreferencesCTModel;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesCTPK;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;

import java.sql.Types;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The base model implementation for the PortletPreferencesCT service. Represents a row in the &quot;PortletPreferencesCT&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface </code>PortletPreferencesCTModel</code> exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link PortletPreferencesCTImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferencesCTImpl
 * @generated
 */
@ProviderType
public class PortletPreferencesCTModelImpl
	extends BaseModelImpl<PortletPreferencesCT>
	implements PortletPreferencesCTModel {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a portlet preferences ct model instance should use the <code>PortletPreferencesCT</code> interface instead.
	 */
	public static final String TABLE_NAME = "PortletPreferencesCT";

	public static final Object[][] TABLE_COLUMNS = {
		{"mvccVersion", Types.BIGINT}, {"classPK", Types.BIGINT},
		{"ctCollectionId", Types.BIGINT}, {"preferences", Types.CLOB}
	};

	public static final Map<String, Integer> TABLE_COLUMNS_MAP =
		new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("mvccVersion", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("classPK", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("ctCollectionId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("preferences", Types.CLOB);
	}

	public static final String TABLE_SQL_CREATE =
		"create table PortletPreferencesCT (mvccVersion LONG default 0 not null,classPK LONG not null,ctCollectionId LONG not null,preferences TEXT null,primary key (classPK, ctCollectionId))";

	public static final String TABLE_SQL_DROP =
		"drop table PortletPreferencesCT";

	public static final String ORDER_BY_JPQL =
		" ORDER BY portletPreferencesCT.id.classPK ASC, portletPreferencesCT.id.ctCollectionId ASC";

	public static final String ORDER_BY_SQL =
		" ORDER BY PortletPreferencesCT.classPK ASC, PortletPreferencesCT.ctCollectionId ASC";

	public static final String DATA_SOURCE = "liferayDataSource";

	public static final String SESSION_FACTORY = "liferaySessionFactory";

	public static final String TX_MANAGER = "liferayTransactionManager";

	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.entity.cache.enabled.com.liferay.portal.kernel.model.PortletPreferencesCT"),
		true);

	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.finder.cache.enabled.com.liferay.portal.kernel.model.PortletPreferencesCT"),
		true);

	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(
		com.liferay.portal.util.PropsUtil.get(
			"value.object.column.bitmask.enabled.com.liferay.portal.kernel.model.PortletPreferencesCT"),
		true);

	public static final long CLASSPK_COLUMN_BITMASK = 1L;

	public static final long CTCOLLECTIONID_COLUMN_BITMASK = 2L;

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(
		com.liferay.portal.util.PropsUtil.get(
			"lock.expiration.time.com.liferay.portal.kernel.model.PortletPreferencesCT"));

	public PortletPreferencesCTModelImpl() {
	}

	@Override
	public PortletPreferencesCTPK getPrimaryKey() {
		return new PortletPreferencesCTPK(_classPK, _ctCollectionId);
	}

	@Override
	public void setPrimaryKey(PortletPreferencesCTPK primaryKey) {
		setClassPK(primaryKey.classPK);
		setCtCollectionId(primaryKey.ctCollectionId);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return new PortletPreferencesCTPK(_classPK, _ctCollectionId);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey((PortletPreferencesCTPK)primaryKeyObj);
	}

	@Override
	public Class<?> getModelClass() {
		return PortletPreferencesCT.class;
	}

	@Override
	public String getModelClassName() {
		return PortletPreferencesCT.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		Map<String, Function<PortletPreferencesCT, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		for (Map.Entry<String, Function<PortletPreferencesCT, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<PortletPreferencesCT, Object> attributeGetterFunction =
				entry.getValue();

			attributes.put(
				attributeName,
				attributeGetterFunction.apply((PortletPreferencesCT)this));
		}

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Map<String, BiConsumer<PortletPreferencesCT, Object>>
			attributeSetterBiConsumers = getAttributeSetterBiConsumers();

		for (Map.Entry<String, Object> entry : attributes.entrySet()) {
			String attributeName = entry.getKey();

			BiConsumer<PortletPreferencesCT, Object> attributeSetterBiConsumer =
				attributeSetterBiConsumers.get(attributeName);

			if (attributeSetterBiConsumer != null) {
				attributeSetterBiConsumer.accept(
					(PortletPreferencesCT)this, entry.getValue());
			}
		}
	}

	public Map<String, Function<PortletPreferencesCT, Object>>
		getAttributeGetterFunctions() {

		return _attributeGetterFunctions;
	}

	public Map<String, BiConsumer<PortletPreferencesCT, Object>>
		getAttributeSetterBiConsumers() {

		return _attributeSetterBiConsumers;
	}

	private static Function<InvocationHandler, PortletPreferencesCT>
		_getProxyProviderFunction() {

		Class<?> proxyClass = ProxyUtil.getProxyClass(
			PortletPreferencesCT.class.getClassLoader(),
			PortletPreferencesCT.class, ModelWrapper.class);

		try {
			Constructor<PortletPreferencesCT> constructor =
				(Constructor<PortletPreferencesCT>)proxyClass.getConstructor(
					InvocationHandler.class);

			return invocationHandler -> {
				try {
					return constructor.newInstance(invocationHandler);
				}
				catch (ReflectiveOperationException roe) {
					throw new InternalError(roe);
				}
			};
		}
		catch (NoSuchMethodException nsme) {
			throw new InternalError(nsme);
		}
	}

	private static final Map<String, Function<PortletPreferencesCT, Object>>
		_attributeGetterFunctions;
	private static final Map<String, BiConsumer<PortletPreferencesCT, Object>>
		_attributeSetterBiConsumers;

	static {
		Map<String, Function<PortletPreferencesCT, Object>>
			attributeGetterFunctions =
				new LinkedHashMap
					<String, Function<PortletPreferencesCT, Object>>();
		Map<String, BiConsumer<PortletPreferencesCT, ?>>
			attributeSetterBiConsumers =
				new LinkedHashMap
					<String, BiConsumer<PortletPreferencesCT, ?>>();

		attributeGetterFunctions.put(
			"mvccVersion", PortletPreferencesCT::getMvccVersion);
		attributeSetterBiConsumers.put(
			"mvccVersion",
			(BiConsumer<PortletPreferencesCT, Long>)
				PortletPreferencesCT::setMvccVersion);
		attributeGetterFunctions.put(
			"classPK", PortletPreferencesCT::getClassPK);
		attributeSetterBiConsumers.put(
			"classPK",
			(BiConsumer<PortletPreferencesCT, Long>)
				PortletPreferencesCT::setClassPK);
		attributeGetterFunctions.put(
			"ctCollectionId", PortletPreferencesCT::getCtCollectionId);
		attributeSetterBiConsumers.put(
			"ctCollectionId",
			(BiConsumer<PortletPreferencesCT, Long>)
				PortletPreferencesCT::setCtCollectionId);
		attributeGetterFunctions.put(
			"preferences", PortletPreferencesCT::getPreferences);
		attributeSetterBiConsumers.put(
			"preferences",
			(BiConsumer<PortletPreferencesCT, String>)
				PortletPreferencesCT::setPreferences);

		_attributeGetterFunctions = Collections.unmodifiableMap(
			attributeGetterFunctions);
		_attributeSetterBiConsumers = Collections.unmodifiableMap(
			(Map)attributeSetterBiConsumers);
	}

	@Override
	public long getMvccVersion() {
		return _mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	@Override
	public long getClassPK() {
		return _classPK;
	}

	@Override
	public void setClassPK(long classPK) {
		_columnBitmask |= CLASSPK_COLUMN_BITMASK;

		if (!_setOriginalClassPK) {
			_setOriginalClassPK = true;

			_originalClassPK = _classPK;
		}

		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	@Override
	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	@Override
	public void setCtCollectionId(long ctCollectionId) {
		_columnBitmask |= CTCOLLECTIONID_COLUMN_BITMASK;

		if (!_setOriginalCtCollectionId) {
			_setOriginalCtCollectionId = true;

			_originalCtCollectionId = _ctCollectionId;
		}

		_ctCollectionId = ctCollectionId;
	}

	public long getOriginalCtCollectionId() {
		return _originalCtCollectionId;
	}

	@Override
	public String getPreferences() {
		if (_preferences == null) {
			return "";
		}
		else {
			return _preferences;
		}
	}

	@Override
	public void setPreferences(String preferences) {
		_preferences = preferences;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public PortletPreferencesCT toEscapedModel() {
		if (_escapedModel == null) {
			Function<InvocationHandler, PortletPreferencesCT>
				escapedModelProxyProviderFunction =
					EscapedModelProxyProviderFunctionHolder.
						_escapedModelProxyProviderFunction;

			_escapedModel = escapedModelProxyProviderFunction.apply(
				new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		PortletPreferencesCTImpl portletPreferencesCTImpl =
			new PortletPreferencesCTImpl();

		portletPreferencesCTImpl.setMvccVersion(getMvccVersion());
		portletPreferencesCTImpl.setClassPK(getClassPK());
		portletPreferencesCTImpl.setCtCollectionId(getCtCollectionId());
		portletPreferencesCTImpl.setPreferences(getPreferences());

		portletPreferencesCTImpl.resetOriginalValues();

		return portletPreferencesCTImpl;
	}

	@Override
	public int compareTo(PortletPreferencesCT portletPreferencesCT) {
		PortletPreferencesCTPK primaryKey =
			portletPreferencesCT.getPrimaryKey();

		return getPrimaryKey().compareTo(primaryKey);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortletPreferencesCT)) {
			return false;
		}

		PortletPreferencesCT portletPreferencesCT = (PortletPreferencesCT)obj;

		PortletPreferencesCTPK primaryKey =
			portletPreferencesCT.getPrimaryKey();

		if (getPrimaryKey().equals(primaryKey)) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		PortletPreferencesCTModelImpl portletPreferencesCTModelImpl = this;

		portletPreferencesCTModelImpl._originalClassPK =
			portletPreferencesCTModelImpl._classPK;

		portletPreferencesCTModelImpl._setOriginalClassPK = false;

		portletPreferencesCTModelImpl._originalCtCollectionId =
			portletPreferencesCTModelImpl._ctCollectionId;

		portletPreferencesCTModelImpl._setOriginalCtCollectionId = false;

		portletPreferencesCTModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<PortletPreferencesCT> toCacheModel() {
		PortletPreferencesCTCacheModel portletPreferencesCTCacheModel =
			new PortletPreferencesCTCacheModel();

		portletPreferencesCTCacheModel.portletPreferencesCTPK = getPrimaryKey();

		portletPreferencesCTCacheModel.mvccVersion = getMvccVersion();

		portletPreferencesCTCacheModel.classPK = getClassPK();

		portletPreferencesCTCacheModel.ctCollectionId = getCtCollectionId();

		portletPreferencesCTCacheModel.preferences = getPreferences();

		String preferences = portletPreferencesCTCacheModel.preferences;

		if ((preferences != null) && (preferences.length() == 0)) {
			portletPreferencesCTCacheModel.preferences = null;
		}

		return portletPreferencesCTCacheModel;
	}

	@Override
	public String toString() {
		Map<String, Function<PortletPreferencesCT, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			4 * attributeGetterFunctions.size() + 2);

		sb.append("{");

		for (Map.Entry<String, Function<PortletPreferencesCT, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<PortletPreferencesCT, Object> attributeGetterFunction =
				entry.getValue();

			sb.append(attributeName);
			sb.append("=");
			sb.append(
				attributeGetterFunction.apply((PortletPreferencesCT)this));
			sb.append(", ");
		}

		if (sb.index() > 1) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		Map<String, Function<PortletPreferencesCT, Object>>
			attributeGetterFunctions = getAttributeGetterFunctions();

		StringBundler sb = new StringBundler(
			5 * attributeGetterFunctions.size() + 4);

		sb.append("<model><model-name>");
		sb.append(getModelClassName());
		sb.append("</model-name>");

		for (Map.Entry<String, Function<PortletPreferencesCT, Object>> entry :
				attributeGetterFunctions.entrySet()) {

			String attributeName = entry.getKey();
			Function<PortletPreferencesCT, Object> attributeGetterFunction =
				entry.getValue();

			sb.append("<column><column-name>");
			sb.append(attributeName);
			sb.append("</column-name><column-value><![CDATA[");
			sb.append(
				attributeGetterFunction.apply((PortletPreferencesCT)this));
			sb.append("]]></column-value></column>");
		}

		sb.append("</model>");

		return sb.toString();
	}

	private static class EscapedModelProxyProviderFunctionHolder {

		private static final Function<InvocationHandler, PortletPreferencesCT>
			_escapedModelProxyProviderFunction = _getProxyProviderFunction();

	}

	private long _mvccVersion;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private long _ctCollectionId;
	private long _originalCtCollectionId;
	private boolean _setOriginalCtCollectionId;
	private String _preferences;
	private long _columnBitmask;
	private PortletPreferencesCT _escapedModel;

}