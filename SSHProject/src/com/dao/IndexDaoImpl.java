package com.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.entity.User;

public class IndexDaoImpl implements IndexDao {

	SessionFactory sessionFactory;
	
	
	

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void close(Session session) {
		session.close();
		sessionFactory.close();
	}
	
	@Override
	public User checkandlogin(String username, String password) {
		// TODO Auto-generated method stub
//		Session session = sessionFactory.openSession();
		Session session = sessionFactory.openSession();
		
		List<User> list = session.createQuery("from User where username = '"+username+"'and password = '"+password+"'").list();
		if(list.size() == 1) {
			System.out.println("Ok "+username);
			close(session);
			return (User)list.get(0);
		}
		close(session);
		return null;
	}

}
