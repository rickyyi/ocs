package cn.hf.manage.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.hf.manage.pojo.EasyUIResult;
import cn.hf.manage.pojo.Text;
import cn.hf.manage.service.TextService;

import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/text")
public class TextController {
	
	@Autowired
	private TextService textService;
	
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public ResponseEntity<EasyUIResult> queryTestPage(Text text,
			@RequestParam(value="page",defaultValue="1") Integer pageNum,
			@RequestParam(value="rows",defaultValue="10") Integer pageSize){
		try {
			// 调用业务层根据条件查询文本的方法
			PageInfo<Text> pageInfo = textService.queryTextByCondition(text, pageNum, pageSize);
			// 对其进行判断
			// 如果为true  返回200 和数据
			EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
			return ResponseEntity.status(HttpStatus.OK).body(easyUIResult);
		} catch (Exception e) {
			e.printStackTrace();
			// 返回500
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public ResponseEntity<Void> updateText(Text text){
		boolean flag = false;
		try {
			if (text.getId() != null) {
				// 调用业务层保存的方法  返回boolean值    true：保存成功    false：保存失败
				flag = textService.updateText(text);
			}
			// 对其进行判断
			if (flag) {
				// 如果为true  返回200  创建成功
				return ResponseEntity.status(HttpStatus.OK).build();
			}
			// 400
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		} catch (Exception e) {
			e.printStackTrace();
			// 如果为false  返回 500  表示失败
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * CreateUser：YeShengGuang
	 * FileExplain：刷新语音文件
	 * @param callid
	 */
	@RequestMapping(value="/reload/{callid}",method=RequestMethod.GET)
	public ResponseEntity<Void> reloadVoiceFile(@PathVariable(value="callid") String callid){
		ResponseEntity<Void> ret = null;
		try {
			Runtime.getRuntime().exec("scp -P 1122 FS:/usr/local/freeswitch/recordings/"+callid+".wav"+" /downFile/voice/client/");
			ret = ResponseEntity.status(HttpStatus.OK).build();
		} catch (IOException e) {
			e.printStackTrace();
			ret =  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		return ret;
	}
}
