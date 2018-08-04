package cn.hf.manage.service;

import java.util.List;

import com.github.pagehelper.PageInfo;

import cn.hf.manage.pojo.Text;

/**
 * 说明：有关没有匹配上的文本的业务层接口
 * @author XiJie
 * @version 1.0
 * @date 2017年10月17日
 */
public interface TextService {

	
	/**
	 * 说明：保存没有匹配上的文本
	 * @param text
	 * @param callid
	 * @param cause
	 * @author XiJie
	 * @time：2017年10月17日 下午3:38:58
	 */
	public void saveText(String text, String callid, String cause);

	/**
	 * 说明：保存
	 * @param text
	 * @author XiJie
	 * @time：2017年10月26日 上午10:12:35
	 */
	public void saveText(Text text);

	/**
	 * 说明：保存
	 * @param string
	 * @param string2
	 * @param string3
	 * @param labels
	 * @author XiJie
	 * @time：2017年10月30日 下午2:31:21
	 */
	public void saveText(String sentence, String callid, String cause, List<String> labels);

	/**
	 * 说明：保存
	 * @param string
	 * @param string2
	 * @param string3
	 * @param masterLabel
	 * @param assistantLabel
	 * @author XiJie
	 * @time：2017年10月30日 下午3:03:48
	 */
	public void saveText(String sentence, String callid, String cause, String masterLabel, String assistantLabel);

	/**
	 * 说明：根据callid和主副标签来查询对应的文本
	 * @param callid
	 * @return
	 * @author XiJie
	 * @time：2017年11月1日 上午10:33:02
	 */
	public int selectTextByCallIdAndMasterLabelAndAssistantLabel(String callid);

	/**
	 * 说明：根据callid和主副标签来查询对应的文本
	 * @param string
	 * @param masterLabel
	 * @return
	 * @author XiJie
	 * @param assistantLabel 
	 * @time：2017年11月1日 上午11:00:34
	 */
	public int selectTextByCallIdAndMasterLabelAndAssistantLabel2(String callid, String masterLabel);

	public PageInfo<Text> queryTextByCondition(Text text, Integer pageNum,
			Integer pageSize);

	public boolean updateText(Text text);

}
