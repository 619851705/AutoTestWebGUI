package com.dcits.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dcits.bean.User;
import com.dcits.dao.UserDao;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	

	
	/**
	 * 登陆
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public User findUserBylogin(String userName,String passWord){
		return userDao.login(userName, passWord);
	}
	
	/**
	 * 保存新增用户
	 * @param user
	 */
	public void saveUser(User user){
		userDao.save(user);
	}
	
	/**
	 * 更新用户
	 * @param user
	 */
	public void updateUser(User user){
		userDao.edit(user);
	}
}
