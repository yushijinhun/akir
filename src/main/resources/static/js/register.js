$(() => {
	ajaxForm({
		form : $('#register-form'),
		url : '/register',
		success : () => $(location).attr('href', '/'),
		error : err => show_alert('danger', localizeError(err)),
		before : () => buttonLoading($('#register-btn'),true),
		after : () => buttonLoading($('#register-btn'),false)
	});
});
