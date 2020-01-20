package com.portal.question.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portal.question.dao.QuestionDAO;
import com.portal.question.model.QuestionLike;
import com.portal.question.model.Questions;
import com.portal.question.rest.QuestionBuffer;

@Service
public class QuestionServiceImpl implements QuestionService 
{

	private QuestionDAO questionDAO;
	;
	
	@Autowired
	public QuestionServiceImpl(QuestionDAO theQuestionDAO) {
		questionDAO = theQuestionDAO;
		
	}

	@Override 
	@Transactional	
	public Questions findQuestionById(String questionId)	
	{	
		return questionDAO.findQuestionById(questionId);	
	}

	@Override 
	@Transactional	
	public QuestionLike saveQuestionLiked(QuestionLike questionLike) 		
	{	
		return questionDAO.likeQuestion(questionLike);	
	}

	@Override
	@Transactional
	public String saveQuestion(QuestionBuffer questionBuffer) 
	{
		return questionDAO.save(questionBuffer);
	}

}






