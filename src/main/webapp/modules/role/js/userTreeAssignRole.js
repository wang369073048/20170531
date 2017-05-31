function assignRoleUser(){
	var zTree = $.fn.zTree.getZTreeObj("userTree");
	nodes = zTree.getCheckedNodes(true);
	$.messager.confirm('提示', '确定要保存吗?', function(r){
		if (r){
			var userIds = '';
			for (var i=0, l=nodes.length; i<l; i++) {
				userIds += nodes[i].id + ";";
			}
			
			$.ajax({
				type: "POST", 
				url: path+'/role/roleAssignUser.action',
				data: 'roleId='+roleId+'&userIds='+userIds,
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
	parent.$("#assignUserWindow").window("close");
}