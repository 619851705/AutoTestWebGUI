package com.dcits.dao;

import org.springframework.stereotype.Repository;

import com.dcits.bean.WebConfig;

@Repository
public class WebConfigDao extends BaseDao<WebConfig>{
	
	public WebConfig findByUserId(Integer userId){
		String hql="From WebConfig w where w.userId=:userId";
		return (WebConfig) getSession().createQuery(hql).setInteger("userId",userId).uniqueResult();
	}
}
