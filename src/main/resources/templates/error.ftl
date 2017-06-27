<#include "util/common.ftl">

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<@ext_css css_bootstrap/>
	</head>
	<body>
		<div class="container">

<h2>${error.error}</h2>
<p>Error code: ${error.code}</p>
<#if error.details??>
<p>${error.details}</p>
</#if>

		</div>
		<@ext_js js_jquery/>
		<@ext_js js_bootstrap/>
	</body>
</html>
