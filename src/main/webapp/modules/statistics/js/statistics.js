$(function () {
    $("div.search-status li a").click(function () {
        $("div.search-status li").removeClass("on");
        $(this).parent("li").addClass("on");
        
        var id = $(this).parent("li").attr('id');
        if(id == 'status_1'){
        	changeStatus(1);
        }
        if(id == 'status_2'){
        	changeStatus(2);
        }
        if(id == 'status_3'){
        	changeStatus(3);
        }
    });
}); 
	 
$(function () {
    $("div.second-menu li a").click(function () {
        $("div.second-menu li").removeClass("on");
        $(this).parent("li").addClass("on");
        
        var id = $(this).parent("li").attr('id');
        if(id=='1'){
        	$('#userUL').css('display','');
        	$('#resourceUL').css('display','none');
        	$('#courseUL').css('display','none');
        	$('#questionUL').css('display','none');
        	$('#studyUL').css('display','none');
        }else if(id=='2'){
        	$('#userUL').css('display','none');
        	$('#resourceUL').css('display','');
        	$('#courseUL').css('display','none');
        	$('#questionUL').css('display','none');
        	$('#studyUL').css('display','none');
        }else if(id=='3'){
        	$('#userUL').css('display','none');
        	$('#resourceUL').css('display','none');
        	$('#courseUL').css('display','');
        	$('#questionUL').css('display','none');
        	$('#studyUL').css('display','none');
        }else if(id=='4'){
        	$('#userUL').css('display','none');
        	$('#resourceUL').css('display','none');
        	$('#courseUL').css('display','none');
        	$('#questionUL').css('display','');
        	$('#studyUL').css('display','none');
        }else if(id=='5'){
        	$('#userUL').css('display','none');
        	$('#resourceUL').css('display','none');
        	$('#courseUL').css('display','none');
        	$('#questionUL').css('display','none');
        	$('#studyUL').css('display','');
        }
        
        var para = getParameters();
		if(!para){
			return;
		}
        
        var url = path+''+getSecondMenu()+'?'+para;
		$('#reportFrame').attr('src',url);
    });
}); 

$(function () {
	$("#searchbycontent div:first").fadeIn(); // Show first tab content
    $("div.oneofsearch").hide(); //隐藏所有
    $("div.search-by a:first").parent("li").addClass("on"); //第一个元素选中
    $("div.oneofsearch:first").show(); //第一个内容显
    $('#searchbytabs a').click(function(e) {
        e.preventDefault();        
        $("#searchbycontent div").hide(); //Hide all content
        //$("#searchbytabs li").attr("id",""); //Reset id's
		$("#searchbytabs li").removeClass("on");
		$(this).parent("li").addClass("on");
        $('#' + $(this).attr('title')).fadeIn(); // Show content for current tab
    });
});

$(function () {
    $("div.selecttime li a").click(function () {
        $("div.selecttime li").removeClass("on");
        $(this).parent("li").addClass("on");
    });
}); 

$(function(){
	$('#zykFullname').combogrid({
		panelWidth:540,
		idField:'zykId',
		textField:'fullname',
		url:path + '/zyk/findZykFroComboFullname.action?status=1',
		columns:[[
			{field:'fullname',title:'专业名称',width:250},
			{field:'instituteInCharge',title:'主持单位',width:250}
		]]
	});
	
	$('#zykInstituteInCharge').combogrid({
		panelWidth:540,
		idField:'zykId',
		textField:'fullname',
		url:path + '/zyk/findZykFroComboInstituteInCharge.action?status=1',
		columns:[[
			{field:'instituteInCharge',title:'主持单位',width:250},
			{field:'fullname',title:'专业名称',width:250}
		]]
	});
	
	$('#zykCity').combogrid({
		panelWidth:700,
		idField:'zykId',
		textField:'fullname',
		url:path + '/zyk/findZykFroComboCity.action?status=1',
		columns:[[
		    {field:'city',title:'城市',width:150},
			{field:'instituteInCharge',title:'主持单位',width:250},
			{field:'fullname',title:'专业名称',width:250}
		]]
	});
});

function changeStatus(status){
	$('#zykFullname').combogrid('setValue', '');
	$('#zykInstituteInCharge').combogrid('setValue', '');
	$('#zykCity').combogrid('setValue', '');
	
	$('#zykFullname').combogrid({
		panelWidth:540,
		idField:'zykId',
		textField:'fullname',
		url:path + '/zyk/findZykFroComboFullname.action?status='+status,
		columns:[[
			{field:'fullname',title:'专业名称',width:250},
			{field:'instituteInCharge',title:'主持单位',width:250}
		]]
	});
	
	$('#zykInstituteInCharge').combogrid({
		panelWidth:540,
		idField:'zykId',
		textField:'fullname',
		url:path + '/zyk/findZykFroComboInstituteInCharge.action?status='+status,
		columns:[[
			{field:'instituteInCharge',title:'主持单位',width:250},
			{field:'fullname',title:'专业名称',width:250}
		]]
	});
	
	$('#zykCity').combogrid({
		panelWidth:700,
		idField:'zykId',
		textField:'fullname',
		url:path + '/zyk/findZykFroComboCity.action?status='+status,
		columns:[[
		    {field:'city',title:'城市',width:150},
			{field:'instituteInCharge',title:'主持单位',width:250},
			{field:'fullname',title:'专业名称',width:250}
		]]
	});
}

