<link href="${ctx}/statics/headUpload/head/cropper.min.css" rel="stylesheet">
<link href="${ctx}/statics/headUpload/head/sitelogo.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/font-awesome/4.6.0/css/font-awesome.min.css">
<link rel="stylesheet" href="${ctx}/css/personDetail.css" />
<script src="${ctx}/statics/headUpload/head/cropper.js"></script>
<script src="${ctx}/statics/headUpload/head/sitelogo.js"></script>
<script src="${ctx}/statics/headUpload/head/html2canvas.min.js" type="text/javascript" charset="utf-8"></script>
<script src="${ctx}/js/distpicker.data.js"></script>
<script src="${ctx}/js/distpicker.js"></script>
<script src="${ctx}/js/echarts.min.js"></script>
<script src="${ctx}/js/china.js"></script>
<script src="${ctx}/js/main.js"></script>
<script type="text/javascript" src="${ctx}/statics/laydate/laydate.dev.js"></script>
<script src="${ctx}/js/common.js"></script>
<script src="${ctx}/js/personZone.js"></script>
<script type="text/javascript">
	var personUserId = "${personUserId}";
	var type = "${personVo.type}";
</script>
<title>个人中心</title>
<@userInfoDirective personUserId = personUserId>
  <div class = "top">
  		<#if personUserId == userId>
	  		<a href = "javascript:openChangeBlock()">
	  			<img src = "${ctx}/images/changeBac.png" class = "changeBac" title = "点击更换背景"/>
	  		</a>
  		</#if>
		<div class="personBac">
			<img src="${personUserInfo.personBac}" />
		</div>
		<div class="test1">
			<#if personUserId == userId>
				<a href = "javascript:void(0)" data-target="#avatar-modal" data-toggle="modal">
					<img src="${personUserInfo.head}" class="img-responsive" alt="Cinque Terre">
				</a>
			<#else>
				<img src="${personUserInfo.head}" class="img-responsive" alt="Cinque Terre">
			</#if>
			<div class="personDetail">
				<span>${personUserInfo.userName}</span>
				<#if personUserId != userId>
					<a href="javascript:void(0)" data-toggle="modal" data-target="#myModal2" id = "remark">
					<@remarkDirective userId = userId personUserId = personUserId>
						<#if remark?has_content>
							(${remark.remark})
						<#else>
							(设置备注) 
						</#if>
					</@remarkDirective>
					</a>
				</#if>	
			</div>
			<#if personUserId != userId>
				<div class="follow">
				   <@followDirective userId = userId followId = personUserId>
				   		<#if result.resultCode == 1>
				   			<button type="button" class="btn btn-info" value = "${result.result}" id = "follow">已关注</button>
				   		<#else>
				   			<button type="button" class="btn btn-warning" id = "follow">关注</button>
				   		</#if>
					   <button type="button" class="btn btn-default" onclick = "sendMsg()">私信</button>
				   </@followDirective>
				</div>
			</#if>
		</div>
		<div class="m1">
			<div class="">
				<ul id="myTab" class="nav nav-tabs">
					<li <#if personVo.type != 'dynamic'> class = "active"</#if>>
						<a href="#home" data-toggle="tab">
							 主页
						</a>
					</li>
					<li >
						<a href="#ios" data-toggle="tab" id = "dynamicsTab">动态</a>
					</li>
					<li >
						<a href="#pics" data-toggle="tab">相册</a>
					</li>
					<#if userId == personUserId>
						<li >
							<a href="#manage" data-toggle="tab" onclick = "loadEchrtsData()">管理中心</a>
						</li>
					</#if>
					<li >
						<a href="#messageWall" data-toggle="tab" >留言墙</a>
					</li>
				</ul>
			
		
				<div id="myTabContent" class="tab-content">
				<!--管理中心-->
					<div class="tab-pane fade" id="manage">
					<!--男女比例开始-->
						<div class="panel panel-default personAnalysePanel">
							<div class="panel-heading">
								比例分析
							   <button class = "btn btn-default PanelButton" onclick = "changePersonAnalyse(this)" value = "follow">
									切换
								</button>
							</div>
							<div class="panel-body">
								<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
   								 <div id="main" style="width: 450px;height:350px;">
   								 </div>
							</div>
						</div>
					<!--男女比例结束-->
					<!--年龄分析开始-->
						<div class="panel panel-default personAgePanel">
							<div class="panel-heading">
								年龄分析
							   <button class = "btn btn-default PanelButton" onclick = "changeAgeAnalyse(this)" value = "follow">
									切换
								</button>
							</div>
							<div class="panel-body">
								<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
   								 <div id="ageBlock" style="width: 450px;height:350px;">
   								 </div>
							</div>
						</div>
					<!--年龄分析结束-->
					<!--主页访问记录开始-->
						<div class = "panel panel-default visitRecord">
							<div class = "panel-heading">
								主页访问
							</div>
							<div class = "panel-body">
								<div class = "tool" style="width: 900px;height:45px;">
								    <select class="form-control month" onchange = "changeMonthVisit()">
								      <option value = "01">1月</option>
								      <option value = "02">2月</option>
								      <option value = "03">3月</option>
								      <option value = "04">4月</option>
								      <option value = "05">5月</option>
								      <option value = "06">6月</option>
								      <option value = "07">7月</option>
								      <option value = "08">8月</option>
								      <option value = "09">9月</option>
								      <option value = "10">10月</option>
								      <option value = "11">11月</option>
								      <option value = "12">12月</option>
								    </select>
								    <select class="form-control year" onchange = "changeMonthVisit()">
								    </select>
   								 </div>
								 <div id="visitBlock" style="width: 900px;height:400px;">
   								 </div>
							</div>
						</div>
					<!--主页访问记录结束-->
					<!--好友分布图开始-->
						<div class = "panel panel-default personLocationBac">
							<div class = "panel-heading">
								人员分布
								<button class = "btn btn-default PanelButton" onclick = "changeAddressAnalyse(this)" value = "follow">
									切换
								</button>
							</div>
							<div class = "panel-body">
								 <div id="locationBlock" style="width: 900px;height:600px;">
   								 </div>
							</div>
						</div>
						
						
					</div>
				<!--管理中心-->	
				
				<!--相册选项卡-->
					<div class="tab-pane fade" id="pics">
						<div class = "picList">
						<#if personUserId == userId>
							<#assign picType = "self">
						<#else>
							<#assign picType = "other">
						</#if>
						<@personPicDirective userId = personUserId type = picType>
							<#if picList?has_content> 
								<#list picList as pic>
									${pic}
								</#list>
							<#else>
								<div class = "f1">暂无图片</div>
							</#if>
						</@personPicDirective>
						</div>
					</div>
				<!--相册选项卡-->
				<!--主页-->
					<div class="tab-pane fade in active" id="home">
						<div class = "personList">
							<div class="detailFoot">
								<a href="${ctx}/personDetail?type=follow">
									<div class="d1 border <#if personVo.type == "follow">selectBac</#if>">
										关注<br />
										${personUserInfo.followCount}
									</div>
								</a>
								<a href="${ctx}/personDetail?type=followed">
									<div class="d1 border <#if personVo.type == "followed">selectBac</#if>">
										粉丝<br />
										${personUserInfo.followedCount}
									</div>
								</a>
								<a href="javascript:turnToDynamicTab()">
									<div class="d1">
										动态<br />
										${personUserInfo.dynamicCount}
									</div>
								</a>
							</div >
							<!--person列表-->
							<div class = "personBlock">
							<@followUserDirective userId = personUserId type = personVo.type page = personVo.followPage>
								<#if followPageInfo.getList()?has_content>
								<#list followPageInfo.getList() as person>
									<div class = "person <#if person_index == 2 >noBottomBorder</#if>" >
										<a href = "${ctx}/person/${person.userInfo.userId}">
											<img src = "${person.userInfo.head}"/>
										</a>
										<div class = "person1">
											<div class = "person3">
												<a href = "${ctx}/person/${person.userInfo.userId}"><span><strong>${person.userInfo.userName}</strong></span> </a>
												 <span>男</span>
											</div>
											<div class = "person2 rightBoder">
												<a href="${ctx}/person/${person.userInfo.userId}/follow">
													关注 ${person.userInfo.followCount}
												</a>
											</div>
											<div class = "person2 rightBoder">
												<a href="${ctx}/person/${person.userInfo.userId}/followed">
													粉丝 ${person.userInfo.followedCount}
												</a>
											</div>
											<div class = "person2">
												<a href="${ctx}/person/${person.userInfo.userId}/dynamic">
													动态 ${person.userInfo.dynamicCount}
												</a>
											</div>	
											</br>
											<div class = "person4">
												地址：<#if person.userInfo.content?has_content>
														 ${person.userInfo.address}
													 <#else>
														无
													 </#if> </br>
												<p>简介：
													<#if person.userInfo.content?has_content>
														${person.userInfo.content}
													<#else>
														无
													</#if>
												</p>
											</div>
										</div>
									</div>
								</#list>
								<div class = "person5">
									<#if followPageInfo.hasPreviousPage>
										<a href = "${ctx}/personDetail?followPage=${followPageInfo.prePage}">上一页</a>
									</#if>
									<#list followPageInfo.getNavigatepageNums() as tempPage>
										<a href = "${ctx}/personDetail?followPage=${tempPage}"
										  <#if followPageInfo.pageNum == tempPage >class = "sel1"</#if> >
										   ${tempPage}</a>
									</#list>
									 <#if followPageInfo.hasNextPage>
										<a href = "${ctx}/personDetail?followPage=${followPageInfo.nextPage}">下一页</a>
									</#if>
								</div>
								<#elseif personVo.type == "follow">
									<div class = "f1">暂无关注</div>
								<#else>
									<div class = "f1">成为他第一个粉丝吧</div>
								</#if>
							</@followUserDirective>
							</div>
						</div>
					<!--个人资料框开始-->
						<div class="panel panel-default data">
						    <div class="panel-heading">
						        <h3 class="panel-title">
						            	个人资料 
						        </h3>
						        <#if personUserId == userId>
						        	<a href = "javascript:void()" style="float: right;" data-toggle="modal" data-target="#myModal">编辑</a>
						        </#if>
						    </div>
						    <div class="table-responsive">
							  <table class="table">
							    <tbody>
							      <tr>
							        <td>登录名</td>
							        <td>
							        	${personUserInfo.userName}
							        	<#if userId == personUserId>
							        		<a href = "javascrpit:void(0)" data-toggle="modal"
							        		 data-target="<#if personUserInfo.phone?has_content>#myModal5<#else>#myModal6</#if>">修改密码</a>
							        	</#if>
							        </td>
							        </tr>
							      <tr>
							        <td>真实姓名</td>
							        <td><#if personUserInfo.trueName?has_content>${personUserInfo.trueName}</#if></td></tr>
							      <tr>
							        <td>所在地</td>
							        <td><#if personUserInfo.address?has_content>${personUserInfo.address}</#if></td></tr>
							      <tr>
							        <td>性别</td>
							        <td><#if personUserInfo.sex == 1>女<#else>男</#if></td></tr>
							      <tr>
							        <td>生日</td>
							        <td><#if personUserInfo.birthday?has_content>${personUserInfo.birthday}</#if></td></tr>
							      <tr>
							        <td>血型</td>
							        <td><#if personUserInfo.blood?has_content>${personUserInfo.blood}</#if></td></tr>
							     <#if personUserId == userId>
							      <tr>
							        <td>手机号</td>
							        <td>
							      	 	 <#if personUserInfo.phone?has_content>
							      	 		 <input id = "tempPhone" type = "hidden" value = "${personUserInfo.phone}"/>
							        		${personUserInfo.phone} <a href = "javascript:void()" onclick = "cancelPhone(this)">解除绑定</a>
							        	<#else>
							        		<a href = "javascript:void(0)" data-toggle="modal" data-target="#myModal3">立即绑定</a>
							       		</#if>
							        </td></tr>
							      </#if>
							      <tr>
							        <td>简介</td>
							        <td><#if personUserInfo.content?has_content>${personUserInfo.content}</#if></td></tr>
							      <tr>
							        <td>注册时间</td>
							        <td>${personUserInfo.createTime?string("yyyy-MM-dd")}</td></tr>
							      <tr>
							        <td>邮箱</td>
							        <td><#if personUserInfo.email?has_content>${personUserInfo.email}</#if></td></tr>
							      <tr>
							        <td>QQ</td>
							        <td><#if personUserInfo.qq?has_content>${personUserInfo.qq}</#if></td></tr>
							    </tbody>
							  </table>
							</div>
						</div>
					<!--个人资料结束-->
					</div>
				<!--主页结束-->
				
				<!--个人动态开始-->		
					<div class="tab-pane fade" id="ios">
						<#include "personDynamics.ftl">
					</div>
					
				<!--留言墙-->		
					<div class="tab-pane fade" id="messageWall">
						<#include "messageWall.ftl">
					</div>
				</div>
				
			</div>
		</div>
	</div>	
	
