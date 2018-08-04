package cn.hf.manage.service;

import org.freeswitch.esl.client.inbound.Client;

import cn.hf.manage.pojo.HttpResult;

public interface EslConnectionService {
	
	public Client getClient();
	
	HttpResult startCallTask(String serviceId, String taskId, String phoneNumber, String callId);
}
