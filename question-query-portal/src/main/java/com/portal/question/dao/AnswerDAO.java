package com.portal.question.dao;

import com.portal.question.model.AnswerLike;
import com.portal.question.model.Answers;

public interface AnswerDAO {

	public Answers findAnswerById(String answerId);

	public AnswerLike likeAnswer(AnswerLike answerLike);

	public String save(Answers answer);	
	
}
