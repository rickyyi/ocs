<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>号码组管理页面</title>
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


<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/easyui/outOfBounds.js"></script>
<!-- 引入ocupload一键上传组件 -->	
<script type="text/javascript" 
	src="${pageContext.request.contextPath }/js/ocupload/jquery.ocupload-1.1.2.js"></script>
<!-- 时间组件 -->	
<script type="text/javascript" 
	src="${pageContext.request.contextPath }/js/bootstrap-datepicker.js"></script>	

<!-- 时间组件 -->
<link href="${pageContext.request.contextPath }/css/jquery-ui.min.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath }/css/jquery-ui-timepicker-addon.min.css" rel="stylesheet" />
<script src="${pageContext.request.contextPath }/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath }/js/jquery-ui-timepicker-addon.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-ui-timepicker-zh-CN.js"></script>
	
<script type="text/javascript"> 
   
	function doAdd(){
		$('#addNumberGroupWindow').window("open");
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
		$("#updateNumberGroupForm input[name=groupId]").val(rows[0].groupId);
		$("#updateNumberGroupForm input[name=groupName]").val(rows[0].groupName);
		$("#updateNumberGroupForm input[name=number]").val(rows[0].number);
		$("#updateNumberGroupForm input[name=importtime]").val(rows[0].importtime);
		$("#updateNumberGroupForm input[name=updatetime]").val(rows[0].updatetime);
		
		
		
		// 加载数据 
		/* var datajson = {
				"groupId": rows[0].groupId,
				"groupName": rows[0].groupName,
				"number": rows[0].number,
				"importtime": rows[0].importtime,
				"updatetime": rows[0].updatetime
		}; */
		
	// $("#updateNumberGroupForm").form("load",datajson);
		
		$('#updateNumberGroupWindow').window("open");
	}
	
	
	// 删除号码组 
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
			url:"${pageContext.request.contextPath }/numbergroup_deleteNumberGroup",
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
	
	
	// 搜索
	function doSearch(){
		$('#searchWindow').window("open");
	}
	
	//工具栏
	var toolbar = [ {
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-add',
		text : '增加',
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
	},{
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo'
	}];
	// 定义列
	var columns = [ [ {
		field : 'hh',
		checkbox : true,
	}, {
		field : 'groupId',
		title : '号码组ID',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.groupId;
		}
	},{
		field : 'groupName',
		title : '号码组名称',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.groupName;
		}
	}, {
		field : 'number',
		title : '组内号码数',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.number;
		}
	}, {
		field : 'importtime',
		title : '导入时间',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.importtime;
		}
	}, {
		field : 'updatetime',
		title : '修改时间',
		width : 120,
		align : 'center'
	} ] ];
	
	$(function(){
		 $('.datepicker').datepicker(); 
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
			// 请求服务器，加载数据，显示在datagrid中
			//url : "${pageContext.request.contextPath }/numbergroup_listPage",
			url : "${pageContext.request.contextPath }/json/numbergroup.json",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加号码组
		$('#addNumberGroupWindow').window({
	        title: '添加号码组',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		// 修改号码组
		$('#updateNumberGroupWindow').window({
	        title: '修改号码组',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 查询号码组
		$('#searchWindow').window({
	        title: '查询号码组',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
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
		
		// 一键上传   先导包   给这个导入按钮添加一个一键上传事件
		var myUpload = $("#button-import").upload({
	        name: 'upload',
	        action: '${pageContext.request.contextPath}/numberGroup_importData',//提交excel表格到数据库
	        enctype: 'multipart/form-data',
	        params: {},
	        autoSubmit: true,
	        onSubmit: function() {},
	        // 文件上传后执行的函数
	        onComplete: function(response) {
	        	//将json字符串转换成json对象
	        	var data = $.parseJSON(response);
	        	if (data.result) {
	        		$.messager.alert("恭喜", "文件上传成功", "info");
	        	} else {
	        		$.messager.alert("警告", "文件上传失败", "warning");
	        	}
	        },
	        // 选择文件后执行的函数
	        onSelect: function() {
	        	// 关闭自动提交功能
	        	this.autoSubmit = false;
	        	// 获取文件名，对文件名进行后缀名的判断，是否是.xls或者.xlsx结尾
	        	var filename = this.filename();
	        	// 创建一个正则规则
	        	var reg = /^.*\.xlsx?$/i;
	        	if (reg.test(filename)) {
	        		// 如果是就自动提交
	        		this.autoSubmit = true;
	        	} else {
	        		// 如果不是，就不提交，并且提示错误
	        		$.messager.alert("警告", "你上传的文件格式不正确，必须是xls或者xlsx为后缀", "warning");
	        	}
	        }
		});
		
		// 增加号码组
		$("#save").click(function(){
			if ($("#numberGroupForm").form("validate")) {
				$("#numberGroupForm").submit();
			} 
				$("#numberGroupForm").form("clear");
		});
		// 修改号码组
		$("#update").click(function(){
			if ($("#updateNumberGroupForm").form("validate")) {
				$("#updateNumberGroupForm").submit();
			} 
				$("#updateNumberGroupForm").form("clear");
		});
		
		
	});

	// 双击修改
	function doDblClickRow(rowIndex, rowData){
		$("#updateNumberGroupForm").form("load",rowData);
		$('#updateNumberGroupWindow').window("open");
	};
	
	
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
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
		<!-- datagrid数据表格 -->
    	<table id="grid"></table>
	</div>
	
	<!-- 添加号码组 -->
	<div class="easyui-window" title="添加号码组" id="addNumberGroupWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<!-- 提交号码组表单 -->
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="numberGroupForm" action="${pageContext.request.contextPath }/numberGroup_savenNumberGroup" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">号码组信息</td>
					</tr>
					<tr>
						<td>号码组名称</td>
						<td><input type="text" name="groupName" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>组内号码数</td>
						<td><input type="text" name="number" class="easyui-validatebox" required="true"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 修改 号码组 -->
	<div class="easyui-window" title="修改号码组" id="updateNumberGroupWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<!-- 提交号码组表单 -->
				<a id="update" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="updateNumberGroupForm" action="${pageContext.request.contextPath }/numberGroup_updateNumberGroup" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">号码组信息</td>
					</tr>
					<tr>
						<td>号码组ID</td>
						<td>
							<input type="text" name="groupId" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
					<tr>
						<td>号码组名称</td>
						<td>
							<input type="text" name="groupName" class="easyui-validatebox" required="true"/>
						</td>
					</tr>
					<tr>
						<td>组内号码数</td>
						<td><input type="text" name="number" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>导入时间</td>
						<td><input type="text" name="importtime" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>修改时间</td>
						<td><input type="text" name="updatetime" class="easyui-validatebox" required="true"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 查询 -->
	<div class="easyui-window" title="查询号码组窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
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
						<td>修改时间</td>
						<td>
							<input type="text" name="startUpdateTime" id="updateTime_start" class="easyui-validatebox"/>
							<input type="text" name="startUpdateTime" id="updateTime_end" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>号码组名称</td>
						<td><input type="text" name="name" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>组内号码数量</td>
						<td><input type="text" name="number" class="easyui-validatebox"/></td>
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