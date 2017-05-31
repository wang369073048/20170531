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
				src = '/report_application/reportUserData.action';
			}else if($("#2").attr("class") == 'on'){
				src = '/report_application/reportResourceCount.action';
			}else if($("#3").attr("class") == 'on'){
				src = '/report_application/reportCourseCount.action';
			}else if($("#4").attr("class") == 'on'){
				src = '/report_application/reportCitedquesCount.action';
			}else if($("#5").attr("class") == 'on'){
				src = '/report_application/reportForumActivedCount.action';
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
				<li id="4"><a href="###">题库</a></li>
				<li id="5"><a href="###">学习</a></li>
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
							<a href="javascript:reportFrame('/report_application/reportUserData.action');">用户行为活动总量</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/line_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportActivedUserLogCount.action');">注册用户占全部用户比例</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/bar_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportAccumulatorUserDateCount.action');">用户累计学习天数</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/pie_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportActivedUserCount.action');">用户活动总次数</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/line_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportActivedDateUserAVG.action');">人均活动天数</a>
						</li>
					</ul>
					
					<ul id="resourceUL" style="display: none;">
						<li>
							<img src="${pageContext.request.contextPath}/images/pie_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportResourceCount.action');">素材使用次数</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/bar_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportResourceActionCount.action');">素材应用情况</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/line_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportTopThreeResourceCount.action');">不同类型素材浏览和下载频次</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/bar_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportUsedResourceRatio.action');">使用的资源在全部资源的占比</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/line_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportUsedResourceAVG.action');">平均使用次数</a>
						</li>
					</ul>
					
					<ul id="courseUL" style="display: none;">
						<li>
							<img src="${pageContext.request.contextPath}/images/pie_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportCourseCount.action');">课程访问次数</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/line_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportTopThreeCourseCount.action');">各个课程访问情况（前三）</a>
						</li>
					</ul>
					
					<ul id="questionUL" style="display: none;">
						<li>
							<img src="${pageContext.request.contextPath}/images/pie_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportCitedquesCount.action');">题库题目引用数</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/line_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportQuesUsingCount.action');">题库题目使用总次数</a>
						</li>
					</ul>
					
					<ul id="studyUL" style="display: none;">
						<li>
							<img src="${pageContext.request.contextPath}/images/pie_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportForumActivedCount.action');">论坛活动模块统计</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/line_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportWorkActivedCount.action');">作业活动模块统计</a>
						</li>
						<li>
							<img src="${pageContext.request.contextPath}/images/bar_chart.png" />
							<a href="javascript:reportFrame('/report_application/reportExamActivedCount.action');">考试活动模块统计</a>
						</li>
					</ul>
				</div>
			</div>
			
			<div class="div-interval"></div>
			
			<div style="padding-left: 20px;height: 100%;">
				<iframe border="false" scrolling="no" frameborder="0" height="100%" width="100%" id="reportFrame"></iframe>
			</div>
		</div>
	</div>
</div>
</body>
</html>