package com.portal.question.cruddemo.dao.masterdata;

import java.util.List;

import com.portal.question.cruddemo.model.Company;
import com.portal.question.cruddemo.model.Employee;
import com.portal.question.cruddemo.model.Tags;
import com.portal.question.cruddemo.model.Topics;

public interface TopicsCrudDAO {
	
	public List<Topics> findAll();
	
	public String delete(String topic);

	public List<String> save(Topics topics);

	public Topics findTopic(String topic);
	
}
