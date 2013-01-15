package com.liferay.portal.util;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.servlet.taglib.ui.BreadcrumbEntry;
import com.liferay.portal.kernel.util.Breadcrumb;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.aui.AUIUtil;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletURL;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class BreadcrumbImpl implements Breadcrumb {

	public void buildGuestGroupBreadcrumb(
			ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			themeDisplay.getCompanyId(), GroupConstants.GUEST);

		if (group.getPublicLayoutsPageCount() > 0) {
			LayoutSet layoutSet = LayoutSetLocalServiceUtil.getLayoutSet(
				group.getGroupId(), false);

			String layoutSetFriendlyURL =
				PortalUtil.getLayoutSetFriendlyURL(layoutSet, themeDisplay);

			if (themeDisplay.isAddSessionIdToURL()) {
				layoutSetFriendlyURL = PortalUtil.getURLWithSessionId(
					layoutSetFriendlyURL, themeDisplay.getSessionId());
			}

			sb.append("<li><span><a href=\"");
			sb.append(layoutSetFriendlyURL);
			sb.append("\">");
			sb.append(HtmlUtil.escape(themeDisplay.getAccount().getName()));
			sb.append("</a></span></li>");
		}
	}

	public void buildLayoutBreadcrumb(
			Layout selLayout, String selLayoutParam, boolean selectedLayout,
			PortletURL portletURL, ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception {

		String layoutURL = getBreadcrumbLayoutURL(
			selLayout, selLayoutParam, portletURL, themeDisplay);

		String target = PortalUtil.getLayoutTarget(selLayout);

		StringBundler breadcrumbSB = new StringBundler(7);

		if (themeDisplay.isAddSessionIdToURL()) {
			layoutURL = PortalUtil.getURLWithSessionId(
				layoutURL, themeDisplay.getSessionId());
		}

		if (selLayout.isTypeControlPanel()) {
			layoutURL = HttpUtil.removeParameter(
				layoutURL, "controlPanelCategory");
		}

		breadcrumbSB.append("<li><span><a href=\"");
		breadcrumbSB.append(layoutURL);
		breadcrumbSB.append("\" ");

		String layoutName = selLayout.getName(themeDisplay.getLocale());

		if (selLayout.isTypeControlPanel()) {
			breadcrumbSB.append(" target=\"_top\"");

			if (layoutName.equals(LayoutConstants.NAME_CONTROL_PANEL_DEFAULT)) {
				layoutName = LanguageUtil.get(
					themeDisplay.getLocale(), "control-panel");
			}
		}
		else {
			breadcrumbSB.append(target);
		}

		breadcrumbSB.append(">");

		breadcrumbSB.append(HtmlUtil.escape(layoutName));

		breadcrumbSB.append("</a></span></li>");

		if (selLayout.getParentLayoutId() !=
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {

			Layout parentLayout = null;

			if (selLayout instanceof VirtualLayout) {
				VirtualLayout virtualLayout = (VirtualLayout)selLayout;

				Layout sourceLayout = virtualLayout.getSourceLayout();

				parentLayout = LayoutLocalServiceUtil.getLayout(
					sourceLayout.getGroupId(), sourceLayout.isPrivateLayout(),
					sourceLayout.getParentLayoutId());

				parentLayout = new VirtualLayout(
					parentLayout, selLayout.getGroup());
			}
			else {
				parentLayout = LayoutLocalServiceUtil.getLayout(
					selLayout.getGroupId(), selLayout.isPrivateLayout(),
					selLayout.getParentLayoutId());
			}

			buildLayoutBreadcrumb(
				parentLayout, selLayoutParam, false, portletURL, themeDisplay,
				sb);

			sb.append(breadcrumbSB.toString());
		}
		else {
			sb.append(breadcrumbSB.toString());
		}
	}

	public void buildParentGroupsBreadcrumb(
			LayoutSet layoutSet, PortletURL portletURL,
			ThemeDisplay themeDisplay, StringBundler sb)
		throws Exception {

		Group group = layoutSet.getGroup();

		if (group.isControlPanel()) {
			return;
		}

		if (group.isSite()) {
			Group parentSite = group.getParentGroup();

			if (parentSite != null) {
				LayoutSet parentLayoutSet =
					LayoutSetLocalServiceUtil.getLayoutSet(
						parentSite.getGroupId(), layoutSet.isPrivateLayout());

				buildParentGroupsBreadcrumb(
					parentLayoutSet, portletURL, themeDisplay, sb);
			}
		}
		else if (group.isUser()) {
			User groupUser = UserLocalServiceUtil.getUser(group.getClassPK());

			List<Organization> organizations =
				OrganizationLocalServiceUtil.getUserOrganizations(
					groupUser.getUserId());

			if (!organizations.isEmpty()) {
				Organization organization = organizations.get(0);

				Group parentGroup = organization.getGroup();

				LayoutSet parentLayoutSet =
					LayoutSetLocalServiceUtil.getLayoutSet(
						parentGroup.getGroupId(), layoutSet.isPrivateLayout());

				buildParentGroupsBreadcrumb(
					parentLayoutSet, portletURL, themeDisplay, sb);
			}
		}

		int layoutsPageCount = 0;

		if (layoutSet.isPrivateLayout()) {
			layoutsPageCount = group.getPrivateLayoutsPageCount();
		}
		else {
			layoutsPageCount = group.getPublicLayoutsPageCount();
		}

		if ((layoutsPageCount > 0) &&
			!group.getName().equals(GroupConstants.GUEST)) {

			String layoutSetFriendlyURL = PortalUtil.getLayoutSetFriendlyURL(
				layoutSet, themeDisplay);

			if (themeDisplay.isAddSessionIdToURL()) {
				layoutSetFriendlyURL = PortalUtil.getURLWithSessionId(
					layoutSetFriendlyURL, themeDisplay.getSessionId());
			}

			sb.append("<li><span><a href=\"");
			sb.append(layoutSetFriendlyURL);
			sb.append("\">");
			sb.append(HtmlUtil.escape(group.getDescriptiveName()));
			sb.append("</a></span></li>");
		}
	}
	
	public void buildPortletBreadcrumb(
			HttpServletRequest request, boolean showCurrentGroup,
			boolean showCurrentPortlet, ThemeDisplay themeDisplay,
			StringBundler sb)
		throws Exception {

		List<BreadcrumbEntry> breadcrumbEntries =
			PortalUtil.getPortletBreadcrumbs(request);

		if (breadcrumbEntries == null) {
			return;
		}

		for (BreadcrumbEntry breadcrumbEntry : breadcrumbEntries) {
			Map<String, Object> data = breadcrumbEntry.getData();

			String breadcrumbTitle = breadcrumbEntry.getTitle();
			String breadcrumbURL = breadcrumbEntry.getURL();

			if (!showCurrentGroup) {
				String parentGroupName = themeDisplay.getParentGroupName();

				if (parentGroupName.equals(breadcrumbTitle)) {
					continue;
				}
			}

			if (!showCurrentPortlet) {
				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				String portletTitle = PortalUtil.getPortletTitle(
					portletDisplay.getId(), themeDisplay.getUser());

				if (portletTitle.equals(breadcrumbTitle)) {
					continue;
				}
			}

			if (!CookieKeys.hasSessionId(request) &&
				Validator.isNotNull(breadcrumbURL)) {

				HttpSession session = request.getSession();

				breadcrumbURL = PortalUtil.getURLWithSessionId(
					breadcrumbURL, session.getId());
			}

			sb.append("<li><span>");

			if (Validator.isNotNull(breadcrumbURL)) {
				sb.append("<a href=\"");
				sb.append(HtmlUtil.escape(breadcrumbURL));
				sb.append("\"");
				sb.append(AUIUtil.buildData(data));
				sb.append(">");
			}

			sb.append(HtmlUtil.escape(breadcrumbTitle));

			if (Validator.isNotNull(breadcrumbURL)) {
				sb.append("</a>");
			}

			sb.append("</span></li>");
		}
	}
	

	
	public String insertClassOption(String breadcrumbString) {

		if (Validator.isNotNull(breadcrumbString)) {
			int pos_first = breadcrumbString.indexOf("<li");

			int pos_last = breadcrumbString.lastIndexOf("<li");

			if (pos_last > pos_first) {
				breadcrumbString = StringUtil.insert(
					breadcrumbString, " class=\"first\"", pos_first + 3);

				pos_last += 14;
			}

			breadcrumbString = StringUtil.insert(
				breadcrumbString, " class=\"last\"", pos_last + 3);
		}

		return breadcrumbString;
	}
	
	public String removeLastClass(String breadcrumbs) {
		return StringUtil.replace(
			breadcrumbs,
			new String[] {"class=\"last\"", "class=\"first last\""},
			new String[] {StringPool.BLANK, "class=\"first\""});
	}
	
	protected String getBreadcrumbLayoutURL(
			Layout selLayout, String selLayoutParam, PortletURL portletURL,
			ThemeDisplay themeDisplay)
		throws Exception {

		if (portletURL == null) {
			return PortalUtil.getLayoutFullURL(selLayout, themeDisplay);
		}
		else {
			portletURL.setParameter(
				selLayoutParam, String.valueOf(selLayout.getPlid()));

			if (selLayout.isTypeControlPanel()) {
				if (themeDisplay.getDoAsGroupId() > 0) {
					portletURL.setParameter(
						"doAsGroupId",
						String.valueOf(themeDisplay.getDoAsGroupId()));
				}

				if (themeDisplay.getRefererPlid() !=
					LayoutConstants.DEFAULT_PLID) {

					portletURL.setParameter(
						"refererPlid",
						String.valueOf(themeDisplay.getRefererPlid()));
				}
			}

			return portletURL.toString();
		}
	}
}
