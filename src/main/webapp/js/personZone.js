
$(function(){
	laydate({
        elem: '#birthday'
    });
	
	$('#avatarInput').on('change', function(e) {
		var filemaxsize = 1024 * 5;//5M
		var target = $(e.target);
		var Size = target[0].files[0].size / 1024;
		if(Size > filemaxsize) {
			alert('图片过大，请重新选择!');
			$(".avatar-wrapper").childre().remove;
			return false;
		}
		if(!this.files[0].type.match(/image.*/)) {
			alert('请选择正确的图片!')
		} else {
			var filename = document.querySelector("#avatar-name");
			var texts = document.querySelector("#avatarInput").value;
			var teststr = texts; //你这里的路径写错了
			testend = teststr.match(/[^\\]+\.[^\(]+/i); //直接完整文件名的
			filename.innerHTML = testend;
		}
	
	})

	$(".avatar-save").on("click", function() {
		var img_lg = document.getElementById('imageHead');
		// 截图小的显示框内的内容
		html2canvas(img_lg, {
			allowTaint: true,
			taintTest: false,
			onrendered: function(canvas) {
				canvas.id = "mycanvas";
				//生成base64图片数据
				var dataUrl = canvas.toDataURL("image/jpeg");
				var newImg = document.createElement("img");
				newImg.src = dataUrl;
				imagesAjax(dataUrl)
			}
		});
	})
	
	//上传头像数据
	function imagesAjax(src) {
		var data = {};
		data.img = src;
		$.ajax({
			url: ctx + "/user/modifyHead",
			data: data,
			type: "POST",
			dataType: 'json',
			success: function(data) {
				if(data.resultCode == 1) {
					swal({
						  title: "ITags tip!",
						  text: "上传成功！",
						  timer: 1000,
						  showConfirmButton: false
						});
					window.location.reload();
				}
			}
		});
	}
	
	//修改个人资料
	$("#modifyData").click(function(data){
		var address = $("#province").val() + " " + $("#city").val() + " " + $("#district").val();
		$.ajax({
			type: "post",
			url: ctx + "/user/updateUserInfo",
			data: $("#fm").serialize() + "&address=" + address +"&userId=" + userId,
			dataType: "json",
			success:function(data) {
				if(data.resultCode == 1) {
					swal({
						  title: "ITags tip!",
						  text: "修改成功！",
						  timer: 1000,
						  showConfirmButton: false
						});
					$("#myModal").modal("hide");
					window.location.reload();
				} else {
					swal({
						  title: "ITags tip!",
						  text: data.msg,
						  timer: 1000,
						  showConfirmButton: false
						});
				}
			}
		})
	})
	
	setAddress();
	
	//点击关注
	$("#follow").click(function(){
		var id = $(this).val();
		var dom = $(this);
		var url = ctx + "/user/saveOrUpdateUserRelation?userId=" + userId + "&followId=" + personUserId;
		if(id != null || id != "") {
			url += "&id=" + id;
		} 
		$.ajax({
			type: "post",
			url: url,
			dataType: "json",
			success:function(data) {
				if(data.resultCode == 1) {
					if(data.result != null) {
						dom.val(data.result);
						dom.text("已关注");
						dom.attr("class", "btn btn-info");
					} else {
						dom.val("");
						dom.text("关注")
						dom.attr("class", "btn btn-warning");
					}
				}
			}
		})
	});
	
	if(type == "dynamic") {
		turnToDynamicTab();
	}
	
	//修改备注名称
	$("#modifyRemark").click(function(){
		var remark = $.trim($(this).parents(".modal-content").children(".modal-body").children().val());
		if(remark == null || remark == "") {
			swal({
				  title: "ITags tip!",
				  text: "备注名称不能为空！",
				  timer: 1000,
				  showConfirmButton: false
				});
			return;
		}
		$.ajax({
			type: "post",
			url: ctx + "/user/addRemark?userId=" + userId + "&personUserId=" + personUserId 
				+ "&remark=" + remark,
			dataType: "json",
			success:function(data) {
				if(data.resultCode == 1) {
					$("#myModal2").modal("hide");
					$("#remark").text("(" + remark + ",");
				} else {
					swal({
						  title: "ITags tip!",
						  text: data.msg,
						  timer: 1000,
						  showConfirmButton: false
						});
				}
			}
		})
	})
	
	//绑定手机号
	$("#bindPhone").click(function(){
		var dom = $(this).parents(".modal-content").children(".modal-body").children();
		var phone = dom.eq(0).val();
		var verifyCode = dom.eq(1).children().eq(0).val();
		var regex = /^1[34578]\d{9}$/;
		var regex2 = /^[0-9]{6}$/;
		if(!regex.test(phone)) {
			swal({
				  title: "ITags tip!",
				  text: "请正确的输入输入手机号码",
				  timer: 1000,
				  showConfirmButton: false
				});
			return;
		}
		if(!regex2.test(verifyCode)) {
			swal({
				  title: "ITags tip!",
				  text: "请正确的输入输入验证码",
				  timer: 1000,
				  showConfirmButton: false
				});
			return;
		}
		var param = {
				userId: userId,
				phone: phone,
				verifyCode: verifyCode
		};
		$.ajax({
			type: "post",
			url: ctx + "/user/bindPhone",
			data: param,
			dataType: "json",
			success: function(data) {
				if(data.resultCode == 1) {
					swal({
						  title: "ITags tip!",
						  text: "绑定成功！",
						  timer: 1000,
						  showConfirmButton: false
						});
					$("#myModal3").modal("hide");
					window.location.reload();
				} else {
					swal({
						  title: "ITags tip!",
						  text: data.msg,
						  timer: 1000,
						  showConfirmButton: false
						});
				}
			}
		})
	})
	
	$("#modifyPwdBtn").click(function(){
		var dom = $(this);
		var userPwd = $.trim(dom.parents(".modal-content").children(".modal-body")
				.children().eq(0).val());
		var verifyCode = $.trim(dom.parents(".modal-content").children(".modal-body")
				.children(".input-group").children().eq(0).val());
		if(null == userPwd || userPwd == ""){
			openSwal("请输入新密码");
			return;
		}
		if(null == verifyCode || userPwd == ""){
			openSwal("请输入验证码");
			return;
		}
		var re = /^[a-zA-Z0-9\(\)]{8,16}$/;
		if(!re.test(userPwd)) {
			openSwal("密码不合法！仅包含字母、数字、符号共8-16位！");
			return;
		}
		var regex2 = /^[0-9]{6}$/;
		if(!regex2.test(verifyCode)) {
			openSwal("验证码为6位纯数字");
			return;
		}
		var param = {
			userId: userId,
			verifyCode: verifyCode,
			userPwd: userPwd
		}
		$.ajax({
			type: "post",
			url: ctx + "/user/modifyPwdByVerifyCode",
			data: param,
			dataType: "json",
			success:function(data) {
				if(data.resultCode == 1) {
					loginOut();
				} else {
					openSwal(data.msg);
				}
			}
		})
	})
	
	$("#modifyPWd2").click(function(){
		var userName = $("#fm3").children().eq(0).val();
		var oldPwd = $("#fm3").children().eq(1).val();
		var newPwd = $("#fm3").children().eq(3).val();
		var re = /^[a-zA-Z0-9\(\)]{8,16}$/;
		
		if(isEmpty(userName) || isEmpty(oldPwd) || isEmpty(newPwd)) {
			$("#fm3").children().eq(4).text("所填项不能为空！");
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
					loginOut();
				} else {
					$("#fm3").children("div").text(data.msg);
				}
			}
		})
	})
	
	//点击更改背景墙图片
	$(".selPic").click(function(){
		var pic = $(this).children().eq(0);
		var clas = pic.attr("class");
		$(".bacPanel-body").children("a").children().removeClass("picBorder");
		if(clas == undefined || clas == "") {
			pic.addClass("picBorder");
		}
	})
	
	//为图片添加预览功能
	$(".picList img").zoomify();
	
	
	if(userId == personUserId) {
	   
	    
	} else {
		//访问别人主页时添加主页记录
		$.post(ctx + "/user/insertVisit?userId=" + userId + "&personUserId=" + personUserId);
	}
	
	//初始化年份月份下拉框
	initDateSel();
	
	
	
	
	
	
	
	
	
})

