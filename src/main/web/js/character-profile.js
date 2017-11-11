(() => {
	'use strict';

	$(() => {
		let skinViewer = null;
		let skinViewerControl = null;

		let getProp = propName => $(".character-properties>li[data-key='" + propName + "']").data('value');
		let updateTitle = () => $('title').text(getProp('owner') + '/' + getProp('name') + ' - ' + pageMetadata('site_name'));
		let propRenderers = {
			'name': (value, node) => {
				node.text(value);
				updateTitle();
			},
			'owner': (value, node) => {
				node.html(`<a></a>`);
				node.find('a').text(value);
				node.find('a').attr('href', '/user/' + value);
				updateTitle();
			},
			'model': (value, node) => {
				node.html(htmlCharacterModelName(value));
				let slim = value === 'ALEX';
				if (skinViewer === null || skinViewer.playerObject.slim !== slim) {
					if (skinViewer !== null) {
						skinViewer.dispose();
						skinViewerControl.dispose();
					}
					skinViewer = new skinview3d.SkinViewer({
						domElement: $('#character_viewer div').get(0),
						slim: slim,
						width: 300,
						height: 300,
						animation: skinview3d.WalkAnimation
					});
					skinViewerControl = new skinview3d.SkinControl(skinViewer);
					skinViewer.skinUrl = '/yggdrasil/textures/texture/' + getProp('skin');
					let cape = getProp('cape');
					if (cape !== '') {
						skinViewer.capeUrl = '/yggdrasil/textures/texture/' + cape;
					}
				}
			},
			'skin': (value, node) => {
				node.text(value.substring(0, 7));
				node.attr('title', value);
				if (skinViewer !== null) {
					skinViewer.skinUrl = '/yggdrasil/textures/texture/' + getProp('skin');
				}
			},
			'cape': (value, node) => {
				if (value === '') {
					node.html(`
						<span class="text-muted"></span>
					`);
					node.find('span').text(msg('character.texture.missing'));
					node.removeAttr('title');
				} else {
					node.text(value).substring(7);
					node.attr('title', value);
				}
				if (skinViewer !== null) {
					if (value === '') {
						skinViewer.playerObject.cape.visible = false;
					} else {
						skinViewer.capeUrl = '/yggdrasil/textures/texture/' + value;
					}
				}
			}
		};
		let setProp = (propName, value) => {
			let prop = $(".character-properties>li[data-key='" + propName + "']");
			prop.data('value', value);
			propRenderers[prop.data('key')](value, prop.find('.prop-value'));
		};
		for (let key of Object.keys(propRenderers)) {
			setProp(key, getProp(key));
		}

		let characterUuid = pageMetadata('character_uuid');

		$('#modal_rename').on('show.bs.modal', () => {
			$('#newCharacterName').val(getProp('name'));
			$('#newCharacterName').trigger('change');
		});

		$(document).on('lang-ready', () => {
			setProp('model', getProp('model'));
			setProp('cape', getProp('cape'));
		});

		ajaxForm({
			form: $('#rename-form'),
			url: '/character/' + characterUuid + '/rename',
			success: response => setProp('name', response.newCharacterName),
			error: err => showErrorModal(localizeError(err)),
			before: () => buttonLoading($('#btn-rename'), true),
			after: () => {
				buttonLoading($('#btn-rename'), false);
				$('#modal_rename').modal('hide');
			}
		});
	});
})();
