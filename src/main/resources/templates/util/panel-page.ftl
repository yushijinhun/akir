<#macro panel_page>
<div class="container">
	<div id="root-panel" class="panel panel-default">
		<div class="panel-body">
			<#nested>
		</div>
	</div>
</div>
</#macro>

<#macro panel_title>
<h3 class="inpanel-title"><#nested></h3>
</#macro>
