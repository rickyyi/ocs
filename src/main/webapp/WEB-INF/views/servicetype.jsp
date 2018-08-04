<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>业务类型管理页面</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>

<!-- 选择框插件样式 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/hdw.css"/>

<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/default.css">	

<!-- 时间组件样式 -->
<link href="${pageContext.request.contextPath }/css/jquery-ui.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath }/css/jquery-ui-timepicker-addon.min.css" rel="stylesheet" />

<!-- 时间组件样式 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/bootstrap-datepicker3.css">
	
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/outOfBounds.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<!-- 时间组件 -->	
<script type="text/javascript" src="${pageContext.request.contextPath }/js/bootstrap-datepicker.js"></script>

<!-- 时间组件 -->
<script src="${pageContext.request.contextPath }/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-ui-timepicker-addon.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-ui-timepicker-zh-CN.js"></script>
	
<!-- 选择框插件 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/hdw.js"></script>

<!-- js代码 -->
<script type="text/javascript"> 
    // 添加
	function doAdd(){
		$('#addServiceTypeWindow').window("open");
	}
	
	// 修改
	function doEdit(){
		var rows = $("#grid").datagrid("getSelections");
		if (rows.length != 1) {
			// 提示错误
			$.messager.alert("警告","对不起,您只能要选中一行","warning"); 
			// 结束
			return;
		}
		// 赋值
		$("#updateServiceTypeForm input[name=id]").val(rows[0].id);
		$("#updateServiceTypeForm input[name=name]").val(rows[0].name);
		$("#updateServiceTypeForm input[name=number]").val(rows[0].number);
		$("#updateServiceTypeForm input[name=welcome]").val(rows[0].welcome);
		$("#updateServiceTypeForm input[name=finish]").val(rows[0].finish);
		$("#updateServiceTypeForm input[name=importtime]").val(rows[0].importtime);
		// 打开窗口
		$('#updateServiceTypeWindow').window("open");
	}
	
	// 条件查询
	function doSearch(){
		$('#searchWindow').window("open");
	}
	
	// 删除
	function doDelete(){
		// 获取所有被选中的行的数组
		var rows = $("#grid").datagrid("getSelections");
		// 对选中的行进行判断是否为空
		if (rows.length == 0) {
			// 提示错误
			$.messager.alert("警告","对不起,您至少要选中一行","warning");
			// 结束
			return;
		}
		// 如果不为空，就遍历这个行数组，得到每一行，然后获取每一行中的id，根据id进行删除
		// 创建一个数组
		var idArr = new Array();
		// 遍历
		$(rows).each(function(){
			idArr.push(this.id);
		});
		// 将数组转换成字符串
		var ids = idArr.join(",");
		
		//然后进行ajax请求  将数据传给后台
		$.ajax({
			type:"post",
			url:"${pageContext.request.contextPath }/serviceType_deleteServiceType",
			data:{"ids":ids},
			success:function(msg){
				if (msg.result) {
					$.messager.alert("恭喜","删除成功","info");
				} else {
					$.messager.alert("警告","删除失败","warning");
				}
				// 重新加载数据，
				$("#grid").datagrid("reload");
				// 清空页面所有选项
				$("#grid").datagrid("clearSelections");
			}
		});
	}
	
	//工具栏
	var toolbar = [ {
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-add',
		text : '增加业务类型',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-edit',	
		text : '修改',
		iconCls : 'icon-edit',
		handler : doEdit
	},{
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}];
	
	// 定义列
	var columns = [ [ {
		field : 'bb',
		checkbox : true,
	}, {
		field : 'id',
		title : '业务类型ID',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.id;
		}
	},{
		field : 'name',
		title : '业务名称',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.name;
		}
	}, {
		field : 'number',
		title : '关键字数量',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.number;
		}
	}, {
		field : 'welcome',
		title : '欢迎语音',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.welcome;
		}
	}, {
		field : 'finish',
		title : '结束语音',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.finish;
		}
	}, {
		field : 'importtime',
		title : '导入时间',
		width : 140,
		align : 'center',
		formatter : function(data, row ,index){
			return row.importtime;
		}
	}] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 标准数据表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList: [10,50,100],
			pagination : true,
			toolbar : toolbar,
			// 请求服务器，加载数据，显示在datagrid中   分页条件查询 
			//url : "${pageContext.request.contextPath }/numbergroup_listPage",
			url : "${pageContext.request.contextPath }/json/servicetype.json",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加业务类型窗口
		$('#addServiceTypeWindow').window({
	        title: '添加业务类型',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 修改业务类型窗口
		$('#updateServiceTypeWindow').window({
	        title: '修改业务类型',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 查询业务类型窗口
		$('#searchWindow').window({
	        title: '查询业务类型',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		//作用：能将表单序列化为json对象
		$.fn.serializeJson=function(){  
	        var serializeObj={};  
	        var array=this.serializeArray();  
	        var str=this.serialize();  
	        $(array).each(function(){  
	            if(serializeObj[this.name]){  
	                if($.isArray(serializeObj[this.name])){  
	                    serializeObj[this.name].push(this.value);  
	                }else{  
	                    serializeObj[this.name]=[serializeObj[this.name],this.value];  
	                }  
	            }else{  
	                serializeObj[this.name]=this.value;   
	            }  
	        });  
	        return serializeObj;  
	    };
	    
		// 分页    条件查询
		$("#btn").click(function(){
			// 将表单数据转换成json对象
			var paramJson = $("#searchform").serializeJson();
			// 将json对象保存到datagroide中
			//当load方法被调用时，自动发起新的的请求并将参数带到服务端    当使用load方法时，这些条件将会被保存到datagrid中
			$("#grid").datagrid("load",paramJson);//当点击查询时，会重新加载datagrid
			// 请求结束后，关闭窗口
			$('#searchWindow').window("close");
		});
		
		// 增加业务类型
		$("#save").click(function(){
			if ($("#addServiceTypeForm").form("validate")) {
				$("#addServiceTypeForm").submit();
			} 
				$("#addServiceTypeForm").form("clear");
		});
	
		// 修改业务类型
		$("#update").click(function(){
			if ($("#updateServiceTypeForm").form("validate")) {
				$("#updateServiceTypeForm").submit();
			} 
				$("#updateServiceTypeForm").form("clear");
		});
		
		
	});

	// 双击修改业务类型
	function doDblClickRow(rowIndex, rowData){
		$("#updateServiceTypeForm").form("load",rowData);
		$('#updateServiceTypeWindow').window("open");
	};
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
		<!-- datagrid数据表格 -->
    	<table id="grid"></table>
	</div>
	
	<!-- 添加业务类型 -->
	<div class="easyui-window" title="添加业务类型" id="addServiceTypeWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<!-- 提交业务类型表单 -->
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="addServiceTypeForm" action="${pageContext.request.contextPath }/number_updateKeyWords" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr>
						<div class="content">
							<span style="font-size: 15px;color: red;">关键字列表</span>
				  			<select multiple="multiple" id="select1">
					    		<option value="1">你好</option>
					    		<option value="2">結束</option>
					    		<option value="3">多少钱</option>
					    		<option value="4">地址</option>
					    		<option value="5">什嘛时间</option>
					    		<option value="6">太贵了</option>
					    		<option value="7">没空</option>
					    		<option value="8">附近</option>
					  		</select>
					  		<span id="add">选中右移>></span> <span id="add_all">全部右移>></span>
						</div>
						
						
							<div class="content">
								<span style="font-size: 15px;color: red;">业务类型管理</span>
						  		<select name="keyname" multiple="multiple" id="select2"></select>
						  		<span id="remove">选中左移>></span><span id="remove_all">全部左移>></span>
							</div>
							
							
							<div class="content">
								<span style="font-size: 15px;color: red;">业务类型管理</span>
					  			<select name="voicename" multiple="multiple" id="select3"></select>
						  		<span id="add2">选中右移>></span> <span id="add_all2">全部右移>></span>
							</div>
						
						
						<div class="content">
							<span style="font-size: 15px;color: red;">语音文件列表</span>
					  		<select multiple="multiple" id="select4">
					  			<option value="1">你好</option>
					    		<option value="2">结束</option>
					    		<option value="3">价格</option>
					    		<option value="4">位置</option>
					    		<option value="5">二次推荐</option>
					    		<option value="6">安抚</option>
					  		</select>
					  		<span id="remove2">选中左移>></span><span id="remove_all2">全部左移>></span>
						</div>
					</tr>
				</table>
			</form>
		</div>
		
	</div>
	
	
	<!-- 修改业务类型 -->
	<div class="easyui-window" title="修改业务类型" id="updateServiceTypeWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<!-- 提交业务类型表单 -->
				<a id="update" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="updateServiceTypeForm" action="${pageContext.request.contextPath }/number_updateKeyWords" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">业务类型信息</td>
					</tr>
					<tr>
						<td>业务类型ID</td>
						<td>
							<input type="text" name="id" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
					<tr>
						<td>业务名称</td>
						<td>
							<input type="text" name="name" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
					<tr>
						<td>关键字数量</td>
						<td><input type="text" name="number" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>欢迎语音</td>
						<td><input type="text" name="welcome" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>结束语音</td>
						<td><input type="text" name="finish" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>导入时间</td>
						<td><input type="text" name="importtime" class="easyui-validatebox" required="true"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 查询 -->
	<div class="easyui-window" title="查询业务类型窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchform" >
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>导入时间</td>
						<td>
							<input type="text" name="startImportTime" id="importTime_start" class="easyui-validatebox"/>
							<input type="text" name="endImportTime" id="importTime_end" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>业务名称</td>
						<td><input type="text" name="name" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>关键字数量</td>
						<td>
							<input type="text" name="startnumber" class="easyui-validatebox"/>
							<br>
							至
							<br>
							<input type="text" name="endnumber" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>关键字</td>
						<td><input type="text" name="keyname" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>语音名称</td>
						<td><input type="text" name="voicename" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
<!-- 时间的js代码 -->
<script type="text/javascript">
	$("#importTime_start").prop("readonly", true).datetimepicker({
		timeText: '时间',
	    hourText: '小时',
	    minuteText: '分钟',
	    secondText: '秒',
	    currentText: '现在',
	    closeText: '完成',
	    showSecond: true, //显示秒  
	    timeFormat: 'HH:mm:ss', //格式化时间 
	    changeMonth: true,
	    dateFormat: "yy-mm-dd",
	    onClose: function(selectedDate) {
	        $("#importTime_end").datepicker("option", "minDate", selectedDate);
	    }
	});
	
	$("#importTime_end").prop("readonly", true).datetimepicker({
		timeText: '时间',
	    hourText: '小时',
	    minuteText: '分钟',
	    secondText: '秒',
	    currentText: '现在',
	    closeText: '完成',
	    showSecond: true, //显示秒  
	    timeFormat: 'HH:mm:ss', //格式化时间 
	    changeMonth: true,
	    dateFormat: "yy-mm-dd",
	    onClose: function(selectedDate) {
	        $("#importTime_start").datepicker("option", "maxDate", selectedDate);
	        $("#importTime_end").val($(this).val());
	    }
	});
	
	
	$("#updateTime_start").prop("readonly", true).datetimepicker({
		timeText: '时间',
	    hourText: '小时',
	    minuteText: '分钟',
	    secondText: '秒',
	    currentText: '现在',
	    closeText: '完成',
	    showSecond: true, //显示秒  
	    timeFormat: 'HH:mm:ss', //格式化时间 
	    changeMonth: true,
	    dateFormat: "yy-mm-dd",
	    onClose: function(selectedDate) {
	        $("#updateTime_end").datepicker("option", "minDate", selectedDate);
	    }
	});
	
	$("#updateTime_end").prop("readonly", true).datetimepicker({
		timeText: '时间',
	    hourText: '小时',
	    minuteText: '分钟',
	    secondText: '秒',
	    currentText: '现在',
	    closeText: '完成',
	    showSecond: true, //显示秒  
	    timeFormat: 'HH:mm:ss', //格式化时间 
	    changeMonth: true,
	    dateFormat: "yy-mm-dd",
	    onClose: function(selectedDate) {
	        $("#updateTime_start").datepicker("option", "maxDate", selectedDate);
	        $("#updateTime_end").val($(this).val());
	    }
	});
</script>
</html>