package com.dcits.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.bean.WebObject;
import com.dcits.dao.WebObjectDao;

@Service
public class WebObjectService {
	
	@Autowired
	private WebObjectDao webObjectDao;
	
	/**
	 * ����ȫ���Ĳ��Զ���
	 * @return
	 */
	public List<WebObject> findAll(){
		return webObjectDao.findAll();
	}
	
	/**
	 * �����µĲ��Զ���
	 * �޸Ĳ��Զ���
	 * @param o
	 * @return
	 */
	public void editObject(WebObject o){
		webObjectDao.edit(o);
	}
	
	/**
	 * ɾ�����Զ���
	 * ɾ��֮ǰ��Ҫ�����֮������WebStep
	 * @param objectId
	 */
	public void delObject(Integer objectId){		
		webObjectDao.delete(objectId);
	}
	
	/**
	 * ���ݶ������Ʋ��Ҳ��Զ���
	 * ��֤�������Ƶ�Ψһ��
	 * @param objectName
	 * @return
	 */
	public WebObject findObjectByName(String objectName){
		return webObjectDao.findObjectByName(objectName);
	}
	
	/**
	 * ����ID���Ҷ�Ӧ�Ĳ��Զ���
	 * @param ObjectId
	 * @return
	 */
	public WebObject findObject(Integer objectId){
		return webObjectDao.get(objectId);
	}
	
}
