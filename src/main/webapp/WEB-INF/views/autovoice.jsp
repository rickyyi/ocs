<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>语音文件管理页面</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/default.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/ext/jquery.cookie.js"></script>
<script src="${pageContext.request.contextPath }/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath }/js/easyui/outOfBounds.js" type="text/javascript"></script>
<!-- 引入ocupload一键上传组件 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ocupload/jquery.ocupload-1.1.2.js"></script>


<script type="text/javascript"> 
   
    // 添加
	function doAdd(){
		$('#addAutoVoiceWindow').window("open");
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
		$("#updateAutoVoiceForm input[name=id]").val(rows[0].id);
		$("#updateAutoVoiceForm input[name=name]").val(rows[0].name);
		$("#updateAutoVoiceForm input[name=masterLabel]").val(rows[0].masterLabel);
		$("#updateAutoVoiceForm input[name=masterLabel]").val(rows[0].assistantLabel);
		$("#updateAutoVoiceForm input[name=weight]").val(rows[0].weight);
		$("#updateAutoVoiceForm input[name=sellPoint]").val(rows[0].sellPoint);
		$("#intention_update").val(rows[0].intention);
		// 打开窗口
		$('#updateAutoVoiceWindow').window("open");
	}
	
	// 查询
	function doSearch(){
		$('#searchWindow').window("open");
	}
	
	// 删除号码
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
			url:"${pageContext.request.contextPath }/rest/autoVoice",
			data:{"ids":ids, "_method":"DELETE"},
			success: function(msg){
			    $.messager.alert("恭喜","删除成功","info");
			    // 重新加载数据，
				$("#grid").datagrid("reload");
				// 清空页面所有选项
				$("#grid").datagrid("clearSelections");
  			   },
  			error: function(){
 				$.messager.alert("警告","删除失败","warning");
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
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-edit',	
		text : '修改',
		iconCls : 'icon-edit',
		handler : doEdit
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}];
	// 定义列
	var columns = [ [ {
		field : 'aa',
		checkbox : true,
	}, {
		field : 'id',
		title : '语音文件ID',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.id;
		}
	},{
		field : 'name',
		title : '语音文件名',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.name;
		}
	}, {
		field : 'masterLabel',
		title : '主标签',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.masterLabel;
		}
	}, {
		field : 'assistantLabel',
		title : '副标签',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.assistantLabel;
		}
	}, {
		field : 'weight',
		title : '权重',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.weight;
		}
	}, {
		field : 'sellPoint',
		title : '卖点',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.sellPoint;
		}
	}, {
		field : 'intention',
		title : '意向标签',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.intention;
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
			// 请求服务器，加载数据，显示在datagrid中
			url : "${pageContext.request.contextPath }/rest/autoVoice/query",
			//url : "${pageContext.request.contextPath }/json/autovoice.json",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加语音文件窗口
		$('#addAutoVoiceWindow').window({
	        title: '添加语音文件',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		// 修改语音文件窗口
		$('#updateAutoVoiceWindow').window({
	        title: '修改语音文件',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 查询语音文件窗口
		$('#searchWindow').window({
	        title: '查询语音文件',
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
			$('#searchform :input').val("");
			$('#searchWindow').window("close");
		});
		
	});
	
	// 方法
	// 双击修改
	function doDblClickRow(rowIndex, rowData){
		$("#updateAutoVoiceForm").form("load",rowData);
		$('#updateAutoVoiceWindow').window("open");
	};
	
	// 增加语音文件
	function submitForm (){
		if(!$('#addAutoVoiceForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return;
		}
		//提交到后台的RESTful
		$.ajax({
		   type: "POST",
		   url: "${pageContext.request.contextPath }/rest/autoVoice",
		   data: $("#addAutoVoiceForm").serialize(),//将表单内容序列化成字符串
		   statusCode : {
			   201 : function(){
				   $.messager.alert('提示','新增内容成功!');
			   },
			   500 : function(){
				   $.messager.alert('提示','新增内容失败!');
			   }
		   }
		});
		   $("#grid").datagrid("reload");
		   $("#addAutoVoiceForm").form("clear");
		   $('#addAutoVoiceWindow').window("close");
	}
	
	// 修改语音文件
	function onAfterEdit(){
		if(!$('#updateAutoVoiceForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		//提交到后台的RESTful
		$.ajax({
		   type: "PUT",
		   url: "${pageContext.request.contextPath }/rest/autoVoice",
		   data: $("#updateAutoVoiceForm").serialize(),//将表单内容序列化成字符串
		   statusCode : {
			   200 : function(){
				   $.messager.alert('提示','修改内容成功!');
				   $("#grid").datagrid("reload");
			   },
			   400 : function(){
				   $.messager.alert('提示','修改内容失败!');
			   },
			   500 : function(){
				   $.messager.alert('提示','修改内容失败!');
			   }
		   }
		});
		$("#updateAutoVoiceForm").form("clear");
	    $('#updateAutoVoiceWindow').window("close");
	}
</script>
</head>
<body class="easyui-layout" style="visibility: hidden;">
	<div region="center" border="false">
		<!-- datagrid数据表格 -->
		<table id="grid"></table>
	</div>

	<!-- 添加语音文件 -->
	<div class="easyui-window" title="添加语音文件" id="addAutoVoiceWindow" collapsible="false" minimizable="false" maximizable="false" style="top: 20px; left: 200px">
		<div style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<!-- 提交语音文件表单 -->
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" onclick="submitForm()">保存</a>
			</div>
		</div>

		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="addAutoVoiceForm" action="" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">语音文件信息</td>
					</tr>
					<tr>
						<td>语音文件名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>主标签</td>
						<td><input type="text" name="masterLabel" class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>副标签</td>
						<td><input type="text" name="assistantLabel" class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>权重</td>
						<td><input type="text" name="weight" class="easyui-validatebox" required="true"></td>
					</tr>
					<tr>
						<td>卖点</td>
						<td><input type="text" name="sellPoint" class="easyui-validatebox" required="true"></td>
					</tr>
					<tr>
						<td>意向标签</td>
						<td>
							<select name="intention" class="easyui-combobox" style="width:175px;">
							 	<option value="">--请选择意向标签--</option>
							 	<option value="A">A</option>
							 	<option value="B">B</option>
							 	<option value="C">C</option>
							 	<option value="D">D</option>
							 	<option value="E">E</option>
							 	<option value="F">F</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<!-- 修改语音文件 -->
	<div class="easyui-window" title="修改语音文件" id="updateAutoVoiceWindow" collapsible="false" minimizable="false" 
			maximizable="false" style="top: 20px; left: 200px">
		<div style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<!-- 提交语音文件表单 -->
				<a id="update" icon="icon-save" href="#" class="easyui-linkbutton" plain="true"  onclick="onAfterEdit()">保存</a>
			</div>
		</div>

		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="updateAutoVoiceForm" action="" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">语音文件信息</td>
					</tr>
					<tr>
						<td>语音文件ID</td>
						<td><input type="text" name="id" class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>语音文件名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>主标签</td>
						<td><input type="text" name="masterLabel" class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>副标签</td>
						<td><input type="text" name="assistantLabel" class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>权重</td>
						<td><input type="text" name="weight" class="easyui-validatebox" required="true"></td>
					</tr>
					<tr>
						<td>卖点</td>
						<td><input type="text" name="sellPoint" class="easyui-validatebox" required="true"></td>
					</tr>
					<tr>
						<td>意向标签</td>
						<td>
							<select id="intention_update" name="intention" class="easyui-combobox" style="width:175px;">
							 	<option value="">--请选择意向标签--</option>
							 	<option value="A">A</option>
							 	<option value="B">B</option>
							 	<option value="C">C</option>
							 	<option value="D">D</option>
							 	<option value="E">E</option>
							 	<option value="F">F</option>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<!-- 查询 -->
	<div class="easyui-window" title="查询语音文件窗口" id="searchWindow" collapsible="false" minimizable="false"
			 maximizable="false" style="top: 20px; left: 200px">
		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="searchform">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>语音文件名</td>
						<td><input type="text" name="name" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>主标签</td>
						<td><input type="text" name="masterLabel" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>副标签</td>
						<td><input type="text" name="assistantLabel" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>结束标签</td>
						<td><input type="text" name="finishTag" class="easyui-validatebox" /></td>
					</tr>
					<tr>
						<td>权重</td>
						<td><input type="text" name="weight" class="easyui-validatebox" required="true"></td>
					</tr>
					<tr>
						<td>卖点</td>
						<td><input type="text" name="sellPoint" class="easyui-validatebox" required="true"></td>
					</tr>
					<tr>
						<td>意向标签</td>
						<td>
							<select name="intention" class="easyui-combobox" style="width:175px;">
							 	<option value="">--请选择意向标签--</option>
							 	<option value="A">A</option>
							 	<option value="B">B</option>
							 	<option value="C">C</option>
							 	<option value="D">D</option>
							 	<option value="E">E</option>
							 	<option value="F">F</option>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="2"><a id="btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a> </td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>