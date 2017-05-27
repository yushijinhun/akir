<#macro panel_page>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
		<link href="css/panel-page.css" rel="stylesheet">
	</head>
	<body>
		<div class="container">
			<div class="panel panel-default">
				<div class="panel-body">
					<#nested>
				</div>
			</div>
		</div>
		<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
		<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	</body>
</html>
</#macro>
