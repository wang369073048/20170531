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
	<!-- <div id="main" style="height:450px;"></div> -->
   	<script type="text/javascript">
    	var myChart = echarts.init(document.getElementById('main'));
    	
    	var titleArray = ${resultList[0]};
    	var dataArray = ${resultList[1]};
    	var totalCount = '专业知识点总量：'+${resultList[2]};

    	myChart.showLoading({
    	    text : '准备加载数据',
    	    effect : 'bubble',
    	    textStyle : {
    	        fontSize : 20
    	    }
    	});
    	
    	var option = {
      		title : {
   	            text: '专业知识点统计',
   	            subtext: totalCount,
   	            x:'center'
   	        },
   	        tooltip : {
   	            trigger: 'item',
   	            formatter: "{b} "
   	        },
   	        legend: {
   	            orient : 'vertical',
   	            x : 'left',
   	            data:titleArray
   	        },
   		    toolbox: {
   	            show : true,
   	            feature : {
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
	   	                 	var param = parent.generateExportData(titleArray,dataArray)+",专业知识点总量:${resultList[2]}";
	                 	 	window.location = path+'/export/download.action?value='+param+'&title=专业知识点统计分布';
	                     }
	               }
   	            }
   	        },
   		    calculable : true,
   	        series : [
   	            {
   	                name:'知识点',
   	                type:'pie',
   	                radius : '70%',
   	                center: ['50%', '50%'],
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
    <div>
    	<table >
	    	<tr>
	    		<td>专业知识点数量：</td>
	    		<td>${resultList[2]}</td>
	    	</tr>
	    </table>
    </div>
</body>
</html>