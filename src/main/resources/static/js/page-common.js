$(() => {
	$('.modal').on('hidden.bs.modal', ()=>{
		$(this).removeClass('fv-modal-stack');
		$('body').data('fv_open_modals', $('body').data('fv_open_modals') - 1);
	});
	$('.modal').on('shown.bs.modal', ()=>{
		if ($('body').data('fv_open_modals') === undefined)
			$('body').data('fv_open_modals', 0);
		if ($(this).hasClass('fv-modal-stack'))
			return;
		$(this).addClass('fv-modal-stack');
		$('body').data('fv_open_modals', $('body').data('fv_open_modals') + 1);
		$(this).css('z-index', 1040 + (10 * $('body').data('fv_open_modals')));
		$('.modal-backdrop').not('.fv-modal-stack').css('z-index', 1039 + (10 * $('body').data('fv_open_modals')));
		$('.modal-backdrop').not('fv-modal-stack').addClass('fv-modal-stack');
	});
});

function createModal(style){
	var modalHtml=$(`
<div class="modal fade" tabindex="-1" role="dialog">
	<div class="modal-dialog${style===undefined?"":" modal-"+style}" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title"></h4>
			</div>
			<div class="modal-body"></div>
			<div class="modal-footer">
				<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
			</div>
		</div>
	</div>
</div>
	`);
	modalHtml.appendTo('body');
	modalHtml.on('hidden.bs.modal',()=>{
		modalHtml.remove();
	});
	return modalHtml;
}

function showErrorModal(message){
	var modal=createModal('sm');
	modal.find('.modal-title').html('<span class="glyphicon glyphicon-exclamation-sign"></span> <span class="danger-modal-error-msg"></span>');
	modal.find('.danger-modal-error-msg').text(msg("error_dialog.title"));
	modal.find('.modal-body').text(message);
	modal.find('.modal-dialog').addClass('modal-danger');
	modal.modal('show');
}

$(()=>{
	window.errorDialog=err=>showErrorModal(localizeError(err));
	$('#logout-link').click(()=>{
		$.post('/logout')
			.done(()=>$(location).attr('href','/'))
			.fail(resolvedError(errorDialog));
	});
	$('#navbar-search-form').submit(()=>{
		var query=$('#navbar-search-box').val();
		if(query!==''){
			$(location).attr('href','/search?w='+encodeURIComponent(query));
		}
		return false;
	});
})
