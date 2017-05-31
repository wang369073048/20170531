var zNodes =[ 
	{name:"信息资源库", id:"5", isParent:true}
]; 

var setting = {
	view: {
		selectedMulti: false,
		showLine: true,
		showIcon: showIcon,
		expandSpeed: "slow"
	},
	check: {
		enable: true,
		chkStyle: "checkbox",
		chkboxType: { "Y" : "s", "N" : "s" }
	},
	async: {
		enable: true,
		url: "${pageContext.request.contextPath}/asyncData/orgTree.action",
		autoParam: ["id"],
		otherParam: {"type":"<%=resourceType %>","irrId":"<%=resourceId %>"}
	},
	callback: {
		beforeClick: beforeClick,
		beforeAsync: zTreeBeforeAsync,
		onAsyncError: onAsyncError,
		onAsyncSuccess: onAsyncSuccess
	},
	expandSpeed: "" 
};

function showIcon(treeId, treeNode) {
	return treeNode.id != 1;
};

function zTreeBeforeAsync(treeId, treeNode) {
    return true;
};

function beforeClick(beforeClickbeforeClick, treeNode) {
	return true;
};

function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown){
	alert("异步加载数据出现异常，请联系管理员");
};

function onAsyncSuccess(event, treeId, treeNode, msg) {
	
};

function getTime() {
	var now= new Date(),
	h=now.getHours(),
	m=now.getMinutes(),
	s=now.getSeconds(),
	ms=now.getMilliseconds();
	return (h+":"+m+":"+s+ " " +ms);
};

$(document).ready(function(){
	$.fn.zTree.init($("#orgTree"), setting, zNodes);
	
	$("#validate").bind('click',function(){
		var uIds = '';
	 	var gIds = '';
		var zTree = $.fn.zTree.getZTreeObj("orgTree");
		nodes = zTree.getCheckedNodes(true);
		
		if(nodes.length < 1){
			alert("请至少选择一个用户或部门");
			return;
		}else{
			for (var i=0, l=nodes.length; i<l; i++) {
				if(nodes[i].isParent){
					gIds += nodes[i].id + ";";
				}else{
					uIds += nodes[i].id + ";";
				}
			}

			if(window.confirm('您确定要执行保存操作吗？')){
				$.ajax({
					type: "POST", 
					url: "${pageContext.request.contextPath}//modules/irracl/acl!updateAjax.action",
					data: "resourceType=${type}&resourceId=${irrId}&groupIds="+gIds+"&userIds="+uIds,
					dataType: 'text',
					success: function(data){
						alert(data);
					}
				});
			}
		}
 		window.close();
       }); 
});