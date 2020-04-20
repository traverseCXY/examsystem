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
    
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>发布培训</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>/static/resources/images/favicon.ico" />
		<link href="/Management/static/resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="/Management/static/resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="/Management/static/resources/css/style.css" rel="stylesheet">
		<link href="/Management/static/resources/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
	
		
		<link href="/Management/static/resources/css/question-add.css" rel="stylesheet">
		<link href="/Management/static/resources/chart/morris.css" rel="stylesheet">
		<style>
			input.add-ques-amount,input.add-ques-score{
				width:50px;
				margin-right:0;
			}
		
		</style>
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
		<%--顶部导航栏--%>
		<div class="navbar bs-docs-nav" role="banner">
			<div class="container">
				<nav class="collapse navbar-collapse bs-navbar-collapse" role="navigation">
					<ul class="nav navbar-nav">
						<li>
							<a href="/Management/dashboard"><i class="fa fa-dashboard"></i>控制面板</a>
						</li>
						<li>
							<a href="/Management/question/question-list"><i class="fa fa-cloud"></i>试题管理</a>
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
					<%--左侧导航栏--%>
					<div class="col-xs-2" id="left-menu">
						<ul class="nav default-sidenav">
							<li>
								<a href="/Management/training/training-list" title="培训管理"><i class="fa fa-list">&nbsp</i><span class="left-menu-item-name"> 培训管理</span></a>
							</li>
							<li style="background: #0088cc;color: white">
								<a style="color: white" href="/Management/training/training-add" title="添加培训"><i class="fa fa-plus">&nbsp</i><span class="left-menu-item-name"> 添加培训</span></a>
							</li>
						</ul>
					</div>
					<div class="col-xs-10" id="right-content">
						<div class="page-header">
							<h1><i class="fa fa-file-text-o"></i> 发布培训 </h1>
						</div>
						<div class="page-content">
							<form id="form-training-add">
								<div class="form-line add-update-training-name">
									<span class="form-label"><span class="warning-label">*</span>培训名称：</span>
									<input id="exam-name" type="text" class="df-input-narrow">
									<span class="form-message"></span>
								</div>
								<div class="form-line add-update-training-desc">
									<span class="form-label"><span class="warning-label">*</span>培训描述：</span>
									<textarea id="exam-name" rows="8" style="width:600px;"></textarea>
									<span class="form-message"></span>
								</div>
								<div class="form-line add-update-training-type" style="display:none;">
									<span class="form-label"><span class="warning-label">*</span>私有培训：</span>
									<input id="training-type" type="checkbox" class="df-input-narrow" checked="checked">
									<span class="form-message"></span>
								</div>
								<div class="form-line add-update-training-field">
									<span class="form-label"><span class="warning-label">*</span>培训专业：</span>
									<select class="df-input-narrow">
										<option value="-1">------请选择培训专业------</option>
										<c:forEach items="${fieldList }" var="item">
											<option value="${item.fieldId }">${item.fieldName }</option>
										</c:forEach>
										
									</select>
									<span class="form-message"></span>
								</div>
								<div class="form-line form-training-duration ">
									<span class="form-label"><span class="warning-label">*</span>失效日期：</span>
									<input id="training-exp-date" type="text" class="df-input-narrow">
									<span class="form-message"></span>
								</div>
								
								<div class="form-line">
									<input value="确认发布" type="button" id="training-add-btn" class="df-submit btn-info btn">
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
		
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="/Management/static/resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/jquery/jquery-ui-1.9.2.custom.min.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/jquery.ui.datepicker-zh-TW.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#training-exp-date").datepicker();
				$("#training-exp-date").datepicker("option", "dateFormat", "yy-mm-dd");
			});
		</script>
		<script type="text/javascript" src="/Management/static/resources/js/add-training.js"></script>
	</body>
</html>