$(function () {
    $("div.second-menu li a").click(function () {
        $("div.second-menu li").removeClass("on");
        $(this).parent("li").addClass("on");
        
        var id = $(this).parent("li").attr('id');
        var url = "";
        if(id=='1'){
        	url = path+'/report_basic/reportBasic.action';
        }else if(id=='2'){
        	url = path+'/modules/statistics/summary/summary.jsp';
        }

        $('#contentFrame').attr('src','');
		$('#contentFrame').attr('src',url);
    });
});