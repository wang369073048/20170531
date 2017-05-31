function create(){
	$.messager.confirm('提示', '确定要保存吗?', function(r){
		if (r){
			$('#sysMenuForm').form('submit',{
		  		url: path+'/sysMenu/saveSysMenu.action',
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
	})
}

function formreset(){
	$('#menuName').attr('value','');
	$('#menuEnName').attr('value','');
	$('#sortNumber').numberbox('setValue','');
	$('#menuUrl').attr('value','');
	$('#imgFileTr').remove();
	addImgFileRow();
}

function cancel(){
	$.messager.confirm('提示', '确定要取消吗?', function(r){
		if (r){
			formreset();
			parent.$("#createMenuWindow").window("close");
		}
	});
}

function gotoList(){
	parent.$('#gridtable').datagrid("reload",{});
	parent.$('#gridtable').datagrid('clearSelections');
	formreset();
	parent.$("#createMenuWindow").window("close");
}

function addImgFileRow(){
	var trTotal = $("#imgFileTable tr").length;
	var trHTML = "<tr id=imgFileTr><td>&nbsp;<input type='file' id='imgFile' name='imgFile' style='width: 85%'></td></tr>"	
	$("#imgFileTable").append(trHTML);
}