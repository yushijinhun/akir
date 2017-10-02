(() => {
	'use strict';

	$(() => {
		let token = $("meta[name='_csrf']").attr("content");
		let header = $("meta[name='_csrf_header']").attr("content");
		$(document).ajaxSend((e, xhr, options) => {
			xhr.setRequestHeader(header, token);
		});
	});
})();
