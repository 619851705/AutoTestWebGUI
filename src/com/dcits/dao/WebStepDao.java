package com.dcits.dao;

import org.springframework.stereotype.Repository;

import com.dcits.bean.WebStep;

@Repository
public class WebStepDao extends BaseDao<WebStep>{

	@Override
	public void delete(int id) {
		String hql="update WebReport w set w.webStep=null where w.webStep.stepId=:id";
		getSession().createQuery(hql).setInteger("id",id).executeUpdate();
		super.delete(id);
	}	
}
