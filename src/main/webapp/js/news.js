$(function() {
	//发表动态点击事件
	$("#submit").click(function(){
		$("#editBlock").show();
	})
	//默认状态不可点击
	$("#subDynamic").attr("disabled", true);
	//添加监听事件 检查是否有内容
	ue.addListener("focus",function(){
		$("#subDynamic").attr("disabled", false);
	})
	ue.addListener("blur",function(){
		if(!ue.hasContents()) {
			$("#subDynamic").attr("disabled", true);
		}
	})
	//发表动态
	$("#subDynamic").click(function(){
		var visibility = $("#visibility option:selected").val();
		var content = ue.getContent();
		var param = {
			userId: userId,
			content: content,
			visibility: visibility
		}
		$.ajax({
			type: "post",
			url: ctx + "/msg/addMsg",
			data: param,
			dataType: "json",
			success:function(data) {
				if(data.resultCode == 1) {
					swal({
						  title: "ITags tip!",
						  text: "发表成功！",
						  timer: 1500,
						  showConfirmButton: false
						});
					window.location.href = ctx + "/index";
				} else {
					alert(data.msg);
				}
			}
		})
	})
	$('#myCarousel').carousel({
		interval: 4000
	})
	
	//页面滚动到底加载更多新闻
	var stop=true; 
	$(window).scroll(function(){ 
		var sc = $(window).scrollTop();
		if(sc > 0){
		  $(".backToTop").show();
		}else{
			$(".backToTop").css("display","none");
		}
	    totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop()); 
	    if($(document).height() <= totalheight){ 
	        if(stop==true){ 
	            stop=false; 
	            var type = $("#type").val();
	            var page = $("#nextPage").val();
	            if(page != null) {
	            	 $.ajax({
	 	            	type: "post",
	 	            	url: ctx + "/news/loadMore/" + type + "/" + page,
	 	            	dataType: "json",
	 	            	success: function(data) {
	 	            		var newsList = data.list;
	 	            		var str = "";
	 	            		var news;
	 	            		for(var i = 0; i < newsList.length; i++) {
	 	            			str += "<a href = '/ITags/news/newsDetail?url=";
	 	            			news = newsList[i];
	 	            			str +=news.url + "'><div class='news'>";
	 	            			if(news.pic != null && news.pic != "") {
	 	            				str += "<img src='" + news.pic + "' />"
	 	            			}
	 	            			str += ("<div class='content'><div class = 'newsContent'><h3>"
				    				+ news.title + "</h3></div><div class = 'subTitle'>" + 
				    				news.publishTime + "&nbsp;&nbsp;&nbsp来源：" + news.author + 
				    				"&nbsp;&nbsp;&nbsp" + news.comment + "</div></div></div></a><hr>");
	 	            		}
	 	            		$(".newsBlank").append(str);
	 	            		if(data.hasNextPage) {
	 	            			$("#nextPage").val(data.nextPage);
	 	            		}	
	 	            		stop = true;
	 	            	}
	 	            })
	            }
	        } 
	    } 
	});
	
	
	//个人中心
	$("#personZone").click(function(){
		window.location.href = ctx + "/person/" + userId;
	})
	
})

function backToTop() {
	$('body,html').animate({scrollTop:0},500);
}


function loginOut() {
	$.get(ctx + "/user/loginOut", function(data) {
		if(data.resultCode == 1) {
			window.location.href = ctx + "/login";
		}
	});
	$.removeCookie("userName");
	$.removeCookie("userPwd");
}

function turnToDetail(url) {
	window.location.href = ctx + "/news/newsDetail?url=" + url;
}


function closeEditBlock() {
	$("#editBlock").hide();
}

Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}  