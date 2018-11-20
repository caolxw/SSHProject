package com.entity;

public class User {
	private int cid;
	private String username;
	private String password;
	
	public User() {}
	public User(int cid,String username,String password) {
		this.cid = cid;
		this.password = password;
		this.username = username;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "User [cid=" + cid + ", username=" + username + ", password=" + password + "]";
	}
	
	
}
