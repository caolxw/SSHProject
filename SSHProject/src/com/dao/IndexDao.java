package com.dao;

import com.entity.User;

public interface IndexDao {
	public User checkandlogin(String username,String password);
}
