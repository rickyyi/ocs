package cn.hf.manage.service;

import com.github.pagehelper.PageInfo;

import cn.hf.manage.form.CallResultForm;
import cn.hf.manage.pojo.CallResult;

/**
 * CreateUser： YeShengGuang
 * CreateTime：2017年10月23日
 * FileExplain:呼叫结果功能业务接口层
 */
public interface CallResultService {
	
	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：查询呼叫结果集
	 * @param call		查询条件
	 * @param pageNum	当前页数
	 * @param pageSize	最大页数
	 * @return	呼叫结果集
	 */
	PageInfo<CallResult> queryCallResult(CallResult call,Integer pageNum,Integer pageSize);
	
	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：根据条件查询呼叫结果集
	 * @param call		查询条件
	 * @param pageNum	当前页数
	 * @param pageSize	最大页数
	 * @return	呼叫结果集
	 */
	PageInfo<CallResult> queryCallResultWhere(CallResultForm call);
	
	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：保存呼叫结果数据
	 * @param call	呼叫结果数据对象
	 * @return	true：保存成功、false：失败
	 */
	boolean saveCallResult(CallResult call);
	
	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：获取呼叫结果详细信息
	 * @param callid
	 * @return
	 */
	String getCallResult(String callid);
	
	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：删除呼叫结果信息
	 * @param call
	 * @return
	 */
	boolean delCallResult(CallResult call);
	
	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：更新呼叫结果集
	 * @param json
	 * @return
	 */
	boolean updateCallResult(String jsonStr);
	
	boolean updateCallResultByTaskId(Integer taskId, CallResult callResult);
	
	boolean updateCallResultByCallId(Integer callId, CallResult callResult);
	
}
