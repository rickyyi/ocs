<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>audio.js</title>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script
	src="${pageContext.request.contextPath }/js/audiojs/audio.min.js"></script>
<script src="${pageContext.request.contextPath }/js/my-js/voice.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/includes/index.css"
	media="screen">
</head>
<body>
	<div
		style=" min-width: 900px;text-align: center;">
		<!-- 消息显示 -->
		<div
			style=" min-height: 465px; overflow: auto;">
			<div style="overflow: auto; max-height:430px; margin-top: 5px;">
				<ul></ul>
			</div>
		</div>
		<div>
			<audio id="media" controls="controls" style="width: 700px;"></audio>
		</div>
	</div>
</html>