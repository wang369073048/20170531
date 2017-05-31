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

		var total = parseInt(${resultList[4]});
		
		myChart.showLoading({
    	    text : '准备加载数据',
    	    effect : 'bubble',
    	    textStyle : {
    	        fontSize : 20
    	    }
    	});
    	
    	var labelTop = {
    		    normal : {
    		        label : {
    		            show : true,
    		            position : 'center',
    		            formatter : '{b}',
    		            textStyle: {
    		                baseline : 'bottom'
    		            }
    		        },
    		        labelLine : {
    		            show : false
    		        }
    		    }
    		};
    		var labelFromatter = {
    		    normal : {
    		        label : {
    		            formatter : function (params){
    		                return  ((total - params.value)/total*100).toFixed(2) + '%'
    		            },
    		            textStyle: {
    		                baseline : 'top'
    		            }
    		        }
    		    }
    		};
    		var labelBottom = {
    		    normal : {
    		        color: '#ccc',
    		        label : {
    		            show : true,
    		            position : 'center'
    		        },
    		        labelLine : {
    		            show : false
    		        }
    		    },
    		    emphasis: {
    		        color: 'rgba(0,0,0,0)'
    		    }
    		};
    		var radius = [60, 85];
    		var option = {
    		    legend: {
    		        x : 'center',
    		        y : 'center',
    		        data:${resultList[3]}
    		    },
    		    title : {
    		        text: '注册用户占全部注册用户的比例',
    		        subtext: '总的注册用户数：'+total,
    		        x: 'center'
    		    },
    		    toolbox: {
    		        show : true,
    		        feature : {
    		    		/*dataView : {show: true, readOnly: false},
    		            magicType : {
    		                show: true, 
    		                type: ['pie', 'funnel'],
    		                option: {
    		                    funnel: {
    		                        width: '20%',
    		                        height: '30%',
    		                        itemStyle : {
    		                            normal : {
    		                                label : {
    		                                    formatter : function (params){
    		                                        return 'other\n' + params.value + '%\n'
    		                                    },
    		                                    textStyle: {
    		                                        baseline : 'middle'
    		                                    }
    		                                }
    		                            }
    		                        } 
    		                    }
    		                }
    		            },*/
    		            restore : {show: true},
    		            saveAsImage : {show: true},
    		            myTool : {
   	   	                 show : true,
   	   	                 title : '导出',
   	   	                 icon : path+'/images/down.png',
   	   	                 onclick : function (){
   	   	                 	var param = parent.getAnnularExportData(${resultList[0]},${resultList[1]},${resultList[2]},total)+",总的注册用户数:"+total+":''";
     	                 	 	window.location = path+'/export/download.action?value='+param+'&title=注册用户占全部注册用户的比例';
     	                     }
     	                }
    		        }
    		    },
    		    series : [
    		        {
    		            type : 'pie',
    		            center : ['20%', '30%'],
    		            radius : radius,
    		            x: '0%', // for funnel
    		            itemStyle : labelFromatter,
    		            data : ${resultList[0]}
    		        },
    		        {
    		            type : 'pie',
    		            center : ['50%', '30%'],
    		            radius : radius,
    		            x:'20%', // for funnel
    		            itemStyle : labelFromatter,
    		            data : ${resultList[1]}
    		        },
    		        {
    		            type : 'pie',
    		            center : ['80%', '30%'],
    		            radius : radius,
    		            x:'40%', // for funnel
    		            itemStyle : labelFromatter,
    		            data : ${resultList[2]}
    		        }
    		    ]
    		};
    		                   
    		loadingTicket = setTimeout(function (){
            	if(total){
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