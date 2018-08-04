<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>关键字管理页面</title>
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
		$('#addKeyWordsWindow').window("open");
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
		$("#updateKeyWordsForm input[name=id]").val(rows[0].id);
		$("#updateKeyWordsForm input[name=text]").val(rows[0].text);
		$("#updateKeyWordsForm input[name=masterLabel]").val(rows[0].masterLabel);
		$("#updateKeyWordsForm input[name=assistantLabel]").val(rows[0].assistantLabel);
		// 打开窗口
		$('#updateKeyWordsWindow').window("open");
	}
	
	// 搜索
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
			url:"${pageContext.request.contextPath }/rest/keyWords",
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
	},{
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
		title : '关键字ID',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.id;
		}
	},{
		field : 'text',
		title : '文本',
		width : 120,
		align : 'center',
		formatter : function(data, row ,index){
			return row.text;
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
			pageList: [10,50,100],
			pagination : true,
			toolbar : toolbar,
			// 请求服务器，加载数据，显示在datagrid中
			url : "${pageContext.request.contextPath }/rest/keyWords/query",
			//url : "${pageContext.request.contextPath }/json/keywords.json",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加关键字窗口
		$('#addKeyWordsWindow').window({
	        title: '添加关键字',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		// 修改关键字窗口
		$('#updateKeyWordsWindow').window({
	        title: '修改关键字',
	        width: 600,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		// 查询关键字窗口
		$('#searchWindow').window({
	        title: '查询关键字',
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
		$("#updateKeyWordsForm").form("load",rowData);
		$('#updateKeyWordsWindow').window("open");
	};
	
	// 增加关键字
	function submitForm (){
		if(!$('#addKeyWordsForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		//提交到后台的RESTful
		$.ajax({
		   type: "POST",
		   url: "${pageContext.request.contextPath }/rest/keyWords",
		   data: $("#addKeyWordsForm").serialize(),//将表单内容序列化成字符串
		   statusCode : {
			   201 : function(){
				   $.messager.alert('提示','新增内容成功!');
				   $("#addKeyWordsForm").form("clear");
				   $("#grid").datagrid("reload");
				   $('#addKeyWordsWindow').window("close");
			   },
			   500 : function(){
				   $.messager.alert('提示','新增内容失败!');
				   $("#addKeyWordsForm").form("clear");
				   $("#grid").datagrid("reload");
				   $('#addKeyWordsWindow').window("close");
			   }
		   }
		});
	}
	
	
	// 修改关键字
	function onAfterEdit(){
		if(!$('#updateKeyWordsForm').form('validate')){
			$.messager.alert('提示','表单还未填写完成!');
			return ;
		}
		//提交到后台的RESTful
		$.ajax({
		   type: "PUT",
		   url: "${pageContext.request.contextPath }/rest/keyWords",
		   data: $("#updateKeyWordsForm").serialize(),//将表单内容序列化成字符串
		   statusCode : {
			   200 : function(){
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
		$("#updateKeyWordsForm").form("clear");
	    $('#updateKeyWordsWindow').window("close");
	}
</script>
</head>
<body class="easyui-layout" style="visibility: hidden;">
	<div region="center" border="false">
		<!-- datagrid数据表格 -->
		<table id="grid"></table>
	</div>

	<!-- 添加关键字 -->
	<div class="easyui-window" title="添加关键字" id="addKeyWordsWindow" collapsible="false" minimizable="false" maximizable="false" style="top: 20px; left: 200px">
		<div style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<!-- 提交关键字表单 -->
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" onclick="submitForm()">保存</a>
			</div>
		</div>

		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="addKeyWordsForm" action="${pageContext.request.contextPath }/rest/keyWords" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">关键字信息</td>
					</tr>
					<tr>
						<td>文本</td>
						<td><input type="text" name="text" class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>主标签</td>
						<td><input type="text" name="masterLabel" class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>副标签</td>
						<td><input type="text" name="assistantLabel" class="easyui-validatebox" required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<!-- 修改关键字 -->
	<div class="easyui-window" title="修改关键字" id="updateKeyWordsWindow" collapsible="false" minimizable="false" 
			maximizable="false" style="top: 20px; left: 200px">
		<div style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<!-- 提交关键字表单 -->
				<a id="update" icon="icon-save" href="#" class="easyui-linkbutton" plain="true"  onclick="onAfterEdit()">保存</a>
			</div>
		</div>

		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="updateKeyWordsForm" action="" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">关键字信息</td>
					</tr>
					<tr>
						<td>文本ID</td>
						<td><input type="text" name="id" class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>文本</td>
						<td><input type="text" name="text" class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>主标签</td>
						<td><input type="text" name="masterLabel" class="easyui-validatebox" required="true" /></td>
					</tr>
					<tr>
						<td>副标签</td>
						<td><input type="text" name="assistantLabel" class="easyui-validatebox" required="true" /></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<!-- 查询 -->
	<div class="easyui-window" title="查询关键字窗口" id="searchWindow" collapsible="false" minimizable="false"
			 maximizable="false" style="top: 20px; left: 200px">
		<div style="overflow: auto; padding: 5px;" border="false">
			<form id="searchform">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询条件</td>
					</tr>
					<tr>
						<td>文本</td>
						<td><input type="text" name="text" class="easyui-validatebox" /></td>
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
						<td colspan="2"><a id="btn" href="javascript:;" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>