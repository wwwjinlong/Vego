<%@ page language="java" contentType="text/html; charset=UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<link rel="stylesheet" type="text/css" href="${contextName}/resources/japi/static/mycss/codelook.css" />
<head>
<meta charset="utf-8">
<!-- <style type="text/css">

</style> -->
	<title>${returnResult.retData.detaiInfo.beanDesc}</title>
</head>

<body id="main_body">
	<div id="main_table">
		<table border="1">
			<tr>
				<c:if test="${returnResult.retData.detaiInfo.beanName != null}">
					<td><span class="msgType">名称</span></td>
					<td>${returnResult.retData.detaiInfo.beanName}</td>
				</c:if>
				<c:if test="${returnResult.retData.detaiInfo.gdate != null}">
					<td><span class="msgType">时间</span></td>
					<td>${returnResult.retData.detaiInfo.gdate}</td>
				</c:if>
			</tr>
			<tr>
				<c:if test="${returnResult.retData.detaiInfo.beanDesc != null}">
					<td><span class="msgType">描述</span></td>
					<td>${returnResult.retData.detaiInfo.beanDesc}</td>
				</c:if>
				<c:if test="${returnResult.retData.refUrl != null}">
					<td><span class="msgType">URL</span></td>
					<td>${returnResult.retData.refUrl}</td>
				</c:if>
			</tr>
			<tr>
				<td><span class="msgType">开发者</span></td>
				<td>${returnResult.retData.developer}</td>
				<td><span class="msgType">代码</span></td>
				<td><a href="${contextName}/api/code/list.do?moduleCode=${returnResult.retData.moduleCode}&serviceCode=${returnResult.retData.serviceCode}">QuickCode</a></td>
			</tr>
			<tr>
				<td><span class="msgType">版本</span></td>
				<td>${returnResult.retData.version}</td>
				<td><span class="msgType">变更简述</span></td>
				<td>${returnResult.retData.change}</td>
			</tr>
			<%-- 			<c:if test="${apiEntity.reqList != null }">
				<tr>
				<td><span class="msgType">JavaBean</span></td>
				<td>
				<c:forEach var="beanItem" items="${apiEntity.beanList}"	varStatus="status">
					<a href="${hostURL}/api0/autocode/${beanItem.clazzName}.java?fullpath=${beanItem.fullPath}">${beanItem.clazzName}.java</a><span>&nbsp;&nbsp;</span>
				</c:forEach>
				</td>
				<tr>
				<td><span class="msgType">AppleBean</span></td>
				<td>
				<c:forEach var="beanItem" items="${apiEntity.beanList}"	varStatus="status">
					<a href="${hostURL}/api0/autocode/${beanItem.clazzName}.h?fullpath=${beanItem.fullPath}">${beanItem.clazzName}.h</a><span>&nbsp;&nbsp;</span>
				</c:forEach>
				</td>
				</tr>
			</c:if> --%>
		</table>
		
		<c:if test="${returnResult.retData.remark != null}">
			<div class="beanTheme"><span class="msgType">调用说明:</span><p class="remark">&nbsp;${returnResult.retData.remark}</p></div>
		</c:if>
		
		<div class="beanTheme">
			<span class="msgType">请求报文:</span>
		</div>
		<table border="1">
			<tr>
				<th>字段名称</th>
				<th>字段类型</th>
				<th>是否必填</th>
				<th>字段中文</th>
				<th>字段说明</th>
			</tr>
			<c:forEach var="item" items="${returnResult.retData.detaiInfo.reqList}" varStatus="status">
				<tr>
					<td>${item.name}</td>
					<td>${item.type}&nbsp;${item.length}</td>
					<c:if test="${item.required == true}">
						<td><span>YES</span></td>
					</c:if>
					<c:if test="${item.required == false}">
						<td><span>NO</span></td>
					</c:if>
					<td>${item.chName}</td>
					<td>${item.desc}</td>
				</tr>
				<c:if test="${item.type == 'LIST'}">
					<tr>
						<td>列表开始</td>
					</tr>
					<c:forEach var="item1" items="${item.fieldList}" varStatus="status">
						<tr>
							<td>${item1.name}</td>
							<td>${item1.type}&nbsp;${item1.length}</td>
							<c:if test="${item1.required == true}">
								<td><span>YES</span></td>
							</c:if>
							<c:if test="${item1.required == false}">
								<td>&nbsp;</td>
							</c:if>
							<td>${item1.chName}</td>
							<td>${item.desc}</td>
						</tr>
					</c:forEach>
					<tr>
						<td>列表结束</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>
		<div class="beanTheme">
			<span class="msgType">返回报文:</span>
		</div>
		<table border="1">
			<tr>
				<th>字段名称</th>
				<th>字段类型</th>
				<th>是否必填</th>
				<th>字段中文</th>
				<th>字段说明</th>
			</tr>
			<c:forEach var="item" items="${returnResult.retData.detaiInfo.rspList}" varStatus="status">
				<tr>
					<td>${item.name}</td>
					<td>${item.type}&nbsp;${item.length}</td>
					<c:if test="${item.required == true}">
						<td><span>YES</span></td>
					</c:if>
					<c:if test="${item.required == false}">
						<td>&nbsp;</td>
					</c:if>
					<td>${item.chName}</td>
					<td>${item.desc}</td>
				</tr>
				<c:if test="${item.type == 'LIST'}">
					<tr>
						<td>列表开始</td>
					</tr>
					<c:forEach var="item1" items="${item.fieldList}" varStatus="status">
						<tr>
							<td>${item1.name}</td>
							<td>${item1.type}&nbsp;${item1.length}</td>
							<c:if test="${item1.required == true}">
								<td><span>YES</span></td>
							</c:if>
							<c:if test="${item1.required == false}">
								<td>&nbsp;</td>
							</c:if>
							<td>${item1.chName}</td>
							<td>${item.desc}</td>
						</tr>
					</c:forEach>
					<tr>
						<td>列表结束</td>
					</tr>
				</c:if>
			</c:forEach>
		</table>

		<%-- 		<div class="reqType">
			<span class="msgType">更新记录</span>
		</div>
		<table>
			<tr>
				<th>变更类型</th>
				<th>变更内容</th>
			</tr>
			<c:if test="${apiEntity.reqBeanInfo.beanUpdate != null}">
				<c:forEach var="updateItem"
					items="${apiEntity.reqBeanInfo.beanUpdate}" varStatus="status">
					<tr>
						<td>_request</td>
						<td>${updateItem}</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${apiEntity.rspBeanInfo.beanUpdate != null}">
				<c:forEach var="updateItem"
					items="${apiEntity.rspBeanInfo.beanUpdate}" varStatus="status">
					<tr>
						<td>RESPONSE</td>
						<td>${updateItem}</td>
					</tr>
				</c:forEach>
			</c:if>
		</table> --%>
	</div>
</body>
</html>
