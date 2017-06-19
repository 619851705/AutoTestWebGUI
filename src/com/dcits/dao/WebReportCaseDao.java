package com.dcits.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dcits.bean.WebReportCase;
@SuppressWarnings("unchecked")
@Repository
public class WebReportCaseDao extends BaseDao<WebReportCase> {
	public List<WebReportCase> findMyReportCase(Integer userId){
		String hql="From WebReportCase w where w.webCase.user.userId=:userId";
		return getSession().createQuery(hql).setInteger("userId",userId).list();
	}
}
