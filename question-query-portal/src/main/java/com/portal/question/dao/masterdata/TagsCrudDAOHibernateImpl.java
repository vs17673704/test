package com.portal.question.dao.masterdata;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.portal.question.model.Tags;

@Repository
public class TagsCrudDAOHibernateImpl implements TagsCrudDAO {

	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public TagsCrudDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<Tags> findAll() 
	{
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Tags> theQuery =	currentSession.createQuery("from Tags", Tags.class);
		List<Tags> tags= theQuery.getResultList();
		return tags;
	}

	@Override
	public String delete(String tag) 
	{
		Session currentSession = entityManager.unwrap(Session.class);		
		@SuppressWarnings("rawtypes")
		Query theQuery = 	currentSession.createQuery("delete from Tags where tag=:tag");
		theQuery.setParameter("tag", tag);
		theQuery.executeUpdate();
		return "Deleted tag:" + tag;
	}

	@Override
	public List<String> save(Tags tags) 
	{
		Session currentSession = entityManager.unwrap(Session.class);
		String st = tags.getTag();
		String[] elements = st.split(",");
		List<String> listOfTags = Arrays.asList(elements);
		System.out.println(listOfTags);
		
		for(String ls:listOfTags)
		{
			currentSession.saveOrUpdate(new Tags(ls));
		}
		
		return listOfTags;
	}

	

}







