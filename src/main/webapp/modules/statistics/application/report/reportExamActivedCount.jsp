<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/common/common.jsp"%>
<%
	String contextpath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>My JSP 'testChart.jsp' starting page</title>
	<script type="text/javascript" src="<%=contextpath %>/js/echarts-all.js"></script>
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>
</head>

<body class="ovreflow1" style="width:100%;height:100%;">

	<div id="main" style="height:400px;"></div>
   	<script type="text/javascript">
    	var myChart = echarts.init(document.getElementById('main'));
    	
    	var titleArray = ${resultList[0]};
    	var dataArray = ${resultList[1]};
		
    	myChart.showLoading({
    	    text : '准备加载数据',
    	    effect : 'bubble',
    	    textStyle : {
    	        fontSize : 20
    	    }
    	});
    	
    	var option = {
       		    title : {
    	            text: '考试活动模块统计',
    	            subtext: '',
    	            x:'center'
    	        },
    	        tooltip : {
    	            trigger: 'axis'
    	        },
    	        toolbox: {
    	            show : true,
    	            feature : {
    	                magicType: {show: true, type: ['line', 'bar']},
    	                restore : {show: true},
    	                saveAsImage : {show: true},
    	                myTool : {
   	   	                 show : true,
   	   	                 title : '导出',
   	   	                 icon : path+'/images/down.png',
   	   	                 onclick : function (){
   	   	                 	var param = parent.generateExportData(titleArray,dataArray);
   	                 	 	window.location = path+'/export/download.action?value='+param+'&title=考试活动模块统计';
   	                     }
   	                	}
    	            }
    	        },
    	        calculable : true,
    	        xAxis : [
    	            {
    	                type : 'value',
    	                boundaryGap : [0, 0.01]
    	            }
    	        ],
    	        yAxis : [
    	            {
    	                type : 'category',
    	                data : titleArray
    	            }
    	        ],
    	        series : [
    	            {
    	                type:'bar',
    	                data:dataArray
    	            }
    	           
    	        ]
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