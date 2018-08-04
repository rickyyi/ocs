package cn.hf.manage.service;

import cn.hf.manage.pojo.HttpResult;

public interface EslConnectionService {
	HttpResult startCallTask(String serviceId, String taskId, String phoneNumber);
}
