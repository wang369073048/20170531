<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String contextpath = request.getContextPath();
%>

<html>
<head>
	<script type="text/javascript" src="<%=contextpath %>/js/echarts-all.js"></script>
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>
</head>

<body class="ovreflow1" style="width:100%;height:100%;">
	<div id="main" style="height:500px;">
		<table >
	    	<tr>
	    		<td>题库题目总数 ：</td>
	    		<td>${resultList[0]}</td>
	    	</tr>
	    </table>
    </div>
</body>
</html>