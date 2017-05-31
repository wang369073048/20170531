<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<link href="${pageContext.request.contextPath}/css/mainstyle.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/modules/statistics/js/main.js"></script>
	
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>
</head>

<body>
<div class="head-menu">
	<div class="bigbox">
		<div class="second-menu">
			<ul>
				<li id="1" class="on"><a href="###">基本报告</a></li>
				<li id="2"><a href="###">汇总报告</a></li>
			</ul>
		</div>
	</div>
</div>

<div class="main">
	<iframe id="contentFrame" name="contentFrame" scrolling="auto" marginheight="0" marginwidth="auto" frameborder="0" 
		height="100%" width="100%" align="center" src="${pageContext.request.contextPath}/report_basic/reportBasic.action"></iframe>
</div>
</body>
</html>