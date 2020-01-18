package com.portal.question.cruddemo.model;

import javax.persistence.Column;
import javax.persistence.Id;

public class QuestionCompanyMapping 
{
	@Id
	@Column(name = "qid")
	private String questionId;
	@Column(name = "company_id")
	private String companyId;
	
	public QuestionCompanyMapping() 
	{
		
	}

	public QuestionCompanyMapping(String questionId, String companyId) {
		this.questionId = questionId;
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "QuestionCompanyMapping [questionId=" + questionId + ", companyId=" + companyId + "]";
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	
}
