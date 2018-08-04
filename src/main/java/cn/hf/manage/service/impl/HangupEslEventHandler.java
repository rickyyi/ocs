package cn.hf.manage.service.impl;

import java.util.Date;

import org.freeswitch.esl.client.transport.event.EslEvent;

import cn.hf.manage.service.EslEventHandler;
import cn.hf.manage.util.OutBoundInstance;

/**
 * Created by cookie on 2018/4/10 0010.
 */
public class HangupEslEventHandler implements EslEventHandler{
    @Override
	public void handle(EslEvent eslEvent) {
        String uuid = eslEvent.getEventHeaders().get("Channel-Call-UUID");
        if (OutBoundInstance.getOutBound(uuid) == null) {
        	return;
        }
        OutBoundInstance.putOutBoundKey(uuid, "startAnswerTime", new Date());
        new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(3000L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				OutBoundInstance.clearOutBound(uuid);
			}
		}).start();
    }
}