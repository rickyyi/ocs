package cn.hf.manage.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hf.manage.mapper.CallResultMapper;
import cn.hf.manage.mapper.CallTaskMapper;
import cn.hf.manage.pojo.CallResult;
import cn.hf.manage.pojo.CallTask;
import cn.hf.manage.pojo.CallTask2;
import cn.hf.manage.service.CallTaskService;
import cn.hf.manage.util.SubStriUtil;

@Service("callTaskService")
public class CallTaskServiceImpl implements CallTaskService{
	
	// 属性注入呼叫任务数据层对象
	@Autowired
	private CallTaskMapper callTaskMapper;
	
	// 属性注入呼叫结果数据层对象
	@Autowired
	private CallResultMapper callResultMapper;

	/**
	 * 说明：保存呼叫任务
	 * @param callTask
	 * @return
	 * @author XiJie
	 * @time：2017年10月23日 下午4:25:48
	 */
	@Override
	public boolean saveCallTask(CallTask callTask) {
		// 调用数据层保存
		boolean flag = callTaskMapper.insert(callTask) == 1;
		if (flag) {
			// 创建呼叫结果的对象
			CallResult callResult = new CallResult();
			// 进行CallResult数据的封装
			callResult.setTaskId(callTask.getId());
			callResult.setTaskName(callTask.getCallTaskName());
			callResult.setTaskStatus(callTask.getPreState());
			callResult.setCallNumManage(callTask.getCallNumManage());
			String serviceId = SubStriUtil.subString(callTask.getCallServiceName());
			callResult.setServiceId(Integer.parseInt(serviceId));
			callResult.setId(callTask.getId());
			callResultMapper.insertSelective(callResult);
		}
		
		return flag;
	}

	/**
	 * 说明：根据条件进行分页查询
	 * @param callTask
	 * @return
	 * @author XiJie
	 * @time：2017年10月23日 下午5:13:30
	 */
	@Override
	public PageInfo<CallTask> queryCallTaskByCondition(CallTask2 callTask2, Integer pageNum, Integer pageSize) {
		CallTask callTask = new CallTask();
		// 1. 对外呼任务名称进行非空判断
		if (StringUtils.isNotEmpty(callTask2.getCallTaskName())) {
			callTask.setCallTaskName(callTask2.getCallTaskName());
		}
		
		// 2. 对外呼业务名称进行非空判断
		if (StringUtils.isNotEmpty(callTask2.getCallServiceName())) {
			callTask.setCallServiceName(callTask2.getCallServiceName());
		}
		// 3. 对外呼号码管理进行非空判断
		if (StringUtils.isNotEmpty(callTask2.getCallNumManage())) {
			callTask.setCallNumManage(callTask2.getCallNumManage());
		}
		
		// 4. 对计划开始时间进行非空判断
		if (callTask2.getPlanStartTime() != null) {
			callTask.setPlanStartTime(callTask2.getPlanStartTime());
		}
		
		// 5. 对当前任务状态进行非空判断
		if (StringUtils.isNotEmpty(callTask2.getPreState())) {
			callTask.setPreState(callTask2.getPreState());
		}
		
		// 6. 对创建时间进行非空判断
		if (callTask2.getCreateTime() != null) {
			callTask.setCreateTime(callTask2.getCreateTime());
		}
		
		// 开启分页助手
		PageHelper.startPage(pageNum, pageSize);
		// 调用数据层进行查询
		List<CallTask> list = callTaskMapper.select(callTask);
		// 获取分页
		PageInfo<CallTask> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}
	
	
	
