package com.dcits.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;



/**
 * ����ͨ��DAO
 * @author Administrator
 *
 */
@SuppressWarnings("unchecked")
public class BaseDao<T> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession(){
		return this.sessionFactory.getCurrentSession();
	}
	
	private Class<T> clazz;
	

	public BaseDao() {
		//�����ȡ�������
		ParameterizedType type=(ParameterizedType)this.getClass().getGenericSuperclass();
		this.clazz=(Class<T>)type.getActualTypeArguments()[0];		
	}
	
	public Integer save(T entity) {
		// TODO Auto-generated method stub
		Integer id=(Integer) getSession().save(entity);
		return id;
	}

	public void delete(int id) {
		// TODO Auto-generated method stub
		getSession().delete(get(id));
	}

	public void edit(T entity) {
		// TODO Auto-generated method stub
		getSession().merge(entity);
	}

	
	public T get(Integer id) {
		// TODO Auto-generated method stub
		
		return (T)getSession().get(clazz, id);
	}

	public T load(Integer id) {
		// TODO Auto-generated method stub
		return (T)getSession().load(clazz, id);
	}

	public List<T> findAll() {
		// TODO Auto-generated method stub
		String hsql="select t from "+clazz.getSimpleName()+" t";
		
		return getSession().createQuery(hsql).list();
	}

	public int totalCount() {
		// TODO Auto-generated method stub
		int count=0;
		String hql="select count(t) from "+clazz.getSimpleName()+" t";
		Long temp=(Long)getSession().createQuery(hql).uniqueResult();
		if(temp!=null){
			count=temp.intValue();
		}
		return count;
	}

	public void update(String sql) {
		// TODO Auto-generated method stub
		getSession().createQuery(sql).executeUpdate();
	}

	public T findUnique(String sql) {
		// TODO Auto-generated method stub
		
		return (T)getSession().createQuery(sql).uniqueResult();
	}
}
