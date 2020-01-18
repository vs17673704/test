package com.portal.question.cruddemo.dao.masterdata;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.portal.question.cruddemo.dao.EmployeeDAO;
import com.portal.question.cruddemo.model.Company;
import com.portal.question.cruddemo.model.Employee;
import com.portal.question.cruddemo.model.SubTopic;
import com.portal.question.cruddemo.model.Tags;

@Repository
public class SubTopicCrudDAOHibernateImpl implements SubTopicCrudDAO {

	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public SubTopicCrudDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public List<SubTopic> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubTopic save(SubTopic subTopic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubTopic update(SubTopic subTopic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String deleteById(String subTopic) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SubTopic findById(String subTopicId) {
		// TODO Auto-generated method stub
		return null;
	}
	


}







