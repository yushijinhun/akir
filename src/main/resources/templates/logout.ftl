<#include "util/common.ftl">
<#include "util/panel-page.ftl">

<@page_head>
	<@title>${msg("action.logout")}</@>
	<@css "/css/panel-page.css"/>
</@>

<@panel_page>
	<p>${msg("logout.message")}</p>
	<button id="logout-btn" class="btn btn-primary">${msg("action.logout")}</button>
</@>

<@page_end>
	<@js "/js/panel-common.js"/>
	<@js "/js/logout.js"/>
</@>
