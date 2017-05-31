function create(){
	$.messager.confirm('提示', '确定要保存吗?', function(r){
		if (r){			
		    var p1 = $('#formerPassword').val();
			var p2 = $('#newPassword').val();
			var p3 = $('#repeatPassword').val();
			if(p2 != p3){
				$.messager.alert('提示', '两次密码不一致','error');
				return;
			}
			$.ajax({
				type: 'POST',
				url: path+'/user/userPasswordUpdate.action?formerpassword='+p1+'&newpassword='+p2,
				dataType: 'text',
				success:function(data){
					var jsonData = $.parseJSON(data);
					if(jsonData.status == 1){
						 windowClose();
					}else{
						$.messager.alert("提示",jsonData.message,"error");
					}
				}
			});
		}
	})
}

function formreset(){
	$('#formerPassword').attr('value','');
	$('#newPassword').attr('Value','');
	$('#repeatPassword').attr('value','');
}

function cancel(){
	$.messager.confirm('提示', '确定要取消吗?', function(r){
		if (r){
			formreset();
			parent.$("#createModifyWindow").window("close");
		}
	});
}

function windowClose(){
	formreset();
	parent.$("#createModifyWindow").window("close");
}