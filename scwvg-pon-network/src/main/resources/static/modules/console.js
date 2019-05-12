/**

 @Name：layuiAdmin 主页控制台
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：GPL-2
    
 */


layui.define(function(exports){
  
  /*
    下面通过 layui.use 分段加载不同的模块，实现不同区域的同时渲染，从而保证视图的快速呈现
  */
  
  
  //区块轮播切换
  layui.use(['admin', 'carousel'], function(){
    var $ = layui.$
    ,admin = layui.admin
    ,carousel = layui.carousel
    ,element = layui.element
    ,device = layui.device();

    //轮播切换
    $('.layadmin-carousel').each(function(){
      var othis = $(this);
      carousel.render({
        elem: this
        ,width: '100%'
        ,arrow: 'none'
        ,interval: othis.data('interval')
        ,autoplay: othis.data('autoplay') === true
        ,trigger: (device.ios || device.android) ? 'click' : 'hover'
        ,anim: othis.data('anim')
      });
    });
    
    element.render('progress');
    
  });

  //数据概览
  layui.use(['admin', 'carousel', 'echarts'], function(){
    var $ = layui.$
    ,admin = layui.admin
    ,carousel = layui.carousel
    ,echarts = layui.echarts;
    
    var mapData = null;
    $.ajax({
		type: "GET",
		url: "/ech/queryAllOLT?token=" + localStorage.getItem("token"),
		async: false,
		data: {},
		dataType: "html",
		success: function(result) {
			mapData = JSON.parse(result);
		}
	});
    
    var echartsApp = [], options = [
      //今日流量趋势
      {
        title: {
          text: '黑龙江省市地图',
          x: 'center',
          textStyle: {
            fontSize: 20
          }
        },
        tooltip : {
          trigger: 'item',
          formatter: function (params,ticket,callback) {
              console.log(params);
              var seriesName = params.seriesName;
              var values = params.data.valueMap;
              var seriesNameArray = seriesName.split(" ");
              var res = "";
              for (var i = 0; i < seriesNameArray.length - 1; i++) {
            	  if(i == 0) {
            		  res += seriesNameArray[i] + ' : ' + values[i];
            	  } else {
            		  res += '<br/>' + seriesNameArray[i] + ' : ' + values[i];
            	  }
              }
//              setTimeout(function (){
//                  // 仅为了模拟异步回调
//                  callback(ticket, res);
//              }, 1000)
              return res;
          },
          textStyle: {
        	  fontSize: '10'
          }
        },
        legend: {
          orient: 'vertical',
	      x: 'left',
	      data:['告警'],
	      show: false,
	      textStyle: {
	        color: "white"
	      }
        },
        dataRange: {
          min: 0,
          max: 1000,
          color:['#003266','#1a8bff'],
          text:['高','低'],           // 文本，默认为数值文本
          calculable : true,
          show: false
        },
        series : [
            {
                name: 'OLT设备',
                type: 'map',
                mapType: '黑龙江',
                hoverable: true,
                roam:false,
                selectedMode : 'single',
                itemStyle:{
                    normal: {
                    	borderColor: '#87cefa',
                    	borderWidth: 1,
                    	label: {
                    		show: true,
                    		textStyle: {
	                      		color: 'white'
	                      	},
                    	}
                    },
                    emphasis: {
                    	borderColor: '#1e90ff',
                    	borderWidth: 1,
                    	color: '#3399ff',
                    	textStyle: {
                    		color: 'white',
                    		fontSize: '20px'
                    	},
                    	label: {
                    		show: true
                    	}
                    }
                },
                data: mapData
            },
            {
                name: 'ONU设备',
                type: 'map',
                mapType: '黑龙江',
                hoverable: true,
                roam:false,
                selectedMode : 'single',
                itemStyle:{
                    normal: {
                    	borderColor: '#87cefa',
                    	borderWidth: 1,
                    	label: {
                    		show: true
                    	}
                    },
                    emphasis: {
                    	borderColor: '#1e90ff',
                    	borderWidth: 1,
                    	color: '#E788C3',
                    	textStyle: {
                    		color: '#fff',
                    		fontSize: '20px'
                    	},
                    	label: {
                    		show: false
                    	}
                    }
                },
                data:[ 
					{name: "齐齐哈尔市", value: Math.round(Math.random()*1000)},
					{name: "牡丹江市", value: Math.round(Math.random()*1000)},
					{name: '大兴安岭地区',value: Math.round(Math.random()*1000)},
					{name: '鸡西市',value: Math.round(Math.random()*1000)},
					{name: '佳木斯市',value: Math.round(Math.random()*1000)},
					{name: '双鸭山市',value: Math.round(Math.random()*1000)},
					{name: '大庆市',value: Math.round(Math.random()*1000)},
					{name: '鹤岗市',value: Math.round(Math.random()*1000)},
					{name: '黑河市',value: Math.round(Math.random()*1000)},
					{name: '哈尔滨市',value: Math.round(Math.random()*1000)},
					{name: '绥化市',value: Math.round(Math.random()*1000)},
					{name: '七台河市',value: Math.round(Math.random()*1000)},
					{name: '伊春市',value: Math.round(Math.random()*1000)},
                ],
            },
            {
                name: '告警',
                type: 'map',
                mapType: '黑龙江',
                hoverable: true,
                roam:false,
                selectedMode : 'single',
                itemStyle:{
                    normal: {
                    	borderColor: '#87cefa',
                    	borderWidth: 1,
                    	label: {
                    		show: true
                    	}
                    },
                    emphasis: {
                    	borderColor: '#1e90ff',
                    	borderWidth: 1,
                    	color: '#E788C3',
                    	textStyle: {
                    		color: '#fff',
                    		fontSize: '20px'
                    	},
                    	label: {
                    		show: false
                    	}
                    }
                },
                data:[ 
					{name: "齐齐哈尔市", value: Math.round(Math.random()*1000)},
					{name: "牡丹江市", value: Math.round(Math.random()*1000)},
					{name: '大兴安岭地区',value: Math.round(Math.random()*1000)},
					{name: '鸡西市',value: Math.round(Math.random()*1000)},
					{name: '佳木斯市',value: Math.round(Math.random()*1000)},
					{name: '双鸭山市',value: Math.round(Math.random()*1000)},
					{name: '大庆市',value: Math.round(Math.random()*1000)},
					{name: '鹤岗市',value: Math.round(Math.random()*1000)},
					{name: '黑河市',value: Math.round(Math.random()*1000)},
					{name: '哈尔滨市',value: Math.round(Math.random()*1000)},
					{name: '绥化市',value: Math.round(Math.random()*1000)},
					{name: '七台河市',value: Math.round(Math.random()*1000)},
					{name: '伊春市',value: Math.round(Math.random()*1000)},
                ],
            },
        ]
      },
      
      //访客浏览器分布
      { 
        title : {
          text: '访客浏览器分布',
          x: 'center',
          textStyle: {
            fontSize: 14
          }
        },
        tooltip : {
          trigger: 'item',
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          orient : 'vertical',
          x : 'left',
          data:['Chrome','Firefox','IE 8.0','Safari','其它浏览器']
        },
        series : [{
          name:'访问来源',
          type:'pie',
          radius : '55%',
          center: ['50%', '50%'],
          data:[
            {value:9052, name:'Chrome'},
            {value:1610, name:'Firefox'},
            {value:3200, name:'IE 8.0'},
            {value:535, name:'Safari'},
            {value:1700, name:'其它浏览器'}
          ]
        }]
      },
      
      //新增的用户量
      {
        title: {
          text: '最近一周新增的用户量',
          x: 'center',
          textStyle: {
            fontSize: 14
          }
        },
        tooltip : { //提示框
          trigger: 'axis',
          formatter: "{b}<br>新增用户：{c}"
        },
        xAxis : [{ //X轴
          type : 'category',
          data : ['11-07', '11-08', '11-09', '11-10', '11-11', '11-12', '11-13']
        }],
        yAxis : [{  //Y轴
          type : 'value'
        }],
        series : [{ //内容
          type: 'line',
          data:[200, 300, 400, 610, 150, 270, 380],
        }]
      }
    ]
    ,elemDataView = $('#LAY-index-dataview').children('div')
    ,loadOther = function(city_id, city_name) {
		    	var oltPieData = null;
		    	var oltVendor = null;
		    	var onuPieData = null;
		    	$.ajax({
		    		type: "GET",
		    		url: "/ech/queryAll?token=" + localStorage.getItem("token"),
		    		async: false,
		    		data: {"city_id": city_id},
		    		dataType: "html",
		    		success: function(result) {
		    			result = JSON.parse(result);
		    			oltPieData = result.olt;
		    			onuPieData = result.onu;
		    			oltVendor = result.olt_vendor;
		    		}
		    	});
		    	
		    	// 全省OLT设备饼图
	  		    var echOltPip = [], oltPipOp = [
	  		      {
	  		    	title : {
	  		  	        text: city_name,
	  		  	        subtext: 'OLT设备',
	  		  	        x:'center',
	  		  	        textStyle: {
	  		  	        	fontSize: '14',
	  		  	        }
	  		  	    },
	  		  	    tooltip : {
	  		  	        trigger: 'item',
	  		  	        formatter: "{b}: {c} <br/>比例: {d}%",
		  		  	    textStyle: {
		  		  	    	fontSize: '8'
	  		  	        }
	  		  	    },
	  		  	    legend: {
	  		  	        orient : 'vertical',
	  		  	        x : 'left',
	  		  	        data:[
	  		  	        	{name: '华为', textStyle: {}},
	  		  	        	{name: '中兴', textStyle: {}},
	  		  	        	{name: '烽火', textStyle: {}}],
	  		  	        textStyle: {
	  	  	            }
	  		  	    },
	  		  	    calculable: true,
	  		  	    itemStyle : {
	  		              normal : {
	  		                  label : {
	  		                      position : 'inner'
	  		                  },
	  		                  labelLine : {
	  		                      show : true
	  		                  },
	  		                  color: 'white',
		  		              textStyle: {
		  		            	  fontSize: '12'
		                      },
	  		              },
	  		            emphasis: {
	                    	borderColor: '#1e90ff',
	                    	borderWidth: 1,
	                    	color: '#3399ff',
	                    	label: {
	                    		show: true,
	                    		textStyle: {
		                    		color: 'white',
		                    		fontSize: '12'
		                    	},
	                    	}
	                    }
	  		          },
	  		  	    series: [
	  		  	    	{
	  		  	    		name: 'OLT设备',
	  		  	    		type: 'pie',
	  		  	    		selectedMode: 'single',
	  		  	    		radius: '30%',
	  		  	    		center: ['45%', '65%'],
	  		  	    		data: oltPieData,
	  		  	    		textStyle: {
	  		  	    			color: "white"
	  		  	    		}
	  		  	    	}
	  		  	    ]
	  		      }
	  		    ]
	  		    ,elemOltPip = $('#LAY-index-olt-pie')
	  		    ,rendeOltPip = function(index){
	  		      echOltPip[index] = echarts.init(elemOltPip[index], layui.echartsTheme);
	  		      echOltPip[index].setOption(oltPipOp[index]);
	  		      echOltPip[index].on("pieSelected", function(param) {
		  			  // 跳转到资源列表页面
		  			  $("#toOlt")[0].click();
		  		  });
	  		      window.onresize = echOltPip[index].resize;
	  		    };
	  		    if(!elemOltPip[0]) return;
	  		    rendeOltPip(0);
	  		    
	  		    //标准折线图
	  		    var echOnuPip = [], oltOnuOp = [
	  		      {
	  		    	title : {
	  		  	        text: city_name,
	  		  	        subtext: 'ONU设备',
	  		  	        x:'center',
	  		  	        textStyle: {
	  		  	        	fontSize: '14',
	  		  	        }
	  		  	    },
	  		  	    tooltip : {
	  		  	        trigger: 'item',
	  		  	        formatter: "{b}: {c}<br/>比例: {d}%",
		  		  	    textStyle: {
	  		  	        	fontSize: '8',
	  		  	        }
	  		  	    },
	  		  	    calculable: true,
	  		  	    itemStyle : {
	  		              normal : {
	  		                  label : {
	  		                      position : 'inner'
	  		                  },
	  		                  labelLine : {
	  		                      show : true
	  		                  }
	  		              }
	  		          },
	  		  	    series: [
	  		  	    	{
	  		  	    		name: 'OLT设备',
	  		  	    		type: 'pie',
	  		  	    		selectedMode: 'single',
	  		  	    		radius: '30%',
	  		  	    		center: ['45%', '65%'],
	  		  	    		data: onuPieData
	  		  	    	}
	  		  	    ]
	  		      }
	  		    ]
	  		    ,elemOnuPip = $('#LAY-index-onu-pie')
	  		    ,rendeOnuPip = function(index){
	  		      echOnuPip[index] = echarts.init(elemOnuPip[index], layui.echartsTheme);
	  		      echOnuPip[index].setOption(oltOnuOp[index]);
	  		      echOnuPip[index].on("pieSelected", function(param) {
//	  		    	  var selected = param.selected;
//	  		    	  var serie;
//	  		    	  for (var idx in selected) {
//	  		    		  if (selected[idx]) {
//	  		    			  serie = option.series[idx];
//	  		    			alert(serie);
//	  		    		  }
//	  		    	  }
	  		    	  // 跳转到资源列表页面
	  		    	  $("#toOnu")[0].click();
	  		      });
	  		      window.onresize = echOnuPip[index].resize;
	  		    };
	  		    if(!elemOnuPip[0]) return;
	  		    rendeOnuPip(0);
	  		    
	  		    //标准折线图
	  		    var echOltAlarmPip = [], oltAlarmOp = [
	  		      {
	  		    	title : {
	  		  	        text: city_name,
	  		  	        subtext: '告警',
	  		  	        x:'center',
	  		  	        textStyle: {
	  		  	        	fontSize: '14',
	  		  	        }
	  		  	    },
	  		  	    tooltip : {
	  		  	        trigger: 'item',
	  		  	        formatter: "{b}: {c}<br/>比例: {d}%",
		  		  	    textStyle: {
	  		  	        	fontSize: '8',
	  		  	        }
	  		  	    },
	  		  	    calculable: true,
	  		  	    series: [
	  		  	    	{
	  		  	    		name: '告警',
	  		  	    		type: 'pie',
	  		  	    		selectedMode: 'single',
	  		  	    		radius: '30%',
	  		  	    		center: ['45%', '65%'],
		  		  	    	
	  		  	    		data: [
		  		  	    		{name: "齐齐哈尔", value: Math.round(Math.random()*1000)},
		  						{name: "牡丹江", value: Math.round(Math.random()*1000)},
		  						{name: '大兴安岭',value: Math.round(Math.random()*1000)},
		  						{name: '鸡西市',value: Math.round(Math.random()*1000)},
		  						{name: '佳木斯',value: Math.round(Math.random()*1000)},
		  						{name: '双鸭山',value: Math.round(Math.random()*1000)},
		  						{name: '大庆',value: Math.round(Math.random()*1000)},
		  						{name: '鹤岗',value: Math.round(Math.random()*1000)},
		  						{name: '黑河',value: Math.round(Math.random()*1000)},
		  						{name: '哈尔滨',value: Math.round(Math.random()*1000)},
		  						{name: '绥化',value: Math.round(Math.random()*1000)},
		  						{name: '七台河',value: Math.round(Math.random()*1000)},
		  						{name: '伊春',value: Math.round(Math.random()*1000)},
	  		  	    		]
	  		  	    	},
	  		  	    ]
	  		      }
	  		    ]
	  		    ,elemOltAlarmPip = $('#LAY-index-olt-alarm-pie')
	  		    ,rendeOltAlarmPip = function(index){
	  		      echOltAlarmPip[index] = echarts.init(elemOltAlarmPip[index], layui.echartsTheme);
	  		      echOltAlarmPip[index].setOption(oltAlarmOp[index]);
	  		      echOltAlarmPip[index].on("pieSelected", function(param) {
		  			  // 跳转到资源列表页面
		  			  $("#toAm")[0].click();
		  		  });
	  		      window.onresize = echOltAlarmPip[index].resize;
	  		    };
	  		    if(!elemOltAlarmPip[0]) return;
	  		      rendeOltAlarmPip(0);
		    }
    ,renderDataView = function(index){
      if(index == 0) {
    	echartsApp[index] = echarts.init(elemDataView[index].children[1], layui.echartsTheme);
    	loadOther('all', '全省');
    	echartsApp[index].on("mapSelected", function(d) {
	    	  var selected = d.selected;
	    	  var sel = null;
	    	  for(var select in selected) {
	    		  if(selected[select]) {
	    			  sel = select;
	    		  }
	    	  }
	    	  
	    	  if(sel) {
	    		  // 加载地市数据
	    		  loadOther(sel, sel);
	    	  } else {
	    		  // 加载全省数据
	    		  loadOther("all", "全省");
	    	  }
	      });
      } else {
    	echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
      }
      echartsApp[index].setOption(options[index]);
      //window.onresize = echartsApp[index].resize;
      admin.resize(function(){
        echartsApp[index].resize();
      });
    };
    
    
    //没找到DOM，终止执行
    if(!elemDataView[0]) return;
    
    
    
    renderDataView(0);
    
    //监听数据概览轮播
    var carouselIndex = 0;
    carousel.on('change(LAY-index-dataview)', function(obj){
      renderDataView(carouselIndex = obj.index);
    });
    
    //监听侧边伸缩
    layui.admin.on('side', function(){
      setTimeout(function(){
        renderDataView(carouselIndex);
      }, 300);
    });
    
    //监听路由
    layui.admin.on('hash(tab)', function(){
      layui.router().path.join('') || renderDataView(carouselIndex);
    });
  });

  //最新订单
  layui.use('table', function(){
    var $ = layui.$
    ,table = layui.table;
    
    //今日热搜
    table.render({
      elem: '#LAY-index-topSearch'
      ,url: layui.setter.base + 'json/console/top-search.js' //模拟接口
      ,page: true
      ,cols: [[
        {type: 'numbers', fixed: 'left'}
        ,{field: 'keywords', title: '关键词', minWidth: 300, templet: '<div><a href="https://www.baidu.com/s?wd={{ d.keywords }}" target="_blank" class="layui-table-link">{{ d.keywords }}</div>'}
        ,{field: 'frequency', title: '搜索次数', minWidth: 120, sort: true}
        ,{field: 'userNums', title: '用户数', sort: true}
      ]]
      ,skin: 'line'
    });
    
    //今日热贴
    table.render({
      elem: '#LAY-index-topCard'
      ,url: layui.setter.base + 'json/console/top-card.js' //模拟接口
      ,page: true
      ,cellMinWidth: 120
      ,cols: [[
        {type: 'numbers', fixed: 'left'}
        ,{field: 'title', title: '标题', minWidth: 300, templet: '<div><a href="{{ d.href }}" target="_blank" class="layui-table-link">{{ d.title }}</div>'}
        ,{field: 'username', title: '发帖者'}
        ,{field: 'channel', title: '类别'}
        ,{field: 'crt', title: '点击率', sort: true}
      ]]
      ,skin: 'line'
    });
  });
  
  exports('console', {})
});