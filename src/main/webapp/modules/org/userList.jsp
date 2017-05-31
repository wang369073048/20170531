<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String groupId = request.getParameter("groupId");
%>

<html>
<head>
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
		var groupId = "<%=groupId %>";
	</script>
	
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/icon.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/easyui/js/easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/modules/org/js/userList.js"></script>
</head>

<body class="ovreflow1" style="width:100%;height:100%;">
	<table id="gridtable"></table>
	
	<div id="separator" style="display: none;" class="datagrid-btn-separator"></div>
	<div id="search" style="display: none;">
		<input id="keyword" name="keyword" style="width: 200px;height: 25px;" onKeyDown="checkKeyDown(event);"/>
		<a href="javascript:search();" class="easyui-linkbutton" plain="true" iconCls="icon-search">检索</a>
	</div>      
	
	<div id="createUserWindow" class="easyui-window" closed="true" style="display: none;">
		<iframe id="createUserframe" scrolling="auto" frameborder="0" height="100%" width="100%" 
			src="${pageContext.request.contextPath}/modules/org/userCreate.jsp?groupId=<%=groupId %>">
		</iframe>
	</div>
	
	<div id="updateUserWindow" class="easyui-window" closed="true" style="display: none;">
		<iframe id="updateUserframe" scrolling="auto" frameborder="0" height="100%" width="100%" src="">
		</iframe>
	</div>
</body>
</html>