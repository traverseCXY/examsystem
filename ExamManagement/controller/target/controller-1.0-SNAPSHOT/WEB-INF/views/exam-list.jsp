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
	String servletPath = (String) request.getAttribute("javax.servlet.forward.servlet_path");
	String[] list = servletPath.split("\\/");
	/*request.setAttribute("role", list[1]);
	request.setAttribute("topMenuId", list[2]);
	request.setAttribute("leftMenuId", list[3]);*/
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>考试管理</title>
<meta name="keywords" content="">
<link rel="shortcut icon"
	href="<%=basePath%>/static/resources/images/favicon.ico" />
<link href="/Management/static/resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
<link href="/Management/static/resources/font-awesome/css/font-awesome.min.css"
	rel="stylesheet">
<link href="/Management/static/resources/css/style.css" rel="stylesheet">

<link href="/Management/static/resources/css/exam.css" rel="stylesheet">
<link href="/Management/static/resources/chart/morris.css" rel="stylesheet">
<style>
.change-property, .publish-paper, .delete-paper, .offline-paper {
	cursor: pointer;
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
						<h1>
							<a href="#">考试管理系统</a>
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
									id="system-info-account" target="_blank">${sessionScope.SPRING_SECURITY_CONTEXT.user.userName}</a>
								<span>|</span> <a href="/Management/user/logout"><i
									class="fa fa-sign-out"></i> 退出</a>
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
			<nav class="collapse navbar-collapse bs-navbar-collapse"
				role="navigation">
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

		<div class="container" style="min-height: 800px;">
			<%--左侧导航栏--%>
			<div class="row">
				<div class="col-xs-2" id="left-menu">
					<ul class="nav default-sidenav">
						<li style="background: #0088cc;color: white">
							<a style="color: white" href="/Management/exam/exam-list" title="考试管理"><i class="fa fa-list">&nbsp</i><span class="left-menu-item-name"> 考试管理</span></a>
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
					</ul>
				</div>
				<div class="col-xs-10" id="right-content">
					<div class="page-header">
						<h1>
							<i class="fa fa-list-ul"></i> 考试管理
						</h1>
					</div>
					<div class="page-content">
						<div id="question-list">
							<table class="table-striped table">
								<thead>
									<tr>
										<td>ID</td>
										<td>考试名称</td>
										<td>起始时间</td>
										<td>截止时间</td>
										<td>试卷</td>
										<td>创建人</td>
										<td>状态</td>
										<td>操作</td>
									</tr>
								</thead>
								<tbody id="tbody">
									<c:forEach items="${examList}" var="item">
										<tr>
											<td>${item.examId }</td>
											<td><span>${item.examName }</span><br><a href="<%=list[1]%>/exam-student-list/${item.examId }" target="_blank">学员名单</a></td>
											<td><fmt:formatDate value="${item.effTime}" pattern="yyyy-MM-dd HH:mm" /></td>
											<td><fmt:formatDate value="${item.expTime}" pattern="yyyy-MM-dd HH:mm" /></td>
											<td>${item.examPaperName }</td>
											<td>${item.creatorId }</td>
											<td><c:choose>
												<c:when test="${item.approved == 0 }">
													未审核
												</c:when>
												<c:when test="${item.approved == 1 }">
													审核通过
												</c:when>
												<c:otherwise>
													审核未通过
												</c:otherwise>
											</c:choose>
											</td>
											<td><c:choose>
													<c:when test="${item.approved == 0 }">
														<button class="approved-btn" data-id="${item.examId }">通过</button>
														<button class="disapproved-btn" data-id="${item.examId }">不通过</button>
													</c:when>
													<c:when test="${item.approved == 2 }">
														<button class="delete-btn" data-id="${item.examId }">删除</button>
													</c:when>
												</c:choose>
												<span class="link-user-r-btn btn-sm btn-info" title="关联学员" data-id="${item.examId }">
												<i class="fa fa-random"></i>
												</span>
											</td>
										</tr>
									</c:forEach>
								</tbody>
								<tfoot></tfoot>
							</table>
						</div>

						<%--分页--%>
						<c:choose>
							<c:when test="${count > 5}">
								<div class="page-link-content" style="float: right;margin-left: 30px;">
									<ul class="pagination pagination-sm">
										<li><a  id="prev" class="${pageInfo.prePage}" href="/Management/exam/exam-list?pageNum=${pageInfo.prePage}">上一页</a> </li>
										<c:forEach items="${pageInfo.navigatepageNums}" var="page">
											<li><a  href="/Management/exam/exam-list?pageNum=${page}">${page}</a></li>
										</c:forEach>
										<li><a id="next" class="${pageInfo.nextPage}" href="/Management/exam/exam-list?pageNum=${pageInfo.nextPage}">下一页</a></li>
									</ul>
								</div>
							</c:when>
							<c:otherwise></c:otherwise>
						</c:choose>

					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="modal fade" id="link-user-modal" tabindex="-1"
		role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">&times;</button>
					<h6 class="modal-title" id="myModalLabel">关联用户</h6>
				</div>
				<div class="modal-body">
					<form id="link-user-form">
						<div class="form-line form-user-id" style="display: block;">
							<span class="form-label"><span class="warning-label"></span>用户ID：</span>
							<input type="text" class="df-input-narrow" id="name-add-link">
							<span class="form-message"></span> <br>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">
						关闭窗口</button>
					<button id="link-user-btn" type="button" class="btn btn-primary">
						确定添加</button>
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
	<script type="text/javascript" src="/Management/static/resources/js/all.js"></script>
	<!-- Bootstrap JS -->
	<script type="text/javascript"
		src="/Management/static/resources/bootstrap/js/bootstrap.min.js"></script>

	<script type="text/javascript" src="/Management/static/resources/js/exam-list.js"></script>
	<script type="text/javascript">


		$(function(){

			$(".link-user-r-btn").click(function(){
				$("#link-user-btn").data("id",$(this).data("id"));
				$("#link-user-modal").modal({
					backdrop : true,
					keyboard : true
				});
			});

		});
	</script>
</body>
</html>