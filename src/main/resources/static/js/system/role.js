"use strict";

/**
 * 初始化
 */
var Role = {
    id: "roleTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 初始化表格的列
 */
Role.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '角色名', field: 'name', align: 'center', valign: 'middle', sortable: true}

    ]
};

/**
 * 删除
 */
Role.delete = function () {
    bootbox.setLocale("zh_CN");
    bootbox.confirm("您确定要删除该角色吗?", function (result) {
        if (result) {
            $.ajax({
                url: "/roles/" + Role.seItem.id,
                type: 'DELETE',
                success: function (data) {
                    if (data.success) {
                        // 从新刷新主界面
                        toastr.info(data.message);
                        Role.table.refresh();
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
Role.add = function () {
    $.ajax({
        url: "/roles/add",
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
Role.edit = function () {
    $.ajax({
        url: "/roles/edit/" + Role.seItem.id,
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
Role.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length > 0) {
        $("#btn_edit").attr('disabled', false);
        $("#btn_delete").attr('disabled', false);
        Role.seItem = selected[0];
    } else {
        $("#btn_edit").attr('disabled', true);
        $("#btn_delete").attr('disabled', true);
    }
};
/**
 * 保存
 */
Role.save = function () {
    var ids = [], nodes = tree.getCheckedNodes(true);
    for (var i = 0; i < nodes.length; i++) {
        ids.push(nodes[i].id);
    }
    $("#menuIds").val(ids);
    $.ajax({
        url: "/roles",
        type: 'POST',
        dataType: 'json',
        //    async:false,
        data: $('#roleForm').serialize(),
        success: function (data) {
            if (data.success) {
                // 从新刷新主界面
                getRole();
            } else {
            }
        },
        error: function () {
        }
    });
};
/**
 * 取消
 */
Role.cancel = function () {
    getRole();
};

function getRole() {
    $.ajax({
        url: "/roles",
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

var defaultColumns = Role.initColumn();
$(function () {

    var table = new BSTable(Role.id, "/roles/list", defaultColumns);
    table.setPaginationType("server");
    table.setHeight(550);
    table.init();
    Role.table = table;

    table.btInstance.on("check.bs.table check-all.bs.table uncheck.bs.table uncheck-all.bs.table load-success.bs.table", function (row) {
        Role.check();
    });

});