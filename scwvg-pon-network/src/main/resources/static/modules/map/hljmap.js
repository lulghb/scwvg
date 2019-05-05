layui.define(function(exports){
	
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
	  
	  layui.use(['carousel', 'echarts'], function(){
		    var $ = layui.$
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
		    
		    var echHLJMap = [], HLJMapOp = [
		      {
		    	  title: {
		              text : '黑龙江省市地图',
		              x: "center",
		              textStyle: {
			  	          color: 'white'
			  	      }
		          },
		          tooltip : {
		              trigger: 'item',
		              
		          },
		          legend: {
		              orient : 'vertical',
		  	          x : 'left',
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
		              
		          ]
		      }
		    ]
		    ,elemHLJMap = $('#LAY-index-map')
		    // 加载其它图形
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
	  		  	        text: city_name + 'OLT设备',
	  		  	        x:'center',
	  		  	        textStyle: {
	  		  	        	color: 'white'
	  		  	        }
	  		  	    },
	  		  	    tooltip : {
	  		  	        trigger: 'item',
	  		  	        formatter: "{b} : {c} <br/>比例 : {d}%"
	  		  	    },
	  		  	    legend: {
	  		  	        orient : 'vertical',
	  		  	        x : 'left',
	  		  	        data:[
	  		  	        	{name: '华为', textStyle: {color: 'white'}},
	  		  	        	{name: '中兴', textStyle: {color: 'white'}},
	  		  	        	{name: '烽火', textStyle: {color: 'white'}}],
	  		  	        textStyle: {
	  	  	        	    color: "white"
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
	  		                  color: 'white'
	  		              }
	  		          },
	  		  	    series: [
	  		  	    	{
	  		  	    		name: 'OLT设备',
	  		  	    		type: 'pie',
	  		  	    		selectedMode: 'single',
	  		  	    		radius: '35%',
	  		  	    		center: ['45%', '55%'],
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
	  		      window.onresize = echOltPip[index].resize;
	  		    };
	  		    if(!elemOltPip[0]) return;
	  		    rendeOltPip(0);
	  		    
	  		    //标准折线图
	  		    var echOnuPip = [], oltOnuOp = [
	  		      {
	  		    	title : {
	  		  	        text: city_name + 'ONU设备',
	  		  	        x:'center',
	  		  	        textStyle: {
	  		  	        	color: 'white'
	  		  	        }
	  		  	    },
	  		  	    tooltip : {
	  		  	        trigger: 'item',
	  		  	        formatter: "{b} : {c}<br/>比例 : {d}%"
	  		  	    },
	  		  	    legend: {
	  		  	        orient : 'vertical',
	  		  	        x : 'left',
	  		  	        data:['在线','离线'],
	  		  	        textStyle: {
	  	  	        	    color: "white"
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
	  		  	    		radius: '35%',
	  		  	    		center: ['45%', '55%'],
	  		  	    		data: onuPieData
	  		  	    	}
	  		  	    ]
	  		      }
	  		    ]
	  		    ,elemOnuPip = $('#LAY-index-onu-pie')
	  		    ,rendeOnuPip = function(index){
	  		      echOnuPip[index] = echarts.init(elemOnuPip[index], layui.echartsTheme);
	  		      echOnuPip[index].setOption(oltOnuOp[index]);
	  		      window.onresize = echOnuPip[index].resize;
	  		    };
	  		    if(!elemOnuPip[0]) return;
	  		    rendeOnuPip(0);
		    }
		    ,renderHLJMap = function(index){
		      echHLJMap[index] = echarts.init(elemHLJMap[index], layui.echartsTheme);
		      echHLJMap[index].setOption(HLJMapOp[index]);
		      window.onresize = echHLJMap[index].resize;
		    };   
		    if(!elemHLJMap[0]) return;
		    renderHLJMap(0);
		    
		    // 地图选择事件
		    var xxx = "aaa";
		      echHLJMap[0].on("mapSelected", function(d) {
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
		    
		    // 加载全省数据
  		    loadOther("all", "全省");
		  });

	exports('hljmap', {})
});