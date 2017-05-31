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
    	var dataArray = ${resultList[1]};
    	var totalCount = '应用总数：' + ${resultList[2]};

    	myChart.showLoading({
		    text : '准备加载数据',
		    effect : 'bubble',
		    textStyle : {
		        fontSize : 20
		    }
		});
    	
    	var option = {
   		    title : {
   		        text: '资源素材应用情况',
   		        subtext: totalCount
   		    },
   		    tooltip : {
   		        trigger: 'axis'
   		    },
   		    toolbox: {
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
	   	                 	var param = parent.generateExportData(titleArray,dataArray)+",应用总量:${resultList[2]}";
	                 	 	window.location = path+'/export/download.action?value='+param+'&title=资源素材应用情况';
	                     }
	               }
   		        }
   		    },
   		    calculable : true,
   		    xAxis : [
   		        {
   		            type : 'category',
   		         	axisLabel:{
   		         		interval:0
   		         	},
   		            data : titleArray
   		        }
   		    ],
   		    yAxis : [
   		        {
   		            type : 'value'
   		        }
   		    ],
   		    		 	
   		    series : [
   		        {
   		            name:'数量',
   		            type:'bar',
   		            radius : '55%',
   		         	barCategoryGap:'70%',
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