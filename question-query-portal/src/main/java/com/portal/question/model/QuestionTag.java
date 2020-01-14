package com.portal.question.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.data.annotation.TypeAlias;

@Entity
@Table(name = "question_tag")
public class QuestionTag 
{
	
	@Column(name = "qid")
	private String questionId;
	@Column(name = "tag")
	private String tag;
	
	public QuestionTag() 
	{
		
	}

	public QuestionTag(String questionId, String tag) {
		this.questionId = questionId;
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "QuestionTag [questionId=" + questionId + ", tag=" + tag + "]";
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
