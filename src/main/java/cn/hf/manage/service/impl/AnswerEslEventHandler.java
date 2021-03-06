package cn.hf.manage.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;
import org.freeswitch.esl.client.transport.event.EslEvent;

import cn.hf.manage.mapper.CallResultMapper;
import cn.hf.manage.pojo.CallResult;
import cn.hf.manage.service.EslEventHandler;
import cn.hf.manage.util.ContextUtils;
import cn.hf.manage.util.OutBoundInstance;

/**
 * Created by cookie on 2018/4/10 0010.
 */
public class AnswerEslEventHandler implements EslEventHandler {

	private static Logger log = Logger.getLogger(AnswerEslEventHandler.class);
	
    @Override
	public void handle(EslEvent eslEvent) {
    	String uuid = eslEvent.getEventHeaders().get("Channel-Call-UUID");
    	log.info("接听事件:" + uuid);
    	if (OutBoundInstance.getOutBound(uuid) == null) {
        	return;
        }
    	
    	CallResultMapper mapper = (CallResultMapper)ContextUtils.getBean(CallResultMapper.class);
    	
    	CallResult callResultFind = new CallResult();
		callResultFind.setCallId(uuid);
		CallResult callResult = mapper.selectOne(callResultFind);
		
		callResult.setCallResult("成功");
		callResult.setAnswertime(new Timestamp(System.currentTimeMillis()));									
		mapper.updateByPrimaryKeySelective(callResult);
    	
        OutBoundInstance.putOutBoundKey(uuid, "HangupTime", new Date());
    }
}
