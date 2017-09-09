<title>个人动态</title>

<!--动态消息列表开始-->
	<div class="messageList">
		<#assign personUserId = '${personUserId}'>
		<#assign userId = '${userId}'>
		<#assign page = '${personVo.dynamicPage}'>
		<#assign type = "other">
		<#if personUserId == userId>
			<#assign type = "other-">
		</#if>
		
		
		<@msgDirective userId = userId personUserId = personUserId page = page type = type >
			<#if pageInfo.getList()?has_content>
				<#list pageInfo.getList() as temp>
				<div class="message block">
					<div class="messageTop block">
						<a href = "${ctx}/person/${temp.userId}"><img src="${personUserInfo.head}" class="head" /></a>
						<div class="personDetaill">
							<a href = "${ctx}/person/${temp.userId}"><span><strong>${personUserInfo.userName}</strong></span></a><br />
							${temp.createTime?string("M月dd号  HH:mm")}
						</div>
					</div> 
					<div class="messageDetail block">
						<p>
							${temp.content}	
						</p>
					</div>
					<#assign imgs = "${temp.imgs}">
					<#if  imgs?has_content>
						<div class="messagePic block">
							<#list imgs?split(",") as img>
								${img}
							</#list>
						</div>
					</#if>
					<div class="messageFoot block">
						<input type = "hidden" value = "${temp.id}" class = "msgId"/>
						<input type = "hidden" value = "${temp.userId}" class = "authorId"/>
						<div class="btnStyle">
							<input type = "hidden" value = "${temp.storeCount}"/>
							<a href="javascript:void(0)" class = "store">
								<@stateDirective userId = userId msgId = temp.id type = 1>
					        		<#if stateIds.storeId?has_content>
					        			<span class="glyphicon glyphicon-heart" style="color: rgb(255, 140, 60);"></span>
					        			<span> 收藏(${temp.storeCount})</span>
					        			<span style = "display:none">${stateIds.storeId}</span>
					        		<#else>
					        			<span class="glyphicon glyphicon-heart"></span>
					        			<span> 收藏(${temp.storeCount})</span>
					        		</#if>
					        	</@stateDirective>
				       		 </a>
						</div>
						 <div class="btnStyle">
					        <a href="javascript:void(0)" class = "transfer">
					          <span class="glyphicon glyphicon-share"></span> <span>转发(${temp.transferCount})</span>
					        </a>
				        </div>
				        <div class="btnStyle">
				        	<input type = "hidden" value = "${temp.commentCount}"/>
					       	<a href="javascript:void(0)" class = "comment">
					        	<span class="glyphicon glyphicon-comment"></span><span> 评论(${temp.commentCount})</span>
					        </a>
					    </div>
				        <div>
				        	<input type = "hidden" value = "${temp.goodCount}"/>
					        <a href="javascript:void(0)" class = "good">
					        	<@stateDirective userId = userId msgId = temp.id type = 4>
					        		<#if stateIds.goodId?has_content>
					        			<span class="glyphicon glyphicon-thumbs-up" style="color: rgb(255, 140, 60);"></span>
					        			<span> 赞(${temp.goodCount})</span>
					        			<span style = "display:none">${stateIds.goodId}</span>
					        		<#else>
					        			<span class="glyphicon glyphicon-thumbs-up "></span>
					        			<span> 赞(${temp.goodCount})</span>
					        		</#if>
					        	</@stateDirective>
					        	
					        </a>
				        </div>
					</div>
				<!--评论列表开始-->
					<div class="adviceList block">
						<div class="form-group">
						    <textarea class="form-control" rows="1"></textarea>
						</div>
						<button type="button" class="btn btn-default commSubmit">发表</button>
						<input type = "hidden" class = "msgId" value = "${temp.id}">
						<input type = "hidden" class = "authorId" value = "${temp.userId}">
						<hr>
				<!--遍历评论开始-->
					<#assign tempId = "${temp.id}">
						<@extraMsgDirective msgId = tempId>
						<#if extraMsgPageInfo.getList()?has_content>
							<#list extraMsgPageInfo.getList() as extraMsg>
								<div class="advlice block ">
									<div class="personDetaill">
										<div>
											<a href = "${ctx}/person/${extraMsg.referenceUserId}"><img src="${extraMsg.referenceUser.head}" class="head" /></a>
										</div>
										<div class="adviceDetail">
											<p>
												<a href="${ctx}/person/${extraMsg.referenceUserId}">${extraMsg.referenceUser.userName}
												</a>：${extraMsg.extraMsg.content}
											</p>
											<div class="adviceTime">
												<span>${extraMsg.createTime?string("M月dd号 HH:mm")}</span>
												<input type = "hidden" id = "authorId" value = "${extraMsg.referenceUserId}" }/>
												<input type = "hidden" id = "msgId" value = "${extraMsg.referenceMsgId}" }/>
												<a onclick="addReplyInput(this)" href="javascript:void(0)">回复</a>
											</div>
											<#if extraMsg.getReplyMsg()?has_content>
												<#list extraMsg.getReplyMsg() as replyMsg>
													<div class="replyBlock">
														<p>
															<a href="${ctx}/person/${replyMsg.referenceUserId}">${replyMsg.referenceUser.userName}</a>
															 回复 <a href="${ctx}/person/${replyMsg.userId}">${replyMsg.user.userName}</a>：
															 ${replyMsg.extraMsg.content}
														</p>
														<div class="adviceTime">
															<input type = "hidden" id = "authorId" value = "${replyMsg.referenceUserId}" />
															<input type = "hidden" id = "msgId" value = "${extraMsg.referenceMsgId}" />
															<span>${replyMsg.createTime?string("M月dd日 HH:mm")}</span>
															<a onclick="addReplyInput(this)" href="javascript:void(0)">回复</a>
														</div>
													</div>
												</#list>
											</#if>
									</div>
								</div>
							</div>
							<hr>
							</#list>
							<!--查看更多-->
						<#else>
						<br/>
							<h5>暂无评论，来发表第一个评论吧</h5>
						</#if>
					</@extraMsgDirective>
				<!--遍历评论结束-->	
					</div>
				</div>
			</#list>
				
		<!--遍历导航菜单开始-->
			<ul class="pagination ">
				<#if pageInfo.hasPreviousPage>
					<li><a href="${ctx}/personDetail?dynamicPage=${pageInfo.prePage}&type=dynamic">&laquo;</a></li>
				</#if>
				<#list pageInfo.getNavigatepageNums() as tempPage>
					<li class = "<#if pageInfo.pageNum == tempPage>active</#if>">
						<a href="${ctx}/personDetail?dynamicPage=${tempPage}&type=dynamic">${tempPage}</a>
					</li>
				</#list>
				<#if pageInfo.hasNextPage>
					<li><a href="${ctx}/personDetail?dynamicPage=${pageInfo.nextPage}&type=dynamic">&raquo;</a></li>
				</#if>
			</ul>
			
	<!--遍历导航菜单结束-->
				
				<#else>
					<div class = "f1">暂无动态</div> 
			</#if>
	
			
		</@msgDirective>
		</div>
<!--动态消息列表结束-->	
	</div>