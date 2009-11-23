<%
/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
%>

<%@ include file="/html/portlet/message_boards/init.jsp" %>

<%
String tabs2 = ParamUtil.getString(request, "tabs2", "general");

String redirect = ParamUtil.getString(request, "redirect");

String emailFromName = ParamUtil.getString(request, "emailFromName", MBUtil.getEmailFromName(preferences));
String emailFromAddress = ParamUtil.getString(request, "emailFromAddress", MBUtil.getEmailFromAddress(preferences));

String emailMessageAddedSubjectPrefix = ParamUtil.getString(request, "emailMessageAddedSubjectPrefix", MBUtil.getEmailMessageAddedSubjectPrefix(preferences));
String emailMessageAddedBody = ParamUtil.getString(request, "emailMessageAddedBody", MBUtil.getEmailMessageAddedBody(preferences));
String emailMessageAddedSignature = ParamUtil.getString(request, "emailMessageAddedSignature", MBUtil.getEmailMessageAddedSignature(preferences));

String emailMessageUpdatedSubjectPrefix = ParamUtil.getString(request, "emailMessageUpdatedSubjectPrefix", MBUtil.getEmailMessageUpdatedSubjectPrefix(preferences));
String emailMessageUpdatedBody = ParamUtil.getString(request, "emailMessageUpdatedBody", MBUtil.getEmailMessageUpdatedBody(preferences));
String emailMessageUpdatedSignature = ParamUtil.getString(request, "emailMessageUpdatedSignature", MBUtil.getEmailMessageUpdatedSignature(preferences));
%>

<liferay-portlet:renderURL var="portletURL" portletConfiguration="true">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
	<portlet:param name="redirect" value="<%= redirect %>" />
</liferay-portlet:renderURL>

<script type="text/javascript">

	<%
	String bodyEditorParam = "";
	String bodyEditorContent = "";
	String signatureEditorParam = "";
	String signatureEditorContent = "";

	if (tabs2.equals("message-added-email")) {
		bodyEditorParam = "emailMessageAddedBody";
		bodyEditorContent = emailMessageAddedBody;
		signatureEditorParam = "emailMessageAddedSignature";
		signatureEditorContent = emailMessageAddedSignature;
	}
	else if (tabs2.equals("message-updated-email")) {
		bodyEditorParam = "emailMessageUpdatedBody";
		bodyEditorContent = emailMessageUpdatedBody;
		signatureEditorParam = "emailMessageUpdatedSignature";
		signatureEditorContent = emailMessageUpdatedSignature;
	}
	%>

	function <portlet:namespace />saveConfiguration() {
		<c:if test='<%= tabs2.equals("user-ranks") || tabs2.equals("thread-priorities") %>'>
			<portlet:namespace />updateLanguage();
		</c:if>

		submitForm(document.<portlet:namespace />fm);
	}
