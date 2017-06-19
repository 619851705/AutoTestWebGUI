package com.dcits.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.bean.WebCaseSetComp;
import com.dcits.dao.WebCaseSetCompDao;

@Service
public class WebCaseSetCompService {
	
	@Autowired
	private WebCaseSetCompDao dao;
	
	/**
	 * ����caseId��setId����ָ���Ĺ�����ϵ
	 * @param caseId
	 * @param setId
	 * @return
	 */
	public WebCaseSetComp get(Integer caseId,Integer setId){
		return dao.get(caseId, setId);
	}
	
	/**
	 * ��ӻ����޸�һ��������ϵ
	 * @param comp
	 */
	public void edit(WebCaseSetComp comp){
		dao.edit(comp);
	}
	
	/**
	 * ����ָ���û�����˼�¼
	 * @param userId
	 * @return
	 */
	public List<WebCaseSetComp> findByUser(Integer userId){
		return dao.findByUserId(userId);
	}
	
	/**
	 * ����״̬
	 * @param id
	 * @param status
	 */
	public void updateStatus(Integer id,String status){
		dao.updateStatus(id, status);
	}
	
	/**
	 * ��������������ϵ
	 * ״̬Ϊ0����1    �����ͨ���ʹ����,����ʾ�Ѵ�غ���ɾ��
	 * @return
	 */
	public List<WebCaseSetComp> findAll(Integer setId){
		return dao.findAll(setId);
	}
}
