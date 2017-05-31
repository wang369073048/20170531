<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="GB2312" />
	<title>${zykList[0].fullname} 专业资源库基本报告</title>
	
	<link href="${pageContext.request.contextPath}/css/mainstyle.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/easyui.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/easyui/js/easyui.min.js"></script>
	
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>
	
	<style type="text/css">
		#main_table{
			position :center;
			width : 100%;
		}
		
		#title_table{
			padding-bottom: 20px; 
			padding-top: 20px; 
		}
		
		.tr_title{
			color: #0F84E7;
			background-color: #F9F9F9;
			height: 42px;
			font-size: 15px;
		}
		
		#main_table td{
			border: solid;
			border-color: #DDDDDD;
			border-width: 1px;
			height: 45px;
			font-size: 14px;
		}
		
		.report_title{
			padding-left: 30px;
		}
		
		#main_table .report_totalCount{
			padding-left: 40px;
			color: #FFA903;
			border-left-color: #FFFFFF; 
			border-right-color: #FFFFFF; 
			border-bottom-color: #FFFFFF;
		}
		
		#main_table .report_count{
			width:260px;
			padding-left: 40px;
			border-left-color: #FFFFFF; 
			border-right-color: #FFFFFF; 
			border-bottom-color: #FFFFFF;
		}
		
		#main_table .report_rate{
			padding-right: 40px;
			color: #FFA903;
			border-left-color: #FFFFFF; 
			border-right-color: #FFFFFF; 
			border-bottom-color: #FFFFFF;
		}
		
		#main_table .report_options{
			width:260px;
			padding-left: 30px;
			border-left-color: #FFFFFF; 
			border-right-color: #FFFFFF; 
			border-bottom-color: #FFFFFF; 
 		}
		
		#main_table .report_content{
			border-bottom-color: #FFFFFF; 
			border-top-color: #FFFFFF; 
		
		}
		
		#main_table .td_count {
			border-bottom-color: #FFFFFF; 
			border-top-color: #FFFFFF;
		}
		
		#main_table .td_comment {
			padding-left: 5px;
			border-top-color: #FFFFFF; 
			border-left-color: #FFFFFF;
		}
		
		body{
			font-family: STSong-Light-H;
		}
	</style>
	
</head>

