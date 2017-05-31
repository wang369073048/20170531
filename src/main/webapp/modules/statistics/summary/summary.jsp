<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<link href="${pageContext.request.contextPath}/css/mainstyle.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/modules/statistics/js/summary.js"></script>
	
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>
</head>

<body>
	<div class="main_summary"  style="background-color: #F2F2F2;">
		<div class="tableheader-tab">
			<ul>
				<li id="1" class="on"><a href="###">资源库总体情况</a></li>
				<li id="2"><a href="###">资源库申报建设总体情况</a></li>
				<li id="3"><a href="###">资源库申报分布情况</a></li>
			</ul>
		</div>
		<div style="height:500px;">
			<iframe id="contentFrame" name="contentFrame" scrolling="auto" marginheight="0" marginwidth="auto" frameborder="0" 
				height="100%" width="100%" align="center" src="${pageContext.request.contextPath}/report_summary/totality.action"></iframe>
		</div>
	</div>
</body>
</html>