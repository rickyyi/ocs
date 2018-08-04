package cn.hf.manage.service;

import java.sql.Timestamp;
import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.hf.manage.pojo.CallTask;
import cn.hf.manage.pojo.CallTask2;

/**
 * 说明：呼叫任务业务层接口
 * @author XiJie
 * @version 1.0
 * @date 2017年10月23日
 */
public interface CallTaskService {

	/**
	 * 说明：保存呼叫任务
	 * @param callTask
	 * @return
	 * @author XiJie
	 * @time：2017年10月23日 下午4:25:30
	 */
	public boolean saveCallTask(CallTask callTask);

	/**
	 * 说明：根据条件进行分页查询
	 * @param callTask2
	 * @return
	 * @author XiJie
	 * @param pageSize 
	 * @param pageNum 
	 * @time：2017年10月23日 下午5:13:07
	 */
	public PageInfo<CallTask> queryCallTaskByCondition(CallTask2 callTask2, Integer pageNum, Integer pageSize);

	/**
	 * 说明：
	 * @param callTask
	 * @param pageNum
	 * @param pageSize
	 * @param startCreateTime
	 * @param endCreateTime
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 下午5:21:47
	 */
	public PageInfo<CallTask> queryCallTaskByCondition2(CallTask2 callTask2, Integer pageNum, Integer pageSize);
	
	/**
	 * 说明：根据id来查询对象
	 * @param id
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 上午10:00:17
	 */
	public CallTask queryCallTaskById(Integer id);

	/**
	 * 说明：修改实体类
	 * @param callTask
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 上午10:42:19
	 */
	public boolean updateCallTask(CallTask callTask);

	/**
	 * 说明：根据id删除对应的
	 * @param id
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 上午11:25:55
	 */
	public boolean deleteCallTaskById(Integer id);

	/**
	 * 说明：根据id批量删除对应的对象
	 * @param ids
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 下午2:45:56
	 */
	public boolean deleteCallTaskByIds(String ids);

	/**
	 * 说明：保存批量数据
	 * @param callTask
	 * @param list
	 * @author XiJie
	 * @return 
	 * @time：2017年10月25日 下午3:50:09
	 */
	public boolean saveBatchImportData(CallTask callTask, List<String> list);

	/**
	 * 说明：根据当前状态查询对象
	 * @param preState
	 * @return
	 * @author XiJie
	 * @param endTime 
	 * @param startTime 
	 * @time：2017年10月26日 下午3:29:33
	 */
	public List<CallTask> queryCallTaskByPreState(String preState, Timestamp startTime, Timestamp endTime);

	
	public boolean pauseCallTaskByIds(String ids);

}
