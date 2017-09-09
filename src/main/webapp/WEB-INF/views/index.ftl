<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title>ITags</title>
		<link rel="shortcut icon" href="${ctx}/images/favicon.ico"  type="image/x-icon"/>
		<link rel="stylesheet" href="${ctx}/statics/bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" href="${ctx}/css/zoomify.min.css">
		<link rel="stylesheet" href="${ctx}/css/newIndex.css" />
		<link rel="stylesheet" href="${ctx}/css/msg.css" />
		<link rel="stylesheet" href="${ctx}/statics/sweetAlert/sweetalert.css" />
		<script type="text/javascript" src="${ctx}/js/common.js"></script>
		<script type="text/javascript" src="${ctx}/js/jquery-1.11.3.js"></script>
		<script type="text/javascript" src="${ctx}/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="${ctx}/statics/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/index.js"></script>
		<script type="text/javascript" src="${ctx}/statics/sweetAlert/sweetalert.min.js"></script>
		<script type="text/javascript" src="${ctx}/js/zoomify.min.js"></script>
		<script>
			var userId = "${userId}";
			var ctx = "${ctx}";
			var userName1 = "${userInfo.userName}";
			var head = "${userInfo.head}";
		</script>
	</head>

	<body>
		<input type = "hidden" value = ${userInfo.userName} name = "userName"/>
		<input type = "hidden" value = ${userId} name = "userId"/>
		<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
			<div class="container-fluid">
				<div class="navbar-header">
					<a class="navbar-brand" href="${ctx}/index"  <#if location == "dynamic">style="color: rgb(255, 140, 60);"</#if>  >ITags</a>
				</div>
				<div>
					<form class="navbar-form navbar-left" role="search">
						<div class="form-group">
							<input type="text" class="form-control" placeholder="Search" id = "searchData" value = "<#if key?has_content>${key}</#if>">
						</div>
						<a href="javascript:void(0)" onclick="searchPerson(this)" id = "searchMan" >搜索</a>
						<a href = "${ctx}/news/index/660" class = "top"
						  <#if location == "top">style="color: rgb(255, 140, 60);"</#if>  >
					          <span class="glyphicon glyphicon-home"></span> 首页
						</a>
					</form>
				</div>
				<div  id="message">
			        <a href = "javascript:void(0)" id="submit">
			          <span class="glyphicon glyphicon-edit"></span> 发表
			        </a>
			        <a href = "${ctx}/notice"  <#if location == "notice">style="color: rgb(255, 140, 60);"</#if>  >
				          <span class="	glyphicon glyphicon-bell"></span> <span id = "noticeCount">
				          	消息<#if userInfo.noticeCount gt 0>(${userInfo.noticeCount})</#if>
			        </a>
			        <a href = "javascript:void(0)" id = "personZone"  <#if location == "personZone">style="color: rgb(255, 140, 60);"</#if> >
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
			</div>
		</nav>

	<#include "${change}">
	
	
	
	
<!--动态消息列表结束-->	
		<!--动态结束-->
		
		<div id = "editBlock">
			<h5>有什么新鲜事告诉大家</h5>
			<!-- 加载编辑器的容器 -->
			<a href = "javascript:closeEditBlock()"> 
				× 
			</a>
			<script id="container" name="content" type="text/plain">
				
			</script>
			<script type="text/javascript" src="${ctx}/ueditor/ueditor.config.js"></script>
			<script type="text/javascript" src="${ctx}/ueditor/ueditor.all.js"></script>
			<script type="text/javascript">
				var ue = UE.getEditor('container', {
					toolbars: [
						['simpleupload',
							'insertimage', 'snapscreen', 'emotion']
						]
				});
				UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
				UE.Editor.prototype.getActionUrl = function(action) {
				    if (action == 'uploadimage' || action == 'uploadfile') {
				        var id = $('#carInfoId').val();
				           return ctx + '/test/test.do';
				    } else {
				        return this._bkGetActionUrl.call(this, action);
				    }
				};
				// 复写UEDITOR的getContentLength方法 解决富文本编辑器中一张图片或者一个文件只能算一个字符的问题,可跟数据库字符的长度配合使用
				//UE.Editor.prototype._bkGetContentLength = UE.Editor.prototype.getContentLength;
				//UE.Editor.prototype.getContentLength = function(){
				//    return this.getContent().length;
				//}
			</script>
			<div class = "btnGroup">
				 <select class="form-control visibility" id = "visibility">
					<option value = "1">公开</option>
					<option value = "0">仅自己可见</option>
				</select>
				<button type="button" class="btn btn-info" id = "subDynamic">发表</button>
			</div>
		</div>
		
		<div class="temp1" id = "chatBlock">
		</div>
		<div class = "chatIcon">
			<a href="javascript:openChatBlock()">
	          <span class="glyphicon glyphicon-phone"></span> 私信
	        </a>
		</div>
		
		<div class = "p-data">
			<a href = "" class = "a1">
				<div class = "p-top">
					<img src = "images/personBac/009.jpg" class = "bac"/>
					<img src = "images/pic08.jpg" class = "p-head" />
					<div class = "detail">
					</div>
					<div class = "detail2">
						<span class = "userName"></span>
						<img src = "images/man.png"  class = "sex"/>
					</div>
				</div>
			</a>
			<div class = "p-bottom">
				<div class = "p-bottom1">
					<a href = "/ITags/person/personUserId/follow" class = "a2">
						<div class = "count r-border follow"><strong>关注 100 </strong></div>
					</a>
					<a href = "/ITags/person/personUserId/followed" class = "a3">
						<div class = "count r-border followed"><strong>粉丝 32 </strong></div>
					</a>
					<a href = "/ITags/person/personUserId/dynamic" class = "a4">
						<div class = "count dynamic"><strong>动态 12 </strong></div>
					</a>
				</div>
				<div class = "addr">
				</div>
				<div class="btn-group closeBtn">
				    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
				    	 <span class="glyphicon glyphicon-ok"></span> 已关注
				        <span class="caret"></span>
				    </button>
				    <ul class="dropdown-menu" role="menu">
				        <li>
				            <a href="javascript:void()" onclick = "cancelFollow(this)">取消关注</a>
				        </li>
				    </ul>
				</div>
				<button class = "btn btn-default followBtn closeBtn" onclick = "follow(this)"><span class="glyphicon glyphicon-plus" style="color: rgb(255, 140, 60);"></span> 关注他</button>
				<button class = "btn btn-default msg closeBtn"  onclick = "sendPrivateMsg(this)">私信</button>
				<input type = "hidden" class = "followId" />
			</div>
		</div>
		
	
	</body>

</html>