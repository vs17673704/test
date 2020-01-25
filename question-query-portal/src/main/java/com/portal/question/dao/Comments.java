package com.portal.question.dao;

public class Comments
{
	private String comment;
	private String userId;
	private String date;
	
	public Comments() 
	{
		
	}

	public Comments(String comment, String userId, String string) {
		this.comment = comment;
		this.userId = userId;
		this.date = string;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Comments [comment=" + comment + ", userId=" + userId + ", Date=" + date + "]";
	}
	
	
	
}
