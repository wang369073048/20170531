function create(){
    var unique;
    $.ajax({
        url:path+'/user/checkLoginName.action?entity.loginName='+$("#loginName").val(),
        type:"POST",
        async:false,
        dataType:"text",
        success:function(result){
            unique = result;
        }
    });
    if(unique=="false"){
    	$.messager.alert('提示', '登录名已存在!', 'error');
        return;
    }

    $.messager.confirm('提示', '确定要保存吗?', function(r){
		if (r){
			var p1 = $('#password').val();
			var p2 = $('#password2').val();
			
			if(p1 != p2){
				$.messager.alert('提示', '两次密码不一致','error');
				return;
			}
		
			$('#userForm').form('submit',{
		  		url: path+'/user/userCreate.action',
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
	$('#loginName').attr('value','');
	$('#userName').attr('value','');
	$('#password').attr('value','');
	$('#password2').attr('value','');
}

function cancel(){
	$.messager.confirm('提示', '确定要取消吗?', function(r){
		if (r){
			formreset();
			parent.$("#createUserWindow").window("close");
		}
	});
}

function gotoList(){
	parent.$('#gridtable').datagrid("reload",{});
	parent.$('#gridtable').datagrid('clearSelections');
	formreset();
	parent.$("#createUserWindow").window("close");
}