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
	 * ��½
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public User findUserBylogin(String userName,String passWord){
		return userDao.login(userName, passWord);
	}
	
	/**
	 * ���������û�
	 * @param user
	 */
	public void saveUser(User user){
		userDao.save(user);
	}
	
	/**
	 * �����û�
	 * @param user
	 */
	public void updateUser(User user){
		userDao.edit(user);
	}
}
