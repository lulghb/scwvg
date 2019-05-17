initMenu();
function initMenu(){
    var token=localStorage.getItem("token");
	 $.ajax({  
	     url:"/menus/current?token="+token,
	     type:"get",  
	     async:false,
	     success:function(data){
	    	 if(!$.isArray(data)){
	    		 location.href='/login';
	    		 return;
	    	 }
	    	 var menu = $("#menu");
	    	 $.each(data, function(i,item){
	             var a = $("<a href='javascript:;'></a>");
	             var css = item.wvg_menu_css;
                 if(css!=null && css!=""){
                     a.append("<i aria-hidden='true' class='layui-icon " + css +"'></i>");
                 }
	             a.append("<cite>"+item.wvg_menu_name+"</cite>");
	             a.attr("lay-id", item.wvg_menu_id);
	             
	             var href = item.wvg_menu_url;
	             if(href != null && href != ""){
	                a.attr("lay-href", href);
	             }
	             
	             var li = $("<li data-name=\"home\" class=\"layui-nav-item\"></li>");
	             if (i == 0) {
	            	 li.addClass("layui-nav-itemed");
	             }
	             li.append(a);
	             
	             //二级菜单
	             var child2 = item.child;
	             if(child2 != null && child2.length > 0){
	            	 $.each(child2, function(j,item2){
	            		 var ca = $("<a href='javascript:;'></a>");
                         ca.attr("lay-href", item2.wvg_menu_url);
                         ca.attr("lay-id", item2.wvg_menu_id);
                         
                         var css2 = item2.wvg_menu_css;
                         if(css2!=null && css2!=""){
                        	 ca.append("<i aria-hidden='true' class='fa " + css2 +"'></i>");
                         }
                         
                         ca.append("<cite>"+item2.wvg_menu_name+"</cite>");
                         
                         var dd = $("<dd></dd>");
                         dd.append(ca);
                         
                         var dl = $("<dl class='layui-nav-child'></dl>");
                         dl.append(dd);
                         
                         li.append(dl);
	            	 });
	            }
	            menu.append(li);
	        });
	     }
	 });
}

// 登陆用户昵称
showLoginInfo();
function showLoginInfo(){
	$.ajax({
		type : 'get',
		url : '/users/current',
		async : false,
		success : function(data) {
			//菜单上方用户真实名
            var loginName =$("#loginName");
            loginName.append("<span>"+data.wvg_real_name+"</span>");
            //右上角登录用户名
            var userInfo=$("#userInfo");
            userInfo.append("<cite>"+data.wvg_login_name+"</cite>")
		}
	});
}
function logout(){
	$.ajax({
		type : 'get',
		url : '/logout',
		success : function(data) {
			localStorage.removeItem("token");
            location.href = '/login';
		}
	});
}

var active;

layui.use(['layer', 'element'], function() {
	var $ = layui.jquery,
	layer = layui.layer;
	var element = layui.element; //导航的hover效果、二级菜单等功能，需要依赖element模块
    element.on('nav(demo)', function(elem){
      //layer.msg(elem.text());
    });
	
	  //触发事件  
	   active = {  
	       tabAdd: function(obj){
	    	 var lay_id = $(this).attr("lay-id");
	    	 var title = $(this).html() + '<i class="layui-icon" data-id="' + lay_id + '"></i>';
	         //新增一个Tab项  
	         element.tabAdd('admin-tab', {  
	           title: title,
	           content: '<iframe src="' + $(this).attr('data-url') + '"></iframe>',
	           id: lay_id
	         });  
	         element.tabChange("admin-tab", lay_id);    
	       }, 
	       tabDelete: function(lay_id){
    	      element.tabDelete("admin-tab", lay_id);
    	   },
	       tabChange: function(lay_id){
	         element.tabChange('admin-tab', lay_id);
	       }  
	   };  
	   //添加tab  
	   $(document).on('click','a',function(){  
	       if(!$(this)[0].hasAttribute('data-url') || $(this).attr('data-url')===''){
	    	   return;  
	       }
	       var tabs = $(".layui-tab-title").children();
	       var lay_id = $(this).attr("lay-id");

	       for(var i = 0; i < tabs.length; i++) {
	           if($(tabs).eq(i).attr("lay-id") == lay_id) { 
	        	   active.tabChange(lay_id);
	               return;  
	           }    
	       }  
	       active["tabAdd"].call(this);  
	       resize();  
	   });
	   
	   //toggle左侧菜单  
	   $('.admin-side-toggle').on('click', function() {
	       var sideWidth = $('#admin-side').width();  
	       if(sideWidth === 200) {  
	           $('#admin-body').animate({  
	               left: '0'  
	           });
	           $('#admin-footer').animate({  
	               left: '0'  
	           });  
	           $('#admin-side').animate({  
	               width: '0'  
	           });  
	       } else {  
	           $('#admin-body').animate({  
	               left: '200px'  
	           });  
	           $('#admin-footer').animate({  
	               left: '200px'  
	           });  
	           $('#admin-side').animate({  
	               width: '200px'  
	               });  
	           }  
	       });
	   
	    //手机设备的简单适配
	    var treeMobile = $('.site-tree-mobile'),
	    shadeMobile = $('.site-mobile-shade');
	    treeMobile.on('click', function () {
	        $('body').addClass('site-mobile');
	    });
	    shadeMobile.on('click', function () {
	        $('body').removeClass('site-mobile');
	    });
});