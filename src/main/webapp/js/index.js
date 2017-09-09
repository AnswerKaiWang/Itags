$(function() {
	//发表动态点击事件
	$("#submit").click(function(){
		$("#editBlock").show();
		
	})
	//默认状态不可点击
	$("#subDynamic").attr("disabled", true);
	$(".adviceList").hide();
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
	
	//点击评论
	$(".comment").click(function(){
		var adviceList = $(this).parent().parent().parent().children(".adviceList");
		if(adviceList.css("display") == "none") {
			adviceList.show();
		}else {
			adviceList.hide();
		}
	})
	
	//发表评论
	$(".commSubmit").click(function() {
		var parent = $(this).parent();
		var val = parent.children(".form-group").children(".form-control").val();
		var authorId = parent.children(".authorId").val();
		var msgId = parent.children(".msgId").val();
		if(null == val || "" == val) {
			swal({
				  title: "ITags tip!",
				  text: "评论内容不能为空！",
				  timer: 1500,
				  showConfirmButton: false
				});;
			return;
		}
		$.ajax({
			type: "post",
			url: ctx + "/msg/addComment?userId=" + authorId + "&content=" + val + "&msgId=" + msgId
					+ "&referenceUserId=" + userId + "&type=3",
			dataType: "json",
			success: function(data) {
				swal({
					  title: "ITags tip!",
					  text: "评论发表成功！",
					  timer: 1500,
					  showConfirmButton: false
					});
				if(data.resultCode == 1) {
					var dom = parent.children(".authorId");
					var time = (new Date()).Format("M月dd号 hh:mm") ;
					var str = "<hr><div class='advlice block '><div class='personDetaill'><div><img src=" + head 
						+	" class='head' /></div><div class='adviceDetail'><p><a href='" + ctx + "/person/" + userId + "'>"
						+ userName1 + "：</a>" + data.result.content + "</p><div class='adviceTime'><span>" 
						+ time +  "</span>" + "<input type = 'hidden' id = 'authorId' value = '"
						+ userId + "'/><input type = 'hidden' id = 'msgId' value = '" +  data.result.id
						+ "'/><a onclick='addReplyInput(this)' href='javascript:void(0)'>回复</a></div></div>"
						;
					dom.after(str);
					parent.children(".form-group").children(".form-control").val("");
					parent.children("h5").hide();
					var count = Number(parent.prev().children().eq(2).children("input").val()) + 1;
					parent.prev().children().eq(2).children(".comment").children().eq(1).text(" 评论(" + count + ")");
				}
			}
		})
	})

	//点赞
	$(".good").click(function(){
		var children = $(this).children();
		var dom = $(this);
		var color = children.eq(0).css("color");
		var count = Number(dom.parent().children("input").val());
		var authorId =dom.parents(".messageFoot").children(".authorId").val();
		var msgId = dom.parents(".messageFoot").children(".msgId").val();
		if(color == "rgb(0, 0, 0)") {
			$.ajax({
				type: "post",
				url: ctx + "/msg/addComment?userId=" + authorId + "&msgId=" + msgId
					+ "&type=4&referenceUserId=" + userId,
				dataType: "json",
				success:function(data) {
					if(data.resultCode == 1) {
						children.eq(0).css("color", "rgb(255, 140, 60)");
						dom.parent().children("input").val(count + 1)
						children.eq(1).text(" 赞(" + (count + 1) + ")" );
						children.eq(1).after("<span style = 'display:none'>" + data.result.id + "</span>");
					}
				}
			})
		} else {
			var id = children.eq(2).text();
			$.ajax({
				type: "post",
				url : ctx + "/msg/updateMsgState?id=" + id + "&state=0&type=4&msgId=" + msgId,
				dataType: "json",
				success:function(data) {
					if(data.resultCode == 1) {
						children.eq(0).css("color", "rgb(0, 0, 0)");
						dom.parent().children("input").val(count - 1)
						children.eq(1).text(" 赞(" + (count - 1) + ")" );
						children.eq(2).remove();
					}
				}
			})
		}
	}) 
	
	//收藏
	$(".store").click(function(){
		var children = $(this).children();
		var dom = $(this);
		var color = children.eq(0).css("color");
		var count = Number(dom.parent().children("input").val());
		var authorId =dom.parents(".messageFoot").children(".authorId").val();
		var msgId = dom.parents(".messageFoot").children(".msgId").val();
		if(color == "rgb(0, 0, 0)") {
			$.ajax({
				type: "post",
				url: ctx + "/msg/addComment?userId=" + authorId + "&msgId=" + msgId
					+ "&type=1&referenceUserId=" + userId,
				dataType: "json",
				success:function(data) {
					if(data.resultCode == 1) {
						children.eq(0).css("color", "rgb(255, 140, 60)");
						dom.parent().children("input").val(count + 1)
						children.eq(1).text(" 收藏(" + (count + 1) + ")" );
						children.eq(1).after("<span style = 'display:none'>" + data.result.id + "</span>");
					}
				}
			})
			
		} else {
			var id = children.eq(2).text();
			$.ajax({
				type: "post",
				url : ctx + "/msg/updateMsgState?id=" + id + "&state=0&type=1&msgId=" + msgId,
				dataType: "json",
				success:function(data) {
					if(data.resultCode == 1) {
						children.eq(0).css("color", "rgb(0, 0, 0)");
						dom.parent().children("input").val(count - 1)
						children.eq(1).text(" 收藏(" + (count - 1) + ")" );
						children.eq(2).remove();
					}
				}
			})
		}
	}) 
	
	//转发
	$(".transfer").click(function(){
		var dom = $(this);
		var authorId =dom.parents(".messageFoot").children(".authorId").val();
		var msgId = dom.parents(".messageFoot").children(".msgId").val();
		swal({
			  title: "ITags tip!",
			  text: "转发消息",
			  type: "input",
			  showCancelButton: true,
			  closeOnConfirm: false,
			  animation: "slide-from-top",
			  inputPlaceholder: "说点什么吧"
			},
			function(inputValue){
			if (inputValue === false) return false;
			  if (inputValue === "") 
				  inputValue = "转发消息";
			  $.ajax({
				  type: "post",
				  url: ctx + "/msg/transformMsg?userId=" + authorId + "&msgId=" + msgId
				  + "&referenceUserId=" + userId + "&content=" + inputValue + "&type=2",
				  dataType: "json",
				  success:function(data) {
					  if(data.resultCode == 1) {
						  swal("Itags tip!", "转发成功！");
						  window.location.href = ctx + "/index";
					  }
				  }
			  })
			});
	})
	
	//个人中心
	$("#personZone").click(function(){
		window.location.href = ctx + "/person/" + userId;
	})
	
	//查询实时消息数量
	queryNoticeCount();
	
	window.setInterval(function(){
		queryNoticeCount();
	},600000);
	
	
	$("#chatBlock").load(ctx + "/linkMan?userId=" + userId);

	$(".messagePic img").zoomify();
        
	document.onkeydown=keyDownSearch;  
	
	//查找联系人enter键监听
    function keyDownSearch(e) {    
        // 兼容FF和IE和Opera    
        var theEvent = e || window.event;    
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
        if (code == 13) {    
        	$("#searchMan").click();
            return false;    
        }    
        return true;    
    }  
	
    //为头像绑定弹出个人资料框
    $(".head").mouseenter(function(e){
    	var dom = $(this);
    	var personUserId = $(this).parent().attr("href").substring(14);
    	$.ajax({
    		type: "post",
    		url: ctx + "/user/queryPersonDetail?userId=" + userId + "&personUserId=" + personUserId,
    		dataType: "json",
    		success:function(data) {
    			if(data != null) {
    				$(".p-data .a1").attr("href", "/ITags/person/" + personUserId);
    				setHref(personUserId, "a2", "a3", "a4");
    				$(".p-data .bac").attr("src", data.personBac);
    				$(".p-data .p-head").attr("src", data.head);
    				if(data.sex == 0)
    					$(".p-data .sex").attr("src", "/ITags/images/man.png");
    				else
    					$(".p-data .sex").attr("src", "/ITags/images/woman.png");
    				$(".p-data .userName").text(data.userName);
    				$(".p-data .follow").text("关注 " + data.followCount);
    				$(".p-data .followed").text("粉丝 " + data.followedCount);
    				$(".p-data .dynamic").text("动态 	" + data.dynamicCount);
    				if(data.address != null) {
    					$(".p-data .addr").text(data.address);
    				} else {
    					$(".p-data .addr").text("");
    				}
    				if(personUserId != userId) {
    					$(".p-data .msg").show();
    					if(data.followId != null) {
    						$(".p-data .followId").val(data.followId);
    						$(".p-data .btn-group").show();
    						$(".p-data .followBtn").hide();
    					} else {
    						$(".p-data .followId").val(null);
    						$(".p-data .btn-group").hide();
    						$(".p-data .followBtn").show();
    					}
    				} else {
    					$(".p-data .closeBtn").hide();
    				}
    				var height = $(window).height();
    				var y = dom.offset().top;
    				var x = dom.offset().left - 125;
    				if((e.clientY + 200) < height) {
    					y += 55;
    				} else {
    					y -= 205;
    				}
    				$(".p-data").css({"left": x, "top": y});
    				$(".p-data").fadeIn();
    			}
    		}
    	})
    })
	
    //鼠标离开时 检查鼠标位置是否处于资料狂  不在则关闭
	$(".head").mouseleave(function(){
		
			var x = $(".p-data").offset().left;
			var y = $(".p-data").offset().top;
			function mouseMove(ev) { 
				Ev= ev || window.event; 
				var mousePos = mouseCoords(ev); 
				var currentX = mousePos.x;
				var currentY = mousePos.y;
				if(currentX <= x || currentX >= (x + 350) && currentY <= y || currentY >= (y + 230)) {
					$(".p-data").hide();
				}
				return;
			} 
			function mouseCoords(ev) {
				if(ev.pageX || ev.pageY){ 
					return {x:ev.pageX, y:ev.pageY}; 
				} 
				return{ 
					x:ev.clientX + document.body.scrollLeft - document.body.clientLeft, 
					y:ev.clientY + document.body.scrollTop - document.body.clientTop 
				}; 
			} 
			document.onmousemove = mouseMove; 
		
	})
	
	/*$(".p-data").mouseover(function(){
		$(".p-data").show();
	})*/

	$(".p-data").mouseleave(function(){
		$(".p-data").hide();
	})
	
})



