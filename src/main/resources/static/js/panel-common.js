function show_alert(type,content){
	$('.popup-alert').alert('close');
	$('#root-panel').prepend('<div class="alert alert-'+type+' alert-dismissible popup-alert" role="alert"><button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button><div class="popup-alert-data"></div></div>');
	$('.popup-alert > .popup-alert-data').text(content);
}
