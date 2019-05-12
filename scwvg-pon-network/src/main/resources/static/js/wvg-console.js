initWvgConsole();
function initWvgConsole() {
    var token=localStorage.getItem("token");
    //快捷菜单
  /*  quickMenu(token);*/
    //厂家支撑
    vendorBrace(token);
    //版本信息
    /*versoinInfo(token);*/
}
function vendorBrace(token){
    $.ajax({
        url:"/console/getVendorBrace?token"+token,
        type:get,
        async:false,
        success:function (data) {
            alert(data);
        }
    })
}