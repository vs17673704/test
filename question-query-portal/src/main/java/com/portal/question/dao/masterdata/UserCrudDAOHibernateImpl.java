package com.portal.question.dao.masterdata;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.portal.question.model.Users;

@Repository
public class UserCrudDAOHibernateImpl implements UserCrudDAO
{
	
	private EntityManager entityManager;
	
	@Autowired
	public UserCrudDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public Users findById(String userId) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);	
		Users theUser 	=	currentSession.get(Users.class, userId);
		return theUser;
	}

	@Override
	public Users save(Users userdetails) 
	{
		Session currentSession = entityManager.unwrap(Session.class);	
		currentSession.saveOrUpdate(userdetails);
		return userdetails;
	}


}







