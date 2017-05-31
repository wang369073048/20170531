function assignRoleMenu(){
	var zTree = $.fn.zTree.getZTreeObj("menuTree");
	nodes = zTree.getCheckedNodes(true);
	$.messager.confirm('提示', '确定要保存吗?', function(r){
		if (r){
			var menuIds = '';
			for (var i=0, l=nodes.length; i<l; i++) {
				menuIds += nodes[i].id + ";";
			}
			
			$.ajax({
				type: "POST", 
				url: path+'/role/roleAssignMenu.action',
				data: 'roleId='+roleId+'&menuIds='+menuIds,
				dataType: 'text',
				success:function(data){
					var jsonData = $.parseJSON(data);
					if(jsonData.status == 1){
						cancel();
					}else{
						$.messager.alert("提示",jsonData.message,"error");
					}
				}
			});
		}
	});
}

function cancel(){
	parent.$("#assignMenuWindow").window("close");
}