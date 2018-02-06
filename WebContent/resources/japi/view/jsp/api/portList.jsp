<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link rel="stylesheet" type="text/css" href="${contextName}/resources/japi/static/mycss/codelook.css" />
<head>
<meta charset="utf-8">
<title>${returnResult.retData.moduleName}</title>
</head>
<% int pseqno=0; %>
<body id="main_body">
	<div class="beanTheme">
		<span class="msgType">${returnResult.retData.moduleName}</span>
		<span class="remark">by&nbsp;${returnResult.retData.developer}</span>
	</div>
	<div id="main_table">
		<div>
		<table border="1">
			<tr>
				<th>序号</th>
				<th>接口名称</th>
				<th>接口地址</th>
				<th>接口版本</th>
				<th>更新时间</th>
				<th>更新内容</th>
			</tr>
			<c:if test="${returnResult.retData.services != null }">
				<c:forEach var="apiItem" items="${returnResult.retData.services}" varStatus="status">
					<tr>
						<td><%=++pseqno %></td>
						<td><a href="${contextName}/api/service/detail.do?moduleCode=${returnResult.retData.moduleCode}&serviceCode=${apiItem.serviceCode}&refUrl=${apiItem.refUrl}">${apiItem.chName}</a></td>
						<td>${apiItem.refUrl}</td>
						<td>${apiItem.version}</td>
						<td>${apiItem.gtime}</td>
						<td>${apiItem.change}</td>
					</tr>
				</c:forEach>
			</c:if>
			<tr>
				<td colspan="5" align="right" style="border:none;"><a href="${contextName}/api/code/downloadAll.do?moduleCode=${returnResult.retData.moduleCode}"><span class="remark">download code</span></a></td>
				<td colspan="1" align="right" style="border:none;"><a href="${contextName}/api/excel/download.do?moduleCode=${returnResult.retData.moduleCode}"><span class="remark">download EXCEL</span></a></td>
			</tr>
		</table>
		</div>
		<div id="downhint"></div>
	</div>
</body>
</html>
