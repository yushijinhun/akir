<#include "util/common.ftl">
<#include "util/page.ftl">

<#assign is_user_self=showing_user.id==login_user.id>

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
			<#if is_user_self||login_user.admin||user_characters?has_content>
				<ul class="character-list divided-list">
					<#if is_user_self||login_user.admin>
						<li class="add-character-btn">
							<a class="add-element-link" href="${url("/character/new?ownerName={owner}",{"owner":showing_user.name})}">
								<i class="fa fa-plus-square-o fa-3x"></i>
								<span><@msg "user_details.add_character"/></span>
							</a>
						</li>
					</#if>
					<#list user_characters as character>
						<#assign character_profile_url=character_url(character)>
						<li>
							<div class="character-info-avatar">
								<a href="${character_profile_url}">
									<@character_avatar character 54/>
								</a>
							</div>
							<div>
								<div class="character-info-name">
									<a class="link-grey-dark" href="${character_profile_url}">
										<span class="h4">
											${character.name}
										</span>
									</a>
								</div>
								<@character_model_name character.model/>
							</div>
						</li>
					</#list>
				</ul>
			<#else>
				<div class="text-muted no-character-tip"><@msg "user_details.no_character"/></div>
			</#if>
		</div>
	</div>
</@>

<@page_end>
	<@js js_page_common/>
	<@js "/js/user-profile.js"/>
</@>
