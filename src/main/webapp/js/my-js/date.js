$(function(){
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
	
	$("#plannedStartTime").prop("readonly", true).datetimepicker({
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
});