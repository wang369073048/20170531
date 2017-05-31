$(function(){
	$('#gridtable').datagrid({
		title : '用户列表数据',
		iconCls : 'icon-save',
		fit : true,
		fitColumns : true,
		nowrap : false,
		striped : true,
		collapsible : false,
		url : path+'/user/findUserGrid.action?groupId='+groupId,
		sortName : 'createDate',
		sortOrder : 'desc',
		remoteSort : true,
		idField : 'id',
		columns : [[
			{field:'id',checkbox:true},
			{title:'用户姓名',field:'userName',width:200},
			{title:'登录名称',field:'loginName',width:200}
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
	var url = path+'/modules/org/userCreate.jsp?groupId='+groupId;
	$('#createUserframe').attr('src','');
	$('#createUserframe').attr('src',url);

    $("#createUserWindow").show();
	var win;
    win = $('#createUserWindow').window({
    	title : '新增人员信息',
    	width : '600',
		height : '400',
		left : ($(window).width() - 600)/2,
		top : ($(window).height() - 400)/2,
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
	var url = path+'/user/goUpdate.action?id='+rowData.id+'&groupId='+groupId+'&timestap='+new Date().getTime();
	$('#updateUserframe').attr('src','');
	$('#updateUserframe').attr('src',url);
	
	$("#updateUserWindow").show();
	var win;
    win = $('#updateUserWindow').window({
    	title : '修改人员信息',
    	width : '600',
		height : '400',
		left : ($(window).width() - 600)/2,
		top : ($(window).height() - 400)/2,
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
                    if(rows[i].id == 1){
                    	alert("【超级管理员】不允许删除！");
                        return false;
                    }
					ids += rows[i].id+',';
				}
				$.ajax({
					type: 'POST',
					url: path+'/user/userDelete.action',
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

function checkKeyDown(event){
	if (event.keyCode==13){
		$('#gridtable').datagrid("reload",{'keyword':$('#keyword').val()});
	}
}

function search(){
	var keyword = $('#keyword').val();
	$('#gridtable').datagrid("reload",{'keyword':keyword});
}