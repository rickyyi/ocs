package cn.hf.manage.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service("properttiesService")
public class ProperttiesService{
		
	@Value("${REMOTE_PATH}")
	public String REMOTE_PATH;
	@Value("${REMOTE_PATH2}")
	public String REMOTE_PATH2;
	
	// 查询solr的url
	@Value("${REMOTE_PATH3}")
	public String REMOTE_PATH3;
}
