package com.dcits.bean;

import java.util.HashSet;
import java.util.Set;

public class OperationInterface {

	private Integer opId;
	private String opName;
	private String callName;
	private String ifParent;
	private String mark;
	private String status;
	private OperationInterface oi;
	
	private Set<Role> roles=new HashSet<Role>();
	
	private Set<OperationInterface> ois=new HashSet<OperationInterface>();
	
	
	
	

	public Set<Role> getRoles() {
		return roles;
	}
		
	public String getIfParent() {
		return ifParent;
	}

	public void setIfParent(String ifParent) {
		this.ifParent = ifParent;
	}

	
	
	public Set<OperationInterface> getOis() {
		return ois;
	}

	public void setOis(Set<OperationInterface> ois) {
		this.ois = ois;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public OperationInterface getOi() {
		return oi;
	}

	public void setOi(OperationInterface oi) {
		this.oi = oi;
	}

	public Integer getOpId() {
		return opId;
	}
	public void setOpId(Integer opId) {
		this.opId = opId;
	}
	public String getOpName() {
		return opName;
	}
	public void setOpName(String opName) {
		this.opName = opName;
	}
	public String getCallName() {
		return callName;
	}
	public void setCallName(String callName) {
		this.callName = callName;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	public OperationInterface(String opName, String callName, String ifParent,
			String mark, String status, OperationInterface oi) {
		super();
		this.opName = opName;
		this.callName = callName;
		this.ifParent = ifParent;
		this.mark = mark;
		this.status = status;
		this.oi = oi;
	}

	public OperationInterface() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
