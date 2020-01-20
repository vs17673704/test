package com.portal.question.service;

import java.util.List;

import com.portal.question.model.AnswerLike;
import com.portal.question.model.Answers;
import com.portal.question.model.Company;

public interface AnswerService {

	public Answers findAnswerById(String answerId);

	public AnswerLike saveAnswerLiked(AnswerLike questionLike);
	
	
	
}
