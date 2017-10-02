(() => {
	'use strict';

	$(() => {
		$('#logout-btn').click(() => {
			buttonLoading($('#logout-btn'), true);
			$.post('/logout')
				.always(() => buttonLoading($('#logout-btn'), false))
				.done(() => $(location).attr('href', '/'))
				.fail(resolvedError(err => show_alert('danger', localizeError(err))));
		});
	});
})();
