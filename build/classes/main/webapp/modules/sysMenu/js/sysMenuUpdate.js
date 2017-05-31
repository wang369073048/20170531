function update(){
	$.messager.confirm('提示', '确定要保存吗?', function(r){
		if (r){
			$('#sysMenuForm').form('submit',{
		  		url: path+'/sysMenu/updateSysMenu.action',
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

function formreset(){
	$('#menuName').attr('value','');
	$('#menuEnName').attr('value','');
	$('#sortNumber').numberbox('setValue','');
	$('#menuUrl').attr('value','');
	$('#fileResultTr').css('display','none');
	$('#imgFileTr').css('display','');
}

function cancel(){
	$.messager.confirm('提示', '确定要取消吗?', function(r){
		if (r){
			parent.$("#updateMenuWindow").window("close");
			location.reload();
		}
	});
}

function gotoList(){
	parent.$('#gridtable').datagrid("reload",{});
	parent.$('#gridtable').datagrid('clearSelections');
	parent.$("#updateMenuWindow").window("close");
	location.reload();
}

function delFile(id){
	$.messager.confirm('提示', '确定要删除该文件吗?', function(r){
		if (r){
			$('#fileResultTr').remove();
			$('#imgFileTr').css('display','');
			
			$.ajax({
				type: 'POST',
				url: path+'/sysMenu/removeSysMenuImg.action',
				data: 'id='+id,
				dataType: 'text',
				success:function(data){
					var jsonData = $.parseJSON(data);
					if(jsonData.status == 1){
						$('#menuImg').attr('value','');
					}else{
						$.messager.alert(messager[0], jsonData.message, "error");
					}
				}
			});
		}
	});
}