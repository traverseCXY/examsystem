<%@ page language="java" contentType="text/html;   charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<ul class="nav navbar-nav">
	<c:forEach items="${topMenuList }" var="topMenuListItem">
		<li <c:if test="${topMenuListItem.menuId eq topMenuId}">class="active"</c:if>>
			<a href="${topMenuListItem.menuHref }"><i class="fa ${topMenuListItem.icon }"></i>${topMenuListItem.menuName }</a>
		</li>
	</c:forEach>
</ul>