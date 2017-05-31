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
	<div id="main" style="height:500px;"></div>
   	<script type="text/javascript">
    	var myChart = echarts.init(document.getElementById('main'));
    	
    	var titleArray = ${resultList[0]};
    	var dataArray = ${resultList[1]};
    	var inpieArray = ${resultList[2]};
    	var totalCount = '资源总数：'+${resultList[3]};

    	myChart.showLoading({
    	    text : '准备加载数据',
    	    effect : 'bubble',
    	    textStyle : {
    	        fontSize : 20
    	    }
    	});
    	
    	var option = {
	    	title : {
	            text: '使用的资源在全部资源的占比',
	            subtext: totalCount,
	            x:'center'
	        },
	    	tooltip : {
	            show: true,
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
	                restore : {show: true},
	                saveAsImage : {show: true},
	                myTool : {
	   	                 show : true,
	   	                 title : '导出',
	   	                 icon : path+'/images/down.png',
	   	                 onclick : function (){
	   	                 	var param = parent.generateExportTotalData(titleArray,dataArray,${resultList[3]})+",资源总数:${resultList[3]}:''";
	                 	 	window.location = path+'/export/download.action?value='+param+'&title=使用的资源在全部资源的占比统计';
	                     }
	                }
	            }
	        },
	        calculable : true,
	        series : [
	            {
	                name:'访问来源',
	                type:'pie',
	                center : ['30%', 300],
	                radius : 80,
	                center: ['50%', '50%'],
	                itemStyle : {
	                    normal : {
	                        label : {
	                            position : 'inner',
	                            formatter : function (params) {                         
	                            	return (params.percent - 0).toFixed(0) + '%'
	                            }
	                        },
	                        labelLine : {
	                            show : false
	                        }
	                    },
	                    emphasis : {
	                        label : {
	                            show : true,
	                            formatter : "{b}\n{d}%"
	                        }
	                    }
	                    
	                },
	                data:inpieArray
	            },
	            {
	                name:'访问来源',
	                type:'pie',
	                center : ['50%', '50%'],
	                radius : [110, 140],
	                data:dataArray
	            }
	        ]
	    };
                        
    	loadingTicket = setTimeout(function (){
        	if(!(titleArray.length==1 && dataArray.length==1)){
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