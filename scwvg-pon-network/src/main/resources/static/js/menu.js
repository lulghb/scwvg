function checkPermission() {
    var pers = [];
    $.ajax({
        type : 'get',
        url : '/menus/owns',
        contentType : "application/json; charset=utf-8",
        async : false,
        success : function(data) {
            pers = data;
            $("[wvgMenus]").each(function() {
                var per = $(this).attr("wvgMenus");
                if ($.inArray(per, data) < 0) {
                    $(this).hide();
                }
            });
        }
    });

    return pers;
}