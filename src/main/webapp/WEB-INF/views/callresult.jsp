<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>呼叫结果管理</title>
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
		collapsible="false" shadow="true" minimizable="false" maximizable="false"
		style="top: 100px; left: 200px; width: 500px;">
		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="searchform">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>呼叫时间</td>
						<td><input type="text" style="background-color: #fff;"
							name="time_1" id="importTime_start"
							class="easyui-validatebox" /> - <input type="text"
							style="background-color: #fff;" name="time_2"
							id="importTime_end" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>通话时长</td>
						<td>
							<!-- <input type="text" style="background-color: #fff;" name="startUpdateTime" id="updateTime_start" class="easyui-validatebox"/>
							<input type="text" style="background-color: #fff;" name="startUpdateTime" id="updateTime_end" class="easyui-validatebox"/> -->
							<input type="text" name="duration_1" class="easyui-validatebox" /> - <input
							type="text" name="duration_2" class="easyui-validatebox" />
						</td>
					</tr>
					<tr>
						<td>意向标签</td>
						<td><input type="text" name="callLabel"
							class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>号码</td>
						<td><input type="text" name="callId" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>任务状态</td>
						<td><select style="width: 174px;" editable="false" name="taskStatus" class="easyui-combobox">
								<option selected value="">不限</option>
								<option>未计划</option>
								<option>计划中</option>
								<option>计划完成</option>
						</select></td>
					</tr>
					<tr>
						<td>呼叫状态</td>
						<td><select style="width: 174px;" editable="false" name="callResult" class="easyui-combobox">
								<option selected value="">不限</option>
								<option>接通</option>
								<option>未接通</option>
								<option>无状态</option>
								<option>占线</option>
						</select></td>
					</tr>
					<tr>
						<td colspan="2">
							<a id="searchBtn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
							<a id="clearBtn" href="javascript:;"  style="right: 40px;" class="easyui-linkbutton" data-options="iconCls:'icon-search'">清空</a>
							<input name="t" value="t" type="hidden">
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 查询 -->
	<div class="easyui-window" id="infoWindow"
		collapsible="false" shadow="true" minimizable="false" maximizable="false"
		style="top: 100px; left: 200px; width: 500px;">
		<div style="overflow: auto; padding: 5px;" border="false">
			<jsp:include page="callResultInfo.jsp"></jsp:include>
		</div>
	</div>
</body>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/my-js/callresult.js"></script>
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
</html>