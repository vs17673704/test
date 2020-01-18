package com.portal.question.cruddemo.dao;

import java.util.List;

import com.portal.question.cruddemo.model.Company;
import com.portal.question.cruddemo.model.Employee;
import com.portal.question.cruddemo.model.Tags;

public interface EmployeeDAO {
	
	public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public void save(Employee theEmployee);
	
	public void deleteById(int theId);

	public void save(List<String> tag);
	
	public void saveCompany(Company cmopanyDetails);
	
}
