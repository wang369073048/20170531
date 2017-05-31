$(function(){
	$('#gridtable').datagrid({
		title : '系统菜单列表数据',
		iconCls : 'icon-save',
		fit : true,
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : false,
		url : path+'/sysMenu/findSysMenuGrid.action?parentId='+parentId,
		sortName : 'sortNumber',
		sortOrder : 'asc',
		remoteSort : true,
		idField : 'id',
		columns : [[
			{field:'id',checkbox:true},
			{title:'菜单名称',field:'menuName',width:150},
			{title:'排序号',field:'sortNumber',width:150},
			{title:'链接地址',field:'menuUrl',width:300}
		]],
		rownumbers : true,
		loadMsg : '正在加载数据...',
		toolbar : [{
			id:'btnadd',
			text:'新增',
			iconCls:'icon-add',
			handler:function(){
				openCreateWindow();
			}
		},'-',{
			id:'btndel',
			text:'删除',
			iconCls:'icon-remove',
			handler:function(){
				deleteRow();
			}
		}],
		pagination : true,
		pageNumber : 1,
		pageSize : 20,
		pageList : [20,30,40,50],
		onDblClickRow : function(rowIndex,rowData){
			openUpdateWindow(rowIndex,rowData);
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

function openCreateWindow(){
	$("#createMenuWindow").show();
	var win;
    win = $('#createMenuWindow').window({
    	title : '添加系统菜单信息',
		width : '600',
		height : '350',
		left : ($(window).width() - 600)/2,
		top : ($(window).height() - 350)/2,
		collapsible : true,
		minimizable : false,
		maximizable : false,
		closable : false,
		draggable : false,
		resizable : false,
		shadow : true,
		modal : true,
		iconCls : 'icon-add',
		closed : true
	});
    win.window("open");
}

function openUpdateWindow(rowIndex,rowData){
	var url = path+'/sysMenu/goUpdate.action?id='+rowData.id;
	$('#updateMenuFrame').attr('src','');
	$('#updateMenuFrame').attr('src',url);
	
	$("#updateMenuWindow").show();
	var win;
    win = $('#updateMenuWindow').window({
    	title : '修改系统菜单信息',
		width : '600',
		height : '350',
		left : ($(window).width() - 600)/2,
		top : ($(window).height() - 350)/2,
		collapsible : true,
		minimizable : false,
		maximizable : false,
		closable : false,
		draggable : false,
		resizable : false,
		shadow : true,
		modal : true,
		iconCls : 'icon-add',
		closed : true
	});
    win.window("open");
}

function deleteRow(){
	var ids = '';
	var rows = $('#gridtable').datagrid('getSelections');
	if(rows.length == 0){
		$.messager.alert('提示','请至少选择一条数据!','error');
	}else{
		$.messager.confirm('提示', '确定要删除所选数据吗?', function(r){
			if (r){
				for(var i=0;i<rows.length;i++){
					ids += rows[i].id+',';
				}
				
				$.ajax({
					type: 'POST',
					url: path+'/sysMenu/removeSysMenu.action',
					data: 'ids='+ids,
					dataType: 'text',
					success:function(data){
						var jsonData = $.parseJSON(data);
						if(jsonData.status == 1){
							$('#gridtable').datagrid("reload",{});
							$('#gridtable').datagrid('clearSelections');
						}else{
							$.messager.alert("提示",jsonData.message,"error");
						}
					}
				});
			}
		});
	}
}