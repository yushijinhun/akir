<#include "util/common.ftl">
<#include "util/page.ftl">

<#assign character_model_icons={
	"steve":"mars",
	"alex":"venus"
}>

<@page_head>
	<@title>${showing_user.name}</@>
	<@css css_page_common/>
	<@css "/css/user-profile.css"/>
</@>

<@page_body>
<div class="row">
	<div class="col-sm-4 col-xs-12 vcard-col">
		<div class="vcard-content avatar-profile-col">
			<@gravatar_img user=showing_user size=200/>
		</div>
		<div class="vcard-content vcard-name">
			<span>${showing_user.name}</span>
		</div>
		<#if showing_user.bio??>
			<div class="vcard-content vcard-bio text-muted">
				<span>${user.bio}</span>
			</div>
		</#if>
		<hr class="hidden-xs">
		<ul class="vcard-content vcard-details">
			<li>
				<span class="fa fa-clock-o"></span>
				<#assign join_time=showing_user.registerTime?number_to_datetime>
				<span>
					<@msg "user_details.joined_on"/>
					<time datetime="${join_time?iso_utc}" title="${join_time?string.full}">
						${join_time?date?string.iso}
					</time>
				</span>
			</li>
			<li>
				<span class="fa fa-envelope-o"></span>
				<a href="mailto:${showing_user.email}">${showing_user.email}</a>
			</li>
		</ul>
		<hr class="visible-xs-block">
	</div>
	<div class="col-sm-8 col-xs-12">
		<ul class="nav nav-tabs">
			<li role="presentation" class="active"><a href="#"><@msg "user_details.characters"/></a></li>
		</ul>
		<#list user_characters>
			<ul class="character-list">
			<#items as character>
				<#assign character_url=url("/character/${character.uuid}")>
				<#assign character_model=character.model?lower_case>
				<li>
					<div class="character-avatar">
						<a href="${character_url}">
							<@character_avatar character 54/>
						</a>
					</div>
					<div>
						<div class="character-name">
							<a class="link-grey-dark" href="${character_url}">
								<span class="h4">
									${character.name}
								</span>
							</a>
						</div>
						<div class="character-model character-model-${character_model}">
							<span>
								<i class="fa fa-${character_model_icons[character_model]}"></i>
								<@msg "character.model.${character_model}"/>
							</span>
						</div>
					</div>
				</li>
			</#items>
			</ul>
		<#else>
			<p class="text-muted no-character-tip"><@msg "user_details.no_character"/></p>
		</#list>
	</div>
</div>
</@>

<@page_end>
	<@js js_page_common/>
	<@js "/js/user-profile.js"/>
</@>
