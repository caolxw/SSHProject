package com.action;

import com.opensymphony.xwork2.ActionSupport;
import com.service.IndexService;

public class LoginAction extends ActionSupport{
	private static final long serialVersionUID = 1L;
	
	private IndexService is;
	
	private String username;
	private String password;


	public IndexService getIs() {
		return is;
	}


	public void setIs(IndexService is) {
		this.is = is;
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


	public String execute() throws Exception{
		System.out.println("�����¼ִ�и÷���");
		if (username == null || username.equals("")) {
			return "FAILURE";
		}
		Integer userId = is.check(username, password);
		if (userId != null) {
			System.out.println("��½�ɹ�");
			return "SUCCESS";
		}else {
			System.out.println("��¼����");
			return "FAILURE";
		}
	}
}
