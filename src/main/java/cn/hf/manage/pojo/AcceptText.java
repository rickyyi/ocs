package cn.hf.manage.pojo;

/**
 * 说明：接受的实体类
 * 
 * @author XiJie
 * @version 1.0
 * @date 2017年10月14日
 */
public class AcceptText {

	private String statusCode;// 状态吗

	private String masterLabel;// 主标签

	private String assistantLabel;// 副标签
	
	private String errorDes;// 没有查询到的字段

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
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

	public String getErrorDes() {
		return errorDes;
	}

	public void setErrorDes(String errorDes) {
		this.errorDes = errorDes;
	}

	@Override
	public String toString() {
		return "AcceptText [statusCode=" + statusCode + ", masterLabel=" + masterLabel + ", assistantLabel="
				+ assistantLabel + ", errorDes=" + errorDes + "]";
	}

}
