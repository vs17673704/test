package com.portal.question.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.portal.question.model.AnswerLike;
import com.portal.question.model.Answers;
import com.portal.question.model.Questions;
import com.portal.question.model.Tags;

@Repository
public class AnswerDAOHibernateImpl implements AnswerDAO {

	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public AnswerDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Override
	public Answers findAnswerById(String answerId) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);			
		Answers theAnswer 	=	currentSession.get(Answers.class, answerId);
		return theAnswer;
	}

	@Override
	public AnswerLike likeAnswer(AnswerLike answerLike) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(answerLike);
		return answerLike;
	}
	
	@Override
	public String save(Answers answer) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);
		//String QID = ("Q"+(new SimpleDateFormat("mmddhh")).format(new Date()));  
		String AID = answer.getUserId()+(new SimpleDateFormat("dd")).format(new Date());  
		//java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
		
		currentSession.saveOrUpdate(new Answers(AID, answer.getAnswer(), answer.getUserId(), answer.getQuestionId()));
		  
		
		return AID;
	}

}







