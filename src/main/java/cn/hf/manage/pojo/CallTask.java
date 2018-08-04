package cn.hf.manage.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 说明：呼叫任务实体类
 * 
 * @author XiJie
 * @version 1.0
 * @date 2017年10月23日
 */
@Table(name = "h_call_task")
public class CallTask {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 外呼任务ID

	@Column(name = "call_task_name")
	private String callTaskName;// 外呼任务名称

	@Column(name = "call_service_name")
	private String callServiceName;// 外呼业务名称

	@Column(name = "call_num_manage")
	private String callNumManage;// 外呼号码管理

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "plan_start_time")
	private Timestamp planStartTime;// 计划开始时间

	@Column(name = "pre_state")
	private String preState;// 当前任务状态

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	@Column(name = "create_time")
	private Timestamp createTime;// 创建时间
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCallTaskName() {
		return callTaskName;
	}

	public void setCallTaskName(String callTaskName) {
		this.callTaskName = callTaskName;
	}

	public String getCallServiceName() {
		return callServiceName;
	}

	public void setCallServiceName(String callServiceName) {
		this.callServiceName = callServiceName;
	}

	public String getCallNumManage() {
		return callNumManage;
	}

	public void setCallNumManage(String callNumManage) {
		this.callNumManage = callNumManage;
	}

	public Timestamp getPlanStartTime() {
		return planStartTime;
	}

	public void setPlanStartTime(Timestamp planStartTime) {
		this.planStartTime = planStartTime;
	}

	public String getPreState() {
		return preState;
	}

	public void setPreState(String preState) {
		this.preState = preState;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "CallTask [id=" + id + ", callTaskName=" + callTaskName + ", callServiceName=" + callServiceName
				+ ", callNumManage=" + callNumManage + ", planStartTime=" + planStartTime + ", preState=" + preState
				+ ", createTime=" + createTime + "]";
	}

}
