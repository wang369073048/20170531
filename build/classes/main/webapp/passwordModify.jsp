<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String contextpath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextpath+"/";
String parentId = request.getParameter("parentId");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

	<script type="text/javascript">
		var path = "<%=contextpath%>";
	</script>
	<link rel="stylesheet" type="text/css" href="<%=contextpath%>/jquery/easyui/css/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=contextpath%>/jquery/easyui/css/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="<%=contextpath%>/css/default.css">
	
	<script type="text/javascript" src="<%=contextpath%>/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="<%=contextpath%>/jquery/easyui/js/easyui.min.js"></script>
	<script type="text/javascript" src="<%=contextpath%>/js/passwordModify.js"></script>

</head>

<body style="overflow: hidden;">
	<form id="createSysMenuForm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="parentId" name="entity.parentId" value="<%=parentId %>" />
	
	<table class="table1" align="center" border=1 bordercolor=#99CCFF style="border-collapse:collapse">
	
		<tr height="30">
			<td colspan="6" bgcolor="#99CCFF" align="center"><font size="2"><b>修改密码</b></font></td>
		</tr>
		
		<tr height="30">
			<td width="20%" align="right"><font color="red">*</font>原密码&nbsp;&nbsp;</td>
			<td width="80%">
				&nbsp;&nbsp;
				<input id="formerPassword" class="easyui-validatebox" 
					style="width: 80%" required="true" missingMessage="不能为空!" type="password"/>
			</td>
		</tr>
		
		<tr height="30">
			<td align="right"><font color="red">*</font>新密码&nbsp;&nbsp;</td>
			<td >
				&nbsp;&nbsp;
				<input id="newPassword" class="easyui-validatebox" 
					style="width: 80%" required="true" missingMessage="不能为空!" type="password"/>
			</td>
		</tr>
		
		<tr height="30">
			<td align="right"><font color="red">*</font>重复新密码&nbsp;&nbsp;</td>
			<td >
				&nbsp;&nbsp;
				<input id="repeatPassword" class="easyui-validatebox" 
					style="width: 80%" required="true" missingMessage="不能为空!" type="password"/>
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
