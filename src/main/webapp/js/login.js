$(function(){
	//点击注册跳转换到注册面板
	$("#register").click(function(){
		$("#loginBac").hide();
		$(".registerBac").show();
	})
	
	//点击返回回到登录面板
	$("#back").click(function(){
		$("#loginBac").show();
		$(".registerBac").hide();
	})
	// 光标聚集输入框时 按钮可用 边框颜色恢复
	$("#userName").focus(function(){
		setFocusEvent("#userName", ".registerBtn");
	})
	$("#userPwd").focus(function(){
		setFocusEvent("#userPwd", ".registerBtn");
	})
	$("#confirmPassword").focus(function(){
		setFocusEvent("#confirmPassword", ".registerBtn");
	})
	$("#checkMark").focus(function(){
		setFocusEvent("#checkMark", ".registerBtn");
	})
	
	//用户名光标离开时检测 用户名合法性
	$("#userName").blur(function(){
		var userName = getValue("#userName");
		if(isEmpty(userName)) {
			setBtnUnabled(".registerBtn", "#userName", "用户名不能为空！");
			return;
		}
		var re = /^[a-zA-Z0-9_\u2E80-\u9FFF]{2,8}$/;
		if(!re.test(userName)) {
			setBtnUnabled(".registerBtn", "#userName", "用户名不合法！仅包含字母、数字、下划线、汉字共2-8个长度！");
			return;
		}
		$.ajax({
			type:"post",
			url: ctx + "/user/queryUserNameIsVailable?userName=" + userName,
			dataType:"json",
			success:function(data) {
				if(data.resultCode == 0) {
					setBtnUnabled(".registerBtn", "#userName", data.msg);
				}
			}
		})
		setInputColor("#userName", "green");
	})

	//密码光标离开时检测 密码合法性
	$("#userPwd").blur(function(){
		var userPwd = getValue("#userPwd");
		var confirmPassword = getValue("#confirmPassword");
		if(isEmpty(userPwd)) {
			setBtnUnabled(".registerBtn", "#userPwd", "密码不能为空！");
			return;
		}
		var re = /^[a-zA-Z0-9\(\)]{8,16}$/;
		if(!re.test(userPwd)) {
			setBtnUnabled(".registerBtn", "#userPwd", "密码不合法！仅包含字母、数字、符号共8-16位！");
			return;
		}
		if(!isEmpty(confirmPassword)) {
			if(userPwd != confirmPassword) {
				setBtnUnabled(".registerBtn", "#confirmPassword", "两次输入密码不一致！！");
				return;
			}
			setInputColor("#confirmPassword", "green");
		}
		setInputColor("#userPwd", "green");
	})
	
	
		//确认密码光标离开时检测
	$("#confirmPassword").blur(function(){
		var confirmPassword = getValue("#confirmPassword");
		var userPwd = getValue("#userPwd");
		if(isEmpty(confirmPassword)) {
			setBtnUnabled(".registerBtn", "#confirmPassword", "确认密码不能为空！");
			return;
		}
		if(userPwd != confirmPassword) {
			setBtnUnabled(".registerBtn", "#confirmPassword", "两次输入密码不一致！！");
			return;
		}
		setInputColor("#confirmPassword", "green");
	})
	
	//验证码光标离开时检测
	$("#checkMark").blur(function(){
		var checkMark =  getValue("#checkMark");
		if(isEmpty(checkMark)) {
			setBtnUnabled(".registerBtn", "#checkMark", "验证码不能为空！");
			return;
		}
		setInputColor("#checkMark", "green");
	})
	
	$("#registerBtn").click(function(){
		var userName = getValue("#userName");
		var userPwd = getValue("#userPwd");
		var confirmPassword = getValue("#confirmPassword");
		var checkMark = getValue("#checkMark");
		if(isEmpty(userName)) {
			setBtnUnabled(".registerBtn", "#userName", "红色框为必填项！");
		}
		if(isEmpty(userPwd)) {
			setBtnUnabled(".registerBtn", "#userPwd", "红色框为必填项！");
		}
		if(isEmpty(confirmPassword)) {
			setBtnUnabled(".registerBtn", "#confirmPassword", "红色框为必填项！");
		}
		if(isEmpty(checkMark)) {
			setBtnUnabled(".registerBtn", "#checkMark", "红色框为必填项！");
			return;
		}
		setBtnAvailable(".registerBtn", false);
		$.ajax({
			type: "post",
			url: ctx + "/user/registerUser",
			data: $("#fm2").serialize(),
			dataType: "json",
			success:function(data) {
				if(data.resultCode == 1) {
					$("#fm2")[0].reset();
					$("#loginBac").show();
					$(".registerBac").hide();
					alert("注册成功！");
				} else {
					alert(data.msg);
				}
			}
		})
	})
	
//  登录事件	
	$("#userName1").focus(function(){
		setFocusEvent("#userName1", "#loginBtn");
	})
	$("#userPwd1").focus(function(){
		setFocusEvent("#userPwd1", "#loginBtn");
	})
	$("#userName1").blur(function(){
		var userName = getValue("#userName1");
		if(isEmpty(userName)) {
			setBtnUnabled("#loginBtn", "userName1", "用户名不能为空！");
		}
	})
	$("#userPwd1").blur(function(){
		var userName = getValue("#userPwd1");
		if(isEmpty(userName)) {
			setBtnUnabled("#loginBtn", "userPwd1", "密码不能为空！");
		}
	})
	
	$("#loginBtn").click(function(){
		var userName = getValue("#userName1");
		userName = encodeURI(userName)
		var userPwd = getValue("#userPwd1");
		var flag = getValue("#rememberMe");
		if(isEmpty(userName)) {
			setBtnUnabled("#loginBtn", "userName1", "用户名不能为空！");
		}
		if(isEmpty(userName)) {
			setBtnUnabled("#loginBtn", "userPwd1", "密码不能为空！");
			return;
		}
		$.ajax({
			type:"post",
			url: ctx + "/user/userLogin?userName=" + userName + "&userPwd=" + userPwd,
			dataType:"json",
			success:function(data) {
				if(data.resultCode == 1) {
					if($("#rememberMe").get(0).checked) {
						$.cookie("userName", data.result.userName,  {expires:7});
						$.cookie("userPwd", data.result.userPwd,    {expires:7});
					}
					window.location.href = ctx + "/index";
				} else {
					alert(data.msg);
				}
			}
		})
	})
	
	$("#modifyData").click(function(){
		var userName = $("#fm3").children().eq(0).val();
		var oldPwd = $("#fm3").children().eq(2).val();
		var newPwd = $("#fm3").children().eq(4).val();
		var re = /^[a-zA-Z0-9\(\)]{8,16}$/;
		if(isEmpty(userName) || isEmpty(oldPwd) || isEmpty(newPwd)) {
			$("#fm3").children().eq(5).text("所填项不能为空！");
			return;
		}
		if(!re.test(newPwd)) {
			$("#fm3").children("div").text("密码不合法！仅包含字母、数字、符号共8-16位！");
			return;
		}
		$.ajax({
			type: "post",
			url: ctx + "/user/modifyPassword",
			data: $("#fm3").serialize(),
			dataType: "json",
			success:function(data) {
				if(data.resultCode == 1) {
					alert("修改成功！");
					$("#myModal").modal("hide");
				} else {
					$("#fm3").children("div").text(data.msg);
				}
			}
		})
		
		
		
	})
	
	
	document.onkeydown=keyDownSearch;  

    function keyDownSearch(e) {    
        // 兼容FF和IE和Opera    
        var theEvent = e || window.event;    
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
        if (code == 13) {    
        	$("#loginBtn").click();
            return false;    
        }    
        return true;    
    }  
	
	
})



//更换验证码图片
function newCheckMark() {
	$("#checkPic").attr("src", "kaptcha/getKaptchaImage");
}

//按钮可用性
function setBtnAvailable(str, flag) {
	$(str).attr("disabled", flag);
}

//设置提示框内容
function setTipMessage(str) {
	$(".tip").html(str);
}

//聚焦
function setFocusEvent(input, btn) {
	setBtnAvailable(btn, false);
	setTipMessage("");
	$(input).css("border-color", "");
}

//按钮不可用 输入框警告 输出提示信息
function setBtnUnabled(btn, input, str) {
	setBtnAvailable(btn, true);
	setInputColor(input, "red");
	setTipMessage(str);
}

//设置输入框颜色
function setInputColor(input, color) {
	$(input).css("border-color", color);
}

function getValue(str) {
	return $.trim($(str).val());
}
