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
		<title>标签管理</title>
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>/static/resources/images/favicon.ico" />
		<link href="/Management/static/resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="/Management/static/resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="/Management/static/resources/css/style.css" rel="stylesheet">
		
		<link href="/Management/static/resources/css/exam.css" rel="stylesheet">
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
							<a href="/Management/question/question-list"><i class="fa fa-cloud"></i>试题管理</a>
						</li>
						<li>
							<a href="/Management/exampaper/exampaper-list"><i class="fa fa-file-text-o"></i>试卷管理</a>
						</li>
						<li>
							<a href="/Management/exam/exam-list"><i class="fa fa-trophy"></i>考试管理</a>
						</li>
						<li>
							<a style="border-top: 1px solid #ff6600;"  href="/Management/common/tag-list"><i class="fa fa-cubes"></i>通用数据</a>
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
							<li style="background: #0088cc;color: white">
								<a style="color: white" href="/Management/common/tag-list" title="标签管理"><i class="fa fa-list">&nbsp</i><span class="left-menu-item-name"> 标签管理</span></a>
							</li>
							<li>
								<a href="/Management/common/field-list" title="专业题库"><i class="fa fa-anchor">&nbsp</i><span class="left-menu-item-name"> 专业题库</span></a>
							</li>
							<li>
								<a href="/Management/common/knowledge-list" title="知识分类"><i class="fa fa-flag">&nbsp</i><span class="left-menu-item-name"> 知识分类</span></a>
							</li>
						</ul>
					</div>
					<div class="col-xs-10" id="right-content">
						<div class="page-header">
							<h1> <i class="fa fa-bar-chart-o"></i> 标签管理 </h1>
						</div>
						<div class="page-content">
							<div id="tag-list">
								<div class="table-controller">
									<div class="btn-group table-controller-item" style="float:left">
										<button class="btn btn-default btn-sm" id="add-tag-modal-btn">
											<i class="fa fa-plus-square"></i> 添加标签
										</button>						
									</div>		
								</div>
								<table class="table-striped table">
									<thead>
										<tr>
											<td></td>
											<td>ID</td>
											<td>标签名</td>
											<td>描述</td>
											<td>创建时间</td>
											<td>创建人</td>
											<td>私有</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody id="tbody">
										<c:forEach items="${tagList }" var="item">
											<tr>
												<td></td>
												<td>${item.tagId }</td>
												<td>${item.tagName }</td>
												<td>${item.memo }</td>
												<td><fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm"/></td>
												<td>${item.creatorName }</td>
												<td>${item.privatee }</td>
												<td><span class="delete-btn btn-sm btn-danger" data-id="${item.tagId }"><i class="ace-icon fa fa-trash-o"></i></span></td>
											</tr>
										</c:forEach>
										
									</tbody><tfoot></tfoot>
								</table>
							</div>

							<%--分页--%>
							<c:choose>
								<c:when test="${count > 10}">
									<div class="page-link-content" style="float: right;margin-left: 30px;">
										<ul class="pagination pagination-sm">
											<li><a  id="prev" class="${pageInfo.prePage}" href="/Management/common/tag-list?pageNum=${pageInfo.prePage}">上一页</a> </li>
											<c:forEach items="${pageInfo.navigatepageNums}" var="page">
												<li><a  href="/Management/common/tag-list?pageNum=${page}">${page}</a></li>
											</c:forEach>
											<li><a id="next" class="${pageInfo.nextPage}" href="/Management/common/tag-list?pageNum=${pageInfo.nextPage}">下一页</a></li>
										</ul>
									</div>
								</c:when>
								<c:otherwise></c:otherwise>
							</c:choose>

						</div>
					</div>
				</div>
			</div>
			<div class="modal fade" id="add-tag-modal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
								<div class="modal-dialog">
									<div class="modal-content">
										<div class="modal-header">
											<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
												&times;
											</button>
											<h6 class="modal-title" id="myModalLabel">添加标签</h6>
										</div>
										<div class="modal-body">
											<form id="tag-add-form" style="margin-top:40px;"  action="/Management/common/tag-add">
												<div class="form-line form-tag-name" style="display: block;">
													<span class="form-label"><span class="warning-label">*</span>名称：</span>
													<input type="text" class="df-input-narrow" id="name">
													<span class="form-message"></span>
													<br>
												</div>
												<div class="form-line form-tag-desc" style="display: block;">
													<span class="form-label"><span class="warning-label">*</span>描述：</span>
													<input type="text" class="df-input-narrow" id="memo">
													<span class="form-message"></span>
													<br>
												</div>
											</form>

										</div>
										<div class="modal-footer">
											<button type="button" class="btn btn-default" data-dismiss="modal">
												关闭窗口
											</button>
											<button id="add-tag-btn" type="button" class="btn btn-primary">
												确定添加
											</button>
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
		<script type="text/javascript" src="/Management/static/resources/js/tag-list.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/add-tag.js"></script>
		<script>
			$(function() {
				$("#add-tag-modal-btn").click(function() {
					$("#add-tag-modal").modal({
						backdrop : true,
						keyboard : true
					});
				});
			});
		</script>
	</body>
</html>