package cn.hf.manage.service;

import com.github.pagehelper.PageInfo;

import cn.hf.manage.pojo.KeyWords;

/**
 * 说明：关键字接口
 * @author XiJie
 * @version 1.0
 * @date 2017年10月13日
 */
public interface KeyWordsService {

	/**
	 * 说明：保存的方法
	 * @param keyWords 对象
	 * @return boolean
	 * @author XiJie
	 * @time：2017年10月13日 下午1:09:57
	 */
	public boolean saveText(KeyWords keyWords);

	/**
	 * 说明：根据条件进行分页查询
	 * @param keyWords
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @author XiJie
	 * @time：2017年10月13日 下午2:24:26
	 */
	public PageInfo<KeyWords> queryTextByCondition(KeyWords keyWords, Integer pageNum, Integer pageSize);

	/**
	 * 说明：根据id进行批量删除
	 * @param ids
	 * @author XiJie
	 * @time：2017年10月13日 下午4:17:49
	 */
	public void deleteTextByIds(String ids);

	/**
	 * 说明：修改
	 * @param keyWords
	 * @return
	 * @author XiJie
	 * @time：2017年10月13日 下午4:55:04
	 */
	public boolean updateText(KeyWords keyWords);

	/**
	 * 说明：根据条件进行查询
	 * @param condition
	 * @return
	 * @author XiJie
	 * @time：2017年10月17日 下午2:27:22
	 */
	public KeyWords queryTextByCondition(String condition);


}
