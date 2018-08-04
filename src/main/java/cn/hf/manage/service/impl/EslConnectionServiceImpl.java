package cn.hf.manage.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.freeswitch.esl.client.inbound.Client;
import org.freeswitch.esl.client.inbound.InboundConnectionFailure;
import org.springframework.beans.factory.InitializingBean;

import cn.hf.manage.pojo.Freeswitch;
import cn.hf.manage.pojo.HttpResult;
import cn.hf.manage.service.EslConnectionService;
import cn.hf.manage.util.OutBoundInstance;

public class EslConnectionServiceImpl implements EslConnectionService, InitializingBean {

	//esl连接超时时间1minute
	private static final int TIME_OUT = 1000 * 60;
 
	private Logger log = Logger.getLogger(EslConnectionServiceImpl.class);
	
	private List<Client> eslClientList = new ArrayList<>();
	
	private List<Freeswitch> freeswitchList= new ArrayList<>();

	public void setFreeswitchList(List<Freeswitch> freeswitchList) {
		this.freeswitchList = freeswitchList;
	}
	
	@Override
	public Client getClient() {
		return eslClientList.get(0);
	}


	@Override
	public HttpResult startCallTask(String serviceId, String taskId, String phoneNumber, String uuid) {
		Client fsClient = getEslClient();
		if (fsClient == null) {
			log.info("----->connect freeswitch failed!");
			log.info("----->taskId:" + taskId + ",call failed!");
			return null;
		}
		//String sendCommand = "sensecall.lua" + " '" + serviceId + "' '" + taskId + "' '" + phoneNumber + "'";
		//String responseResult = fsClient.sendAsyncApiCommand("luarun", sendCommand);
		String param = "{" + "origination_uuid=" + uuid +"}";
		//originate sofia/gateway/bjfxy/13122868630 &playback(A12).wav
		String sendCommand = "";
		String responseResult = fsClient.sendAsyncApiCommand("originate", param + "sofia/gateway/bjfxy/" + phoneNumber + " &playback(A12).wav");
		HttpResult httpResult = null;
		if (responseResult != null) {
			log.info("----->taskId:" + taskId + ",sendCommand return result ：" + responseResult);
			OutBoundInstance.initOutBound(uuid);
			OutBoundInstance.putOutBoundKey(uuid, "phone", phoneNumber);
			OutBoundInstance.putOutBoundKey(uuid, "taskId", taskId);
			OutBoundInstance.putOutBoundKey(uuid, "serviceId", serviceId);
			OutBoundInstance.putOutBoundKey(uuid, "startCallTime", new Date());
			httpResult = new HttpResult(200, "");
		} else {
			log.info("----->taskId:" + taskId + ",sendCommand :" + sendCommand + ",failed!");
			httpResult = new HttpResult(500, "");
		}
		return httpResult;
	}
	
	public Client getEslClient() {
		return eslClientList.get(0);
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		for (Freeswitch fs : freeswitchList) {
			try {
				Client client = new Client();
				client.connect(fs.getIp(), fs.getPort(), fs.getPwd(), TIME_OUT);
				client.addEventListener(new EslEventHandlerSubscriber());
		        //client.setEventSubscriptions("plain", "all");
				client.setEventSubscriptions("plain", "CHANNEL_EXECUTE DTMF CHANNEL_EXECUTE_COMPLETE BACKGROUND_JOB CHANNEL_PARK CHANNEL_HANGUP CHANNEL_ANSWER DETECTED_SPEECH CUSTOM ASR ");
				eslClientList.add(client);
				log.info("----->connect freeswitch " + fs.getIp() + " success!!!");
				break;
			} catch (InboundConnectionFailure inboundConnectionFailure) {
				log.error("----->connect freeswitch " + fs.getIp() + " failed");
				inboundConnectionFailure.printStackTrace();
			}
		}
	}
}
