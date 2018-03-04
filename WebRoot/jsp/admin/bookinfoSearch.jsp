<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bookinfoSearch</title>
<link type="text/css" rel="stylesheet" href="../../css/common.css">
</head>
<body>
	<div class="rightbar-search">
		<form action="<%=request.getContextPath() %>/ManageBookInfoServlet" method="post">
			<select name="getCategory">
				<option value="title" selected="selected">书名</option>
				<option value="author">作者</option>
				<option value="ISBN">ISBN</option>
				<option value="subject">subject</option>
			</select>
			<input name="str" type="text">
			<button type="submit">搜索</button>
		</form>
	</div>
</body>
</html>
