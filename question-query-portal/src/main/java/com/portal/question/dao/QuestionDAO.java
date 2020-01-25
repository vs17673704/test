package com.portal.question.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.portal.question.model.QuestionLike;
import com.portal.question.model.Questions;
import com.portal.question.rest.QuestionBuffer;

public interface QuestionDAO {

	public Questions findQuestionById(String questionId);

	public QuestionLike likeQuestion(QuestionLike questionLike);

	public String save(QuestionBuffer questionBuffer);

	public Map<String, List<Map>> getRestults(List<String> company, List<String> subtopic, List<String> tag, Integer like, String date);
	
	
	
}
