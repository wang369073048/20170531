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
    	var totalCount = '资源素材总量：'+${resultList[2]}; 

    	myChart.showLoading({
    	    text : '准备加载数据',
    	    effect : 'bubble',
    	    textStyle : {
    	        fontSize : 20
    	    }
    	});
    	
    	var option = {
      		title : {
   	            text: totalCount,
   	            x:'center',
   	            y: 190,
   		        itemGap: 20,
   		        textStyle : {
   		            color : 'rgba(30,104,55,0.8)',
   		            fontFamily : '微软雅黑',
   		            fontSize : 20,
   		            fontWeight : 'bolder'
   		        }
   	        },
   	        tooltip : {
   	            trigger: 'item',
   	            formatter: "{b} : {c} ({d}%)"
   	        },
   	        legend: {
   	            orient : 'vertical',
   	            x : document.getElementById('main').offsetWidth / 20,
   		        y : 15,
   		        itemGap:12,
   		        data:titleArray
   	        },
   		    toolbox: {
   		    	show : true,
   		        feature : {
   		         	magicType : {
    	                show: true, 
    	                type: ['pie', 'funnel'],
    	                option: {
    	                    funnel: {
    	                        x: '25%',
    	                        width: '50%',
    	                        funnelAlign: 'center',
    	                        max: 1548
    	                    }
    	                }
   	        		},
   		            restore : {show: true},
   		            saveAsImage : {show: true},
   		         	myTool : {
	   	                 show : true,
	   	                 title : '导出',
	   	                 icon : path+'/images/down.png',
	   	                 onclick : function (){
	   	                 	var param = parent.generateExportData(titleArray,dataArray)+",资源素材总量:${resultList[2]}";
	                 	 	window.location = path+'/export/download.action?value='+param+'&title=资源素材总量统计';
	                     }
	               }
   		        }
   	        },
   	        series : [
   	            {
   	                type:'pie',
   	                radius : [105, 150],
   	                center: ['50%', '45%'],
   	                selectedMode:'single',
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