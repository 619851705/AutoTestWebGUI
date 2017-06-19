package com.dcits.dao;

import org.springframework.stereotype.Repository;

import com.dcits.bean.WebCaseSet;

@Repository
public class WebCaseSetDao extends BaseDao<WebCaseSet> {
	
	public void addTestCount(Integer setId){
		String hql="update WebCaseSet w set w.testCount=w.testCount+1 where w.setId=:setId";
		getSession().createQuery(hql).setInteger("setId",setId).executeUpdate();
	}
}
