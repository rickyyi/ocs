package cn.hf.manage.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hf.manage.service.CallResultService;

@Controller
@RequestMapping("/callResultApi")
public class CallResultControllerApi {

	// 呼叫结果业务层接口对象
	@Autowired
	private CallResultService service;

	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：更新呼叫结果
	 * @param call
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> updateCallResult(@RequestBody String jsonCall){
		ResponseEntity<Map<String, Object>> ret = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			//执行保存
			if (service.updateCallResult(jsonCall)){
				resultMap.put("result", true);
				ret = ResponseEntity.status(HttpStatus.OK).body(resultMap);
			} else {
				resultMap.put("result", false);
				ret = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(resultMap);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("result", false);
			ret = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
		}
		return ret;
	}

}
