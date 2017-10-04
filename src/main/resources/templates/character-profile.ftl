<#include "util/common.ftl">
<#include "util/page.ftl">

<#assign is_user_self=showing_owner.id==login_user.id>
<#assign allow_edit=is_user_self||login_user.admin>

<#macro character_prop id name edit_tooltip modal_selector>
	<li id="${id}">
		<div class="row">
			<div class="col-xs-4 prop-key">
				<label>${name}:</label>
			</div>
			<div class="col-xs-4 prop-value">
				<#nested>
			</div>
			<#if allow_edit>
				<div class="col-xs-4 prop-edit">
					<a class="add-element-link" href="#" data-toggle="modal" data-target="${modal_selector}">
						<span class="fa fa-pencil" title="${edit_tooltip}" data-toggle="tooltip"></span>
					</a>
				</div>
			</#if>
		</div>
	</li>
</#macro>

<@page_head>
	<@title>${showing_owner.name}/${showing_character.name}</@>
	<@css css_page_common/>
	<@css "/css/character-profile.css"/>
</@>

<@page_body>
	<div id="character_viewer"
			data-skin="${texture_url(ext.skinTextureId(showing_character))}"
			data-model="${showing_character.model?lower_case}"
			<#if ext.capeTextureId(showing_character)??>
				data-cape="${texture_url(ext.capeTextureId(showing_character))}"
			</#if>
		>
		<div></div>
	</div>

	<ul class="character-properties">
		<@character_prop "prop_name" msg("character.name") msg("action.character.rename") "#modal_rename">
			${showing_character.name}
		</@>
		<@character_prop "prop_owner" msg("character.owner") msg("action.character.transfer") "#modal_transfer">
			${showing_owner.name}
		</@>
		<@character_prop "prop_model" msg("character.model") msg("action.character.change_model") "#modal_change_model">
			<@character_model_name showing_character.model/>
		</@>
		<@character_prop "prop_skin" msg("character.texture.skin.id") msg("action.character.upload_skin") "#modal_upload_skin">
			<span title="${ext.skinTextureId(showing_character)}" data-toggle="tooltip">
				${ext.skinTextureId(showing_character)[0..*7]}
			</span>
		</@>
		<@character_prop "prop_cape" msg("character.texture.cape.id") msg("action.character.upload_cape") "#modal_upload_cape">
			<#if ext.capeTextureId(showing_character)??>
				<span title="${ext.capeTextureId(showing_character)}" data-toggle="tooltip">
					${ext.capeTextureId(showing_character)[0..*7]}
				</span>
			<#else>
				<span class="text-muted">
					${msg("character.texture.missing")}
				</span>
			</#if>
		</@>
	</ul>
</@>

<#-- rename -->
<@modal "modal_rename">
	<@modal_header>${msg("action.character.rename")}</@>
	<@modal_body>
		content
	</@>
	<@modal_footer>
		<@modal_btn_close/>
		<@modal_btn_primary msg("action.character.rename.submit")/>
	</@>
</@>

<@page_end>
	<@js js_page_common/>
	<@js js_three/>
	<@js js_skinview3d/>
	<@js "/js/character-profile.js"/>
</@>
