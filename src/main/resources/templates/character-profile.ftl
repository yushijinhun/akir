<#include "util/common.ftl">
<#include "util/page.ftl">

<#assign is_user_self=showing_owner.id==login_user.id>
<#assign allow_edit=is_user_self||login_user.admin>

<#macro character_prop id name edit_tooltip>
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
					<a class="fa fa-pencil add-element-link" href="#" title="${edit_tooltip}"></a>
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
		<@character_prop "prop_name" "Character name" "Rename">
			${showing_character.name}
		</@>
		<@character_prop "prop_owner" "Owner" "Transfer character">
			${showing_owner.name}
		</@>
		<@character_prop "prop_model" "Model" "Change model">
			<@character_model_name showing_character.model/>
		</@>
		<@character_prop "prop_skin" "Skin id" "Upload skin">
			<span title="${ext.skinTextureId(showing_character)}">
				${ext.skinTextureId(showing_character)[0..*7]}
			</span>
		</@>
		<@character_prop "prop_cape" "Cape id" "Upload cape">
			<#if ext.capeTextureId(showing_character)??>
				<span title="${ext.capeTextureId(showing_character)}">
					${ext.capeTextureId(showing_character)[0..*7]}
				</span>
			<#else>
				<span class="text-muted">
					None
				</span>
			</#if>
		</@>
	</ul>
</@>

<@page_end>
	<@js js_page_common/>
	<@js js_three/>
	<@js js_skinview3d/>
	<@js "/js/character-profile.js"/>
</@>
