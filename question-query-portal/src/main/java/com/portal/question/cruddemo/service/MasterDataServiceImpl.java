package com.portal.question.cruddemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.portal.question.cruddemo.dao.EmployeeDAO;
import com.portal.question.cruddemo.dao.masterdata.CompanyCrudDAO;
import com.portal.question.cruddemo.dao.masterdata.SubTopicCrudDAO;
import com.portal.question.cruddemo.dao.masterdata.TagsCrudDAO;
import com.portal.question.cruddemo.dao.masterdata.TopicsCrudDAO;
import com.portal.question.cruddemo.model.Company;
import com.portal.question.cruddemo.model.Employee;
import com.portal.question.cruddemo.model.SubTopic;
import com.portal.question.cruddemo.model.Tags;
import com.portal.question.cruddemo.model.Topics;

@Service
public class MasterDataServiceImpl implements MasterDataService 
{

	
	private TagsCrudDAO tagsCrudDAO;
	private CompanyCrudDAO companyCrudDAO;
	private SubTopicCrudDAO subTopicCrudDao;
	private TopicsCrudDAO topicCrudDAO;
	
	
	@Autowired
	public MasterDataServiceImpl(TagsCrudDAO temptagsCrudDAO, CompanyCrudDAO tempcompanyCrudDAO,	SubTopicCrudDAO tempsubTopicCrudDao, TopicsCrudDAO temptopicCrudDAO) 
	{
		tagsCrudDAO = temptagsCrudDAO;
		companyCrudDAO = tempcompanyCrudDAO;
		topicCrudDAO = temptopicCrudDAO;
		subTopicCrudDao = tempsubTopicCrudDao;
	}
	
	
	/*----------------------------------------------------------------------------TAGS----------------------------------------------------------------------------------*/
	@Override	@Transactional	public List<Tags> findAllTag() 					 	{		return tagsCrudDAO.findAll();			}

	@Override	@Transactional	public String deleteTag(String tag) 			 	{		return tagsCrudDAO.delete(tag);			}

	@Override	@Transactional	public List<String> saveTag(Tags tags) 				{		return tagsCrudDAO.save(tags);	}

	
	/*--------------------------------------------------------------------------TOPICS----------------------------------------------------------------------------------*/
	@Override	@Transactional	public List<Topics> findAllTopics() 				{		return topicCrudDAO.findAll();			}

	@Override	@Transactional	public String deleteTopic(String topic) 			{		return topicCrudDAO.delete(topic);		}

	@Override	@Transactional	public List<String> saveTopics(Topics topics) 		{		return topicCrudDAO.save(topics);		}
	
	@Override	@Transactional	public Topics findTopic(String topic) 				{		return topicCrudDAO.findTopic(topic);		}
	
	/*---------------------------------------------------------------------------COPAMAY------------------------------------------------------------------------------*/
	@Override	@Transactional	public List<Company> findAllCompanies() 			{	return companyCrudDAO.findAll();				}

	@Override	@Transactional	public Company findCompanyById(String companyId)	{	return companyCrudDAO.findById(companyId);		}

	@Override	@Transactional	public Company saveCompany(Company company) 		{	return companyCrudDAO.save(company);			}

	@Override	@Transactional	public Company updateCompany(Company company) 		{	return companyCrudDAO.save(company);			}

	@Override	@Transactional	public String deleteCompanyById(String companyId) 	{	return companyCrudDAO.deleteById(companyId);	}
	
	/*------------------------------------------------------------------------------SUBTOPICS--------------------------------------------------------------------------*/
	@Override	@Transactional	public List<SubTopic> findAllSubTopics()				{	return subTopicCrudDao.findAll();				}

	@Override	@Transactional	public SubTopic saveSubTopic(SubTopic subTopic)			{	return subTopicCrudDao.save(subTopic);			}

	@Override	@Transactional	public SubTopic updateSubTopic(SubTopic subTopic)		{	return subTopicCrudDao.update(subTopic);		}

	@Override	@Transactional	public String deleteSubTopicById(String topic)			{	return subTopicCrudDao.deleteById(topic);		}

	@Override	@Transactional	public SubTopic findSubTopicById(String subTopicId) 	{	return subTopicCrudDao.findById(subTopicId);	}
	
	
}






