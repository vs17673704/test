package com.portal.question.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;

//import org.hibernate.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.portal.question.model.QuestionLike;
import com.portal.question.model.Questions;
import com.portal.question.model.Tags;
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
		//String QID = ("Q"+(new SimpleDateFormat("mmddhh")).format(new Date()));  
		String QID = questionBuffer.getUserId()+(new SimpleDateFormat("dd")).format(new Date());  
		java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
		
			  currentSession.saveOrUpdate(new Questions(QID,questionBuffer.getQuestion(),date,questionBuffer.getUserId(),
						questionBuffer.getSubTopicId(),questionBuffer.getCompanyId() ));
		  
		if(questionBuffer.getTaglist()!=null)
		{
			for(String tag:questionBuffer.getTaglist())
			{	
				Query query= currentSession.createNativeQuery("INSERT INTO question_tag(qid, tag) VALUES(?,?)")
							 .setParameter(1,QID)
							 .setParameter(2, tag);
				query.executeUpdate();
				currentSession.saveOrUpdate(new Tags(tag));
			}
		}
		return QID;
	}
	
	
	


}