function getToDay(){
	var today = new Date();
	var y = today.getFullYear();
	var m = today.getMonth() + 1;
	var d = today.getDate();

	if(m < 10){
		m = "0"+m;
	}
	if(d < 10){
		d = "0"+d;
	}
	return y+"-"+m+"-"+d;
}

function getDate(num){
	var today = new Date();
	today.setDate(today.getDate() - num);

	var y = today.getFullYear();
	var m = today.getMonth() + 1;
	var d = today.getDate();

	if(m < 10){
		m = "0"+m;
	}
	if(d < 10){
		d = "0"+d;
	}
	return y+"-"+m+"-"+d;
}

function checkDate(date1,date2){
	if(date1 == '' && date2 == ''){
		return 0;
	}
	if(date1 != '' && date2 == ''){
		$.messager.alert('提示','请选择结束日期!','error');
		return 1;
	}
	if(date1 == '' && date2 != ''){
		$.messager.alert('提示','请选择开始日期!','error');
		return 1;
	}

	var arrdate1 = date1.split('-');
 	var arrdate2 = date2.split('-');

 	var date1Date = new Date(parseInt(arrdate1[0],10), parseInt(arrdate1[1],10) - 1, parseInt(arrdate1[2],10), 0, 0, 0);
 	var date2Date = new Date(parseInt(arrdate2[0],10), parseInt(arrdate2[1],10) - 1, parseInt(arrdate2[2],10), 0, 0 ,0);
 	if(date1Date.getTime() > date2Date.getTime()) {
 		$.messager.alert('提示','开始时间应在截至日期之前!','error');
 		return 1;
 	}else{
 		return 2;
 	}
}

function getParameters(){
	var css1 = $("#bymajarLI").attr("class");
	var css2 = $("#byschoolLI").attr("class");
	var css3 = $("#byareaLI").attr("class");

	var val = '';
	
	if(css1 == 'on'){
		val = $('#zykFullname').combogrid('getValue');
	}else if(css2 == 'on'){
		val = $('#zykInstituteInCharge').combogrid('getValue');
	}else if(css3 == 'on'){
		val = $('#zykCity').combogrid('getValue');
	}
	
	if(val == ''){
		$.messager.alert('提示','请先选择一个资源库!','error');
		return false;
	}

	var beginDate = $('#beginDate').val();
	var endDate = $('#endDate').val();
	var result = checkDate(beginDate,endDate);

	if(result == 0){
		var date1 = $("#date1LI").attr("class");
		var date2 = $("#date2LI").attr("class");
		var date3 = $("#date3LI").attr("class");
		
		if(date1 == 'on'){
			beginDate = getDate(7);
			endDate = getToDay();
		}else if(date2 == 'on'){
			beginDate = getDate(30);
			endDate = getToDay();
		}else if(date3 == 'on'){
			beginDate = getDate(90);
			endDate = getToDay();
		}
	}else if(result == 1){
		return false;
	}
	
	return 'zykId='+val+'&beginDate='+beginDate+'&endDate='+endDate;
}

function generateExportData(key,value){
	if(!key || !value) return null;
	var result = "";
	if(typeof value[0] == "object"){
		for(var i = 0 ; i < value.length; i++){
			result += value[i].name+":"+value[i].value;
			if(i < value.length -1){
				result+=",";
			}
		}
	}else{
		for(var i = 0 ;i < key.length; i++){
			result+=key[i]+":"+value[i];
			if(i < key.length - 1){
				result+=",";
			}
		}
	}
	return result;
}

function generateExportTotalData(key,value,total){
	if(!key || !value || !total) return null;
	var result = "";
	if(typeof value[0] == "object"){
		for(var i = 0 ; i < value.length; i++){
			result += value[i].name+":"+value[i].value+":"+(value[i].value/total*100).toFixed(2)+"%25";
			if(i < value.length -1){
				result+=",";
			}
		}
	}else{
		for(var i = 0 ;i < key.length; i++){
			result+=key[i]+":"+value[i]+":"+(value[i].value/total*100).toFixed(2)+"%25";
			if(i < key.length - 1){
				result+=",";
			}
		}
	}
	return result;
}

function getAnnularExportData(){
	var result = "";
	var total = arguments[arguments.length -1];
	for(var i = 0 ; i < arguments.length; i++){
		if(typeof arguments[i] == 'object'){
			var arr = arguments[i];
			for(var k = 0; k < arr.length; k++){
				var obj = arr[k];
				if(obj.name != 'other'){
					result += obj.name+":"+obj.value+":"+(obj.value/total*100).toFixed(2)+"%25";
				}
			}
			if(i < arguments.length - 2){
				result+=",";
			}
		}
	}
	return result;
}