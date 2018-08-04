<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/default.css">	
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<!-- 引入 ECharts 文件 -->
<script src="${pageContext.request.contextPath }/js/echarts.js"></script>
<title>平台页面</title>
</head>
<body>
	 <!-- 平台状态 -->
	<br>
    <span style="font-size: 20px;">呼叫通道状态：</span>
    <span style="font-size: 20px;color: green">正常</span>
    
    <br>
    <br>
    <!-- 平台CPU使用率 -->
    <div id="cpu" style="width: 600px;height:400px;"></div>
    
    <br>
    <br>
    <!-- 平台内存使用率 -->
    <div id="memory" style="width: 600px;height:400px;"></div>
</body>
 <script type="text/javascript">
        // 1. 平台CPU使用率
        var myChart1 = echarts.init(document.getElementById('cpu'));
        
        $.get('${pageContext.request.contextPath }/json/data.json', function (data) {
            myChart1.setOption({
            	title: {
                    text: '平台CPU使用率'
                },
                legend: {
                    data:['数量']
                },
            	tooltip : {//提示框 
                    trigger: 'axis'
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataZoom : {show: true},
                        dataView : {show: true},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
                /* dataZoom : {
                     show : true,
                     realtime : true,
                     y: 36,
                     height: 20,
                     start : 40,
                     end : 60
                }, */
                xAxis : [
                    {
                        type : 'category',
                        data : function (){
                            var list = [];
                            var n = 0;
                            while (n++ < 150) {
                                list.push(n);
                            }
                            return list;
                        }()
                    }
                ],
                yAxis : [
                     {
                        type : 'value'
                     }
                ],
                series : [
                    {
                        name:'dz',
                        type:'line',
                        data:function (){
                            var list = [];
                            for (var i = 1; i <= 150; i++) {
                                list.push(Math.round(Math.random()* 30));
                            }
                            return list;
                        }()
                    }
                ],
                calculable:true
            });
        }, "json");
        
        
        // 2. 平台内存使用率
        var myChart2 = echarts.init(document.getElementById('memory'));
        
        $.get('${pageContext.request.contextPath }/json/data.json', function (data) {
            myChart2.setOption({
            	title: {
                    text: '平台内存使用率'
                },
            	tooltip : {
                    trigger: 'axis'
                },
                toolbox: {
                    show : true,
                    feature : {
                        mark : {show: true},
                        dataZoom : {show: true},
                        dataView : {show: true},
                        magicType : {show: true, type: ['line', 'bar']},
                        restore : {show: true},
                        saveAsImage : {show: true}
                    }
                },
               /*  dataZoom : {
                    show : true,
                    realtime : true,
                    y: 36,
                    height: 20,
                    start : 40,
                    end : 60
                }, */
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : function (){
                            var list = [];
                            var n = 0;
                            while (n++ < 150) {
                                list.push(n);
                            }
                            return list;
                        }()
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : [
                    {
                        name:'dz',
                        type:'line',
                        data:function (){
                            var list = [];
                            for (var i = 1; i <= 150; i++) {
                                list.push(Math.round(Math.random()* 30));
                            }
                            return list;
                        }()
                    }
                ],
                calculable:false
            });
        }, "json");

        // 使用刚指定的配置项和数据显示图表。
        //myChart1.setOption(option);
    </script>
</html>