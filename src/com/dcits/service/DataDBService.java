package com.dcits.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.bean.DataDB;
import com.dcits.dao.DataDBDao;


@Service
public class DataDBService {
	
	@Autowired
	private DataDBDao dao;
	
	/**
	 * �������п��õ����ݿ�
	 * @return
	 */
	public List<DataDB> findAll(){
		return dao.findAll();
	}
	
	/**
	 * ��ȡָ�������ݿ���Ϣ
	 * @param dbId
	 * @return
	 */
	public DataDB getDB(Integer dbId){
		return dao.get(dbId);				
	}
	
	/**
	 * �޸Ļ����������ݿ���Ϣ
	 * @param db
	 */
	public void editDBInfo(DataDB db){
		dao.edit(db);
	}
	
	/**
	 * ɾ��ָ�������ݿ���Ϣ
	 * @param dbId
	 */
	public void delDBInfo(Integer dbId){
		dao.delete(dbId);
	}
}
