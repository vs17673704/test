package com.portal.question.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.TypeAlias;

@Entity
@Table(name = "user")
public class User 
{
	@Column(name = "user_id")
	private String userId;
	@Column(name = "user_name")
	private String userName;
	
	public User() 
	{
		
	}

	public User(String userId, String userName) {
		this.userId = userId;
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + "]";
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
