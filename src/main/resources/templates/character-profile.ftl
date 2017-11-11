<#include "util/common.ftl">
<#include "util/page.ftl">

<#assign is_user_self=showing_owner.id==login_user.id>
<#assign allow_edit=is_user_self||login_user.admin>

<#macro character_prop id name edit_tooltip modal_selector data>
	<li id="prop_${id}" data-key="${id}" data-value="${data}">
		<div class="row">
			<div class="col-xs-4 prop-key">
				<label>${name}:</label>
			</div>
			<div class="col-xs-4 prop-value">
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
	<@metadata key="character_uuid" value=showing_character.uuid/>
</@>

<@page_body>
	<div id="character_viewer">
		<div></div>
	</div>

	<ul class="character-properties">
		<@character_prop "name" msg("character.name") msg("action.character.rename") "#modal_rename" showing_character.name/>
		<@character_prop "owner" msg("character.owner") msg("action.character.transfer") "#modal_transfer" showing_owner.name/>
		<@character_prop "model" msg("character.model") msg("action.character.change_model") "#modal_change_model" showing_character.model/>
		<@character_prop "skin" msg("character.texture.skin.id") msg("action.character.upload_skin") "#modal_upload_skin" ext.skinTextureId(showing_character)/>
		<@character_prop "cape" msg("character.texture.cape.id") msg("action.character.upload_cape") "#modal_upload_cape" ext.capeTextureId(showing_character)!""/>
	</ul>
</@>

<#-- rename -->
<@modal "modal_rename">
	<form id="rename-form" data-toggle="validator">
		<@modal_header>${msg("action.character.rename")}</@>
		<@modal_body>
			<div class="form-group has-feedback">
				<label for="newCharacterName">${msg("action.character.rename.new_name")}</label>
				<input id="newCharacterName" name="newCharacterName" type="text" class="form-control" required autofocus
					maxlength="${character_restriction.name_maxlength?c}"
					pattern="${character_restriction.name_regex}"
					data-remote="${url("/character/${showing_character.uuid}/rename/validate/name")}"
					data-remote-error="${msg("error.character.conflict.name")}">
				<@form_feedback_icon/>
				<@form_helper_errors/>
			</div>
		</@>
		<@modal_footer>
			<@modal_btn_close/>
			<button id="rename-btn" class="btn btn-primary" type="submit">${msg("action.character.rename.submit")}</button>
		</@>
	</form>
</@>

<@page_end>
	<@js js_page_common/>
	<@js js_three/>
	<@js js_skinview3d/>
	<@js js_bootstrap_validator/>
	<@js "/js/character-profile.js"/>
</@>
