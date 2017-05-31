var zNodes =[ 
	{name:"组织机构", id:"1", isParent:true}
]; 

var setting = {
	view: {
		selectedMulti: false,
		showLine: true,
		expandSpeed: "slow"
	},
	async: {
		enable: true,
		url: path+'/orgTree/findGroupFroTree.action',
		autoParam: ["id=parentId"]
	},
	callback: {
		beforeClick: beforeClick,
		onAsyncError: onAsyncError
	},
	expandSpeed: "" 
};

function ajaxDataFilter(treeId, parentNode, childNodes) {
    return childNodes;
};

function beforeClick(beforeClickbeforeClick, treeNode) {
	var url = path+'/modules/org/userList.jsp?groupId='+treeNode.id;
	$('#gridframe').attr('src','');
	$('#gridframe').attr('src',url);
};

function onAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown){
	alert("异步加载数据出现异常，请联系管理员");
};

$(function(){
	$.fn.zTree.init($("#groupTree"), setting, zNodes);
});