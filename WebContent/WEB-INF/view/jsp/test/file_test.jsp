<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<form id="form1" method="post" enctype="multipart/form-data" action="/fileboxy/file/net/upload.do"  >
	<input type="text" name="fileName">
	<br/>
	<input type="text" name="path">
	<br/>
	<input type="text" name="fileMark">
	<br/>
	<div id="files">
		<input type="file" name="yourFile" />
	</div>
	<br/>
	<input type="submit" value="submit">
	</form>
</body>
</html>