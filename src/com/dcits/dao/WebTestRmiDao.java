package com.dcits.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dcits.bean.WebTestRmi;
@SuppressWarnings("unchecked")
@Repository
public class WebTestRmiDao extends BaseDao<WebTestRmi> {
	
	public List<WebTestRmi> getByUser(Integer userId){
		//String hql="From WebTestRmi w where w.user.userId=:userId and w.status='1' and w.submitTime>CURDATE() order by w.submitTime desc";
		String hql = "From WebTestRmi w where w.user.userId=:userId and w.submitTime>CURDATE() order by w.submitTime desc";
		return getSession().createQuery(hql).setInteger("userId", userId).list();
	}
	
	public List<WebTestRmi> getUserTask(Integer userId){
		String hql = "From WebTestRmi w where w.user.userId=:userId";
		return getSession().createQuery(hql).setInteger("userId", userId).list();
	}
	
	public void updateTaskStatus(Integer testId,String status){
		String hql = "update WebTestRmi w set w.status=:status where w.testId=:testId";
		getSession().createQuery(hql).setString("status", status).setInteger("testId",testId).executeUpdate();
	}
}