<body class="ovreflow1" style="width:100%;height:100%;"
oncontextmenu="return false" 
ondragstart="return false" 
onselectstart ="return false" 
onselect="return false" 
oncopy="return false" 
onbeforecopy="return false" 
onmouseup="return false">
<noscript>
<iframe scr="*.htm"></iframe>
</noscript>
	<div align="center" >
		<div style="width: 98%; padding-top: 10px; padding-bottom: 10px; ">
			<table id="title_table" cellpadding="0" cellspacing="0" style="width: 100%; background-color: #F9F9F9;" >
				<tr>
					<th colspan="5" style="height: 30px; padding-bottom: 10px;" >
						<font style="font-size: 23px;"><b>${zykList[0].fullname} 专业资源库基本报告</b></font>
					</th>
				</tr>
				<tr align="center">
					<td style="font-size: 18px; text-align:right; width: 300px;" >
						<b>资源库编号：
							<c:if test="${empty zykList[0].zykNum}">
								无   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</c:if>
							${zykList[0].zykNum}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
						</b>
					</td>
					<td style="font-size: 18px; text-align:left; width: 250px;" >
						<c:if test="${zykList[0].status == '1'}"><b>状态:<font color="red">申报中</font></b></c:if>
						<c:if test="${zykList[0].status == '2'}"><b>状态:<font color="red">已立项</font></b></c:if>
						<c:if test="${zykList[0].status == '3'}"><b>状态:<font color="red">已验收</font></b></c:if>
					</td>
				</tr>
				<c:if test="${not empty zykLogStartTime}">
					<tr align="center">
						<td colspan="5" style="font-size: 18px;" >
							<b>运行数据提交时间：${zykLogStartTime} — ${zykLogEndTime }</b>
						</td>
					</tr>
				</c:if>
			</table>
		</div>
		
		<div>
			<table id="main_table" cellpadding="0" cellspacing="0" style="width: 98%; padding-bottom: 10px;" >
				<tr align="center" >
					<td class="tr_title" style="width: 10%">维度</td>
					<td class="tr_title" style="width: 10%">内容</td>
					<td class="tr_title" style="width: 20%">统计</td>
					<td class="tr_title" style="width: 20%">统计项</td>
					<td class="tr_title" style="width: 20%">统计值</td>
					<td class="tr_title" style="width: 20%">备注</td>	
				</tr>
				
				<tr>
					<td align="center" rowspan="16">建设</td>
				</tr>
				
				<tr>
					<td align="center" rowspan="4">用户</td>
				</tr>
				
				<tr>
					<td class="report_title">用户总数</td>
					<td class="report_content" style="border-bottom-color: #FFFFFF">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options" style="border-top-color: #FFFFFF;"><b>总量</b></td></tr>
							<c:forEach items="${resultMap.userCountList}" var="userCountList" >
								<tr>
									<c:forEach items="${userCountList}" var="userCount">
						  				<c:if test='${userCount.key eq "role"}'>
					  						<td class="report_options" >
					  							${userCount.value}
					  						</td>
					  					</c:if>
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					
					<td align="center" class="td_count" style="border-bottom-color: #FFFFFF;">
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
								<%-- 总量 --%>
								<c:forEach items="${resultMap.userCountList}" var="userCountList" >
									<c:forEach items="${userCountList}" var="userCount">
										<c:if test='${userCount.key eq "totalCount"}'>
							<tr>
						  					<td class="report_totalCount" style="border-top-color: #FFFFFF;" colspan="2" align="left" >
						  						<b>${userCount.value}</b>
						  					</td>
							</tr>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							<c:forEach items="${resultMap.userCountList}" var="userCountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${userCountList}" var="userCount">
										<c:if test='${userCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${userCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					<%-- 统计百分比 --%>
				  					<c:forEach items="${userCountList}" var="userCount">
										<c:if test='${userCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${userCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">“用户总数”指的是资源库运行平台用户总数，包括教师、学生、社会学习者、企业用户等类型。文件要求不少于1000人。</td>
				</tr>
				<tr>
					<td class="report_title">用户来自的院校数 </td>
						<%-- 统计项 --%>
					<td class="report_content" style="border-top-color: #FFFFFF">
						<table class="report_content_table" style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<%-- <c:forEach items="${resultMap.userInstituteList}" var="userInstituteList" >
							<tr>
								<c:forEach items="${userInstituteList}" var="userInstitute">
					  				<c:if test='${userInstitute.key eq "institute"}'>
				  						<td class="report_options" >${userInstitute.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach> --%>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.userInstituteList}" var="userInstituteList" >
										<c:forEach items="${userInstituteList}" var="userInstitute">
											<c:if test='${userInstitute.key eq "totalCount"}'>
							  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
							  						<b>${userInstitute.value}</b>
							  					</td>
							  				</c:if>
					  					</c:forEach>
								</c:forEach>
							</tr>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">用户来自的省级行政区数 </td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table class="report_content_table" style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.userProvinceList}" var="userProvinceList" >
									<c:forEach items="${userProvinceList}" var="userProvince">
										<c:if test='${userProvince.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${userProvince.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td align="center" rowspan="3">资源</td>
				</tr>
				<tr>
					<td class="report_title">资源素材总数</td>
					<%-- 检验是否有数据 --%>
					<%-- <c:if test="${fn:length(resultMap.resourceCountList) == 1 }">
						<td align="center" colspan="2">无统计数据</td>
					</c:if> --%>
					
					<%-- 统计项 --%>
					<%-- <c:if test="${fn:length(resultMap.resourceCountList) != 1 }"> --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.resourceCountList}" var="resourceCountList" >
							<tr>
								<c:forEach items="${resourceCountList}" var="resourceCount">
					  				<c:if test='${resourceCount.key eq "media"}'>
				  						<td class="report_options" >${resourceCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<%-- </c:if> --%>
					<%-- 统计值 --%>
					<%-- <c:if test="${fn:length(resultMap.resourceCountList) != 1 }"> --%>
						<td align="center" class="td_count" >
							<table style="width: 100%" cellpadding="0" cellspacing="0" >
								<tr>
									<%-- 总量 --%>
									<c:forEach items="${resultMap.resourceCountList}" var="resourceCountList" >
										<c:forEach items="${resourceCountList}" var="resourceCount">
											<c:if test='${resourceCount.key eq "totalCount"}'>
							  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
							  						<b>${resourceCount.value}</b>
							  					</td>
							  				</c:if>
					  					</c:forEach>
									</c:forEach>
								</tr>
								<c:forEach items="${resultMap.resourceCountList}" var="resourceCountList" >
									<tr>
										<%-- 统计数量 --%>
										<c:forEach items="${resourceCountList}" var="resourceCount">
											<c:if test='${resourceCount.key eq "count"}'>
							  					<td class="report_count" style="border-right-color:#FFFFFF;" align="left">
							  						${resourceCount.value}
							  					</td>
							  				</c:if>
					  					</c:forEach>
					  					<%-- 统计百分比 --%>
					  					<c:forEach items="${resourceCountList}" var="resourceCount">
											<c:if test='${resourceCount.key eq "rate"}'>
							  					<td class="report_rate" style="border-left-color:#FFFFFF;" align="right">
							  						<b>${resourceCount.value}</b>
							  					</td>
							  				</c:if>
					  					</c:forEach>
							  		</tr>
								</c:forEach>
							</table>
						</td>
					<%-- </c:if> --%>
					<td class="td_comment">“资源素材总数”指的是资源库运行平台中存储的资源素材总量。包括文本类、图形图像类等，其中文本类和图形图像类总量不超过50%。</td>
				</tr>
				<%-- <tr>
					<td class="report_title">不同教学应用的资源素材分布</td>
					检验是否有数据
					<c:if test="${fn:length(resultMap.instructionCountList) == 1 }">
						<td align="center" colspan="2">无统计数据</td>
					</c:if>
					
					统计项
					<c:if test="${fn:length(resultMap.instructionCountList) != 1 }">
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.instructionCountList}" var="instructionCountList" >
							<tr>
								<c:forEach items="${instructionCountList}" var="instructionCount">
					  				<c:if test='${instructionCount.key eq "instruction"}'>
				  						<td class="report_options" >${instructionCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
						</td>
					</c:if>
					统计值
					<c:if test="${fn:length(resultMap.instructionCountList) != 1 }">
						<td align="center" class="td_count" >
							<table style="width: 100%" cellpadding="0" cellspacing="0" >
								<tr>
									总量
									<c:forEach items="${resultMap.instructionCountList}" var="instructionCountList" >
											<c:forEach items="${instructionCountList}" var="instructionCount">
												<c:if test='${instructionCount.key eq "totalCount"}'>
								  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
								  						<b>${instructionCount.value}</b>
								  					</td>
								  				</c:if>
						  					</c:forEach>
									</c:forEach>
								</tr>
								<c:forEach items="${resultMap.instructionCountList}" var="instructionCountList" >
									<tr>
										统计数量
										<c:forEach items="${instructionCountList}" var="instructionCount">
											<c:if test='${instructionCount.key eq "count"}'>
							  					<td class="report_count" style="border-right-color:#FFFFFF;" align="left">
							  						${instructionCount.value}
							  					</td>
							  				</c:if>
					  					</c:forEach>
					  					统计百分比
					  					<c:forEach items="${instructionCountList}" var="instructionCount">
											<c:if test='${instructionCount.key eq "rate"}'>
							  					<td class="report_rate" style="border-left-color:#FFFFFF;" align="right">
							  						<b>${instructionCount.value}</b>
							  					</td>
							  				</c:if>
					  					</c:forEach>
							  		</tr>
								</c:forEach>
							</table>
						</td>
					</c:if>
				</tr> --%>
				<tr>
					<td class="report_title">涉及本专业知识点数 </td>
					<%-- 检验是否有数据 --%>
					<%-- <c:if test="${fn:length(resultMap.knowledgeCountList) == 1 }">
						<td align="center" colspan="2">无统计数据</td>
					</c:if> --%>
					
					<%-- 统计项 --%>
					<%-- <c:if test="${fn:length(resultMap.knowledgeCountList) != 1 }"> --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<%-- 
						<c:forEach items="${resultMap.knowledgeCountList}" var="knowledgeCountList" >
							<tr>
								<c:forEach items="${knowledgeCountList}" var="knowledgeCount">
					  				<c:if test='${knowledgeCount.key eq "knowledge"}'>
				  						<td class="report_options" >${knowledgeCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>--%>
						</table>
					</td>
					<%-- </c:if> --%>
					<%-- 统计值 --%>
					<%-- <c:if test="${fn:length(resultMap.knowledgeCountList) != 1 }"> --%>
					<td align="center" class="td_count" style="border-top-color: #DDDDDD;" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.knowledgeCountList}" var="knowledgeCountList" >
										<c:forEach items="${knowledgeCountList}" var="knowledgeCount">
											<c:if test='${knowledgeCount.key eq "totalCount"}'>
							  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903; border-top-color: #FFFFFF;">
							  						<b>${knowledgeCount.value}</b>
							  					</td>
							  				</c:if>
					  					</c:forEach>
								</c:forEach>
							</tr>
							<%-- 
							<c:forEach items="${resultMap.knowledgeCountList}" var="knowledgeCountList" >
								<tr>
									<%-- 统计数量
									<c:forEach items="${knowledgeCountList}" var="knowledgeCount">
										<c:if test='${knowledgeCount.key eq "count"}'>
						  					<td class="report_count" style="border-right-color:#FFFFFF;" align="left">
						  						${knowledgeCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					<%-- 统计百分比
				  					<c:forEach items="${knowledgeCountList}" var="knowledgeCount">
										<c:if test='${knowledgeCount.key eq "rate"}'>
						  					<td class="report_rate" style="border-left-color:#FFFFFF;" align="right">
						  						<b>${knowledgeCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
							 --%>
						</table>
					</td>
					<%-- </c:if> --%>
					<td class="td_comment">指的是资源素材信息中标注的知识点数量。</td>
				</tr>
				<tr>
					<td align="center" rowspan="6">课程</td>
				</tr>
				<tr>
					<td class="report_title">课程数</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr>
							<td class="report_options" style="border-top-color: #DDDDDD;">
								<b>总量</b>
							</td>
			  			</tr>
						</table>
					</td>
					<td class="report_totalCount" align="left" style="color: #FFA903; border-right-color: #DDDDDD;">
						<b>${resultMap.courseCount.totalCount}</b>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">不同类型课程分布 </td>
					<%-- 统计项 --%>
					<td class="report_content" style="border-bottom-color: #FFFFFF">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
							<c:forEach items="${resultMap.courseTypeCountList}" var="courseTypeCountList" >
								<tr>
									<c:forEach items="${courseTypeCountList}" var="courseTypeCount">
						  				<c:if test='${courseTypeCount.key eq "courseType"}'>
					  						<td class="report_options" >
					  							${courseTypeCount.value}
					  						</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count">
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.courseTypeCountList}" var="courseTypeCountList" >
									<c:forEach items="${courseTypeCountList}" var="courseTypeCount">
										<c:if test='${courseTypeCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" >
						  						<b>${courseTypeCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.courseTypeCountList}" var="courseTypeCountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${courseTypeCountList}" var="courseTypeCount">
										<c:if test='${courseTypeCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${courseTypeCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					<%-- 统计百分比 --%>
				  					<c:forEach items="${courseTypeCountList}" var="courseTypeCount">
										<c:if test='${courseTypeCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${courseTypeCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">文件要求专业核心课不少于6门。</td>
				</tr>
				<tr>
					<td class="report_title">微课和培训项目</td>
					<%-- 统计项 --%>
					<td class="report_content" style="border-bottom-color: #FFFFFF">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
							<c:forEach items="${resultMap.courseLevelCountList}" var="courseLevelCountList" >
								<tr>
									<c:forEach items="${courseLevelCountList}" var="courseLevelCount">
						  				<c:if test='${courseLevelCount.key eq "courseLevel"}'>
					  						<td class="report_options" >
					  							${courseLevelCount.value}
					  						</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count">
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.courseLevelCountList}" var="courseLevelCountList" >
									<c:forEach items="${courseLevelCountList}" var="courseLevelCount">
										<c:if test='${courseLevelCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" >
						  						<b>${courseLevelCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.courseLevelCountList}" var="courseLevelCountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${courseLevelCountList}" var="courseLevelCount">
										<c:if test='${courseLevelCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${courseLevelCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					<%-- 统计百分比 --%>
				  					<c:forEach items="${courseLevelCountList}" var="courseLevelCount">
										<c:if test='${courseLevelCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${courseLevelCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">模块数</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.courseModuleCountList}" var="courseModuleCountList" >
									<c:forEach items="${courseModuleCountList}" var="courseModuleCount">
										<c:if test='${courseModuleCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${courseModuleCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
						</table>
					</td>
					<td class="td_comment">指的是资源库运行平台中所有课程各种学习模块的总数。</td>
				</tr>
				<tr>
					<td class="report_title">课程引用的资源素材占所有资源素材比</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<!-- <tr><td class="report_options"><b>总量</b></td></tr> -->
						<c:forEach items="${resultMap.courseResourceCountList}" var="courseResourceCountList" >
							<tr>
								<c:forEach items="${courseResourceCountList}" var="courseResourceCount">
					  				<c:if test='${courseResourceCount.key eq "resource"}'>
				  						<td class="report_options" >${courseResourceCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- <tr>
								总量
								<c:forEach items="${resultMap.courseResourceCountList}" var="courseResourceCountList" >
									<c:forEach items="${courseResourceCountList}" var="courseResourceCount">
										<c:if test='${courseResourceCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${courseResourceCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr> --%>
							<c:forEach items="${resultMap.courseResourceCountList}" var="courseResourceCountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${courseResourceCountList}" var="courseResourceCount">
										<c:if test='${courseResourceCount.key eq "count"}'>
						  					<td class="report_count" style="border-right-color:#FFFFFF;" colspan="2" align="left">
						  						${courseResourceCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					<%-- 统计百分比 --%>
				  					<%-- <c:forEach items="${courseResourceCountList}" var="courseResourceCount">
										<c:if test='${courseResourceCount.key eq "rate"}'>
						  					<td class="report_rate" style="border-left-color:#FFFFFF;" align="right">
						  						<b>${courseResourceCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach> --%>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">资源库运行平台中课程的各学习模块中出现的资源素材即为课程引用的资源素材。</td>
				</tr>
				<tr>
					<td align="center" rowspan="2">题库</td>
				</tr>
				<tr>
					<td class="report_title">题库题目总数 </td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr>
								<td class="report_options" style="border-top-color: #DDDDDD;">
									<b>总量</b>
								</td>
				  			</tr>
						</table>
					</td>
					<td class="report_totalCount" align="left" style="color: #FFA903; border-right-color: #DDDDDD;">
						<b>${resultMap.totalQuestionCount.count}</b>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td align="center" rowspan="19">应用</td>
				</tr>
				<tr>
					<td align="center" rowspan="3">用户</td>
				</tr>
				<tr>
					<td class="report_title">用户行为活动总量</td>
						
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.userBehaviourCountList}" var="userBehaviourCountList" >
							<tr>
								<c:forEach items="${userBehaviourCountList}" var="userBehaviourCount">
					  				<c:if test='${userBehaviourCount.key eq "behaviour"}'>
				  						<td class="report_options" >${userBehaviourCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.userBehaviourCountList}" var="userBehaviourCountList" >
									<c:forEach items="${userBehaviourCountList}" var="userBehaviourCount">
										<c:if test='${userBehaviourCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${userBehaviourCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.userBehaviourCountList}" var="userBehaviourCountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${userBehaviourCountList}" var="userBehaviourCount">
										<c:if test='${userBehaviourCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${userBehaviourCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					<%-- 统计百分比 --%>
				  					<c:forEach items="${userBehaviourCountList}" var="userBehaviourCount">
										<c:if test='${userBehaviourCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${userBehaviourCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">“用户行为活动总量”及其下的资源库运行平台各模块活动量反映了用户活跃度。文件要求用户活跃度高，开展了授课、布置作业、考试、学习等活动。</td>
				</tr>
				<tr>
					<td class="report_title">注册用户活动情况</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<!-- <tr><td class="report_options"><b>总量</b></td></tr> -->
						<c:forEach items="${resultMap.userActiveLogCountList}" var="userActiveLogCountList" >
							<tr>
								<c:forEach items="${userActiveLogCountList}" var="userActiveLogCount">
					  				<c:if test='${userActiveLogCount.key eq "active"}'>
				  						<td class="report_options" >${userActiveLogCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- <tr>
								总量
								<c:forEach items="${resultMap.userActiveLogCountList}" var="userActiveLogCountList" >
									<c:forEach items="${userActiveLogCountList}" var="userActiveLogCount">
										<c:if test='${userActiveLogCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${userActiveLogCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr> --%>
							<c:forEach items="${resultMap.userActiveLogCountList}" var="userActiveLogCountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${userActiveLogCountList}" var="userActiveLogCount">
										<c:if test='${userActiveLogCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${userActiveLogCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					<%-- 统计百分比 --%>
				  					<c:forEach items="${userActiveLogCountList}" var="userActiveLogCount">
										<c:if test='${userActiveLogCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${userActiveLogCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">“注册用户活动情况”反映了用户活跃度。</td>
				</tr>
				<%-- <tr>
					<td class="report_title">用户累计学习天数</td>
					统计项
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.userStudyDateCountList}" var="userStudyDateCountList" >
							<tr>
								<c:forEach items="${userStudyDateCountList}" var="userStudyDateCount">
					  				<c:if test='${userStudyDateCount.key eq "role"}'>
				  						<td class="report_options" >${userStudyDateCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					统计值
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								总量
								<c:forEach items="${resultMap.userStudyDateCountList}" var="userStudyDateCountList" >
									<c:forEach items="${userStudyDateCountList}" var="userStudyDateCount">
										<c:if test='${userStudyDateCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${userStudyDateCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.userStudyDateCountList}" var="userStudyDateCountList" >
								<tr>
									统计数量
									<c:forEach items="${userStudyDateCountList}" var="userStudyDateCount">
										<c:if test='${userStudyDateCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${userStudyDateCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					统计百分比
				  					<c:forEach items="${userStudyDateCountList}" var="userStudyDateCount">
										<c:if test='${userStudyDateCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${userStudyDateCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
				</tr> --%>
				<%-- <tr>
					<td class="report_title">用户活动总次数</td>
					统计项
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.userActiveCountList}" var="userActiveCountList" >
							<tr>
								<c:forEach items="${userActiveCountList}" var="userStudyDateCount">
					  				<c:if test='${userStudyDateCount.key eq "role"}'>
				  						<td class="report_options" >${userStudyDateCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					统计值
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								总量
								<c:forEach items="${resultMap.userActiveCountList}" var="userActiveCountList" >
									<c:forEach items="${userActiveCountList}" var="userActiveCount">
										<c:if test='${userActiveCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${userActiveCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.userActiveCountList}" var="userActiveCountList" >
								<tr>
									统计数量
									<c:forEach items="${userActiveCountList}" var="userActiveCount">
										<c:if test='${userActiveCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${userActiveCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					统计百分比
				  					<c:forEach items="${userActiveCountList}" var="userActiveCount">
										<c:if test='${userActiveCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${userActiveCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
				</tr> --%>
				<%-- <tr>
					<td class="report_title">人均活动天数</td>
					统计项
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.userActiveDateAvgCountList}" var="userActiveDateAvgCountList" >
							<tr>
								<c:forEach items="${userActiveDateAvgCountList}" var="userActiveDateAvgCount">
					  				<c:if test='${userActiveDateAvgCount.key eq "active"}'>
				  						<td class="report_options" >${userActiveDateAvgCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					统计值
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userActiveDateAvgCountList}" var="userActiveDateAvgCountList" >
								<tr>
									统计数量
									<c:forEach items="${userActiveDateAvgCountList}" var="userActiveDateAvgCount">
										<c:if test='${userActiveDateAvgCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${userActiveDateAvgCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					统计百分比
				  					<c:forEach items="${userActiveDateAvgCountList}" var="userActiveDateAvgCount">
										<c:if test='${userActiveDateAvgCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${userActiveDateAvgCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
				</tr> --%>
				<tr>
					<td align="center" rowspan="6">资源</td>
				</tr>
				<tr>
					<td class="report_title">资源素材使用总次数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.resourceUsingCountList}" var="resourceUsingCountList" >
									<c:forEach items="${resourceUsingCountList}" var="resourceUsingCount">
										<c:if test='${resourceUsingCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${resourceUsingCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">资源素材应用情况</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
							<c:forEach items="${resultMap.resourceUsingActionCountList}" var="resourceUsingActionCountList" >
								<tr>
									<c:forEach items="${resourceUsingActionCountList}" var="resourceUsingActionCount">
						  				<c:if test='${resourceUsingActionCount.key eq "action"}'>
					  						<td class="report_options" >${resourceUsingActionCount.value}</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.resourceUsingActionCountList}" var="resourceUsingActionCountList" >
									<c:forEach items="${resourceUsingActionCountList}" var="resourceUsingActionCount">
										<c:if test='${resourceUsingActionCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${resourceUsingActionCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.resourceUsingActionCountList}" var="resourceUsingActionCountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${resourceUsingActionCountList}" var="resourceUsingActionCount">
										<c:if test='${resourceUsingActionCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${resourceUsingActionCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					<%-- 统计百分比 --%>
				  					<c:forEach items="${resourceUsingActionCountList}" var="resourceUsingActionCount">
										<c:if test='${resourceUsingActionCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${resourceUsingActionCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">不同文件类型资源素材浏览和下载频次</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.resourceUsingAndDownloadCountList}" var="resourceUsingAndDownloadCountList" >
							<tr>
								<c:forEach items="${resourceUsingAndDownloadCountList}" var="resourceUsingAndDownloadCount">
					  				<c:if test='${resourceUsingAndDownloadCount.key eq "media"}'>
				  						<td class="report_options" >${resourceUsingAndDownloadCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.resourceUsingAndDownloadCountList}" var="resourceUsingAndDownloadCountList" >
									<c:forEach items="${resourceUsingAndDownloadCountList}" var="resourceUsingAndDownloadCount">
										<c:if test='${resourceUsingAndDownloadCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${resourceUsingAndDownloadCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.resourceUsingAndDownloadCountList}" var="resourceUsingAndDownloadCountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${resourceUsingAndDownloadCountList}" var="resourceUsingAndDownloadCount">
										<c:if test='${resourceUsingAndDownloadCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${resourceUsingAndDownloadCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					<%-- 统计百分比 --%>
				  					<c:forEach items="${resourceUsingAndDownloadCountList}" var="resourceUsingAndDownloadCount">
										<c:if test='${resourceUsingAndDownloadCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${resourceUsingAndDownloadCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">使用的资源在全部资源的占比</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<!-- <tr><td class="report_options"><b>总量</b></td></tr> -->
							<c:forEach items="${resultMap.resourceUsingRateCountList}" var="resourceUsingRateCountList" >
								<tr>
									<c:forEach items="${resourceUsingRateCountList}" var="resourceUsingRateCount">
						  				<c:if test='${resourceUsingRateCount.key eq "action"}'>
					  						<td class="report_options" >${resourceUsingRateCount.value}</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.resourceUsingRateCountList}" var="resourceUsingRateCountList" >
									<c:forEach items="${resourceUsingRateCountList}" var="resourceUsingRateCount">
										<c:if test='${resourceUsingRateCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${resourceUsingRateCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.resourceUsingRateCountList}" var="resourceUsingRateCountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${resourceUsingRateCountList}" var="resourceUsingRateCount">
										<c:if test='${resourceUsingRateCount.key eq "count"}'>
						  					<td class="report_count" colspan="2" align="left">
						  						${resourceUsingRateCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					<%-- 统计百分比 --%>
				  					<%-- <c:forEach items="${resourceUsingRateCountList}" var="resourceUsingRateCount">
										<c:if test='${resourceUsingRateCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${resourceUsingRateCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach> --%>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">平均每个资源使用次数</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<!-- <tr>
								<td class="report_options"><b>总量</b></td>
							</tr> -->
							<tr>
								<td class="report_options"><b>平均值</b></td>
							</tr>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- <tr>
								总量
								<c:forEach items="${resultMap.resourceUsingAvgCountList}" var="resourceUsingAvgCountList" >
									<c:forEach items="${resourceUsingAvgCountList}" var="resourceUsingAvgCount">
										<c:if test='${resourceUsingAvgCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${resourceUsingAvgCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr> --%>
							<c:forEach items="${resultMap.resourceUsingAvgCountList}" var="resourceUsingAvgCountList" >
								<tr>
				  					<%-- 平均值 --%>
				  					<c:forEach items="${resourceUsingAvgCountList}" var="resourceUsingAvgCount">
										<c:if test='${resourceUsingAvgCount.key eq "avgCount"}'>
						  					<td class="report_count" colspan="2" align="left">
						  						${resourceUsingAvgCount.value}
						  					</td>
						  				</c:if>
					  				</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td align="center" rowspan="2">课程</td>
				</tr>
				<%-- <tr>
					<td class="report_title">课程访问次数</td>
					统计项
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
							<c:forEach items="${resultMap.courseVisitCountList}" var="courseVisitCountList" >
								<tr>
									<c:forEach items="${courseVisitCountList}" var="courseVisitCount">
						  				<c:if test='${courseVisitCount.key eq "fullname"}'>
					  						<td class="report_options" >${courseVisitCount.value}</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					统计值
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								总量
								<c:forEach items="${resultMap.courseVisitCountList}" var="courseVisitCountList" >
									<c:forEach items="${courseVisitCountList}" var="courseVisitCount">
										<c:if test='${courseVisitCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${courseVisitCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.courseVisitCountList}" var="courseVisitCountList" >
								<tr>
									统计数量
									<c:forEach items="${courseVisitCountList}" var="courseVisitCount">
										<c:if test='${courseVisitCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${courseVisitCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					统计百分比
				  					<c:forEach items="${courseVisitCountList}" var="courseVisitCount">
										<c:if test='${courseVisitCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${courseVisitCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr> --%>
				<tr>
					<td class="report_title">各个课程的访问情况(前三)</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
							<c:forEach items="${resultMap.courseVisitTop3CountList}" var="courseVisitTop3CountList" >
								<tr>
									<c:forEach items="${courseVisitTop3CountList}" var="courseVisitTop3Count">
						  				<c:if test='${courseVisitTop3Count.key eq "fullname"}'>
					  						<td class="report_options" >${courseVisitTop3Count.value}</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.courseVisitTop3CountList}" var="courseVisitTop3CountList" >
									<c:forEach items="${courseVisitTop3CountList}" var="courseVisitTop3Count">
										<c:if test='${courseVisitTop3Count.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${courseVisitTop3Count.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.courseVisitTop3CountList}" var="courseVisitTop3CountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${courseVisitTop3CountList}" var="courseVisitTop3Count">
										<c:if test='${courseVisitTop3Count.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${courseVisitTop3Count.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					<%-- 统计百分比 --%>
				  					<c:forEach items="${courseVisitTop3CountList}" var="courseVisitTop3Count">
										<c:if test='${courseVisitTop3Count.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${courseVisitTop3Count.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td align="center" rowspan="3">题库</td>
				</tr>
				<tr>
					<td class="report_title">题库题目引用数</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.questionQuesCountList}" var="questionQuesCountList" >
									<c:forEach items="${questionQuesCountList}" var="questionQuesCount">
										<c:if test='${questionQuesCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${questionQuesCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">题库题目使用总次数</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.questionUsingCountList}" var="questionUsingCountList" >
									<c:forEach items="${questionUsingCountList}" var="questionUsingCount">
										<c:if test='${questionUsingCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${questionUsingCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td align="center" rowspan="4">学习</td>
				</tr>
				<tr>
					<td class="report_title">论坛活动模块统计</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
							<c:forEach items="${resultMap.forumActiveCountList}" var="forumActiveCountList" >
								<tr>
									<c:forEach items="${forumActiveCountList}" var="workActiveCount">
						  				<c:if test='${workActiveCount.key eq "action"}'>
					  						<td class="report_options" >${workActiveCount.value}</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.forumActiveCountList}" var="forumActiveCountList" >
									<c:forEach items="${forumActiveCountList}" var="forumActiveCount">
										<c:if test='${forumActiveCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${forumActiveCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.forumActiveCountList}" var="forumActiveCountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${forumActiveCountList}" var="forumActiveCount">
										<c:if test='${forumActiveCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${forumActiveCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					<%-- 平均值 --%>
				  					<c:forEach items="${forumActiveCountList}" var="forumActiveCount">
										<c:if test='${forumActiveCount.key eq "avgCount"}'>
						  					<td class="report_count" colspan="2" align="left">
						  						${forumActiveCount.value}
						  					</td>
						  				</c:if>
					  				</c:forEach>
				  					<%-- 统计百分比 --%>
				  					<c:forEach items="${forumActiveCountList}" var="forumActiveCount">
										<c:if test='${forumActiveCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${forumActiveCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">作业活动模块统计</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.workActiveCountList}" var="workActiveCountList" >
							<tr>
								<c:forEach items="${workActiveCountList}" var="workActiveCount">
					  				<c:if test='${workActiveCount.key eq "work"}'>
				  						<td class="report_options" >${workActiveCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.workActiveCountList}" var="workActiveCountList" >
									<c:forEach items="${workActiveCountList}" var="workActiveCount">
										<c:if test='${workActiveCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${workActiveCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.workActiveCountList}" var="workActiveCountList" >
							<tr>
								<%-- 统计数量 --%>
								<c:forEach items="${workActiveCountList}" var="workActiveCount">
									<c:if test='${workActiveCount.key eq "count"}'>
					  					<td class="report_count" align="left">
					  						${workActiveCount.value}
					  					</td>
					  				</c:if>
			  					</c:forEach>
			  					<%-- 平均值 --%>
			  					<c:forEach items="${workActiveCountList}" var="workActiveCount">
									<c:if test='${workActiveCount.key eq "avgCount"}'>
					  					<td class="report_count" colspan="2" align="left">
					  						${workActiveCount.value}
					  					</td>
					  				</c:if>
				  				</c:forEach>
			  					<%-- 统计百分比 --%>
			  					<c:forEach items="${workActiveCountList}" var="workActiveCount">
									<c:if test='${workActiveCount.key eq "rate"}'>
					  					<td class="report_rate" align="right">
					  						<b>${workActiveCount.value}</b>
					  					</td>
					  				</c:if>
			  					</c:forEach>
					  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">考试活动模块统计</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.examActiveCountList}" var="examActiveCountList" >
							<tr>
								<c:forEach items="${examActiveCountList}" var="examActiveCount">
					  				<c:if test='${examActiveCount.key eq "exam"}'>
				  						<td class="report_options" >${examActiveCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								<%-- 总量 --%>
								<c:forEach items="${resultMap.examActiveCountList}" var="examActiveCountList" >
									<c:forEach items="${examActiveCountList}" var="examActiveCount">
										<c:if test='${examActiveCount.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${examActiveCount.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.examActiveCountList}" var="examActiveCountList" >
							<tr>
								<%-- 统计数量 --%>
								<c:forEach items="${examActiveCountList}" var="examActiveCount">
									<c:if test='${examActiveCount.key eq "count"}'>
					  					<td class="report_count" align="left">
					  						${examActiveCount.value}
					  					</td>
					  				</c:if>
			  					</c:forEach>
			  					<%-- 平均值 --%>
			  					<c:forEach items="${examActiveCountList}" var="examActiveCount">
									<c:if test='${examActiveCount.key eq "avgCount"}'>
					  					<td class="report_count" colspan="2" align="left">
					  						${examActiveCount.value}
					  					</td>
					  				</c:if>
				  				</c:forEach>
			  					<%-- 统计百分比 --%>
			  					<c:forEach items="${examActiveCountList}" var="examActiveCount">
									<c:if test='${examActiveCount.key eq "rate"}'>
					  					<td class="report_rate" align="right">
					  						<b>${examActiveCount.value}</b>
					  					</td>
					  				</c:if>
			  					</c:forEach>
					  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td align="center" rowspan="17">更新</td>
				</tr>
				<tr>
					<td align="center" rowspan="6">用户</td>
				</tr>
				<tr>
					<td class="report_title">同比上一年用户注册增长率</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userIncreaseRateList}" var="userIncreaseRateList" >
								<tr>
									<c:forEach items="${userIncreaseRateList}" var="userIncreaseRate">
						  				<c:if test='${userIncreaseRate.key eq "year"}'>
					  						<td class="report_options" >${userIncreaseRate.value}</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userIncreaseRateList}" var="userIncreaseRateList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${userIncreaseRateList}" var="userIncreaseRate">
										<c:if test='${userIncreaseRate.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${userIncreaseRate.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
						以“2015年比2014年增长率”为例，<br/>
						先算出截止到2014年年底上传或更新的资源素材数量a，再算出截止到2015年年底上传或更新的资源素材数量b，那么：<br/>
						2015年比2014年增长率=(b-a)/a
					</td>
				</tr>
				<tr>
					<td class="report_title">同比上一年用户更新率</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userModifiedRateList}" var="userModifiedRateList" >
								<tr>
									<c:forEach items="${userModifiedRateList}" var="userModifiedRate">
						  				<c:if test='${userModifiedRate.key eq "year"}'>
					  						<td class="report_options" >${userModifiedRate.value}</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userModifiedRateList}" var="userModifiedRateList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${userModifiedRateList}" var="userModifiedRate">
										<c:if test='${userModifiedRate.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${userModifiedRate.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<tr>
					<td class="report_title">历年新增用户数</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userAddCountList}" var="userAddCountList" >
								<tr>
									<c:forEach items="${userAddCountList}" var="userAddCount">
						  				<c:if test='${userAddCount.key eq "year"}'>
					  						<td class="report_options" >${userAddCount.value}</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userUpdateCountList}" var="userUpdateCountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${userUpdateCountList}" var="userUpdateCount">
										<c:if test='${userUpdateCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${userUpdateCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<tr>
					<td class="report_title">历年修改用户数</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userUpdateCountList}" var="userUpdateCountList" >
								<tr>
									<c:forEach items="${userUpdateCountList}" var="userUpdateCount">
						  				<c:if test='${userUpdateCount.key eq "year"}'>
					  						<td class="report_options" >${userUpdateCount.value}</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userAddCountList}" var="userAddCountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${userAddCountList}" var="userAddCount">
										<c:if test='${userAddCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${userAddCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<tr>
					<td class="report_title">历年删除用户数</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userDeleteCountList}" var="userDeleteCountList" >
								<tr>
									<c:forEach items="${userDeleteCountList}" var="userDeleteCount">
						  				<c:if test='${userDeleteCount.key eq "year"}'>
					  						<td class="report_options" >${userDeleteCount.value}</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userDeleteCountList}" var="userDeleteCountList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${userDeleteCountList}" var="userDeleteCount">
										<c:if test='${userDeleteCount.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${userDeleteCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<tr>
					<td align="center" rowspan="5">资源</td>
				</tr>
				<tr>
					<td class="report_title">同比上一年资源更新率</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.resourceUpdateRateList}" var="resourceUpdateRateList" >
							<tr>
								<c:forEach items="${resourceUpdateRateList}" var="resourceUpdateRate">
					  				<c:if test='${resourceUpdateRate.key eq "year"}'>
				  						<td class="report_options" >${resourceUpdateRate.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.resourceUpdateRateList}" var="resourceUpdateRateList" >
								<tr>
				  					<%-- 更新率 --%>
				  					<c:forEach items="${resourceUpdateRateList}" var="resourceUpdateRate">
										<c:if test='${resourceUpdateRate.key eq "count"}'>
						  					<td class="report_count" colspan="2" align="left">
						  						${resourceUpdateRate.value}
						  					</td>
						  				</c:if>
					  				</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
						以“2015年比2014年更新率”为例，<br/>
						先算出截止到2014年年底上传或更新的资源素材数量a，再算出截止到2015年年底上传或更新的资源素材数量b，那么：<br/>
						2015年比2014年更新率=(b-a)/a
					</td>
				</tr>
				<tr>
					<td class="report_title">历年新增资源数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.resourceAddCountList}" var="resourceAddCountList" >
							<tr>
								<c:forEach items="${resourceAddCountList}" var="resourceAddCount">
					  				<c:if test='${resourceAddCount.key eq "year"}'>
				  						<td class="report_options" >${resourceAddCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.resourceAddCountList}" var="resourceAddCountList" >
								<tr>
				  					<c:forEach items="${resourceAddCountList}" var="resourceAddCount">
										<c:if test='${resourceAddCount.key eq "count"}'>
						  					<td class="report_count" colspan="2" align="left">
						  						${resourceAddCount.value}
						  					</td>
						  				</c:if>
					  				</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<tr>
					<td class="report_title">历年修改资源数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.resourceUpdateCountList}" var="resourceUpdateCountList" >
							<tr>
								<c:forEach items="${resourceUpdateCountList}" var="resourceUpdateCount">
					  				<c:if test='${resourceUpdateCount.key eq "year"}'>
				  						<td class="report_options" >${resourceUpdateCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.resourceUpdateCountList}" var="resourceUpdateCountList" >
								<tr>
				  					<c:forEach items="${resourceUpdateCountList}" var="resourceUpdateCount">
										<c:if test='${resourceUpdateCount.key eq "count"}'>
						  					<td class="report_count" colspan="2" align="left">
						  						${resourceUpdateCount.value}
						  					</td>
						  				</c:if>
					  				</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<tr>
					<td class="report_title">历年删除资源数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.resourceDeleteCountList}" var="resourceDeleteCountList" >
							<tr>
								<c:forEach items="${resourceDeleteCountList}" var="resourceDeleteCount">
					  				<c:if test='${resourceDeleteCount.key eq "year"}'>
				  						<td class="report_options" >${resourceDeleteCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.resourceDeleteCountList}" var="resourceDeleteCountList" >
								<tr>
				  					<c:forEach items="${resourceDeleteCountList}" var="resourceDeleteCount">
										<c:if test='${resourceDeleteCount.key eq "count"}'>
						  					<td class="report_count" colspan="2" align="left">
						  						${resourceDeleteCount.value}
						  					</td>
						  				</c:if>
					  				</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<%-- <tr>
					<td class="report_title">当年更新数与资源总数的比值</td>
					统计项
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
							<c:forEach items="${resultMap.resourceCurYearUpdateRateList}" var="resourceCurYearUpdateRateList" >
								<tr>
									<c:forEach items="${resourceCurYearUpdateRateList}" var="resourceCurYearUpdateRate">
						  				<c:if test='${resourceCurYearUpdateRate.key eq "year"}'>
					  						<td class="report_options" >${resourceCurYearUpdateRate.value}</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					统计值
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<tr>
								总量
								<c:forEach items="${resultMap.resourceCurYearUpdateRateList}" var="resourceCurYearUpdateRateList" >
									<c:forEach items="${resourceCurYearUpdateRateList}" var="resourceCurYearUpdateRate">
										<c:if test='${resourceCurYearUpdateRate.key eq "totalCount"}'>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${resourceCurYearUpdateRate.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</tr>
							<c:forEach items="${resultMap.resourceCurYearUpdateRateList}" var="resourceCurYearUpdateRateList" >
								<tr>
									统计数量
									<c:forEach items="${resourceCurYearUpdateRateList}" var="resourceCurYearUpdateRate">
										<c:if test='${resourceCurYearUpdateRate.key eq "count"}'>
						  					<td class="report_count" align="left">
						  						${resourceCurYearUpdateRate.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
				  					统计百分比
				  					<c:forEach items="${resourceCurYearUpdateRateList}" var="resourceCurYearUpdateRate">
										<c:if test='${resourceCurYearUpdateRate.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>qqqq${resourceCurYearUpdateRate.value}</b>
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
				</tr> --%>
				<tr>
					<td align="center" rowspan="5">课程</td>
				</tr>
				<tr>
					<td class="report_title">同比上一年课程更新率</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.courseUpdateRateList}" var="courseUpdateRateList" >
								<tr>
									<c:forEach items="${courseUpdateRateList}" var="resourceCurYearUpdateRate">
						  				<c:if test='${resourceCurYearUpdateRate.key eq "year"}'>
				  						<td class="report_options" >
					  							${resourceCurYearUpdateRate.value}
					  						</td>
					  					</c:if>  
						  			</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.courseUpdateRateList}" var="courseUpdateRateList" >
								<tr>
									<%-- 统计数量 --%>
									<c:forEach items="${courseUpdateRateList}" var="courseUpdateRate">
										<c:if test='${courseUpdateRate.key eq "count"}'>
						  					<td class="report_count" colspan="2" align="left">
						  						${courseUpdateRate.value}
						  					</td>
						  				</c:if>
				  					</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
						以“2015年比2014年更新率”为例，<br/>
						先算出截止到2014年年底创建的课程数量a，再算出截止到2015年年底创建的课程数量b，那么：<br/>
						2015年比2014年更新率=(b-a)/a
					</td>
				</tr>
				<tr>
					<td class="report_title">历年新增课程数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.courseAddCountList}" var="courseAddCountList" >
							<tr>
								<c:forEach items="${courseAddCountList}" var="courseAddCount">
					  				<c:if test='${courseAddCount.key eq "year"}'>
				  						<td class="report_options" >
				  							${courseAddCount.value}
				  						</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.courseAddCountList}" var="courseAddCountList" >
								<tr>
				  					<c:forEach items="${courseAddCountList}" var="courseAddCount">
										<c:if test='${courseAddCount.key eq "count"}'>
						  					<td class="report_count" colspan="2" align="left">
						  						${courseAddCount.value}
						  					</td>
						  				</c:if>
					  				</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<tr>
					<td class="report_title">历年修改课程数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.courseUpdateCountList}" var="courseUpdateCountList" >
							<tr>
								<c:forEach items="${courseUpdateCountList}" var="courseUpdateCount">
					  				<c:if test='${courseUpdateCount.key eq "year"}'>
				  						<td class="report_options" >${courseUpdateCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.courseUpdateCountList}" var="courseUpdateCountList" >
								<tr>
				  					<c:forEach items="${courseUpdateCountList}" var="courseUpdateCount">
										<c:if test='${courseUpdateCount.key eq "count"}'>
						  					<td class="report_count" colspan="2" align="left">
						  						${courseUpdateCount.value}
						  					</td>
						  				</c:if>
					  				</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<tr>
					<td class="report_title">历年删除课程数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.courseDeleteCountList}" var="courseDeleteCountList" >
							<tr>
								<c:forEach items="${courseDeleteCountList}" var="courseDeleteCount">
					  				<c:if test='${courseDeleteCount.key eq "year"}'>
					  					<td class="report_options" style="border-bottom-color: #DDDDDD;" >
				  							${courseDeleteCount.value}
				  						</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.courseDeleteCountList}" var="courseDeleteCountList" >
								<tr>
				  					<c:forEach items="${courseDeleteCountList}" var="courseDeleteCount">
										<c:if test='${courseDeleteCount.key eq "count"}'>
						  					<td class="report_count" align="left" style="border-bottom-color: #DDDDDD;">
						  						${courseDeleteCount.value}
						  					</td>
						  				</c:if>
					  				</c:forEach>
						  		</tr>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
			</table>
		</div>
	</div>
	
</body>
</html>