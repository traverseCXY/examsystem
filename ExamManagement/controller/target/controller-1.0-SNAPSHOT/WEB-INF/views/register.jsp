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
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<!--
		<link rel="stylesheet" type="text/css" href="styles.css">
		-->
		<title>注册</title>
		<meta name="viewport"
		content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>/static/resources/images/favicon.ico" />
		<link href="/Management/static/resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="/Management/static/resources/font-awesome/css/font-awesome.min.css"
		rel="stylesheet">
		<link href="/Management/static/resources/css/style.css" rel="stylesheet">
		<!-- Javascript files -->
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
							<h1><a href="#"><%--<img alt="" src="/Management/static/resources/images/logo.png">--%></a></h1>
						</div>
					</div>
					<div class="col-xs-7" id="login-info">
						<c:choose>
							<c:when test="${not empty sessionScope.user.userName}">
								<div id="login-info-user">
									
									<a href="user-detail/${sessionScope.user.userName}" id="system-info-account" target="_blank">${sessionScope.user.userName}</a>
									<span>|</span>
									<a href="/user/logout"><i class="fa fa-sign-out"></i> 退出</a>
								</div>
							</c:when>
							<c:otherwise>
								<a class="btn btn-primary" href="/Management/user/user-register">用户注册</a>
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
				<nav class="collapse navbar-collapse bs-navbar-collapse"
				role="navigation">

				</nav>
			</div>
		</div>

		<!-- Navigation bar ends -->

		<div class="content" style="margin-bottom: 100px;">

			<div class="container">
				<div class="row">

					<div class="col-md-12">
						<div class="lrform">
							<h5>注册账号</h5>
							<span class="form-message"></span>
							<div class="form">
								<!-- Register form (not working)-->
								<form class="form-horizontal" id="form-create-account"
								action="/Management/system/user-reg">
									<!-- Name -->
									<div class="form-group form-username">
										<label class="control-label col-md-3" for="name">账号</label>
										<div class="col-md-9">
											<input type="text" class="form-control" id="userName">
											<span class="form-message"></span>
										</div>

									</div>
									<!-- Email -->
									<div class="form-group form-email">
										<label class="control-label col-md-3" for="email">email</label>
										<div class="col-md-9">
											<input type="text" class="form-control" id="email">
											<span class="form-message"></span>
										</div>

									</div>
									<!-- password -->
									<div class="form-group form-password">
										<label class="control-label col-md-3" for="password">密码</label>
										<div class="col-md-9">
											<input type="password" class="form-control" id="password">
											<span class="form-message"></span>
										</div>

									</div>
									<!-- password-confirm -->
									<div class="form-group form-password-confirm">
										<label class="control-label col-md-3" for="password-confirm">确认密码</label>
										<div class="col-md-9">
											<input type="password" class="form-control" id="password-confirm">
											<span class="form-message"></span>
										</div>
									</div>

									<!-- Checkbox -->
									<div class="form-group form-confirm">
										<div class="col-md-9 col-md-offset-3">
											<label class="checkbox-inline">
												<input type="checkbox"
												id="inlineCheckbox1" value="agree">
												<a> 同意相关条款</a> </label>
											<span class="form-message"></span>
										</div>

									</div>

									<!-- Buttons -->
									<div class="form-group">
										<!-- Buttons -->
										<div class="col-md-9 col-md-offset-3">
											<button type="submit" class="btn" id="btn-reg">
												注册账号
											</button>
											<button type="reset" class="btn">
												重置
											</button>
										</div>
									</div>
								</form>
								已有账号? <a href="/Management/user/user-login">直接登录</a>
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
		<!-- jQuery -->
		<script type="text/javascript" src="/Management/static/resources/js/jquery/jquery-1.9.0.min.js"></script>
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="/Management/static/resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/register.js"></script>

	</body>
</html>
