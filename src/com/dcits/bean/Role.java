package com.dcits.bean;

import java.util.HashSet;
import java.util.Set;
// default package



/**
 * Role entity. @author MyEclipse Persistence Tools
 */

public class Role {

	private Integer roleId;
	private String roleGroup;
	private String roleName;
	private String mark;
	
	private Set<OperationInterface> ois=new HashSet<OperationInterface>();
	
	private Set<User> users = new HashSet<User>();
	
	
	
	public Set<User> getUsers() {
		return users;
	}
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	//��ǰӵ�е�Ȩ�޸���
	private Integer oiNum;
		
	public Integer getOiNum() {
		return oiNum;
	}
	public void setOiNum() {
		this.oiNum = ois.size();
	}
	public Set<OperationInterface> getOis() {
		return ois;
	}
	public void setOis(Set<OperationInterface> ois) {
		this.ois = ois;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleGroup() {
		return roleGroup;
	}
	public void setRoleGroup(String roleGroup) {
		this.roleGroup = roleGroup;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public Role(String roleGroup, String roleName, String mark) {
		super();
		this.roleGroup = roleGroup;
		this.roleName = roleName;
		this.mark = mark;
	}
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
}