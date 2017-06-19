package com.dcits.dao;

import org.springframework.stereotype.Repository;

import com.dcits.bean.WebObjectCategory;

@Repository
public class WebObjectCategoryDao extends BaseDao<WebObjectCategory>{
	
	public void updateCategoryName(Integer categoryId,String categoryName){
		String hql="update WebObjectCategory w set w.categoryName=:categoryName where w.categoryId=:categoryId";
		getSession().createQuery(hql).setString("categoryName",categoryName).setInteger("categoryId",categoryId).executeUpdate();
	}
}
