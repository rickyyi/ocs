package cn.hf.manage.pojo;

import java.sql.Timestamp;

import org.apache.commons.lang3.StringUtils;

/**
 * 说明：呼叫任务实体类
 * 
 * @author XiJie
 * @version 1.0
 * @date 2017年10月23日
 */
public class CallTask2 {

	private Integer id;// 外呼任务ID
	private String callTaskName;// 外呼任务名称
	private String callServiceName;// 外呼业务名称
	private String callNumManage;// 外呼号码管理
	private Timestamp planStartTime;// 计划开始时间
	private String preState;// 当前任务状态
	private Timestamp createTime;// 创建时间
	private Timestamp startCreateTime;
	private Timestamp endCreateTime;

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

	public void setPlanStartTime(String planStartTime) {
		if (StringUtils.isNotEmpty(planStartTime)) {
			this.planStartTime = Timestamp.valueOf(planStartTime);
		}
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

	public void setCreateTime(String createTime) {
		if (StringUtils.isNotEmpty(createTime)) {
			this.createTime = Timestamp.valueOf(createTime);
		}
	}

	public Timestamp getStartCreateTime() {
		return startCreateTime;
	}

	public void setStartCreateTime(String startCreateTime) {
		if (StringUtils.isNotEmpty(startCreateTime)) {
			this.startCreateTime = Timestamp.valueOf(startCreateTime);
		}
	}

	public Timestamp getEndCreateTime() {
		return endCreateTime;
	}

	public void setEndCreateTime(String endCreateTime) {
		if (StringUtils.isNotEmpty(endCreateTime)) {
			this.endCreateTime = Timestamp.valueOf(endCreateTime);
		}
	}

	@Override
	public String toString() {
		return "CallTask [id=" + id + ", callTaskName=" + callTaskName + ", callServiceName=" + callServiceName
				+ ", callNumManage=" + callNumManage + ", planStartTime=" + planStartTime + ", preState=" + preState
				+ ", createTime=" + createTime + ", startCreateTime=" + startCreateTime + ", endCreateTime="
				+ endCreateTime + "]";
	}

}
