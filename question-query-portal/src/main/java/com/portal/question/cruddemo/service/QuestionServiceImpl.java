package com.portal.question.cruddemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portal.question.cruddemo.dao.EmployeeDAO;
import com.portal.question.cruddemo.model.Company;
import com.portal.question.cruddemo.model.Employee;

@Service
public class QuestionServiceImpl implements QuestionService {

	private EmployeeDAO employeeDAO;
	
	@Autowired
	public QuestionServiceImpl(EmployeeDAO theEmployeeDAO) {
		employeeDAO = theEmployeeDAO;
	}
	
	@Override
	@Transactional
	public List<Employee> findAll() {
		return employeeDAO.findAll();
	}

	@Override
	@Transactional
	public Employee findById(int theId) {
		return employeeDAO.findById(theId);
	}

	@Override
	@Transactional
	public void save(Employee theEmployee) {
		employeeDAO.save(theEmployee);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		employeeDAO.deleteById(theId);
	}

	@Override
	@Transactional
	public void saveTag(List<String> tag) {
		employeeDAO.save(tag);
	}

	@Override
	@Transactional
	public void saveCompany(Company companyDetails) 
	{
		employeeDAO.saveCompany(companyDetails);
	}

	@Override
	public Company findById1(String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee findById(String employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(String employeeId) {
		// TODO Auto-generated method stub
		
	}
}






