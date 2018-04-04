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
	//it works here.
	'use strict';
	
	//not worked here.
	// bindClearText();
	function saveHisData (data,textStatus,jqXHR )
	{
		var moduleCode = $("#pModuleCode").text();
		var serviceCode = $("#pServiceCode").text();
		
		//not good, but I am lazy,and however it works.
		var reqData = moduleCode+"#"+serviceCode+"#"+$("#reqBody").val();
		
		// alert("saveHisData:"+reqData);
		$.post("${contextName}/api/unit/saveData.do",reqData);
	}

	// alert("-------"+CKNAME);
	$(document).ready(function() {
		
		//format json.
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
		
		//load raw data, not history data.
		$("#btnLoadTmpl").click(function()
		{
			var reqDataUrl = "${contextName}/api/unit/loadData.do?loadType=raw&moduleCode=${returnResult.retData.moduleCode}&serviceCode=${returnResult.retData.serviceCode}";
			$.get(reqDataUrl,null,function(data,textStatus,jqXHR)
			{
				// alert("textStatus="+textStatus+",recv"+data);
				$("#reqBody").val(JSON.stringify(data, null, 4));
			},"json");
		});
		
		//bind click event.
		$("#btnGoTest").click(function()
		{
			//alert(CKNAME+"11");
			
			//clear
			$("#retBody").val("loading data , please wait...");
			
			//post request.
			setSessionCookie();
			
			var reqData = $("#reqBody").val();
			var reqUrl=$("#reqUrl").val();
			// alert("reqUrl:"+reqUrl+"\nreqData:"+reqData)
			
			// bind error event
			$(document).ajaxError(callError);
			
			// $(this).attr("k1","v1");
			// alert("button-clicked1:"+$(this).attr("k1"));
			$.post(reqUrl,reqData,[callSuccess,saveHisData],"json");
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
<title>${returnResult.retData.beanChName}'Unit Test</title>
</head>

<body>
	<div class="mainlayout" >
		<table class="mainGrid">
			<tr>
				<td><a href="${contextName}/api/module/list.do?moduleCode=${returnResult.retData.moduleCode}">接口列表</a></td>
				<td><a href="${contextName}/api/mgr/userinfo.do?moduleCode=${returnResult.retData.moduleCode}">会话管理</a></td>
			</tr>
			<tr>
				<td><span class="msgType">请求地址</span></td>
				<td><input id="reqUrl"value="${contextName}/${returnResult.retData.refUrl}" style="width:400px"></input></td>
			</tr>
			<tr>
				<td><span class="msgType">会话ID</span></td>
				<td><input id="sessID" value="" style="width:400px" ></input></td>
			</tr>
			<tr>
				<td colspan="2" >
					<div>
						<span class="msgType">请求报文</span><br />
						<textarea id="reqBody" placeholder="暂无请求数据">${returnResult.retData.beanJson}</textarea>
					</div>
				</td>
			</tr>
			<tr>
				<td align="right" colspan="2" >
					<button id="btnClear">Clear</button>
					<span>&nbsp;</span>
					<button id="btnFormat">Format</button>
					<span>&nbsp;</span>
					<button id="btnLoadTmpl">LoadRaw</button>
					<span>&nbsp;&nbsp;|&nbsp;&nbsp;</span>
					<button id="btnGoTest">Go Test</button>
				</td>
			</tr>
			<tr>
				<td colspan="2" >
					<div>
						<span class="msgType">返回报文</span><br />
						<textarea id="retBody" placeholder="暂无返回数据"></textarea>
					</div>
				</td>
			</tr>
		</table>
		<span id="pModuleCode" hidden="hidden" >${returnResult.retData.moduleCode}</span>
		<span id="pServiceCode" hidden="hidden">${returnResult.retData.serviceCode}</span>
	</div>
</body>
</html>