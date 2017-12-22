
// DOM 加载完再执行
$(function () {

    // 菜单事件
    $(".items .list-item").click(function () {
        var url = $(this).attr("url");
        // 先移除其他的点击样式，再添加当前的点击样式
        $(".items .list-item").removeClass("active");
        $(this).addClass("active");

        // 加载其他模块的页面到右侧工作区
        $.ajax({
            url: url,
            success: function (data) {
                $(".main").html(data);
            },
            error: function () {
                toastr.error("error");
            }
        });
    });

    // 选中菜单第一项
    $(".items .list-item:first").trigger("click");
});