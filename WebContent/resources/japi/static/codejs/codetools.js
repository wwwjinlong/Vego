/**
 *  public js function.
 */
'use strict';
var CKNAME="ttkn2";

function fmtJsonString(jsonStr)
{
	var jsObj = JSON.parse(jsonStr);
	var jstStr = JSON.stringify(jsObj, null, 4);

	return jstStr;
}

function getCookie(cname){
	var name = cname + "=";
	var ca = document.cookie.split(';');
	for(var i=0; i<ca.length; i++) {
		var c = ca[i].trim();
		if (c.indexOf(name)==0) { return c.substring(name.length,c.length); }
	}
	return "";
}

function delCookie(cname){
	document.cookie = cname+"=;Path=/;expires=Thu, 01 Jan 1970 00:00:01 GMT";
}

var fmtReqBody = function()
{
	//if text(),no value returned.
	var jsonText = $("#reqBody").val();
	//alert("fmtReqBody");
	
	if ( jsonText !== "" )
	{
		$("#reqBody").val(fmtJsonString(jsonText));
	}
}

function testF()
{
		alert("call testF");
}

function bindClearText()
{
	$("#btnClear").click(function(){
		//alert("bindClearText");
		$("#reqBody").val("");
		$("#retBody").val("");
		});
}

function setSessionCookie()
{
	var ckSession = $("#sessID").val();
	if ( ckSession == "" )
	{
		//maybe the explorer hold the cookie but user clear the html-text.
		delCookie(CKNAME);
	}
	else
	{
		//must add Path.
		document.cookie = CKNAME+"="+ckSession+";Path=/";
	}
}

function callSuccess (data,textStatus,jqXHR )
{
	//alert("callSuccess"+textStatus);
	$("#retBody").val(JSON.stringify(data, null, 4));
}

function saveHisData()
{
	
}

function callError(event,xhr,options,exc)
{
	var errHD = "An error occured!\n";
	var errMsg = xhr.status+":"+xhr.statusText+"\n";
	var errTip = "type F12 for detail";
	$("#retBody").val(errHD+errMsg+errTip);
}