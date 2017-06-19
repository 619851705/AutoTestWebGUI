package com.dcits.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dcits.bean.WebStepCategory;

@SuppressWarnings("unchecked")
@Repository
public class WebStepCategoryDao extends BaseDao<WebStepCategory> {
	
	
	//anҪ�����category
	//0-ֻ�����������õ�   1-���������ʹ�ȷ����˵� 2-���Ҵ�ȷ�ϵ�
	public List<WebStepCategory> findAll(Integer type) {
		String hql="From WebStepCategory w where ";
		if(type==0){
			hql+="w.status='0'";
		}else if(type==1){
			hql+="w.status<>2";
		}else if(type==2){
			hql+="w.status='1'";
		}
		return getSession().createQuery(hql).list();
		
	}
	
	public List<WebStepCategory> findByUser(String username){
		String hql="From WebStepCategory w where w.createUser=:username";
		return getSession().createQuery(hql).setString("username",username).list();
	}
	
	public void updateStatus(Integer categoryId,String status){
		String hql="update WebStepCategory w set w.status=:status where w.categoryId=:categoryId";		
		getSession().createQuery(hql).setString("status",status).setInteger("categoryId",categoryId).executeUpdate();
	}
	
	public void updateDesc(Integer categoryId,String desc){
		String hql="update WebStepCategory w set w.categoryDesc=:desc where w.categoryId=:categoryId";
		getSession().createQuery(hql).setString("desc",desc).setInteger("categoryId", categoryId).executeUpdate();
	}
	
}