<!-- 修改个人资料模态框（Modal） -->
<div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<input type = "hidden" id = "address" <#if personUserInfo.address?has_content>value = "${personUserInfo.address}"</#if>  />
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						个人资料修改
					</h4>
				</div>
				<div class="modal-body">
					<div class="table-responsive">
					 <form id = "fm">
					  <table class="table">
					  	<caption>${personUserInfo.userName}</caption>
					    <tbody>
					    <tr>
					        <td>性别</td>
					        <td>
					        	<label class="checkbox-inline">
								    <input type="radio" name="sex" id="optionsRadios3" value="0" <#if personUserInfo.sex == 0>checked</#if>  > 男
								  </label>
								  <label class="checkbox-inline">
								    <input type="radio" name="sex" id="optionsRadios4" value="1"  <#if personUserInfo.sex == 1>checked</#if>  > 女
								  </label>
					        </td></tr>
					      <tr>
					        <td>真实姓名</td>
					        <td>
					       		<input type="text" class="form-control" name="trueName" <#if personUserInfo.trueName?has_content>value = '${personUserInfo.trueName}'</#if> >
					        </td></tr>
					      <tr>
					        <td>生日</td>
					        <td>
					        	<input type="text" class="form-control" name = "birthday" id="birthday" <#if personUserInfo.birthday?has_content>value = '${personUserInfo.birthday}'</#if> >
					        </td></tr>
					      <tr>
					        <td>血型</td>
					        <td>
					        	<input type="text" class="form-control" name = "blood" <#if personUserInfo.blood?has_content>value = '${personUserInfo.blood}'</#if> >
					        </td></tr>
					      <tr>
					        <td>简介</td>
					        <td>
					        	<input type="text" class="form-control"  name = "content"<#if personUserInfo.content?has_content>value = '${personUserInfo.content}'</#if> >
							</td></tr>
					      <tr>
					        <td>邮箱</td>
					        <td>
					        	<input type="text" class="form-control"  name = "email" <#if personUserInfo.email?has_content>value = '${personUserInfo.email}'</#if> >
							</td></tr>
					      <tr>
					        <td>QQ</td>
					        <td>
					        	<input type="text" class="form-control"  name = "qq" <#if personUserInfo.qq?has_content>value = '${personUserInfo.qq}'</#if> >
					        </td></tr>
					      <tr>
					        <td>所在地</td>
					        <td>
							  <form class="form-inline">
							      <div  id = "distpicker">
							        <div class="form-group" style = "display:inline-block;">
							          <label class="sr-only" for="province">Province</label>
							          <select class="form-control" id="province"  data-province=""></select>
							        </div>
							        <div class="form-group" style = "display:inline-block;">
							          <label class="sr-only" for="city">City</label>
							          <select class="form-control" id="city"></select>
							        </div>
							        <div class="form-group" style = "display:inline-block;">
							          <label class="sr-only" for="district">District</label>
							          <select class="form-control" id="district"></select>
							        </div>
							      </div>
							    </form>
					        </td></tr>
					    </tbody>
					  </table>
					 </form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" id = "modifyData">
						提交更改
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
</div>	
<!--修改个人资料模态框结束-->			
</@userInfoDirective>	


