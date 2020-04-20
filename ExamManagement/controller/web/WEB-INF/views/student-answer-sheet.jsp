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
		<title>学员答卷</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>/static/resources/images/favicon.ico" />
		<link href="/Management/static/resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="/Management/static/resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="/Management/static/resources/css/style.css" rel="stylesheet">
		
		<link href="/Management/static/resources/css/exam.css" rel="stylesheet">
		<link href="/Management/static/resources/chart/morris.css" rel="stylesheet">
		<style>
			#add-more-qt-to-paper{
				cursor: pointer;
				color: #1ba1e2;
			}
			#add-more-qt-to-paper:hover{
				color:#ff7f74;
			}
			#add-more-qt-to-paper i{
				color: #47a447;
				cursor: pointer;
				margin-right:5px;	
			}
			
			#qt-selector-iframe{
				border:none;
				height:600px;
			}
			.tmp-ques-remove{
				margin-left:10px;
			}
			
			.question-point{
				padding:0 8px;
				margin:0 2px;
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
							<a style="border-top: 1px solid #ff6600;"  href="/Management/exam/exam-list"><i class="fa fa-trophy"></i>考试管理</a>
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
								<a href="/Management/exam/exam-list" title="考试管理"><i class="fa fa-list">&nbsp</i><span class="left-menu-item-name"> 考试管理</span></a>
							</li>
							<li>
								<a href="/Management/exam/exam-add" title="发布考试"><i class="fa fa-plus-square-o">&nbsp;</i><span class="left-menu-item-name"> 发布考试</span></a>
							</li>
							<li>
								<a href="/Management/exam/model-test-list" title="模拟考试"><i class="fa fa-glass">&nbsp;</i><span class="left-menu-item-name"> 模拟考试</span></a>
							</li>
							<li>
								<a href="/Management/exam/model-test-add" title="发布模拟考试"><i class="fa fa-flag">&nbsp;</i><span class="left-menu-item-name"> 发布模拟考试</span></a>
							</li>
							<li>
								<a href="" title="学员名单"><i class="fa fa-sitemap">&nbsp;</i><span class="left-menu-item-name"> 学员名单</span></a>
							</li>
							<li style="background: #0088cc;color: white">
								<a style="color: white" href="" title="学员试卷"><i class="fa fa-file-o">&nbsp;</i><span class="left-menu-item-name"> 学员试卷</span></a>
							</li>
						</ul>
					</div>
					<div class="col-xs-10" id="right-content">
						<div class="page-header">
							<h1><i class="fa fa-file-text"></i> 学员试卷 </h1>
						</div>
						<div class="page-content">
							<div class="def-bk" id="bk-exampaper">

								<div class="expand-bk-content" id="bk-conent-exampaper">
									<div id="exampaper-header">
										<div id="exampaper-title">
											<i class="fa fa-send"></i>
											<span id="exampaper-title-name">${exampapername} </span>
											<span style="display:none;" id="exampaper-id">${exampaperid}</span>
											
										</div>
										<div id="exampaper-desc-container">
											<div id="exampaper-desc" class="exampaper-filter">
												
											
											</div>
											<div style="margin-top: 5px;">
												<span>试卷总分：</span><span id="exampaper-total-point" style="margin-right:20px;font-weight:bolder;"></span>
												<span>考生得分：</span><span id="exampaper-raw-point" style="color: #5cb85c;margin-right:20px;font-weight:bolder;"></span>
												<!-- <span id="add-more-qt-to-paper"><i class="small-icon fa fa-plus-square" title="添加选项"></i><span>增加更多题目</span></span> -->
											</div>
										</div>
										
										
									</div>
									<input type="hidden" id="hist-id" value="${examHistoryId }">
									<input type="hidden" id="paper-id" value="${examPaperId }">
									<input type="hidden" id="exam-id" value="${examId }">
									<ul id="exampaper-body" style="padding:0px;">
										${htmlStr }
									</ul>
									<div id="exampaper-footer">
										<div id="question-navi">
											<div id="question-navi-controller">
												<i class="fa fa-arrow-circle-down"></i>
												<span>答题卡</span>
											</div>
											<div id="question-navi-content">
											</div>
										</div>
										<!-- <div style="padding-left:30px;">
											<button class="btn btn-info"><i class="fa fa-save"></i>完成阅卷</button>
										</div>
										 -->
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
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="/Management/static/resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/all.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/exampaper-mark.js"></script>
		<script>
			$(function(){
				$(".raw-answer-point").attr("disabled","disabled");
				$(".raw-answer-comment").attr("disabled","disabled");
			});
		</script>
	</body>
</html>