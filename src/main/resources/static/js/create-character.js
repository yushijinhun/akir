$(() => {
	ajaxForm({
		form : $('#create-character-form'),
		url : '/character/new',
		success : response => $(location).attr('href', '/character/'+response.uuid),
		error : errorDialog,
		before : () => buttonLoading($('#submit-btn'),true),
		after : () => buttonLoading($('#submit-btn'),false),
		processForm:form=>form.ownerName=$('#owner').data('value')
	});
});
