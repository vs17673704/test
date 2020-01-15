package com.portal.question.dao;

import javax.persistence.Column;

public class QuestionCompanyMapping 
{
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
