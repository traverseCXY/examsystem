<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme() + "://"
+ request.getServerName() + ":" + request.getServerPort()
+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>登录</title>
		
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>/static/resources/images/favicon.ico" />
		<link href="/Management/static/resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="/Management/static/resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="/Management/static/resources/css/style.css" rel="stylesheet">
		<style type="text/css">
			.form-group {
				margin-bottom: 5px;
				height: 59px;
			}

		</style>
	</head>
	<body>
		<header>
			<div class="container">
				<div class="row">
					<div class="col-xs-5">
						<div class="logo">
							<h1><a href="#">考试管理系统</a></h1>
						</div>
					</div>
					<div class="col-xs-7" id="login-info">
						<c:choose>
							<c:when test="${not empty sessionScope.user.userName}">
								<div id="login-info-user">
									
									<a href="user-detail/${sessionScope.user.userName}" id="system-info-account" target="_blank">${sessionScope.user.userName}</a>
									<span>|</span>
									<a href="/Management/user/logout"><i class="fa fa-sign-out"></i> 退出</a>
								</div>
							</c:when>
							<c:otherwise>
								<a class="btn btn-primary" id="register" href="#">用户注册</a>
								<a class="btn btn-success" href="/Management/user/user-login">登录</a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</header>
		<!-- Navigation bar starts -->

		<div class="navbar bs-docs-nav" role="banner">
			<div class="container">
				<nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation" >
				</nav>
			</div>
		</div>

		<!-- Navigation bar ends -->

		<div class="content" style="margin-bottom: 100px;background: rgb(77,191,217);margin-top:0;">
 
			<div class="container">
				<div class="row">
					<div class="col-md-7" id="admin-login-bg">
						<p>
							
						</p>
					</div>
					<div class="col-md-5">
						<div class="lrform">
							<h5>登陆考试管理系统</h5>
							<div class="form">
								<!-- Login form (not working)-->
								<form class="form-horizontal" action="/Management/login" method="post">
									<!-- Username -->
									<div class="form-group">
										<label class="control-label col-md-3" for="username">用户名</label>
										<div class="col-md-9">
											<input type="text" class="form-control" name="userName">
										</div>
									</div>
									<!-- Password -->
									<div class="form-group">
										<label class="control-label col-md-3" for="password">密码</label>
										<div class="col-md-9">
											<input type="password" class="form-control" name="password">
										</div>
									</div>
									<!-- Buttons -->
									<div class="form-group">
										<!-- Buttons -->
										<div class="col-md-9 col-md-offset-3">
											<button type="submit" class="btn btn-default">
												登陆
											</button>
											<button type="reset" class="btn btn-default">
												取消
											</button>
											<span class="form-message">${result}</span>
										</div>
									</div>
								</form>
								<i class="fa fa-info"></i> 
								通过后台管理员账号登陆系统
								<div style="color: red">${message}</div>
							</div>
						</div>

					</div>
				</div>

			</div>

		</div>
		<footer>
			<div class="container">
				<div class="row">
					<div class="col-md-12">
						<div class="copy">
							<p>
								 © <a href="/Management/dashboard" target="_blank">考试管理系统</a> - <a href="." target="_blank">主页</a> | <a href="/Management/dashboard" target="_blank">关于我们</a> | <a href="/Management/dashboard" target="_blank">FAQ</a> | <a href="/Management/dashboard" target="_blank">联系我们</a>
							</p>
						</div>
					</div>
				</div>

			</div>

		</footer>

		<!-- Slider Ends -->

		<!-- Javascript files -->
		<!-- jQuery -->
		<script type="text/javascript"
		src="/Management/static/resources/js/jquery/jquery-1.9.0.min.js"></script>
		<!-- Bootstrap JS -->
		<script type="text/javascript"
		src="/Management/static/resources/bootstrap/js/bootstrap.min.js"></script>

	<script>
		$("#register").click(){
			alert("目前不支持注册，请联系后台管理员通过进入该系统实现注册功能！");
		}
	</script>

	</body>
</html>