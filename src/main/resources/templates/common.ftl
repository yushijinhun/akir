<#include "libs.ftl">

<#-- reusable elements -->
<#macro meta_csrf>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
</#macro>

<#macro js path>
<script src="${path}"></script>
</#macro>

<#macro css path>
<link href="${path}" rel="stylesheet">
</#macro>

<#macro jslib path>
<script src="${view_config.assetsCdn}${path}"></script>
</#macro>

<#macro csslib path>
<link href="${view_config.assetsCdn}${path}" rel="stylesheet">
</#macro>


<#-- structures -->
<#macro page_head>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<@meta_csrf/>
		<@css "/css/background.css"/>
		<@csslib css_bootstrap/>
		<#nested>
	</head>
	<body>
		<div class="container">
</#macro>

<#macro page_end>
		</div>
		<@jslib js_jquery/>
		<@jslib js_bootstrap/>
		<@jslib js_jquery_serialize_object/>
		<@js "/js/csrf.js"/>
		<#nested>
	</body>
</html>
</#macro>

<#macro title title>
<title>${title} - ${akir_server.name}</title>
</#macro>
