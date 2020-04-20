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
request.setAttribute("leftMenuId","");*/
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8"><meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title>主页</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>/static/resources/images/favicon.ico" />
		<link href="/Management/static/resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="/Management/static/resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="/Management/static/resources/css/style.css" rel="stylesheet">
		<link href="/Management/static/resources/css/jquery-ui-1.9.2.custom.min.css" rel="stylesheet" type="text/css" />
		<link href='/Management/static/resources/fullcalendar-2.1.1/fullcalendar.css' rel='stylesheet' />
		<link href='/Management/static/resources/fullcalendar-2.1.1/fullcalendar.print.css' rel='stylesheet' media='print' />
	
		
		<link href="/Management/static/resources/css/question-add.css" rel="stylesheet">
		<link href="/Management/static/resources/chart/morris.css" rel="stylesheet">
		<style>
			input.add-ques-amount,input.add-ques-score{
				width:50px;
				margin-right:0;
			}
			.bs-example-bg-classes p {
			    padding: 15px;
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
							<h1><a href="/Management/admin/dashboard">考试管理系统</a></h1>
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
							<a style="border-top: 1px solid #ff6600;" href="/Management/dashboard"><i class="fa fa-dashboard"></i>控制面板</a>
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

				<%--左侧导航栏--%>
				<div class="row">
					<div class="col-xs-2" id="left-menu">
						<ul class="nav default-sidenav">
							<li>
								<a href="/Management/question/question-add" title="添加试题"><i class="fa fa-plus">&nbsp</i><span class="left-menu-item-name"> 添加试题</span></a>
							</li>
							<li>
								<a href="/Management/exampaper/exampaper-add" title="创建新试卷"><i class="fa fa-leaf">&nbsp;</i><span class="left-menu-item-name"> 创建新试卷</span></a>
							<li>
								<a href="/Management/exam/exam-add" title="发布考试"><i class="fa fa-plus-square-o">&nbsp;</i><span class="left-menu-item-name"> 发布考试</span></a>
							</li>
							<li>
								<a href="/Management/exam/model-test-add" title="发布模拟考试"><i class="fa fa-flag">&nbsp;</i><span class="left-menu-item-name"> 发布模拟考试</span></a>
							</li>
						</ul>
					</div>
					<div class="col-xs-10" id="right-content">
						<div class="page-header">
							<h1><i class="fa fa-dashboard"></i> DashBoard </h1>
						</div>
						<div class="page-content">
   							<div class="bs-example bs-example-bg-classes" data-example-id="contextual-backgrounds-helpers">
							    <p class="bg-success">
							    	<i class="ace-icon fa fa-check green"></i>
							    
							   		欢迎使用考试管理系统，您可以在此页面快速查看您系统的状态。
							    </p>
							    
							    <div class="row" style="margin-top:20px;">
							    	<div class="col-xs-7">
							    		<div style="height:100px;">
							    			<div class="infobox infobox-green infobox-small">
											<div class="infobox-progress">
												<i class="fa fa-cloud"></i>
											</div>
											<div class="infobox-data">
												<div class="infobox-content">试题</div>
												<div class="infobox-content" id="question-num">-</div>
											</div>
											</div>
											<div class="infobox infobox-dark infobox-small">
												<div class="infobox-progress">
													<i class="fa fa-file-text-o"></i>
												</div>
												<div class="infobox-data">
													<div class="infobox-content">试卷</div>
													<div class="infobox-content" id="questionpaper-num">-</div>
												</div>
											</div>
											<div class="infobox infobox-blue infobox-small">
												<div class="infobox-progress">
													<i class="fa fa-user"></i>
												</div>
												<div class="infobox-data">
													<div class="infobox-content">学员</div>
													<div class="infobox-content" id="student-num">-</div>
												</div>
											</div>
							    		</div>
										
										
										
										<div class="widget-box transparent">
							    			<div class="widget-header widget-header-flat">
												<h4 class="widget-title lighter">
													<i class="ace-icon fa fa-star orange"></i>
													需要审核
												</h4>
												
												<div class="widget-toolbar no-border">
													<a href="/Management/exam/exam-list" target="_blank">查看更多</a>
												</div>
											</div>
							    			<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table-striped table" id="studentApprovedTable">
									<thead>
										<tr>
											<td>学员ID</td>
											<td>姓名</td>
											<td>单位</td>
										
											<td>
												操作
											</td>
										</tr>
									</thead>
									<tbody>
										
									</tbody>
									<tfoot>
	
									</tfoot>
								</table>
													
													
																									
												</div>
											</div>
							    		</div>
										<div class="widget-box transparent">
							    			<div class="widget-header widget-header-flat">
												<h4 class="widget-title lighter">
													<i class="ace-icon fa fa-rss orange"></i>
													需要阅卷
												</h4>
												
												<div class="widget-toolbar no-border">
													<a href="/Management/exam/exam-list" target="_blank">查看更多</a>
												</div>
											</div>
							    			<div class="widget-body">
												<div class="widget-main no-padding">
													<table class="table-striped table" id="studentMarkTable">
									<thead>
										<tr>
											<td>学员ID</td>
											<td>姓名</td>
											<td>单位</td>
											<td>交卷时间</td>
											<td>
												操作
											</td>
										</tr>
									</thead>
									<tbody>
									</tbody>
									<tfoot>
									</tfoot>
								</table>
												</div>
											</div>
							    		</div>
																			    		
							    	</div>
							    	<div class="col-xs-5">
							    		<div class="widget-box">
							    			<div class="widget-header widget-header-flat">
												<h4 class="widget-title lighter">
													<i class="ace-icon fa fa-signal orange"></i>
													Popular Domains
												</h4>
												
												<div class="widget-toolbar no-border">
													<select id="field-select">
														<c:forEach items="${fieldList }" var="item">
															<option value="${item.fieldId}">${item.fieldName}</option>
														</c:forEach>
													</select>
												</div>
											</div>
							    			<div class="widget-body">
												<div class="widget-main no-padding">
													<div id="mychart" style="height:400px;"></div>
												</div>
											</div>
							    		</div>
							    		
							    		<div id="calendar" style="margin-top:50px;">
							    		</div>
							    	</div>
							    </div>
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
		<script type="text/javascript" src="/Management/static/resources/js/jquery/jquery-1.9.0.min.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/all.js"></script>
		
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="/Management/static/resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/jquery/jquery-ui-1.9.2.custom.min.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/echarts-all.js"></script>
		<script type="text/javascript" src="/Management/static/resources/fullcalendar-2.1.1/lib/moment.min.js"></script>
		<script type="text/javascript" src="/Management/static/resources/fullcalendar-2.1.1/fullcalendar.min.js"></script>
		<script type="text/javascript" src="/Management/static/resources/fullcalendar-2.1.1/lang-all.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/dashboard.js"></script>
	</body>
</html>