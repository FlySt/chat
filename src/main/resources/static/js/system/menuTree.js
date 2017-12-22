var tree;
var selectNode = null;
var setting = {
    async: {
        enable: true,
        url: "/menus/treeData",
        type:"get"
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
        onAsyncSuccess: zTreeOnAsyncSuccess,
        onClick: zTreeOnClick
    }
};

/**
 * 点击事件
 */
function zTreeOnClick(event, treeId, treeNode) {
    selectNode = treeNode;
}
/**
 * 异步加载结束调用
 */
function zTreeOnAsyncSuccess() {
    // 默认展开全部节点
    tree.expandAll(true);
}


$(function () {
    tree = $.fn.zTree.init($("#treeDemo"), setting);
});