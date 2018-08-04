package cn.hf.manage.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.hf.manage.mapper.KeyWordsMapper;
import cn.hf.manage.pojo.KeyWords;
import cn.hf.manage.service.KeyWordsService;

/**
 * 说明：关键字实现层
 * @author XiJie
 * @version 1.0
 * @date 2017年10月13日
 */
@Service("keyWordsService")
public class KeyWordsServiceImpl implements KeyWordsService{

	@Autowired
	private KeyWordsMapper keyWordsMapper;
	
	/**
	 * 说明：保存方法
	 * @param keyWords
	 * @return boolean
	 * @author XiJie
	 * @time：2017年10月13日 下午1:12:25
	 */
	@Override
	public boolean saveText(KeyWords keyWords) {
		boolean flag = keyWordsMapper.insert(keyWords)==1;
		return flag;
	}

	/**
	 * 说明：根据条件进行分页查询
	 * @param keyWords
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author XiJie
	 * @time：2017年10月13日 下午2:25:12
	 */
	@Override
	public PageInfo<KeyWords> queryTextByCondition(KeyWords keyWords, Integer pageNum, Integer pageSize) {
		// 对文本进行非空判断
		if (StringUtils.isBlank(keyWords.getText())){
			keyWords.setText(null);
		}
		// 对副标签进行非空判断
		if (StringUtils.isBlank(keyWords.getAssistantLabel())){
			keyWords.setAssistantLabel(null);
		}
		// 对主标签进行非空判断
		if (StringUtils.isBlank(keyWords.getMasterLabel())){
			keyWords.setMasterLabel(null);
		}
		
		// 开启分页助手
		PageHelper.startPage(pageNum, pageSize);
		// 调用数据数据层进行查询
		List<KeyWords> list = keyWordsMapper.select(keyWords);
		// 封装分页信息
		PageInfo<KeyWords> pageInfo = new PageInfo<>(list);
		return pageInfo;
	}

	/**
	 * 说明：根据id进行批量删除
	 * @param ids
	 * @return
	 * @author XiJie
	 * @time：2017年10月13日 下午4:18:42
	 */
	@Override
	public void deleteTextByIds(String ids) {
		// 对ids进行分割
		String[] idArr = ids.split(",");
		for (String id : idArr) {
			keyWordsMapper.deleteByPrimaryKey(Integer.parseInt(id));
		}
	}

	/**
	 * 说明：根据id进行修改
	 * @param keyWords
	 * @return
	 * @author XiJie
	 * @time：2017年10月13日 下午4:55:30
	 */
	@Override
	public boolean updateText(KeyWords keyWords) {
		boolean flag = keyWordsMapper.updateByPrimaryKeySelective(keyWords)==1;
		return flag;
	}

	@Override
	public KeyWords queryTextByCondition(String condition) {
		// 创建对象
		Example example = new Example(KeyWords.class);
		Criteria criteria = example.createCriteria();
		if (StringUtils.isEmpty(condition)) {
			criteria.andEqualTo("text", "+-&+");
		} else {
			criteria.andEqualTo("text", condition);
		}
		example.or(criteria);
		List<KeyWords> list = keyWordsMapper.selectByExample(example);
		if (list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}
	
	

}
