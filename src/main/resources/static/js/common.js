function ajaxForm(config){
	config.form.submit(function(){
		if(config.form.find("button[type='submit']").is('.disabled')){
			return false;
		}
		config.before();
		$.ajax({
			url:config.url,
			type:'POST',
			contentType: "application/json; charset=utf-8",
			data:JSON.stringify(config.form.serializeObject()),
			success:function(result){
				config.after();
				config.success(result);
			},
			error:function(err){
				config.after();
				config.error(handleErrorResponse(err));
			}
		});
		return false;
	});
}

function handleErrorResponse(err){
	if(err.responseJSON===undefined){
		if(err.responseText===undefined){
			return {
				error:'unknown_error',
				errorMessage:'Unknown error'
			};
		}else{
			return {
				error:'json_parse_error',
				errorMessage:err.responseText
			};
		}
	}else{
		return err.responseJSON;
	}
}

function homeUrl(){
	return $("meta[name='_home_url']").attr("content");
}
