<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${contextName}/resources/japi/static/mycss/codelook.css" />
<title>${returnResult.retData.beanName}'JSON</title>
</head>
<body>
<p>
<textarea id="jsonArea">${returnResult.retData.beanJson}</textarea>
<div id="json"></div>
</p>
</body>
</html>