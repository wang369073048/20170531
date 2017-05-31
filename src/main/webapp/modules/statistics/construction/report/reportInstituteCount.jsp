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
	<div>
    	<table >
	    	<tr>
	    		<td>用户来自的院校数：</td>
	    		<td>${resultList[2]}</td>
	    	</tr>
	    </table>
    </div>
	<div id="main" style="height:450px; display: none"></div>
   	<script type="text/javascript">
    	var myChart = echarts.init(document.getElementById('main'));
    	
    	var titleArray = ${resultList[0]};
    	var dataArray = ${resultList[1]};
    	var totalCount = '用户总数：' + ${resultList[2]};

    	myChart.showLoading({
		    text : '准备加载数据',
		    effect : 'bubble',
		    textStyle : {
		        fontSize : 20
		    }
		});
    	
    	option = {
   		    title : {
   		        text: '用户来自的院校数',
   		        subtext: totalCount
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
	   	                 	var param = parent.generateExportData(titleArray,dataArray)+",用户总量:${resultList[2]}";
	                 	 	window.location = path+'/export/download.action?value='+param+'&title=用户院校分布统计';
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
   		            name:'数量',
   		            type:'bar',
   		            radius : '55%',
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