package com.liferay.portal.kernel.util;

import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.theme.ThemeDisplay;

import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;


public interface Breadcrumb {

	public void buildGuestGroupBreadcrumb(
			ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception;

	public void buildLayoutBreadcrumb(
			Layout selLayout, String selLayoutParam, boolean selectedLayout,
			PortletURL portletURL, ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception;

	public void buildParentGroupsBreadcrumb(
			LayoutSet layoutSet, PortletURL portletURL,
			ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception;

	public void buildPortletBreadcrumb(
			HttpServletRequest request, boolean showCurrentGroup,
			boolean showCurrentPortlet, ThemeDisplay themeDisplay,
			StringBundler sb)
		throws Exception;

	public String insertClassOption(String breadcrumbString);

	public String removeLastClass(String breadcrumbs);

}
