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
	 * 查找所有可用的数据库
	 * @return
	 */
	public List<DataDB> findAll(){
		return dao.findAll();
	}
	
	/**
	 * 获取指定的数据库信息
	 * @param dbId
	 * @return
	 */
	public DataDB getDB(Integer dbId){
		return dao.get(dbId);				
	}
	
	/**
	 * 修改或者增加数据库信息
	 * @param db
	 */
	public void editDBInfo(DataDB db){
		dao.edit(db);
	}
	
	/**
	 * 删除指定的数据库信息
	 * @param dbId
	 */
	public void delDBInfo(Integer dbId){
		dao.delete(dbId);
	}
}
