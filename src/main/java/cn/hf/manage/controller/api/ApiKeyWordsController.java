package cn.hf.manage.controller.api;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.hf.manage.pojo.AcceptText2;
import cn.hf.manage.pojo.KeyWords;
import cn.hf.manage.pojo.ReturnText;
import cn.hf.manage.service.KeyWordsService;
import cn.hf.manage.service.impl.ProperttiesService;
import cn.hf.manage.util.HttpClientUtil;

/**
 * 说明：根据文本条件查询对应的主副标签接口 
 *     接受的参数：是form表单的形式
 *     返回的数据：以json的格式进行返回  
 * 
 * @author XiJie
 * @version 1.0
 * @date 2017年10月13日
 */
@Controller("apiKeyWordsController")
@RequestMapping("/api")
public class ApiKeyWordsController {

	// 属性注入关键字业务层对象
	@Autowired
	private KeyWordsService keyWordsService;

	// 属性注入属性管理业务层对象
	@Autowired
	private ProperttiesService properttiesService;
	
	
	/**
	 * 说明：根据条件进行分页查询
	 * @param condition
	 * @param pageNum
	 * @param pageSize
	 * @return  ReturnText
	 * @author XiJie
	 * @time：2017年10月13日 下午3:47:20
	 */
	@RequestMapping("/keyWords")
	public ResponseEntity<ReturnText> queryText(@RequestParam("condition") String condition) {
		// 返回的实体类
		ReturnText returnText = new ReturnText();
		// 创建转换json对象
		ObjectMapper objectMapper = new ObjectMapper();
		
		try {
			// 调用业务层根据条件查询文本的方法
			KeyWords keyWords = keyWordsService.queryTextByCondition(condition);
			
			// 对其进行判断
			if (keyWords != null) {
				// 如果不为空    (进入后台进行查询)
				// 封装返回的数据
				returnText.setMasterLabel(keyWords.getMasterLabel());
				returnText.setAssistantLabel(keyWords.getAssistantLabel());
				// 如果为true 返回200 和数据
				return ResponseEntity.status(HttpStatus.OK).body(returnText);
			} else {
				// 如果为空   (就调用远程接口进行查询)
				// 远程的url地址
				String url = properttiesService.REMOTE_PATH3;
				// 进行远程get访问   并获取数据
//				String str = HttpClientUtil.doGet(url + condition);
				// 进行远程post访问   并获取数据
				String str = HttpClientUtil.doPost(condition, url);
				
				// 对返回的值进行非空判断
				if (StringUtils.isEmpty(str)) {
					// 如果没空   返回空的对象
					return ResponseEntity.status(HttpStatus.OK).body(returnText);
				}
				
				// 如果不为空   封装数据  并返回
				// 将json字符串转换成java对象
				AcceptText2 acceptText2 = objectMapper.readValue(str, AcceptText2.class);
				
				// 重新封装returnText对象数据
				returnText.setLabels(acceptText2.getLabels());
				// 返回结果
				return ResponseEntity.status(HttpStatus.OK).body(returnText);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 返回500
			return ResponseEntity.status(HttpStatus.OK).body(returnText);
		}
	}
	
}
