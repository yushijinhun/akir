<#include "util/common.ftl">
<#include "util/page.ftl">

<@page_head>
	<@title>${msg("action.character.create")}</@>
	<@css css_page_common/>
	<@css "/css/create-character.css"/>
</@>

<@page_body>
	<div class="h3">${msg("action.character.create")}</div>
	<hr>
	<form id="create-character-form" data-toggle="validator">
		<div class="form-group has-feedback">
			<div class="input-owner-character">
				<div class="owner-chooser">
					<label for="owner">${msg("character.owner")}</label>
					<button id="owner" class="btn btn-default" type="button" data-value="${owner_user.name}">
						<@gravatar_img owner_user 20/>
						${owner_user.name}
					</button>
				</div>
				<span class="owner-character-divider">/</span>
				<div>
					<label for="characterName">${msg("character.name")}</label>
					<div class="character-name-input">
						<input id="characterName" name="characterName" type="text" class="form-control" required autofocus
						maxlength="${name_maxlength?c}"
						pattern="${name_regex}"
						data-remote="${url("/character/new/validate/name")}"
						data-remote-error="${msg("error.character.conflict.name")}">
						<@form_feedback_icon/>
					</div>
				</div>
			</div>
			<@form_helper_errors/>
		</div>
		<div class="form-group model-chooser">
			<label for="textureModel">${msg("action.character.create.choose_model")}</label>
			<div class="text-muted">${msg("action.character.create.choose_model.description")}</div>
			<div class="divided-list">
				<#list available_texture_models as texture_model>
					<div class="radio">
						<label>
							<input type="radio" name="textureModel"
									id="textureModel_${texture_model}"
									value="${texture_model}"
									<#if texture_model==available_texture_models?first>
										checked
									</#if>
								>
							<@default_character_avatar texture_model 54/>
							<div class="texture-model-info">
								<@character_model_name texture_model/>
								<div class="text-muted">
									${msg("character.model.${texture_model}.description")}
								</div>
							</div>
						</label>
					</div>
				</#list>
			</div>
		</div>
		<div>
			<button id="submit-btn" class="btn btn-primary" type="submit">${msg("action.character.create.submit")}</button>
		</div>
	</form>
</@>

<@page_end>
	<@js js_page_common/>
	<@js js_bootstrap_validator/>
	<@js "/js/create-character.js"/>
</@>
