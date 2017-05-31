<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>国家职业教育专业教学资源库运行监测平台</title>
	
	<link href="css/mainstyle.css" rel="stylesheet" type="text/css" />
	<link href="css/login.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript">
		function login(){
			var u = $('#loginName').val();
			var p = $('#password').val();
			if(u=='' || p==''){
				$('#message').html('用户名和密码不能为空!');
				return;
			}
			loginform.submit();
		}
	</script>
</head>

<body>
<div class="login_main">
	<div class="login_head">
		<div class="bigbox">
			<a class="head_left" href="#">
				<p>监测平台</p>
			</a>
		</div>
	</div>
	
	<div class="login_bigbox">
		<div class="login_content">
			<form id="loginform" method="post" action="${pageContext.request.contextPath}/login.action">
				<div class="loginname">
					<input id="loginName" name="loginName" type="text" placeholder="请输入用户名" class="login-input"/>
				</div>
				<div class="password">
					<input id="password" name="password" type="password" placeholder="请输入密码" class="login-input"
						onKeyDown="javascript:if (event.keyCode==13) login();"/>
				</div>
				<div>
					<a href="javascript:;" onclick="login();" class="login_btn">登录</a>
				</div>
				<div class="login_info">&nbsp;<span id="message" style="color:red">${message}</span></div>
			</form>
		</div>
	</div>
	
	<div class="login_foot">
		<div class="bigbox textcenter">
			<span style="text-align: center;">&copy;数字化学习技术集成与应用教育部工程研究中心，依托单位：国家开放大学</span>
		</div>
	</div>
</div>
</body>
</html>
