<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>bookinfoCreate</title>
<link type="text/css" rel="stylesheet" href="../../css/common.css">
</head>
<body>
	<div class="rightbar-create">
		<h2>新书入库</h2>
		<form action="<%=request.getContextPath() %>/ManageBookInfoServlet" method="post">
			<table>
				<tr>
					<th>*书名:</th>
					<td><input name="title" type="text"></td>
				</tr>
				<tr>
					<th>*作者:</th>
					<td><input name="author" type="text"></td>
				</tr>
				<tr>
					<th>出版社:</th>
					<td><input name="publishing" type="text"></td>
				</tr>
				<tr>
					<th>*ISBN:</th>
					<td><input name="ISBN" type="text"></td>
				</tr>
				<tr>
					<th>学科主题:</th>
					<td><input name="subject" type="text"></td>
				</tr>
				<tr>
					<th>索书号:</th>
					<td><input name="searchNumber" type="text"></td>
				</tr>
				<tr>
					<th>*馆藏数目:</th>
					<td><input name="amount" type="text"></td>
				</tr>
				<tr>
					<th>简介:</th>
					<td><input name="summary" type="text"></td>
				</tr>
				<tr>
					<th></th>
					<td>
						<button type="submit">提交</button>
						<button type="reset">重置</button>
					</td>
				</tr>
				<tr>
					<th></th>
					<td>(带*为必填)</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>