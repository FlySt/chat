/**
 * 保存
 */
Menu.submitAdd = function () {
    $.ajax({
        url: "/menus",
        type: 'POST',
        dataType: 'json',
        async: false,
        data: $('#menuForm').serialize(),
        success: function (data) {
            if (data.success) {
                // 从新刷新主界面
                getMenu();
            } else {
                toastr.error(data.message);
            }
        },
        error: function () {
            toastr.error("error!");
        }
    });
};

/**
 * 取消
 */
Menu.cancel = function () {
    getMenu();
};

/**
 * 获取菜单列表
 */
function getMenu() {
    $.ajax({
        url: "/menus",
        contentType: 'application/json',
        type: 'get',
        cache: false,
        async: false,
        success: function (data) {
            $(".main").html(data);
        },
        error: function () {
            toastr.error("error!");
        }
    });
}

$(function () {

    /**
     * 上级菜单点击事件
     */
    $("#parentName").on("click", function () {
        selectNode = null;
        $("#flipFlop").modal({});
    })

    /**
     * 菜单树确认事件
     */
    $("#sure").on("click", function () {
        if (selectNode !== null) {
            if (selectNode.id == $("#id").val()) {
                toastr.error("不能选择自己做为父节点!");
                return;
            }
            $("#parentId").val(selectNode.id);
            $("#parentName").val(selectNode.name);
        }
    })
});