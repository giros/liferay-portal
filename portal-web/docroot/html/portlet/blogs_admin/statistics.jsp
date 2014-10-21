<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
--%>

<%@ include file="/html/portlet/blogs_admin/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");
String backURL = ParamUtil.getString(request, "backURL");
%>

<liferay-ui:header
	backURL="<%= backURL %>"
	localizeTitle="<%= true %>"
	title="statistics"
/>

<%
List<AssetEntry> topViewedAssetEntries = AssetEntryLocalServiceUtil.getTopViewedEntriesByGroup(scopeGroupId, BlogsEntry.class.getName(), false, 0, 5);
List<BlogsEntry> topViewedBlogEntries = new ArrayList<BlogsEntry>();

for (AssetEntry assetEntry: topViewedAssetEntries) {
	topViewedBlogEntries.add(BlogsEntryLocalServiceUtil.getEntry(assetEntry.getClassPK()));
}
%>

<liferay-ui:search-container>

	<liferay-ui:search-container-results>
		<%
		searchContainer.setTotal(5);
		searchContainer.setResults(topViewedBlogEntries);
		%>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.blogs.model.BlogsEntry"
		escapedModel="<%= true %>"
		keyProperty="entryId"
		modelVar="entry"
		rowIdProperty="urlTitle"
	>
		<liferay-portlet:renderURL varImpl="rowURL">
			<portlet:param name="struts_action" value="/blogs_admin/view_entry" />
			<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		</liferay-portlet:renderURL>

		<%
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(BlogsEntry.class.getName(), entry.getEntryId());

		int viewCount = assetEntry.getViewCount();

		int commentsCount = MBMessageLocalServiceUtil.getDiscussionMessagesCount(BlogsEntry.class.getName(), entry.getEntryId(), WorkflowConstants.STATUS_ANY);

		RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.getStats(BlogsEntry.class.getName(), entry.getEntryId());

		double averageRatings = ratingsStats.getAverageScore();
		%>

		<%@ include file="/html/portlet/blogs_admin/search_columns.jspf" %>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/blogs_admin/entry_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />

</liferay-ui:search-container>

<%
List<AssetEntry> topCommentedAssetEntries = AssetEntryLocalServiceUtil.getTopCommentedEntriesByGroup(scopeGroupId, BlogsEntry.class.getName(), false, 0, 5);
List<BlogsEntry> topCommentedBlogEntries = new ArrayList<BlogsEntry>();

for (AssetEntry assetEntry: topCommentedAssetEntries) {
	topCommentedBlogEntries.add(BlogsEntryLocalServiceUtil.getEntry(assetEntry.getClassPK()));
}
%>

<liferay-ui:search-container>

	<liferay-ui:search-container-results>
		<%
		searchContainer.setTotal(5);
		searchContainer.setResults(topCommentedBlogEntries);
		%>
	</liferay-ui:search-container-results>

	<liferay-ui:search-container-row
		className="com.liferay.portlet.blogs.model.BlogsEntry"
		escapedModel="<%= true %>"
		keyProperty="entryId"
		modelVar="entry"
		rowIdProperty="urlTitle"
	>
		<liferay-portlet:renderURL varImpl="rowURL">
			<portlet:param name="struts_action" value="/blogs_admin/view_entry" />
			<portlet:param name="redirect" value="<%= searchContainer.getIteratorURL().toString() %>" />
			<portlet:param name="entryId" value="<%= String.valueOf(entry.getEntryId()) %>" />
		</liferay-portlet:renderURL>

		<%
		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(BlogsEntry.class.getName(), entry.getEntryId());

		int viewCount = assetEntry.getViewCount();

		int commentsCount = MBMessageLocalServiceUtil.getDiscussionMessagesCount(BlogsEntry.class.getName(), entry.getEntryId(), WorkflowConstants.STATUS_ANY);

		RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.getStats(BlogsEntry.class.getName(), entry.getEntryId());

		double averageRatings = ratingsStats.getAverageScore();
		%>

		<%@ include file="/html/portlet/blogs_admin/search_columns.jspf" %>

		<liferay-ui:search-container-column-jsp
			align="right"
			path="/html/portlet/blogs_admin/entry_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />

</liferay-ui:search-container>
