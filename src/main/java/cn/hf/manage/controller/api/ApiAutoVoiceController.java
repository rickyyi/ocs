package cn.hf.manage.controller.api;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.hf.manage.pojo.AutoVoice;
import cn.hf.manage.pojo.ReturnText;
import cn.hf.manage.pojo.Text;
import cn.hf.manage.service.AutoVoiceService;
import cn.hf.manage.service.TextService;

/**
 * 说明：根据文本条件查询对应的语音文件名 
 * 	         访问路径：http://192.168.30.29:8080/KeyWords/rest/api/autoVoice
 *     接受json格式的数据：{"sentence":"我知道了","callid":"111"}
 *     返回接送格式的数据：{name: "end.wav"}
 * 
 * @author XiJie
 * @version 1.0
 * @date 2017年10月13日
 */
@Controller
@RequestMapping("/api")
public class ApiAutoVoiceController {
	
	
	// 属性注入查询关键字接口对象
	@Autowired
	private ApiKeyWordsController apiKeyWordsController;

	// 属性注入语音文件业务层对象
	@Autowired
	private AutoVoiceService autoVoiceService;
	
	// 注入文本业务层对象
	@Autowired
	private TextService textService;
	
	/**
	 * 说明：根据条件进行分页查询
	 * @param acceptMap：接受的参数
	 * @param pageNum 当前页,默认值是1
	 * @param pageSize 每页最大记录数,默认值10
	 * @return
	 * @author XiJie
	 * @time：2017年10月16日 下午3:07:51
	 */
	@RequestMapping(value="/autoVoice", method=RequestMethod.POST)
	public ResponseEntity<Map<String, Object>> queryText(@RequestBody Map<String, String> acceptMap) {
		// 创建一个接受返回结果的map对象
		Map<String, Object> resultMap = new HashMap<>();
		try {
			// 1. 首先根据条件查询对应的主标签和副标签 
//			ResponseEntity<ReturnText> queryEntity = apiKeyWordsController.queryText(acceptMap.get("sentence"));
			// 获取集合
			ReturnText returnText = apiKeyWordsController.queryText(acceptMap.get("sentence")).getBody();
			
			// 2. 对返回的集合进行非空判断
			// 2.1  如果为空 
			if (StringUtils.isEmpty(returnText.getMasterLabel()) && StringUtils.isEmpty(returnText.getAssistantLabel())) {
				if (returnText.getLabels() == null || returnText.getLabels().isEmpty()) {
					// 2.2   如果为空  返回  (没有查到对应的主副标签) 
					// 查询text表  查询出现的次数
					int count = textService.selectTextByCallIdAndMasterLabelAndAssistantLabel(acceptMap.get("callid"));
					// 2.2.1  如果是第一次  放nolabel.wav语音
					if (count == 0) {
						resultMap.put("name", "nolabel.wav");
					}
					// 2.2.2  如果是第一次  放nolabel.wav语音
					if (count == 1) {
						resultMap.put("name", "sellpoint1.wav");
					}
					// 2.2.3  如果是第2次  放nolabel.wav语音
					if (count == 2) {
						resultMap.put("name", "sellpoint2.wav");
					}
					// 2.2.4  如果是第3次  放nolabel.wav语音
					if (count == 3) { 
						resultMap.put("name", "sellpoint3.wav");
					}
					// 2.2.5  如果是第4次  放nolabel.wav语音
					if (count >= 4) {
						resultMap.put("name", "end_yes.wav");
					}
					
					// 2.3   档查到没有对应的主副标签时  对传进的文本进行保存
					textService.saveText(acceptMap.get("sentence"), acceptMap.get("callid"), resultMap.get("name").toString());
					
					// 返回数据
					return ResponseEntity.status(HttpStatus.OK).body(resultMap);
				}
			}
			
			// 3. 如果不为空    (就查到对应主副标签) 根据主副标签来查询对应的语音文件名
			// 3.1   如果只有一条数据
			if (StringUtils.isNotEmpty(returnText.getMasterLabel()) && StringUtils.isNotEmpty(returnText.getAssistantLabel())) {
				// 3.1.1   获取主副标签，到数据库进行查询
				String masterLabel = returnText.getMasterLabel();
				String assistantLabel = returnText.getAssistantLabel();
				// 3.1.2  根据主标签和副标签来到后台查询对应的语音文件名，和卖点
				AutoVoice autoVoice = autoVoiceService.queryAutoVoiceByTag2(masterLabel, assistantLabel);
				// 对返回的结果进行判断，是否为空
				if (autoVoice != null) {
					// 对返回的语音文件名进行判断
					if (StringUtils.isEmpty(autoVoice.getName())) {
						// 如果为空  返回  没有对应的语音文件名
						resultMap.put("name", "novoice.wav");
						// 档查到没有对应的语音文件名时  对传进的文本进行保存
						saveTextSelective(acceptMap, autoVoice);
					} else {
						// 对主副标签进行判断  是否是拒绝标签，如果是并查询出现几次，根据次数，做相应的判断
						if (masterLabel.equals("拒绝")) {
							// 查询text表  查询出现的次数
							int count2 = textService.selectTextByCallIdAndMasterLabelAndAssistantLabel2(acceptMap.get("callid"), masterLabel);
							// 如果是第1次  播放对应的语音
							if (count2 == 0) {
								resultMap.put("name", autoVoice.getName());
							}
							// 如果是第2次  播放拒绝卖点1
							if (count2 == 1) {
								resultMap.put("name", "refuse_sellpoint1.wav");
							}
							// 如果是第3次  播放拒绝卖点2
							if (count2 == 2) {
								resultMap.put("name", "refuse_sellpoint2.wav");
							}
							// 如果是第4次  播放拒绝卖点3
							if (count2 == 3) {
								resultMap.put("name", "refuse_sellpoint3.wav");
							}
							// 如果是第5次  播放结束语音
							if (count2 >= 4) {
								resultMap.put("name", "end_no.wav");
							}
						} else {
							// 如果不是拒绝
							resultMap.put("name", autoVoice.getName());
						}
						// 对text进行保存
						autoVoice.setName(resultMap.get("name").toString());
						saveTextSelective(acceptMap, autoVoice);
					}
					
					// 对返回的卖点进行判断
					/*if (StringUtils.isEmpty(autoVoice.getSellPoint())) {
					// 如果为空  返回  没有对应的语音文件名
					resultMap.put("sellPoint", "没有对应的卖点");
					// 档查到没有对应的语音文件名时  对传进的文本进行保存
					textService.saveText(acceptMap.get("sentence"), acceptMap.get("callid"), resultMap.get("sellPoint").toString());
				} else {
					// 如果不为空   对卖点进行封装  并返回
					resultMap.put("sellPoint", autoVoice.getSellPoint());
				}*/
					
					// 返回结果
					return ResponseEntity.status(HttpStatus.OK).body(resultMap);
				} else {
					// 如果为空   返回没有对应的语音文件名
					resultMap.put("name", "novoice.wav");
					// 档查到没有对应的语音文件名时  对传进的文本进行保存
					textService.saveText(acceptMap.get("sentence"), acceptMap.get("callid"), resultMap.get("name").toString(), masterLabel, assistantLabel);
					return ResponseEntity.status(HttpStatus.OK).body(resultMap);
				}
			}
			
			// 3.2   如果大于1条数据
			if (returnText.getLabels() != null && !returnText.getLabels().isEmpty()) {
				// 3.2.1   到数据库进行查询，并获取权重最大的那个语音文件名，和卖点
				List<String> list = returnText.getLabels();
				AutoVoice autoVoice = autoVoiceService.queryAutoVoiceByTagList(list);
				
				if (autoVoice != null) {
					// 3.2.2  对返回的语音文件名进行判断
					if (StringUtils.isEmpty(autoVoice.getName())) {
						// 如果为空  返回  没有对应的语音文件名
						resultMap.put("name", "novoice.wav");
						// 档查到没有对应的语音文件名时  对传进的文本进行保存
						saveTextSelective(acceptMap, autoVoice);
					} else {
						// 获取主标签
						String masterLabel = autoVoice.getMasterLabel();
						// 对主副标签进行判断  是否是拒绝标签，如果是并查询出现几次，根据次数，做相应的判断
						if (masterLabel.equals("拒绝")) {
							// 查询text表  查询出现的次数
							int count2 = textService.selectTextByCallIdAndMasterLabelAndAssistantLabel2(acceptMap.get("callid"), masterLabel);
							// 如果是第1次  播放对应的语音
							if (count2 == 0) {
								resultMap.put("name", autoVoice.getName());
							}
							// 如果是第2次  播放卖点1
							if (count2 == 1) {
								resultMap.put("name", "refuse_sellpoint1.wav");
							}
							// 如果是第3次  播放卖点2
							if (count2 == 2) {
								resultMap.put("name", "refuse_sellpoint2.wav");
							}
							// 如果是第4次  播放卖点3
							if (count2 == 3) {
								resultMap.put("name", "refuse_sellpoint3.wav");
							}
							// 如果是第5次  播放结束语音
							if (count2 >= 4) {
								resultMap.put("name", "end_no.wav");
							}
						} else {
							// 如果不是拒绝
							resultMap.put("name", autoVoice.getName());
						}
						// 对text进行保存
						autoVoice.setName(resultMap.get("name").toString());
						saveTextSelective(acceptMap, autoVoice);
					}
					
					// 3.2.3   对返回的卖点进行判断
					/*if (StringUtils.isEmpty(autoVoice.getSellPoint())) {
						// 如果为空  返回  没有对应的语音文件名
						resultMap.put("sellPoint", "没有对应的卖点");
						// 档查到没有对应的语音文件名时  对传进的文本进行保存
						textService.saveText(acceptMap.get("sentence"), acceptMap.get("callid"), resultMap.get("sellPoint").toString());
					} else {
						// 如果不为空   对卖点进行封装  并返回
	  					resultMap.put("sellPoint", autoVoice.getSellPoint());
					}*/
				} else {
					// 如果为空   返回没有对应的语音文件名
					resultMap.put("name", "novoice.wav");
					// 档查到没有对应的语音文件名时  对传进的文本进行保存
					textService.saveText(acceptMap.get("sentence"), acceptMap.get("callid"), resultMap.get("name").toString(), returnText.getLabels());
					return ResponseEntity.status(HttpStatus.OK).body(resultMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 4     返回空map 
			resultMap.put("name", "系统出现异常！！！");
			// 当检查到异常时，进行保存提醒
			textService.saveText(acceptMap.get("sentence"), acceptMap.get("callid"), resultMap.get("name").toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resultMap);
		}
		
		// 3.2.4  返回结果
		return ResponseEntity.status(HttpStatus.OK).body(resultMap);
	}

	private void saveTextSelective(Map<String, String> acceptMap, AutoVoice autoVoice) {
		// 封装数据
		Text text = new Text();
		// 如果name为空
		String name = acceptMap.get("sentence");
		if (StringUtils.isEmpty(name)) {
			name = "sentence 传入的数据方式有误";
		}
		text.setName(name);
		// 对callid进行非空判断
		String callid = acceptMap.get("callid");
		if (StringUtils.isEmpty(callid)) {
			callid = "callid 传入的数据方式有误";
		}
		// 保存callid
		text.setCallid(callid);
		// 保存case
		text.setCause(autoVoice.getName());
		// 保存主副标签
		text.setMasterLabel(autoVoice.getMasterLabel());
		text.setAssistantLabel(autoVoice.getAssistantLabel());
		
		// 创建当前时间
		long time = System.currentTimeMillis();
		Timestamp createTime = new Timestamp(time);
		text.setCreateTime(createTime );
		
		textService.saveText(text);
	}
	
}
