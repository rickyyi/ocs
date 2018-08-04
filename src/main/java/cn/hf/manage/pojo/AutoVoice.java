package cn.hf.manage.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 说明：语音文件实体类
 * 
 * @author XiJie
 * @version 1.0
 * @date 2017年10月15日
 */
@Table(name = "h_auto_voice")
public class AutoVoice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 语音文件id

	private String name;// 语音文件名

	private Integer weight;// 权重
 
	@Column(name = "sell_point")
	private String sellPoint;// 卖点

	@Column(name = "master_label")
	private String masterLabel;// 主标签

	@Column(name = "assistant_label")
	private String assistantLabel;// 副标签
	
	private String intention;//意向标签

	public String getIntention() {
		return intention;
	}

	public void setIntention(String intention) {
		this.intention = intention;
	}

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

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getSellPoint() {
		return sellPoint;
	}

	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
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

	@Override
	public String toString() {
		return "AutoVoice [id=" + id + ", name=" + name + ", weight=" + weight + ", sellPoint=" + sellPoint
				+ ", masterLabel=" + masterLabel + ", assistantLabel=" + assistantLabel + ", intention=" + intention
				+ "]";
	}

}
