<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link type="text/css" rel="stylesheet" href="css/common.css">
<title>resPage</title>
</head>
<body>
	<%
		int resCode = (int) request.getAttribute("resCode");
		String res = "";
		switch (resCode / 100) {
			case 1:
				if ((resCode / 10) % 10 == 1) {//a为1
					res = res + "借阅:更新成功<br/>";
				} else {
					res = res + "借阅:更新失败(没有借阅超期)<br/>";
				}
				if (resCode % 10 == 1) {
					res = res + "预约:更新成功<br/>";
				} else {
					res = res + "预约:更新失败(没有预约信息)<br/>";
				}
				break;
			case 2:
				if (resCode % 10 == 1) {
					res = res + "检索:查无此书<br/>";
				}
				break;
			default:
				break;
		}
	%>
	resCode = <%=resCode %><br/>
	<%=res %><br/>
</body>
</html>