<#assign js_page_common="/js/page-common.js">
<#assign css_page_common="/css/page-common.css">
<#assign navbar_tabs={
	"tab.dashboard":"/dashboard",
	"tab.marketplace":"/marketplace",
	"tab.help":"/help"
}>

<#macro top_navbar current_tab="">
<nav class="navbar navbar-default navbar-static-top">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#top-navbar" aria-expanded="false">
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
				<span class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="${url("/")}">${akir_server.name}</a>
		</div>
		<div class="collapse navbar-collapse" id="top-navbar">
			<ul class="nav navbar-nav">
				<#list navbar_tabs as navbar_tab,navbar_tab_url>
					<#if current_tab?? && navbar_tab==current_tab>
						<li class="active"><a href="${url(navbar_tab_url)}"><@msg navbar_tab/></a></li>
					<#else>
						<li><a href="${url(navbar_tab_url)}"><@msg navbar_tab/></a></li>
					</#if>
				</#list>
			</ul>
			<div class="navbar-right">
				<ul class="nav navbar-nav">
					<li class="dropdown">
						<a id="navbar-user-icon" href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
							<@gravatar_img user=login_user size=40/>
							${login_user.name}
							<span class="caret"></span>
						</a>
						<ul class="dropdown-menu">
							<li><a href="${user_profile_url(login_user)}"><@msg "nav.user.profile"/></a></li>
							<li role="separator" class="divider"></li>
							<li><a href="${url("/settings")}"><@msg "tab.settings"/></a></li>
							<li><a id="logout-link" href="#"><@msg "action.logout"/></a></li>
						</ul>
					</li>
				</ul>
				<form id="navbar-search-form" class="navbar-form" role="search">
					<div class="form-group">
						<input id="navbar-search-box" type="text" class="form-control" placeholder="<@msg "action.search"/>">
					</div>
				</form>
			</div>
		</div>
	</div>
</nav>
</#macro>

<#macro bottom_footer>
<div class="page-footer">
	<div class="container">
		<hr>
		<span class="text-muted">Powered by <a href="${url("https://github.com/yushijinhun/akir")}">akir</a>.</span>
	</div>
</div>
</#macro>

<#macro page_body current_tab="">
<div class="page-content">
	<@top_navbar current_tab/>
	<div class="container">
		<#nested>
	</div>
</div>
<@bottom_footer/>
</#macro>
