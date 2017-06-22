<#include "util/common.ftl">

<@page_head>
	<meta name="_login_return_url" content="${login_return_url!home_page_url}"/>
	<@title "Login"/>
	<@css "/css/panel-page.css"/>
</@page_head>

<div id="root-panel" class="panel panel-default">
	<div class="panel-body">
		<h3 class="inpanel-title">Login to ${akir_server.name}</h3>
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
	</div>
</div>

<@page_end>
	<@js "/js/panel-common.js"/>
	<@js "/js/login.js"/>
</@page_end>
