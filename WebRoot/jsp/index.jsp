<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
<link type="text/css" rel="stylesheet" href="../css/common.css">
</head>
<body>
	<div class="all">
		<!-- 页面顶部 -->
		<div class="admin-top">
		</div>
		
		<!-- 页面中部 -->
		<div class="admin-content">
			<div class="login">
				<form action="admin/login.jsp" method="post">
					<table>
					<tr>
						<th>用户名:</th>
						<td><input name="user" type="text"></td>
					</tr>
					<tr>
						<th>密码:</th>
						<td><input name="password" type="password"></td>
					</tr>
					<tr>
						<th></th>
						<td><button type="submit">登陆</button></td>
					</tr>
				</table>
				</form>
			</div>
		</div>
		
		<!-- 页面底部 -->
		<div class="admin-bottom">
			Copyright ©2016 by 小呓的欧尼酱<br/>
			东北电力大学-图书馆
		</div>
	</div>
</body>
</html>