function openEditBlock(data) {
	var dom = $(data);
	var foot = dom.parents(".panel").children(".panel-footer");
	if(foot.css("display") == "none") {
		foot.show();
	}else {
		foot.hide();
	}
}

function replyMsg(obj) {
	var dom = $(obj);
	var content = dom.parents(".input-group").children().eq(0).val();
	var authorId = dom.parents(".input-group").children().eq(1).val();
	var msgId = dom.parents(".input-group").children().eq(2).val();
	if(null == content || "" == content) {
		swal({
			  title: "ITags tip!",
			  text: "回复内容不能为空!",
			  timer: 1500,
			  showConfirmButton: false
			});
	return;
	}
	$.ajax({
		type: "post",
		url: ctx + "/msg/addComment?userId=" + authorId + "&content=" + content + "&msgId=" + msgId
				+ "&referenceUserId=" + userId + "&type=5",
		dataType: "json",
		success: function(data) {
			if(data.resultCode == 1) {
				swal({
					  title: "回复成功!",
					  text: data.msg,
					  timer: 1500,
					  showConfirmButton: false
					});
				dom.parents(".panel-footer").hide();
				dom.parents(".input-group").children().eq(0).val("");
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


$(function(){
})