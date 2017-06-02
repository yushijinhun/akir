$(function(){
	$('#login-form').submit(function(){
		$('#login-btn').prop('disabled',true);
		$('#login-btn').text('Login...');
		$.ajax({
			url:'login',
			dataType:'json',
			type:'POST',
			contentType: "application/json; charset=utf-8",
			data:JSON.stringify($('#login-form').serializeObject()),
			success:function(data){
				$(location).attr('href',data.returnUrl);
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
