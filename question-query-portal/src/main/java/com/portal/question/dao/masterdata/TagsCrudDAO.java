package com.portal.question.dao.masterdata;

import java.util.List;

import com.portal.question.model.Company;
import com.portal.question.model.Employee;
import com.portal.question.model.Tags;

public interface TagsCrudDAO {
	
	public List<Tags> findAll();
	
	public String delete(String tag);

	public List<String> save(Tags tags);
	
}
