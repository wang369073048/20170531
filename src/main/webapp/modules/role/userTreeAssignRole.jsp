<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String contextpath = request.getContextPath();
	String roleId = request.getParameter("roleId");
%>

<html>
<head>
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
		var roleId = "<%=roleId%>";
	</script>
	
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jquery/zTree/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/easyui/js/easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/zTree/js/ztree.core-3.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/zTree/js/ztree.excheck-3.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/modules/role/js/userTreeAssignRole.js"></script>
	
	<script type="text/javascript">
		var setting = {
			check: {
				enable: true,
				chkboxType: { "Y" : "", "N" : "" }
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		
		var data = ${jsonData};
		var zNodes = data;
	
		$(document).ready(function(){
			$.fn.zTree.init($("#userTree"), setting, zNodes);
		});
	</script>
</head>

<body>
	<ul id="userTree" class="ztree"></ul>
	
	<div>
		<a href="javascript:assignRoleUser();" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
		<a href="javascript:cancel();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
	</div>
</body>
</html>
