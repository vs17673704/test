package com.portal.question.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portal.question.dao.AnswerDAO;
import com.portal.question.model.AnswerLike;
import com.portal.question.model.Answers;

@Service
public class AnswererviceImpl implements AnswerService {

	private AnswerDAO answerDAO;
	
	@Autowired
	public AnswererviceImpl(AnswerDAO theAnswerDAO) {
		answerDAO = theAnswerDAO;
	}
	
	@Override 
	@Transactional	
	public Answers findAnswerById(String answerId)	
	{	
		return answerDAO.findAnswerById(answerId);	
	}

	@Override 
	@Transactional	
	public AnswerLike saveAnswerLiked(AnswerLike questionLike) 		
	{	
		return answerDAO.likeAnswer(questionLike);	
	}
	
}






