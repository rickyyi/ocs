package cn.hf.manage.pojo;

import java.util.List;
import java.util.Map;

/**
 * CreateUser： YeShengGuang
 * CreateTime：2017年10月31日
 * FileExplain: 呼叫结果详情
 */
public class CallResultInfo {
	
	private String voicePath;
	private List<Map<String, String>> client;
	private List<Map<String, String>> service;
	
	public String getVoicePath() {
		return voicePath;
	}
	public void setVoicePath(String voicePath) {
		this.voicePath = voicePath;
	}
	public List<Map<String, String>> getClient() {
		return client;
	}
	public void setClient(List<Map<String, String>> client) {
		this.client = client;
	}
	public List<Map<String, String>> getService() {
		return service;
	}
	public void setService(List<Map<String, String>> service) {
		this.service = service;
	}
	@Override
	public String toString() {
		return "CallResultInfo [voicePath=" + voicePath + ", client=" + client + ", service=" + service + "]";
	}
}
