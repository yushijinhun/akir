<#import "../spring.ftl" as spring/>
<#include "libs.ftl">

<#-- reusable elements -->
<#macro url url>
<@spring.url url/>
</#macro>

<#macro ext_js path>
<script src="${path}"></script>
</#macro>

<#macro ext_css path>
<link href="${path}" rel="stylesheet">
</#macro>

<#macro js path>
<script src="<@url path/>"></script>
</#macro>

<#macro css path>
<link href="<@url path/>" rel="stylesheet">
</#macro>


<#-- structures -->
<#macro page_head>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="_csrf" content="${_csrf.token}"/>
		<meta name="_csrf_header" content="${_csrf.headerName}"/>
		<meta name="_home_url" content="${home_page_url}"/>
		<@ext_css css_bootstrap/>
		<#nested>
	</head>
	<body>
		<div class="container">
</#macro>

<#macro page_end>
		</div>
		<@ext_js js_jquery/>
		<@ext_js js_bootstrap/>
		<@ext_js js_jquery_serialize_object/>
		<@js "/js/csrf.js"/>
		<@js "/js/common.js"/>
		<#nested>
	</body>
</html>
</#macro>

<#macro title title>
<title>${title} - ${akir_server.name}</title>
</#macro>
