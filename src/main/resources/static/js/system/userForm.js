$(function () {
    init();
});


function init() {
    var ids = $("#roleIds").val().split(",");
    $('input:checkbox').each(function() {
        if($.inArray($(this).val(), ids) !== -1){
            $(this).attr('checked', true);
        }
    })
}