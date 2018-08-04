package cn.hf.manage.form;

import java.sql.Timestamp;

import org.springframework.util.StringUtils;

/**
 * CreateUser： YeShengGuang
 * CreateTime：2017年10月24日
 * FileExplain:呼叫结果表单类
 */
public class CallResultForm extends BaseForm{
	
	private String callId;
	
	private String taskName;
	
	private String taskStatus;
	
	private String callResult;
	
	private String callLabel;
	
	private String communicateTime;
	
	private Timestamp callTime;
	
	private Timestamp endTime;
	
	private Timestamp time_1;
	
	private Timestamp time_2;
	
	private String duration_1;
	
	private String duration_2;
	
	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	public String getCallResult() {
		return callResult;
	}

	public void setCallResult(String callResult) {
		this.callResult = callResult;
	}

	public String getCallLabel() {
		return callLabel;
	}

	public void setCallLabel(String callLabel) {
		this.callLabel = callLabel;
	}

	public String getCommunicateTime() {
		return communicateTime;
	}

	public void setCommunicateTime(String communicateTime) {
		this.communicateTime = communicateTime;
	}

	public Timestamp getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = StringUtils.isEmpty(callTime)?null:Timestamp.valueOf(callTime);;
	}

	public Timestamp getTime_1() {
		return time_1;
	}

	public void setTime_1(String time_1) {
		this.time_1 = StringUtils.isEmpty(time_1)?null:Timestamp.valueOf(time_1);
	}

	public Timestamp getTime_2() {
		return time_2;
	}

	public void setTime_2(String time_2) {
		this.time_2 = StringUtils.isEmpty(time_2)?null:Timestamp.valueOf(time_2);
	}

	public String getDuration_1() {
		return duration_1;
	}

	public void setDuration_1(String duration_1) {
		this.duration_1 = duration_1;
	}

	public String getDuration_2() {
		return duration_2;
	}

	public void setDuration_2(String duration_2) {
		this.duration_2 = duration_2;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = StringUtils.isEmpty(endTime)?null:Timestamp.valueOf(endTime);
	}
	
}
