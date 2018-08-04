//编辑
function editText(index) {
	var value=window.prompt("请输入纠正后的语音文本");
	//校验数据
	if (value!="" && value!=null) {
		//发送put请求修改数据
		$.post("../text/update",{"id":$("#grid").datagrid("getData").rows[index].id,"isHit":value},function(data,status){
			if (status=="success") {
				$("#grid").datagrid("reload");
			}
		},"json").error(function(){
			$.messager.alert("消息提示","请求发生错误","error");
		});
	}
}

//查询
function searchText(){
	//获取数据校验
	var callId = $("input[name=callId]").val();
	if(callId == ""){
		$("#grid").datagrid("reload");
	}
	else if (callId != null) {
		//post请求条件查询
		$.post("../text/query",{"callid":callId},function(data,status){
			if (status=="success") {
				$("#grid").datagrid("loadData", data);
				$("input[class=pagination-num]").val(1);
			}
		},"json").error(function(){
			$.messager.alert("消息提示","请求发生错误","error");
		});
	} 
}

//手机号码：下次点击详情如果是同一个号码，则不进行数据库查找
var callID = "";
//详情
function getCallResult(index){
	//获得当前行数据
	var rowData = $("#grid").datagrid("getRows")[index];
	//校验是否点击的是同一条数据
	if (callID!=rowData.callNumManage) {
		//清除ul
		$('#infoWindow ul').html("<li></li>");
		//校验数据
		if (rowData.callId!=null && rowData.callId != undefined && rowData.callId != "") {
			//ajax获取对话数据
			$.post("../callResult/callinfo",{"callid":rowData.callId},function(data,status){
				if (status=="success") {
					//设置语音控件地址
					$("#media").attr("src",data.voicePath);
					callID = rowData.callNumManage;
					//设置标题
					setTitle(rowData.callNumManage,rowData.callTime);
					//聊天消息存放对象
					$li = $('#infoWindow ul li');
					$li.append('<li><div class="textright">'+data.service[0].serviceText+'</div></li>');
					//设置消息记录
					for (var i = 0; i < data.client.length; i++) {
						$li.append('<li><a href="javascript:playAudio()"><div class="textleft">'+data.client[i].clientText+'</div></a><audio></audio></li>');
						var serviceText = data.service[i+1].serviceText;
						$li.append('<li><div class="textright">'+(serviceText==undefined?'没有对应语音文本':serviceText)+'</div></li>');
					}
				}
			},"json").error(function(){
				$.messager.alert("消息提示","请求发生错误","error");
			})
		}
	}
	//打开通话详情界面
	$('#infoWindow').window("open");
}

//刷新语音
function reloadVoice(index){
	//获得当前行数据
	var rowData = $("#grid").datagrid("getRows")[index];
	var callid = rowData.callid;
	$.get("../text/reload/"+callid,function(data){}).error(function(){$.messager.alert("消息提示","请求发生错误","error");});
	
}

//设置标题
function setTitle(callnum,calltime){
	var title = "通话详情&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	title+="被呼ID："+callnum+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;呼叫时间："+calltime;
	$("#infoWindow").panel({title: title});
}

// 定义列
var columns = [ [ 
                 {
                	 field : 'id',
                	 hidden:'true'
                 },{
                	 field : 'name',
                	 title : '语音识别文字',
                	 align : 'center',
                	 resizable:true
                 }, {
                	 field : 'callid',
                	 title : '呼叫ID',
                	 width : 160,
                	 align : 'center'
                 }, {
                	 field : 'cause',
                	 title : '播放文件',
                	 width : 120,
                	 align : 'center'
                 }, {
                	 field : 'masterLabel',
                	 title : '主标签',
                	 width : 60,
                	 align : 'center'
                 }, {
                	 field : 'assistantLabel',
                	 title : '副标签',
                	 width : 60,
                	 align : 'center'
                 }, {
                	 field : 'createTime',
                	 title : '创建时间',
                	 width : 180,
                	 align : 'center'
                 },{
                	 field : 'isHit',
                	 title : '纠正识别文字',
                	 width : 220,
                	 align : 'center'
                 }, {
                	 field : 'operation',
                	 title : '操作',
                	 width : 430,
                	 align : 'center',
                	 formatter:function(value, rowData, rowIndex){
                		 var btn = '<div style="float:left;margin-top: 6px;"><a style="text-decoration: none;" href=javascript:editText('+rowIndex+')>编辑</a>&nbsp;&nbsp;';
                		 btn += '<a style="text-decoration: none;" href="javascript:reloadVoice('+rowIndex+')">刷新语音</a>&nbsp;&nbsp;';
                		 btn += '</div><audio style="width:300px;" controls src="/voice/client/'+rowData.callid+'.wavv?v='+parseInt(Math.random()*10000)+'"></audio>';
                		 return btn;
                	 }
                 } ] ];

$(function() {
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
		pageSize : 25,
		pageList : [ 25, 50, 100 ],
		pagination : true,
		toolbar : toolbar,
		// 请求服务器，加载数据，显示在datagrid中
		url : "../text/query",
		idField : 'id',
		columns : columns,
		onDblClickRow : doDblClickRow
	});

	// 查询分区
	$('#searchWindow').window({
		title : '查询分区',
		width : 580,
		modal : true,
		shadow : false,
		closed : true,
		height : 400,
		resizable : false,
		left:293,
		top:103
	});
	// 查询分区
	$('#infoWindow').window({
		title : '通话详情&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
		width : 920,
		modal : true,
		shadow : false,
		closed : true,
		height : 560,
		resizable : false,
		left:165,
		top:31,
		onBeforeClose:function(){
			$("#media")[0].pause();
		}
	});

	// 分页    条件查询
	$("#searchBtn").click(function() {
		$.post("../callResult/query",$("#searchform").serializeJson(),function(data,status){
			if (status=="success") {
				$("#grid").datagrid("loadData", data);//当点击查询时，会重新加载datagrid
				// 请求结束后，关闭窗口
				$('#searchWindow').window("close");
			}
		},"json").error(function(){
			$.messager.alert("消息提示","请求发生错误","error");
		});
	});
	
	//清空表单数据
	$("#clearBtn").click(function(){
		$('#searchform input').val('');
	})
});

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
//页面内容加载完后执行里面内容
$(function(){
	//在工具栏中加入选择呼叫任务下拉框
	$("table:eq(0)").find("tr").append('<td style="padding-left: 5px;">callID：<input name="callId"></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button onclick="javascript:searchText();" style="border:0px;background-color:#F0FFF0">查 询</button></td>');
});