package cn.hf.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;

import cn.hf.manage.pojo.AutoVoice;
import cn.hf.manage.pojo.EasyUIResult;
import cn.hf.manage.service.AutoVoiceService;

/**
 * 说明：语音文件控制层
 * @author XiJie
 * @version 1.0
 * @date 2017年10月15日
 */
@Controller
@RequestMapping("/autoVoice")
public class AutoVoiceController {

	// 属性注入语音文件业务层对象
	@Autowired
	private AutoVoiceService autoVoiceService;
		
		
	/**
	 * 说明：保存语音文件
	 * @param autoVoice
	 * @return
	 * @author XiJie
	 * @time：2017年10月15日 下午2:48:43
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> saveAutoVoice(AutoVoice autoVoice){
		try {
			// 调用业务层保存的方法  返回boolean值    true：保存成功    false：保存失败
			boolean flag = autoVoiceService.saveAutoVoice(autoVoice);
			// 对其进行判断
			if (flag) {
				// 如果为true  返回201  创建成功
				return ResponseEntity.status(HttpStatus.CREATED).build();
			}
			// 400
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			e.printStackTrace();
			// 如果为false  返回 500  表示失败
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * 说明：根据id进行修改
	 * @param keyWords
	 * @return
	 * @author XiJie
	 * @time：2017年10月15日 下午3:02:20
	 */
	@RequestMapping(method=RequestMethod.PUT)
	public ResponseEntity<Void> updateText(AutoVoice autoVoice){
		try {
			// 调用业务层保存的方法  返回boolean值    true：保存成功    false：保存失败
			boolean flag = autoVoiceService.updateAutoVoice(autoVoice);
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
	 * 说明：根据id进行批量删除
	 * @param ids
	 * @return
	 * @author XiJie
	 * @time：2017年10月15日 下午3:14:16
	 */
	@RequestMapping(method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteAutoVoice(@RequestParam("ids")String ids){
		try {
			// 调用业务层删除的方法  返回boolean值    true：删除成功    false：删除失败
			autoVoiceService.deleteAutoVoiceByIds(ids);
			// 如果为true  返回201  删除成功
			return ResponseEntity.status(HttpStatus.OK).build();
		} catch (Exception e) {
			e.printStackTrace();
			// 如果为false  返回 500  表示失败
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * 说明：根据条件进行分页查询
	 * @param autoVoice
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author XiJie
	 * @time：2017年10月15日 下午2:51:33
	 */
	@RequestMapping(value="/query",method=RequestMethod.POST)
	public ResponseEntity<EasyUIResult> queryAutoVoice(AutoVoice autoVoice,
			@RequestParam(value="page",defaultValue="1")Integer pageNum,
			@RequestParam(value="rows",defaultValue="10")Integer pageSize){
		try {
			// 调用业务层根据条件查询文本的方法
			PageInfo<AutoVoice> pageInfo = autoVoiceService.queryAutoVoiceByCondition(autoVoice, pageNum, pageSize);
			// 对其进行判断
			if (pageInfo != null) {
				// 如果为true  返回200 和数据
				EasyUIResult easyUIResult = new EasyUIResult(pageInfo.getTotal(), pageInfo.getList());
				return ResponseEntity.status(HttpStatus.OK).body(easyUIResult);
			}
			// 400
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		} catch (Exception e) {
			e.printStackTrace();
			// 返回500
			return ResponseEntity.status(HttpStatus.OK).body(null);
		}
	}
	
	
}
