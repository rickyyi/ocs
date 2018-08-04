<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>号码标签管理</title>
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
<!-- 时间组件 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/css/bootstrap-datepicker3.css">
<!-- 时间组件 -->
<link href="${pageContext.request.contextPath }/css/jquery-ui.min.css"
	rel="stylesheet" />
<link
	href="${pageContext.request.contextPath }/css/jquery-ui-timepicker-addon.min.css"
	rel="stylesheet" />
</head>
<body class="easyui-layout" style="visibility: hidden;">
	<div region="center" border="false">
		<!-- datagrid数据表格 -->
		<table id="grid"></table>
	</div>

	<!-- 查询 -->
	<div class="easyui-window" title="查询号码组窗口" id="searchWindow"
		collapsible="false" shadow="false" minimizable="false" maximizable="false"
		style="top: 100px; left: 200px; width: 500px;">
		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="searchform">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>呼叫时间</td>
						<td>
							<input type="text" style="background-color: #fff;" name="startImportTime" id="importTime_start"	class="easyui-validatebox" /> 
							<input type="text" style="background-color: #fff;" name="endImportTime"  id="importTime_end" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<td>通话时长</td>
						<td>
							<!-- <input type="text" style="background-color: #fff;" name="startUpdateTime" id="updateTime_start" class="easyui-validatebox"/>
							<input type="text" style="background-color: #fff;" name="startUpdateTime" id="updateTime_end" class="easyui-validatebox"/> -->
							<input type="text" class="easyui-validatebox" /> <input
							type="text" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<td>号码标签</td>
						<td><input type="text" name="callLabel"
							class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>号码</td>
						<td><input type="text" name="call" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>任务状态</td>
						<td><select style="width: 174px;" class="easyui-combobox">
								<option selected>不限</option>
						</select></td>
					</tr>
					<tr>
						<td>呼叫状态</td>
						<td><select style="width: 174px;" class="easyui-combobox">
								<option selected>不限</option>
						</select></td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn" href="javascript:;"
							class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 新增或修改 -->
	<div class="easyui-window" title="操作窗口" id="addOrUpdate"
		collapsible="false" resizable="false" shadow="false" minimizable="false" closed="true" maximizable="false"
		style="top: 100px; left: 200px; width: 700px;">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="numberGroupForm" action="${pageContext.request.contextPath }/numberGroup_savenNumberGroup" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="4">号码标签</td>
					</tr>
					<tr>
						<td>标签名称</td>
						<td><input type="text" name="callJobName" class="easyui-validatebox" required="true"/></td>
						<td>标签说明</td>
						<td><input type="text" name="callBusiName" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>命中关键字 </td>
						<td><input type="text" name="callIdGroup" class="easyui-validatebox" required="true"/></td>
						<td>命中购买信息关键字 </td>
						<td>
							<input type="text" style="background-color: #fff;" name="plannedStartTime"  id="plannedStartTime" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<td colspan="4" style="text-align: right;">
							<!-- 提交呼叫任务管理表单 -->
							<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<!-- 引入ocupload一键上传组件 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/ocupload/jquery.ocupload-1.1.2.js"></script>
<!-- 时间组件 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/bootstrap-datepicker.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-ui.min.js"></script>
<script
	src="${pageContext.request.contextPath }/js/jquery-ui-timepicker-addon.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-ui-timepicker-zh-CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/my-js/date.js"></script>
<script type="text/javascript">
	$(function() {
		// 搜索
		function doSet() {
			$.messager.alert("系统提示","功能尚未完善","info");
		}
		//新增外呼任务单击事件
		function doAdd(){
			$("#addOrUpdate").window("open");
		}
		//工具栏
		var toolbar = [ {
			id : 'button-add',
			text : '新增号码标签',
			iconCls : 'icon-add',
			handler : doAdd
		},{
			id:'button-set',
			text:'设置购买信号关键字',
			iconCls : 'icon-add',
			handler : doSet
		}];

		// 定义列
		var columns = [ [ 
		{
			field : 'numberLabelId',
			title : '号码标签ID',
			width : 120,
			align : 'center'
		}, {
			field : 'labelName',
			title : '标签名称',
			width : 120,
			align : 'center'
		}, {
			field : 'labelExplain',
			title : '标签说明',
			width : 120,
			align : 'center'
		}, {
			field : 'keyword',
			title : '命中关键字',
			width : 120,
			align : 'center'
		}, {
			field : 'purchaseSignalKeyword',
			title : '命中购买信号关键字',
			width : 120,
			align : 'center'
		}, {
			field : 'createTime',
			title : '生成时间',
			width : 120,
			align : 'center'
		}, {
			field : 'operation',
			title : '操作',
			width : 120,
			align : 'center',
			formatter:function(value, rowData, rowIndex){
				return '<a id="edit" style="text-decoration: none;" onclick="doUpdate()" href="#">修改</a>&nbsp;&nbsp;<a id="del" style="text-decoration: none;" onclick="doDel('+rowData.numberLabelId+')" href="#">删除</a>';
			}
		} ] ];
		
		function doDblClickRow(rowIndex, rowData) {
			$("#subareaForm").form("load", rowData);
			$('#addNumberGroupWindow').window("open");
		};

		//作用：能将表单序列化为json对象
		$.fn.serializeJson = function() {
			var serializeObj = {};
			var array = this.serializeArray();
			var str = this.serialize();
			$(array).each(
					function() {
						if (serializeObj[this.name]) {
							if ($.isArray(serializeObj[this.name])) {
								serializeObj[this.name].push(this.value);
							} else {
								serializeObj[this.name] = [
										serializeObj[this.name], this.value ];
							}
						} else {
							serializeObj[this.name] = this.value;
						}
					});
			return serializeObj;
		};
		
		$('.datepicker').datepicker();
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility : "visible"
		});

		// 标准数据表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : true,
			rownumbers : true,
			striped : true,
			pageList : [ 10, 50, 100 ],
			pagination : true,
			toolbar : toolbar,
			// 请求服务器，加载数据，显示在datagrid中
			url : "${pageContext.request.contextPath }/json/numbertag.json",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});

		// 查询分区
		$('#searchWindow').window({
			title : '查询分区',
			width : 400,
			modal : true,
			shadow : true,
			closed : true,
			height : 400,
			resizable : false
		});

		// 分页    条件查询
		$("#btn").click(function() {
			// 将表单数据转换成json对象
			var paramJson = $("#searchform").serializeJson();
			// 将json对象保存到datagroide中
			//当load方法被调用时，自动发起新的的请求并将参数带到服务端    当使用load方法时，这些条件将会被保存到datagrid中
			$("#grid").datagrid("load", paramJson);//当点击查询时，会重新加载datagrid
			// 请求结束后，关闭窗口
			$('#searchWindow').window("close");
		});
		
		//新增或修改保存单击事件
		$("#save").click(function(){
			$("#addOrUpdate").window("close");
		});
	});
	//删除外呼任务单击事件
	function doDel(ID){
		$.messager.confirm("系统提示","确定要删除吗？",function(del){
			if(del){
				$.messager.alert("系统提示","功能尚未完善","info");
			}
		});
	}
	//修改外呼任务单击事件
	function doUpdate(){
		$("#addOrUpdate").window("open");
	}
</script>
</html>