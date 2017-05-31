<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<%@page import="java.util.Calendar"%>  
<%  
	String fileName = (String)request.getAttribute("fileName");
    response.setCharacterEncoding("UTF-8");  
    response.setContentType("application/vnd.ms-excel");    
    response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls\"");     
%>
<!DOCTYPE html>
<html>
<head>
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
			padding-left: 40px;
		}
		
		#main_table .report_rate{
			padding-right: 40px;
			color: #FFA903;
		}
		
		#main_table .report_options{
			padding-left: 30px;
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

<body class="ovreflow1" style="width:100%;height:100%;">
	<div align="center" >
		<div style="width: 98%; padding-top: 10px; padding-bottom: 10px; ">
			<table id="title_table" cellpadding="0" cellspacing="0" style="width: 100%;" >
				<tr>
					<th colspan="5" style="height: 30px; padding-bottom: 10px;" >
						<font style="font-size: 23px;"><b>${zykList[0].fullname} 专业资源库基本报告</b></font>
					</th>
				</tr>
				<tr align="center">
					<td colspan="5" style="font-size: 18px;" >
						<b>资源库编号：
							<c:if test="${empty zykList[0].zykNum}">
								无   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</c:if>
							${zykList[0].zykNum}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   
						</b>
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
					<td align="center" rowspan="11">建设</td>
					<td align="center" rowspan="3">用户</td>
					<td class="report_title">用户总数</td>
							
					<td class="report_content">
						<table style=" width: 100%; border-color: #000000;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.userCountList}" var="userCountList" >
							<c:forEach items="${userCountList}" var="userCount">
				  				<c:if test='${userCount.key eq "role"}'>
									<tr>
				  						<td class="report_options" >
				  							${userCount.value}
				  						</td>
				  					</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
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
								<%-- 统计数量 --%>
								<c:forEach items="${userCountList}" var="userCount">
									<c:if test='${userCount.key eq "count"}'>
									<tr>
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
					  					</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">“用户总数”指的是资源库运行平台用户总数，包括教师、学生、社会学习者、企业用户等类型。文件要求不少于1000人。</td>
				</tr>
				<tr>
					<td class="report_title">用户来自的院校数</td>
					<%-- 统计项 --%>
					<td class="report_content" style="border-top-color: #FFFFFF">
						<table class="report_content_table" style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.userInstituteList}" var="userInstituteList" >
								<c:forEach items="${userInstituteList}" var="userInstitute">
									<c:if test='${userInstitute.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${userInstitute.value}</b>
						  					</td>
										</tr>
						  				</c:if>
				  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">用户来自的省级行政区数</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table class="report_content_table" style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.userProvinceList}" var="userProvinceList" >
								<c:forEach items="${userProvinceList}" var="userProvince">
									<c:if test='${userProvince.key eq "totalCount"}'>
										<tr>
					  						<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
					  							<b>${userProvince.value}</b>
					  						</td>
										</tr>
				  					</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td align="center" rowspan="2">资源</td>
					<td class="report_title">资源素材总数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
							<c:forEach items="${resultMap.resourceCountList}" var="resourceCountList" >
								<c:forEach items="${resourceCountList}" var="resourceCount">
					  				<c:if test='${resourceCount.key eq "media"}'>
										<tr>
					  						<td class="report_options" >${resourceCount.value}</td>
								  		</tr>
				  					</c:if>  
					  			</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.resourceCountList}" var="resourceCountList" >
								<c:forEach items="${resourceCountList}" var="resourceCount">
									<c:if test='${resourceCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${resourceCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<c:forEach items="${resultMap.resourceCountList}" var="resourceCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${resourceCountList}" var="resourceCount">
									<c:if test='${resourceCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" align="left">
						  						${resourceCount.value}
						  					</td>
					  				</c:if>
			  					</c:forEach>
			  					<%-- 统计百分比 --%>
			  					<c:forEach items="${resourceCountList}" var="resourceCount">
									<c:if test='${resourceCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${resourceCount.value}</b>
						  					</td>
								  		</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
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
							<c:forEach items="${instructionCountList}" var="instructionCount">
				  				<c:if test='${instructionCount.key eq "instruction"}'>
									<tr>
				  						<td class="report_options" >${instructionCount.value}</td>
							  		</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					</c:if>
					统计值
					<c:if test="${fn:length(resultMap.instructionCountList) != 1 }">
						<td align="center" class="td_count" >
							<table style="width: 100%" cellpadding="0" cellspacing="0" >
								总量
								<c:forEach items="${resultMap.instructionCountList}" var="instructionCountList" >
									<c:forEach items="${instructionCountList}" var="instructionCount">
										<c:if test='${instructionCount.key eq "totalCount"}'>
											<tr>
							  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
							  						<b>${instructionCount.value}</b>
							  					</td>
											</tr>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
								<c:forEach items="${resultMap.instructionCountList}" var="instructionCountList" >
									统计数量
									<c:forEach items="${instructionCountList}" var="instructionCount">
									<c:if test='${instructionCount.key eq "count"}'>
											<tr>
							  					<td class="report_count" align="left">
							  						${instructionCount.value}
							  					</td>
							  				</c:if>
				  					</c:forEach>
				  					统计百分比
				  					<c:forEach items="${instructionCountList}" var="instructionCount">
										<c:if test='${instructionCount.key eq "rate"}'>
							  					<td class="report_rate" align="right">
							  						<b>${instructionCount.value}</b>
							  					</td>
							  				</tr>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</table>
						</td>
					</c:if>
				</tr> --%>
				<tr>
					<td class="report_title">涉及本专业知识点数</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
							<%-- <c:forEach items="${resultMap.knowledgeCountList}" var="knowledgeCountList" >
								<c:forEach items="${knowledgeCountList}" var="knowledgeCount">
					  				<c:if test='${knowledgeCount.key eq "knowledge"}'>
										<tr>
					  						<td class="report_options" >${knowledgeCount.value}</td>
								  		</tr>
				  					</c:if>  
					  			</c:forEach>
							</c:forEach> --%>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" style="border-top-color: #DDDDDD;" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.knowledgeCountList}" var="knowledgeCountList" >
								<c:forEach items="${knowledgeCountList}" var="knowledgeCount">
									<c:if test='${knowledgeCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903; border-top-color: #FFFFFF;">
						  						<b>${knowledgeCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<%-- <c:forEach items="${resultMap.knowledgeCountList}" var="knowledgeCountList" >
								统计数量
								<c:forEach items="${knowledgeCountList}" var="knowledgeCount">
									<c:if test='${knowledgeCount.key eq "count"}'>
									<tr>
					  					<td class="report_count" style="border-right-color:#FFFFFF;" align="left">
					  						${knowledgeCount.value}
					  					</td>
					  				</c:if>
			  					</c:forEach>
			  					统计百分比
			  					<c:forEach items="${knowledgeCountList}" var="knowledgeCount">
									<c:if test='${knowledgeCount.key eq "rate"}'>
					  					<td class="report_rate" style="border-left-color:#FFFFFF;" align="right">
					  						<b>${knowledgeCount.value}</b>
					  					</td>
							  		</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach> --%>
						</table>
					</td>
					<td class="td_comment">指的是资源素材信息中标注的知识点数量。</td>
				</tr>
				<tr>
					<td align="center" rowspan="5">课程</td>
					<td class="report_title">课程数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr>
							<td class="report_options" style="border-top-color: #DDDDDD;">
								<b>总量</b>
							</td>
			  			</tr>
						</table>
					</td>
					<td class="report_totalCount" align="left" style="border-right-color: #DDDDDD;border-left-color: #DDDDDD;">
						<b>${resultMap.courseCount.totalCount}</b>
					</td>
				
					<%-- <td class="report_title">课程统计</td>
					检验是否有数据
					<c:if test="${fn:length(resultMap.courseLevelCountList) == 0 }">
						<td align="center" colspan="2">无统计数据</td>
					</c:if>
					统计项
					<c:if test="${fn:length(resultMap.courseLevelCountList) != 0 }">
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.courseLevelCountList}" var="courseLevelCountList" >
							<tr>
								<c:forEach items="${courseLevelCountList}" var="courseLevelCount">
					  				<c:if test='${courseLevelCount.key eq "courseLevel"}'>
				  						<td class="report_options" >${courseLevelCount.value}</td>
				  					</c:if>  
					  			</c:forEach>
					  		</tr>
						</c:forEach>
						</table>
					</td>
					</c:if>
					统计值
					<c:if test="${fn:length(resultMap.courseLevelCountList) != 0 }">
						<td align="center" class="td_count" >
							<table style="width: 100%" cellpadding="0" cellspacing="0" >
								<c:forEach items="${resultMap.courseLevelCountList}" var="courseLevelCountList" >
									统计数量
									<c:forEach items="${courseLevelCountList}" var="courseLevelCount">
										<c:if test='${courseLevelCount.key eq "count"}'>
											<tr>
							  					<td class="report_count" colspan="2" align="left">
							  						${courseLevelCount.value}
							  					</td>
						  					</tr>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</table>
						</td>
					</c:if> --%>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">不同类型课程分布 </td>
					<td class="report_content" style="border-bottom-color: #FFFFFF">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.courseTypeCountList}" var="courseTypeCountList" >
							<c:forEach items="${courseTypeCountList}" var="courseTypeCount">
				  				<c:if test='${courseTypeCount.key eq "courseType"}'>
									<tr>
				  						<td class="report_options" >
				  							${courseTypeCount.value}
				  						</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count">
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.courseTypeCountList}" var="courseTypeCountList" >
								<c:forEach items="${courseTypeCountList}" var="courseTypeCount">
									<c:if test='${courseTypeCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" >
						  						<b>${courseTypeCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<c:forEach items="${resultMap.courseTypeCountList}" var="courseTypeCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${courseTypeCountList}" var="courseTypeCount">
									<c:if test='${courseTypeCount.key eq "count"}'>
										<tr>
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
						  				</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">文件要求专业核心课不少于6门。</td>
				</tr>
				<tr>
					<td class="report_title">不同层级课程分布</td>
					<td class="report_content" style="border-bottom-color: #FFFFFF">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.courseLevelCountList}" var="courseLevelCountList" >
							<c:forEach items="${courseLevelCountList}" var="courseLevelCount">
				  				<c:if test='${courseLevelCount.key eq "courseLevel"}'>
									<tr>
				  						<td class="report_options" >
				  							${courseLevelCount.value}
				  						</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count">
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.courseLevelCountList}" var="courseLevelCountList" >
								<c:forEach items="${courseLevelCountList}" var="courseLevelCount">
									<c:if test='${courseLevelCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" >
						  						<b>${courseLevelCount.value}</b>
						  					</td>
										</tr>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							<c:forEach items="${resultMap.courseLevelCountList}" var="courseLevelCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${courseLevelCountList}" var="courseLevelCount">
									<c:if test='${courseLevelCount.key eq "count"}'>
										<tr>
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
							  			</tr>
					  				</c:if>
			  					</c:forEach>
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
							<%-- <c:forEach items="${resultMap.courseModuleCountList}" var="courseModuleCountList" >
								<c:forEach items="${courseModuleCountList}" var="courseModuleCount">
					  				<c:if test='${courseModuleCount.key eq "courseFullname"}'>
										<tr>
					  						<td class="report_options" >${courseModuleCount.value}</td>
								  		</tr>
				  					</c:if>  
					  			</c:forEach>
							</c:forEach> --%>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.courseModuleCountList}" var="courseModuleCountList" >
								<c:forEach items="${courseModuleCountList}" var="courseModuleCount">
									<c:if test='${courseModuleCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${courseModuleCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<%-- <c:forEach items="${resultMap.courseModuleCountList}" var="courseModuleCountList" >
								统计数量
								<c:forEach items="${courseModuleCountList}" var="courseModuleCount">
									<c:if test='${courseModuleCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${courseModuleCount.value}
						  					</td>
					  				</c:if>
			  					</c:forEach>
			  					统计百分比
			  					<c:forEach items="${courseModuleCountList}" var="courseModuleCount">
									<c:if test='${courseModuleCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${courseModuleCount.value}</b>
						  					</td>
							  			</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach> --%>
						</table>
					</td>
					<td class="td_comment">指的是资源库运行平台中所有课程各种学习模块的总数。</td>
				</tr>
				<tr>
					<td class="report_title">课程引用的资源素材占所有资源素材比</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<!-- <tr><td class="report_options"><b>总量</b></td></tr> -->
						<c:forEach items="${resultMap.courseResourceCountList}" var="courseResourceCountList" >
							<c:forEach items="${courseResourceCountList}" var="courseResourceCount">
				  				<c:if test='${courseResourceCount.key eq "resource"}'>
									<tr>
				  						<td class="report_options" >${courseResourceCount.value}</td>
							  		</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
						<%-- 总量 --%>
						<%-- <c:forEach items="${resultMap.courseResourceCountList}" var="courseResourceCountList" >
							<c:forEach items="${courseResourceCountList}" var="courseResourceCount">
								<c:if test='${courseResourceCount.key eq "totalCount"}'>
									<tr>
					  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
					  						<b>${courseResourceCount.value}</b>
					  					</td>
									</tr>
				  				</c:if>
		  					</c:forEach>
						</c:forEach> --%>
						<c:forEach items="${resultMap.courseResourceCountList}" var="courseResourceCountList" >
							<%-- 统计数量 --%>
							<c:forEach items="${courseResourceCountList}" var="courseResourceCount">
								<c:if test='${courseResourceCount.key eq "count"}'>
									<tr>
					  					<td class="report_count" colspan="2" style="border-right-color:#FFFFFF;" align="left">
					  						${courseResourceCount.value}
					  					</td>
					  				</c:if>
		  					</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td class="td_comment">资源库运行平台中课程的各学习模块中出现的资源素材即为课程引用的资源素材。</td>
				</tr>
				<tr>
					<td align="center">题库</td>
					<td class="report_title">题库题目总数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr>
							<td class="report_options" style="border-top-color: #DDDDDD;">
								<b>总量</b>
							</td>
			  			</tr>
						</table>
					</td>
					<td class="report_totalCount" align="left" style="color: #FFA903; border-right-color: #DDDDDD;border-left-color: #DDDDDD;">
							 <b>${resultMap.totalQuestionCount.count}</b>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td align="center" rowspan="13">应用</td>
					<td align="center" rowspan="2">用户</td>
					<td class="report_title">用户行为活动总量</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.userBehaviourCountList}" var="userBehaviourCountList" >
							<c:forEach items="${userBehaviourCountList}" var="userBehaviourCount">
				  				<c:if test='${userBehaviourCount.key eq "behaviour"}'>
									<tr>
				  						<td class="report_options" >${userBehaviourCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.userBehaviourCountList}" var="userBehaviourCountList" >
								<c:forEach items="${userBehaviourCountList}" var="userBehaviourCount">
									<c:if test='${userBehaviourCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${userBehaviourCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<c:forEach items="${resultMap.userBehaviourCountList}" var="userBehaviourCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${userBehaviourCountList}" var="userBehaviourCount">
									<c:if test='${userBehaviourCount.key eq "count"}'>
										<tr>
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
						  				</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">“用户行为活动总量”及其下的资源库运行平台各模块活动量反映了用户活跃度。文件要求用户活跃度高，开展了授课、布置作业、考试、学习等活动。</td>
				</tr>
				<tr>
					<td class="report_title">注册用户占全部用户比例</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<!-- <tr><td class="report_options"><b>总量</b></td></tr> -->
						<c:forEach items="${resultMap.userActiveLogCountList}" var="userActiveLogCountList" >
							<c:forEach items="${userActiveLogCountList}" var="userActiveLogCount">
				  				<c:if test='${userActiveLogCount.key eq "active"}'>
									<tr>
					  					<td class="report_options" >${userActiveLogCount.value}</td>
						  			</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.userActiveLogCountList}" var="userActiveLogCountList" >
								<c:forEach items="${userActiveLogCountList}" var="userActiveLogCount">
									<c:if test='${userActiveLogCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${userActiveLogCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<c:forEach items="${resultMap.userActiveLogCountList}" var="userActiveLogCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${userActiveLogCountList}" var="userActiveLogCount">
									<c:if test='${userActiveLogCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
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
						  				</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">“注册用户活动情况”反映了用户活跃度。</td>
				</tr>
				<%-- <tr>
					<td class="report_title">用户累计学习天数</td>
					检验是否有数据
					<c:if test="${fn:length(resultMap.userStudyDateCountList) == 1 }">
						<td align="center" colspan="2">无统计数据</td>
					</c:if>
					统计项
					<c:if test="${fn:length(resultMap.userStudyDateCountList) != 1 }">
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.userStudyDateCountList}" var="userStudyDateCountList" >
							<c:forEach items="${userStudyDateCountList}" var="userStudyDateCount">
				  				<c:if test='${userStudyDateCount.key eq "role"}'>
									<tr>
					  						<td class="report_options" >${userStudyDateCount.value}</td>
							  		</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					</c:if>
					统计值
					<c:if test="${fn:length(resultMap.userStudyDateCountList) != 1 }">
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							总量
							<c:forEach items="${resultMap.userStudyDateCountList}" var="userStudyDateCountList" >
								<c:forEach items="${userStudyDateCountList}" var="userStudyDateCount">
									<c:if test='${userStudyDateCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${userStudyDateCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<c:forEach items="${resultMap.userStudyDateCountList}" var="userStudyDateCountList" >
								统计数量
								<c:forEach items="${userStudyDateCountList}" var="userStudyDateCount">
									<c:if test='${userStudyDateCount.key eq "count"}'>
										<tr>
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
						  				</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					</c:if>
				</tr> --%>
				<%-- <tr>
					<td class="report_title">用户活动总次数</td>
					检验是否有数据
					<c:if test="${fn:length(resultMap.userActiveCountList) == 1 }">
						<td align="center" colspan="2">无统计数据</td>
					</c:if>
					统计项
					<c:if test="${fn:length(resultMap.userActiveCountList) != 1 }">
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.userActiveCountList}" var="userActiveCountList" >
							<c:forEach items="${userActiveCountList}" var="userStudyDateCount">
				  				<c:if test='${userStudyDateCount.key eq "role"}'>
									<tr>
				  						<td class="report_options" >${userStudyDateCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					</c:if>
					统计值
					<c:if test="${fn:length(resultMap.userActiveCountList) != 1 }">
						<td align="center" class="td_count" >
							<table style="width: 100%" cellpadding="0" cellspacing="0" >
								总量
								<c:forEach items="${resultMap.userActiveCountList}" var="userActiveCountList" >
									<c:forEach items="${userActiveCountList}" var="userActiveCount">
										<c:if test='${userActiveCount.key eq "totalCount"}'>
											<tr>
							  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
							  						<b>${userActiveCount.value}</b>
							  					</td>
											</tr>
							  				</c:if>
					  					</c:forEach>
								</c:forEach>
								<c:forEach items="${resultMap.userActiveCountList}" var="userActiveCountList" >
									统计数量
									<c:forEach items="${userActiveCountList}" var="userActiveCount">
										<c:if test='${userActiveCount.key eq "count"}'>
											<tr>
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
						  					</tr>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</table>
						</td>
					</c:if>
				</tr> --%>
				<%-- <tr>
					<td class="report_title">人均活动天数</td>
					检验是否有数据
					<c:if test="${fn:length(resultMap.userActiveDateAvgCountList) == 0 }">
						<td align="center" colspan="2">无统计数据</td>
					</c:if>
					统计项
					<c:if test="${fn:length(resultMap.userActiveDateAvgCountList) != 0 }">
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.userActiveDateAvgCountList}" var="userActiveDateAvgCountList" >
							<c:forEach items="${userActiveDateAvgCountList}" var="userActiveDateAvgCount">
				  				<c:if test='${userActiveDateAvgCount.key eq "active"}'>
									<tr>
				  						<td class="report_options" >${userActiveDateAvgCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					</c:if>
					统计值
					<c:if test="${fn:length(resultMap.userActiveDateAvgCountList) != 0 }">
						<td align="center" class="td_count" >
							<table style="width: 100%" cellpadding="0" cellspacing="0" >
								<c:forEach items="${resultMap.userActiveDateAvgCountList}" var="userActiveDateAvgCountList" >
									<tr>
										统计数量
										<c:forEach items="${userActiveDateAvgCountList}" var="userActiveDateAvgCount">
											<c:if test='${userActiveDateAvgCount.key eq "count"}'>
							  					<td class="report_count" colspan="2" align="left">
							  						${userActiveDateAvgCount.value}
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
					<td align="center" rowspan="5">资源</td>
					<td class="report_title">资源素材使用总次数</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
						<%-- <c:forEach items="${resultMap.resourceUsingCountList}" var="resourceUsingCountList" >
							<c:forEach items="${resourceUsingCountList}" var="resourceUsingCount">
				  				<c:if test='${resourceUsingCount.key eq "action"}'>
									<tr>
				  						<td class="report_options" >${resourceUsingCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach> --%>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.resourceUsingCountList}" var="resourceUsingCountList" >
								<c:forEach items="${resourceUsingCountList}" var="resourceUsingCount">
									<c:if test='${resourceUsingCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${resourceUsingCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<%-- <c:forEach items="${resultMap.resourceUsingCountList}" var="resourceUsingCountList" >
								统计数量
								<c:forEach items="${resourceUsingCountList}" var="resourceUsingCount">
									<c:if test='${resourceUsingCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" align="left">
						  						${resourceUsingCount.value}
						  					</td>
					  				</c:if>
			  					</c:forEach>
			  					统计百分比
			  					<c:forEach items="${resourceUsingCountList}" var="resourceUsingCount">
									<c:if test='${resourceUsingCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${resourceUsingCount.value}</b>
						  					</td>
						  				</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach> --%>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">资源素材应用情况</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.resourceUsingActionCountList}" var="resourceUsingActionCountList" >
							<c:forEach items="${resourceUsingActionCountList}" var="resourceUsingActionCount">
				  				<c:if test='${resourceUsingActionCount.key eq "action"}'>
									<tr>
				  						<td class="report_options" >${resourceUsingActionCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.resourceUsingActionCountList}" var="resourceUsingActionCountList" >
								<c:forEach items="${resourceUsingActionCountList}" var="resourceUsingActionCount">
									<c:if test='${resourceUsingActionCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${resourceUsingActionCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<c:forEach items="${resultMap.resourceUsingActionCountList}" var="resourceUsingActionCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${resourceUsingActionCountList}" var="resourceUsingActionCount">
									<c:if test='${resourceUsingActionCount.key eq "count"}'>
										<tr>
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
						  				</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">不同文件类型资源素材浏览和下载频次</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.resourceUsingAndDownloadCountList}" var="resourceUsingAndDownloadCountList" >
							<c:forEach items="${resourceUsingAndDownloadCountList}" var="resourceUsingAndDownloadCount">
				  				<c:if test='${resourceUsingAndDownloadCount.key eq "media"}'>
									<tr>
				  						<td class="report_options" >${resourceUsingAndDownloadCount.value}</td>
					  				</tr>
				  					</c:if>  
					  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.resourceUsingAndDownloadCountList}" var="resourceUsingAndDownloadCountList" >
								<c:forEach items="${resourceUsingAndDownloadCountList}" var="resourceUsingAndDownloadCount">
									<c:if test='${resourceUsingAndDownloadCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${resourceUsingAndDownloadCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<c:forEach items="${resultMap.resourceUsingAndDownloadCountList}" var="resourceUsingAndDownloadCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${resourceUsingAndDownloadCountList}" var="resourceUsingAndDownloadCount">
									<c:if test='${resourceUsingAndDownloadCount.key eq "count"}'>
										<tr>
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
						  				</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">使用的资源在全部资源的占比</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<!-- <tr><td class="report_options"><b>总量</b></td></tr> -->
						<c:forEach items="${resultMap.resourceUsingRateCountList}" var="resourceUsingRateCountList" >
							<c:forEach items="${resourceUsingRateCountList}" var="resourceUsingRateCount">
				  				<c:if test='${resourceUsingRateCount.key eq "action"}'>
									<tr>
				  						<td class="report_options" >${resourceUsingRateCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.resourceUsingRateCountList}" var="resourceUsingRateCountList" >
								<c:forEach items="${resourceUsingRateCountList}" var="resourceUsingRateCount">
									<c:if test='${resourceUsingRateCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${resourceUsingRateCount.value}</b>
						  					</td>
										</tr>
						  				</c:if>
				  					</c:forEach>
							</c:forEach>
							<c:forEach items="${resultMap.resourceUsingRateCountList}" var="resourceUsingRateCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${resourceUsingRateCountList}" var="resourceUsingRateCount">
									<c:if test='${resourceUsingRateCount.key eq "count"}'>
										<tr>
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
						  				</tr>
					  				</c:if>
			  					</c:forEach> --%>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">平均每个资源使用次数</td>
					<%-- 检验是否有数据 --%>
					<%-- <c:if test="${fn:length(resultMap.resourceUsingAvgCountList) == 0 }">
						<td align="center" colspan="2">无统计数据</td>
					</c:if> --%>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.resourceUsingAvgCountList}" var="resourceUsingAvgCountList" >
								<c:forEach items="${resourceUsingAvgCountList}" var="resourceUsingAvgCount">
					  				<c:if test='${resourceUsingAvgCount.key eq "media"}'>
										<tr>
					  						<td class="report_options" >${resourceUsingAvgCount.value}</td>
						  				</tr>
				  					</c:if>  
					  			</c:forEach>
							</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<%-- <c:forEach items="${resultMap.resourceUsingAvgCountList}" var="resourceUsingAvgCountList" >
								<c:forEach items="${resourceUsingAvgCountList}" var="resourceUsingAvgCount">
									<c:if test='${resourceUsingAvgCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${resourceUsingAvgCount.value}</b>
						  					</td>
										</tr>
						  				</c:if>
				  					</c:forEach>
							</c:forEach> --%>
							<c:forEach items="${resultMap.resourceUsingAvgCountList}" var="resourceUsingAvgCountList" >
								<%-- 统计数量 --%>
								<%-- c:forEach items="${resourceUsingAvgCountList}" var="resourceUsingAvgCount">
									<c:if test='${resourceUsingAvgCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" align="left">
						  						${resourceUsingAvgCount.value}
						  					</td>
						  				</c:if>
				  					</c:forEach> --%>
				  					<%-- 平均值 --%>
				  					<c:forEach items="${resourceUsingAvgCountList}" var="resourceUsingAvgCount">
										<c:if test='${resourceUsingAvgCount.key eq "avgCount"}'>
						  					<td class="report_count" colspan="2" align="left">
						  						${resourceUsingAvgCount.value}
						  					</td>
						  				</c:if>
					  				</c:forEach>
				  					<%-- 统计百分比 --%>
				  					<%-- <c:forEach items="${resourceUsingAvgCountList}" var="resourceUsingAvgCount">
										<c:if test='${resourceUsingAvgCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${resourceUsingAvgCount.value}</b>
						  					</td>
						  				</tr>
					  				</c:if>
			  					</c:forEach> --%>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td align="center" rowspan="1">课程</td>
					<%-- <td class="report_title">课程访问次数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
							<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.courseVisitCountList}" var="courseVisitCountList" >
							<c:forEach items="${courseVisitCountList}" var="courseVisitCount">
				  				<c:if test='${courseVisitCount.key eq "fullname"}'>
									<tr>
				  						<td class="report_options" >${courseVisitCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					统计值
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							总量
							<c:forEach items="${resultMap.courseVisitCountList}" var="courseVisitCountList" >
								<c:forEach items="${courseVisitCountList}" var="courseVisitCount">
									<c:if test='${courseVisitCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${courseVisitCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<c:forEach items="${resultMap.courseVisitCountList}" var="courseVisitCountList" >
								统计数量
								<c:forEach items="${courseVisitCountList}" var="courseVisitCount">
									<c:if test='${courseVisitCount.key eq "count"}'>
										<tr>
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
						  				</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr> --%>
				
					<td class="report_title">各个课程的访问情况(前三)</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.courseVisitTop3CountList}" var="courseVisitTop3CountList" >
							<c:forEach items="${courseVisitTop3CountList}" var="courseVisitTop3Count">
				  				<c:if test='${courseVisitTop3Count.key eq "fullname"}'>
									<tr>
				  						<td class="report_options" >${courseVisitTop3Count.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.courseVisitTop3CountList}" var="courseVisitTop3CountList" >
								<c:forEach items="${courseVisitTop3CountList}" var="courseVisitTop3Count">
									<c:if test='${courseVisitTop3Count.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${courseVisitTop3Count.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<c:forEach items="${resultMap.courseVisitTop3CountList}" var="courseVisitTop3CountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${courseVisitTop3CountList}" var="courseVisitTop3Count">
									<c:if test='${courseVisitTop3Count.key eq "count"}'>
										<tr>
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
					  					</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td align="center" rowspan="2">题库</td>
					<td class="report_title">题库题目引用数</td>
					<%-- 统计项 --%>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<%-- <c:forEach items="${resultMap.questionQuesCountList}" var="questionQuesCountList" >
							<c:forEach items="${questionQuesCountList}" var="questionQuesCount">
				  				<c:if test='${questionQuesCount.key eq "title"}'>
									<tr>
				  						<td class="report_options" >${questionQuesCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach> --%>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.questionQuesCountList}" var="questionQuesCountList" >
								<c:forEach items="${questionQuesCountList}" var="questionQuesCount">
									<c:if test='${questionQuesCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${questionQuesCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
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
						<%-- <c:forEach items="${resultMap.questionUsingCountList}" var="questionUsingCountList" >
							<c:forEach items="${questionUsingCountList}" var="questionUsingCount">
				  				<c:if test='${questionUsingCount.key eq "title"}'>
									<tr>
				  						<td class="report_options" >${questionUsingCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach> --%>
						</table>
					</td>
					<%-- 统计值 --%>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.questionUsingCountList}" var="questionUsingCountList" >
								<c:forEach items="${questionUsingCountList}" var="questionUsingCount">
									<c:if test='${questionUsingCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${questionUsingCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<%-- <c:forEach items="${resultMap.questionUsingCountList}" var="questionUsingCountList" >
								统计数量
								<c:forEach items="${questionUsingCountList}" var="questionUsingCount">
									<c:if test='${questionUsingCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" align="left">
						  						${questionUsingCount.value}
						  					</td>
						  				</c:if>
			  					</c:forEach>
			  					统计百分比
			  					<c:forEach items="${questionUsingCountList}" var="questionUsingCount">
									<c:if test='${questionUsingCount.key eq "rate"}'>
						  					<td class="report_rate" align="right">
						  						<b>${questionUsingCount.value}</b>
						  					</td>
					  					</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach> --%>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td align="center" rowspan="3">学习</td>
					<td class="report_title">论坛活动模块统计</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.forumActiveCountList}" var="forumActiveCountList" >
							<c:forEach items="${forumActiveCountList}" var="workActiveCount">
				  				<c:if test='${workActiveCount.key eq "action"}'>
									<tr>
				  						<td class="report_options" >${workActiveCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.forumActiveCountList}" var="forumActiveCountList" >
								<c:forEach items="${forumActiveCountList}" var="forumActiveCount">
									<c:if test='${forumActiveCount.key eq "totalCount"}'>
										<tr>
							  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
							  						<b>${forumActiveCount.value}</b>
							  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<c:forEach items="${resultMap.forumActiveCountList}" var="forumActiveCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${forumActiveCountList}" var="forumActiveCount">
									<c:if test='${forumActiveCount.key eq "count"}'>
										<tr>
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
						  				</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">作业活动模块统计</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.workActiveCountList}" var="workActiveCountList" >
							<c:forEach items="${workActiveCountList}" var="workActiveCount">
				  				<c:if test='${workActiveCount.key eq "work"}'>
									<tr>
				  						<td class="report_options" >${workActiveCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.workActiveCountList}" var="workActiveCountList" >
								<c:forEach items="${workActiveCountList}" var="workActiveCount">
									<c:if test='${workActiveCount.key eq "totalCount"}'>
										<tr>
						  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
						  						<b>${workActiveCount.value}</b>
						  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<c:forEach items="${resultMap.workActiveCountList}" var="workActiveCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${workActiveCountList}" var="workActiveCount">
									<c:if test='${workActiveCount.key eq "count"}'>
										<tr>
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
					  					</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td class="report_title">考试活动模块统计</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.examActiveCountList}" var="examActiveCountList" >
							<c:forEach items="${examActiveCountList}" var="examActiveCount">
				  				<c:if test='${examActiveCount.key eq "exam"}'>
									<tr>
				  						<td class="report_options" >${examActiveCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<%-- 总量 --%>
							<c:forEach items="${resultMap.examActiveCountList}" var="examActiveCountList" >
								<c:forEach items="${examActiveCountList}" var="examActiveCount">
									<c:if test='${examActiveCount.key eq "totalCount"}'>
										<tr>
							  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
							  						<b>${examActiveCount.value}</b>
							  					</td>
										</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
							<c:forEach items="${resultMap.examActiveCountList}" var="examActiveCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${examActiveCountList}" var="examActiveCount">
									<c:if test='${examActiveCount.key eq "count"}'>
										<tr>
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
						  				</tr>
					  				</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment"></td>
				</tr>
				<tr>
					<td align="center" rowspan="13">更新</td>
					<td align="center" rowspan="5" >用户</td>
					<td class="report_title">同比上一年用户注册增长率</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.userIncreaseRateList}" var="userIncreaseRateList" >
							<c:forEach items="${userIncreaseRateList}" var="userIncreaseRate">
				  				<c:if test='${userIncreaseRate.key eq "year"}'>
									<tr>
				  						<td class="report_options" >${userIncreaseRate.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userIncreaseRateList}" var="userIncreaseRateList" >
								<%-- 统计数量 --%>
								<c:forEach items="${userIncreaseRateList}" var="userIncreaseRate">
									<c:if test='${userIncreaseRate.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${userIncreaseRate.value}
						  					</td>
					  					</tr>
				  					</c:if>
			  					</c:forEach>
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
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.userModifiedRateList}" var="userModifiedRateList" >
							<c:forEach items="${userModifiedRateList}" var="userModifiedRate">
				  				<c:if test='${userModifiedRate.key eq "year"}'>
									<tr>
				  						<td class="report_options" >${userModifiedRate.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userModifiedRateList}" var="userModifiedRateList" >
								<%-- 统计数量 --%>
								<c:forEach items="${userModifiedRateList}" var="userModifiedRate">
									<c:if test='${userModifiedRate.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${userModifiedRate.value}
						  					</td>
					  					</tr>
				  					</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<tr>
					<td class="report_title">历年新增用户数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.userAddCountList}" var="userAddCountList" >
							<c:forEach items="${userAddCountList}" var="userAddCount">
				  				<c:if test='${userAddCount.key eq "year"}'>
									<tr>
				  						<td class="report_options" >${userAddCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userAddCountList}" var="userAddCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${userAddCountList}" var="userAddCount">
									<c:if test='${userAddCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${userAddCount.value}
						  					</td>
					  					</tr>
				  					</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<tr>
					<td class="report_title">历年修改用户数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.userUpdateCountList}" var="userUpdateCountList" >
							<c:forEach items="${userUpdateCountList}" var="userUpdateCount">
				  				<c:if test='${userUpdateCount.key eq "year"}'>
									<tr>
				  						<td class="report_options" >${userUpdateCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userUpdateCountList}" var="userUpdateCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${userUpdateCountList}" var="userUpdateCount">
									<c:if test='${userUpdateCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${userUpdateCount.value}
						  					</td>
					  					</tr>
				  					</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<tr>
					<td class="report_title">历年删除用户数</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.userDeleteCountList}" var="userDeleteCountList" >
							<c:forEach items="${userDeleteCountList}" var="userDeleteCount">
				  				<c:if test='${userDeleteCount.key eq "year"}'>
									<tr>
				  						<td class="report_options" >${userDeleteCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.userDeleteCountList}" var="userDeleteCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${userDeleteCountList}" var="userDeleteCount">
									<c:if test='${userDeleteCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${userDeleteCount.value}
						  					</td>
					  					</tr>
				  					</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<tr>
					<td align="center" rowspan="4">资源</td>
					<td class="report_title">同比上一年资源更新率</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.resourceUpdateRateList}" var="resourceUpdateRateList" >
							<c:forEach items="${resourceUpdateRateList}" var="resourceUpdateRate">
				  				<c:if test='${resourceUpdateRate.key eq "year"}'>
									<tr>
				  						<td class="report_options" >${resourceUpdateRate.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.resourceUpdateRateList}" var="resourceUpdateRateList" >
								<%-- 统计数量 --%>
								<c:forEach items="${resourceUpdateRateList}" var="resourceUpdateRate">
									<c:if test='${resourceUpdateRate.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${resourceUpdateRate.value}
						  					</td>
						  				</tr>
					  				</c:if>
			  					</c:forEach>
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
							<c:forEach items="${resourceAddCountList}" var="resourceAddCount">
				  				<c:if test='${resourceAddCount.key eq "year"}'>
									<tr>
				  						<td class="report_options" >${resourceAddCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.resourceAddCountList}" var="resourceAddCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${resourceAddCountList}" var="resourceAddCount">
									<c:if test='${resourceAddCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${resourceAddCount.value}
						  					</td>
					  					</tr>
				  					</c:if>
			  					</c:forEach>
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
							<c:forEach items="${resourceUpdateCountList}" var="resourceUpdateCount">
				  				<c:if test='${resourceUpdateCount.key eq "year"}'>
									<tr>
				  						<td class="report_options" >${resourceUpdateCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.resourceUpdateCountList}" var="resourceUpdateCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${resourceUpdateCountList}" var="resourceUpdateCount">
									<c:if test='${resourceUpdateCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${resourceUpdateCount.value}
						  					</td>
					  					</tr>
				  					</c:if>
			  					</c:forEach>
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
							<c:forEach items="${resourceDeleteCountList}" var="resourceDeleteCount">
				  				<c:if test='${resourceDeleteCount.key eq "year"}'>
									<tr>
				  						<td class="report_options" >${resourceDeleteCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.resourceDeleteCountList}" var="resourceDeleteCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${resourceDeleteCountList}" var="resourceDeleteCount">
									<c:if test='${resourceDeleteCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${resourceDeleteCount.value}
						  					</td>
					  					</tr>
				  					</c:if>
			  					</c:forEach>
							</c:forEach>
						</table>
					</td>
					<td class="td_comment">
					</td>
				</tr>
				<%-- <tr>
					<td class="report_title">当年更新数与资源总数的比值</td>
					检验是否有数据
					<c:if test="${fn:length(resultMap.resourceCurYearUpdateRateList) == 0 }">
						<td align="center" colspan="2">无统计数据</td>
					</c:if>
					统计项
					<c:if test="${fn:length(resultMap.resourceCurYearUpdateRateList) != 0 }">
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<tr><td class="report_options"><b>总量</b></td></tr>
						<c:forEach items="${resultMap.resourceCurYearUpdateRateList}" var="resourceCurYearUpdateRateList" >
							<c:forEach items="${resourceCurYearUpdateRateList}" var="resourceCurYearUpdateRate">
				  				<c:if test='${resourceCurYearUpdateRate.key eq "year"}'>
									<tr>
				  						<td class="report_options" >${resourceCurYearUpdateRate.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					</c:if>
					统计值
					<c:if test="${fn:length(resultMap.resourceCurYearUpdateRateList) != 0 }">
						<td align="center" class="td_count" >
							<table style="width: 100%" cellpadding="0" cellspacing="0" >
								总量
								<c:forEach items="${resultMap.resourceCurYearUpdateRateList}" var="resourceCurYearUpdateRateList" >
									<c:forEach items="${resourceCurYearUpdateRateList}" var="resourceCurYearUpdateRate">
										<c:if test='${resourceCurYearUpdateRate.key eq "totalCount"}'>
											<tr>
							  					<td class="report_totalCount" colspan="2" align="left" style="color: #FFA903">
							  						<b>${resourceCurYearUpdateRate.value}</b>
							  					</td>
											</tr>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
								<c:forEach items="${resultMap.resourceCurYearUpdateRateList}" var="resourceCurYearUpdateRateList" >
									统计数量
									<c:forEach items="${resourceCurYearUpdateRateList}" var="resourceCurYearUpdateRate">
										<c:if test='${resourceCurYearUpdateRate.key eq "count"}'>
											<tr>
							  					<td class="report_count" align="left" colspan="2">
							  						${resourceCurYearUpdateRate.value}
							  					</td>
						  					</tr>
						  				</c:if>
				  					</c:forEach>
								</c:forEach>
							</table>
						</td>
					</c:if>
				</tr> --%>
				<tr>
					<td align="center" rowspan="4">课程</td>
					<td class="report_title">同比上一年课程更新率</td>
					<td class="report_content">
						<table style=" width: 100%;" cellpadding="0" cellspacing="0" >
						<c:forEach items="${resultMap.courseUpdateRateList}" var="courseUpdateRateList" >
							<c:forEach items="${courseUpdateRateList}" var="resourceCurYearUpdateRate">
				  				<c:if test='${resourceCurYearUpdateRate.key eq "year"}'>
									<tr>
				  						<td class="report_options" style="border-bottom-color: #DDDDDD;" >${resourceCurYearUpdateRate.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.courseUpdateRateList}" var="courseUpdateRateList" >
								<%-- 统计数量 --%>
								<c:forEach items="${courseUpdateRateList}" var="courseUpdateRate">
									<c:if test='${courseUpdateRate.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${courseUpdateRate.value}
						  					</td>
					  					</tr>
					  				</c:if>
			  					</c:forEach>
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
							<c:forEach items="${courseAddCountList}" var="courseAddCount">
				  				<c:if test='${courseAddCount.key eq "year"}'>
									<tr>
				  						<td class="report_options" >${courseAddCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.courseAddCountList}" var="courseAddCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${courseAddCountList}" var="courseAddCount">
									<c:if test='${courseAddCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${courseAddCount.value}
						  					</td>
					  					</tr>
				  					</c:if>
			  					</c:forEach>
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
							<c:forEach items="${courseUpdateCountList}" var="courseUpdateCount">
				  				<c:if test='${courseUpdateCount.key eq "year"}'>
									<tr>
				  						<td class="report_options" >${courseUpdateCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.courseUpdateCountList}" var="courseUpdateCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${courseUpdateCountList}" var="courseUpdateCount">
									<c:if test='${courseUpdateCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${courseUpdateCount.value}
						  					</td>
					  					</tr>
				  					</c:if>
			  					</c:forEach>
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
							<c:forEach items="${courseDeleteCountList}" var="courseDeleteCount">
				  				<c:if test='${courseDeleteCount.key eq "year"}'>
									<tr>
				  						<td class="report_options" >${courseDeleteCount.value}</td>
					  				</tr>
			  					</c:if>  
				  			</c:forEach>
						</c:forEach>
						</table>
					</td>
					<td align="center" class="td_count" >
						<table style="width: 100%" cellpadding="0" cellspacing="0" >
							<c:forEach items="${resultMap.courseDeleteCountList}" var="courseDeleteCountList" >
								<%-- 统计数量 --%>
								<c:forEach items="${courseDeleteCountList}" var="courseDeleteCount">
									<c:if test='${courseDeleteCount.key eq "count"}'>
										<tr>
						  					<td class="report_count" colspan="2" align="left">
						  						${courseDeleteCount.value}
						  					</td>
					  					</tr>
				  					</c:if>
			  					</c:forEach>
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