<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link rel="stylesheet" type="text/css" href="${contextName}/resources/japi/static/mycss/codelook.css" />
<head>
<meta charset="utf-8">
<title>模块列表</title>
</head>
<% int pseqno=0; %>
<body id="main_body">
	<div class="beanTheme">
		<span class="msgType">模块列表 MDL-list</span>
	</div>
	<div id="main_table">
		<table border="1">
			<tr>
				<th>序号</th>
				<th>模块名称</th>
				<th>模块代码</th>
				<th>接口列表</th>
				<th>开发者</th>
				<th>发布日期</th>
			</tr>
			<c:if test="${returnResult.retData != null }">
				<c:forEach var="apiItem" items="${returnResult.retData}" varStatus="status">
				<c:if test="${apiItem != null }">
					<tr>
						<td><%=++pseqno %></td>
						<td>${apiItem.mdlDesc}</td>
						<td>${apiItem.mdlCode}</td>
						<td><a href="${contextName}/api/module/list.do?moduleCode=${apiItem.mdlCode}">port-list</a></td>
						<td>${apiItem.developer}</td>
						<td>${apiItem.relDate}</td>
					</tr>
				</c:if>
				</c:forEach>
			</c:if>
		</table>
	</div>
</body>
</html>
