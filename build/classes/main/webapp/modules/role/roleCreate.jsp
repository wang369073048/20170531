<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>

	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/easyui/js/easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/modules/role/js/roleCreate.js"></script>
</head>

<body style="overflow: hidden">
<form id="roleForm" method="post">
	<table class="table1" align="center" border=1 bordercolor=#99CCFF style="border-collapse:collapse" width="90%">
		<tr height="30">
			<td colspan="2" bgcolor="#99CCFF" align="center"><font size="2"><b>创建角色</b></font></td>
		</tr>
		
		<tr height="30">
			<td width="20%" align="right"><font color="red">*</font>角色名称&nbsp;&nbsp;</td>
			<td width="80%">
				&nbsp;&nbsp;
				<input id="roleName" name="entity.roleName" class="easyui-validatebox" style="width: 80%"
					required="true" missingMessage="不能为空!" maxlength="20"
					validType="length[1,20]" invalidMessage="不能超过20个字符!">
			</td>
		</tr>
		
		<tr height="30">
			<td width="20%" align="right"><font color="red">*</font>角色编码&nbsp;&nbsp;</td>
			<td width="80%">
				&nbsp;&nbsp;
				<input id="roleCode" name="entity.roleCode" class="easyui-validatebox" style="width: 80%"
					required="true" missingMessage="不能为空!"  maxlength="20">
			</td>
		</tr>
	</table>
	
	<table width="100%" align="center">
		<tr height="50">
			<td align="center">
				<a href="javascript:create();" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
				<a href="javascript:cancel();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
			</td>
		</tr>
	</table>
</form>
</body>
</html>