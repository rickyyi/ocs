package cn.hf.manage.pojo;

import java.util.List;

/**
 * 说明：返回的实体类
 * 
 * @author XiJie
 * @version 1.0
 * @date 2017年10月14日
 */
public class ReturnText {
	
	private String masterLabel;// 主标签

	private String assistantLabel;// 副标签

	private List<String> labels;// 主副标签

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

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	@Override
	public String toString() {
		return "ReturnText [masterLabel=" + masterLabel + ", assistantLabel=" + assistantLabel + ", labels=" + labels
				+ "]";
	}

}
