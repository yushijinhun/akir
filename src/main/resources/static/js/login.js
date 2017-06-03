$(function(){
	$('#login-form').submit(function(){
		$('#login-btn').prop('disabled',true);
		$('#login-btn').text('Login...');
		$.ajax({
			url:'login',
			type:'POST',
			contentType: "application/json; charset=utf-8",
			data:JSON.stringify($('#login-form').serializeObject()),
			success:function(){
				$(location).attr('href',$("meta[name='_login_return_url']").attr("content"));
			},
			error:function(err){
				$('#login-btn').prop('disabled',false);
				$('#login-btn').text('Login');
				show_alert('danger',err.responseJSON.errorMessage);
			}
		});
		return false;
	});
});
