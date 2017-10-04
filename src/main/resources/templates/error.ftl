<#include "util/common.ftl">

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<@title>${error.error}</@>
		<@css css_bootstrap/>
	</head>
	<body>
		<div class="container">
			<h2>${error.error}</h2>
			<p>${msg("error_page.error_code",[error.code?c])}</p>
			<#if error.details??>
				<p>${msg(error.details)}</p>
			</#if>
		</div>
		<@js js_jquery/>
		<@js js_bootstrap/>
	</body>
</html>
