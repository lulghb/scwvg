/**
 * @aothor: lul
 * @unit: 智谷园网络科技有限公司
 * @iphone:18482297774
 * @date：20192019/5/12
 * @Explain：指令库下拉框选择
 **/
layui.config({
    base: '/' //静态资源所在路径
}).extend({
    index: 'lib/index' //主入口模块
}).use(['layer', 'form', 'element'], function () {
    var layer = layui.layer
        , form = layui.form
        , element = layui.element

    function renderSelect() {
        //重新渲染 不然无法显示
        layui.form.render("select");
    }

    var token = localStorage.getItem("token");


    queryVendors();
    querySpec();
    queryProtocol();
    queryOptType();

    //宏指令管理
    queryDataTpye();
    queryResType();
    queryIdtBaseInt();
    queryIdtSpec();
    queryCardType();

    function queryVendors() {
        $.ajax({
            type: "get",
            url: "/selects/getResVendorId?token" + token,
            success: function (data) {
                var res_vendor_id = $("#res_vendor_id");
                $.each(data, function (i, item) {
                    res_vendor_id.append("<option value=" + item.res_parent_id + ">" + item.res_vendor_name + "</option>");
                })
                renderSelect();
            },
        });
    }
    function querySpec() {
        $.ajax({
            type:"get",
            url: "/selects/getUserSpec?token" + token,
            success: function (data) {
                var spec_id = $("#spec_id");
                $.each(data, function (i, item) {
                    if(item.spec_id !=1) {
                        spec_id.append("<option value=" + item.spec_id + ">" + item.spec_name + "</option>");
                    }
                })
                renderSelect();
            },
        })
    }
    function queryProtocol() {
        $.ajax({
            type:"get",
            url: "/selects/queryProtocol?token" + token,
            success: function (data) {
                var cmd_protocol_id = $("#cmd_protocol_id");
                $.each(data, function (i, item) {
                    cmd_protocol_id.append("<option value=" + item.cmd_type_id + ">" + item.cmd_type_name + "</option>");
                })
                renderSelect();
            },
        })
    }
    function queryOptType() {
        $.ajax({
            type:"get",
            url: "/selects/queryOptType?token" + token,
            success: function (data) {
                var opt_type_id = $("#opt_type_id");
                $.each(data, function (i, item) {
                    opt_type_id.append("<option value=" + item.opt_type_id + ">" + item.opt_type_name + "</option>");
                })
                renderSelect();
            },
        })
    }

    function queryDataTpye() {
        $.ajax({
            type:"get",
            url:"/selects/queryDataType?token" + token,
            success:function (data) {
                var data_type_id = $("#data_type_id");
                $.each(data, function (i, item) {
                    data_type_id.append("<option value=" + item.data_type_id + ">" + item.data_type_name + "</option>");
                })
                renderSelect();
            },
        })
    }

    function queryResType() {
        $.ajax({
            type:"get",
            url:"/selects/queryResType?token" + token,
            success:function (data) {
                var res_type_id = $("#res_type_id");
                $.each(data, function (i, item) {
                    res_type_id.append("<option value=" + item.res_type_id + ">" + item.res_type_name + "</option>");
                })
                renderSelect();
            },
        })
    }
    function queryIdtBaseInt(){
        $.ajax({
            type:"get",
            url:"/selects/queryIdtBaseInt?token" + token,
            success:function (data) {
                var idt_base_int = $("#idt_base_int");
                $.each(data, function (i, item) {
                    idt_base_int.append("<option value=" + item.base_id + ">" + item.base_name + "</option>");
                })
                renderSelect();
            },
        })
    }
    function queryIdtSpec() {
        $.ajax({
            type:"get",
            url: "/selects/getUserSpec?token" + token,
            success: function (data) {
                var idt_spec_id = $("#idt_spec_id");
                $.each(data, function (i, item) {
                    if(item.spec_id !=1){
                        idt_spec_id.append("<option value=" + item.spec_id + ">" + item.spec_name + "</option>");
                    }
                })
                renderSelect();
            },
        })
    }
    function queryCardType() {
        $.ajax({
            type:"get",
            url: "/selects/queryCardType?token" + token,
            success: function (data) {
                var cmd_card_type = $("#cmd_card_type");
                $.each(data, function (i, item) {
                    cmd_card_type.append("<option value=" + item.card_type_id + ">" + item.card_type_name + "</option>");
                })
                renderSelect();
            },
        })
    }

});