</script>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm" onSubmit='<%= renderResponse.getNamespace() + "saveConfiguration(); return false;" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= HtmlUtil.escapeAttribute(tabs2) %>" />

	<liferay-ui:tabs
		names="general,email-from,message-added-email,message-updated-email,thread-priorities,user-ranks,rss"
		param="tabs2"
		url="<%= portletURL %>"
	/>

	<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
	<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
	<liferay-ui:error key="emailMessageAddedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailMessageAddedSignature" message="please-enter-a-valid-signature" />
	<liferay-ui:error key="emailMessageAddedSubjectPrefix" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailMessageUpdatedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailMessageUpdatedSignature" message="please-enter-a-valid-signature" />
	<liferay-ui:error key="emailMessageUpdatedSubjectPrefix" message="please-enter-a-valid-subject" />

	<c:choose>
		<c:when test='<%= tabs2.equals("general") %>'>
			<aui:fieldset>
				<aui:input inlineLabel="left" name="allowAnonymousPosting" type="checkbox" value="<%= MBUtil.isAllowAnonymousPosting(preferences) %>" />

				<aui:input inlineLabel="left" name="enableFlags" type="checkbox" value="<%= enableFlags %>" />

				<aui:input inlineLabel="left" name="enableRatings" type="checkbox" value="<%= enableRatings %>" />
			</aui:fieldset>
		</c:when>
		<c:when test='<%= tabs2.equals("email-from") %>'>
			<aui:fieldset>
				<aui:input cssClass="lfr-input-text-container" label="name" name="emailFromName" type="text" value="<%= emailFromName %>" />

				<aui:input cssClass="lfr-input-text-container" label="address" name="emailFromAddress" type="text" value="<%=emailFromAddress  %>" />

				<aui:input inlineLabel="left" label="html-format" name="emailHtmlFormat" type="checkbox" value="<%= MBUtil.getEmailHtmlFormat(preferences) %>" />
			</aui:fieldset>

			<strong><liferay-ui:message key="definition-of-terms" /></strong>

			<br /><br />

			<table class="lfr-table">
			<tr>
				<td>
					<strong>[$COMPANY_ID$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-id-associated-with-the-message-board" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMPANY_MX$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-mx-associated-with-the-message-board" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMPANY_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-name-associated-with-the-message-board" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMMUNITY_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-community-name-associated-with-the-message-board" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$MAILING_LIST_ADDRESS$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-email-address-of-the-mailing-list" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$MESSAGE_USER_ADDRESS$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-email-address-of-the-user-who-added-the-message" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$MESSAGE_USER_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-user-who-added-the-message" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PORTLET_NAME$]</strong>
				</td>
				<td>
					<%= ((RenderResponseImpl)renderResponse).getTitle() %>
				</td>
			</tr>
			</table>
		</c:when>
		<c:when test='<%= tabs2.startsWith("message-") %>'>
			<aui:fieldset>
				<c:choose>
					<c:when test='<%= tabs2.equals("message-added-email") %>'>
						<aui:input inlineLabel="left" label="enabled" name="emailMessageAddedEnabled" type="checkbox" value="<%= MBUtil.getEmailMessageAddedEnabled(preferences) %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("message-updated-email") %>'>
						<aui:input inlineLabel="left" label="enabled" name="emailMessageUpdatedEnabled" type="checkbox" value="<%= MBUtil.getEmailMessageUpdatedEnabled(preferences) %>" />
					</c:when>
				</c:choose>

				<c:choose>
					<c:when test='<%= tabs2.equals("message-added-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject-prefix" name="emailMessageAddedSubjectPrefix" type="text" value="<%= emailMessageAddedSubjectPrefix %>" />
					</c:when>
					<c:when test='<%= tabs2.equals("message-updated-email") %>'>
						<aui:input cssClass="lfr-input-text-container" label="subject-prefix" name="emailMessageUpdatedSubjectPrefix" type="text" value="<%= emailMessageUpdatedSubjectPrefix %>" />
					</c:when>
				</c:choose>

				<aui:input cssClass="lfr-textarea-container" label="body" name="<%= bodyEditorParam %>" type="textarea" value="<%= bodyEditorContent %>" warp="soft" />

				<aui:input cssClass="lfr-textarea-container" label="signature" name="<%= signatureEditorParam %>" type="textarea" value="<%= signatureEditorContent %>" wrap="soft" />
			</aui:fieldset>

			<strong><liferay-ui:message key="definition-of-terms" /></strong>

			<br /><br />

			<table class="lfr-table">
			<tr>
				<td>
					<strong>[$CATEGORY_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-category-in-which-the-message-has-been-posted" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMPANY_ID$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-id-associated-with-the-message-board" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMPANY_MX$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-mx-associated-with-the-message-board" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMPANY_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-company-name-associated-with-the-message-board" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$COMMUNITY_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-community-name-associated-with-the-message-board" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$FROM_ADDRESS$]</strong>
				</td>
				<td>
					<%= emailFromAddress %>
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$FROM_NAME$]</strong>
				</td>
				<td>
					<%= emailFromName %>
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$MAILING_LIST_ADDRESS$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-email-address-of-the-mailing-list" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$MESSAGE_BODY$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-message-body" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$MESSAGE_ID$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-message-id" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$MESSAGE_SUBJECT$]</strong>
				</td>
				<td>
					The message subject
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$MESSAGE_USER_ADDRESS$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-email-address-of-the-user-who-added-the-message" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$MESSAGE_USER_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-user-who-added-the-message" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PORTAL_URL$]</strong>
				</td>
				<td>
					<%= company.getVirtualHost() %>
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$PORTLET_NAME$]</strong>
				</td>
				<td>
					<%= ((RenderResponseImpl)renderResponse).getTitle() %>
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$TO_ADDRESS$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-address-of-the-email-recipient" />
				</td>
			</tr>
			<tr>
				<td>
					<strong>[$TO_NAME$]</strong>
				</td>
				<td>
					<liferay-ui:message key="the-name-of-the-email-recipient" />
				</td>
			</tr>
			</table>
		</c:when>
		<c:when test='<%= tabs2.equals("thread-priorities") %>'>
			<div class="portlet-msg-info">
				<liferay-ui:message key="enter-the-name,-image,-and-priority-level-in-descending-order" />
			</div>

			<br /><br />

			<table class="lfr-table">
			<tr>
				<td>
					<aui:field-wrapper label="default-language" >
						<%= defaultLocale.getDisplayName(defaultLocale) %>
					</aui:field-wrapper>
				</td>
				<td>
					<aui:select label="localized-language" name="languageId" onClick='<%= renderResponse.getNamespace() + "updateLanguage();" %>' >
						<aui:option value="" />

						<%
						for (int i = 0; i < locales.length; i++) {
							if (locales[i].equals(defaultLocale)) {
								continue;
							}
						%>

							<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

						<%
						}
						%>

					</aui:select>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<br />
				</td>
			</tr>
			<tr>
				<td>
					<table class="lfr-table">
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="name" />
						</td>
						<td class="lfr-label">
							<liferay-ui:message key="image" />
						</td>
						<td class="lfr-label">
							<liferay-ui:message key="priority" />
						</td>
					</tr>

					<%
					priorities = LocalizationUtil.getPreferencesValues(preferences, "priorities", defaultLanguageId);

					for (int i = 0; i < 10; i++) {
						String name = StringPool.BLANK;
						String image = StringPool.BLANK;
						String value = StringPool.BLANK;

						if (priorities.length > i) {
							String[] priority = StringUtil.split(priorities[i]);

							try {
								name = priority[0];
								image = priority[1];
								value = priority[2];
							}
							catch (Exception e) {
							}

							if (Validator.isNull(name) && Validator.isNull(image)) {
								value = StringPool.BLANK;
							}
						}
					%>

						<tr>
							<td>
								<aui:input label="" name='<%= "priorityName" + i + "_" + defaultLanguageId %>' size="15" type="text" value="<%= name %>" />
							</td>
							<td>
								<aui:input label="" name='<%= "priorityImage" + i + "_" + defaultLanguageId %>' size="40" type="text" value="<%= image %>" />
							</td>
							<td>
								<aui:input label="" name='<%= "priorityValue" + i + "_" + defaultLanguageId %>' size="4" type="text" value="<%= value %>" />
							</td>
						</tr>

					<%
					}
					%>

					</table>
				</td>
				<td>
					<table id="<portlet:namespace />localized-priorities-table" class="lfr-table" <%= currentLocale.equals(defaultLocale) ? "style='display: none'" : "" %>>
					<tr>
						<td class="lfr-label">
							<liferay-ui:message key="name" />
						</td>
						<td class="lfr-label">
							<liferay-ui:message key="image" />
						</td>
						<td class="lfr-label">
							<liferay-ui:message key="priority" />
						</td>
					</tr>

					<%
					for (int i = 0; i < 10; i++) {
					%>

						<tr>
							<td>
								<aui:input label="" name='<%= "priorityName" + i + "_temp" %>' size="15" type="text" onChange='<%= renderResponse.getNamespace() + "onChanged();" %>' />
							</td>
							<td>
								<aui:input label="" name='<%= "priorityImage" + i + "_temp" %>' size="40" type="text" onChange='<%= renderResponse.getNamespace() + "onChanged();" %>' />
							</td>
							<td>
								<aui:input label="" name='<%= "priorityValue" + i + "_temp" %>' size="4" type="text" onChange='<%= renderResponse.getNamespace() + "onChanged();" %>' />	
							</td>
						</tr>

					<%
					}
					%>

					</table>

					<%
					for (int i = 0; i < locales.length; i++) {
						if (locales[i].equals(defaultLocale)) {
							continue;
						}

						String[] tempPriorities = LocalizationUtil.getPreferencesValues(preferences, "priorities", LocaleUtil.toLanguageId(locales[i]));

						for (int j = 0; j < 10; j++) {
							String name = StringPool.BLANK;
							String image = StringPool.BLANK;
							String value = StringPool.BLANK;

							if (tempPriorities.length > j) {
								String[] priority = StringUtil.split(tempPriorities[j]);

								try {
									name = priority[0];
									image = priority[1];
									value = priority[2];
								}
								catch (Exception e) {
								}

								if (Validator.isNull(name) && Validator.isNull(image)) {
									value = StringPool.BLANK;
								}
							}

					%>

							<aui:input name='<%= "priorityName" + j + "_" + LocaleUtil.toLanguageId(locales[i]) %>' type="hidden" value="<%= name %>" />
							<aui:input name='<%= "priorityImage" + j + "_" + LocaleUtil.toLanguageId(locales[i]) %>' type="hidden" value="<%= image %>" />
							<aui:input name='<%= "priorityValue" + j + "_" + LocaleUtil.toLanguageId(locales[i]) %>' type="hidden" value="<%= value %>" />

					<%
						}
					}
					%>

				</td>
			</tr>
			</table>

			<script type="text/javascript">
				var changed = false;
				var lastLanguageId = "<%= currentLanguageId %>";

				function <portlet:namespace />onChanged() {
					changed = true;
				}

				function <portlet:namespace />updateLanguage() {
					if (lastLanguageId != "<%= defaultLanguageId %>") {
						if (changed) {
							for (var i = 0; i < 10; i++) {
								var priorityName = jQuery("#<portlet:namespace />priorityName" + i + "_temp").attr("value");
								var priorityImage = jQuery("#<portlet:namespace />priorityImage" + i + "_temp").attr("value");
								var priorityValue = jQuery("#<portlet:namespace />priorityValue" + i + "_temp").attr("value");

								if (priorityName == null) {
									priorityName = "";
								}

								jQuery("#<portlet:namespace />priorityName" + i + "_" + lastLanguageId).attr("value", priorityName);

								if (priorityImage == null) {
									priorityImage = "";
								}

								jQuery("#<portlet:namespace />priorityImage" + i + "_" + lastLanguageId).attr("value", priorityImage);

								if (priorityValue == null) {
									priorityValue = "";
								}

								jQuery("#<portlet:namespace />priorityValue" + i + "_" + lastLanguageId).attr("value", priorityValue);
							}

							changed = false;
						}
					}

					var selLanguageId = "";

					for (var i = 0; i < document.<portlet:namespace />fm.<portlet:namespace />languageId.length; i++) {
						if (document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].selected) {
							selLanguageId = document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].value;

							break;
						}
					}

					if (selLanguageId != "") {
						<portlet:namespace />updateLanguageTemps(selLanguageId);

						jQuery("#<portlet:namespace />localized-priorities-table").show();
					}
					else {
						jQuery("#<portlet:namespace />localized-priorities-table").hide();
					}

					lastLanguageId = selLanguageId;

					return null;
				}

				function <portlet:namespace />updateLanguageTemps(lang) {
					if (lang != "<%= defaultLanguageId %>") {
						for (var i = 0; i < 10; i++) {
							var priorityName = jQuery("#<portlet:namespace />priorityName" + i + "_" + lang).attr("value");
							var priorityImage = jQuery("#<portlet:namespace />priorityImage" + i + "_" + lang).attr("value");
							var priorityValue = jQuery("#<portlet:namespace />priorityValue" + i + "_" + lang).attr("value");

							var defaultName = jQuery("#<portlet:namespace />priorityName" + i + "_" + "<%= defaultLanguageId %>").attr("value");
							var defaultImage = jQuery("#<portlet:namespace />priorityImage" + i + "_" + "<%= defaultLanguageId %>").attr("value");
							var defaultValue = jQuery("#<portlet:namespace />priorityValue" + i + "_" + "<%= defaultLanguageId %>").attr("value");

							if (defaultName == null) {
								defaultName = "";
							}

							if (defaultImage == null) {
								defaultImage = "";
							}

							if (defaultValue == null) {
								defaultValue = "";
							}

							if ((priorityName == null) || (priorityName == "")) {
								jQuery("#<portlet:namespace />priorityName" + i + "_temp").attr("value", defaultName);
							}
							else {
								jQuery("#<portlet:namespace />priorityName" + i + "_temp").attr("value", priorityName);
							}

							if ((priorityImage == null) || (priorityImage == "")) {
								jQuery("#<portlet:namespace />priorityImage" + i + "_temp").attr("value", defaultImage);
							}
							else {
								jQuery("#<portlet:namespace />priorityImage" + i + "_temp").attr("value", priorityImage);
							}

							if ((priorityValue == null) || (priorityValue == "")) {
								jQuery("#<portlet:namespace />priorityValue" + i + "_temp").attr("value", defaultValue);
							}
							else {
								jQuery("#<portlet:namespace />priorityValue" + i + "_temp").attr("value", priorityValue);
							}
						}
					}
				}

				<portlet:namespace />updateLanguageTemps(lastLanguageId);
			</script>
		</c:when>
		<c:when test='<%= tabs2.equals("user-ranks") %>'>
			<div class="portlet-msg-info">
				<liferay-ui:message key="enter-rank-and-minimum-post-pairs-per-line" />
			</div>

			<aui:fieldset>
				<table class="lfr-table">
				<tr>
					<td class="lfr-label">
						<aui:field-wrapper label="default-language" >
							<%= defaultLocale.getDisplayName(defaultLocale) %>
						</aui:field-wrapper>
					</td>
					<td class="lfr-label">
						<aui:select label="localized-language" name="languageId" onChange='<%= renderResponse.getNamespace() + "updateLanguage();" %>' >
							<aui:option value="" />

							<%
							for (int i = 0; i < locales.length; i++) {
								if (locales[i].equals(defaultLocale)) {
									continue;
								}
							%>

								<aui:option label="<%= locales[i].getDisplayName(locale) %>" selected="<%= currentLanguageId.equals(LocaleUtil.toLanguageId(locales[i])) %>" value="<%= LocaleUtil.toLanguageId(locales[i]) %>" />

							<%
							}
							%>

						</aui:select>
					</td>
				</tr>
				<tr>
					<td>
						<aui:input cssClass="lfr-textarea-container" label="" name='<%= "ranks_" + defaultLanguageId %>' type="textarea" value='<%= StringUtil.merge(LocalizationUtil.getPreferencesValues(preferences, "ranks", defaultLanguageId), StringPool.NEW_LINE) %>' />
					</td>
					<td>

						<%
						for (int i = 0; i < locales.length; i++) {
						if (locales[i].equals(defaultLocale)) {
							continue;
						}
						%>

							<input id="<portlet:namespace />ranks_<%= LocaleUtil.toLanguageId(locales[i]) %>" name="<portlet:namespace />ranks_<%= LocaleUtil.toLanguageId(locales[i]) %>" type="hidden" value='<%= StringUtil.merge(LocalizationUtil.getPreferencesValues(preferences, "ranks", LocaleUtil.toLanguageId(locales[i]), false), StringPool.NEW_LINE) %>' />

						<%
						}
						%>

						<aui:input cssClass="lfr-textarea-container" label="" name="ranks_temp" onChange='<%= renderResponse.getNamespace() + "onRanksChanged();" %>' type="textarea" />
					</td>
				</tr>
				</table>
			</aui:fieldset>

			<script type="text/javascript">
				var ranksChanged = false;
				var lastLanguageId = "<%= currentLanguageId %>";

				function <portlet:namespace />onRanksChanged() {
					ranksChanged = true;
				}

				function <portlet:namespace />updateLanguage() {
					if (lastLanguageId != "<%= defaultLanguageId %>") {
						if (ranksChanged) {
							var ranksValue = jQuery("#<portlet:namespace />ranks_temp").attr("value");

							if (ranksValue == null) {
								ranksValue = "";
							}

							jQuery("#<portlet:namespace />ranks_" + lastLanguageId).attr("value", ranksValue);

							ranksChanged = false;
						}
					}

					var selLanguageId = "";

					for (var i = 0; i < document.<portlet:namespace />fm.<portlet:namespace />languageId.length; i++) {
						if (document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].selected) {
							selLanguageId = document.<portlet:namespace />fm.<portlet:namespace />languageId.options[i].value;

							break;
						}
					}

					if (selLanguageId != "") {
						<portlet:namespace />updateLanguageTemps(selLanguageId);

						jQuery("#<portlet:namespace />ranks_temp").show();
					}
					else {
						jQuery("#<portlet:namespace />ranks_temp").hide();
					}

					lastLanguageId = selLanguageId;

					return null;
				}

				function <portlet:namespace />updateLanguageTemps(lang) {
					if (lang != "<%= defaultLanguageId %>") {
						var ranksValue = jQuery("#<portlet:namespace />ranks_" + lang).attr("value");
						var defaultRanksValue = jQuery("#<portlet:namespace />ranks_<%= defaultLanguageId %>").attr("value");

						if (defaultRanksValue == null) {
							defaultRanksValue = "";
						}

						if ((ranksValue == null) || (ranksValue == "")) {
							jQuery("#<portlet:namespace />ranks_temp").attr("value", defaultRanksValue);
						}
						else {
							jQuery("#<portlet:namespace />ranks_temp").attr("value", ranksValue);
						}
					}
				}

				<portlet:namespace />updateLanguageTemps(lastLanguageId);
			</script>
		</c:when>
		<c:when test='<%= tabs2.equals("rss") %>'>
			<aui:fieldset>
				<aui:select label="maximum-items-to-display" name="rssDelta">
					<aui:option label="1" selected="<%= rssDelta == 1 %>" />
					<aui:option label="2" selected="<%= rssDelta == 2 %>" />
					<aui:option label="3" selected="<%= rssDelta == 3 %>" />
					<aui:option label="4" selected="<%= rssDelta == 4 %>" />
					<aui:option label="5" selected="<%= rssDelta == 5 %>" />
					<aui:option label="10" selected="<%= rssDelta == 10 %>" />
					<aui:option label="15" selected="<%= rssDelta == 15 %>" />
					<aui:option label="20" selected="<%= rssDelta == 20 %>" />
					<aui:option label="25" selected="<%= rssDelta == 25 %>" />
					<aui:option label="30" selected="<%= rssDelta == 30 %>" />
					<aui:option label="40" selected="<%= rssDelta == 40 %>" />
					<aui:option label="50" selected="<%= rssDelta == 50 %>" />
					<aui:option label="60" selected="<%= rssDelta == 60 %>" />
					<aui:option label="70" selected="<%= rssDelta == 70 %>" />
					<aui:option label="80" selected="<%= rssDelta == 80 %>" />
					<aui:option label="90" selected="<%= rssDelta == 90 %>" />
					<aui:option label="100" selected="<%= rssDelta == 100 %>" />
				</aui:select>

				<aui:select label="display-style" name="rssDisplayStyle" >
					<aui:option label="full-content" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_FULL_CONTENT) %>" value="<%= RSSUtil.DISPLAY_STYLE_FULL_CONTENT %>" />
					<aui:option label="abstract" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_ABSTRACT) %>" value="<%= RSSUtil.DISPLAY_STYLE_ABSTRACT %>" />
					<aui:option label="title" selected="<%= rssDisplayStyle.equals(RSSUtil.DISPLAY_STYLE_TITLE) %>" value="<%= RSSUtil.DISPLAY_STYLE_TITLE %>" />
				</aui:select>

				<aui:select label="format" name="rssFormat">
					<aui:option label="RSS 1.0" selected='<%= rssFormat.equals("rss10") %>' value="rss10" />
					<aui:option label="RSS 2.0" selected='<%= rssFormat.equals("rss20") %>' value="rss20" />
					<aui:option label="Atom10" selected='<%= rssFormat.equals("atom10") %>' value="atom10" />
				</aui:select>
			</aui:fieldset>
		</c:when>
	</c:choose>

	<aui:button-row>
		<aui:button name="saveButton" type="submit" value="save" />

		<aui:button name="cancelButton" onClick="<%= redirect %>" type="button" value="cancel" />
	</aui:button-row>
</aui:form>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.message_boards.edit_configuration.jsp";
%>