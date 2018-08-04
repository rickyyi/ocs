package cn.hf.manage.form;

import org.springframework.util.StringUtils;

/**
 * CreateUser： YeShengGuang
 * CreateTime：2017年10月24日
 * FileExplain:表单基类
 */
public class BaseForm {
	
	protected Integer pageNum;
	protected Integer pageSize;
	
	public Integer getPageNum() {
		return StringUtils.isEmpty(pageNum)?0:pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getPageSize() {
		return StringUtils.isEmpty(pageSize)?10:pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
}
