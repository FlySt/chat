var tree;
var setting = {
    async: {
        enable: true,
        url: "/menus/treeData",
        type:"get"
    },
    check: {
        enable: true
    },
    data: {
        simpleData: {
            enable: true,
            idKey : "id",
            pIdKey : "pId",
            rootPId : -1
        }
    },
    callback: {
        onAsyncSuccess: zTreeOnAsyncSuccess
    }
};

/**
 * 异步加载结束调用
 */
function zTreeOnAsyncSuccess() {
    // 默认选择节点
    var ids = $("#menuIds").val().split(",");//"${role.menuIds}".split(",");
    for(var i=0; i<ids.length; i++) {
        var node = tree.getNodeByParam("id", ids[i]);
        try{tree.checkNode(node, true, false);}catch(e){}
    }
    // 默认展开全部节点
    tree.expandAll(true);
}

$(function () {
    tree = $.fn.zTree.init($("#treeDemo"), setting);
});