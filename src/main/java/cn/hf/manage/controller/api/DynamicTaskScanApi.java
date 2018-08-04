package cn.hf.manage.controller.api;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import cn.hf.manage.pojo.CallTask;
import cn.hf.manage.pojo.HttpResult;
import cn.hf.manage.service.CallTaskService;
import cn.hf.manage.service.EslConnectionService;
import cn.hf.manage.util.HttpClientUtil;
import cn.hf.manage.util.SubStriUtil;

/**
 * 说明：调用远程接口   传入数据
 * @author XiJie
 * @version 1.0
 * @date 2017年10月27日
 */
public class DynamicTaskScanApi implements Job {
	
	@Value(value="${TPS}")
	private String tps;
	
	// 属性注入呼叫业务业务层对象
	@Autowired
	private CallTaskService callTaskService;
	@Autowired
	private EslConnectionService eslConnectionService;

	private Logger logger = Logger.getLogger(DynamicTaskScanApi.class);
	
	public long getTodayStartTime() {  
        Calendar todayStart = Calendar.getInstance();  
        todayStart.set(Calendar.HOUR_OF_DAY, 0);  
        todayStart.set(Calendar.MINUTE, 0);  
        todayStart.set(Calendar.SECOND, 0);  
        todayStart.set(Calendar.MILLISECOND, 0);  
        return todayStart.getTimeInMillis();
    }
	
	public long getTodayEndTime() {  
        Calendar todayEnd = Calendar.getInstance();  
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);  
        todayEnd.set(Calendar.MINUTE, 59);  
        todayEnd.set(Calendar.SECOND, 59);  
        todayEnd.set(Calendar.MILLISECOND, 999);  
        return todayEnd.getTimeInMillis();
    }


	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.info("DynamicTaskScanApi running");
    	long currentTimeMillis = System.currentTimeMillis();
		Timestamp now = new Timestamp(currentTimeMillis);
		// 任务开始及结束时间
		Timestamp startTime = new Timestamp(getTodayStartTime());
		Timestamp endTime = new Timestamp(getTodayEndTime());
		logger.info(startTime);
		logger.info(endTime);
		
		String preState1 = "计划中";
		int i = 0;
		// 获取任务集合
		List<CallTask> list = callTaskService.queryCallTaskByPreState(preState1, startTime, endTime);
		if (list != null && !list.isEmpty()) {
			for (CallTask callTask : list) {
				// 获取每一个对象
				// 1. 获取呼叫业务名称
				String callServiceName = callTask.getCallServiceName();
				// 1.1  对呼叫任务名称进行截取，获取业务id
				String serviceId = SubStriUtil.subString(callServiceName);
				
				// 2. 获取呼叫任务名称id
				Integer taskId = callTask.getId();
				
				// 3. 获取号码
				String phoneNumber = callTask.getCallNumManage();
				
				// 4.进行传输任务
				HttpResult httpResult = eslConnectionService.startCallTask(serviceId, String.valueOf(taskId), phoneNumber);
				if (httpResult != null) {
					if (httpResult.getCode() == 200) {
						callTask.setPreState("进行中_连接正常_" + httpResult.getCode());
						callTaskService.updateCallTask(callTask);
						logger.info("连接正常");
					} else {
						callTask.setPreState("进行中_连接异常");
						callTaskService.updateCallTask(callTask);
						logger.info("连接异常");
					}
				} else {
					callTask.setPreState("进行中_连接超时");   
					callTaskService.updateCallTask(callTask);
					logger.error("连接超时");
				}
				
				// 休眠1秒
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				// 依次增加
				i++;
				// 如果超过并发数，就结束循环
				if (i >= Integer.valueOf(tps)) {
					break;
				}
			}
		}
	}  
}
