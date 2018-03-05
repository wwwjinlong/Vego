<%@ page language="java" contentType="text/html; charset=UTF-8"
	isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link rel="stylesheet" type="text/css" href="${contextName}/resources/japi/static/mycss/code_list.css" />
<head>
<meta charset="utf-8">
<title>${returnResult.retData.detaiInfo.beanDesc}</title>
</head>

<body>
	<div id="main_table">
		<table border="1">
			<tr>
				<c:if test="${returnResult.retData.detaiInfo.beanName != null}">
					<td><span class="msgType">名称</span></td>
					<td>${returnResult.retData.detaiInfo.beanName}</td>
				</c:if>
				<c:if test="${returnResult.retData.detaiInfo.gdate != null}">
					<td><span class="msgType">更新时间</span></td>
					<td>${returnResult.retData.detaiInfo.gdate}</td>
				</c:if>
				<c:if test="${returnResult.retData.detaiInfo.beanDesc != null}">
					<td><span class="msgType">描述</span></td>
					<td>${returnResult.retData.detaiInfo.beanDesc}</td>
				</c:if>
				<c:if test="${returnResult.retData.refUrl != null}">
					<td><span class="msgType">URL</span></td>
					<td>${returnResult.retData.refUrl}</td>
				</c:if>
			</tr>
		</table>
		<div class="beanTheme">
			<span class="msgType">1. Java&nbsp;Bean接口代码</span>
		</div>
		<table border="1">
			<tr>
				<th align="left" >请求bean名称</th>
			</tr>
			<c:forEach var="item" items="${returnResult.retData.reqList}" varStatus="status">
				<tr>
					<td>
						<a	href="${contextName}/api/java/viewcode.do?moduleCode=${returnResult.retData.moduleCode}&serviceCode=${returnResult.retData.serviceCode}&beanName=${item.beanName}">${item.beanName}</a>
						<span>&nbsp;&nbsp;|</span>
						<a href="${contextName}/api/java/download.do?moduleCode=${returnResult.retData.moduleCode}&serviceCode=${returnResult.retData.serviceCode}&beanName=${item.beanName}">download</a>
					</td>
				</tr>
			</c:forEach>
			<tr>
				<td align="left" >&nbsp;</td>
			</tr>
			<tr>
				<th align="left" >响应bean名称</th>
			</tr>
			<c:forEach var="item" items="${returnResult.retData.rspList}" varStatus="status">
				<tr>
					<td>
						<a	href="${contextName}/api/java/viewcode.do?moduleCode=${returnResult.retData.moduleCode}&serviceCode=${returnResult.retData.serviceCode}&beanName=${item.beanName}">${item.beanName}</a>
						<span>&nbsp;&nbsp;|</span>
						<a href="${contextName}/api/java/download.do?moduleCode=${returnResult.retData.moduleCode}&serviceCode=${returnResult.retData.serviceCode}&beanName=${item.beanName}">download</a>
					</td>
				</tr>
			</c:forEach>
		</table>
		<div class="beanTheme">
			<span class="msgType" width="65%" >2. iOS&nbsp;Bean接口代码</span>
		</div>
		<table border="1">
			<tr>
				<th align="left" >请求bean名称</th>
			</tr>
			<c:forEach var="item" items="${returnResult.retData.reqList}" varStatus="status">
				<tr>
					<td>
						<a href="${contextName}/api/ios/viewcode.do?moduleCode=${returnResult.retData.moduleCode}&serviceCode=${returnResult.retData.serviceCode}&beanName=${item.beanName}">${item.beanName}</a>
						<span>&nbsp;&nbsp;|</span>
						<a href="${contextName}/api/ios/download.do?moduleCode=${returnResult.retData.moduleCode}&serviceCode=${returnResult.retData.serviceCode}&beanName=${item.beanName}">download</a>
					</td>
				</tr>
			</c:forEach>
			<tr><td align="left" >&nbsp;</td></tr>
			<tr>
				<th align="left" >响应bean名称</th>
			</tr>
			<c:forEach var="item" items="${returnResult.retData.rspList}" varStatus="status">
				<tr>
					<td>
						<a href="${contextName}/api/ios/viewcode.do?moduleCode=${returnResult.retData.moduleCode}&serviceCode=${returnResult.retData.serviceCode}&beanName=${item.beanName}">${item.beanName}</a>
						<span>&nbsp;&nbsp;|</span>
						<a href="${contextName}/api/ios/download.do?moduleCode=${returnResult.retData.moduleCode}&serviceCode=${returnResult.retData.serviceCode}&beanName=${item.beanName}">download</a>
						</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
