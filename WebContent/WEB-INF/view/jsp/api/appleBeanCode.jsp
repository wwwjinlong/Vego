<%@ page language="java" contentType="text/html; charset=UTF-8"
	isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>${beanName}</title>
	<script type="text/javascript" src="${hostUrl}/codeview/scripts/shCore.js"></script>
	<%-- <script type="text/javascript" src="${hostUrl}/codeview/scripts/shBrushAppleScript.js"></script> --%>
	<script type="text/javascript" src="${hostUrl}/codeview/scripts/shBrushCpp.js"></script> 
	<link type="text/css" rel="stylesheet" href="${hostUrl}/codeview/styles/shCoreDefault.css"/>
	<script type="text/javascript">SyntaxHighlighter.all();</script>
</head>

<body style="background: white; font-family: Helvetica">

<h3>${beanName}.h</h3>
<!-- <pre class="brush: applescript;"> -->
<pre class="brush: cpp;">
${beanEntity}
</pre>
</html>
