<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- <%@taglib uri="spring.tld" prefix="spring"%> --%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String servletPath = (String)request.getAttribute("javax.servlet.forward.servlet_path");
String[] list = servletPath.split("\\/");
/*request.setAttribute("role",list[1]);
request.setAttribute("topMenuId",list[2]);
request.setAttribute("leftMenuId",list[3]);*/
%>

<!DOCTYPE html>
<html>
  	<head>
   		<base href="<%=basePath%>">
   	 	<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>导入试题</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>/static/resources/images/favicon.ico" />
		<link href="/Management/static/resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="/Management/static/resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="/Management/static/resources/css/style.css" rel="stylesheet">
		
		<link href="/Management/static/resources/css/question-add.css" rel="stylesheet">
		<link href="/Management/static/resources/chart/morris.css" rel="stylesheet">
	</head>
	<body>
		<header>
			<span style="display:none;" id="rule-role-val"><%=list[1]%></span>
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
				<nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
					<ul class="nav navbar-nav">
						<li>
							<a href="/Management/dashboard"><i class="fa fa-dashboard"></i>控制面板</a>
						</li>
						<li>
							<a style="border-top: 1px solid #ff6600;"  href="/Management/question/question-list"><i class="fa fa-cloud"></i>试题管理</a>
						</li>
						<li>
							<a href="/Management/exampaper/exampaper-list"><i class="fa fa-file-text-o"></i>试卷管理</a>
						</li>
						<li>
							<a href="/Management/exam/exam-list"><i class="fa fa-trophy"></i>考试管理</a>
						</li>
						<li>
							<a href="/Management/common/tag-list"><i class="fa fa-cubes"></i>通用数据</a>
						</li>
						<li>
							<a href="/Management/system/news-list"><i class="fa fa-cog"></i>系统设置</a>
						</li>
					</ul>
				</nav>
			</div>
		</div>

		<!-- Navigation bar ends -->

		<!-- Slider starts -->

		<div>
			<!-- Slider (Flex Slider) -->

			<div class="container" style="min-height:500px;">

				<div class="row">
					<div class="col-xs-2" id="left-menu">
						<ul class="nav default-sidenav">
							<li>
								<a href="/Management/question/question-list" title="试题管理"><i class="fa fa-list">&nbsp</i><span class="left-menu-item-name"> 试题管理</span></a>
							</li>
							<li>
								<a href="/Management/question/question-add" title="添加试题"><i class="fa fa-plus">&nbsp</i><span class="left-menu-item-name"> 添加试题</span></a>
							</li>
							<li style="background: #0088cc;color: white">
								<a style="color: white" href="/Management/question/question-import" title="导入试题"><i class="fa fa-cloud-upload">&nbsp</i><span class="left-menu-item-name"> 导入试题</span></a>
							</li>
						</ul>
					</div>
					<div class="col-xs-10" id="right-content">
						<div class="page-header">
							<h1><i class="fa fa-cloud-upload"></i> 导入试题 </h1>
						</div>
						<div class="page-content" style="padding-top:20px;">
							<form id="from-question-import" action="secure/question-import">
							<div class="form-line upload-question-group">
								<span class="form-label">选择题库：</span>
								<select class="df-input-narrow">
									<option value="0">-- 请选择 --</option>
										<c:forEach items="${fieldList }" var="item">
											<option value="${item.fieldId }">${item.fieldName }</option>
										</c:forEach>
								</select>
								<span class="form-message"></span>
							</div>
							<div class="form-line template-download">
								<span class="form-label">下载模板：</span>
								<a href="resources/template/question.xlsx" style="color:rgb(22,22,22);text-decoration: underline;">点击下载</a>
							</div>
							<div class="form-line control-group">
								<span class="form-label"><span class="warning-label">*</span>上传文件：</span>
								<div class="controls file-form-line">
									<div>
										<div id="div-file-list"></div>
										<!-- 用来作为文件队列区域 -->
										<div id="fileQueue"></div>
										<div id="uploadify"></div>
									</div>
									<span class="help-inline form-message"></span>
								</div>
							</div>
							<div class="form-line">
								<input value="提交" type="submit" class="df-submit btn btn-info">
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
		<script type="text/javascript" src="/Management/static/resources/js/jquery/jquery-1.9.0.min.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/all.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/uploadify/jquery.uploadify3.1Fixed.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/question-import.js"></script>
		
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="/Management/static/resources/bootstrap/js/bootstrap.min.js"></script>
	</body>
</html>