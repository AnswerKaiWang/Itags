	
	
	<input type = "hidden" value = "${type}" id = "type"/>
	<div class="container">
	    <div class="leftNav">
	    	<a href="${ctx}/news/index/660" >
	    		<div class="subNav <#if type == 660>select</#if>" >
	    			热点
	    		</div>
	    	</a>
	    	<a href="${ctx}/news/index/9">
	    		<div class="subNav <#if type == 9>select</#if>">
	    			娱乐
	    		</div>
	    	</a>
	    	<a href="${ctx}/news/index/7">
	    		<div class="subNav <#if type == 7>select</#if>">
	    			汽车
	    		</div>
	    	</a>
	    	<a href="${ctx}/news/index/8">
	    		<div class="subNav <#if type == 8>select</#if>">
	    			体育
	    		</div>
	    	</a>
	    	<a href="${ctx}/news/index/13">
	    		<div class="subNav <#if type == 13>select</#if>">
	    			科技
	    		</div>
	    	</a>
	    	<a href="${ctx}/news/index/1">
	    		<div class="subNav <#if type == 1>select</#if>">
	    			国内
	    		</div>
	    	</a>
	    	<a href="${ctx}/news/index/2">
	    		<div class="subNav <#if type == 2>select</#if>">
	    			国际
	    		</div>
	    	</a>
	    	<a href="${ctx}/news/index/3">
	    		<div class="subNav <#if type == 3>select</#if>">
	    			军事
	    		</div>
	    	</a>
	    	<a href="${ctx}/news/index/4">
	    		<div class="subNav <#if type == 4>select</#if>">
	    			财经
	    		</div>
	    	</a>
	    	<a href="${ctx}/news/index/5">
	    		<div class="subNav <#if type == 5>select</#if>">
	    			互联网
	    		</div>
	    	</a>
	    	<a href="${ctx}/news/index/11">
	    		<div class="subNav <#if type == 11>select</#if>">
	    			教育
	    		</div>
	    	</a>
	    </div>
	    <div class="rightNav">
	    	<#if type == 660>
	    		<div id="myCarousel" class="carousel slide">
					<!-- 轮播（Carousel）指标 -->
					<ol class="carousel-indicators">
						<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
						<li data-target="#myCarousel" data-slide-to="1"></li>
						<li data-target="#myCarousel" data-slide-to="2"></li>
					</ol>   
					<!-- 轮播（Carousel）项目 -->
					<div class="carousel-inner">
						<#list news.getList() as tempNews3>
							<#if tempNews3_index lt 3>
									<div class="item <#if tempNews3_index == 0> active </#if>" onclick = "turnToDetail('${tempNews3.url}')">
										<#if tempNews3.pic?has_content>
											<img src="${tempNews3.pic}" alt="First slide">
										<#else>
											<img src="${ctx}/images/pic02.jpg" alt="First slide">
										</#if>	
										<div class="carousel-caption">${tempNews3.title}</div>
									</div>
							</#if>
						</#list>			
					</div>
					<!-- 轮播（Carousel）导航 -->
					<a class="carousel-control left" href="#myCarousel" 
					   data-slide="prev">&lsaquo;</a>
					<a class="carousel-control right" href="#myCarousel" 
					   data-slide="next">&rsaquo;</a>
				</div> 
	    	</#if>	
	    	<div class="newsBlank">
				<#list news.list as tempNews>
					<#if type == 660>
						<#if tempNews_index gt 2>
							<@newsList tempNews2 = tempNews>
							</@newsList>
						</#if>
					<#else>
						<@newsList tempNews2 = tempNews>
						</@newsList>
					</#if>
				</#list>
	    		<#macro newsList tempNews2>
	    			<a href = "${ctx}/news/newsDetail?url=${tempNews2.url}">
						<div class="news">
			    			<#if tempNews2.pic?has_content>
			    				<img src="${tempNews2.pic}" />
			    			</#if>
			    			<div class="content">
			    				<div class = "newsContent">
			    					<h3>${tempNews2.title}</h3>
			    				</div>
			    			<div class = "subTitle">
			    			${tempNews2.publishTime}&nbsp;&nbsp;&nbsp
			    			来源：${tempNews2.author}   &nbsp;&nbsp;&nbsp
			    			${tempNews2.comment}
			    			</div> 
			    			</div>
			    		</div>
		    		</a>
		    		<hr />
	    		</#macro>
	    		<#if news.hasNextPage>
	    			<input type = "hidden" value = "${news.nextPage}" id = "nextPage"/>
	    		</#if>
	    	</div>
	</div>
	
	<!--最右侧热点消息-->
	<div class = "hotBlank">
		<a href = "http://www.12377.cn/" target = "_blank">
			<img src = "/ITags/images/report.png" class = "report"/>
		</a>
	
		<@hotNewsDirective type = type>
			<div class = "title">
				24小时热榜
			</div>
			<#list newses.newsest as tempNews>
				<a href = "${ctx}/news/newsDetail?url=${tempNews.url}">
					<div class = "hotNews">
						<div class = "subTitle">
							${tempNews.title}
						</div>
						<div class = "newsDetail">
							${tempNews.author}   ${tempNews.publishTime}
						</div>
					</div>
				</a>
			</#list>
			<div class = "title">
				最新资讯
			</div>
			<#list newses.hotNews as tempNews>
				<a href = "${ctx}/news/newsDetail?url=${tempNews.url}">
					<div class = "hotNews">
						<div class = "subTitle">
							${tempNews.title}
						</div>
						<div class = "newsDetail">
							${tempNews.author}   ${tempNews.publishTime}
						</div>
					</div>
				</a>
			</#list>
		</@hotNewsDirective>
		
		<div class = "bottomBlank">
			Copyright © 2017- kiTe.All Rights Reserved <br/>
			
			<a href = "http://www.miibeian.gov.cn" target = "_blank"/>皖ICP备17017909号-1</a> </br>
			<a href = "http://www.12377.cn/" target = "_blank"/>违法和不良信息举报电话：020-38465849</a> </br>
			<a href = "http://www.myzaker.com/" target = "_blank"/>网站数据源来自 ZAKER</a> </br>
			联系方式：mcyuaner@foxmail.com</br>
			<a href = "http://www.myzaker.com/" target = "_blank"/>关于本站  意见反馈</a> </br>
		</div>
		
		
	</div>
	
	
	<div class = "backToTop">
		<button type="button" class="btn btn-default btn-sm" onclick = "backToTop()">
          <span class="glyphicon glyphicon-open"></span> 返回顶部
        </button>
	</div>
	
	
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
				UE.Editor.prototype._bkGetContentLength = UE.Editor.prototype.getContentLength;
				UE.Editor.prototype.getContentLength = function(){
				    return this.getContent().length;
				}
			</script>
			<div class = "btnGroup">
				 <select class="form-control visibility" id = "visibility">
					<option value = "1">公开</option>
					<option value = "0">仅自己可见</option>
				</select>
				<button type="button" class="btn btn-info" id = "subDynamic">发表</button>
			</div>
		</div>
	
	</body>

</html>