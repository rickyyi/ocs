package cn.hf.manage.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hf.manage.mapper.AutoVoiceMapper;
import cn.hf.manage.pojo.AutoVoice;
import cn.hf.manage.pojo.ReturnText;
import cn.hf.manage.service.AutoVoiceService;

@Service("autoVoiceService")
public class AutoVoiceServiceImpl implements AutoVoiceService{

	// 属性注入数据层对象
	@Autowired
	private AutoVoiceMapper autoVoiceMapper;
	
	/**
	 * 说明：保存语音文件
	 * @param autoVoice
	 * @return boolean
	 * @author XiJie
	 * @time：2017年10月15日 下午2:29:18
	 */
	@Override
	public boolean saveAutoVoice(AutoVoice autoVoice) {
		boolean flag = autoVoiceMapper.insert(autoVoice)==1;
		return flag;
	}


	/**
	 * 说明：根据条件进行分页查询
	 * @param autoVoice
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author XiJie
	 * @time：2017年10月15日 下午2:52:41
	 */
	@Override
	public PageInfo<AutoVoice> queryAutoVoiceByCondition(AutoVoice autoVoice, Integer pageNum, Integer pageSize) {
		if (StringUtils.isBlank(autoVoice.getName())){
			autoVoice.setName(null);
		}
		if (StringUtils.isBlank(autoVoice.getAssistantLabel())){
			autoVoice.setAssistantLabel(null);
		}
		if (StringUtils.isBlank(autoVoice.getMasterLabel())){
			autoVoice.setMasterLabel(null);
		}
		if (StringUtils.isBlank(autoVoice.getSellPoint())){
			autoVoice.setSellPoint(null);
		}
		if (StringUtils.isBlank(autoVoice.getIntention())){
			autoVoice.setIntention(null);
		}
		
		// 开启分页助手
		PageHelper.startPage(pageNum, pageSize);
		// 调用数据数据层进行查询
		List<AutoVoice> list = autoVoiceMapper.select(autoVoice);
		// 封装分页信息
		PageInfo<AutoVoice> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}


	/**
	 * 说明：根据id进行修改
	 * @param autoVoice
	 * @return
	 * @author XiJie
	 * @time：2017年10月15日 下午3:09:46
	 */
	@Override
	public boolean updateAutoVoice(AutoVoice autoVoice) {
		boolean flag = autoVoiceMapper.updateByPrimaryKeySelective(autoVoice)==1;
		return flag;
	}


	/**
	 * 说明：根据id进行批量删除
	 * @param ids
	 * @author XiJie
	 * @time：2017年10月15日 下午3:15:46
	 */
	@Override
	public void deleteAutoVoiceByIds(String ids) {
		// 对ids进行分割
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			autoVoiceMapper.deleteByPrimaryKey(Integer.parseInt(id));
		}
		
	}


	/**
	 * 说明：根据主标签和副标签来到后台查询对应的语音文件名
	 * @param masterLabel
	 * @param assistantLabel
	 * @return
	 * @author XiJie
	 * @time：2017年10月15日 下午3:38:10
	 */
	@Override
	public String queryAutoVoiceByTag(String masterLabel, String assistantLabel) {
		// 创建对象
		Example example = new Example(AutoVoice.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("masterLabel", masterLabel);
		criteria.andEqualTo("assistantLabel", assistantLabel);
		example.or(criteria);
		List<AutoVoice> list = autoVoiceMapper.selectByExample(example);
		if (list.isEmpty()) {
			return "";
		}
		String autoVoiceName = list.get(0).getName();
		return autoVoiceName;
	}
	
	@Override
	public AutoVoice queryAutoVoiceByTag2(String masterLabel, String assistantLabel) {
		AutoVoice autoVoice = new AutoVoice();
		// 创建对象
		Example example = new Example(AutoVoice.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("masterLabel", masterLabel);
		criteria.andEqualTo("assistantLabel", assistantLabel);
		example.or(criteria);
		List<AutoVoice> list = autoVoiceMapper.selectByExample(example);
		if (list.isEmpty()) {
			return autoVoice;
		}
		autoVoice = list.get(0);
		return autoVoice;
	}


	/**
	 * 说明：根据标签集合查询对应的对象
	 * @param labels
	 * @return
	 * @author XiJie
	 * @throws JsonProcessingException 
	 * @time：2017年10月18日 下午1:41:21
	 */
	@Override
	public AutoVoice queryAutoVoiceByTagList(List<String> list) {
		List<ReturnText> returnList = new ArrayList<>();
		for (String str : list) {
			String[] labelArr = str.split("\\|");
			ReturnText returnText = new ReturnText();
			returnText.setMasterLabel(labelArr[0]);
			returnText.setAssistantLabel(labelArr[1]);
			returnList.add(returnText);
		}
		
		// 创建对象
		Example example = new Example(AutoVoice.class);
		// 添加查询条件
		for (ReturnText returnText : returnList) {
			// 创建查询对象
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("masterLabel", returnText.getMasterLabel());
			criteria.andEqualTo("assistantLabel", returnText.getAssistantLabel());
			example.or(criteria);
		}
		// 添加排序
		example.setOrderByClause("weight desc");
		
		List<AutoVoice> autoVoiceList = autoVoiceMapper.selectByExample(example);
		if (autoVoiceList.isEmpty()) {
			return null;
		}
		AutoVoice autoVoice = autoVoiceList.get(0);
		return autoVoice;
	}

}
