<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en" >
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">


<title>WeChat - Login</title>

<!-- Custom fonts for this template-->
<link href="./resources/css/all.min.css" rel="stylesheet"
	type="text/css">

<!-- Custom styles for this template-->
<link href="./resources/css/sb-admin.css" rel="stylesheet">





</head>

<body class="bg-dark">

	<div class="container">
		<div class="card card-login mx-auto mt-5">
			<div class="card-header">Login</div>
			<div class="card-body">
				<form action="login" method="post" >
					<div class="form-group">
						<div class="form-label-group">
							<input type="text" id="userName" name="userName"
								class="form-control" placeholder="UserName" required="required"
								autofocus="autofocus"> <label for="userName">userName</label>
						</div>
					</div>
					<div class="form-group">
						<div class="form-label-group">
							<input type="password" id="userPassword" name="userPassword"
								class="form-control" placeholder="user_Password"
								required="required"> <label for="userPassword">userPassword</label>
						</div>
					</div>
					<div class="form-group">
						<div class="form-label-group">
							<input type="text" id="verificationCode" name="verificationCode"
								class="form-control" placeholder="请输入以下图片中的验证码"
								required="required" autofocus="autofocus"> <label
								for="verificationCode">请输入以下图片中的验证码</label><br /> 
								<img id="codeImg" alt="点击更换" src="./Kaptcha" onclick="changeVerifyCode(this)" /><span>&nbsp;&nbsp;看不清楚?点击图片刷新验证码</span>
						</div>
					</div>
					<div class="form-group">
						<div class="checkbox">
							<label> <input type="checkbox" value="remember-me"  id="remember">
								Remember Password
							</label>
							<div style="color:red">${msg }</div>
						</div>
					</div>
					<div class="text-center">
						<input type="submit" style="width: 50%" value="login" onclick="return check()" >
						<!-- <button id="login" type="button" style="width: 50%"  onclick="return false;" >login</button> -->
					</div>
				</form>
			</div>
		</div>
	</div>

	<!-- Bootstrap core JavaScript-->
	<script src="./resources/jquery/jquery-3.4.1.min.js"></script>
	<script src="./resources/bootstrap/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="./resources/jquery/jquery.easing.min.js"></script>
	<script src="./resources/js/common.js"></script>
	<script src="./resources/js/login.js"></script>
	<script type="text/javascript">
	function check() {
		var username = $('#userName').val();
		var userpassword = $('#userPassword').val();

		if ($('#remember').is(':checked')) {
			setCookie('username', username, 1000);
			setCookie('userpassword', userpassword, 1000);
		} else {
			delCookie('nameUser');
			delCookie('passUser');
		}

		return true;

	};
	// 读取cookie
	window.onload = function() {

		var oUser = document.getElementById('userName');
		var oPswd = document.getElementById('userPassword');

		var oRemember = document.getElementById('remember');
		// 页面初始化时，如果帐号密码cookie存在则填充
		var tmpName = getCookie('username');
		var tmpPwd = getCookie('userpassword');
		if (tmpName && tmpPwd) {
			// alert("oUser="+tmpName+" oPswd="+tmpPwd);
			if (tmpName != 'null' && tmpPwd != 'null') {
				oUser.value = tmpName;
				oPswd.value = tmpPwd;
				oRemember.checked = true;
			}
		}

	};

	// 设置cookie
	function setCookie(name, value, day) {
		var date = new Date();
		date.setTime(date.getTime() + day * 24 * 60 * 60 * 30);
		document.cookie = name + '=' + value + ';expires=' + date.toGMTString();
	}
	;
	// 获取cookie
	function getCookie(name) {
		var reg = RegExp(name + '=([^;]+)');
		var arr = document.cookie.match(reg);
		if (arr) {
			return arr[1];
		} else {
			return '';
		}
	}
	;
	// 删除cookie
	function delCookie(name) {
		setCookie(name, null, -1);
	}
	;
	</script>
</body>

</html>
