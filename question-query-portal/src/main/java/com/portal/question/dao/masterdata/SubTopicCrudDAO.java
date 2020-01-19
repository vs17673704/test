package com.portal.question.dao.masterdata;

import java.util.List;

import com.portal.question.model.Company;
import com.portal.question.model.Employee;
import com.portal.question.model.SubTopic;
import com.portal.question.model.Tags;

public interface SubTopicCrudDAO {
	
	public List<SubTopic> findAll();
	
	public SubTopic save(SubTopic subTopic);
	
	public String deleteById(String subTopic);
	
	public SubTopic findById(String subTopicId);
	
	
}
