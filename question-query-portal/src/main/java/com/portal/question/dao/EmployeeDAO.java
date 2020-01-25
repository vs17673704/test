package com.portal.question.dao;

import java.util.List;

import com.portal.question.model.Company;
import com.portal.question.model.Employee;

public interface EmployeeDAO {
	
	public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public void save(Employee theEmployee);
	
	public void deleteById(int theId);

	public void save(List<String> tag);
	
	public void saveCompany(Company cmopanyDetails);
	
}
