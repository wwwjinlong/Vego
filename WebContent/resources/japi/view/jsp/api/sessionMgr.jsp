<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="stylesheet" type="text/css"
	href="${contextName}/resources/japi/static/mycss/codelook.css" />

<script src="${contextName}/resources/jquery/1.10.2/jquery.min.js"></script>
<script src="${contextName}/resources/japi/static/codejs/codetools.js"></script>
<script>
	'use strict';
	$(document).ready(function() {

		//format json.
		//if text(),no value returned.
		fmtReqBody();
		
		//bind event.
		$("#btnFormat").click(fmtReqBody);
		bindClearText();
		
		//show cookie ttkn2 sessionID.
		var sessID = getCookie(CKNAME);
		if ( sessID != "" )
		{
			$("#sessID").val(sessID);
		}
		
		//bind click event.
		$("#btnGoTest").click(function()
		{
			//clear
			$("#retBody").val("loading data , please wait...");
			
			//post request.
			setSessionCookie();
			
			var reqData = $("#reqBody").val();
			var reqUrl = $("select[name='reqUrl'] option:selected").val();//选中项的值
			// alert("reqUrl:"+reqUrl+"\nreqData:"+reqData)
			
			//bind error event
			$(document).ajaxError(callError);
			$.post(reqUrl,reqData,callSuccess,"json");
		});

/* 		$("#gotest").click(function() {
			$.ajax({
				url : "${contextName}/${returnResult.retData.refUrl}",
				async : true,
				success : function(result) {
					$("#retBody").html(fmtJsonString(result));
				}
			});
		}); */
	}); // ready
</script>
<title>测试会话管理</title>
</head>

<body>
	<div class="mainlayout" >
		<table class="mainGrid">
			<tr>
				<td colspan="2" ><a href="${contextName}/api/module/list.do?moduleCode=${returnResult.retData.moduleCode}">接口列表</a></td>
			</tr>
			<tr>
				<td><span class="msgType">操作类型</span></td>
				<td>
					<%-- <input id="reqUrl" value="${contextName}/${returnResult.retData.refUrl}" style="width:400px"></input> --%>
					<select style="width:400px" name="reqUrl" >
						<option value="${contextName}/test/user/query.do">客户信息 查询</option>
						<option value="${contextName}/test/user/add.do">客户信息 新增</option>
						<option value="${contextName}/test/user/add.do">客户信息 修改</option>
						<option value="${contextName}/test/user/load.do">客户信息 模板</option>
						<option value="${contextName}/test/head.do">报文请求 分析</option>
					</select>
				</td>
			</tr>
			<tr>
				<td><span class="msgType">会话ID</span></td>
				<td><input id="sessID" value="" style="width:400px" placeholder="新增会话 则无需填写"></input></td>
			</tr>
			<tr>
				<td colspan="2" >
					<div>
						<span class="msgType">请求报文</span><br />
						<textarea id="reqBody" placeholder="暂无请求数据" >${returnResult.retData.beanJson}</textarea>
						
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right" >
					<button id="btnClear">Clear</button>
					<span>&nbsp;</span>
					<button id="btnFormat">Format</button>
					<span>&nbsp;|&nbsp;</span>
					<button id="btnGoTest">Go Go</button>
				</td>
			</tr>
			<tr>
				<td colspan="2" >
					<div>
						<span class="msgType">返回报文</span><br />
						<textarea id="retBody" placeholder="暂无返回数据" ></textarea>
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>
</html>