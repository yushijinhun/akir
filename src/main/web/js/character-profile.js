'use strict';
(() => {
	'use strict';

	$(() => {
		let viewernode = $('#character_viewer');

		let skinViewer = new skinview3d.SkinViewer({
			domElement: $('#character_viewer div').get(0),
			slim: viewernode.data('model') === 'alex',
			width: 300,
			height: 300,
			animation: skinview3d.WalkAnimation
		});
		let control = new skinview3d.SkinControl(skinViewer);

		skinViewer.skinUrl = viewernode.data('skin');
		if (viewernode.data('cape') !== undefined) {
			skinViewer.capeUrl = viewernode.data('cape');
		}
	});
})();
