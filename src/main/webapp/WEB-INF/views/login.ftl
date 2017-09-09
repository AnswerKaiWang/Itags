<!DOCTYPE html>
<html>
	<head>
		<title>ITags</title>
		<link rel="shortcut icon" href="${ctx}/images/favicon.ico"  type="image/x-icon"/>
		<link rel="stylesheet" href="${ctx}/statics/bootstrap/css/bootstrap.css" />
		<link rel="stylesheet" href="${ctx}/css/login.css" />
		<script type="text/javascript" src="${ctx}/js/jquery-1.11.3.js" ></script>
		<script type="text/javascript" src="${ctx}/statics/bootstrap/js/bootstrap.js" ></script>
		<script type="text/javascript" src="${ctx}/js/login.js" ></script>
		<script type="text/javascript" src="${ctx}/js/common.js" ></script>
		<script type="text/javascript" src="${ctx}/js/jquery.cookie.js" ></script>
		<script type="text/javascript">
			var ctx = "${ctx}";
		</script>
	</head>
	<body>
		<div class="bac" id="loginBac">
			<img src="images/1499171399_887934.png" />
			<div class = "login">
				<form id = "fm1">
					用户名<br />
					<input class="form-control" placeholder = "请输入用户名" id = "userName1"/> <br />
					密码 <br />
					<input  class="form-control" type="password" id = "userPwd1"/> <br />
					
					<div class="checkbox">
						<label class="demo1">
							<a href="javascript:void(0)"  data-toggle="modal" data-target="#myModal">忘记密码？</a>
						</label>
				        <label class="demo2">
				          <input type="checkbox" id = "rememberMe">记住我
				        </label>
				    </div>
					<button type="button" class="btn color1" id = "loginBtn">登录</button>
					<button type="button" class="btn color2" id="register">注册</button>
				</form>
			</div>
		</div>
		<div class="bac registerBac">
			<button class="btn back" id="back">返回</button>
			<h3>账户注册</h3><hr />
			<form id = "fm2">
				用户名 
				<input class="form-control " placeholder = "请输入用户名" id = "userName" name = "userName"/>
				密码 
				<input  class="form-control " type="password" id = "userPwd" name = "userPwd"/>
				确认密码 
				<input  class="form-control test" type="password" id = "confirmPassword"  name = "confirmPassword"/>
				验证码
				<input  class="form-control checkMark" placeholder = "请输入验证码" id = "checkMark" name = "checkMark"/>
			</form>
			<a href = "javascript:newCheckMark()" title = "点击更换">
				<img  src="${ctx}/kaptcha/getKaptchaImage" id = "checkPic" />
			</a>
			<br />
			<button class="btn registerBtn" id = "registerBtn">立即注册</button>
		</div>
		<div class="tip">
			<p></p>
		</div>
	<div>
	
	
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
						用户名<input type="text" class="form-control" name="userName" >
						<br/>
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
				<button type="button" class="btn btn-primary" id = "modifyData">
					提交更改
				</button>
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

	
	
	
	
	
	
	
	
	
	
	</body>
	
</html>