<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%  
	String fileName = (String)request.getAttribute("fileName");
    response.setCharacterEncoding("UTF-8");  
    response.setContentType("application/vnd.ms-excel");    
    response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(fileName.getBytes("gb2312"), "ISO8859-1" ) + ".xls\"");     
%>
<html>
<head>
	<meta charset="UTF-8" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	
	<style type="text/css">
		#main_table{
			position :center;
			width : 100%;
		}
		#main_table td{
			border: solid;
			border-color: #DDDDDD;
			border-width: 1px;
			height: 45px;
			font-size: 14px;
			text-align:center;
		}
		#main_table thead th{
			border: solid;
			border-color: #DDDDDD;
			border-width: 1px;
			height: 45px;
			text-align:center;
			font-size: 14px;
			color:blue;
		}
		.color1{background-color:#EEE; color:#333;}
		.color2{background-color:#FFF; color:#333;}
	</style>
	
	</head>

<body>
	<div align="center" style="height: 490px;">
		<table id="main_table" cellpadding="0" cellspacing="0" style="width: 98%;border: solid thin #DED9D8" >
			<thead>
				<tr >
					<th align="left" width="15%">
						申报专业名称
					</th>
					<th align="left" width="8%">
						所属专业大类
					</th>
					<th align="center" width="8%">
						所属专业类
					</th>
					<th align="center" width="10%">
						申请单位
					</th>
					<th align="center" width="5%">
						资源总数
					</th>
					<th align="center" width="5%">
						课程数
					</th>
					<th align="center" width="5%">
						用户数
					</th>
					<th align="center" width="5%">
						日志总数
					</th>
					<th align="center" width="5%">
						应用（模块）总数
					</th>
					<th align="center" width="5%">
						题目总数
					</th>
					<th align="center" width="4%">
						主观题总数
					</th>
					<th align="center" width="4%">
						客观题总数
					</th>
					<th align="center" width="4%">
						题目引用数
					</th>
					<th align="center" width="4%">
						题目使用数
					</th>
					<th align="center" width="8%">
						申报日期
					</th>
				</tr>
			</thead>
			<tbody class="zykData">
			<c:if test="${list!=null}">
				<c:forEach items="${list}" var="zykMap">
					<tr>
						<td>
							${zykMap.fullName}
						</td>
						<td>
							<c:if test="${zykMap.specialtyCategory==''}">无</c:if>${zykMap.specialtyCategory }
						</td>
						<td>
							<c:if test="${zykMap.specialtyName==''}">无</c:if>${zykMap.specialtyName}
						</td>
						<td>
							${zykMap.instituteInCharge }
						</td>
						<td>
							${zykMap.resourceCount}
						</td>
						<td>
							${zykMap.courseCount}
						</td>
						<td>
							${zykMap.userCount}
						</td>
						<td>
							${zykMap.logCount}
						</td>
						<td>
							${zykMap.modulesCount}
						</td>
						<td>
							${zykMap.questionCount}
						</td>
						<td>
							${zykMap.subquestionCount}
						</td>
						<td>
							${zykMap.objquestionCount}
						</td>
						<td>
							${zykMap.citiedquestionCount}
						</td>
						<td>
							${zykMap.usingquestionCount}
						</td>
						<td>
							${zykMap.modifiedDate}
						</td>
					</tr>
				</c:forEach>
			</c:if>
			</tbody>
		</table>
	</div>
</body>
</html>