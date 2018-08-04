package cn.hf.manage.pojo;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 说明：保存没有匹配上的文本实体类
 * 
 * @author XiJie
 * @version 1.0
 * @date 2017年10月17日
 */
@Table(name = "h_text")
public class Text {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 主键id

	private String name;// 要保存的文本

	private String callid;// callid

	private String cause;// 导致的原因

	@Column(name = "master_label")
	private String masterLabel;// 主标签

	@Column(name = "assistant_label")
	private String assistantLabel;// 副标签

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "create_time")
	private Timestamp createTime;// 创建时间
	
	@Column(name = "is_hit")
	private String isHit;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCallid() {
		return callid;
	}

	public void setCallid(String callid) {
		this.callid = callid;
	}

	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

	public String getMasterLabel() {
		return masterLabel;
	}

	public void setMasterLabel(String masterLabel) {
		this.masterLabel = masterLabel;
	}

	public String getAssistantLabel() {
		return assistantLabel;
	}

	public void setAssistantLabel(String assistantLabel) {
		this.assistantLabel = assistantLabel;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getIsHit() {
		return isHit;
	}

	public void setIsHit(String isHit) {
		this.isHit = isHit;
	}

	@Override
	public String toString() {
		return "Text [id=" + id + ", name=" + name + ", callid=" + callid
				+ ", cause=" + cause + ", masterLabel=" + masterLabel
				+ ", assistantLabel=" + assistantLabel + ", createTime="
				+ createTime + ", isHit=" + isHit + "]";
	}

}
