<#include "common.ftl">

<@page_head>
	<@title "Register"/>
	<@css "/css/panel-page.css"/>
</@page_head>

<div id="root-panel" class="panel panel-default">
	<div class="panel-body">
		<h3 class="inpanel-title">Create a new account</h3>
		<form action="/register" method="post">
			<div class="form-group">
				<label for="email">Email</label>
				<input type="email" id="email" class="form-control" placeholder="Email" required autofocus>
			</div>
			<div class="form-group">
				<label for="name">Name</label>
				<input type="text" id="name" class="form-control" placeholder="Name" autocomplete="off" required>
			</div>
			<div class="form-group">
				<label for="password">Password</label>
				<input type="password" id="password" class="form-control" placeholder="Password" required>
			</div>
			<div class="form-group">
				<label for="confirm-password">Confirm Password</label>
				<input type="password" id="confirm-password" class="form-control" placeholder="Confirm Password" required>
			</div>
			<div>
				<button class="btn btn-primary" type="submit" class="btn">Register</button>
				or <a href="login">login</a>
			</div>
		</form>
	</div>
</div>

<@page_end>
	<@js "/js/panel-common.js"/>
</@page_end>
