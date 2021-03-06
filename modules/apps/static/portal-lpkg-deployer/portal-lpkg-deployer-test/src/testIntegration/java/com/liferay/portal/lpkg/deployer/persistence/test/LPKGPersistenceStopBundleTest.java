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

package com.liferay.portal.lpkg.deployer.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Matthew Tambara
 */
@RunWith(Arquillian.class)
public class LPKGPersistenceStopBundleTest {

	@Test
	public void testStopBundle() throws BundleException {
		Bundle bundle = FrameworkUtil.getBundle(
			LPKGPersistenceStopBundleTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		Bundle lpkgPersistenceTestBundle = null;

		for (Bundle testBundle : bundleContext.getBundles()) {
			String symbolicName = testBundle.getSymbolicName();

			if (symbolicName.equals("lpkg.persistence.test")) {
				lpkgPersistenceTestBundle = testBundle;

				break;
			}
		}

		Assert.assertNotNull(lpkgPersistenceTestBundle);

		Assert.assertEquals(
			Bundle.ACTIVE, lpkgPersistenceTestBundle.getState());

		lpkgPersistenceTestBundle.stop();

		Assert.assertEquals(
			Bundle.RESOLVED, lpkgPersistenceTestBundle.getState());
	}

}