	/**
	 * 说明：根据条件进行分页查询
	 * @param callTask
	 * @param pageNum
	 * @param pageSize
	 * @param startCreateTime
	 * @param endCreateTime
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 下午5:22:44
	 */
	@Override
	public PageInfo<CallTask> queryCallTaskByCondition2(CallTask2 callTask2, Integer pageNum, Integer pageSize) {
		// 创建实体类对象
		CallTask callTask = new CallTask();
		
		// 开启分页助手
		PageHelper.startPage(pageNum, pageSize);
		
		// 创建Example对象
		Example example = new Example(CallTask.class);
		// 创建查询对象
		Criteria criteria = example.createCriteria();
		
		// 添加查询条件
		// 1. 对外呼任务名称进行非空判断
		if (StringUtils.isNotEmpty(callTask2.getCallTaskName())) {
			callTask.setCallTaskName(callTask2.getCallTaskName());
			criteria.andEqualTo("callTaskName", callTask2.getCallTaskName());
		}
		
		// 2. 对外呼业务名称进行非空判断
		if (StringUtils.isNotEmpty(callTask2.getCallServiceName())) {
			callTask.setCallServiceName(callTask2.getCallServiceName());
			criteria.andEqualTo("callServiceName", callTask2.getCallServiceName());
		}
		// 3. 对外呼号码管理进行非空判断
		if (StringUtils.isNotEmpty(callTask2.getCallNumManage())) {
			callTask.setCallNumManage(callTask2.getCallNumManage());
			criteria.andEqualTo("callNumManage", callTask2.getCallNumManage());
		}
		
		// 4. 对计划开始时间进行非空判断
		if (callTask2.getPlanStartTime() != null) {
			callTask.setPlanStartTime(callTask2.getPlanStartTime());
			criteria.andEqualTo("planStartTime", callTask2.getPlanStartTime());
		}
		
		// 5. 对当前任务状态进行非空判断
		if (StringUtils.isNotEmpty(callTask2.getPreState())) {
			callTask.setPreState(callTask2.getPreState());
			criteria.andEqualTo("preState", callTask2.getPreState());
		}
		
		// 6. 对创建时间进行非空判断
		if (callTask2.getCreateTime() != null) {
			callTask.setCreateTime(callTask2.getCreateTime());
			criteria.andEqualTo("createTime", callTask2.getCreateTime());
		}
		// 7. 对开始创建时间进行非空判断
		if (callTask2.getStartCreateTime() != null) {
			criteria.andGreaterThanOrEqualTo("createTime", callTask2.getStartCreateTime());
		}
		// 8. 对结束创建时间进行非空判断
		if (callTask2.getEndCreateTime() != null) {
			criteria.andLessThanOrEqualTo("createTime", callTask2.getEndCreateTime());
		}
		// 执行查询
		List<CallTask> list = callTaskMapper.selectByExample(example);
		PageInfo<CallTask> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 说明：根据id来查询对象
	 * @param id
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 上午10:00:44
	 */
	@Override
	public CallTask queryCallTaskById(Integer id) {
		CallTask callTask = callTaskMapper.selectByPrimaryKey(id);
		return callTask;
	}

	/**
	 * 说明：修改实体类
	 * @param callTask
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 上午10:42:36
	 */
	@Override
	public boolean updateCallTask(CallTask callTask) {
		boolean flag = callTaskMapper.updateByPrimaryKeySelective(callTask) == 1;
		return flag;
	}

	/**
	 * 说明：根据id删除对应的
	 * @param id
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 上午11:26:12
	 */
	@Override
	public boolean deleteCallTaskById(Integer id) {
		boolean flag = callTaskMapper.deleteByPrimaryKey(id) == 1;
		return flag;
	}

	/**
	 * 说明：根据id批量删除对应的对象
	 * @param ids
	 * @return
	 * @author XiJie
	 * @time：2017年10月24日 下午2:46:18
	 */
	@Override
	public boolean deleteCallTaskByIds(String ids) {
		boolean flag = false;
		// 对字符串进行非空判断
		if (StringUtils.isNoneEmpty(ids)) {
			// 对字符串进行分割
			String[] idArr = ids.split(",");
			// 创建一个空的集合对象
			List<Object> list = new ArrayList<>();
			// 将数据添加到集合中
			for (String id : idArr) {
				list.add(Integer.parseInt(id));
			}
			// 创建Example对象
			Example example = new Example(CallTask.class);
			// 通过Example来创建一个查询对象
			Criteria criteria = example.createCriteria();
			// 添加查询条件
			criteria.andIn("id", list);
			// 进行批量删除
			flag = callTaskMapper.deleteByExample(example) > 0;
		}
		return flag;
	}

	/**
	 * 说明：保存批量数据
	 * @param callTaskName
	 * @param list
	 * @author XiJie
	 * @time：2017年10月25日 下午3:50:35
	 */
	@Override
	public boolean saveBatchImportData(CallTask callTask, List<String> list) {
		boolean flag = false;
		// 对集合进行非空判断
		if (!list.isEmpty()) {
			// 如果不为空  就进行遍历集合   得到每一个号码
			for (String number : list) {
				// 对每一个号码进行批量保存
				callTask.setCallNumManage(number);
				callTask.setCreateTime(new Timestamp(System.currentTimeMillis()));
				callTask.setId(null);
				// 进行数据库的保存
				int taskId = callTaskMapper.insertSelective(callTask);
				
				// 创建呼叫结果的对象
				CallResult callResult = new CallResult();
				// 进行数据的封装
				callResult.setTaskId(callTask.getId());
				callResult.setTaskName(callTask.getCallTaskName());
				callResult.setTaskStatus(callTask.getPreState());
				callResult.setCallNumManage(callTask.getCallNumManage());
				String serviceId = SubStriUtil.subString(callTask.getCallServiceName());
				callResult.setServiceId(Integer.parseInt(serviceId));
				callResult.setId(callTask.getId());
				// 进行数据库保存
				callResultMapper.insertSelective(callResult);
			}
			flag = true;
		}
		return flag;
	}

	/**
	 * 说明：
	 * @param preState
	 * @return
	 * @author XiJie
	 * @time：2017年10月26日 下午3:40:29
	 */
	@Override
	public List<CallTask> queryCallTaskByPreState(String preState, Timestamp startTime, Timestamp endTime) {
		// 创建Example对象
		Example example = new Example(CallTask.class);
		// 通过Example对象来获取查询对象
		Criteria criteria = example.createCriteria();
		// 添加查询条件
		// 1. 添加当前状态查询条件
		criteria.andEqualTo("preState", preState);
		// 2. 添加在开始计划时间在开始和结束时间之间的所有数据
		// 大于开始 时间
		criteria.andGreaterThanOrEqualTo("planStartTime", startTime);
		// 小于结束时间
		criteria.andLessThanOrEqualTo("planStartTime", endTime);
		// 执行查询
		List<CallTask> list = callTaskMapper.selectByExample(example);
		if (list.isEmpty()) {
			return null;
		}
		return list;
	}

	@Override
	public boolean pauseCallTaskByIds(String ids) {
		boolean flag = false;
		// 对字符串进行非空判断
		if (StringUtils.isNoneEmpty(ids)) {
			// 对字符串进行分割
			String[] idArr = ids.split(",");
			// 创建一个空的集合对象
			List<Integer> list = new ArrayList<>();
			// 将数据添加到集合中
			for (String id : idArr) {
				list.add(Integer.parseInt(id));
			}
			CallTask calltask = new CallTask();
			calltask.setPreState("计划暂停");
			// 进行批量删除
			flag = callTaskMapper.batchUpdate(calltask, list) > 0;
		}
		return flag;
	}

	
	
}
