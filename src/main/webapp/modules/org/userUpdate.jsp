<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String groupId = request.getParameter("groupId");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<html>
<head>
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>
	
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/easyui.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/easyui/js/easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/modules/org/js/userUpdate.js"></script>
</head>

<body>
<form id="userForm" method="post" enctype="multipart/form-data">
	<input type="hidden" id="groupId" name="groupId" value="<%=groupId %>">
	<input type="hidden" id="id" name="entity.id" value="${entity.id}">
	<input type="hidden" id="password" name="entity.password" value="${entity.password}">
	<input type="hidden" id="creator" name="entity.creator" value="${entity.creator}">
	<input type="hidden" id="createDate" name="entity.createTime" value="${entity.createTime}">
	<input type="hidden" id="userType" name="entity.userType" value="${entity.userType}">
	<input type="hidden" id="enabled" name="entity.enabled" value="${entity.enabled}">
	<input type="hidden" id="deleteMark" name="entity.deleteMark" value="${entity.deleteMark}">
	
	<table class="table1" align="center" border=1 bordercolor=#99CCFF style="border-collapse:collapse" width="90%">
		<tr height="30">
			<td colspan="2" bgcolor="#99CCFF" align="center"><font size="2"><b>修改人员</b></font></td>
		</tr>
		
		<tr height="30">
			<td width="20%" align="right">
				<font color="red">*</font>登录名称&nbsp;&nbsp;
			</td>
			<td width="80%">
				&nbsp;&nbsp;
				<input id="loginName" name="entity.loginName" value="${entity.loginName}" 
					class="easyui-validatebox" style="width: 75%" maxlength="20"
					required="true" missingMessage="不能为空!" validType="userName" />
			</td>
		</tr>
		
		<tr height="30">
			<td width="20%" align="right">
				<font color="red">*</font>用户名称&nbsp;&nbsp;
			</td>
			<td width="80%">
				&nbsp;&nbsp;
				<input id="userName" name="entity.userName" value="${entity.userName}" 
					class="easyui-validatebox" style="width: 75%" maxlength="20"
					required="true" missingMessage="不能为空!" validType="length[1,20]" invalidMessage="不能超过20个字符!" />
			</td>
		</tr>
		
		<tr height="30">
			<td width="20%" align="right">
				<font color="red">*</font>登录密码&nbsp;&nbsp;
			</td>
			<td width="80%">
				&nbsp;&nbsp;
				<input id="password1" name="password1" class="easyui-validatebox" 
					style="width: 75%" type="password" maxlength="20"
					validType="length[4,20]" invalidMessage="密码应是4-20位!" />
			</td>
		</tr>
		
		<tr height="30">
			<td width="20%" align="right">
				<font color="red">*</font>确认密码&nbsp;&nbsp;
			</td>
			<td width="80%">
				&nbsp;&nbsp;
				<input id="password2" name="password2" class="easyui-validatebox" 
					style="width: 80%" type="password" maxlength="20"
					validType="length[4,20]" invalidMessage="密码应是4-20位!" />
			</td>
		</tr>
	</table>
	
	<table width="100%" align="center">
		<tr height="50">
			<td align="center">
				<a href="javascript:update();" class="easyui-linkbutton" iconCls="icon-ok">确定</a>
				<a href="javascript:cancel();" class="easyui-linkbutton" iconCls="icon-cancel">取消</a>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>