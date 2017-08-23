<#include "util/common.ftl">
<#include "util/page.ftl">

<@page_head>
	<@title><@msg "action.create_character"/></@>
	<@css css_page_common/>
	<@css "/css/create-character.css"/>
</@>

<@page_body>
<div class="h3"><@msg "action.create_character"/></div>
<hr>
<form id="create-character" data-toggle="validator">
	<div class="form-group has-feedback">
		<div class="input-owner-character">
			<div class="owner-chooser">
				<label for="owner"><@msg "create_character.owner"/></label>
				<button id="owner" class="btn btn-default" type="button">
					<@gravatar_img login_user 20/>
					${login_user.name}
				</button>
			</div>
			<span class="owner-character-divider">/</span>
			<div>
				<label for="characterName"><@msg "create_character.character_name"/></label>
				<div class="character-name-input">
					<input id="characterName" name="characterName" type="text" class="form-control" required autofocus
					maxlength="${name_maxlength?c}"
					pattern="${name_regex}"
					data-remote="${url("/character/new/validate/name")}"
					data-remote-error="<@msg "create_character.error.name_conflict"/>">
					<@form_feedback_icon/>
				</div>
			</div>
		</div>
		<@form_helper_errors/>
	</div>
	<div class="form-group model-chooser">
		<label for="textureModel"><@msg "create_character.choose_model"/></label>
		<div class="text-muted"><@msg "create_character.choose_model.description"/></div>
		<div class="divided-list">
			<#list available_texture_models as texture_model>
				<div class="radio">
					<label>
						<input type="radio" name="textureModel" id="textureModel_${texture_model}" value="${texture_model}"
							<#if texture_model==available_texture_models?first>checked</#if>>
						<@default_character_avatar texture_model 54/>
						<div class="texture-model-info">
							<@character_model_name texture_model/>
							<div class="text-muted">
								<@msg "character.model.${texture_model}.description"/>
							</div>
						</div>
					</label>
				</div>
			</#list>
		</div>
	</div>
	<div>
		<button id="submit-btn" class="btn btn-primary" type="submit"><@msg "create_character.submit"/></button>
	</div>
</form>
</@>

<@page_end>
	<@js js_page_common/>
	<@js js_bootstrap_validator/>
	<@css "/js/create-character.js"/>
</@>
