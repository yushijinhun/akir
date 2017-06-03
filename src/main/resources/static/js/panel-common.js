function show_alert(type,content){
	$('.popup-alert').removeClass('fade in');
	close_alert();
	$('#root-panel').prepend('<div class="alert alert-'+type+' alert-dismissible fade in popup-alert" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button><div class="popup-alert-data"></div></div>');
	$('.popup-alert > .popup-alert-data').text(content);
}

function close_alert(){
	$('.popup-alert').alert('close');
}
