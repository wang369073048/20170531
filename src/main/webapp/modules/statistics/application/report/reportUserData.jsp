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
    	var maxCount = ${resultList[2]};
		var total = ${resultList[3]};
    	
    	myChart.showLoading({
    	    text : '准备加载数据',
    	    effect : 'bubble',
    	    textStyle : {
    	        fontSize : 20
    	    }
    	});
    	
    	var option = {
      		title : {
   	            text: '用户行为统计',
   	            subtext: '用户行为总数：'+total,
   	            x:'center'
   	        },
   	        tooltip : {
   	            trigger: 'item',
   	            formatter: "{a} <br/>{b} : {c} ({d}%)"
   	        },
   	        legend: {
   	            orient : 'vertical',
   	            x : 'left',
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
   	                            funnelAlign: 'left',
   	                            max: maxCount
   	                        }
   	                    }
   	                },
   	                restore : {show: true},
   	                saveAsImage : {
   	                    show : true,
   	                    title : '保存为图片',
   	                    type : 'png',
   	                    lang : ['点击保存'] 
   	                },
   	             	myTool : {
	   	                 show : true,
	   	                 title : '导出',
	   	                 icon : path+'/images/down.png',
	   	                 onclick : function (){
	   	                 	var param = parent.generateExportData(titleArray,dataArray);
   	                 	 	window.location = path+'/export/download.action?value='+param+'&title=用户行为统计&total='+total;
   	                     }
   	               }
   	            }
   	        },
   		    calculable : true,
   	        series : [
   	            {
   	                name:'访问来源',
   	                type:'pie',
   	                radius : '70%',
   	                center: ['50%', '65%'],
   	                selectedMode:'single',
   	                selectedOffset: '20',
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