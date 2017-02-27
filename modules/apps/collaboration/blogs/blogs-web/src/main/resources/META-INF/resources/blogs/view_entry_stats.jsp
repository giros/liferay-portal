<%--
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
--%>

<%@ include file="/blogs/init.jsp" %>

<%
String redirect = ParamUtil.getString(request, "redirect");

PortletURL portletURL = renderResponse.createRenderURL();

portletDisplay.setShowBackIcon(true);
portletDisplay.setURLBack(redirect);


BlogsEntry entry = (BlogsEntry)request.getAttribute(WebKeys.BLOGS_ENTRY);

String className = BlogsEntry.class.getName();
long classPK = entry.getEntryId();

String tabs1Names = "daily,weekly,monthly,yearly";
%>

<liferay-ui:tabs
	names="<%= tabs1Names %>"
	refresh="<%= false %>"
	type="tabs nav-tabs-default"
>
	<%
	List<AssetEntryViewStatsResult> dailyCounts = AssetEntryViewStatsLocalServiceUtil.getViewCounts(className, classPK, AssetEntryViewStatsConstants.DAILY);
	%>

	<liferay-ui:section>
		<liferay-ui:search-container
			emptyResultsMessage="no-entries-were-found"
			iteratorURL="<%= portletURL %>"
			total="<%= dailyCounts.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= dailyCounts %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.asset.kernel.model.AssetEntryViewStatsResult"
				modelVar="result"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="period"
				>

					<%= result.getDisplayDate() %>

				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="visits"
				>

					<%= String.valueOf(result.getViewCount()) %>

				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</liferay-ui:section>

	<%
	List<AssetEntryViewStatsResult> weeklyCounts = AssetEntryViewStatsLocalServiceUtil.getViewCounts(className, classPK, AssetEntryViewStatsConstants.WEEKLY);
	%>

	<liferay-ui:section>
		<liferay-ui:search-container
			emptyResultsMessage="no-entries-were-found"
			iteratorURL="<%= portletURL %>"
			total="<%= weeklyCounts.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= weeklyCounts %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.asset.kernel.model.AssetEntryViewStatsResult"
				modelVar="result"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="period"
				>

					<%= result.getDisplayDate() %>

				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="visits"
				>

					<%= String.valueOf(result.getViewCount()) %>

				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</liferay-ui:section>

	<%
	List<AssetEntryViewStatsResult> monthlyCounts = AssetEntryViewStatsLocalServiceUtil.getViewCounts(className, classPK, AssetEntryViewStatsConstants.MONTHLY);
	%>

	<liferay-ui:section>
		<liferay-ui:search-container
			emptyResultsMessage="no-entries-were-found"
			iteratorURL="<%= portletURL %>"
			total="<%= monthlyCounts.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= monthlyCounts %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.asset.kernel.model.AssetEntryViewStatsResult"
				modelVar="result"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="period"
				>

					<%= result.getDisplayDate() %>

				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="visits"
				>

					<%= String.valueOf(result.getViewCount()) %>

				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</liferay-ui:section>

	<%
	List<AssetEntryViewStatsResult> yearlyCounts = AssetEntryViewStatsLocalServiceUtil.getViewCounts(className, classPK, AssetEntryViewStatsConstants.YEARLY);
	%>

	<liferay-ui:section>
		<liferay-ui:search-container
			emptyResultsMessage="no-entries-were-found"
			iteratorURL="<%= portletURL %>"
			total="<%= yearlyCounts.size() %>"
		>
			<liferay-ui:search-container-results
				results="<%= yearlyCounts %>"
			/>

			<liferay-ui:search-container-row
				className="com.liferay.asset.kernel.model.AssetEntryViewStatsResult"
				modelVar="result"
			>
				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="period"
				>

					<%= result.getDisplayDate() %>

				</liferay-ui:search-container-column-text>

				<liferay-ui:search-container-column-text
					cssClass="table-cell-content"
					name="visits"
				>

					<%= String.valueOf(result.getViewCount()) %>

				</liferay-ui:search-container-column-text>
			</liferay-ui:search-container-row>

			<liferay-ui:search-iterator markupView="lexicon" />
		</liferay-ui:search-container>
	</liferay-ui:section>
</liferay-ui:tabs>