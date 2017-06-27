<#include "util/common.ftl">
<#include "util/panel-page.ftl">
<#include "util/form.ftl">

<@page_head>
	<@title "Register"/>
	<@css "/css/panel-page.css"/>
</@page_head>

<@panel_page>
<@panel_title>Create a new account</@panel_title>
<form id="register-form" data-toggle="validator">
	<div class="form-group has-feedback">
		<label for="email">Email</label>
		<input type="email" id="email" name="email" class="form-control" placeholder="Email" required autofocus
			maxlength="254"
			data-remote="<@url "/register/validate/email"/>"
			data-remote-error="Email is already in use">
		<@form_feedback_icon/>
		<@form_helper_errors/>
	</div>
	<div class="form-group has-feedback">
		<label for="name">Name</label>
		<input type="text" id="name" name="name" class="form-control" placeholder="Name" autocomplete="off" required
			maxlength="254"
			pattern="[a-zA-Z]([-_]?[a-zA-Z0-9]+)*"
			data-remote="<@url "/register/validate/name"/>"
			data-remote-error="Name is already in use">
		<@form_feedback_icon/>
		<@form_helper_errors/>
	</div>
	<div class="form-group has-feedback">
		<label for="password">Password</label>
		<input type="password" id="password" name="password" class="form-control" placeholder="Password" required
			maxlength="256"
			data-minlength="6">
		<@form_feedback_icon/>
		<@form_helper>Minimum of 6 characters</@form_helper>
	</div>
	<div class="form-group has-feedback">
		<label for="confirm-password">Confirm Password</label>
		<input type="password" id="confirm-password" class="form-control" placeholder="Confirm Password" required
			data-match="#password"
			data-match-error="Passwords don't match">
		<@form_feedback_icon/>
		<@form_helper_errors/>
	</div>
	<div>
		<button id="register-btn" class="btn btn-primary" type="submit">Register</button>
		or <a href="/login">login</a>
	</div>
</form>
</@panel_page>

<@page_end>
	<@ext_js js_bootstrap_validator/>
	<@js "/js/panel-common.js"/>
	<@js "/js/register.js"/>
</@page_end>
