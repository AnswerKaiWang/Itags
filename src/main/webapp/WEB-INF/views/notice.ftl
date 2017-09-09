<link rel="stylesheet" href="${ctx}/css/notice.css" />
<script type="text/javascript" src="${ctx}/js/notice.js" ></script>
<title>消息中心</title>
<div class="d1">
	<@noticeDirective userId = userId page = page>
		<#if noticePageInfo.getList()?has_content>
			<#list noticePageInfo.getList() as notice>
				<div class="panel panel-default">
					<div class="panel-heading">
				        <h3 class="panel-title">
				            <#if notice.type == 1>
				            	<#assign condition = "收藏">
				            	${condition}
				            <#elseif notice.type == 2>
				            	<#assign condition = "转发">
				            	${condition}
				            <#elseif notice.type == 3>
				            	<#assign condition = "评论">
				            	${condition}
				            <#elseif notice.type == 4>
				            	<#assign condition = "赞">
				            	${condition}
				            <#else>
				            	<#assign condition = "回复">
				            	${condition}
				            </#if>
				        </h3>
				    </div>
				    	<div class="panel-body">
				    		<#if notice.type == 1 || notice.type == 4 || notice.type == 2>
					       		<a href="${ctx}/person?personUserId=${notice.referenceUserId}">
					       			${notice.referenceUser.userName} 
					       		</a>: ${condition}了你的动态 <br />
					       	<#elseif notice.type == 3>
					   	    	<a href="${ctx}/person?personUserId=${notice.referenceUserId}">
					   	    		${notice.referenceUser.userName} 
					   	    	</a>: ${notice.extraMsg.content}
					   	    <#else>
					   	    	<a href="${ctx}/person?personUserId=${notice.referenceUserId}">
					   	    		${notice.referenceUser.userName} 
					   	    	</a>:
					   	    	<@noticeExtraMsgDirective msgId = notice.referenceMsgId>
					   	    		 ${extraMsg.content}
					       		</@noticeExtraMsgDirective>
					       	</#if>
					       	<#if notice.type == 5>
					       		<#assign tempType = "extra">
					       	<#else>
					       		<#assign tempType = "root">
					       	</#if>
				       		<a href="">
						       	<div class="comment">
						       		<@noticeMsgDirective msgId = notice.msgId type = tempType>
						       			<#if msg.content != "<p></p>">
						       				${msg.content}
						       			<#else>
						       				[图片]
						       			</#if>
						       		</@noticeMsgDirective>
						       	</div>
					       	</a>
					       	<div class="d2">
					       		${notice.createTime?string("M月dd日 HH:mm")}
					       		<#if notice.type == 3 || notice.type == 5>
					       			<a href = "javascript:void(0)" onclick = "openEditBlock(this)">回复</a>
					       		</#if>
					       	</div>
					       	<br />
					    </div>
					<#if notice.type == 3 || notice.type == 5>
						<div class="panel-footer" style="display: none;">
					    	<div class="input-group">
					            <input type="text" class="form-control">
					            <input type = "hidden" value = "${notice.referenceUserId}">
					            <input type = "hidden" value = "<#if notice.type == 3>${notice.referenceMsgId}<#else> ${notice.msgId}</#if>">
					            <span class="input-group-btn">
									<button class="btn btn-default" type="button" id = "replyBtn" onclick = "replyMsg(this)">
										提交
									</button>
								</span>
					        </div>
					    </div>
				    </#if>
				</div>
			</#list>
		<!--遍历导航菜单开始-->
			<ul class="pagination ">
				<#if noticePageInfo.hasPreviousPage>
					<li><a href="${ctx}/notice?page=${noticePageInfo.prePage}">&laquo;</a></li>
				</#if>
				<#list noticePageInfo.getNavigatepageNums() as tempPage>
					<li class = "<#if noticePageInfo.pageNum == tempPage>active</#if>">
						<a href="${ctx}/notice?page=${tempPage}">${tempPage}</a>
					</li>
				</#list>
				<#if noticePageInfo.hasNextPage>
					<li><a href="${ctx}/notice?page=${noticePageInfo.nextPage}">&raquo;</a></li>
				</#if>
			</ul>
			
	<!--遍历导航菜单结束-->
			
			
			
		<#else>
			暂无消息
		</#if>
	</@noticeDirective>
</div>
