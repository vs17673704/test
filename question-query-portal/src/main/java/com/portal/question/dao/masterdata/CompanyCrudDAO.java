package com.portal.question.dao.masterdata;

import java.util.List;

import com.portal.question.model.Company;
import com.portal.question.model.Employee;
import com.portal.question.model.Tags;

public interface CompanyCrudDAO {
	
	public List<Company> findAll();
	
	public Company findById(String companyId);
	
	public Company save(Company company);
	
	public String deleteById(String companyId);
	
}
