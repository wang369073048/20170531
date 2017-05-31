<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/icon.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/easyui/js/easyui.min.js"></script>

	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>
	
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/modules/log/js/logList.js"></script>
</head>

<body class="ovreflow1" style="width:100%;height:100%;">
	<table id="gridtable"></table>
	
	<div id="search" style="display: none;">
		关键字<input id="keyword" name="keyword" style="width: 200px;height: 25px;" onKeyDown="checkKeyDown(event);"/>
		起始日期<input id="beginDate" name="beginDate" class="easyui-datebox" style="width: 150px;height: 25px;"/>
		截至日期<input id="endDate" name="endDate" class="easyui-datebox" style="width: 150px;height: 25px;"/>
		<a href="javascript:search();" class="easyui-linkbutton" plain="true" iconCls="icon-search">检索</a>
		<a href="javascript:clear();" class="easyui-linkbutton" plain="true" iconCls="icon-cut">清空</a>
	</div>      
</body>
</html>