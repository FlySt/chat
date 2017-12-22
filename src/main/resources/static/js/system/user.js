/**
 * 初始化
 */
var User = {
    id: "userTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 初始化表格的列
 */
User.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '用户名', field: 'username', align: 'center', valign: 'middle', sortable: true}
    ]
};

/**
 * 删除
 */
User.delete = function () {
    bootbox.setLocale("zh_CN");
    bootbox.confirm("您确定要删除该用户吗?", function (result) {
        if (result) {
            $.ajax({
                url: "/users/" + User.seItem.id,
                type: 'DELETE',
                success: function (data) {
                    if (data.success) {
                        // 从新刷新主界面
                        toastr.info(data.message);
                        User.table.refresh();
                    } else {
                        toastr.error(data.message);
                    }
                },
                error: function () {
                    toastr.error("error!");
                }
            });
        }
    });
};
/**
 * 添加
 */
User.add = function () {
    $.ajax({
        url: "/users/add",
        success: function (data) {
            $(".main").html(data);
        },
        error: function () {
            toastr.error("error!");
        }
    });
};
/**
 * 修改
 */
User.edit = function () {
    $.ajax({
        url: "/users/edit/" + User.seItem.id,
        success: function (data) {
            $(".main").html(data);
        },
        error: function () {
            toastr.error("error!");
        }
    });
};

/**
 * 检查选中
 */
User.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length > 0) {
        $("#btn_edit").attr('disabled', false);
        $("#btn_delete").attr('disabled', false);
        User.seItem = selected[0];
    } else {
        $("#btn_edit").attr('disabled', true);
        $("#btn_delete").attr('disabled', true);
    }
};
/**
 * 保存
 */
User.save=function () {
    $.ajax({
        url: "/users",
        type: 'POST',
        data: $('#userForm').serialize(),
        success: function (data) {
            $('#userForm')[0].reset();
            if (data.success) {
                // 从新刷新主界面
                getUser();
                //    location.href = "/users";
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
User.cancel = function () {
    getUser();
};
function getUser() {
    $.ajax({
        url: "/users",
        contentType: 'application/json',
        type: "get",
        data: {
            "async": true,
        },
        success: function (data) {
            $(".main").html(data);
        },
        error: function () {
            toastr.error("error!");
        }
    });
}
var defaultColumns = User.initColumn();
$(function () {

    //列表初始化
    var table = new BSTable(User.id, "/users/list", defaultColumns);
    table.setPaginationType("server");
    table.setHeight(550);

    table.init();
    User.table = table;

    table.btInstance.on("check.bs.table check-all.bs.table uncheck.bs.table uncheck-all.bs.table load-success.bs.table", function (row) {
        User.check();
    });
});
