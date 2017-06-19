package com.dcits.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.bean.Role;
import com.dcits.dao.RoleDao;

@Service
public class RoleService {
	@Autowired
	private RoleDao dao;
	
	/**
	 * �������еĽ�ɫ
	 * @return
	 */
	public List<Role> findAll(){
		return dao.findAll();
	}
	
	/**
	 * ����ID����ָ���Ľ�ɫ��Ϣ
	 * @param roleId
	 * @return
	 */
	public Role get(Integer roleId){
		return dao.get(roleId);
	}
	
	/**
	 * ɾ��һ����ɫ,����ɾ��Ԥ�õ�admin��ɫ,ɾ����ɫ���ᵼ����������û���ɫΪ�ն�����ִ���κβ���
	 * @param roleId
	 */
	public void del(Integer roleId){
		dao.delete(roleId);
	}
	
	/**
	 * ɾ����������һ����ɫ
	 * @param role
	 */
	public void edit(Role role){
		dao.edit(role);
	}
	
}
