<#import "../spring.ftl" as spring/>
<#include "libs.ftl">

<#-- reusable elements -->
<#function url url>
	<#local result><@spring.url url/></#local>
	<#return result>
</#function>

<#function user_profile_url user>
	<#return url("/user/"+user.name)>
</#function>

<#macro msg key args=[]>
<#if args?size==0>
<@spring.message key/>
<#else>
<@spring.messageArgs key args/>
</#if>
</#macro>

<#macro js path>
<script src="${url(path)}"></script>
</#macro>

<#macro css path>
<link href="${url(path)}" rel="stylesheet">
</#macro>

<#assign lang_metadata_locale><@msg "metadata.locale"/></#assign>

<#-- structures -->
<#macro page_head>
<!DOCTYPE html>
<html lang="<@msg "metadata.lang"/>">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
		<meta name="_lang_json" content="${url("/js/lang/locale_${lang_metadata_locale}.json")}" />
		<@css css_bootstrap/>
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
	<#local img_url="https://www.gravatar.com/avatar/"+ext.md5(user.email)+"?default=mm">
	<#if size??>
		<#local img_url=img_url+"&size="+size?c>
	</#if>
	<#return url(img_url)>
</#function>

<#macro gravatar_img user size>
<img alt="@${user.name}" src="${gravatar_url(user,size)}">
</#macro>