<!--上传头像模态框开始-->		
<div>
	<div class="user_pic" style="margin: 10px;">
			<img src=""/>
		</div>

		<div class="modal fade" id="avatar-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<!--<form class="avatar-form" action="upload-logo.php" enctype="multipart/form-data" method="post">-->
					<form class="avatar-form">
						<div class="modal-header">
							<button class="close" data-dismiss="modal" type="button">&times;</button>
							<h4 class="modal-title" id="avatar-modal-label">上传图片</h4>
						</div>
						<div class="modal-body">
							<div class="avatar-body">
								<div class="avatar-upload">
									<input class="avatar-src" name="avatar_src" type="hidden">
									<input class="avatar-data" name="avatar_data" type="hidden">
									<label for="avatarInput" style="line-height: 35px;">图片上传</label>
									<button class="btn btn-danger"  type="button" style="height: 35px;" onClick="$('input[id=avatarInput]').click();">请选择图片</button>
									<span id="avatar-name"></span>
									<input class="avatar-input hide" id="avatarInput" name="avatar_file" type="file"></div>
								<div class="row">
									<div class="col-md-9">
										<div class="avatar-wrapper"></div>
									</div>
									<div class="col-md-3">
										<div class="avatar-preview preview-lg" id="imageHead"></div>
										<!--<div class="avatar-preview preview-md"></div>
								<div class="avatar-preview preview-sm"></div>-->
									</div>
								</div>
								<div class="row avatar-btns">
									<div class="col-md-4">
										<div class="btn-group">
											<button class="btn btn-danger fa fa-undo" data-method="rotate" data-option="-90" type="button" title="Rotate -90 degrees"> 向左旋转</button>
										</div>
										<div class="btn-group">
											<button class="btn  btn-danger fa fa-repeat" data-method="rotate" data-option="90" type="button" title="Rotate 90 degrees"> 向右旋转</button>
										</div>
									</div>
									<div class="col-md-5" style="text-align: right;">								
										<button class="btn btn-danger fa fa-arrows" data-method="setDragMode" data-option="move" type="button" title="移动">
							            <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;setDragMode&quot;, &quot;move&quot;)">
							            </span>
							          </button>
							          <button type="button" class="btn btn-danger fa fa-search-plus" data-method="zoom" data-option="0.1" title="放大图片">
							            <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;zoom&quot;, 0.1)">
							              <!--<span class="fa fa-search-plus"></span>-->
							            </span>
							          </button>
							          <button type="button" class="btn btn-danger fa fa-search-minus" data-method="zoom" data-option="-0.1" title="缩小图片">
							            <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;zoom&quot;, -0.1)">
							              <!--<span class="fa fa-search-minus"></span>-->
							            </span>
							          </button>
							          <button type="button" class="btn btn-danger fa fa-refresh" data-method="reset" title="重置图片">
								            <span class="docs-tooltip" data-toggle="tooltip" title="" data-original-title="$().cropper(&quot;reset&quot;)" aria-describedby="tooltip866214">
								       </button>
							        </div>
									<div class="col-md-3">
										<button class="btn btn-danger btn-block avatar-save fa fa-save" type="button" data-dismiss="modal"> 保存修改</button>
									</div>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="loading" aria-label="Loading" role="img" tabindex="-1"></div>
