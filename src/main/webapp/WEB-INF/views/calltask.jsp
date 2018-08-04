<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>外呼任务管理页面</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>

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
	
<!-- 引入ocupload一键上传组件 -->	
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ocupload/jquery.ocupload-1.1.2.js"></script>	

<!-- js代码 -->
<script type="text/javascript"> 
    // 添加
	function doAdd(){
		$('#addCallTaskWindow').window("open");
	}
	
	// 修改前数据回显
	function updateCallJob(id){
		// 请求后台  根据id 来查询对应的呼叫任务对象
		$.ajax({
			type:"GET",
			url:"${pageContext.request.contextPath }/rest/callTask?id="+id,
			success:function(data){
				// 赋值
				$("#updateCallTaskForm input[name=id]").val(data.id);
				$("#updateCallTaskForm input[name=callTaskName]").val(data.callTaskName);
				$('#aa').combobox('setValue', data.callServiceName);
				$("#updateCallTaskForm input[name=callNumManage]").val(data.callNumManage);
				$("#updateCallTaskForm input[name=planStartTime]").val(data.planStartTime);
				$('#cc').combobox('setValue', data.preState);
				$("#updateCallTaskForm input[name=createTime]").val(data.createTime);
				// 打开窗口
				$('#updateCallTaskWindow').window("open");
			}
		});
	}
	
	// 条件查询
	function doSearch(){
		$('#searchWindow').window("open");
	}
	
	// 单个删除
	function delCallJob(id){
		//然后进行ajax请求  将数据传给后台
		$.ajax({
			type:"POST",
			url:"${pageContext.request.contextPath }/rest/callTask/single",
			data:{"id":id,"_method":"DELETE"},
			statusCode:{
				200 : function(){
					$.messager.alert('提示','删除内容成功!');
				},
				400 : function(){
					$.messager.alert('提示','删除内容失败!');
				},
				500 : function(){
					$.messager.alert('提示','修改内容失败!');
				}
			}
		});
		// 重新加载数据，
		$("#grid").datagrid("reload");
		// 清空页面所有选项
		$("#grid").datagrid("clearSelections");
	}
	
	// 批量删除
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
			type:"POST",
			url:"${pageContext.request.contextPath }/rest/callTask/batch",
			data:{"ids":ids, "_method":"DELETE"},
			statusCode:{
				200 : function(){
					$.messager.alert('提示','删除内容成功!');
				},
				400 : function(){
					$.messager.alert('提示','删除内容失败!');
				},
				500 : function(){
					$.messager.alert('提示','修改内容失败!');
				}
			}
		});
		// 重新加载数据，
		$("#grid").datagrid("reload");
	}
	
	function doPause () {
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
			type:"POST",
			url:"${pageContext.request.contextPath }/rest/callTask/pause",
			data:{"ids":ids, "_method":"PUT"},
			statusCode:{
				200 : function(){
					$.messager.alert('提示','已暂停!');
				},
				400 : function(){
					$.messager.alert('提示','系统错误,请重试!');
				},
				500 : function(){
					$.messager.alert('提示','系统错误,请重试!');
				}
			} 
		});
		// 重新加载数据，
		$("#grid").datagrid("reload");
	}
	
	// 批量导入模板下载
	function batchTemplet (){
		var url="${pageContext.request.contextPath }/rest/callTask/templetDownload";
	    window.open(url);
	}
	
	// 批量导入
	function batchImportData(index, obj){
		var callTaskName = $("#grid").datagrid("getData").rows[index].callTaskName;
		var callServiceName = $("#grid").datagrid("getData").rows[index].callServiceName;
		var planStartTime = $("#grid").datagrid("getData").rows[index].planStartTime;
		var preState = $("#grid").datagrid("getData").rows[index].preState;
		
		$(obj).upload({
	        name: 'file',
	       	action: '${pageContext.request.contextPath}/rest/callTask/importData',
	        enctype: 'multipart/form-data',//协议
	        params: {"callTaskName":callTaskName, "callServiceName":callServiceName, "planStartTime":planStartTime, "preState":preState},//携带的参数
	        autoSubmit: true,//自动提交
	        // 提交之前执行的方法
	        onSubmit: function() {},
	      	// 当用户选择了一个文件后触发事件
	        onSelect: function() {
	        	//先关闭自动提交
	        	this.autoSubmit = false; 
	        	
	        	// 先获取文件类型
	        	var filename = this.filename();
	        	// 创建正则表达式  i表示对大小写不敏感
	        	var reg = /^.*\.xlsx?$/i;
	        	// 对用户选择的文件进行判断
	        	// 看是不是属于.xls或.xlsx这个结尾的文件
	        	if (reg.test(filename)) {
	        		// 如果是，就提交数据
	        		this.autoSubmit = true;
	        	} else {
	        		// 如果不是，就不提交，并且提示
	        		$.messager.alert("警告","文件格式不正确，必须以.xls或xlsx结尾","warning"); 
	        	}
	        },
	        // 提交表单完成后触发的事件
	        onComplete: function(response) {
	        	//将json字符串转换成json对象
	        	var data = $.parseJSON(response);
	        	// 对返回回来的数据记性判断，是否上传成功
	        	if (data.result) {
	        		$.messager.alert("恭喜","文件上传成功","info");
	        	} else {
	        		$.messager.alert("警告","文件上传失败","warning"); 
	        	}
	        }
		});
	}
	
	function batchImport(){
		$("#batchImportWindow").window('open');
	}
	
	//工具栏
	var toolbar = [ {
		id : 'button-search',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doSearch
	}, {
		id : 'button-add',
		text : '新增外呼任务',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '批量删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	},{
		id : 'button-pause',
		text : '批量暂停',
		iconCls : 'icon-cut',
		handler : doPause
	}, {
		id : 'button-import',
		text : '批量导入模板下载',
		iconCls : 'icon-undo',
		handler : batchTemplet
	}, {
		id : 'button-import',
		text : '批量导入',
		iconCls : 'icon-redo',
		handler : batchImport
	}];
	
	// 定义列
	var columns = [ [ {
		field : 'vv',
		checkbox : true,
	}, {
		field : 'id',
		title : '外呼任务ID',
		width : 120,
		align : 'center'
	}, {
		field : 'callTaskName',
		title : '外呼任务名称',
		width : 120,
		align : 'center'
	}, {
		field : 'callServiceName',
		title : '模板名称',
		width : 120,
		align : 'center'
	}, {
		field : 'callNumManage',
		title : '外呼号码',
		width : 120,
		align : 'center'
	}, {
		field : 'preState',
		title : '当前任务状态',
		width : 180,
		align : 'center'
	}, {
		field : 'createTime',
		title : '创建时间',
		width : 140,
		align : 'center'
	}, {
		field : 'operation',
		title : '操作',
		width : 160,
		align : 'center',
		formatter:function(value, rowData, rowIndex){    
			var btn = "";    
			var jsonStr = JSON.stringify(rowData);
			if (rowData.preState=="计划中") {   
				btn = '<a id="edit" style="text-decoration: none;" onclick="updateCallJob('+rowData.id+')" href="#">修改</a>&nbsp;&nbsp;<a id="del" style="text-decoration: none;" onclick="delCallJob('+rowData.id+')" href="#">删除</a>&nbsp;&nbsp;<a id="import" style="text-decoration: none;" onclick=batchImportData('+rowIndex+',this) href="#">批量导入</a>';
			}else
				btn = '<a id="del" style="text-decoration: none;" onclick="delCallJob('+rowData.id+')" href="#">删除</a>';
			return btn;
		}
	} ] ];
	
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
			pageList: [10,50,100,500,1000],
			pageSize: 100,
			pagination : true,
			toolbar : toolbar,
			// 请求服务器，加载数据，显示在datagrid中   分页条件查询 
			url : "${pageContext.request.contextPath }/rest/callTask/query",
			//url : "${pageContext.request.contextPath }/json/calltask.json",
			idField : 'id',
			columns : columns
		});
		setInterval(function(){
			$("#grid").datagrid("reload");
		}, 1000*10)
		// 添加外呼任务窗口
		$('#addCallTaskWindow').window({
	        title: '添加外呼任务',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 修改外呼任务窗口
		$('#updateCallTaskWindow').window({
	        title: '修改外呼任务',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 查询外呼任务窗口
		$('#searchWindow').window({
	        title: '查询外呼任务',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 批量导入任务窗口
		$('#batchImportWindow').window({
	        title: '批量导入任务',
	        width: 600,
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
			$('#bb').combobox('clear');  
			$("#searchform :input").val("");
			// 清空页面所有选项
			$("#grid").datagrid("clearSelections");
			// 请求结束后，关闭窗口
			$('#searchWindow').window("close");
		});
		
		// 批量导入  
		$("#importNum").click(function(){
			// 将form表单转换成接送对象
			var jsonObj = $("#batchImportFrom").serializeJson();
			var a1 = $("#batchImportFrom input[name='callTaskName']").val().trim()=='';
			var a2 = $("#batchImportFrom input[name='callServiceName']").val().trim()=='';
			var a3 = $("#batchImportFrom input[name='planStartTime']").val().trim()=='';
			var a4 = $("#batchImportFrom input[name='preState']").val().trim()=='';
			var a5 = $("#batchImportFrom input[name='createTime']").val().trim()=='';
			
			if (!(!a1 && !a2 && !a3 && !a4 && !a5)) {
				$.messager.alert('提示','表单还未填写完成!');
				return;
			}
			$("#importNum").upload({
		        name: 'file',
		       	action: '${pageContext.request.contextPath}/rest/callTask/importData',
		        enctype: 'multipart/form-data',//协议
		        params: jsonObj,//携带的参数
		        autoSubmit: true,//自动提交
		        // 提交之前执行的方法
		        onSubmit: function() {},
		      	// 当用户选择了一个文件后触发事件
		        onSelect: function() {
		        	//先关闭自动提交
		        	this.autoSubmit = false; 
		        	
		        	// 先获取文件类型
		        	var filename = this.filename();
		        	// 创建正则表达式  i表示对大小写不敏感
		        	var reg = /^.*\.xlsx?$/i;
		        	// 对用户选择的文件进行判断
		        	// 看是不是属于.xls或.xlsx这个结尾的文件
		        	if (reg.test(filename)) {
		        		// 如果是，就提交数据
		        		this.autoSubmit = true;
		        	} else {
		        		// 如果不是，就不提交，并且提示
		        		$.messager.alert("警告","文件格式不正确，必须以.xls或xlsx结尾","warning"); 
		        	}
		        },
		        // 提交表单完成后触发的事件
		        onComplete: function(response) {
		        	//将json字符串转换成json对象
		        	var data = $.parseJSON(response);
		        	// 对返回回来的数据记性判断，是否上传成功
		        	if (data.result) {
		        		$.messager.alert("恭喜","文件上传成功","info");
		        	} else {
		        		$.messager.alert("警告","文件上传失败","warning"); 
		        	}
		        	location.reload();
		        }
			});
		});
		
		// 修改外呼任务
		$("#update").click(function(){
			$.ajax({
				type:"PUT",
				url:"${pageContext.request.contextPath }/rest/callTask",
				data:$("#updateCallTaskForm").serialize(),
				statusCode:{
					201 : function(){
						$.messager.alert('提示','修改内容成功!');
					},
					400 : function(){
						$.messager.alert('提示','修改内容失败!');
					},
					500 : function(){
						$.messager.alert('提示','修改内容失败!');
					}
				}
			});
			$("#grid").datagrid("reload");
			$("#updateCallTaskForm").form("clear");
			// 清空页面所有选项
			$("#grid").datagrid("clearSelections");
			$('#updateCallTaskWindow').window("close");
		});
		
	});
	
	// 增加
	function submitForm (){
		var a1 = $("#addCallTaskForm input[name='callTaskName']").val().trim()=='';
		var a2 = $("#addCallTaskForm input[name='callServiceName']").val().trim()=='';
		var a3 = $("#addCallTaskForm input[name='planStartTime']").val().trim()=='';
		var a4 = $("#addCallTaskForm input[name='preState']").val().trim()=='';
		var a5 = $("#addCallTaskForm input[name='createTime']").val().trim()=='';
		var a6 = $("#addCallTaskForm input[name='callNumManage']").val().trim()=='';
		
		if (!(!a1 && !a2 && !a3 && !a4 && !a5 && !a6)) {
			$.messager.alert('提示','表单还未填写完成!');
			return;
		}
		//提交到后台的RESTful
		$.ajax({
		   type: "POST",
		   url: "${pageContext.request.contextPath }/rest/callTask",
		   data: $("#addCallTaskForm").serialize(),//将表单内容序列化成字符串
		   statusCode : {
			   201 : function(){
				   $.messager.alert('提示','新增内容成功!');
			   },
			   500 : function(){
				   $.messager.alert('提示','新增内容失败!');
			   }
		   }
		});
		$("#addCallTaskForm :input").val("");
		$('#cc').combobox('clear');  
		$('#serviceName').combobox('clear');  
		$("#grid").datagrid("reload");
	    $('#addCallTaskWindow').window("close");
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
		<!-- datagrid数据表格 -->
    	<table id="grid"></table>
	</div>
	
	<!-- 添加外呼任务 -->
	<div class="easyui-window" title="添加外呼任务" id="addCallTaskWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<!-- 提交外呼任务表单 -->
				<a icon="icon-save" href="#" class="easyui-linkbutton" plain="true" onclick="submitForm()">保存</a>
			</div>
		</div>
		
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="addCallTaskForm" action="">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="4">呼叫任务管理</td>
					</tr>
					<tr>
						<td>外呼任务名称</td>
						<td><input type="text" name="callTaskName" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>模板名称</td>
						<td>
							<select name="callServiceName" class="easyui-combobox" style="width:175px;">   
								<option value="">--请选择模板名称--</option>
								<option value="逾期催收(1000)">逾期催收(1000)</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>外呼号码 </td>
						<td><input type="text" name="callNumManage" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>计划开始时间 </td>
						<td><input type="text" name="planStartTime" id="planTime_start_add" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>当前任务状态</td>
						<td>
							<select name="preState" class="easyui-combobox" style="width:175px;">   
								<option value="">--请选择当前任务状态--</option>
								<option value="计划中">计划中</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>创建时间</td>
						<td><input type="text" name="createTime" id="createTime_add" class="easyui-validatebox"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 修改外呼任务 -->
	<div class="easyui-window" title="修改外呼任务" id="updateCallTaskWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<!-- 提交外呼任务表单 -->
				<a id="update" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="updateCallTaskForm" action="">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">外呼任务信息</td>
					</tr>
					<tr>
						<td>外呼任务ID</td>
						<td><input type="text" name="id" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>外呼任务名称</td>
						<td><input type="text" name="callTaskName" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>模板名称</td>
						<td>
							<select id="aa" name="callServiceName" class="easyui-combobox" style="width:175px;">   
								<option value="">--请选择业务名称--</option>
								<option value="逾期催收(1000)">逾期催收(1000)</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>外呼号码</td>
						<td><input type="text" name="callNumManage" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>计划开始时间 </td>
						<td><input type="text" name="planStartTime" id="planTime_start_update" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>当前任务状态</td>
						<td>
							<select id="cc" name="preState" class="easyui-combobox" style="width:175px;">   
								<option value="计划中">计划中</option>
								<option value="已完成">已完成</option>
								<option value="计划暂停">计划暂停</option>
								<option value="进行中_连接正常_200">进行中_连接正常_200</option>
								<option value="进行中_连接异常">进行中_连接异常</option>
								<option value="进行中_连接超时">进行中_连接超时</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>创建时间</td>
						<td><input type="text" name="createTime" id="createTime_update" class="easyui-validatebox"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	
	<!-- 查询 -->
	<div class="easyui-window" title="查询外呼任务窗口" id="searchWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="searchform" >
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>创建时间</td>
						<td>
							<input type="text" name="startCreateTime" id="createTime_start" class="easyui-validatebox"/>
							<input type="text" name="endCreateTime" id="createTime_end" class="easyui-validatebox"/>
						</td>
					</tr>
					<tr>
						<td>计划开始时间 </td>
						<td><input type="text" name="planStartTime" id="planTime_start_query" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>模板名称</td>
						<td>
							<select name="callServiceName" class="easyui-combobox" style="width:175px;"> 
							    <option value="">--请选择模板名称--</option>  
								<option value="逾期催收(1000)">逾期催收(1000)</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>任务名称</td>
						<td><input type="text" name="callTaskName" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>外呼号码</td>
						<td><input type="text" name="callNumManage" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>当前任务状态</td>
						<td>
							<select id="bb" name="preState" class="easyui-combobox" style="width:175px;">   
								<option value="">--请选择--</option>
								<option value="计划中">计划中</option>
								<option value="已完成">已完成</option>
								<option value="计划暂停">计划暂停</option>
								<option value="进行中_连接正常_200">进行中_连接正常_200</option>
								<option value="进行中_连接异常">进行中_连接异常</option>
								<option value="进行中_连接超时">进行中_连接超时</option>
							</select>
						</td>
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
	
	<!-- 批量导入-->
	<div id="batchImportWindow" class="easyui-window" title="批量导入窗口" style="top:20px;left:200px" data-options="iconCls:'icon-save',modal:true,collapsible:false">   
		<div style="overflow:auto;padding:5px;" border="false">
			<form id="batchImportFrom" action="" method="post" >
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="4">呼叫任务管理</td>
					</tr>
					<tr>
						<td>外呼任务名称</td>
						<td><input type="text" name="callTaskName" class="easyui-validatebox" data-options="required:true"></td>
					</tr>
					<tr>
						<td>模板名称</td>
						<td>
							<select id="ss" name="callServiceName" class="easyui-combobox" style="width:175px;">
								<option value="">--请选择业务名称--</option>
								<option value="逾期催收(1000)">逾期催收(1000)</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>计划开始时间 </td>
						<td><input type="text" name="planStartTime" id="planTime_start_batchImport" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>当前任务状态</td>
						<td>
							<select name="preState" class="easyui-combobox" style="width:175px;">   
								<option selected="selected" value="">--请选择当前状态--</option>
								<option value="计划中">计划中</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>创建时间</td>
						<td><input type="text" name="createTime" id="createTime_batchImport" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>批量导入号码</td>
						<td>
							<a href="#" id="importNum" class="easyui-linkbutton" data-options="iconCls:'icon-redo'">批量导入号码</a> 
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div> 
	
</body>
<!-- 时间的js代码 -->
<script type="text/javascript">
	$("#planTime_start_add").prop("readonly", true).datetimepicker({
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
	    maxDate: new Date()
	});
	$("#planTime_start_batchImport").prop("readonly", true).datetimepicker({
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
	    maxDate: new Date()
	});
	$("#planTime_start_update").prop("readonly", true).datetimepicker({
		timeText: '时间',
	    hourText: '小时',
	    minuteText: '分钟',
	    secondText: '秒',
	    currentText: '现在',
	    closeText: '完成',
	    showSecond: true, //显示秒  
	    timeFormat: 'HH:mm:ss', //格式化时间 
	    changeMonth: true,
	    dateFormat: "yy-mm-dd"
	});
	$("#planTime_start_query").prop("readonly", true).datetimepicker({
		timeText: '时间',
	    hourText: '小时',
	    minuteText: '分钟',
	    secondText: '秒',
	    currentText: '现在',
	    closeText: '完成',
	    showSecond: true, //显示秒  
	    timeFormat: 'HH:mm:ss', //格式化时间 
	    changeMonth: true,
	    dateFormat: "yy-mm-dd"
	});
	
	$("#createTime_add").prop("readonly", true).datetimepicker({
		timeText: '时间',
	    hourText: '小时',
	    minuteText: '分钟',
	    secondText: '秒',
	    currentText: '现在',
	    closeText: '完成',
	    showSecond: true, //显示秒  
	    timeFormat: 'HH:mm:ss', //格式化时间 
	    changeMonth: true,
	    dateFormat: "yy-mm-dd"
	});
	$("#createTime_batchImport").prop("readonly", true).datetimepicker({
		timeText: '时间',
	    hourText: '小时',
	    minuteText: '分钟',
	    secondText: '秒',
	    currentText: '现在',
	    closeText: '完成',
	    showSecond: true, //显示秒  
	    timeFormat: 'HH:mm:ss', //格式化时间 
	    changeMonth: true,
	    dateFormat: "yy-mm-dd"
	});
	$("#createTime_update").prop("readonly", true).datetimepicker({
		timeText: '时间',
	    hourText: '小时',
	    minuteText: '分钟',
	    secondText: '秒',
	    currentText: '现在',
	    closeText: '完成',
	    showSecond: true, //显示秒  
	    timeFormat: 'HH:mm:ss', //格式化时间 
	    changeMonth: true,
	    dateFormat: "yy-mm-dd"
	});
	
	
	
	$("#createTime_start").prop("readonly", true).datetimepicker({
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
	
	$("#createTime_end").prop("readonly", true).datetimepicker({
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
</script>
</html>