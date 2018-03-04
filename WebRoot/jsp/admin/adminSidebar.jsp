<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="admin-content-left">
	<div class="side-box">
		<div class="side-box-header">
			馆藏管理
		</div>
		<div class="side-box-content">
			<a href="bookinfoSearch.jsp">⊙书目检索</a>
		</div>
		<div class="side-box-content">
			<a href="bookinfoCreate.jsp">⊙新书入库</a>
		</div>
		<div class="side-box-content">
			<a href="<%=request.getContextPath() %>/AdviseInfoServlet?u_id=1&way=1&pageNow=1">⊙查看建议</a>
		</div>
		<div class="side-box-content">
			<a href="<%=request.getContextPath() %>/UpdateDatabaseServlet">⊙更新数据</a>
		</div>
		<div class="side-box-bottom">
		</div>
	</div>
</div>