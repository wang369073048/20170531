$(function(){
	$('#brithDate').datebox({
		currentText : '今天',
		closeText : '关闭',
		formatter : formatDate
	});
	
	$('#entryDate').datebox({
		currentText : '今天',
		closeText : '关闭',
		formatter : formatDate
	});
	
	$(".datebox :text").attr("readonly","readonly");
})

function formatDate(v){
	if (v instanceof Date) {
		var y = v.getFullYear();
		var m = v.getMonth() + 1;
		var d = v.getDate();
		var h = v.getHours();
		var i = v.getMinutes();
		var s = v.getSeconds();
		
		if(m < 10)
			m = "0"+m;
		if(d < 10)
			d = "0"+d;
		if (h > 0 || i > 0 || s > 0){
			if(h < 10)
				h = "0"+h;
			if(i < 10)
				i = "0"+i;
			if(s < 10)
				s = "0"+s;
			return y + '-' + m + '-' + d + ' ' + h + ':' + i + ':' + s;
		}else{
			return y + '-' + m + '-' + d;
		}
	}
	return '';
}

$.extend($.fn.validatebox.defaults.rules,{
    idcard: {
        validator: function (value) {
            var reg = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
            var year = parseInt(value.substring(6,10));
            var month = parseInt(value.substring(10,12));
            var day = parseInt(value.substring(12,14));
            
            if(month<=0||month>12){
            	this.message = "出生月份不能大于12,小于或等于0";
            	return false;
            }
            if(day<=0||day>31){
            	this.message = "出生日期(天)不能大于31,小于或等于0";
            	return false;
            }
            var nowDate = new Date();
            var nowYear = parseInt(nowDate.getFullYear());
            var nowMonth = parseInt(nowDate.getMonth()+1);
            var nowDay = parseInt(nowDate.getDate());
            
            if(year>nowYear||(year==nowYear&&month>nowMonth)||(year==nowYear&&month==nowMonth&&day>nowDay)){
            	this.message = "出生日期不能大于今天!";
            	return false;
            }
            return reg.test(value);
        },
        message:'请输入正确的身份证号码'
    }
});

$(function(){
    $('#identityCard').blur(function(){
        var identity = $(this).val();
        var year = identity.substring(6,10);
        var month = identity.substring(10,12);
        var day = identity.substring(12,14);
        if(identity.length!=18){
            return;
        }
        var birthdate = year + "-" + month + "-" + day;
        $('#brithDate').datebox('setValue',birthdate);
    });
})