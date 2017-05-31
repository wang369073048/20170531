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
	   	var totalCount = '更新率：' + ${resultList[2]}; 
    	myChart.showLoading({
		    text : '准备加载数据',
		    effect : 'bubble',
		    textStyle : {
		        fontSize : 20
		    }
		});
    	
    	var option = {
   		    title : {
   		        text: '同比上一年课程更新率',
   		        subtext: totalCount 
   		    },
   		    tooltip : {
   		        trigger: 'axis',
 	        	formatter : "{a} <br/>{b} : {c}"
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
  	                 	var param = parent.generateExportData(titleArray,dataArray)+",同比上一年课程更新增长率:"+${resultList[2]}.substring(0,${resultList[2]}.length-1)+"%25";
                	 	window.location = path+'/export/download.action?value='+param+'&title=同比上一年课程更新增长率统计';
                    }
              		}
   		        }
   		    },
   		    calculable : true,
   		    xAxis : [
   		        {
   		            type : 'category',
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
   		            name:'课程',
   		            type:'bar',
   		            radius : '55%',
   		     		barCategoryGap:'70%',
   		     		itemStyle:{
   		     			normal:{
   		     				color:"#EB8174"
   		     			}
   		     		},
   		            data:dataArray
   		        }
   		    ]
   		};
    		                    
    	loadingTicket = setTimeout(function (){
        	if(titleArray.length == 2 && dataArray.length == 2){
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