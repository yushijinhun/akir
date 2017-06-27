$(function(){
	ajaxForm({
		form:$('#login-form'),
		url:'/login',
		success:function(){
			$(location).attr('href',$("meta[name='_login_return_url']").attr("content"));
		},
		error:function(err){
			show_alert('danger',err.error);
		},
		before:function(){
			close_alert();
			$('#login-btn').prop('disabled',true);
			$('#login-btn').text('Login...');
		},
		after:function(){
			$('#login-btn').prop('disabled',false);
			$('#login-btn').text('Login');
		}
	});
	
	var loginTooltipMeta=$("meta[name='_login_tooltip']");
	if(loginTooltipMeta.length){
		switch (loginTooltipMeta.attr('content')) {
		case 'email_verified':
			show_alert('info','Your email has been verified.');
			break;
		case 'email_already_verified':
			show_alert('info','Your email has already been verified.');
			break;
		case 'email_verify_code_expired':
			show_alert('danger','Your verify code has been expired, please login and resend it.');
			break;
		case 'email_wrong_verify_code':
			show_alert('danger','Your verify code is wrong, please login and resend it.');
			break;
		}
	}
});
