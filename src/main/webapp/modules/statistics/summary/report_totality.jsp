<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>
	<style type="text/css">
		.div_totalCount{
			width: 30%;
			height: 180px;
			text-align: center;
			background-color: #F8F8F8;
			margin-top:20px;
			margin-left:20px;
			margin-right:10px;
			border: 1px solid #DDDDDD;
		}
		.table_totalCount{
			margin: auto;
			margin-top: 40px;
		}
		.td_count{
			font-family: Kokila;
			font-size: 55px;
			height: 80px;
		}
		.td_title{
			height:40px;
			font-family: 微软雅黑; 
			font-size: 18px; 
		}
	</style>
</head>

<body>
<div style="background-color: #FFFFFF; height: 490px; border:1px #cccccc solid; ">
	 <div style=" border-color:#FFFFFF; width: 90%; height: 100%; margin-left: 80px; " scrolling="no" >
		<div class="div_totalCount" style="float: left;" >
			<table class="table_totalCount" style="color: #0F84E7;" >
				<tr><td class="td_count"><b>${totalityList[1]}</b></td></tr>
				<tr><td class="td_title"><b>${totalityList[0]}</b></td></tr>
			</table>
		</div>
		<div class="div_totalCount" style=" float: left; " >
			<table class="table_totalCount" style="color: #FA3678; " >
				<tr><td class="td_count"><b>${totalityList[3]}</b></td></tr>
				<tr><td class="td_title"><b>${totalityList[2]}</b></td></tr>
			</table>
		</div>
		<div class="div_totalCount" style=" float: left; " >
			<table class="table_totalCount" style="color: #79C8F2; " >
				<tr><td class="td_count"><b>${totalityList[5]}</b></td></tr>
				<tr><td class="td_title"><b>${totalityList[4]}</b></td></tr>
			</table>
		</div>
		<div class="div_totalCount" style=" float: left; " >
			<table class="table_totalCount" style="color: #F9951D; " >
				<tr><td class="td_count"><b>${totalityList[7]}</b></td></tr>
				<tr><td class="td_title"><b>${totalityList[6]}</b></td></tr>
			</table>
		</div>
		<div class="div_totalCount" style=" float: left; " >
			<table class="table_totalCount" style="color: #2DB374; " >
				<tr><td class="td_count"><b>${totalityList[9]}</b></td></tr>
				<tr><td class="td_title"><b>${totalityList[8]}</b></td></tr>
			</table>
		</div>
	</div>
</div>
</body>
</html>