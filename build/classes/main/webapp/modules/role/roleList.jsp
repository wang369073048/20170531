<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/icon.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/easyui/js/easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/modules/role/js/roleList.js"></script>

	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>
</head>

<body class="ovreflow1" style="width:100%;height:100%;">
	<table id="gridtable"></table>
	
	<div id="createRoleWindow" class="easyui-window" closed="true" style="display: none;">
		<iframe id="createRoleframe" scrolling="auto" frameborder="0" height="100%" width="100%" 
			src="${pageContext.request.contextPath}/modules/role/roleCreate.jsp">
		</iframe>
	</div>
	
	<div id="updateRoleWindow" class="easyui-window" closed="true" style="display: none;">
		<iframe id="updateRoleframe" scrolling="auto" frameborder="0" height="100%" width="100%" src="">
		</iframe>
	</div>
	
	<div id="assignUserWindow" class="easyui-window" closed="true" style="display: none;">
		<iframe id="assignUserframe" scrolling="auto" frameborder="0" height="100%" width="100%" src="">
		</iframe>
	</div>
	
	<div id="assignMenuWindow" class="easyui-window" closed="true" style="display: none;">
		<iframe id="assignMenuframe" scrolling="auto" frameborder="0" height="100%" width="100%" src="">
		</iframe>
	</div>
</body>
</html>