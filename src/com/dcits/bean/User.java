package com.dcits.bean;
// default package

import java.sql.Timestamp;
import java.util.Date;


/**
 * User entity. @author MyEclipse Persistence Tools
 */

public class User{


    // Fields    

     private Integer userId;
     private Role role;
     private String username;
     private String password;
     private Date createTime;
     private String status;
     private Timestamp lastLoginTime;
     private String ifNew;
     private String realName;


    // Constructors

    /** default constructor */
    public User() {
    }

	/** minimal constructor */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    /** full constructor */
    public User(Role role, String username, String password, Date createTime, String status, Timestamp lastLoginTime, String ifNew,String realName) {
        this.role = role;
        this.username = username;
        this.password = password;
        this.createTime = createTime;
        this.status = status;
        this.lastLoginTime = lastLoginTime;
        this.ifNew = ifNew;
        this.realName = realName;
    }

   
    // Property accessors

    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Role getRole() {
        return this.role;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return this.createTime;
    }
    
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatus() {
        return this.status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getLastLoginTime() {
        return this.lastLoginTime;
    }
    
    public void setLastLoginTime(Timestamp lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getIfNew() {
        return this.ifNew;
    }
    
    public void setIfNew(String ifNew) {
        this.ifNew = ifNew;
    }
   
    
    public void setRealName(String realName) {
		this.realName = realName;
	}

    public String getRealName() {
		return realName;
	}






}