function loadEchrtsData() {
	 //查询粉丝或关注详情
	window.personAnalyseEcharts = echarts.init(document.getElementById('main'));
	window.personAnalyseData = initPersonAnalyseData();
    initPersonAnalyse("follow", personAnalyseData, personAnalyseEcharts);
	
	//查询粉丝或关注年龄详情
    window.personAgeAnalyseEcharts = echarts.init(document.getElementById('ageBlock'));
    window.personAgeAnalyseData = initPersonAgeAnalyseData();
    initPersonAgeAnalyse("follow", personAgeAnalyseData, personAgeAnalyseEcharts);
    
    //初始化主页访问量折线图
    var now = new Date();
    var month = now.getMonth() + 1;
    var year = now.getFullYear();
    if(month < 10) {
    	month = "0" + month;
    }
    window.personVisitData = initVistData(year + "-" + month);
    window.visitAnalyseEchatrs = echarts.init(document.getElementById('visitBlock'));
    initVisitAnalyse(personVisitData, visitAnalyseEchatrs, year, month);
    
    //初始化人员分布
    window.personLocationData = initLocationData();
    window.locationAnalyseEcharts = echarts.init(document.getElementById('locationBlock'));
    initLocationAnalyse(personLocationData, "follow" ,locationAnalyseEcharts);
}





