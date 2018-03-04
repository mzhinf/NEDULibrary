<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<title>adviseinfoList</title>
</head>
<body>
	<%
		int pageNum = (int) request.getAttribute("pageNum");
		int pageNow = (int) request.getAttribute("pageNow");
		int way = (int) request.getAttribute("way");
	%>
	<div class="advise-category">
		<a href="AdviseInfoServlet?u_id=1&way=1&pageNow=1">全部建议</a>
		<a href="AdviseInfoServlet?u_id=1&way=2&pageNow=1">未读建议</a>
		<a href="AdviseInfoServlet?u_id=1&way=3&pageNow=1">已阅建议</a>
	</div>
	<div class="adviselist-list">
		<ol>
			<c:forEach var="adviseList" items="${requestScope.adviseList}" varStatus="status">
			<li>
				<h3>${status.index + 1 + (pageNow - 1) * 2}. u_id: ${adviseList.u_id}</h3>
				<div class="adviselist-listp1">
					${adviseList.advise}
				</div>
				<div class="adviselist-listp2">
					email: ${adviseList.email}
				</div>
				<div class="adviselist-check">
					<c:choose>  
						<c:when test="${adviseList.isCheck}">
							<a href="#" onclick="return false">已查看</a>
						</c:when>
						<c:otherwise>
							<a href="AdviseInfoServlet?u_id=1&way=<%=way %>&id=${adviseList.id}&pageNow=<%=pageNow %>&check=true">未查看</a>
						</c:otherwise> 
					</c:choose>
				</div>
			</li>
			</c:forEach>
		</ol>
	</div>
	
	<div class="adviselist-page">
		<a id="firstPage" href="AdviseInfoServlet?u_id=1&way=<%=way %>&pageNow=1">首页</a>
		<a id="prevPage" href="AdviseInfoServlet?u_id=1&way=<%=way %>&pageNow=<%=pageNow-1 %>">上一页</a>
		<%
			for(int i = pageNow;i <= pageNow + 3 && i <= pageNum;i++){
		%>
			<a href="AdviseInfoServlet?u_id=1&way=<%=way %>&pageNow=<%=i %>"><%=i %></a>
		<%
			}
		%>
		<a id="nextPage" href="AdviseInfoServlet?u_id=1&way=<%=way %>&pageNow=<%=pageNow+1 %>" onclick="return nextCheck(<%=pageNow %>,<%=pageNum %>)">下一页</a>
		<a id="endPage" href="AdviseInfoServlet?u_id=1&way=<%=way %>&pageNow=<%= pageNum %>">尾页</a>
	</div>
</body>
</html>