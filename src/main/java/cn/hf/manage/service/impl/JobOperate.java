package cn.hf.manage.service.impl;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.stereotype.Component;

import cn.hf.manage.job.ScheduleJob;

import javax.annotation.Resource;

/**
 * Created by Administrator on 2017/11/13 0013.
 */
public class JobOperate {

    @Resource
    Scheduler scheduler;

    public void flushJob(ScheduleJob scheduleJob) throws SchedulerException{
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getDetailName(), scheduleJob.getGroupDefineId());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        if (null == trigger) {
            addJob(scheduleJob);
        } else {
            updateJobCron(scheduleJob);
        }
    }

    public void addJob(ScheduleJob job) throws SchedulerException{
        Class<Job> clazz;
        try {
            clazz = (Class<Job>) Class.forName(job.getClassName());
        } catch (ClassNotFoundException e1) {
            throw new RuntimeException(e1);
        }
        JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(job.getDetailName(), job.getGroupDefineId()).build();
        jobDetail.getJobDataMap().put("scheduleJob", job);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression()).withMisfireHandlingInstructionDoNothing();

        TriggerBuilder<Trigger> builder = TriggerBuilder.newTrigger().withIdentity(job.getDetailName(), job.getGroupDefineId());
        Trigger trigger = "ONCE".equals(job.getStatus()) ? builder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0)).build() : builder.withSchedule(scheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    public void updateJobCron(ScheduleJob scheduleJob) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getDetailName(), scheduleJob.getGroupDefineId());
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression()).withMisfireHandlingInstructionDoNothing();
        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, trigger);
    }

    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException{
        JobKey jobKey = JobKey.jobKey(scheduleJob.getDetailName(), scheduleJob.getGroupDefineId());
        scheduler.deleteJob(jobKey);
    }

    public void pauseJob(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getDetailName(), scheduleJob.getGroupDefineId());
        scheduler.pauseJob(jobKey);
    }

    public void runJobNow(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getDetailName(), scheduleJob.getGroupDefineId());
        scheduler.triggerJob(jobKey);
    }

    public void resumeJob(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getDetailName(), scheduleJob.getGroupDefineId());
        scheduler.resumeJob(jobKey);
    }

    public void shutdownJobs() throws SchedulerException {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }
}
