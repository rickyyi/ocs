//搜索
function doSearch() {
	$('#searchWindow').window("open");
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
					$("#media").attr("src",data.voicePath+"?v="+parseInt(Math.random()*10000));
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

//设置标题
function setTitle(callnum,calltime){
	var title = "通话详情&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	title+="被呼ID："+callnum+"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;呼叫时间："+calltime;
	$("#infoWindow").panel({title: title});
}

//工具栏
var toolbar = [ {
	id : 'button-search',
	text : '查询',
	iconCls : 'icon-search',
	handler : doSearch
} ];

// 定义列
var columns = [ [ 
                 {
                	 field : 'id',
                	 hidden:'true'
                 },{
                	 field : 'callId',
                	 hidden:'true'
                 },{
                	 field : 'callNumManage',
                	 title : '号码',
                	 width : 120,
                	 align : 'center'
                 }, 
                 {
                	 field : 'taskId',
                	 title : '外呼任务ID',
                	 width : 120,
                	 align : 'center'
                 },
                 {
                	 field : 'taskName',
                	 title : '外呼任务名称',
                	 width : 120,
                	 align : 'center'
                 }, {
                	 field : 'taskStatus',
                	 title : '任务状态',
                	 width : 120,
                	 align : 'center'
                 }, {
                	 field : 'callResult',
                	 title : '呼叫结果',
                	 width : 120,
                	 align : 'center'
                 }, {
                	 field : 'purpose',
                	 title : '意向标签',
                	 width : 120,
                	 align : 'center',
                	 hidden: 'true'
                 }, {
                	 field : 'communicateTime',
                	 title : '通话时长',
                	 width : 120,
                	 align : 'center'
                 },{
                	 field : 'answertime',
                	 title : '接听时间',
                	 width : 120,
                	 align : 'center'
                 }, {
                	 field : 'callTime',
                	 title : '呼叫时间',
                	 width : 120,
                	 align : 'center'
                 }, {
                	 field : 'endTime',
                	 title : '结束时间',
                	 width : 120,
                	 align : 'center'
                 }, {
                	 field : 'operation',
                	 title : '操作',
                	 width : 120,
                	 align : 'center',
                	 formatter:function(value, rowData, rowIndex){
                		 var btn = '<a style="text-decoration: none;" href=javascript:getCallResult('+rowIndex+')>详情</a>';
                		 if (rowData.callId==null)
                			 btn = '<a style="text-decoration: none;color:gray;" href=javascript:void()>详情</a>';
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
		pageList: [10,50,100,500,1000],
		pageSize: 100,
		pagination : true,
		toolbar : toolbar,
		// 请求服务器，加载数据，显示在datagrid中
		url : "../callResult/query",
		idField : 'id',
		columns : columns,
		onDblClickRow : doDblClickRow
	});
	setInterval(function(){
		$("#grid").datagrid("reload");
	}, 1000*10)
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
/*//页面内容加载完后执行里面内容
$(function(){
	//在工具栏中加入选择呼叫任务下拉框
	$("table:eq(0)").find("tr").append('<td>选择呼叫任务：<select style="width:100px;" class="easyui-combobox" data-options="editable:false"><option selected>不限</option></select></td>');
});*/