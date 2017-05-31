<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String contextpath = request.getContextPath();
%>

<html>
<head>
	<script type="text/javascript" src="<%=contextpath%>/js/echarts-all.js"></script>
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>
</head>

<body class="ovreflow1" style="width: 100%; height: 100%;">
	<div id="main" style="height: 450px;"></div>
	<script type="text/javascript">
		var myChart = echarts.init(document.getElementById('main'));

		var titleArray = ${resultList[0]};
		var dataArray = ${resultList[1]};
		var total = ${resultList[2]};

		myChart.showLoading({
		    text : '准备加载数据',
		    effect : 'bubble',
		    textStyle : {
		        fontSize : 20
		    }
		});

		var option = {
			title : {
				text : '用户累计学习天数',
				subtext : '总数：'+total
			},
			tooltip : {
				trigger : 'axis'
			},
			
			toolbox : {
				show : true,
				feature : {
					magicType : {show: true, type: ['line', 'bar']},
		            restore : {show: true},
		            saveAsImage : {show: true},
		            myTool : {
	   	                 show : true,
	   	                 title : '导出',
	   	                 icon : path+'/images/down.png',
	   	                 onclick : function (){
	   	                 	var param = parent.generateExportData(titleArray,dataArray);
  	                 	 	window.location = path+'/export/download.action?value='+param+'&title=用户累计学习天数统计&total='+total;
  	                     }
  	               }
				}
			},
			calculable : true,
			xAxis : [ {
				type : 'category',
				data : titleArray
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			series : [ {
				name : '活动情况',
				type : 'bar',
				data : dataArray
			} ]
		};

		loadingTicket = setTimeout(function (){
	    	if(titleArray && dataArray){
		    	myChart.hideLoading();
		    	myChart.setOption(option);
	        }else{
	        	myChart.showLoading({
	        		text : '暂时无数据加载',
	        	    effect : 'bubble',
	        	    textStyle : {
	        	        fontSize : 20
	        	    }
	            })
	        }
		},1000);
		
		
	</script>
</body>
</html>