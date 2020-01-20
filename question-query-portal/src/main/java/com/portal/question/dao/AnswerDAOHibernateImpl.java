package com.portal.question.dao;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.portal.question.model.AnswerLike;
import com.portal.question.model.Answers;

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

}







