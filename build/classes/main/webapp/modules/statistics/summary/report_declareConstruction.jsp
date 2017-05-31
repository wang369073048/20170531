<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>

<html>
<head>
	<meta charset="UTF-8" />
	<link href="${pageContext.request.contextPath}/css/mainstyle.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/easyui.css" rel="stylesheet" type="text/css" />
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
	
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
		var index = 0;
		$(function(){
			$(".zykData tr:odd").addClass("color1");
			$(".zykData tr:even").addClass("color2");
			var status = $('#selected').val();
			$(".findData").click(function(){
				index++;
				$.ajax({
					url:path+'/report_summary/declareConstruction.action',
					data:{"pageIndex":index,count:10,"status":status},
					dataType:'text',	
					success:function(data){
						if(data){
							var data = eval("("+data+")");
							var len = data.length;
							if(len>0){
								var sr = "";
								for(var i = 0 ; i < data.length; i++){
									sr += "<tr>";
									var zyk = data[i];
									sr += "<td>"+zyk.fullName+"</td><td>"+(zyk.specialtyCategory==""?"无":zyk.specialtyCategory)+"</td>";
									sr += "<td>"+(zyk.specialtyName==""?"无":zyk.specialtyName)+"</td><td>"+zyk.instituteInCharge+"</td>";
									sr += "<td>"+zyk.resourceCount+"</td><td>"+zyk.courseCount+"</td>";
									sr += "<td>"+zyk.userCount+"</td><td>"+zyk.logCount+"</td>";
									sr += "<td>"+zyk.modulesCount+"</td><td>"+zyk.questionCount+"</td>";
									sr += "<td>"+zyk.subquestionCount+"</td><td>"+zyk.objquestionCount+"</td>";
									sr += "<td>"+zyk.citiedquestionCount+"</td><td>"+zyk.usingquestionCount+"</td>";
									sr += "<td>"+(zyk.modifiedDate==null?"":zyk.modifiedDate)+"</td>";
									sr += "</tr>";
								}
								//$(".zykData").prepend(sr);
								$(".zykData").append(sr);
								$(".zykData tr:odd").addClass("color1");
								$(".zykData tr:even").addClass("color2");
								$("html, body").animate({
								      scrollTop: $(".findData").offset().top + "px"
							    },{
							        duration: 1000,
							        easing: "swing"
							      })
							}else if(len < 10){
								$(".findData").hide();
							}
						}
					}	
			  	})
			})
		})
		
		function changeStatus(obj){
			var url = "${pageContext.request.contextPath}/report_summary/declareConstruction.action?status="+obj.value;
			parent.$('#contentFrame').attr('src','');
			parent.$('#contentFrame').attr('src',url);
		}
		function export_report(){
			var status = $('#selected').val();
			var url = "${pageContext.request.contextPath}/report_summary/exprot_declareConstruction.action?status="+status;
			parent.$('#contentFrame').attr('src','');
			parent.$('#contentFrame').attr('src',url);
		}
	</script>
</head>

<body>
	<div align="center" style="height: 490px;">
		<div class="summary-search" style="float: left;">
			<input type="hidden" id="status" name="status" value="0" />
			<select id="selected" style="width: 150px; height: 25px;" onchange="changeStatus(this);">
				<option value="0" <c:if test="${status == 0}">selected="selected"</c:if>>全部状态</option>
				<option value="1" <c:if test="${status == 1}">selected="selected"</c:if>>申报中</option>
				<option value="2" <c:if test="${status == 2}">selected="selected"</c:if>>已立项</option>
				<option value="3" <c:if test="${status == 3}">selected="selected"</c:if>>已验收</option>
			</select>
		</div>
		<div class="summary-export">
			<input type="button" id="export" onclick="export_report()" value="导出资源库申报数据" />
		</div>
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
							<c:if test="${fn:length(zykMap.fullName)>18}">${fn:substring(zykMap.fullName,0,18)}...</c:if>
							<c:if test="${fn:length(zykMap.fullName)<=18}">${zykMap.fullName}</c:if>
						</td>
						<td>
							<c:if test="${zykMap.specialtyCategory==''}">无</c:if>${zykMap.specialtyCategory }
						</td>
						<td>
							<c:if test="${zykMap.specialtyName==''}">无</c:if>${zykMap.specialtyName}
						</td>
						<td>
							<c:if test="${fn:length(zykMap.instituteInCharge)>18}">${fn:substring(zykMap.instituteInCharge,0,18)}...</c:if>
							<c:if test="${fn:length(zykMap.instituteInCharge)<=18}">${zykMap.instituteInCharge }</c:if>
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
		<div style="float:right;width:100px;height:30px;margin-top:10px;margin-right: 20px;">
			<a href="###" class="findData" style="color:blue;">加载下10条记录</a>
		</div>
	</div>
</body>
</html>