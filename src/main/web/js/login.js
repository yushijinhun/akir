(() => {
	'use strict';

	$(() => {
		ajaxForm({
			form: $('#login-form'),
			url: '/login',
			success: () => $(location).attr('href', pageMetadata('login_return_url')),
			error: err => show_alert('danger', localizeError(err)),
			before: () => buttonLoading($('#login-btn'), true),
			after: () => buttonLoading($('#login-btn'), false)
		});

		$(document).on('lang-ready', () => {
			let tooltip = pageMetadata('login_tooltip');
			if (tooltip)
				show_alert(tooltip === 'email_verify.success' || tooltip === 'register.success' ? 'success' : 'danger', msg(tooltip));
		});
	});
})();
