package com.dcits.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dcits.bean.User;

@Repository
public class UserDao extends BaseDao<User>{
	
	/**
	 * ƥ����е�½���û�
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public User login(String userName,String passWord){
		// ����HQL���
		Query query = getSession()
				.createQuery(
						"from User u where u.username=:userName and u.password=:passWord");
		// ����HQL����еĲ���
		query.setString("userName", userName);
		query.setString("passWord", passWord);
		// ִ��HQL���
		return (User) query.uniqueResult();
	}
	
}
