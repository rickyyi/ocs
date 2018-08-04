package cn.hf.manage.controller.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import cn.hf.manage.job.ScheduleJob;
import cn.hf.manage.service.impl.JobOperate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/11/10 0010.
 */
public class MainTask{

	@Resource
    JobOperate jobOperate;
	
	@Value(value="${CORN}")
	private String cron;
	
    public void execute() throws Exception {
        System.out.println("主任务->" + Thread.currentThread().getName() + ":" + org.apache.commons.lang3.time.DateFormatUtils.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
        List<ScheduleJob> jobList = new ArrayList<>();
        ScheduleJob schedule = new ScheduleJob("1", "外呼任务", "cn.hf.manage.controller.api.DynamicTaskScanApi", cron, "ONUSE");
        jobList.add(schedule);
        for (ScheduleJob scheduleJob : jobList) {
            jobOperate.flushJob(scheduleJob);
        }
    }
}
