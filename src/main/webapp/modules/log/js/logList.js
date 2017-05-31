$(function(){
	$('#gridtable').datagrid({
		title : '系统日志列表数据',
		iconCls : 'icon-save',
		fit : true,
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : false,
		url : path+'/log/logGrid.action',
		sortName : 'operateTime',
		sortOrder : 'desc',
		remoteSort : true,
		idField : 'id',
		columns : [[
			{title:'操作人',field:'userName',width:100},
			{title:'操作时间',field:'operateTime',width:120},
			{title:'IP地址',field:'ip',width:120},
			{title:'模块名称',field:'operateModule',width:100},
			{title:'操作动作',field:'operateAction',width:100},
			{title:'操作对象',field:'operateObject',width:500}
		]],
		rownumbers : true,
		loadMsg : '正在加载数据...',
		toolbar : [],
		pagination : true,
		pageNumber : 1,
		pageSize : 20,
		pageList : [20,30,40,50],
		onDblClickRow : function(rowIndex,rowData){
			
		},
		onLoadSuccess: function (){
			var grid = $(".datagrid-toolbar");
			var separator = $("#separator");
			grid.append(separator);
			separator.css("display","");
			
			var search = $("#search");
			grid.append(search);
			search.css("display","");
		}
	});
	
	var grid = $('#gridtable');
	var p = grid.datagrid('getPager');
	$(p).pagination({
		beforePageText:'第',
		afterPageText:'页，共{pages}页',
		displayMsg:'当前显示从第{from}条到{to}条 共{total}条记录',
		onBeforeRefresh:function(){
			
		}
	});
});

function checkDate(){
	var from = $('#beginDate').datebox('getValue');
	var to=$('#endDate').datebox('getValue');
 	var arrFrom = from.split('-');
 	var arrTo = to.split('-');
 	var fromDate = new Date(parseInt(arrFrom[0],10), parseInt(arrFrom[1],10) - 1, parseInt(arrFrom[2],10), 0, 0, 0);
 	var toDate = new Date(parseInt(arrTo[0],10), parseInt(arrTo[1],10) - 1, parseInt(arrTo[2],10), 0, 0 , 0);
 	if(fromDate.getTime() > toDate.getTime()) {
 		$.messager.alert('提示','起始日期不能大于截至日期!','error');
 		return false;
 	}
	return true;
}

function search(){
	if(!checkDate()){
		return ;
	}
	var keyword = $('#keyword').val();
	var beginDate = $('#beginDate').datebox('getValue');
	var endDate = $('#endDate').datebox('getValue');
	$('#gridtable').datagrid("reload",{'keyword':keyword,'beginDate':beginDate,'endDate':endDate});
}

function checkKeyDown(event){
	if (event.keyCode==13){
		var keyword = $('#keyword').val();
		var beginDate = $('#beginDate').datebox('getValue');
		var endDate = $('#endDate').datebox('getValue');
		$('#gridtable').datagrid("reload",{'keyword':keyword,'beginDate':beginDate,'endDate':endDate});
	}
}

function clear(){
	$('#keyword').attr('value','');
	$("#beginDate").datebox('setValue','');
	$("#endDate").datebox('setValue','');
}