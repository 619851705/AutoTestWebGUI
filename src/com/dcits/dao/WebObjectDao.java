package com.dcits.dao;

import org.springframework.stereotype.Repository;

import com.dcits.bean.WebObject;

@Repository
public class WebObjectDao extends BaseDao<WebObject>{
	
	public WebObject findObjectByName(String objectName){
		String hql="From WebObject w where w.objectName=:objectName";
		return (WebObject) getSession().createQuery(hql).setString("objectName",objectName).uniqueResult();
	}

	@Override
	public void delete(int id) {
		String hql="update WebStep w set w.webObject=null where w.webObject.objectId=:id";
		getSession().createQuery(hql).setInteger("id",id).executeUpdate();
		super.delete(id);
	}
	
	
	
}
