if (!String.prototype.format) {
  String.prototype.format = () => {
    var args = arguments;
    return this.replace(/{(\d+)}/g, (match, number) => {
      return args[number] === undefined ? match : args[number];
    });
  };
}
$(() => {
  var langData = {};
  $.getJSON($('meta[name=\'_lang_json\']').attr('content')).done(result => {
    if (result instanceof Object) {
      langData = result;
    } else {
      console.error('Could not load lang data');
    }
  }).fail(() => console.error('Could not load lang data')).always(() => {
    window.msg = (key, ...args) => {
      var localized = langData[key];
      if (localized === undefined) return key;
      if (!args.length) return localized;
      return localized.format(args);
    };
    $(document).trigger('lang-ready');
  });
});

function localizeError(err){
	return msg(err.details===undefined?err.error:err.details);
}
