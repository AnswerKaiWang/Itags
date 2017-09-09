<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
	</head>
	<body>
		<div>
			<div class="x1">
				 <span class="glyphicon glyphicon-user"></span>  联系人
				  <a href="javascript:closeChatBlock()" class="right right-mar">
			      	<span class="glyphicon glyphicon-minus"></span>
			      </a>
			</div>
			<div class="x2">
				<div class="x5">
					<input type="text" class="form-control" placeholder="Search" id = "linkManKey">
					<a href = "javascript:searchLinkMan()">
						<span class="glyphicon glyphicon-search"></span>
					</a>
				</div>
				<#if linkMans?has_content>
					<#list linkMans as linkMan>
						<a href="javascript:turnToChat(${linkMan.userId})">
							<div class="x3">
								<img src="${linkMan.head}" class="left" />
								<strong><span>${linkMan.userName}</span></strong>
								<@chatCountDirective userId = userId personUserId = linkMan.userId>
									<#if unReadCount gt 0>
										<span class = "t8">${unReadCount}</span>
									</#if>
								</@chatCountDirective>
								
							</div>
						</a>
					</#list>
				<#else>
					<div class = "f1">暂无联系人</div>
				</#if>
				
			</div>
		</div>
	</body>
</html>
