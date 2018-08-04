package cn.hf.manage.util;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import cn.hf.manage.service.impl.HangupEslEventHandler;

@Component
public class ContextUtils implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    private static Logger log = Logger.getLogger(HangupEslEventHandler.class);

    public ContextUtils() {
    }

    public static ApplicationContext getApplicationContext() {
        Class var0 = ContextUtils.class;
        synchronized(ContextUtils.class) {
            while(applicationContext == null) {
                try {
                    ContextUtils.class.wait(60000L);
                    if(applicationContext == null) {
                    	log.warn("Have been waiting for ApplicationContext to be set for 1 minute", new Exception());
                    }
                } catch (InterruptedException var3) {
                	log.debug("getApplicationContext, wait interrupted");
                }
            }

            return applicationContext;
        }
    }

    public static Object getBean(Class<?> beanType) {
        return getApplicationContext().getBean(beanType);
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    @Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Class var2 = ContextUtils.class;
        synchronized(ContextUtils.class) {
            ContextUtils.applicationContext = applicationContext;
            ContextUtils.class.notifyAll();
        }
    }
}
