package com.portal.question.service;

import java.util.List;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;

import com.portal.question.model.QuestionLike;
import com.portal.question.model.Questions;
import com.portal.question.rest.QuestionBuffer;

public interface QuestionService {

	public Questions findQuestionById(String questionId);

	public QuestionLike saveQuestionLiked(QuestionLike questionLike);

	public String saveQuestion(QuestionBuffer questionBuffer);
	
	
}
