<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Library</title>
<!-- 让超链接后的页面在页面的右下部分显示 -->
<link type="text/css" rel="stylesheet" href="../../css/common.css">
<base target="rightFrame"/>
</head>
<body>
	<div class="all">
		<!-- 页面顶部 -->
		<jsp:include page="adminTop.jsp"></jsp:include>
		
		<!-- 页面中部 -->
		<div class="admin-content">
			<jsp:include page="adminSidebar.jsp"></jsp:include>
			<jsp:include page="adminRightbar.jsp"></jsp:include>
		</div>
		
		<!-- 页面底部 -->
		<jsp:include page="adminBottom.jsp"></jsp:include>
	</div>
</body>
</html>