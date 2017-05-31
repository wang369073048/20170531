$(function(){
	$('#beginDate').datebox({
		currentText : '今天',
		closeText : '关闭',
		formatter : formatDate
	});
	
	$('#endDate').datebox({
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