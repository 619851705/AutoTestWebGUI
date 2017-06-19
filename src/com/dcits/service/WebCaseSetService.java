package com.dcits.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.bean.WebCaseSet;
import com.dcits.dao.WebCaseSetDao;


@Service
public class WebCaseSetService {
	
	@Autowired
	private WebCaseSetDao dao;
	
	/**
	 * �������еĲ�����������Ϣ
	 * @return
	 */
	public List<WebCaseSet> findAll(){
		return dao.findAll();
	}
	
	/**
	 * ����ID��ȡ������������Ϣ
	 * @param setId
	 * @return
	 */
	public WebCaseSet get(Integer setId){
		return dao.get(setId);
	}
	
	/**
	 * ɾ��ָ����caseSet,��ɾ����������case
	 * @param setId
	 */
	public void del(Integer setId){
		dao.delete(setId);
	}
	
	/**
	 * ���������޸Ĳ���������
	 * @param caseSet
	 */
	public void edit(WebCaseSet caseSet){
		dao.edit(caseSet);
	}
	
	/**
	 * ���Դ���+1
	 * @param setId
	 */
	public void addCount(Integer setId){
		dao.addTestCount(setId);
	}
}
