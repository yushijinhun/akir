<#import "../spring.ftl" as spring/>
<#include "libs.ftl">

<#-- reusable elements -->
<#function url url extra={}>
	<#if extra?has_content>
		<#return springMacroRequestContext.getContextUrl(url,extra)>
	<#else>
		<#return springMacroRequestContext.getContextUrl(url)>
	</#if>
</#function>

<#function user_profile_url user>
	<#return url("/user/{name}",{"name":user.name})>
</#function>

<#function character_url character>
	<#return url("/character/${character.uuid}")>
</#function>

<#function msg key args=[]>
	<#if args?size==0>
		<#return springMacroRequestContext.getMessage(key)/>
	<#else>
		<#return springMacroRequestContext.getMessage(key,args)/>
	</#if>
</#function>

<#macro js path>
	<script src="${url(path)}"></script>
</#macro>

<#macro css path>
	<link href="${url(path)}" rel="stylesheet">
</#macro>

<#macro metadata key value>
	<meta name="akir:${key}" content="${value}" />
</#macro>

<#assign lang_metadata_locale = msg("metadata.locale") />

<#-- structures -->
<#macro page_head>
	<!DOCTYPE html>
	<html lang="${msg("metadata.lang")}">
		<head>
			<meta charset="utf-8">
			<meta name="viewport" content="width=device-width, initial-scale=1">
			<meta name="_csrf" content="${_csrf.token}"/>
			<meta name="_csrf_header" content="${_csrf.headerName}"/>
			<@metadata key="lang_json" value=url("/js/lang/locale_${lang_metadata_locale}.json")/>
			<@metadata key="site_name" value=akir_server.name/>
			<@css css_bootstrap/>
			<@css css_font_awesome/>
			<#nested>
		</head>
		<body>
</#macro>

<#macro page_end>
			<@js js_jquery/>
			<@js "/js/i18n.js"/>
			<@js "/js/csrf.js"/>
			<@js js_bootstrap/>
			<@js js_jquery_serialize_object/>
			<@js "/js/common.js"/>
			<#nested>
		</body>
	</html>
</#macro>

<#macro title>
	<title><#nested> - ${akir_server.name}</title>
</#macro>

<#-- gravatar -->
<#function gravatar_url user size>
	<#return url("https://www.gravatar.com/avatar/${ext.md5(user.email)}?default=mm&size=${size?c}")>
</#function>

<#macro gravatar_img user size>
	<img class="gravatar" alt="@${user.name}" src="${gravatar_url(user,size*2)}" width="${size}" height="${size}">
</#macro>

<#-- character avatar -->
<#function texture_url texture_id>
	<#return url("/yggdrasil/textures/texture/${texture_id}")>
</#function>

<#macro character_avatar_by_id texture_id size>
	<#local img_url=texture_url(texture_id)>
	<span class="character-avatar">
		<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="${size?c}" height="${size?c}">
			<image class="character-avatar-img-head" x="0" y="0" width="${(size/9*8*8)?c}" height="${(size/9*8*8)?c}" xlink:href="${img_url}" />
			<image class="character-avatar-img-helm" x="0" y="0" width="${(size*8)?c}" height="${(size*8)?c}" xlink:href="${img_url}" />
		</svg>
	</span>
</#macro>

<#macro character_avatar character size>
	<@character_avatar_by_id ext.skinTextureId(character) size/>
</#macro>

<#macro default_character_avatar model size>
	<@character_avatar_by_id ext.defaultSkinTextureId(model) size/>
</#macro>

<#-- form validation -->
<#macro form_feedback_icon>
	<span class="glyphicon form-control-feedback"></span>
</#macro>

<#macro form_helper>
	<div class="help-block">
		<#nested>
	</div>
</#macro>

<#macro form_helper_errors>
	<div class="help-block with-errors"></div>
</#macro>

<#-- character model name display -->
<#assign character_model_icons={
	"steve":"mars",
	"alex":"venus"
}>

<#macro character_model_name character_model>
	<#local character_model=character_model?lower_case>
	<div class="character-model-name character-model-name-${character_model}">
		<span class="text-muted">
			<i class="fa fa-${character_model_icons[character_model]}"></i>
			${msg("character.model.${character_model}")}
		</span>
	</div>
</#macro>

<#-- modal -->
<#macro modal id>
	<div class="modal fade" id="${id}" tabindex="-1" role="dialog" >
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<#nested>
			</div>
		</div>
	</div>
</#macro>

<#macro modal_header>
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
		<h4 class="modal-title"><#nested></h4>
	</div>
</#macro>

<#macro modal_body>
	<div class="modal-body">
		<#nested>
	</div>
</#macro>

<#macro modal_footer>
	<div class="modal-footer">
		<#nested>
	</div>
</#macro>

<#macro modal_btn_close content="">
	<button type="button" class="btn btn-default" data-dismiss="modal">
		<#if content=="">
			${msg("general.cancel")}
		<#else>
			${content}
		</#if>
	</button>
</#macro>
