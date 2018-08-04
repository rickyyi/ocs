package cn.hf.manage.pojo;

import java.util.List;

/**
 * 说明：接受的实体类2
 * 
 * @author XiJie
 * @version 1.0
 * @date 2017年10月14日
 */
public class AcceptText2 {

	private String statusCode;// 状态吗

	private String errorDes;// 没有查询到的字段

	private List<String> labels;// 主副标签

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getErrorDes() {
		return errorDes;
	}

	public void setErrorDes(String errorDes) {
		this.errorDes = errorDes;
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	@Override
	public String toString() {
		return "AcceptText2 [statusCode=" + statusCode + ", errorDes=" + errorDes + ", labels=" + labels + "]";
	}

}
