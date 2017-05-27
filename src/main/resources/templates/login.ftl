<#include "common/panel-page.ftl">
<@panel_page>
<h3 class="inpanel-title">Login to ${akir_server.name}</h3>
<form action="login" method="post">
	<div class="form-group">
		<label for="email">Email</label>
		<input type="email" id="email" name="email" class="form-control" placeholder="Email" required autofocus>
	</div>
	<div class="form-group">
		<label for="password">Password</label>
		<input type="password" id="password" name="password" class="form-control" placeholder="Password" required>
	</div>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	<div>
		<button class="btn btn-primary" type="submit">Login</button>
		or <a href="register">register</a>
	</div>
</form>
</@>
