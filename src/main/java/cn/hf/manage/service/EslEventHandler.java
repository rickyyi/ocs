package cn.hf.manage.service;

import org.freeswitch.esl.client.transport.event.EslEvent;

/**
 * Created by Administrator on 2018/4/10 0010.
 */
public interface EslEventHandler {
    void handle(EslEvent eslEvent);
}
