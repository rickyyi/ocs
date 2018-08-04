package cn.hf.manage.pojo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "h_key_words")
public class KeyWords {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;// 文本ID

	private String text;// 文本

	@Column(name = "master_label")
	private String masterLabel;// 主标签

	@Column(name = "assistant_label")
	private String assistantLabel;// 副标签

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
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
		return "KeyWords [id=" + id + ", text=" + text + ", masterLabel=" + masterLabel + ", assistantLabel="
				+ assistantLabel + "]";
	}

}
