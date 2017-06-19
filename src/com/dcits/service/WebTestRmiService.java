package com.dcits.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.bean.WebTestRmi;
import com.dcits.dao.WebTestRmiDao;

@Service
public class WebTestRmiService {
	
	@Autowired
	private WebTestRmiDao dao;
	
	/**
	 * ����
	 * @param test
	 */
	public void edit(WebTestRmi test){
		dao.edit(test);
	}
	
	/**
	 * ���ӱ���
	 * @param test
	 * @return
	 */
	public Integer save(WebTestRmi test){
		return dao.save(test);
	}
	
	/**
	 * ��ȡָ����
	 * @param testId
	 * @return
	 */
	public WebTestRmi get(Integer testId){
		return dao.get(testId);
	}
	
	/**
	 * �����û�ID���Ҹ��û������ύ�Ļ�δ��ʼ���ԵĲ�������
	 * �õ����Ǽ���
	 * @param userId
	 * @return
	 */
	public List<WebTestRmi> getByUser(Integer userId){
		return dao.getByUser(userId);
	}
	
	/**
	 * ��ȡָ���û��ĵ�ǰ��������
	 * @param userId
	 * @return
	 */
	public List<WebTestRmi> getUserTask(Integer userId){
		return dao.getUserTask(userId);
	}
	
	/**
	 * ɾ��ָ������
	 * @param testId
	 */
	public void delTask(Integer testId){
		dao.delete(testId);
	}
	
	/**
	 * ��������״̬
	 * @param status
	 * @param testId
	 */
	public void updateStatus(String status,Integer testId){
		dao.updateTaskStatus(testId, status);
	}
}
