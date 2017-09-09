<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title></title>
	</head>
	<body>
		<div>
			<div class="t1">
				<a href="javascript:turnToLink()" class="b1"> 
					<span class="glyphicon glyphicon-circle-arrow-left"></span>
				</a>
				<a href="">${chatUser.userName}</a>
				<input type = "hidden" value = "${chatUser.userId}" id = "chatUser"/>
				<input type = "hidden" value = "${chatUser.head}" id = "personHead"/>
				<input type = "hidden" value = "${userInfo.head}" id = "userHead"/>
			</div>
			
			<div class="t2">
				<#if chatPageInfo.getList()?has_content>
					<#list chatPageInfo.getList() as chat>
						<#if chat.userId = userId>
							<div class="t4 right">
								<img src="${userInfo.head}" class="right img" />
								<div class="t5 right right-mar">
									${chat.content}
								</div>
							</div>
						<#else>
							<div class="t4 left">
								<img src="${chatUser.head}" class="left img" />
								<div class="t5 left left-mar chat">
									${chat.content}
								</div>
							</div>
						</#if>
						<#if chat_index == chatPageInfo.size - 1>
							<div class="time">
								${chat.createTime?string("M月dd号 H:mm")}
							</div>
						</#if>
					</#list>
				</#if>
				
			</div>
			<div class="t3">
				<div class="input-group">
		            <input type="text" class="form-control">
		            <span class="input-group-btn">
						<button class="btn btn-default" type="button" onclick = "sendChatMsg(this)">
							发送
						</button>
					</span>
				</div>
			</div>
		</div>
	</body>
</html>
