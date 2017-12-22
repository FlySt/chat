"use strict";

/**
 * 菜单管理初始化
 */
var Menu = {
    id: "menuTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};
/**
 * 初始化表格的列
 */
Menu.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '链接', field: 'href', align: 'center', valign: 'middle', sortable: true},
        {title: '权限', field: 'permission', align: 'center', valign: 'middle', sortable: true},
    ]
};

/**
 * 检查是否选中
 */
Menu.check = function () {
    var selected = $('#' + this.id).bootstrapTreeTable('getSelections');
    if (selected.length === 0) {
        toastr.error("请先选中表格中的某一记录！");
        return false;
    } else {
        Menu.seItem = selected[0];
        return true;
    }
};
/**
 * 获取添加界面
 */
Menu.add = function () {
    // 加载其他模块的页面到右侧工作区
    $.ajax({
        url: "/menus/add",
        success: function (data) {
            $(".main").html(data);
        },
        error: function () {
            toastr.error("error!");
        }
    });
};
/**
 * 获取编辑界面
 */
Menu.edit = function () {
    if (Menu.check()) {
        // 加载其他模块的页面到右侧工作区
        $.ajax({
            url: "/menus/edit/" + Menu.seItem.id,
            success: function (data) {
                $(".main").html(data);
            },
            error: function () {
                toastr.error("error!");
            }
        });
    }
};
/**
 * 删除
 */
Menu.delete = function () {
    if (Menu.check()) {
        bootbox.setLocale("zh_CN");
        bootbox.confirm("您确定要删除该用户吗?", function (result) {
            if (result) {
                $.ajax({
                    url: "/menus/" + Menu.seItem.id,
                    type: 'DELETE',
                    success: function (data) {
                        if (data.success) {
                            // 从新刷新主界面
                            //  getMenu();
                            Menu.table.refresh();
                        } else {
                            toastr.error(data.message);
                        }
                    },
                    error: function () {
                        toastr.error("error!");
                    }
                });
            }
        })
    }
};


$(function () {
    var defaultColumns = Menu.initColumn();
    var table = new BSTreeTable(Menu.id, "/menus/list", defaultColumns);
    table.setExpandColumn(1);
    table.setIdField("id");
    table.setCodeField("id");
    table.setParentCodeField("parentId");
    table.setExpandAll(true);
    table.setRootCodeValue($("#menuRootId").val());
    table.init();
    Menu.table = table;
});