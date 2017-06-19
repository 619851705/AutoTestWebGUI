package com.dcits.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dcits.bean.WebCase;
@SuppressWarnings("unchecked")
@Repository
public class WebCaseDao extends BaseDao<WebCase>{
	
	public List<WebCase> findMyCases(Integer userId){
		String hql="From WebCase w where w.user.userId=:userId";
		return getSession().createQuery(hql).setInteger("userId",userId).list();
	}
}
