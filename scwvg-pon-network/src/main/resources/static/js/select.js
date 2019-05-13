/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/12
 * @Explain：下拉框公共类查询
 **/
var token=localStorage.getItem("token");
initSelectUserId(); //用户身份类型
initSelectSpec();
initSelectCity();
initSelectRole();
function renderSelect() {
    //重新渲染 不然无法显示
    layui.form.render("select");
}
function initSelectUserId() {
    $.ajax({
        type:"get",
        url:"/selects/getUserTypeAll?token"+token,
        success:function (data) {
            var userID=$("#userid");
            $.each(data,function (i,item) {
                userID.append("<option value="+item.user_type_id+">"+item.user_type_name+"</option>");
            })
            renderSelect();
        },
    });
}
function initSelectSpec() {
    $.ajax({
        type:"get",
        url:"/selects/getUserSpec?token"+token,
        success:function (data) {
            var userID=$("#userspec");
            $.each(data,function (i,item) {
                if(item.spec_id !=1){
                    userID.append("<option value="+item.spec_id+">"+item.spec_name+"</option>");
                }
            })
            renderSelect();
        },
    });
}
function initSelectCity() {
    $.ajax({
        type:"get",
        url:"/selects/getUserCity?token"+token,
        success:function (data) {
            var userID=$("#usercity");
            $.each(data,function (i,item) {
                if(item.spec_id !=1){
                    userID.append("<option value="+item.city_id+">"+item.city_name+"</option>");
                }
            })
            renderSelect();
        },
    });
}

function initSelectRole(){
    $.ajax({
        type:"get",
        url:"/selects/getUserRole?token"+token,
        success:function (data) {
            var userID=$("#userrole");
            $.each(data,function (i,item) {
                if(item.spec_id !=1){
                    userID.append("<option value="+item.wvg_role_id+">"+item.wvg_role_description+"</option>");
                }
            })
            renderSelect();
        },
    });
}

