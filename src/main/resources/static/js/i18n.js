var lang={};

$(function(){
	var jsonLocation=$("meta[name='_lang_json']").attr("content");
	$.ajax({
		url:jsonLocation,
		type:'GET',
		success:function(result){
			if(!(result instanceof Object)){
				console.error('Could not load lang json',jsonLocation,':',result);
				return;
			}
			lang=result;
		},
		error:function(err){
			console.error('Could not load lang json',jsonLocation,', error:',err);
		}
	});
});

function msg(key){
	var value=lang[key];
	if(value===undefined){
		console.warn('Missing i18n key:',key);
		return key;
	}
	return value;
}
