package cn.hf.manage.service.impl;

import org.apache.log4j.Logger;
import org.freeswitch.esl.client.IEslEventListener;
import org.freeswitch.esl.client.transport.event.EslEvent;

/**
 * Created by cookie on 2018/4/10 0010.
 */
public class EslEventHandlerSubscriber implements IEslEventListener {

	private Logger logger = Logger.getLogger(EslEventHandlerSubscriber.class);
	
    @Override
    public void eventReceived(EslEvent eslEvent) {
    	logger.info("eslEvent->eventName:" + eslEvent.getEventName());
        if (eslEvent.getEventName().equals("CHANNEL_ANSWER")) {
            new AnswerEslEventHandler().handle(eslEvent);
        } else if (eslEvent.getEventName().equals("HEARTBEAT")) {

        } else if (eslEvent.getEventName().equals("DTMF")) {
        	new DtmfEslEventHandler().handle(eslEvent);
        } else if (eslEvent.getEventName().equals("CHANNEL_DESTROY")) {

        } else if (eslEvent.getEventName().equals("CHANNEL_HANGUP")) {
        	new HangupEslEventHandler().handle(eslEvent);
        } else if (eslEvent.getEventName().equals("CHANNEL_HANGUP_COMPLETE")) {

        }
    }

    @Override
    public void backgroundJobResultReceived(EslEvent eslEvent) {
        String uuid = eslEvent.getEventHeaders().get("Job-UUID");
    }
}
