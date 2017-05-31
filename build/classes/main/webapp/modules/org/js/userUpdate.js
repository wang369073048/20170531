function update(){
    var unique;
    $.ajax({
        url:path+'/user/checkLoginName.action?entity.loginName='+$("#loginName").val()+'&entity.id='+$("#id").val(),
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
    
	var p1 = $('#password1').val();
	var p2 = $('#password2').val();
	
	if(p1 == "" && p2 == ""){
		$.messager.confirm('提示', '不输入密码将使用原有密码，是否继续?', function(r){
			if (r){
				$('#userForm').form('submit',{
			  		url: path+'/user/userUpdate.action',
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
	}else{
		$.messager.confirm('提示', '确定要保存吗?', function(r){
			if (r){
				if(p1 != p2){
					$.messager.alert('提示', '两次密码不一致','error');
					return;
				}else{
					$('#password').attr('value',p1);
				}
			
				$('#userForm').form('submit',{
			  		url: path+'/user/userUpdate.action',
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
}

function cancel(){
	$.messager.confirm('提示', '确定要取消吗?', function(r){
		if (r){
			parent.$("#updateUserWindow").window("close");
			location.reload();
		}
	});
}

function gotoList(){
	parent.$('#gridtable').datagrid("reload",{});
	parent.$('#gridtable').datagrid('clearSelections');
	parent.$("#updateUserWindow").window("close");
	location.reload();
}