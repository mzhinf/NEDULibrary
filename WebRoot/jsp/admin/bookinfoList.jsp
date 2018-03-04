<%@page import="nedu.edu.library.entity.BookInfo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="nedu.edu.library.dao.impl.BookInfoImpl"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script type="text/javascript" src="js/check.js"></script>
<title>bookinfoList</title>
</head>
<body>
	<%
		int pageNum = (int) request.getAttribute("pageNum");
		int pageNow = (int) request.getAttribute("pageNow");
		String str = (String) request.getAttribute("str");
		String category = (String) request.getAttribute("getCategory");
	%>
	<div class="booklist-list">
		<ol>
			<c:forEach var="bookList" items="${requestScope.bookList}" varStatus="status">
			<li>
				<h3>${status.index + 1 + (pageNow - 1) * 5}. ${bookList.title}</h3>
				<p>
					作者:${bookList.author}
				</p>
				<p>
					出版社:${bookList.publishing}
				</p>
			</li>
			</c:forEach>
		</ol>
	</div>

	<div class="booklist-page">
		<a id="firstPage" href="ManageBookInfoServlet?getCategory=<%=category %>&str=<%=str %>&pageNow=1">首页</a>
		<a id="prevPage" href="ManageBookInfoServlet?getCategory=<%=category %>&str=<%=str %>&pageNow=<%=pageNow-1 %>" onclick="return prevCheck(<%=pageNow %>)">上一页</a>
		<%
			for(int i=pageNow;i<=pageNow+3&&i<=pageNum;i++){
		%>
			<a href="ManageBookInfoServlet?getCategory=<%=category %>&str=<%=str %>&pageNow=<%=i %>"><%=i %></a>
		<%
			}
		%>
		<a id="nextPage" href="ManageBookInfoServlet?getCategory=<%=category %>&str=<%=str %>&pageNow=<%=pageNow+1 %>" onclick="return nextCheck(<%=pageNow %>,<%=pageNum %>)">下一页</a>
		<a id="endPage" href="ManageBookInfoServlet?getCategory=<%=category %>&str=<%=str %>&pageNow=<%= pageNum %>">尾页</a>
	</div>
</body>
</html>
