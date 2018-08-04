package cn.hf.manage.job;

/**
 *
 * @author 845477519@qq.com
 * @date 2017/11/10 0010
 */
public class ScheduleJob {
    private String groupDefineId;
    private String detailName;
    private String detailDescription;
    private String cronExpression;
    private String status;
    private String className;
    private String channel;
    private String queueType;
    
    
    
    
	public ScheduleJob(String groupDefineId, String detailName, String cronExpression, String className, String status) {
		super();
		this.groupDefineId = groupDefineId;
		this.detailName = detailName;
		this.cronExpression = cronExpression;
		this.className = className;
		this.status = status;
	}
	public String getGroupDefineId() {
		return groupDefineId;
	}
	public void setGroupDefineId(String groupDefineId) {
		this.groupDefineId = groupDefineId;
	}
	public String getDetailName() {
		return detailName;
	}
	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}
	public String getDetailDescription() {
		return detailDescription;
	}
	public void setDetailDescription(String detailDescription) {
		this.detailDescription = detailDescription;
	}
	public String getCronExpression() {
		return cronExpression;
	}
	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getQueueType() {
		return queueType;
	}
	public void setQueueType(String queueType) {
		this.queueType = queueType;
	}
    
    
}
