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
<title>呼叫页面</title>
</head>
<body>
	 <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
	 <!-- 1. 外呼任务执行状态 -->
    <div id="execute" style="width: 600px;height:400px;"></div>
    <div>
        <span class="text-primary">切换主题</span>
        <select id="theme-select">
        	<option>全部</option>
        	<option>地产</option>
        	<option>金融</option>
        	<option>招聘</option>
        </select>
    </div>
    
    <br>
    <br>
    <br>
    <br>
    
     <!-- 2. 外呼呼叫状态 -->
    <div id="call" style="width: 600px;height:400px;"></div>
    <div>
        <span class="text-primary">切换主题</span>
        <select id="theme-select">
        	<option>全部</option>
        	<option>地产</option>
        	<option>金融</option>
        	<option>招聘</option>
        </select>
    </div>
</body>
 <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例 
        // 1. 外呼任务执行状态
        var myChart1 = echarts.init(document.getElementById('execute'));
        
        $.get('${pageContext.request.contextPath }/json/data.json', function (data) {
            myChart1.setOption({
            	title: {
                    text: '外呼任务执行状态'
                },
                tooltip: {trigger: 'axis', formatter: "Temperature : <br/>{b} : {c}"},
                toolbox: {
        	        show : true,
        	        feature : {
        	            mark : {show: true},
        	            dataView : {show: true, readOnly: false},
        	            magicType : {show: true, type: ['line', 'bar']},
        	            restore : {show: true},
        	            saveAsImage : {show: true}
        	        }
        	    },
        	    legend: {
                    data:['数量']
                },
                xAxis: {
                	data: data[0].xdata
                },
                yAxis: {},
                series: [{
                    name: '数量',
                    type: 'line',
                    smooth:true,
                    itemStyle: {
    	                normal: {
    	                    lineStyle: {
    	                        shadowColor : 'rgba(0,0,0,0.4)'
    	                    }
    	                }
    	            },
                    data: data[1].ydata
                }]
            });
        }, "json");

        // 使用刚指定的配置项和数据显示图表。
        //myChart1.setOption(option);
        
        
        // 2. 外呼呼叫状态
        var myChart2 = echarts.init(document.getElementById('call'));
        
        $.get('${pageContext.request.contextPath }/json/data2.json', function (data) {
            myChart2.setOption({
            	title: {
                    text: '外呼呼叫状态'
                },
                tooltip: {trigger: 'axis', formatter: "Temperature : <br/>{b} : {c}"},
                toolbox: {
        	        show : true,
        	        feature : {
        	            mark : {show: true},
        	            dataView : {show: true, readOnly: false},
        	            magicType : {show: true, type: ['line', 'bar']},
        	            restore : {show: true},
        	            saveAsImage : {show: true}
        	        }
        	    },
        	    legend: {
                    data:['数量']
                },
                xAxis: {
                	data: data[0].xdata
                },
                yAxis: {},
                series: [{
                    name: '数量',
                    type: 'line',
                    smooth:true,
                    itemStyle: {
    	                normal: {
    	                    lineStyle: {
    	                        shadowColor : 'rgba(0,0,0,0.4)'
    	                    }
    	                }
    	            },
                    data: data[1].ydata
                }]
            });
        }, "json");

        // 使用刚指定的配置项和数据显示图表。
       // myChart2.setOption(option);
    </script>
</html>