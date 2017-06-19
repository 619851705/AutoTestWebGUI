package com.dcits.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.bean.WebConfig;
import com.dcits.dao.WebConfigDao;

@Service
public class WebConfigService {
	
	@Autowired
	private WebConfigDao webConfigDao;
	
	/**
	 * 根据userId查找对应的web自动化测试配置
	 * @param userId
	 * @return
	 */
	public WebConfig findConfig(Integer userId){
		return webConfigDao.findByUserId(userId);
	}
	
	/**
	 * 增加或者修改更新指定的配置
	 * @param config
	 */
	public void editConfig(WebConfig config){
		webConfigDao.edit(config);
	}
}