function changeAddressAnalyse(obj) {
	var dom = $(obj);
	if(dom.val() == "follow") {
		initLocationAnalyse(personLocationData, "followed", locationAnalyseEcharts);
		dom.val("followed");
	} else {
		initLocationAnalyse(personLocationData, "follow", locationAnalyseEcharts);
		dom.val("follow");
	}
}


//初始化人员分布echarts
function  initLocationAnalyse(data, type, mycharts) {
	var title;
	var addressData;
	if(type == "follow") {
		addressData = data.follow;
		title = "关注人员分布统计";
	} else {
		addressData = data.followed;
		title = "粉丝分布统计";
	}
	if(addressData == null)
		return;
	option = {
	    title: {
	        text: title,
	        subtext: '来自ITags',
	        left: 'center'
	    },
	    tooltip: {
	        trigger: 'item'
	    },
	    legend: {
	        orient: 'vertical',
	        left: 'left',
	        data:['各省人数']
	    },
	    visualMap: {
	        min: 0,
	        left: 'left',
	        top: 'bottom',
	        text: ['高','低'],           // 文本，默认为数值文本
	        calculable: true
	    },
	    toolbox: {
	        show: true,
	        orient: 'vertical',
	        left: 'right',
	        top: 'center',
	        feature: {
	            dataView: {readOnly: false},
	            restore: {},
	            saveAsImage: {}
	        }
	    },
	    series: [
	        {
	            name: '数量',
	            type: 'map',
	            mapType: 'china',
	            label: {
	                normal: {
	                    show: true
	                },
	                emphasis: {
	                    show: true
	                }
	            },
	            data: addressData
	        }
	    ]
	};
	mycharts.setOption(option);
}

function randomData() {
    return Math.round(Math.random()*1000);
}

//初始化位置后台数据
function initLocationData() {
	var temp;
	$.ajax({
		type:"post",
		url: ctx + "/user/queryAddress?userId=" + personUserId,
		dataType: "json",
		async : false,
		success:function(data) {
			temp = data;
		}
	})
	return temp;
}


