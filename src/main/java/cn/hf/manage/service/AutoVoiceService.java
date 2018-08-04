package cn.hf.manage.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.hf.manage.pojo.AutoVoice;

/**
 * 说明：语音文件业务层接口
 * @author XiJie
 * @version 1.0
 * @date 2017年10月15日
 */
public interface AutoVoiceService {

	/**
	 * 说明：保存语音文件
	 * @param autoVoice
	 * @return boolean
	 * @author XiJie
	 * @time：2017年10月15日 下午2:27:46
	 */
	public boolean saveAutoVoice(AutoVoice autoVoice);

	/**
	 * 说明：根据条件进行分页查询
	 * @param autoVoice
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author XiJie
	 * @time：2017年10月15日 下午2:52:23
	 */
	public PageInfo<AutoVoice> queryAutoVoiceByCondition(AutoVoice autoVoice, Integer pageNum, Integer pageSize);

	/**
	 * 说明：根据id进行修改
	 * @param autoVoice
	 * @return
	 * @author XiJie
	 * @time：2017年10月15日 下午3:09:13
	 */
	public boolean updateAutoVoice(AutoVoice autoVoice);

	/**
	 * 说明：根据id进行批量删除
	 * @param ids
	 * @author XiJie
	 * @time：2017年10月15日 下午3:15:22
	 */
	public void deleteAutoVoiceByIds(String ids);

	/**
	 * 说明：根据主标签和副标签来到后台查询对应的语音文件名
	 * @param masterLabel
	 * @param assistantLabel
	 * @return
	 * @author XiJie
	 * @time：2017年10月15日 下午3:37:41
	 */
	public String queryAutoVoiceByTag(String masterLabel, String assistantLabel);

	/**
	 * 说明：根据主标签和副标签来到后台查询对应的语音文件名
	 * @param masterLabel
	 * @param assistantLabel
	 * @return
	 * @author XiJie
	 * @time：2017年10月18日 下午1:17:57
	 */
	public AutoVoice queryAutoVoiceByTag2(String masterLabel, String assistantLabel);

	/**
	 * 说明：根据标签集合查询对应的对象
	 * @param labels
	 * @return
	 * @author XiJie
	 * @time：2017年10月18日 下午1:40:42
	 */
	public AutoVoice queryAutoVoiceByTagList(List<String> list);

}
