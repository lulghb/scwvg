var token=localStorage.getItem("token");
layui.use('table', function () {
    var $ = layui.$
        , table = layui.table;
//今日用户增减情况
    table.render({
        elem: '#LAY-index-topSearch',
        smartReloadModel: true,// 是否开启智能reload的模式
        cols: [[
            {field: 'city_id', title: '地市编码',align:"center", width: 120},
            {field: 'city_name', title: '地市', minWidth: 120, align:"center",templet: '<div><a href="/page/res/list" target="_blank" class="layui-table-link">{{d.city_name}}</div>'},
            {field: 'userSum', title: '用户总数',align:"center", width: 120,sort: true},
            {field: 'addSum', title: '用户新增数',align:"center", width: 120,sort: true, templet:function (d) {
                if(d.addSum<d.delSum){return "<font color='#FF0000'>"+d.addSum+"</font>";}
                else {return "<font color='#79bd40'>"+d.addSum+"</font>";}
                }},
            {field: 'delSum', title: '用户拆机数',align:"center", width: 120,sort: true},
            {field: 'produce_date', title: '出数日期',align:"center", width: 180}
        ]],
        url: '/publics/getBandUserInfo?token='+token,
        //分页
        request:{
            pageName: 'pageNum',
            limitName: 'pageSize',
            size: 10,
            page: 0
        },
        //获得服务器数据
        parseData:function(res){
            for(var i =0;i<res.items.length;i++){
                if(res.items[i].addSum<res.items[i].delSum){
                    $("td").eq(i).css("background-color","red")
                }
            }
            return{
                success:true,
                data:res.items,
                count:res.totalNum
            };
        },
        page:{
            page:0
        },
        response: {
            statusName: 'success' //规定数据状态的字段名称，默认：code
            ,statusCode: true //规定成功的状态码，默认：0
        },
        toolbar: 'default', //让工具栏左侧显示默认的内置模板
        even: true
    });

    //今日告警产生量
    table.render({
        elem: '#alarm_counts',
        smartReloadModel: true,// 是否开启智能reload的模式
        cols: [[
            {field: 'city_id', title: '地市编码',align:"center", width: 120},
            {field: 'city_name', title: '地市', minWidth: 120, align:"center",templet: '<div><a href="/page/res/list" target="_blank" class="layui-table-link">{{d.city_name}}</div>'},
            {field: 'alarm_mubers', title: '告警量',align:"center", width: 120,sort: true},
            {field: 'alarm_clear', title: '清除数',align:"center", width: 120,sort: true},
            {field: 'affect_muber', title: '影响用户数',align:"center", width: 120,sort: true},
            {field: 'produce_date', title: '日期',align:"center", width: 180}
        ]],
        url: '/publics/getAlarmInfo?token='+token,
        //分页
        request:{
            pageName: 'pageNum',
            limitName: 'pageSize',
            size: 10,
            page: 0
        },
        //获得服务器数据
        parseData:function(res){
            for(var i =0;i<res.items.length;i++){
                if(res.items[i].addSum<res.items[i].delSum){
                    $("td").eq(i).css("background-color","red")
                }
            }
            return{
                success:true,
                data:res.items,
                count:res.totalNum
            };
        },
        page:{
            page:0
        },
        response: {
            statusName: 'success' //规定数据状态的字段名称，默认：code
            ,statusCode: true //规定成功的状态码，默认：0
        },
        toolbar: 'default', //让工具栏左侧显示默认的内置模板
        even: true
    });
    //今日流量拥塞量
    table.render({
        elem: '#flux_counts',
        smartReloadModel: true,// 是否开启智能reload的模式
        cols: [[
            {field: 'city_id', title: '地市编码',align:"center", width: 100},
            {field: 'city_name', title: '地市', minWidth: 80, align:"center",templet:
                '<div><a href="/page/res/list" target="_blank" class="layui-table-link">{{d.city_name}}</div>'},
            {field: 'flux_olt_mubers', title: 'OLT数量',align:"center", width: 100,sort: true},
            {field: 'flux_pon_mubers', title: 'PON口数量',align:"center", width: 120,sort: true},
            {field: 'affect_mubers', title: '影响用户数量',align:"center", width: 120,sort: true},
            {field: 'produce_date', title: '数据日期',align:"center", width: 140}

        ]],
        url: '/publics/getFluxInfo?token='+token,
        //分页
        request:{
            pageName: 'pageNum',
            limitName: 'pageSize',
            size: 10,
            page: 0
        },
        //获得服务器数据
        parseData:function(res){
            return{
                success:true,
                data:res.items,
                count:res.totalNum
            };
        },
        page:{
            page:0
        },
        response: {
            statusName: 'success' //规定数据状态的字段名称，默认：code
            ,statusCode: true //规定成功的状态码，默认：0
        },
        toolbar: 'default', //让工具栏左侧显示默认的内置模板
        even: true
    });


});

initWvgConsole();

function initWvgConsole() {
    var token = localStorage.getItem("token");
    //快捷菜单
    /*  quickMenu(token);*/
    //厂家支撑
    vendorBrace(token);
    //版本信息
    /*versoinInfo(token);*/
}

function vendorBrace(token) {
    $.ajax({
        url: "/publics/getVendorBrace?token" + token,
        type: 'get',
        async: false,
        success: function (data) {
            var emp = 0;
            $.each(data, function (i, item) {
                emp = emp + 1;
                if (emp <= 4) {
                    var vendorInfo1 = $("#vendorInfo1");
                    vendorInfo1.append("<li class=\"layui-col-xs6\"><a class=\"layadmin-backlog-body\">" +
                        "<cite>" + item.res_vendor_name + ":" + "</cite>" +
                        "<cite>" + item.res_vendor_admin_name + ":" + "</cite>" +
                        "<cite>" + item.res_vendor_phone + "</cite>" +
                        "</a></li>");
                }
                else {
                    var vendorInfo2 = $("#vendorInfo2");
                    vendorInfo2.append("<li class=\"layui-col-xs6\"><a class=\"layadmin-backlog-body\">" +
                        "<cite>" + item.res_vendor_name + ":" + "</cite>" +
                        "<cite>" + item.res_vendor_admin_name + "</cite>" +
                        "<cite>" + item.res_vendor_phone + "</cite>" +
                        "</a></li>");
                }
            })
        }
    })
}