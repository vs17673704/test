package com.portal.question.cruddemo.dao.masterdata;

import java.util.List;

import com.portal.question.cruddemo.model.Company;
import com.portal.question.cruddemo.model.Employee;
import com.portal.question.cruddemo.model.SubTopic;
import com.portal.question.cruddemo.model.Tags;

public interface SubTopicCrudDAO {
	
	public List<SubTopic> findAll();
	
	public SubTopic save(SubTopic subTopic);
	
	public SubTopic update(SubTopic subTopic);
	
	public String deleteById(String subTopic);
	
	public SubTopic findById(String subTopicId);
	
	
}
