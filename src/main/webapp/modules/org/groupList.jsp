<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String parentId = request.getParameter("parentId");
%>

<html>
<head>
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
		var parentId = "<%=parentId%>";
	</script>
	
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/icon.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/easyui/js/easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/modules/org/js/groupList.js"></script>
</head>

<body class="ovreflow1" style="width:100%;height:100%;">
	<table id="gridtable"></table>    
	
	<div id="createGroupWindow" class="easyui-window" closed="true" style="display: none;">
		<iframe id="createGroupFrame" scrolling="auto" frameborder="0" height="100%" width="100%" 
			src="${pageContext.request.contextPath}/modules/org/groupCreate.jsp?parentId=<%=parentId %>">
		</iframe>
	</div>
	
	<div id="updateGroupWindow" class="easyui-window" closed="true" style="display: none;">
		<iframe id="updateGroupFrame" scrolling="auto" frameborder="0" height="100%" width="100%" src="">
		</iframe>
	</div>
</body>
</html>