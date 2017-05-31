<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="common/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	
	<link href="${pageContext.request.contextPath}/css/mainstyle.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/easyui.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/easyui/js/easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/DatePicker/WdatePicker.js"></script>
	
	<script type="text/javascript">
		function contentframe(menuUrl){
			var url = "${pageContext.request.contextPath}/"+menuUrl;
			$('#iFrame').attr('src','');
			$('#iFrame').attr('src',url);
		}
	</script>
</head>

<body>
<div class="head-menu">
	<div class="bigbox">
		<div class="second-menu"></div>
	</div>
</div>

<div class="main">
	<div class="main_bigbox">
	    <div class="main_leftMenu">
	    	<c:forEach items="${menuList}" var="menu">
	    		<div style="height: 20px; padding-left: 30px;padding-top: 10px;">
	    			<a href="javascript:contentframe('${menu.menuUrl}');">
						${menu.menuName}
					</a>
				</div>
				<div style="height:1px;line-height:1px;border-bottom:#999999 1px dotted;margin:5px 20px;"></div>
			</c:forEach>
	    </div>
	    
		<div class="main_rightMenu">
			<div id="chart-content" style="height: 100%;">
				<iframe id="iFrame" name="iFrame" scrolling="auto" marginheight="0" marginwidth="auto" frameborder="0" 
					height="100%" width="100%" align="center"></iframe>
			</div>
		</div>
	</div>
</div>
</body>
</html>