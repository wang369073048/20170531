<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	
	<link href="${pageContext.request.contextPath}/css/mainstyle.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath}/jquery/easyui/css/themes/default/easyui.css" rel="stylesheet" type="text/css" />

	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/jquery/easyui/js/easyui.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/modules/statistics/js/statistics.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/DatePicker/WdatePicker.js"></script>
	
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";

		function reportFrame(path){
			var url = '';
			var para = getParameters();

			if(!para){
				return;
			}
			
			if (typeof(path) == "undefined") {
				var src = $('#reportFrame').attr('src');
				if (typeof(src) == "undefined" || src == "") {
					path = getSecondMenu();
					url = '${pageContext.request.contextPath}'+path+'?'+getParameters();
				}else{
					var arrsrc = src.split('?');
					url = arrsrc[0]+'?'+para;
				}
			}else{
				url = '${pageContext.request.contextPath}'+path+'?'+getParameters();
			}
			
			$('#reportFrame').attr('src','');
			$('#reportFrame').attr('src',url);
		}

		function getSecondMenu(){
			var src = '';
			if($("#1").attr("class") == 'on'){
				src = '/report_update/reportUserUpdate.action';
			}else if($("#2").attr("class") == 'on'){
				src = '/report_update/reportResourceLastYearUpdate.action';
			}else if($("#3").attr("class") == 'on'){
				src = '/report_update/reportCourseUpdate.action';
			}

			return src;
		}
		
	</script>
</head>

<body class="ovreflow1" style="width:100%;height:100%;">
<div class="head-menu">
	<div class="bigbox">
		<div class="second-menu">
			<ul>
				<li id="1" class="on"><a href="###">用户</a></li>
				<li id="2"><a href="###">资源</a></li>
				<li id="3"><a href="###">课程</a></li>
			</ul>
		</div>
	</div>
</div>

<div class="main">
	<div class="main_bigbox">
	    <div class="main_leftsearch">
	    	<div class="search-status">
				<ul>
					<li id="status_1" class="on"><a href="###">申报中</a></li>
					<li id="status_2"><a href="###">已立项</a></li>
					<li id="status_3"><a href="###">已验收</a></li>
				</ul>
			</div>
			
			<div class="search-by" id="searchbytabs">
				<ul>
					<li id="bymajarLI">
						<img src="${pageContext.request.contextPath}/images/search-by_01.png" />
						<a href="###" title="bymajar"><font size="2px">按专业名称</font></a>
					</li>
					<li id="byschoolLI">
						<img src="${pageContext.request.contextPath}/images/search-by_02.png" />
						<a href="###" title="byschool"><font size="2px">按学校</font></a>
					</li>
					<li id="byareaLI">
						<img src="${pageContext.request.contextPath}/images/search-by_03.png" />
						<a href="###" title="byarea"><font size="2px">按地域</font></a>
					</li>
				</ul>
			</div>
      
			<div id="searchbycontent">
				<div id="bymajar" class="oneofsearch">
					<select id="zykFullname" name="zykFullname" style="width:220px;"></select>
				</div>
				<div id="byschool" class="oneofsearch">
					<select id="zykInstituteInCharge" name="zykInstituteInCharge" style="width:220px;"></select>
				</div>
				<div id="byarea" class="oneofsearch">
					<select id="zykCity" name="zykCity" style="width:220px;"></select>
				</div>
			</div>
			<div class="div-interval"></div>
			
			<div class="selecttime">
				<ul>
					<li id="date1LI" class="on"><a href="###">最近7天</a></li>
					<li id="date2LI"><a href="###">最近30天</a></li>
					<li id="date3LI"><a href="###">最近90天</a></li>
				</ul>
				
				<input class="Wdate" type="text" id="beginDate" style="width: 110px;" onClick="WdatePicker()" value="">
				<input class="Wdate" type="text" id="endDate" style="width: 110px;" onClick="WdatePicker()" value="">
			</div>
			
			<div class="inputarea">
				<!-- <input name="" type="text" placeholder="关键字搜索" class="search-input"/> -->
				<a href="javascript:reportFrame()" class="easyui-linkbutton">开始统计</a>
			</div>
	    </div>
	    
		<div class="main_rightcontent">
			<div class="chart-head">
				<div class="chart-tabs">
					<ul id="userUL">
						<li>
							<img src="${pageContext.request.contextPath}/images/pie_chart.png" />
							<a href="javascript:reportFrame('/report_update/reportUserUpdate.action');">同比上一年用户注册增长率</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/line_chart.png" />
							<a href="javascript:reportFrame('/report_update/reportUserChange.action');">历年用户数据变化</a>
						</li>
					</ul>
					
					<ul id="resourceUL" style="display: none;">
						<li>
							<img src="${pageContext.request.contextPath}/images/pie_chart.png" />
							<a href="javascript:reportFrame('/report_update/reportResourceLastYearUpdate.action');">同比上一年资源更新率</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/line_chart.png" />
							<a href="javascript:reportFrame('/report_update/reportResourceTotalCountUpdate.action');">当年更新数与资源总数的比值</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/line_chart.png" />
							<a href="javascript:reportFrame('/report_update/reportResourceChange.action');">历年资源数据变化</a>
						</li>
					</ul>
					
					<ul id="courseUL" style="display: none;">
						<li>
							<img src="${pageContext.request.contextPath}/images/pie_chart.png" />
							<a href="javascript:reportFrame('/report_update/reportCourseUpdate.action');">同比上一年课程更新率</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/line_chart.png" />
							<a href="javascript:reportFrame('/report_update/reportCourseChange.action');">历年课程数据变化</a>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="div-interval"></div>
			
			<div id="chart-content" style="padding-left: 20px;height: 100%;">
				<iframe border="false" scrolling="no" frameborder="0" height="100%" width="100%" id="reportFrame"></iframe>
			</div>
		</div>
	</div>
</div>
</body>
</html>