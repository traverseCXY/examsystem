<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%-- <%@taglib uri="spring.tld" prefix="spring"%> --%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>个人设置</title>
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="keywords" content="">
<link rel="shortcut icon"
	href="<%=basePath%>/static/resources/images/favicon.ico" />
<link href="/Portal/static/resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
<link href="/Portal/static/resources/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<link href="/Portal/static/resources/css/style.css" rel="stylesheet">

<link href="/Portal/static/resources/css/exam.css" rel="stylesheet">
<link href="/Portal/static/resources/chart/morris.css" rel="stylesheet">
</head>
<body>
	<header>
		<div class="container">
			<div class="row">
				<div class="col-xs-5">
					<div class="logo">
						<h1>
							<a href="#"><%--<img alt="" src="/static/resources/images/logo.png">--%></a>
						</h1>
					</div>
				</div>
				<div class="col-xs-7" id="login-info">
					<c:choose>
						<c:when
							test="${not empty sessionScope.user.userName}">
							<div id="login-info-user">

								<a
									href="user-detail/${sessionScope.user.userName}"
									id="system-info-account" target="_blank">${sessionScope.user.userName}</a>
								<span>|</span> <a href="/Portal/logout"><i
									class="fa fa-sign-out"></i> 退出</a>
							</div>
						</c:when>
						<c:otherwise>
							<a class="btn btn-primary" href="/Portal/user/user-register">用户注册</a>
							<a class="btn btn-success" href="/Portal/user/user-login">登录</a>
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
				<ul class="nav navbar-nav">
						<li>
							<a href="/Portal/home"><i class="fa fa-home"></i>主页</a>
						</li>
						<li>
							<a href="/Portal/student/practice-list"><i class="fa fa-edit"></i>试题练习</a>
						</li>
						<li>
							<a href="/Portal/exam-list"><i class="fa  fa-paper-plane-o"></i>在线考试</a>
						</li>
						<li>
							<a href="/Portal/training-list"><i class="fa fa-book"></i>培训资料</a>
						</li>
						<li>
							<a href="/Portal/student/usercenter"><i class="fa fa-dashboard"></i>会员中心</a>
						</li>
						<li class="active">
							<a href="/Portal/student/setting"><i class="fa fa-cogs"></i>个人设置</a>
						</li>
					</ul>
			</nav>
		</div>
	</div>

	<!-- Navigation bar ends -->

	<!-- Slider starts -->

	<div>
		<!-- Slider (Flex Slider) -->

		<div class="container" style="min-height: 500px;">

			<div class="row">
				<div class="col-xs-2">
					<ul class="nav default-sidenav">
						<li class="active"><a> <i class="fa fa-cogs"></i> 基本资料
						</a></li>
						<li><a href="/Portal/student/change-password"> <i
								class="fa fa-wrench"></i> 修改密码
						</a></li>

					</ul>

				</div>
				<div class="col-xs-10">
					<div class="page-header">
						<h1>
							<i class="fa fa-cogs"></i> 基本资料
						</h1>
					</div>
					<div class="page-content row">
						<form class="form-horizontal" id="form-change-password"
							action="setting" style="margin-top: 40px;" method="post">

							<div class="form-line form-user-id-u" style="display: none;">
								<span class="form-label"><span class="warning-label">*</span>用户ID：</span>
								<input type="text" class="df-input-narrow" id="id-update"
									maxlength="20" value="${sessionScope.user.userId}"> <span class="form-message"></span> <br>
							</div>
							<div class="form-line form-username-u" style="display: block;">
								<span class="form-label"><span class="warning-label">*</span>用户名：</span>
								<input type="text" class="df-input-narrow" id="name-update"
									disabled="disabled" maxlength="20" value="${sessionScope.user.userName}"> <span
									class="form-message"></span> <br>
							</div>
							<div class="form-line form-truename-u" style="display: block;">
								<span class="form-label"><span class="warning-label">*</span>真实姓名：</span>
								<input type="text" class="df-input-narrow" id="truename-update"
									maxlength="20" value="${sessionScope.user.trueName}"> <span class="form-message"></span> <br>
							</div>
							<div class="form-line form-national-id-u" style="display: block;">
								<span class="form-label"><span class="warning-label">*</span>身份证号：</span>
								<input type="text" class="df-input-narrow"
									id="national-id-update" maxlength="18" value="${sessionScope.user.nationalId}"> <span
									class="form-message"></span> <br>
							</div>
							<div class="form-line form-phone-u" style="display: block;">
								<span class="form-label"><span class="warning-label">*</span>手机：</span>
								<input type="text" class="df-input-narrow" id="phone-update"
									maxlength="20" value="${sessionScope.user.phoneNum}"> <span class="form-message"></span> <br>
							</div>
							<div class="form-line form-email-u" style="display: block;">
								<span class="form-label"><span class="warning-label">*</span>邮箱：</span>
								<input type="text" class="df-input-narrow" id="email-update"
									maxlength="20" value="${sessionScope.user.email}"> <span class="form-message"></span> <br>
							</div>
							<!-- Buttons -->
							<div class="form-group">
								<!-- Buttons -->
								<div class="col-md-5 col-md-offset-2">
									<button class="btn btn-info" id="update-student-btn">确认修改</button>

								</div>
							</div>
						</form>
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
							ExamStack Copyright © <a href="http://www.examstack.com/"
								target="_blank">ExamStack</a> - <a href="." target="_blank">主页</a>
							| <a href="http://www.examstack.com/" target="_blank">关于我们</a> | <a
								href="http://www.examstack.com/" target="_blank">FAQ</a> | <a
								href="http://www.examstack.com/" target="_blank">联系我们</a>
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
		src="/Portal/static/resources/js/jquery/jquery-1.9.0.min.js"></script>
	<script type="text/javascript" src="/Portal/static/resources/js/all.js"></script>
	<!-- Bootstrap JS -->
	<script type="text/javascript"
		src="/Portal/static/resources/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/Portal/static/resources/chart/raphael-min.js"></script>
	<script type="text/javascript" src="/Portal/static/resources/chart/morris.js"></script>
	<script type="text/javascript" src="/Portal/static/resources/js/update-user.js"></script>
</body>
</html>