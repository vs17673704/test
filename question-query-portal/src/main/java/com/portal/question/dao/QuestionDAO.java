package com.portal.question.dao;

import com.portal.question.model.QuestionLike;
import com.portal.question.model.Questions;
import com.portal.question.rest.QuestionBuffer;

public interface QuestionDAO {

	public Questions findQuestionById(String questionId);

	public QuestionLike likeQuestion(QuestionLike questionLike);

	public String save(QuestionBuffer questionBuffer);
	
	
	
}
