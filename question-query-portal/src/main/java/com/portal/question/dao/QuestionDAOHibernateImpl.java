package com.portal.question.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.portal.question.model.QuestionLike;
import com.portal.question.model.QuestionTag;
import com.portal.question.model.Questions;
import com.portal.question.rest.QuestionBuffer;

@Repository
public class QuestionDAOHibernateImpl implements QuestionDAO {

	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public QuestionDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public Questions findQuestionById(String questionId) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);			
		Questions theQuestion 	=	currentSession.get(Questions.class, questionId);
		return theQuestion;
	}

	@Override
	public QuestionLike likeQuestion(QuestionLike questionLike) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(questionLike);
		return questionLike;
	}

	@Override
	public String save(QuestionBuffer questionBuffer) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);
		String QID = ("Q"+(new SimpleDateFormat("mmddhh")).format(new Date()));  
		  java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
		  currentSession.saveOrUpdate(new Questions(QID,questionBuffer.getQuestion(),date,questionBuffer.getUserId(),
				  									questionBuffer.getSubTopicId(),questionBuffer.getCompanyId() ));
		for(String tag:questionBuffer.getTaglist())
		{	System.out.println(questionBuffer.getTaglist());
			QuestionTag qtag = new QuestionTag();
			qtag.setQuestionId(QID);
			qtag.setTag(tag);
			currentSession.saveOrUpdate(qtag);	
		}
		return QID;
	}
	
	
	


}