//根据日期查询访问数据
function changeMonthVisit() {
	var year = $(".year").val();
	var month = $(".month").val();
	var visitData = initVistData(year + "-" + month);
	initVisitAnalyse(visitData, visitAnalyseEchatrs, year, month);
}

//初始化关注粉丝后台数据
function initVistData(date) {
	var temp;
	$.ajax({
		type:"post",
		url: ctx + "/user/queryVisitData?userId=" + personUserId + "&date=" + date,
		dataType: "json",
		async : false,
		success:function(data) {
			temp = data;
		}
	})
	return temp;
}



//初始化日期选择下拉框
function initDateSel() {
	var year = (new Date()).getFullYear();
	var month = (new Date()).getMonth();
	var yearStr = "";
	for(var i = year; i >= 2010; i--) {
		if(i == year) {
			yearStr += "<option selected = 'selected' value = '" + i + "'>" + i + "年</option>";
		} else {
			yearStr += "<option value = '" + i + "'>" + i + "年</option>";
		}
	}
	$(".year").append(yearStr);
	$(".month").children().eq(month).attr("selected", true);
}

//初始化访问记录图
function initVisitAnalyse(data, echarts ,year, month) {
	if(data == null)
		return;
	var dayCount = new Date(year , month ,0).getDate();
	var dateStr = month + "-";
	var xData = [];
	var yData = [];
	var xTempData;
	for(var i = 1; i <= dayCount; i++) {
		xTempData = dateStr + i;
		xData.push(xTempData);
		if(i < 10) {
			i = "0" + i;
		}
		var yTempData = data[i];
		if(yTempData != undefined) {
			yData.push(yTempData);
		} else {
			yData.push(0);
		}
	}
	option = {
		    title: {
		        text: '主页访问量统计'
		    },
		    tooltip: {
		        trigger: 'axis'
		    },
		    grid: {
		        left: '3%',
		        right: '4%',
		        bottom: '3%',
		        containLabel: true
		    },
		    toolbox: {
		        feature: {
		            saveAsImage: {}
		        }
		    },
		    xAxis: {
		        type: 'category',
		        boundaryGap: false,
		        data: xData
		    },
		    yAxis: {
		        type: 'value'
		    },
		    series: [
		        {
		            name:'访问数量',
		            type:'line',
		            stack: '总量',
		            data: yData
		        }
		    ]
		};
	echarts.setOption(option);
}


//粉丝关注切换
function changeAgeAnalyse(obj) {
	var dom = $(obj);
	if(dom.val() == "follow") {
		initPersonAgeAnalyse("followed", personAgeAnalyseData, personAgeAnalyseEcharts);
		dom.val("followed");
	} else {
		initPersonAgeAnalyse("follow", personAgeAnalyseData, personAgeAnalyseEcharts);
		dom.val("follow");
	}
}

//初始化关注粉丝年龄柱形图
function initPersonAgeAnalyse(str ,data, echarts) {
	var ageData;
	var followData;
	var title;
	if(str == "follow") {
		followData = data.followAge;
		title = "关注年龄分布";
	} else {
		followData = data.followedAge;
		title = "粉丝年龄分布";
	}
	if(followData == null)
		return;
	ageData = [followData._60, followData._60y, followData._70y, followData._80y,
	           followData._90y, followData._00y, followData.unWrite];
	option = {
		 title : {
		        text: title,
		        subtext: "来自ITags",
		        x:'center'
		    },
	    color: ['#3398DB'],
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    toolbox: {
	        feature: {
	            saveAsImage: {}
	        }
	    },	
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data : ["60前", "60后", "70后", "80后", "90后", "00后", "未填写"],
	            axisTick: {
	                alignWithLabel: true
	            }
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value'
	        }
	    ],
	    series : [
	        {
	            name:'数量',
	            type:'bar',
	            barWidth: '60%',
	            data: ageData
	        }
	    ]
	};
	echarts.setOption(option);
}

