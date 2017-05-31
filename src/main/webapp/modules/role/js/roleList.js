$(function(){
	$('#gridtable').datagrid({
		title : '角色列表数据',
		iconCls : 'icon-save',
		fit : true,
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : false,
		url : path+'/role/findRoleGrid.action',
		sortName : 'createTime',
		sortOrder : 'desc',
		remoteSort : true,
		idField : 'id',
		columns : [[
			{field:'id',checkbox:true},
			{title:'角色名称',field:'roleName',width:150},
			{title:'角色编码',field:'roleCode',width:150},
			{title:'创建时间',field:'createTime',width:300},
			{title:'授权选项',field:'roleId',width:200,align:'center',formatter:function(value,rowData,rowIndex){
			   return '<a href="javascript:openUserTreeWindow(\'' + value + '\');">用户授权</a>&nbsp;&nbsp;'+
			   '<a href="javascript:openMenuTreeWindow(\'' + value + '\');">菜单授权</a>';
			}}
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
	var p = $('#gridtable').datagrid('getPager');
	$(p).pagination({
		beforePageText:'第',
		afterPageText:'页，共{pages}页',
		displayMsg:'当前显示从第{from}条到{to}条 共{total}条记录',
		onBeforeRefresh:function(){
			
		}
	});
});

function openCreateWindow(){
	$("#createRoleWindow").show();
	var win;
    win = $('#createRoleWindow').window({
    	title : '添加角色信息',
		width : '600',
		height : '300',
		left : ($(window).width() - 600)/2,
		top : ($(window).height() - 300)/2,
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
	var url = path+'/role/goUpdate.action?id='+rowData.id+'&timestap='+new Date().getTime();
	$('#updateRoleframe').attr('src','');
	$('#updateRoleframe').attr('src',url);
	
	$("#updateRoleWindow").show();
	var win;
    win = $('#updateRoleWindow').window({
    	title : '修改角色信息',
		width : '600',
		height : '300',
		left : ($(window).width() - 600)/2,
		top : ($(window).height() - 300)/2,
		collapsible : true,
		minimizable : false,
		maximizable : false,
		closable : false,
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
					url: path+'/role/roleDelete.action',
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

function openUserTreeWindow(roleId){
	var url = path+'/role/findUserFroTreeAssignRole.action?roleId='+roleId+'&timestap='+new Date().getTime();
	$('#assignUserframe').attr('src','');
	$('#assignUserframe').attr('src',url);
	
	$("#assignUserWindow").show();
	var win;
    win = $('#assignUserWindow').window({
    	title : '角色关联用户',
		width : '500',
		height : '400',
		left : ($(window).width() - 500)/2,
		top : ($(window).height() - 400)/2,
		collapsible : true,
		minimizable : false,
		maximizable : false,
		closable : false,
		resizable : false,
		shadow : true,
		modal : true,
		iconCls : 'icon-add',
		closed : true
	});
    win.window("open");
}

function openMenuTreeWindow(roleId){
	var url = path+'/sysMenu/findSysMenuFroTreeAssignRole.action?roleId='+roleId+'&timestap='+new Date().getTime();
	$('#assignMenuframe').attr('src','');
	$('#assignMenuframe').attr('src',url);
	
	$("#assignMenuWindow").show();
	var win;
    win = $('#assignMenuWindow').window({
    	title : '角色关联菜单',
		width : '500',
		height : '400',
		left : ($(window).width() - 500)/2,
		top : ($(window).height() - 400)/2,
		collapsible : true,
		minimizable : false,
		maximizable : false,
		closable : false,
		resizable : false,
		shadow : true,
		modal : true,
		iconCls : 'icon-add',
		closed : true
	});
    win.window("open");
}