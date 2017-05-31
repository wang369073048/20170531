<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String contextpath = request.getContextPath();
%>

<html>
<head>
	<script type="text/javascript" src="<%=contextpath %>/js/echarts-all.js"></script>
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>
</head>

<body class="ovreflow1" style="width:100%;height:100%;">
	<div id="main" style="height:450px;"></div>
   	<script type="text/javascript">
    	var myChart = echarts.init(document.getElementById('main'));
    	var titleArray = ${resultList[0]};
    	var addDataArray = ${resultList[1]};
    	var updateDataArray = ${resultList[3]};
    	var deleteDataArray = ${resultList[5]};
    	myChart.showLoading({
		    text : '准备加载数据',
		    effect : 'bubble',
		    textStyle : {
		        fontSize : 20
		    }
		});
    	
    	var option = {
    		    title : {
    		        text: '历年课程数据变化情况'
    		    },
    		    tooltip : {
    		        trigger: 'axis'
    		    },
    		    legend: {
    		        data:['新增','修改','删除']
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		            magicType : {show: true, type: ['line', 'bar']},
    		            restore : {show: true},
    		        }
    		    },
    		    calculable : true,
    		    xAxis : [
    		        {
    		            type : 'category',
    		            boundaryGap : false,
    		            data : titleArray
    		        }
    		    ],
    		    yAxis : [
    		        {
    		            type : 'value',
    		            axisLabel : {
    		                formatter: '{value}'
    		            }
    		        }
    		    ],
    		    series : [
    		        {
    		            name:'新增',
    		            type:'line',
    		            data: addDataArray
    		        },
    		        {
    		            name:'修改',
    		            type:'line',
    		            data:updateDataArray,
    		        },
    		        {
    		            name:'删除',
    		            type:'line',
    		            data:deleteDataArray,
    		        }
    		    ]
    		};

	    	loadingTicket = setTimeout(function (){
	   	    	myChart.hideLoading();
	   	    	myChart.setOption(option);
	    	},1000);
    </script>
</body>
</html>