//资料版私信
function sendPrivateMsg(obj) {
	var dom = $(obj);
	var personUserId = dom.parents(".p-data").children(".a1").attr("href").substring(14);
	openChatBlock();
	turnToChat(personUserId);
}

//资料版取消关注
function follow(obj) {
	var dom = $(obj);
	var personUserId = dom.parents(".p-data").children(".a1").attr("href").substring(14);
	var url = ctx + "/user/saveOrUpdateUserRelation?userId=" + userId + "&followId=" + personUserId;
	$.post(url, function(data){
		if(data.resultCode == 1 && data.result != null) {
			$(".p-data .followId").val(data.result);
			$(".p-data .btn-group").show();
			$(".p-data .followBtn").hide();
		}
	})
}


//资料版取消关注
function cancelFollow(obj) {
	var dom = $(obj);
	var personUserId = dom.parents(".p-data").children(".a1").attr("href").substring(14);
	var url = ctx + "/user/saveOrUpdateUserRelation?userId=" + userId + "&followId=" + personUserId;
	var followId = $(".p-data .followId").val();
	if(followId != null) {
		url += ("&id=" + followId); 
	}
	$.post(url, function(data){
		if(data.resultCode == 1) {
			$(".p-data .followId").val(null);
			$(".p-data .btn-group").hide();
			$(".p-data .followBtn").show();
		}
	})
}

