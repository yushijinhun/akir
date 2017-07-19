$(() => {
	ajaxForm({
		form : $('#login-form'),
		url : '/login',
		success : () => $(location).attr('href', $("meta[name='_login_return_url']").attr("content")),
		error : err => show_alert('danger', localizeError(err)),
		before : () => buttonLoading($('#login-btn'),true),
		after : () => buttonLoading($('#login-btn'),false)
	});

	$(document).on('lang-ready', () => {
		var tooltipMeta = $("meta[name='_login_tooltip']");
		if (tooltipMeta.length) {
			var tooltip = tooltipMeta.attr('content');
			show_alert(tooltip === 'email_verify.success'||tooltip==='register.success' ? 'success' : 'danger', msg(tooltip));
		}
	});
});
