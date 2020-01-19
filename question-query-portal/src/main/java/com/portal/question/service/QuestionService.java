package com.portal.question.service;

import java.util.List;

import com.portal.question.model.Company;
import com.portal.question.model.Employee;

public interface QuestionService {
	
	public List<Employee> findAll();
	
	public Employee findById(String employeeId);
	
	public void save(Employee theEmployee);
	
	public void deleteById(String employeeId);

	public void saveTag(List<String> listOfTags);
	
	public void saveCompany(Company companyDetails);

	Employee findById(int theId);

	Company findById1(String employeeId);

	void deleteById(int theId);
	
}
