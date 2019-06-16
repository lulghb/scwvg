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
              var indexs = params.data.seriesIndex;
              var seriesNameArray = seriesName.split(" ");
              var res = "";
              for (var i = 0; i < seriesNameArray.length - 1; i++) {
                  if(i == 0) {
            		  res += seriesNameArray[i] + ' : ' + values[0];
            	  } else {
            		  res += '<br/>' + seriesNameArray[i] + ' : ' + values[indexs[i]];
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
//        legend: {
//          orient: 'vertical',
//	      x: 'left',
//	      data:['告警'],
//	      show: false,
//	      textStyle: {
//	        color: "white"
//	      }
//        },
//        dataRange: {
//          min: 0,
//          max: 1000,
//          color:['#003266','#1a8bff'],
//          text:['高','低'],           // 文本，默认为数值文本
//          calculable : true,
//          show: false
//        },
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
//                    	borderColor: '#87cefa',
//                    	borderWidth: 1,
                    	label: {
                    		show: true,
                    		textStyle: {
                    			color: 'white'
                    		}
                    	},
                    	areaStyle: {
                      		color: 'green'
                      	},
                    },
                    emphasis: {
                    	label: {
                    		show: true
                    	},
                    	color: "#FFA07A",
                    	areaStyle: {
                      		color: 'black'
                      	},
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
//                    	borderColor: '#87cefa',
//                    	borderWidth: 1,
                    	label: {
                    		show: true
                    	},
                    	labelLine: {
                    		show: true,
                            smooth: 0.2,
                            length: 1,
                    	}
                    },
                    emphasis: {
                    	borderColor: '#1e90ff',
                    	borderWidth: 1,
//                    	color: '#E788C3',
                    	textStyle: {
                    		//color: '#fff',
                    		fontSize: '20px'
                    	},
                    	label: {
                    		show: false
                    	},
                    	areaStyle: {
                      		color: 'black'
                      	},
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
//                    	borderColor: '#87cefa',
//                    	borderWidth: 1,
                    	label: {
                    		show: true
                    	}
                    },
                    emphasis: {
                    	borderColor: '#1e90ff',
                    	borderWidth: 1,
                    	//color: '#E788C3',
                    	textStyle: {
                    		//color: '#fff',
                    		fontSize: '20px'
                    	},
                    	label: {
                    		show: false
                    	}
                    }
                },
                markPoint: {
                	itemStyle: {
                		normal: {
                			label: {
                				show: false
                			},
                			color: 'red'
                		},
                		emphasis: {
                			label: {
                				show: false
                			},
                			color: 'red'
                		}
                	},
                	data: [
                		{tooltip: {show: false},name: "大庆市",value:2000, geoCoord:[124.7717,46.4282],
                			itemStyle: {
                				normal: {
                					areaStyle: {
                						color: 'red'
                					}
                				}
                			}
                		},
                        {tooltip: {show: false},name: "哈尔滨市",values:1000, geoCoord:[127.9688,45.368]},
                	]
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
		  		  	    	itemStyle: {
		  		                normal: {
		  		                    label: {        //此处为指示线文字
		  		                        show: true,
		  		                        textStyle: {
		  		                            fontWeight: 200,
		  		                            fontSize: 10    //文字的字体大小
		  		                        }
		  		                    },
		  		                    labelLine: {    //指示线状态
		  		                        show: true,
		  		                        smooth: 0.2,
		  		                        length: 5,
		  		                    }
		  		                }
		  		            },
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
		  		  	    	itemStyle: {
		  		                normal: {
		  		                    label: {        //此处为指示线文字
		  		                        show: true,
		  		                        textStyle: {
		  		                            fontWeight: 200,
		  		                            fontSize: 10    //文字的字体大小
		  		                        }
		  		                    },
		  		                    labelLine: {    //指示线状态
		  		                        show: true,
		  		                        smooth: 0.2,
		  		                        length: 5,
		  		                    }
		  		                }
		  		            },
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
	  		  	       	//text: city_name,
	  		  	        //subtext: '告警',
	  		    		text: '告警',
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
		  		  	        itemStyle: {
		  		                normal: {
		  		                    label: {        //此处为指示线文字
		  		                        show: true,
		  		                        textStyle: {
		  		                            fontWeight: 200,
		  		                            fontSize: 10    //文字的字体大小
		  		                        }
		  		                    },
		  		                    labelLine: {    //指示线状态
		  		                        show: true,
		  		                        smooth: 0.2,
		  		                        length: 5,
		  		                    }
		  		                }
		  		            },
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
	  		    $("#grave_am").click(function() {
	  		    	rendeOltAlarmPip(0);
	  		    });
	  		    $("#major_am").click(function() {
	  		    	rendeOltAlarmPip(0);
	  		    });
	  		    $("#minor_am").click(function() {
	  		    	rendeOltAlarmPip(0);
	  		    });
	  		    $("#warn_am").click(function() {
	  		    	rendeOltAlarmPip(0);
	  		    });
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


      //今日新增用户量
      var echnormcol = [], normcol = [
          {
              title : {
                  text: '某地区蒸发量和降水量',
              },
              tooltip : {
                  trigger: 'axis'
              },
              legend: {
                  data:['蒸发量','降水量']
              },
              calculable : true,
              xAxis : [
                  {
                      type : 'category',
                      data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                  }
              ],
              yAxis : [
                  {
                      type : 'value'
                  }
              ],
              series : [
                  {
                      name:'蒸发量',
                      type:'bar',
                      data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
                      markPoint : {
                          data : [
                              {type : 'max', name: '最大值'},
                              {type : 'min', name: '最小值'}
                          ]
                      },
                      markLine : {
                          data : [{type : 'average', name: '平均值'}]
                      }
                  },
                  {
                      name:'降水量',
                      type:'bar',
                      data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
                      markPoint : {
                          data : [
                              {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
                              {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
                          ]
                      },
                      markLine : {
                          data : [
                              {type : 'average', name : '平均值'}
                          ]
                      }
                  }
              ]
          }
      ]
          ,elemNormcol = $('#LAY-index-normcol').children('div')
          ,renderNormcol = function(index){
          echnormcol[index] = echarts.init(elemNormcol[index], layui.echartsTheme);
          echnormcol[index].setOption(normcol[index]);
          window.onresize = echnormcol[index].resize;
      };
      if(!elemNormcol[0]) return;
      renderNormcol(0);
      //堆积柱状图
      var echheapcol = [], heapcol = [
          {
              tooltip : {
                  trigger: 'axis',
                  axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                      type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                  }
              },
              legend: {
                  data:['直接访问','邮件营销','联盟广告','视频广告','搜索引擎','百度','谷歌','必应','其他']
              },
              calculable : true,
              xAxis : [
                  {
                      type : 'category',
                      data : ['周一','周二','周三','周四','周五','周六','周日']
                  }
              ],
              yAxis : [
                  {
                      type : 'value'
                  }
              ],
              series : [
                  {
                      name:'直接访问',
                      type:'bar',
                      data:[320, 332, 301, 334, 390, 330, 320]
                  },
                  {
                      name:'邮件营销',
                      type:'bar',
                      stack: '广告',
                      data:[120, 132, 101, 134, 90, 230, 210]
                  },
                  {
                      name:'联盟广告',
                      type:'bar',
                      stack: '广告',
                      data:[220, 182, 191, 234, 290, 330, 310]
                  },
                  {
                      name:'视频广告',
                      type:'bar',
                      stack: '广告',
                      data:[150, 232, 201, 154, 190, 330, 410]
                  },
                  {
                      name:'搜索引擎',
                      type:'bar',
                      data:[862, 1018, 964, 1026, 1679, 1600, 1570],
                      markLine : {
                          itemStyle:{
                              normal:{
                                  lineStyle:{
                                      type: 'dashed'
                                  }
                              }
                          },
                          data : [
                              [{type : 'min'}, {type : 'max'}]
                          ]
                      }
                  },
                  {
                      name:'百度',
                      type:'bar',
                      barWidth : 5,
                      stack: '搜索引擎',
                      data:[620, 732, 701, 734, 1090, 1130, 1120]
                  },
                  {
                      name:'谷歌',
                      type:'bar',
                      stack: '搜索引擎',
                      data:[120, 132, 101, 134, 290, 230, 220]
                  },
                  {
                      name:'必应',
                      type:'bar',
                      stack: '搜索引擎',
                      data:[60, 72, 71, 74, 190, 130, 110]
                  },
                  {
                      name:'其他',
                      type:'bar',
                      stack: '搜索引擎',
                      data:[62, 82, 91, 84, 109, 110, 120]
                  }
              ]
          }
      ]
          ,elemHeapcol = $('#LAY-index-heapcol').children('div')
          ,renderHeapcol = function(index){
          echheapcol[index] = echarts.init(elemHeapcol[index], layui.echartsTheme);
          echheapcol[index].setOption(heapcol[index]);
          window.onresize = echheapcol[index].resize;
      };
      if(!elemHeapcol[0]) return;
      renderHeapcol(0);

  });
  exports('console', {})
});