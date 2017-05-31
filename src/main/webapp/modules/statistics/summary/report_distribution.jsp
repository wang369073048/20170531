<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<%
	String contextpath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<script type="text/javascript" src="<%=contextpath %>/js/echarts-all.js"></script>
	<script type="text/javascript">
		var path = "${pageContext.request.contextPath}";
	</script>
	<style type="text/css">
		#main{
			height:405px;
			width: 60%;
			padding: 10px; 
			padding-left: 80px; 
			float: left;
			border-left: 1px #DDDDDD solid;
		}
		
		#data_div{
			height:405px;
			width:30%;
			padding-left:20px; 
			padding-top:20px; 
			float: right;
		}
		
		#data_table td{
			height: 30px;
		}
	</style>
</head>

<body>
	<div style="background-color: #FFFFFF; height: 490px; border:1px #cccccc solid; ">
		<div id="main" align="center"></div>
		<div id="data_div">
	     	<table id="data_table" style="font-size: 12px;">
	     	<tr><td></td><td><b>省份</b></td><td></td></tr>
	     		<c:forEach items="${dataList}" var="data" begin="1" end="10" varStatus="loop">
		     		<c:if test="${ not empty dataList[2*(loop.count-1)] }">
		     			<tr>
		     				<td width="20px;">${dataList[3*loop.count-3]}</td>
		     				<td width="50px;">${dataList[3*loop.count-2]}</td>
		     				<td>${dataList[3*loop.count-1]}</td>
		     			</tr>
	     			</c:if>
	     		</c:forEach>
	     	</table>
		</div>
		
	  	<script type="text/javascript">
	    	var myChart = echarts.init(document.getElementById('main'));
	    	
	    	var dataArray = ${resultList[0]};
	    	var maxCount = ${resultList[1]};
	    	
	    	var option = {
	    		    title : {
	    		        text: '资源库申报分布情况',
	    		        x:'center'
	    		    },
	    		    tooltip : {
	    		        trigger: 'item'
	    		    },
	    		    dataRange: {
	    		        orient: 'vertical',
	    		        min: 0,
	    		        max: maxCount,
	    		        precision: 0,
	    		        x: 'left',
	    		        y: 'bottom',
	    		        text:['高','低'],
	    		        calculable : true
	    		    },
	    		    toolbox: {
	    		        show: true,
	    		        orient : 'horizontal',
	    		        x: 'right',
	    		        y: 'top',
	    		        feature : {
	    		            restore : {show: true},
	    		            saveAsImage : {show: true}
	    		        }
	    		    },
	    		    series : [
	    		        {
	    		            name: '资源库申报',
	    		            type: 'map',
	    		            mapType: 'china',
	    		            roam: false,
	    		            itemStyle:{
	    		                normal:{label:{show:true}},
	    		                emphasis:{label:{show:true}}
	    		            },
	    		            data:dataArray
	    		        }
	    		    ]
	    		};
	        
	    	loadingTicket = setTimeout(function (){
	        	if(dataArray){
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
	</div>
</body>
</html>