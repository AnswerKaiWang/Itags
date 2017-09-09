<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>ITags</title>
		<link rel="shortcut icon" href="${ctx}/images/favicon.ico"  type="image/x-icon"/>
		<link rel="stylesheet" href="${ctx}/statics/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx}/statics/sweetAlert/sweetalert.css" />
		<link rel="stylesheet" href="${ctx}/css/newIndex.css" />
		<link rel="stylesheet" href="${ctx}/css/news.css" />
		<script type="text/javascript" src="${ctx}/js/jquery-1.11.3.js"></script>
		<script type="text/javascript" src="${ctx}/statics/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${ctx}/statics/sweetAlert/sweetalert.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/news.js"></script>
		<script>
			var userId = "${userId?if_exists}";
			var ctx = "${ctx}";
		</script>
	</head>
	<body>
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="${ctx}/index">ITags</a>
				</div>
				<div>
					<form class="navbar-form navbar-left" role="search">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Search" id = "searchData" value = "<#if key?has_content>${key}</#if>">
						</div>
						<a href="${ctx}/index" id = "searchMan" >搜索</a>
						<a href = "${ctx}/news/index/660" class = "top" >
					          <span class="glyphicon glyphicon-home"  <#if location == "top">style="color: rgb(255, 140, 60);"</#if>></span> 首页
						</a>
					</form>
				</div>
				<#if userId?has_content>
					<div  id="message">
				        <a href = "javascript:void(0)" id="submit">
				          <span class="glyphicon glyphicon-edit"></span> 发表
				        </a>
				        <a href = "${ctx}/notice">
					          <span class="	glyphicon glyphicon-bell"></span> <span id = "noticeCount">
					          	消息<#if userInfo.noticeCount gt 0>(${userInfo.noticeCount})</#if>
					          </span>
				        </a>
				        <a href = "javascript:void(0)" id = "personZone">
				          <span class="glyphicon glyphicon-user"></span> 个人中心
						</a>			        
				    </div>
					<div>
				        <ul class="nav navbar-nav nickName">
				            <li class="dropdown">
				                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
				                    	${userInfo.userName}
				                    <b class="caret"></b>
				                </a>
				                <ul class="dropdown-menu">
				                    <li><a href="javascript:loginOut()">退出登录</a></li>
				                    <#--<li><a href="javascript:void()" data-target="#avatar-modal" data-toggle="modal">更换头像</a></li>-->
				                </ul>
				            </li>
				        </ul>
	    			</div>
					<a href="javascript:viewHead()"><img src="${userInfo.head}" id="head" /></a>
				<#else>
					<ul class="nav navbar-nav navbar-right" id = "loginModel">
						<li>
							<a href="${ctx}/login"><span class="glyphicon glyphicon-user"></span> 注册</a>
						</li>
						<li>
							<a href="${ctx}/login"><span class="glyphicon glyphicon-log-in"></span> 登录</a>
						</li>
					</ul>
				</#if>
				

			</div>
		</nav>
			<#if newsChange == "newsDetail.ftl">
		         <button type="button" class="btn btn-primary btn-sm" id = "backNewsIndex" onclick="javascript:window.history.back(-1);">
		           <span class="glyphicon glyphicon-chevron-left"></span> 返回
		         </button>
		    </#if>
		<#include "${newsChange}">
		
		
		
	</body>

</html>