(() => {
	'use strict';

	$(() => {
		$('#resend-btn').click(() => {
			buttonLoading($('#resend-btn'), true);
			$.post('/email_verify/resend')
				.always(() => buttonLoading($('#resend-btn'), false))
				.done(() => show_alert('success', msg("verify_email.resend.success")))
				.fail(resolvedError(err => show_alert('danger', localizeError(err))));
		});
	});

})();
