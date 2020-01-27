package com.portal.question.dao.masterdata;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.portal.question.model.Topics;

@Repository
public class TopicsCrudDAOHibernateImpl implements TopicsCrudDAO {

	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public TopicsCrudDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Topics> findAll() 
	{
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Topics> theQuery =	currentSession.createQuery("FROM Topics", Topics.class);
		List<Topics> topics= theQuery.getResultList();
		return topics;
	}
	
	@Override
	public Topics findTopic(String topic) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);	
		Topics foundTopic 	=	currentSession.get(Topics.class, topic);
		return foundTopic;
	}

	@Override
	public List<String> save(Topics topics) 
	{
		Session currentSession = entityManager.unwrap(Session.class);
		String st = topics.getTopic();
		String[] elements = st.split(",");
		List<String> listOfTopics = Arrays.asList(elements);
		System.out.println(listOfTopics);
		
		for(String ls:listOfTopics)
		{
			currentSession.saveOrUpdate(new Topics(ls));
		}
		
		return listOfTopics;
	}
	
	


}







