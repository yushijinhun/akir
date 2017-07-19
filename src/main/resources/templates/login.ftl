<#include "util/common.ftl">
<#include "util/panel-page.ftl">

<@page_head>
	<meta name="_login_return_url" content="${login_return_url!"/"}"/>
	<#if login_tooltip??>
		<meta name="_login_tooltip" content="${login_tooltip}"/>
	</#if>
	<@title><@msg "action.login"/></@>
	<@css "/css/panel-page.css"/>
</@>

<@panel_page>
<@panel_title><@msg key="login.panel_title" args=[akir_server.name]/></@>
<form id="login-form">
	<div class="form-group">
		<label for="email"><@msg "user.email"/></label>
		<input type="email" id="email" name="email" class="form-control" placeholder="<@msg "user.email"/>" required autofocus>
	</div>
	<div class="form-group">
		<label for="password"><@msg "user.password"/></label>
		<input type="password" id="password" name="password" class="form-control" placeholder="<@msg "user.password"/>" required>
	</div>
	<div>
		<button id="login-btn" class="btn btn-primary" type="submit"><@msg "action.login"/></button>
		<@msg "login_register.or_choose"/> <a href="${url("/register")}"><@msg "login.register_action"/></a>
	</div>
</form>
</@>

<@page_end>
	<@js "/js/panel-common.js"/>
	<@js "/js/login.js"/>
</@>