//设置资料版跳转链接
function setHref() {
	var temp;
	for(var i = 1; i < arguments.length; i++) {
		temp = arguments[i];
		$(".p-data ." + temp).attr("href", $(".p-data ." + temp).attr("href").replace("personUserId", arguments[0]));
	}
}

//设置图片显示路径
function setImgSrc(obj) {
	var dom = $(obj);
	dom.attr("href",dom.children("img").attr("src"));
}


//长连接轮询消息
function longPolling() {
    $.ajax({
        url: ctx + "/chatMsg?userId=" + userId + "&personUserId=" + $("#chatUser").val(),
        data: {"timed": new Date().getTime()},
        dataType: "json",
        timeout: 10000,
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            if (textStatus == "timeout") { // 请求超时
                    longPolling(); // 递归调用
                // 其他错误，如网络错误等
                } else { 
                    longPolling();
                }
            },
        success: function (data, textStatus) {
            if (textStatus == "success") { // 请求成功
            	console.log(data);
            	for(var i = 0; i < data.length; i++) {
            		var str = "<div class='t4 left'><img src='" + $("#personHead").val() +
					"' class='left img' /><div class='t5 left left-mar chat'>" +
					data[i].content + "</div></div>";
            		$(".t2").append(str);
            	}
            	$(".t2").scrollTop($(".t2")[0].scrollHeight );
                longPolling();
            }
        }
    });
};

// 发送私信
function sendMsg() {
	openChatBlock();
	turnToChat(personUserId);
}

//打开聊天框
function openChatBlock() {
	$("#chatBlock").show();
	$(".chatIcon").hide();
}

function closeChatBlock() {
	$("#chatBlock").hide();
	$(".chatIcon").show();
}

//查找联系人
function searchLinkMan() {
	var key = $.trim($("#linkManKey").val());
	if(key != null && key != "") {
		$("#chatBlock").load("/ITags/linkMan?userName=" + key);
	}
}

//跳转聊天框
function turnToChat(temp) {
	$("#chatBlock").load("/ITags/chat?personUserId=" + temp + "&userId=" + userId);
	//longPolling();
}

