package com.portal.question.cruddemo.service;

import java.util.List;

import com.portal.question.cruddemo.entity.Tags;
import com.portal.question.cruddemo.entity.Employee;

public interface EmployeeService {
	
	public List<Employee> findAll();
	
	public Employee findById(int theId);
	
	public void save(Employee theEmployee);
	
	public void deleteById(int theId);

	public void saveTag(Tags tag);
	
}
