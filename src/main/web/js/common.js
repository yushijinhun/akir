(() => {
	'use strict';

	window.ajaxForm = config => {
		config.form.submit(() => {
			if (config.form.find("button[type='submit']").is('.disabled')) {
				return false;
			}
			config.before();
			let data = config.form.serializeObject();
			if (config.processForm !== undefined)
				config.processForm(data);
			$.ajax({
				url: config.url,
				type: 'POST',
				contentType: "application/json; charset=utf-8",
				data: JSON.stringify(data),
				success: result => {
					config.after();
					config.success(result);
				},
				error: resolvedError(err => {
					config.after();
					config.error(err);
				})
			});
			return false;
		});
	};

	window.buttonLoading = (btn, status) => {
		if (btn.data('loading-status') === true && status === true) return;
		if (btn.data('loading-status') !== true && status === false) return;
		btn.data('loading-status', status);
		if (status) {
			btn.data('origin-text', btn.text());
			btn.text(btn.text() + '...');
			btn.prop('disabled', true);
			// trick
			if (window.close_alert !== undefined) close_alert();
		} else {
			btn.text(btn.data('origin-text'));
			btn.data('origin-text', null);
			btn.prop('disabled', false);
		}
	};

	window.resolvedError = customer => {
		function resolveErrorResponse(xhr, textStatus, errorThrown) {
			if (xhr.responseJSON === undefined) {
				if (xhr.readyState == 0) {
					return {
						error: 'ajax_error',
						code: 0,
						details: 'error.ajax.network'
					};
				} else {
					return {
						error: 'ajax_error',
						code: xhr.status,
						details: 'error.ajax.unknown'
					};
				}
			} else {
				return xhr.responseJSON;
			}
		}
		return (xhr, textStatus, errorThrown) => customer(resolveErrorResponse(xhr, textStatus, errorThrown));
	};

	window.pageMetadata = key => $("meta[name='akir:" + key + "']").attr('content');

	let character_model_icons = {
		"steve": "mars",
		"alex": "venus"
	};
	window.htmlCharacterModelName = model => {
		model = model.toLowerCase();
		return `
			<div class="character-model-name character-model-name-${model}">
				<span class="text-muted">
					<i class="fa fa-${character_model_icons[model]}"></i>
					${msg("character.model." + model)}
				</span>
			</div>
		`;
	};
})();
