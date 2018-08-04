package cn.hf.manage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.abel533.mapper.Mapper;

import cn.hf.manage.form.CallResultForm;
import cn.hf.manage.pojo.CallResult;

/**
 * CreateUser： YeShengGuang
 * CreateTime：2017年10月23日
 * FileExplain:呼叫结果功能数据接口层
 */
public interface CallResultMapper extends Mapper<CallResult>{

	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：条件查询呼叫结果
	 * @param call
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<CallResult> queryCallResult(CallResultForm call);
	
	boolean updateCallResultByTaskId(@Param(value = "taskId") String taskId, @Param(value = "callResult") CallResult callResult);
	
	boolean updateCallResultByCallId(@Param(value = "callId") String callId, @Param(value = "callResult") CallResult callResult);
	
}
