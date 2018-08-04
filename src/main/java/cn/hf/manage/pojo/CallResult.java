package cn.hf.manage.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * CreateUser： YeShengGuang CreateTime：2017年10月23日 FileExplain:呼叫结果实体类
 */
@Table(name = "h_call_result")
public class CallResult {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "call_num_manege")
	private String callNumManage;// 外呼号码管理

	@Column(name = "task_id")
	private Integer taskId;// 任务ID

	@Column(name = "task_name")
	private String taskName;// 任务名称

	@Column(name = "task_status")
	private String taskStatus;// 任务状态

	@Column(name = "call_result")
	private String callResult;// 呼叫结果

	private String purpose;// 意向标签
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "answer_time")
	private Timestamp answertime;//振铃时间
	
	@Column(name="service_id")
	private Integer serviceId;//业务标识

	@Column(name = "communicate_time")
	private String communicateTime;// 通话时长

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "call_time")
	private Timestamp callTime;// 呼叫时间

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "end_time")
	private Timestamp endTime;// 结束时间
	
	@Column(name="call_id")
	private String callId;		//呼叫id

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getCallNumManage() {
		return callNumManage;
	}

	public void setCallNumManage(String callNumManage) {
		this.callNumManage = callNumManage;
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

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
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

	public void setCallTime(Timestamp callTime) {
		this.callTime = callTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Timestamp getAnswertime() {
		return answertime;
	}

	public void setAnswertime(Timestamp answertime) {
		this.answertime = answertime;
	}

	public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	
	public String getCallId() {
		return callId;
	}

	public void setCallId(String callId) {
		this.callId = callId;
	}

	@Override
	public String toString() {
		return "CallResult [id=" + id + ", callNumManage=" + callNumManage + ", taskName=" + taskName + ", taskStatus="
				+ taskStatus + ", callResult=" + callResult + ", purpose=" + purpose + ", answertime=" + answertime
				+ ", serviceId=" + serviceId + ", communicateTime=" + communicateTime + ", callTime=" + callTime
				+ ", endTime=" + endTime + ", callId=" + callId + "]";
	}

}
