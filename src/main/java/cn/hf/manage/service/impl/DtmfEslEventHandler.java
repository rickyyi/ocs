package cn.hf.manage.service.impl;

import org.apache.log4j.Logger;
import org.freeswitch.esl.client.transport.event.EslEvent;

import cn.hf.manage.service.EslEventHandler;
import cn.hf.manage.util.OutBoundInstance;

/**
 * Created by cookie on 2018/4/10 0010.
 */
public class DtmfEslEventHandler implements EslEventHandler {

	private static Logger log = Logger.getLogger(DtmfEslEventHandler.class);
	
    @Override
	public void handle(EslEvent eslEvent) {
    	String uuid = eslEvent.getEventHeaders().get("Channel-Call-UUID");
    	final String dtmf_digit = eslEvent.getEventHeaders().get("DTMF-Digit");
    	log.info("按键事件:" + uuid + ";按了数字键:" + dtmf_digit);
    	if (OutBoundInstance.getOutBound(uuid) == null) {
        	return;
        }
        
    }
}
