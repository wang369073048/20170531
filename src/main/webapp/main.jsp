<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.asdc.lrm.util.UtilSession"%>
<%@ include file="common/taglibs.jsp" %>

<%
	String userName = UtilSession.getUserName();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>国家职业教育专业教学资源库运行监测平台</title>
	
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/layout.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/css/mainstyle.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";

		$(function () {
		    $("div.head_right li a").click(function () {
		        $("div.head_right li").removeClass("on");
		        $(this).parent("li").addClass("on");
		    });
		}); 
		
		function contentFrame(data,type){
			if(type == 1){
				var url = '${pageContext.request.contextPath}/secondMenu.action?parentId='+data;
				$('#contentFrame').attr('src','');
				$('#contentFrame').attr('src',url);
			}
			if(type == 2){
				var url = '${pageContext.request.contextPath}/'+data;
				$('#contentFrame').attr('src','');
				$('#contentFrame').attr('src',url);
			}
		}
		
	    function openCreateWindow(){
	        $("#createModifyWindow").show();
	        var win;
	        win = $('#createModifyWindow').window({
	        	 title : '修改密码信息',
	            width : '500',
	            height : '260',
	            left : ($(window).width() - 500)/2,
	            top : ($(window).height() - 260)/2,
	            collapsible : true,
	            minimizable : false,
	            maximizable : false,
	            closable : false,
	            draggable : false,
	            resizable : false,
	            shadow : true,
	            modal : true,
	            iconCls : 'icon-add',
	            closed : true
	        });
	        win.window("open");
	    }
	</script>
</head>

<body class="easyui-layout">
<div region="north" border="false">
<div class="head">
	<div class="head-logreg">
		<div class="logreg bigbox"><span>您好，<%=userName %></span><a href="${pageContext.request.contextPath}/logout.action">退出</a></div>
	</div>
	<div class="bigbox">
		<a class="head_left" href="#"><p>监测平台</p></a>
		<div class="head_right">
			<ul>
				<li class="on"><a href="javascript:contentFrame('modules/statistics/main.jsp','2');">首页</a></li>
				<c:forEach items="${menuList}" var="menu">
					<c:if test="${menu.menuName == '系统管理'}">
						<li><a href="javascript:contentFrame('${menu.id}','1');">${menu.menuName}</a></li>
					</c:if>
					<c:if test="${menu.menuName != '系统管理'}">
						<li><a href="javascript:contentFrame('${menu.menuUrl}','2');">${menu.menuName}</a></li>
					</c:if>
				</c:forEach>
			</ul>
		</div>
	</div>
</div>
</div>

<div region="center" style="height: 100%;">
	<iframe id="contentFrame" name="contentFrame" scrolling="no" marginheight="0" marginwidth="auto" frameborder="0" 
		height="100%" width="100%" align="center" src="${pageContext.request.contextPath}/modules/statistics/main.jsp"></iframe>
</div>

<div class="subhead2" region="south" border="false">
	<div class="foot">
		<div class="bigbox" align="center"><span>&copy;数字化学习技术集成与应用教育部工程研究中心，依托单位：国家开放大学</span></div>
	</div>
</div>

</body>
</html>

