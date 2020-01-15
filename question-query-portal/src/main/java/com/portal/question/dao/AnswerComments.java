package com.portal.question.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "answer_comment")
public class AnswerComments 
{
	@Column(name = "comment_id")
	private String commentId;
	@Column(name = "comment")
	private String comment;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "aid")
	private String answerId;
	
	public AnswerComments() 
	{
		
	}

	public AnswerComments(String commentId, String comment, String userId, String answerId) {
		this.commentId = commentId;
		this.comment = comment;
		this.userId = userId;
		this.answerId = answerId;
	}

	@Override
	public String toString() {
		return "AnswerComments [commentId=" + commentId + ", comment=" + comment + ", userId=" + userId + ", answerId="
				+ answerId + "]";
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
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

	public String getAnswerId() {
		return answerId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	
	
	
}
