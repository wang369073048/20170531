<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<link href="${pageContext.request.contextPath}/css/mainstyle.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";

		$(function () {
		    var screenwidth, screenheight, mytop, getPosLeft, getPosTop
		    screenwidth = $(window).width();
		    screenheight = $(window).height();
		    //获取滚动条距顶部的偏移
		    mytop = $(document).scrollTop();
		    //计算弹出层的left
		    getPosLeft = screenwidth / 2;
		    //计算弹出层的top
		    getPosTop = screenheight / 2 - 150;
		    //css定位弹出层
		    $("#divLoginWindow").css({ "left": getPosLeft, "top": getPosTop });
		    //当浏览器窗口大小改变时
		    $(window).resize(function () {
		        screenwidth = $(document).width();
		        screenheight = $(document).height();
		        mytop = $(document).scrollTop();
		        getPosLeft = screenwidth / 2 ;
		        getPosTop = screenheight / 2 - 150;
		        $("#divLoginWindow").css({ "left": getPosLeft, "top": getPosTop + mytop });
		    });
		    //当拉动滚动条时，弹出层跟着移动
		    $(window).scroll(function () {
		        screenwidth = $(document).width();
		        screenheight = $(document).height();
		        mytop = $(document).scrollTop();
		        getPosLeft = screenwidth / 2;
		        getPosTop = screenheight / 2 - 150;
		        $("#divLoginWindow").css({ "left": getPosLeft, "top": getPosTop + mytop });
		    });
		    
		    //弹出登录窗口
		   var divShow = function () {
		        $("#divLoginWindow").fadeIn("slow"); //toggle("slow"); 
		        //获取页面文档的高度
		        var docheight = $(document).height();
		        //追加一个层，使背景变灰
		        $(".main").append("<div id='greybackground'></div>");
		        $("#greybackground").css({ "opacity": "0.5", "height": docheight, "z-index": "1000", "background-color": "#ccc","position":"absolute" });
		        $("#greybackground").width($(document).width()); 
		        $('#greybackground').height(500); 
		        $('#greybackground').css('left',0); 
		        $('#greybackground').css('top',0);
		        return true; 
		    };
		    //关闭按钮
		   var hideDiv = function () {
		        $("#divLoginWindow").fadeOut("slow"); ////hide();
		        //删除变灰的层
		        $("#greybackground").remove();
		    };	
		    
		    //ajax提交下发表单
		    $(".reportData").click(function(){
			    var zyk = $(this).attr("data").split(",");
			    var v = $(this);
			    if(divShow()){
			    	$.ajax({
						url : path + '/report_basic/sendReports.action',
						data : {"zykId":zyk[0],"fullName":zyk[1],"instituteInCharge":zyk[2],"status":zyk[3]},
						dataType : "text",
						success : function(data){
							var data = eval("("+data+")");
							v.closest("tr").find(".sendstatus").text(data.sendstatus);
							v.closest("tr").find(".sendtime").text(data.sendLastTime);
							hideDiv();
						},
						error : function(data){
							v.closest("tr").find(".sendstatus").text("下发失败");
							v.closest("tr").find(".sendtime").text(new Date());
							hideDiv();
						}
					});	
				};
				
			})
		}); 

		function changeStatus(status){
			var url = "${pageContext.request.contextPath}/report_basic/reportBasic.action?status="+status;
			parent.$('#contentFrame').attr('src','');
			parent.$('#contentFrame').attr('src',url);
		}
	</script>
</head>

