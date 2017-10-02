(() => {
	'use strict';

	if (!String.prototype.format) {
		String.prototype.format = (str, ...args) => {
			return this.replace(/{(\d+)}/g, (match, number) => {
				return args[number] === undefined ? match : args[number - 1];
			});
		};
	}

	let langData = {};

	window.msg = (key, ...args) => {
		let localized = langData[key];
		if (localized === undefined) return key;
		if (!args.length) return localized;
		return localized.format(args);
	};

	window.localizeError = err => {
		return msg((err.details === undefined || err.details === 'No message available') ? err.error : err.details);
	};

	$(() => {
		$.getJSON($('meta[name=\'_lang_json\']').attr('content')).done(result => {
			if (result instanceof Object) {
				langData = result;
			} else {
				console.error('Could not load lang data');
			}
		}).fail(() => console.error('Could not load lang data')).always(() => {
			$(document).trigger('lang-ready');
		});
	});

})();
