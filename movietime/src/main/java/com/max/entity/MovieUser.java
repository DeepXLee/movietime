package com.max.entity;

import java.io.Serializable;

public class MovieUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6042383265563027462L;
	
	
	private Integer userId;//id
	private String userName;//用户名
	private String userPassword;//密码
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + "]";
	}
	
	


    
}