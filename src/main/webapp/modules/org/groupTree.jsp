<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>
	
	<link href="${pageContext.request.contextPath}/css/mainstyle.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jquery/zTree/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/easyui/js/easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/zTree/js/ztree.core-3.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/modules/org/js/groupTree.js"></script>
</head>

<body>
<div class="main">
	<div class="main_bigbox1">
	    <div class="main_leftTree">
	    	<ul id="groupTree" class="ztree"></ul>
	    </div>
	    
		<div class="main_rightMenu">
			<iframe id="gridframe" scrolling="auto" marginheight="0"  marginwidth="0" frameborder="0" height="100%" width="100%" src=""></iframe>
		</div>
	</div>
</div>
</body>
</html>