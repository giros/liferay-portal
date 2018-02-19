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

<%@ include file="/init.jsp" %>

<%
long backgroundTaskId = ParamUtil.getLong(request, BackgroundTaskConstants.BACKGROUND_TASK_ID);

BackgroundTask backgroundTask = BackgroundTaskManagerUtil.fetchBackgroundTask(backgroundTaskId);

JSONObject jsonObject = null;

try {
	jsonObject = JSONFactoryUtil.createJSONObject(backgroundTask.getStatusMessage());
}
catch (Exception e) {
}

boolean failed = (backgroundTask.getStatus() == BackgroundTaskConstants.STATUS_FAILED);
%>

<c:choose>
	<c:when test="<%= jsonObject == null %>">
		<div class="alert <%= failed ? "alert-danger" : StringPool.BLANK %> publish-error">
			<liferay-ui:message arguments="<%= backgroundTask.getStatusMessage() %>" key="unable-to-execute-process-x" translateArguments="<%= false %>" />
		</div>
	</c:when>
	<c:otherwise>
		<c:if test="<%= failed %>">
			<div class="alert alert-danger publish-error">

				<%
				boolean exported = MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "exported");
				boolean validated = MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "validated");
				%>

				<c:choose>
					<c:when test="<%= exported && !validated %>">
						<h4 class="upload-error-message"><liferay-ui:message key="the-publication-process-did-not-start-due-to-validation-errors" /></h4>
					</c:when>
					<c:otherwise>
						<h4 class="upload-error-message"><liferay-ui:message key="an-unexpected-error-occurred-with-the-publication-process.-please-check-your-portal-and-publishing-configuration" /></h4>
					</c:otherwise>
				</c:choose>

				<span class="error-message"><%= HtmlUtil.escape(jsonObject.getString("message")) %></span>

				<%
				JSONArray messageListItemsJSONArray = jsonObject.getJSONArray("messageListItems");
				%>

				<c:if test="<%= (messageListItemsJSONArray != null) && (messageListItemsJSONArray.length() > 0) %>">
					<ul class="error-list-items">

						<%
						for (int i = 0; i < messageListItemsJSONArray.length(); i++) {
							JSONObject messageListItemJSONArray = messageListItemsJSONArray.getJSONObject(i);

							String info = messageListItemJSONArray.getString("info");
						%>

							<li>
								<%= messageListItemJSONArray.getString("type") %>

								<%= messageListItemJSONArray.getString("site") %>:

								<strong><%= HtmlUtil.escape(messageListItemJSONArray.getString("name")) %></strong>

								<c:if test="<%= Validator.isNotNull(info) %>">
									<span class="error-info">(<%= HtmlUtil.escape(messageListItemJSONArray.getString("info")) %>)</span>
								</c:if>
							</li>

						<%
						}
						%>

					</ul>
				</c:if>
			</div>
		</c:if>

		<%
		JSONArray warningMessagesJSONArray = jsonObject.getJSONArray("warningMessages");
		%>

		<c:if test="<%= (warningMessagesJSONArray != null) && (warningMessagesJSONArray.length() > 0) %>">
			<div class="alert alert-warning">
				<h4 class="upload-error-message"><liferay-ui:message key="the-process-finished-with-the-following-warnings" /></h4>

				<ul class="error-list-items">

					<%
					for (int i = 0; i < warningMessagesJSONArray.length(); i++) {
						JSONObject warningMessageJSONArray = warningMessagesJSONArray.getJSONObject(i);

						String info = warningMessageJSONArray.getString("info");
					%>

						<li>
							<c:choose>
								<c:when test="<%= Validator.isNotNull(warningMessageJSONArray.getString("type")) %>">
									<%= warningMessageJSONArray.getString("type") %>:

									<strong><%= warningMessageJSONArray.getString("size") %></strong>

									<c:if test="<%= Validator.isNotNull(info) %>">
										<span class="error-info">(<%= HtmlUtil.escape(info) %>)</span>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="<%= Validator.isNotNull(info) %>">
										<span class="error-info"><%= HtmlUtil.escape(info) %></span>
									</c:if>
								</c:otherwise>
							</c:choose>
						</li>

					<%
					}
					%>

				</ul>
			</div>
		</c:if>
	</c:otherwise>
</c:choose>