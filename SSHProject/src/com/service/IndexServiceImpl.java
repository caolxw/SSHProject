package com.service;

import com.dao.IndexDao;
import com.entity.User;

public class IndexServiceImpl implements IndexService {

	private IndexDao id;
	
	
	public IndexDao getId() {
		return id;
	}


	public void setId(IndexDao id) {
		this.id = id;
	}


	@Override
	public Integer check(String username,String password) {
		// TODO Auto-generated method stub
		try {
			User user = id.checkandlogin(username, password);
			if (user!=null) {
				return user.getCid();
			}
		}catch (Exception e) {
			e.printStackTrace();// TODO: handle exception
		}
		return null;
	}

}
