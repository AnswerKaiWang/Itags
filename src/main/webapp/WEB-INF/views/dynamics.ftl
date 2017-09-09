
<!--动态消息列表开始-->
	<div class="messageList">
		<#assign userId = '${userId}'>
		<#assign page = '${page}'>
		<@msgDirective userId = userId page = page type = "self" personUserId = userId>
			<#if pageInfo.getList()?has_content>
				<#list pageInfo.getList() as temp>
				<div class="message block">
					<div class="messageTop block">
						<a href = "${ctx}/person/${temp.authorId}"><img src="${temp.author.head}" class="head" /></a>
						<div class="personDetaill">
							<a href = "${ctx}/person/${temp.authorId}"><span><strong>${temp.author.userName}</strong></span></a><br />
							${temp.msg.createTime?string("M月d号  HH:mm")}
						</div>
						<div class="dropdown hide1">
							<a href="#" data-toggle="dropdown" class = "black">
					          <span class="glyphicon glyphicon-chevron-down"></span>
					        </a>
							<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
								<li role="presentation">
									<a role="menuitem" tabindex="-1" href="javascript:void(0)" onclick="hideDynamic(this,${temp.id})">
										不看此条动态
									</a>
								</li>
							</ul>
						</div>
					</div> 
					<div class="messageDetail block">
						<p>
							${temp.msg.content}	
						</p>
					</div>
					<#assign imgs = "${temp.msg.imgs}">
					<#if  imgs?has_content>
						<div class="messagePic block">
							<#list imgs?split(",") as img>
								${img}
							</#list>
						</div>
					</#if>
					<div class="messageFoot block">
						<input type = "hidden" value = "${temp.msgId}" class = "msgId"/>
						<input type = "hidden" value = "${temp.authorId}" class = "authorId"/>
						<div class="btnStyle">
							<input type = "hidden" value = "${temp.msg.storeCount}"/>
							<a href="javascript:void(0)" class = "store">
								<@stateDirective userId = userId msgId = temp.msg.id type = 1>
					        		<#if stateIds.storeId?has_content>
					        			<span class="glyphicon glyphicon-heart-empty" style="color: rgb(255, 140, 60);"></span>
					        			<span> 收藏(${temp.msg.storeCount})</span>
					        			<span style = "display:none">${stateIds.storeId}</span>
					        		<#else>
					        			<span class="glyphicon glyphicon-heart-empty"></span>
					        			<span> 收藏(${temp.msg.storeCount})</span>
					        		</#if>
					        	</@stateDirective>
				       		 </a>
						</div>
						 <div class="btnStyle">
					        <a href="javascript:void(0)" class = "transfer">
					          <span class="glyphicon glyphicon-share"></span> <span>转发(${temp.msg.transferCount})</span>
					        </a>
				        </div>
				        <div class="btnStyle">
				        	<input type = "hidden" value = "${temp.msg.commentCount}"/>
					       	<a href="javascript:void(0)" class = "comment">
					        	<span class="glyphicon glyphicon-comment"></span><span> 评论(${temp.msg.commentCount})</span>
					        </a>
					    </div>
				        <div>
				        	<input type = "hidden" value = "${temp.msg.goodCount}"/>
					        <a href="javascript:void(0)" class = "good">
					        	<@stateDirective userId = userId msgId = temp.msg.id type = 4>
					        		<#if stateIds.goodId?has_content>
					        			<span class="glyphicon glyphicon-thumbs-up" style="color: rgb(255, 140, 60);"></span>
					        			<span> 赞(${temp.msg.goodCount})</span>
					        			<span style = "display:none">${stateIds.goodId}</span>
					        		<#else>
					        			<span class="glyphicon glyphicon-thumbs-up "></span>
					        			<span> 赞(${temp.msg.goodCount})</span>
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
						<input type = "hidden" class = "msgId" value = "${temp.msgId}">
						<input type = "hidden" class = "authorId" value = "${temp.authorId}">
						<hr>
				<!--遍历评论开始-->
					<#assign tempId = "${temp.msgId}">
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
												<span>${extraMsg.createTime?string("M月d号  HH:mm")}</span>
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
															<span>${replyMsg.createTime?string("M月d号  HH:mm")}</span>
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
			<ul class="pagination">
				<#if pageInfo.hasPreviousPage>
					<li><a href="${ctx}/index?page=${pageInfo.prePage}">&laquo;</a></li>
				</#if>
				<#list pageInfo.getNavigatepageNums() as tempPage>
					<li class = "<#if pageInfo.pageNum == tempPage>active</#if>">
						<a href="${ctx}/index?page=${tempPage}">${tempPage}</a>
					</li>
				</#list>
				<#if pageInfo.hasNextPage>
					<li><a href="${ctx}/index?page=${pageInfo.nextPage}">&raquo;</a></li>
				</#if>
			</ul>
			
	<!--遍历导航菜单结束-->
				
				<#else>
					暂无动态 
			</#if>
		</@msgDirective>
<!--动态消息列表结束-->	
	</div>