//初始化关注粉丝后台数据
function initPersonAgeAnalyseData() {
	var temp;
	$.ajax({
		type:"post",
		url: ctx + "/user/queryAgeAnalyse?userId=" + personUserId,
		dataType: "json",
		async : false,
		success:function(data) {
			temp = data;
		}
	})
	return temp;
}

//粉丝关注切换
function changePersonAnalyse(obj) {
	var dom = $(obj);
	if(dom.val() == "follow") {
		initPersonAnalyse("followed", personAnalyseData, personAnalyseEcharts);
		dom.val("followed");
	} else {
		initPersonAnalyse("follow", personAnalyseData, personAnalyseEcharts);
		dom.val("follow");
	}
}

//初始化关注粉丝后台数据
function initPersonAnalyseData() {
	var temp;
	$.ajax({
		type:"post",
		url: ctx + "/user/queryPersonAnalyse?userId=" + personUserId,
		dataType: "json",
		async : false,
		success:function(data) {
			temp = data;
		}
	})
	return temp;
}


//初始化人员分析饼图
function initPersonAnalyse(str ,data, echarts) {
	var title;
	var menCount;
	var womenCount;
	if(str == "follow") {
		title = "个人关注";
		womenCount = data.follow.womenCount;
		menCount = data.follow.menCount;
	} else {
		title = "个人粉丝";
		womenCount = data.followed.womenCount;
		menCount = data.followed.menCount;
	}
	if(data == null)
		return;
	// 指定图表的配置项和数据
    var option = {
    	    title : {
    	        text: title,
    	        subtext: "来自ITags",
    	        x:'center'
    	    },
    	    tooltip : {
    	        trigger: 'item',
    	        formatter: "{a} <br/>{b} : {c} ({d}%)"
    	    },
    	
    	    legend: {
    	        orient: 'vertical',
    	        left: 'left',
    	        data: ['男性','女性']
    	    },
    	    series : [
    	        {
    	            name: '数量',
    	            type: 'pie',
    	            radius : '55%',
    	            center: ['50%', '60%'],
    	            data:[
    	                {value: menCount, name:'男性'},
    	                {value: womenCount, name:'女性'}
    	            ],
    	            itemStyle: {
    	                emphasis: {
    	                    shadowBlur: 10,
    	                    shadowOffsetX: 0,
    	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
    	                }
    	            }
    	        }
    	    ]
    	};
 // 使用刚指定的配置项和数据显示图表。
    echarts.setOption(option);
}

//更换背景
function changeBac() {
	var selPic = $(".bacPanel-body").children("a").children().filter(".picBorder");
	if(selPic.length <= 0) {
		openSwal("请选择一张图片！");
		return;
	}
	var personBac = selPic.attr("src");
	var param = {
			userId: userId,
			personBac: personBac
	};
	$.ajax({
		type: "post",
		url: ctx + "/user/modifyPersonBac",
		data: param,
		dataType: "json",
		success: function(data) {
			if(data.resultCode == 1) {
				closePicPanel();
				$(".personBac").children().eq(0).attr("src", personBac);
			} else {
				alert(data.msg);
			}
		}
	})
	
}

//打开修改个人背景墙面板
function openChangeBlock() {
	/*$(".bacPanel").animate({top:'0px'});
	$(".bacPanel").animate({top:'80px'});*/
	$(".bacPanel-body").children("a").children().removeClass("picBorder");
	$(".p-bacPanel").show();
	$("body").css({"overflow":"hidden","height":"100%"});
}

//关闭
function closePicPanel() {
	$(".p-bacPanel").hide();
	$("body").css({"overflow":"auto"});
	
}