</div>
<!--上传头像模态框结束-->		


<!--添加备注模态框开始-->		
<div>
	<div class="modal fade" id="myModal2" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						添加备注
					</h4>
				</div>
				<div class="modal-body">
					备注名称<input class = "form-control" />
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" id = "modifyRemark">
						保存
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
</div>	
<!--添加备注模态框结束-->			
	
		
<!--绑定手机模态框开始-->		
<div>
	<div class="modal fade" id="myModal3" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						绑定手机
					</h4>
				</div>
				<div class="modal-body">
					手机号码
					<input class = "form-control halfWidth" placeholder = "请输入手机号" /> 
					验证码<div class="input-group halfWidth">
		            <input type="text" class="form-control" placeholder = "请输入验证码">
		            <span class="input-group-btn">
						<button class="btn btn-default" type="button" onclick = "getVerifyCode(this)" style = "width: 80px">
							立即获取
						</button>
					</span>
				</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" id = "bindPhone">
						保存
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
</div>			
<!--绑定手机模态框结束-->	

<!--解除绑定手机模态框开始-->					
<div>
	<div class="modal fade" id="myModal4" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						解除绑定手机
					</h4>
				</div>
				<div class="modal-body">
					<input id = "cancelPhone" type = "hidden" /> 
					验证码<div class="input-group ">
		            <input type="text" class="form-control" placeholder = "请输入验证码">
		            <span class="input-group-btn">
						<button class="btn btn-default" type="button" onclick = "getVerifyCode(this)" style = "width: 80px">
							立即获取
						</button>
					</span>
				</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" id = "cancelBindPhone">
						保存
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
</div>		
<!--解除绑定手机模态框结束-->				
		
