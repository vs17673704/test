package com.portal.question.cruddemo.dao.masterdata;

import java.util.List;

import com.portal.question.cruddemo.model.Company;
import com.portal.question.cruddemo.model.Employee;
import com.portal.question.cruddemo.model.Tags;

public interface CompanyCrudDAO {
	
	public List<Company> findAll();
	
	public Company findById(String companyId);
	
	public Company save(Company company);
	
	public String deleteById(String companyId);
	
}
