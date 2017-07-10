<#include "util/common.ftl">
<#include "util/panel-page.ftl">

<@page_head>
	<@title><@msg "action.verify_email"/></@>
	<@css "/css/panel-page.css"/>
</@>

<@panel_page>
<@panel_title><@msg "verify_email.panel_title"/></@>
<p><@msg key="verify_email.text" args=[user.getName(),user.email]/></p>
<p><@msg "verify_email.resend_tips"/></p>
<button id="resend-btn" class="btn btn-primary"><@msg "action.resend_email"/></button>
</@>

<@page_end>
	<@js "/js/panel-common.js"/>
	<@js "/js/verify-email.js"/>
</@>
