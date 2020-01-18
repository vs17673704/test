package com.portal.question.cruddemo.service;

import java.util.List;

import com.portal.question.cruddemo.model.Company;
import com.portal.question.cruddemo.model.SubTopic;
import com.portal.question.cruddemo.model.Tags;
import com.portal.question.cruddemo.model.Topics;

public interface MasterDataService {
	
	/////////////////////////////////////////////////////////////////         TAGS         \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public List<Tags> findAllTag();
	
	public String deleteTag(String tag);

	public List<String> saveTag(Tags listOfTags);
	
	/////////////////////////////////////////////////////////////////        TOPICS        \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public List<Topics> findAllTopics();
	
	public String deleteTopic(String topic);

	public List<String> saveTopics(Topics topic);
	
	public Topics findTopic(String topic);
	
	/////////////////////////////////////////////////////////////////        COMPANY        \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public List<Company> findAllCompanies();
	
	public Company findCompanyById(String companyId);
	
	public Company saveCompany(Company company);
	
	public Company updateCompany(Company company);
	
	public String deleteCompanyById(String companyId);
	
	
	/////////////////////////////////////////////////////////////////       SUB-TOPICS      \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
	public List<SubTopic> findAllSubTopics();
	
	public SubTopic saveSubTopic(SubTopic subTopic);
	
	public SubTopic updateSubTopic(SubTopic subTopic);
	
	public String deleteSubTopicById(String subTopic);
	
	public SubTopic findSubTopicById(String subTopicId);
	
}
