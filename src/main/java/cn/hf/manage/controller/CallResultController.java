package cn.hf.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;

import cn.hf.manage.form.CallResultForm;
import cn.hf.manage.pojo.CallResult;
import cn.hf.manage.pojo.EasyUIResult;
import cn.hf.manage.service.CallResultService;

/**
 * CreateUser： YeShengGuang
 * CreateTime：2017年10月23日
 * FileExplain:呼叫结果页面交互层
 */
@Controller
@RequestMapping("/callResult")
public class CallResultController {
	
	// 呼叫结果业务层接口对象
	@Autowired
	private CallResultService service;
	
	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：查询呼叫结果信息
	 * @return
	 */
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public ResponseEntity<EasyUIResult> queryCallResult(CallResultForm call,@RequestParam(value="page",defaultValue="1") Integer pageNum,@RequestParam(value="rows",defaultValue="10") Integer pageSize,@RequestParam(value="t",required=false) String reqType){
		ResponseEntity<EasyUIResult> ret = null;
		try {
			//根据请求类型来调用不同的查询方法
			PageInfo<CallResult> calls = StringUtils.isEmpty(reqType)?calls = service.queryCallResult(null, pageNum, pageSize):service.queryCallResultWhere(call);
			ret = ResponseEntity.status(HttpStatus.OK).body(new EasyUIResult(calls.getTotal(),calls.getList()));
		} catch (Exception e) {
			e.printStackTrace();
			ret = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		return ret;
	}
	
	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：更新呼叫结果
	 * @param call
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ResponseEntity<Void> updateCallResult(@RequestBody String jsonCall){
		ResponseEntity<Void> ret = null;
		try {
			//执行保存
			if (service.updateCallResult(jsonCall))
				ret = ResponseEntity.status(HttpStatus.OK).build();
			else
				ret = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			e.printStackTrace();
			ret = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ret;
	}
	
	
	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：获取呼叫结果详情信息
	 * @param callid
	 * @return
	 */
	@RequestMapping(value="callinfo",method=RequestMethod.POST)
	public ResponseEntity<String> getCallResult(@RequestParam(value="callid") String callid){
		ResponseEntity<String> ret = null;
		try{
			ret = ResponseEntity.status(HttpStatus.OK).body(service.getCallResult(callid));
		}catch(Exception e){
			e.printStackTrace();
			ret = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
		return ret;
	}
	
	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：保存呼叫查询结果信息
	 * @param call
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> saveCallResult(CallResult call){
		ResponseEntity<Void> ret = null;
		try {
			//执行保存
			if (service.saveCallResult(call))
				ret = ResponseEntity.status(HttpStatus.OK).build();
			else
				ret = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			e.printStackTrace();
			ret = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ret;
	}
	
}
