<#include "util/common.ftl">
<#include "util/panel-page.ftl">

<@page_head>
	<meta name="_login_return_url" content="${login_return_url!home_page_url}"/>
	<#if login_tooltip??>
		<meta name="_login_tooltip" content="${login_tooltip}"/>
	</#if>
	<@title "Login"/>
	<@css "/css/panel-page.css"/>
</@page_head>

<@panel_page>
<@panel_title>Login to ${akir_server.name}</@panel_title>
<form id="login-form">
	<div class="form-group">
		<label for="email">Email</label>
		<input type="email" id="email" name="email" class="form-control" placeholder="Email" required autofocus>
	</div>
	<div class="form-group">
		<label for="password">Password</label>
		<input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
	</div>
	<div>
		<button id="login-btn" class="btn btn-primary" type="submit">Login</button>
		or <a href="register">register</a>
	</div>
</form>
</@panel_page>

<@page_end>
	<@js "/js/panel-common.js"/>
	<@js "/js/login.js"/>
</@page_end>
