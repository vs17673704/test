package com.portal.question.cruddemo.dao.masterdata;

import java.util.List;

import com.portal.question.cruddemo.model.Company;
import com.portal.question.cruddemo.model.Employee;
import com.portal.question.cruddemo.model.Tags;

public interface TagsCrudDAO {
	
	public List<Tags> findAll();
	
	public String delete(String tag);

	public List<String> save(Tags tags);
	
}
