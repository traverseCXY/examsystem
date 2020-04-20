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
		<title>选择试题添加到试卷中--做了分页</title>
		<meta name="keywords" content="">
		<link rel="shortcut icon" href="<%=basePath%>/static/resources/images/favicon.ico" />
		<link href="/Management/static/resources/bootstrap/css/bootstrap-huan.css" rel="stylesheet">
		<link href="/Management/static/resources/font-awesome/css/font-awesome.min.css" rel="stylesheet">
		<link href="/Management/static/resources/css/style.css" rel="stylesheet">
		
		<link href="/Management/static/resources/css/exam.css" rel="stylesheet">
		<link href="/Management/static/resources/chart/morris.css" rel="stylesheet">
		<style>
			.examing-point{
				display:block;
				font-size:10px;
				margin-top:5px;
			}
			.question-name-td{
				width:300px;
			}
/* 			input[disabled]{
			  outline:1px solid red; 
			}
*/		
 		</style>
	</head>
	<body>
		<span style="display:none;" id="rule-role-val"><%=list[1]%></span>
		<!-- Navigation bar starts -->

		

		<!-- Navigation bar ends -->

		<!-- Slider starts -->

		<div>
			<!-- Slider (Flex Slider) -->

			<div class="container" style="min-height:500px;width:840px;">

				<div class="row">
					
					<div class="col-xs-12">
						
						<div class="page-content">

							<div id="question-filter">

								<dl id="question-filter-field">
									<dt>
										专业：
									</dt>
									<dd>
										<c:choose>
											<c:when test="${questionFilter.fieldId == 0 }">
												<span data-id="0" class="label label-info">全部</span>
											</c:when>
											<c:otherwise>
												<span data-id="0">全部</span>
											</c:otherwise>
										</c:choose>
										
										<c:forEach items="${fieldList}" var="field">
											<c:choose>
												<c:when test="${questionFilter.fieldId == field.fieldId }">
													<span class="label label-info" data-id="${field.fieldId}">${field.fieldName}</span>
												</c:when>
												<c:otherwise>
													<span data-id="${field.fieldId}">${field.fieldName}</span>
												</c:otherwise>
											</c:choose>	
										</c:forEach>
									</dd>
								</dl>
								<dl id="question-filter-knowledge">
									<dt>
										知识类：
									</dt>
									<dd>
										<c:choose>
											<c:when test="${questionFilter.knowledge == 0 }">
												<span data-id="0" class="label label-info">全部</span>
											</c:when>
											<c:otherwise>
												<span data-id="0">全部</span>
											</c:otherwise>
										</c:choose>
										<c:forEach items="${knowledgeList}" var="knowledge">
											<c:choose>
												<c:when test="${questionFilter.knowledge == knowledge.pointId }">
													<span data-id="${knowledge.pointId}" class="label label-info">${knowledge.pointName}</span>
												</c:when>
												<c:otherwise>
													<span data-id="${knowledge.pointId}">${knowledge.pointName}</span>
												</c:otherwise>
											</c:choose>
										</c:forEach>
									</dd>
								</dl>

								<dl id="question-filter-qt">
									<dt>
										试题类型：
									</dt>
									<dd>
										<c:choose>
											<c:when test="${questionFilter.questionType == 0 }">
												<span data-id="0" class="label label-info">全部</span>
											</c:when>
											<c:otherwise>
												<span data-id="0">全部</span>
											</c:otherwise>
										</c:choose>
										<c:forEach items="${questionTypeList}" var="questionType">
											<c:choose>
												<c:when test="${questionFilter.questionType == questionType.id }">
													<span data-id="${questionType.id}" class="label label-info">${questionType.name}</span>
												</c:when>
												<c:otherwise>
													<span data-id="${questionType.id}">${questionType.name}</span>
												</c:otherwise>
											</c:choose>
											
											
										</c:forEach>

									</dd>
								</dl>
							</div>
							<div id="question-list">
								<input id="field-id-hidden" value="${fieldId }" type="hidden">
								<input id="knowledge-hidden" value="${knowledge }" type="hidden">
								<input id="question-type-hidden" value="${questionType }" type="hidden">
								<input id="search-param-hidden" value="${searchParam }" type="hidden">
								<table class="table-striped table">
									<thead>
										<tr>
											<td></td><td>ID</td><td class="question-name-td">试题名称</td><td>试题类型</td><td>专业</td><td>知识类</td><td>创建人</td>
										</tr>
									</thead>
									<tbody id="tbody">
										
										<c:forEach items="${questionList }" var="items">
											<tr>
												<td>
												<input type="checkbox" value="${items.id }">
												</td>
												<td class="question-id">${items.id }</td>
												<td>
													<a href="<%=list[1]%>/question-preview/${items.id }" target="_blank" title="预览">${items.name }</a>
													<span class="examing-point">${items.examingPoint} </span>
												</td>
												
												<td>${items.questionTypeName }</td><td>${items.fieldName }</td><td>${items.pointName }</td><td>${items.creator }</td>
											</tr>
										</c:forEach>
										

									</tbody><tfoot></tfoot>
								</table>
							</div>

							<%--分页--%>
							<c:choose>
								<c:when test="${count > 10}">
									<div class="page-link-content" style="float: right;margin-left: 30px;">
										<ul id="pagination" class="pagination pagination-sm">
											<li><a  id="prev" class="${pageInfo.prePage}">上一页</a></li>
											<c:forEach items="${pageInfo.navigatepageNums}" var="page">
												<li>
													<a class="pageNumber" id="${page}">${page}</a>
												</li>
											</c:forEach>
											<li><a id="next" class="${pageInfo.nextPage}">下一页</a></li>
										</ul>
									</div>
								</c:when>
								<c:otherwise>

								</c:otherwise>
							</c:choose>

						</div>
					</div>
				</div>
			</div>
		</div>

		<!-- Slider Ends -->

		<!-- Javascript files -->
		<!-- jQuery -->
		<script type="text/javascript" src="/Management/static/resources/js/jquery/jquery-1.9.0.min.js"></script>
		<!-- Bootstrap JS -->
		<script type="text/javascript" src="/Management/static/resources/bootstrap/js/bootstrap.min.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/all.js"></script>
		<script type="text/javascript" src="/Management/static/resources/js/question-list4dialog.js?v=1"></script>

		<script>
			function ajax(pageNum){
				var paramurl = {
					"fieldId":$("#field-id-hidden").attr("value"),
					"knowledge":$("#knowledge-hidden").attr("value"),
					"questionType ": $("#question-type-hidden").attr("value"),
					"searchParam":$("#search-param-hidden").attr("value"),
					"pageNum":pageNum
				}
				$.ajax({
					headers : {
						'Accept' : 'application/json',
						'Content-Type' : 'application/json'
					},
					type:"POST",
					url:"/Management/question/question-list/filterdialogPage",
					data:JSON.stringify(paramurl),
					success:function (result){
						var html="";
						$.each(result.list,function(index,items){
							html+="<tr>";
							html+="	<td>";
							html+="<input type='checkbox' value='"+items.id +"'>";
							html+="</td>";
							html+="<td class='question-id'>"+items.id+"</td>";
							html+="<td>";
							html+="<a href='/question/question-preview/"+items.id+"' target='_blank' title='预览'>"+items.name +"</a>";
							html+="<span class='examing-point'>"+items.examingPoint +"</span>";
							html+="	</td>";
							html+="<td>"+items.questionTypeName +"</td><td>"+items.fieldName+"</td><td>"+items.pointName+"</td><td>"+items.creator+"</td>";
							html+="</tr>";
						});
						$("#tbody").html(html);
						var pageNumHtml="";
						pageNumHtml+="<li><a  id=\"prev\" class=\""+result.prePage+"\">上一页</a></li>";
						$.each(result.navigatepageNums,function(index,val){
							pageNumHtml+="<li><a class='pageNumber' id='"+val+"'>"+val+"</a></li>";
						})
						pageNumHtml+="<li><a  id=\"next\" class=\""+result.nextPage+"\">下一页</a></li>";
						$("#pagination").html(pageNumHtml);

						$("#prev").click(function () {
							var pageNum=$(this).attr("class");
							ajax(pageNum);
						});

						$(".pageNumber").click(function () {
							ajax($(this).attr("id"));
						});

						$("#next").click(function () {
							var pageNum=$(this).attr("class");
							ajax(pageNum);
						})

					}

				});
			}; /*end ajax function*/

			$(function () {

				$("#prev").click(function () {
					var pageNum=$(this).attr("class");
					ajax(pageNum);
				});

				$(".pageNumber").click(function(){
					var pageNum=$(this).attr("id");
					ajax(pageNum);
				})

				$("#next").click(function () {
					var pageNum=$(this).attr("class");
					ajax(pageNum);
				})
			})
		</script>

	</body>
</html>
