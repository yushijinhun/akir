<#include "util/common.ftl">
<#include "util/panel-page.ftl">

<@page_head>
	<@title><@msg "action.register"/></@>
	<@css "/css/panel-page.css"/>
</@>

<@panel_page>
<@panel_title><@msg "register.panel_title"/></@>
<form id="register-form" data-toggle="validator">
	<div class="form-group has-feedback">
		<label for="email"><@msg "user.email"/></label>
		<input type="email" id="email" name="email" class="form-control" placeholder="<@msg "user.email"/>" required autofocus
			maxlength="${email_maxlength?c}"
			data-remote="${url("/register/validate/email")}"
			data-remote-error="<@msg "error.user.conflict.email"/>">
		<@form_feedback_icon/>
		<@form_helper_errors/>
	</div>
	<div class="form-group has-feedback">
		<label for="name"><@msg "user.name"/></label>
		<input type="text" id="name" name="name" class="form-control" placeholder="<@msg "user.name"/>" autocomplete="off" required
			maxlength="${name_maxlength?c}"
			pattern="${name_regex}"
			data-remote="${url("/register/validate/name")}"
			data-remote-error="<@msg "error.user.conflict.name"/>">
		<@form_feedback_icon/>
		<@form_helper_errors/>
	</div>
	<div class="form-group has-feedback">
		<label for="password"><@msg "user.password"/></label>
		<input type="password" id="password" name="password" class="form-control" placeholder="<@msg "user.password"/>" required
			maxlength="${password_maxlength?c}"
			data-minlength="${password_minlength?c}">
		<@form_feedback_icon/>
		<@form_helper><@msg "register.tooltip.password_minlength"/></@>
	</div>
	<div class="form-group has-feedback">
		<label for="confirm-password"><@msg "register.confirm_password"/></label>
		<input type="password" id="confirm-password" class="form-control" placeholder="<@msg "register.confirm_password"/>" required
			data-match="#password"
			data-match-error="<@msg "register.tooltip.password_mismatch"/>">
		<@form_feedback_icon/>
		<@form_helper_errors/>
	</div>
	<div>
		<button id="register-btn" class="btn btn-primary" type="submit"><@msg "action.register"/></button>
		<@msg "login_register.or_choose"/> <a href="${url("/login")}"><@msg "register.login_action"/></a>
	</div>
</form>
</@>

<@page_end>
	<@js js_bootstrap_validator/>
	<@js "/js/panel-common.js"/>
	<@js "/js/register.js"/>
</@>
