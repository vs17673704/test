package com.portal.question.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portal.question.dao.EmployeeDAO;
import com.portal.question.model.Company;
import com.portal.question.model.Employee;

@Service
public class AnswererviceImpl implements AnswerService {

	private EmployeeDAO employeeDAO;
	
	@Autowired
	public AnswererviceImpl(EmployeeDAO theEmployeeDAO) {
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






