package com.dcits.dao;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dcits.bean.User;

@Repository
public class UserDao extends BaseDao<User>{
	
	/**
	 * 匹配进行登陆的用户
	 * @param userName
	 * @param passWord
	 * @return
	 */
	public User login(String userName,String passWord){
		// 创建HQL语句
		Query query = getSession()
				.createQuery(
						"from User u where u.username=:userName and u.password=:passWord");
		// 设置HQL语句中的参数
		query.setString("userName", userName);
		query.setString("passWord", passWord);
		// 执行HQL语句
		return (User) query.uniqueResult();
	}
	
}
