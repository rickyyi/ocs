package cn.hf.manage.pojo;

public class HttpResult {

	
	private Integer code; //响应状态码
	
	private String data;//响应内容

	
	public HttpResult(Integer code, String data) {
		super();
		this.code = code;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
	
	
}