<body>
<div class="main">
	<div id="divLoginWindow" style="display:none;position:absolute;">
		<img src="${pageContext.request.contextPath}/images/loading.gif"/>
	</div>
	<div class="main_bigbox">
		<div class="leftstatus">
			<div class="menu-status">
				<ul>
					<li class="on"><a href="###" class="sbz">申报中</a> </li>
					<li><a href="javascript:changeStatus(2);" class="ylx">已立项</a> </li>
					<li><a href="javascript:changeStatus(3);" class="yys">已验收</a> </li>
				</ul>
			</div>
		</div>
		
		<div class="main_rightcontent" style="background-color:#F3F1F1!important;margin-left:90px; overflow:auto;" id="div_sbz">
			<div class="basic-search">
				<div class="basic-search-div">
					<form id="searchForm" method="post" action="${pageContext.request.contextPath}/report_basic/reportBasic.action?status=1">
						名称 <input type="text" id="fullName" name="fullName" size="20" />&nbsp;&nbsp;
						编号 <input type="text" id="zykNum" name="zykNum" size="20" />&nbsp;&nbsp;
						主持人 <input type="text" id="personInCharge" name="personInCharge" size="20" />&nbsp;&nbsp;
						主持单位 <input type="text" id="instituteInCharge" name="instituteInCharge" size="20" />&nbsp;&nbsp;
						<button type="submit" class="button blue" style="width: 70px;">检索</button>
					</form>
				</div>
			</div>
			
			<table width="98%" align="center" bgcolor="#FFFFFF" style="border: solid thin #DED9D8" cellpadding="0" cellspacing="0">
				<tr height="40" bgcolor="#F7F7F6">
					<td align="left" width="2%">&nbsp;&nbsp;<font color="blue"></font></td>
					<td align="left" width="15%">&nbsp;&nbsp;<font color="blue">
						<a href="<%=request.getContextPath() %>/report_basic/reportBasic.action?order=fullname" style="color: blue">名称</a></font>
					</td>
					<td align="left" width="10%">&nbsp;&nbsp;<font color="blue">
						<a href="<%=request.getContextPath() %>/report_basic/reportBasic.action" style="color: blue">资源库编号</a></font>
					</td>
					<td align="left" width="20%">&nbsp;&nbsp;&nbsp;&nbsp;<font color="blue">主持单位</font></td>
					<td align="center" width="7%"><font color="blue">项目负责人</font></td>
					<td align="center" width="8%"><font color="blue">日期</font></td>
					<td align="center" width="20%"><font color="blue">操作</font></td>
					<td align="center" width="8%"><font color="blue">下发状态</font></td>
					<td align="center" width="10%"><font color="blue">最近下发时间</font></td>
				</tr>
				
				<c:forEach items="${resultList}" var="zyk" varStatus="status">
					<tr height="40" <c:if test="${status.count%2==0}">bgcolor="#FCFCFC"</c:if>>
						<td align="center" style="border-top:1px solid #DED9D8;">${status.index + 1}</td>
						<td align="left" style="border-top:1px solid #DED9D8;">
							&nbsp;&nbsp;
							<font color="${zyk.sendColor}">
								<c:if test="${fn:length(zyk.fullname)>15}">${fn:substring(zyk.fullname,0,15)} ...</c:if>
								<c:if test="${fn:length(zyk.fullname)<=15}">${zyk.fullname }</c:if>
							</font>
						</td>
						<td align="left" style="border-top:1px solid #DED9D8;">
								<c:if test="${empty zyk.zykNum}">无</c:if>
								&nbsp;&nbsp;&nbsp;${zyk.zykNum}
						</td>
						<td align="left" style="border-top:1px solid #DED9D8;">
							&nbsp;&nbsp;
							<font color="${zyk.sendColor}">
								<c:if test="${fn:length(zyk.instituteInCharge)>15}">${fn:substring(zyk.instituteInCharge,0,15)}...</c:if>
								<c:if test="${fn:length(zyk.instituteInCharge)<=15}">${zyk.instituteInCharge }</c:if>
							</font>
						</td>
						<td align="center" style="border-top:1px solid #DED9D8;">&nbsp;&nbsp;${zyk.personInCharge}</td>
						<td align="center" style="border-top:1px solid #DED9D8;">&nbsp;&nbsp;${zyk.modifiedDate}</td>
						<td align="center" style="border-top:1px solid #DED9D8;">
							<div>
								<div style="float:left; width:25px;"><img src="${pageContext.request.contextPath}/images/tableicon_01.png" /></div>
								<div style="float:left;">
									<a target="_blank" href="${pageContext.request.contextPath}/report_basic/showAllReports.action?zykId=${zyk.zykId}" target="black">
										查看
									</a>
								</div>
								<div style="float:left; width:15px;">&nbsp;</div>
							</div>
							<div>
								<div style="float:left; width:25px;"><img src="${pageContext.request.contextPath}/images/tableicon_02.png" /></div>
								<div style="float:left;">
									<%-- <a href="${pageContext.request.contextPath}/export/exportPDF.action?zykId=${zyk.zykId}&fullName=${zyk.fullname}&instituteInCharge=${zyk.instituteInCharge}&status=${zyk.status}">
										导出
									</a> --%>
									<a target="_blank"  href="${pageContext.request.contextPath}/report_basic/exportExcelBasicReport.action?zykId=${zyk.zykId}&fullName=${zyk.fullname}&instituteInCharge=${zyk.instituteInCharge}&status=${zyk.status}">
										导出
									</a>
								</div>
								<div style="float:left; width:15px;">&nbsp;</div>
							</div>
							
							<c:if test="${zyk.sendFlag != 2}">
							<div>
								<div style="float:left; width:25px;"><img src="${pageContext.request.contextPath}/images/tableicon_03.png" /></div>
								<div style="float:left;">
									<a class="reportData" style="cursor:pointer" data="${zyk.zykId},${zyk.fullname},${zyk.instituteInCharge},${zyk.status}">
										下发报表
									</a>
								</div>
							</div>
							</c:if>
						</td>
						<td align="center" style="border-top:1px solid #DED9D8;" class="sendstatus">
							<c:if test="${zyk.sendStatus == 1}">下发成功</c:if>
							<c:if test="${zyk.sendStatus == 2}"><font color="red">下发失败</></c:if>
						</td>
						<td align="center" style="border-top:1px solid #DED9D8;" class="sendtime">
							<fmt:formatDate value="${zyk.sendLastTime}" pattern="yyyy-MM-dd HH:mm" var="time"/>${time}
						</td>
					</tr>
				</c:forEach>
			</table>
			<div style="height: 10px;"></div>
		</div>
	</div>
</div>
</body>
</html>