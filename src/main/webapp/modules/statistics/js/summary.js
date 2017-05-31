$(function () {
	$("div.tableheader-tab li a").click(function () {
		$("div.tableheader-tab li").removeClass("on");
		$(this).parent("li").addClass("on");
		
		var id = $(this).parent("li").attr('id');
        var url = "";
        if(id=='1'){
        	url = path+'/report_summary/totality.action';
        }else if(id=='2'){
        	url = path+'/report_summary/declareConstruction.action';
        }else if(id=='3'){
        	url = path+'/report_summary/distribution.action';
        }

        $('#contentFrame').attr('src','');
		$('#contentFrame').attr('src',url);
	});
}); 