//跳转联系人界面
function turnToLink() {
	$("#chatBlock").load("/ITags/linkMan?userId=" + userId);
}




function sendChatMsg(obj) {
	var dom = $(obj);
	var content = $.trim($(obj).parents(".input-group").children().eq(0).val());
	var param = {
			userId: userId,
			personUserId: $("#chatUser").val(),
			content: content
	};
	console.log(content);
	if(content != null && content != "") {
		$.ajax({
			type: "post",
			url: ctx + "/addChatMsg",
			data: param,
			dataType: "json",
			success: function(data) {
				if(data.resultCode == 1) {
					dom.parents(".input-group").children().eq(0).val("");
					var str = "<div class='t4 right'><img src='" + $("#userHead").val() +
					"' class='right img' /><div class='t5 right right-mar'>" +
								content + "</div></div>";
					$(".t2").append(str);
					$(".t2").scrollTop($(".t2")[0].scrollHeight );
				}
			}
		})
	} 
	
}



function queryNoticeCount() {
	$.ajax({
		type: "post",
		url: ctx + "/msg/queryNoticeCount?userId=" + userId,
		dataType: "json",
		success: function(data) {
			if(data > 0) {
				$("#noticeCount").text("消息(" + data + ")");
			}
		}
	})
}




//分割线
//添加回复输入框
function addReplyInput(obj) {
	var parent = $(obj).parent();
	var dom = parent.parent().children(".replyInput");
	if(dom.length <= 0) {
		var name = $.trim(parent.parent().children("p").children("a").html());
		var str = "<div class='replyInput'><input class='form-control' placeholder='回复@"
			+ name + "：' /><input type='button' value='发表' class='btn btn-default replySubmit' " +
			"onclick = 'submitReply(this)'/></div>";
		parent.after(str);
	}
	if(dom.css("display") == "none") {
		dom.show();
	} else {
		dom.hide();
	}
	
}

function submitReply(obj) {
	var dom = $(obj);
	var authorId = dom.parent().prev(".adviceTime").children("#authorId").val();
	var msgId = dom.parent().prev(".adviceTime").children("#msgId").val();
	var content = $.trim(dom.parent().children(".form-control").val());
	if(null == content || '' == content) {
		swal({
			  title: "ITags tip!",
			  text: "回复内容不能为空！",
			  timer: 1500,
			  showConfirmButton: false
			});
		return;
	}
	var time = (new Date()).Format("M月dd号 hh:mm") ;
	$.ajax({
		type: "post",
		url: ctx + "/msg/addComment?userId=" + authorId + "&content=" + content + "&msgId=" + msgId
				+ "&referenceUserId=" + userId + "&type=5",
		dataType: "json",
		success: function(data) {
			if(data.resultCode == 1) {
				var name = $.trim(dom.parent().parent().children("p").children("a").eq(0).text());
				var str = "<div class='replyBlock'><p><a href='"+ ctx + "/person/" + userId +"'> " + userName1
				+ "</a> 回复 <a href='" + ctx + "/person/" + authorId + "'>" + name + "</a>：" + content + "</p>" 
				+ "<div class='adviceTime'><input type = 'hidden' id = 'authorId' value ='" 
				+ userId + "'/><input type = 'hidden' id = 'msgId' value = '"
				+ msgId + "'/><span>" + time + "</span><a onclick='addReplyInput(this)'"
				+ " href='javascript:void(0)'>回复</a></div></div>";
				dom.parents(".adviceDetail").children(".adviceTime").after(str);
				swal({
					  title: "回复成功!",
					  text: data.msg,
					  timer: 1500,
					  showConfirmButton: false
					});
				dom.parent().hide();
				dom.parent().children(".form-control").val("");
			} else {
				swal({
					  title: "ITags tip!",
					  text: data.msg,
					  timer: 1500,
					  showConfirmButton: false
					});
			}
		}
	})
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

function closeEditBlock() {
	$("#editBlock").hide();
}


function searchPerson(obj) {
	if($("#searchData").val() == null || $("#searchData").val() == "") {
		swal({
			  title: "ITags tip!",
			  text: "请输入名称查询",
			  timer: 1500,
			  showConfirmButton: false
			});
		return;
	}
	window.location.href = ctx + "/findPerson?key=" + $("#searchData").val();
}

//隐藏动态
function hideDynamic(obj, id) {
	var dom = $(obj);
	var param = {
		id: id
	};
	$.ajax({
		type: "post",
		url: ctx + "/msg/hideDynamic",
		data: param,
		dataType: "json",
		success: function(data) {
			if(data.resultCode == 1) {
				dom.parents(".message").hide();
			}
		}
	})
}





