package com.portal.question.dao.masterdata;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
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
		System.out.println("INSIDE SAVE USER");
		Session currentSession = entityManager.unwrap(Session.class);	
		currentSession.saveOrUpdate(userdetails);
		return userdetails;
	}

	@Override
	public String deleteById(String userId) 
	{
		Session currentSession = entityManager.unwrap(Session.class);		
		@SuppressWarnings("rawtypes")
		Query theQuery = 	currentSession.createQuery("delete from User where id=:userId");
		theQuery.setParameter("userId", userId);
		theQuery.executeUpdate();
		return "Deleted user id - " + userId;
	}

	/*
	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public UserCrudDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	
	@Override
	public List<Company> findAll() 
	{
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Company> theQuery =	currentSession.createQuery("from Company", Company.class);
		List<Company> company = theQuery.getResultList();
		return company;
	}


	@Override
	public Company findById(String companyId) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);	
		Company theEmployee 	=	currentSession.get(Company.class, companyId);
		return theEmployee;
	}


	@Override
	public Company save(Company company) 
	{
		Session currentSession = entityManager.unwrap(Session.class);	
		currentSession.saveOrUpdate(company);
		return company;
	}


	@Override
	public String deleteById(String companyId) {
		
		Session currentSession = entityManager.unwrap(Session.class);		
		Query theQuery = 	currentSession.createQuery("delete from Company where id=:companyId");
		theQuery.setParameter("companyId", companyId);
		theQuery.executeUpdate();
		return "Deleted employee id - " + companyId;
		
	}
	*/
	
	
	


}







