<link href="${ctx}/css/person.css" rel="stylesheet">
<title>查找用户</title>
<@personDirective key = key>
	<#if persons.size() gt 0>
		<div class="d1">
			<#list persons as person>
					<div class="p1">
						<a href = "${ctx}/person/${person.userId}">
							<img src="${person.head}" />
						</a>
						<div class="p2">
							<strong><a href="">${person.userName}</a></strong>
							<#if person.sex == 0> 男 <#else> 女 </#if>  
							<div class="p4">
								<a href="${ctx}/person/${person.userId}/follow" class="p3 border">关注 ${person.followCount}</a>
								<a href="${ctx}/person/${person.userId}/followed" class="p3 border">粉丝 ${person.followedCount}</a>
								<a href="${ctx}/person/${person.userId}/dynamic" class="p3">动态 ${person.dynamicCount}</a>
							</div>
						</div>
					</div>
			</#list>
		</div>
	<#else>
		<div class = "temp2">没有找到相关的人</div>
	</#if>
</@personDirective>