package com.portal.question.dao.masterdata;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.portal.question.model.Company;

@Repository
public class CompanyCrudDAOHibernateImpl implements CompanyCrudDAO
{

	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public CompanyCrudDAOHibernateImpl(EntityManager theEntityManager) {
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
		Company theCompany 	=	currentSession.get(Company.class, companyId);
		return theCompany;
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
		@SuppressWarnings("rawtypes")
		Query theQuery = 	currentSession.createQuery("DELETE FROM Company WHERE companyId=:companyId");
		theQuery.setParameter("companyId", companyId);
		theQuery.executeUpdate();
		
		theQuery = 	currentSession.createQuery("DELETE FROM QuestionCompanyMapping WHERE companyId=:companyId");
		theQuery.setParameter("companyId", companyId);
		theQuery.executeUpdate();
		
		return "Deleted company id - " + companyId;
		
	}


}







