<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="${hostUrl}/resources/codelook.css" />
<link rel="stylesheet" href="${hostUrl}/static/jsonView/jquery.jsonview.css">
<script src="${hostUrl}/static/jsonView/jquery.jsonview.js"></script>
<script src="${hostUrl}/static/jquery/jquery.min.js"></script>
<script type="text/javascript">
var json = {"hey": "guy","anumber": 243,"anobject": {"hey": "guy","anumber": 243}};
$(function() {
  //$("#jsonArea").JSONView(json);
  // with options
  $("#json").JSONView(json, { collapsed: true });
}); 
</script>
<title>${beanName}'JSON</title>
</head>
<body>
<p>
<textarea id="jsonArea">${beanJson}</textarea>
<div id="json"></div>
</p>
</body>
</html>