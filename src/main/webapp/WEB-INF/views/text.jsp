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
<script
	src="${pageContext.request.contextPath }/js/audiojs/audio.min.js"></script>
<script src="${pageContext.request.contextPath }/js/my-js/voice.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/includes/index.css"
	media="screen">
</head>
<body class="easyui-layout" style="visibility: hidden;">
	<div region="center" border="false">
		<!-- datagrid数据表格 -->
		<table id="grid" ></table>
	</div>

	<!-- 通话详情 -->
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
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/my-js/text.js"></script>
</html>