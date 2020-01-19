package com.portal.question.dao.masterdata;

import java.util.List;

import com.portal.question.model.Company;
import com.portal.question.model.Employee;
import com.portal.question.model.Tags;
import com.portal.question.model.Topics;

public interface TopicsCrudDAO {
	
	public List<Topics> findAll();
	
	public String delete(String topic);

	public List<String> save(Topics topics);

	public Topics findTopic(String topic);
	
}