<!--修改密码模态框开始-->					
<div>
	<div class="modal fade" id="myModal5" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						修改密码
					</h4>
				</div>
				<div class="modal-body">
					新密码<input type = "password" class="form-control" placeholder = "请输入新密码"/> 
					验证码<div class="input-group">
			           		 <input type="text" class="form-control" placeholder = "请输入验证码">
			            	 <span class="input-group-btn">
								<button class="btn btn-default" type="button" onclick = "getModifyPwdVerifyCode(this)" style = "width: 80px">
									立即获取
								</button>
							</span>
						</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" id = "modifyPwdBtn">
						保存
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>
</div>		
<!--修改密码模态框结束-->					
<div>		
	<div class="modal fade" id="myModal6" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						密码修改    
					</h4>
				</div>
				<div class="modal-body">
					<div class="table-responsive">
						<form id = "fm3">
							<input class="form-control" name="userName" type = "hidden" value = "${userInfo.userName}">
							原密码<input type="password" class="form-control" name="oldPwd" >
							<br/>
							新密码<input type="password" class="form-control" name="newPwd" >
							<div></div>
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="button" class="btn btn-primary" id = "modifyPWd2">
						提交更改
					</button>
				</div>
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	</div>		
</div>
		
<div class = "p-bacPanel">		
	<div class="bacPanel">
	    <div class="bacPanel-heading">
	     	<span>   更换背景</span>
	     	<a href = "javascript:closePicPanel()">×</a>
	    </div>
	    <div class="bacPanel-body">
	        <a href = "javascript:void(0)" class = "selPic"> 
	        	<img src = "${ctx}/images/personBac/002.jpg"/>
	        </a>
	        <a href = "javascript:void(0)" class = "selPic"> 
	        	<img src = "${ctx}/images/personBac/003.jpg"/>
	        </a>
	        <a href = "javascript:void(0)" class = "selPic"> 
	        	<img src = "${ctx}/images/personBac/004.jpg"/>
	        </a>
	        <a href = "javascript:void(0)" class = "selPic"> 
	        	<img src = "${ctx}/images/personBac/008.jpg"/>
	        </a>
	         <a href = "javascript:void(0)" class = "selPic"> 
	        	<img src = "${ctx}/images/personBac/009.jpg"/>
	        </a>
	        <a href = "javascript:void(0)" class = "selPic"> 
	        	<img src = "${ctx}/images/personBac/010.jpg"/>
	        </a>
	         <a href = "javascript:void(0)" class = "selPic"> 
	        	<img src = "${ctx}/images/personBac/011.jpg"/>
	        </a>
	        <a href = "javascript:void(0)" class = "selPic"> 
	        	<img src = "${ctx}/images/personBac/013.jpg"/>
	        </a>
	         <a href = "javascript:void(0)" class = "selPic"> 
	        	<img src = "${ctx}/images/personBac/020.jpg"/>
	        </a>
	        <a href = "javascript:void(0)" class = "selPic"> 
	        	<img src = "${ctx}/images/personBac/022.jpg"/>
	        </a>
	    </div>
	    <div class="bacPanel-foot">
			<button type="button" class="btn btn-primary" onclick = "changeBac()">确定</button>
			<button type="button" class="btn btn-default" onclick = "closePicPanel()">取消</button>
		</div>
	</div>
</div>

		
		
		
		