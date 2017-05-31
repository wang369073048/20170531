function update(){
	$.messager.confirm('提示', '确定要保存吗?', function(r){
		if (r){
			$('#roleForm').form('submit',{
		  		url: path+'/role/roleUpdate.action',
		  		onSubmit:function(){
		  			return $(this).form('validate');
				},
				success:function(data){
					var jsonData = $.parseJSON(data);
					if(jsonData.status == 1){
						gotoList();
					}else{
						$.messager.alert("提示",jsonData.message,"error");
					}
				}
			});
		}
	});
}

function cancel(){
	$.messager.confirm('提示', '确定要取消吗?', function(r){
		if (r){
			parent.$("#updateRoleWindow").window("close");
		}
	});
}

function gotoList(){
	parent.$('#gridtable').datagrid("reload",{});
	parent.$('#gridtable').datagrid('clearSelections');
	parent.$("#updateRoleWindow").window("close");
}