//打开取消绑定手机模态框
function cancelPhone(obj) {
	swal({ 
		  title: "确定取消绑定吗？", 
		  text: "你将无法接收到验证码！", 
		  type: "warning",
		  showCancelButton: true, 
		  confirmButtonColor: "#DD6B55",
		  confirmButtonText: "确定", 
		  cancelButtonText: "取消",
		  closeOnConfirm: false, 
		  closeOnCancel: false	
		},
		function(isConfirm){ 
			var str = "<div class = 'tx1'><a href = 'javascrit:void()' style = " +
					"'text-decoration:none' onclick = 'getVerifyCode2(this)'>" +
			"立即获取</a></div>";
		  if (isConfirm) { 
			  swal({ 
				  title: "ITags tip", 
				  type: "input",
				  text: str,
				  showCancelButton: true, 
				  closeOnConfirm: false, 
				  confirmButtonText: "提交", 
				  cancelButtonText: "取消",
				  html: true,
				  animation: "slide-from-top", 
				  inputPlaceholder: "请输入验证码" 
				},
				function(inputValue){ 
					  if (inputValue === false)
						  return false; 
					  if (inputValue === "") { 
					    swal.showInputError("请输入验证码!");
					    return false;
					  } 
					  var regex2 = /^[0-9]{6}$/;
					  if(!regex2.test(inputValue)) {
						  swal.showInputError("验证码为六位纯数字!");
						    return false;
					  }
					  var param = {
							userId: userId,
							verifyCode: inputValue
					  };
					  $.ajax({
							type: "post",
							url: ctx + "/user/cancelPhoneBind",
							data: param,
							dataType: "json",
							success: function(data) {
								if(data.resultCode == 1) {
									swal({
										  title: "ITags tip!",
										  text: "取消绑定成功！",
										  timer: 1000,
										  showConfirmButton: false
										});
									window.location.reload();
								} else {
									swal.showInputError(data.msg);
								    return false;
								}
							} 
				  
					  });
				});
		  } else { 
		    swal("取消", "可以继续接收验证码:)","success"); 
		  } 
		});
}

//修改资料地址回显
function setAddress() {
	var address = $("#address").val();
	if(address != null && address != "") {
		var temp = address.split(" ");
		setAddr(temp[0], temp[1], temp[2]);
	} else {
		setAddr(null, null, null);
	}
	
}


function getModifyPwdVerifyCode(obj) {
	var dom = $(obj);
	var phone = $("#tempPhone").val();
	sendVerifyReq(dom, phone, "getModifyPwdVerifyCode");
}


//获取取消绑定验证码
function getVerifyCode2(obj) {
	var dom = $(obj);
	var phone = $("#tempPhone").val();
	sendVerifyReq(dom, phone, "getCancelBindVerifyCode");
}

//获取绑定验证码
function getVerifyCode(obj) {
	var dom = $(obj);
	var phone = dom.parents(".modal-body").children().eq(0).val();
	sendVerifyReq(dom, phone , "getBindVerifyCode");
}

function sendVerifyReq(dom, phone, method) {
	var regex = /^1[34578]\d{9}$/;
	if(!regex.test(phone)) {
		swal({
			  title: "ITags tip!",
			  text: "请正确的输入输入手机号码",
			  timer: 1000,
			  showConfirmButton: false
			});
		return;
	}
	$.ajax({
		type: "post",
		url: ctx + "/sms/" + method,
		data: "phone=" + phone,
		dataType: "json",
		success: function(data) {
			if(data.resultCode == 1) {
				dom.attr("disabled", true);
				var time = 60;
				var timer = window.setInterval(function(){
					dom.text(time + "s");
					if(time == 0) {
						clearInterval(timer);
						dom.attr("disabled", false);
						dom.text("点击获取");
					}
					time = time - 1;
				}, 1000);
			}
		}
	})
}


function setAddr(pro, city, dis) {
	var $province = $("#province");
    var $city = $("#city");
    var $district = $("#district");
	$province.val(pro);
    $province.trigger("change");
    $city.val(city);
    $city.trigger("change");
    $district.val(dis);
    $district.trigger("change");
}


function turnToDynamicTab() {
	$("#dynamicsTab").click();
}






