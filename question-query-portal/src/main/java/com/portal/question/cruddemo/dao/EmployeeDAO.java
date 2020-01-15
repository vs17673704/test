package com.portal.question.cruddemo.dao;

import java.util.List;

import com.portal.question.cruddemo.entity.Employee;
import com.portal.question.cruddemo.entity.Tags;

public interface EmployeeDAO {
	
	public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public void save(Employee theEmployee);
	
	public void deleteById(int theId);

	public void save(Tags tag);
	
}
