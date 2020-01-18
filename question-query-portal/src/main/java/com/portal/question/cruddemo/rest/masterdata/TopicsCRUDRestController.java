package com.portal.question.cruddemo.rest.masterdata;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portal.question.cruddemo.model.Company;
import com.portal.question.cruddemo.model.Employee;
import com.portal.question.cruddemo.model.Tags;
import com.portal.question.cruddemo.model.Topics;
import com.portal.question.cruddemo.service.MasterDataService;
import com.portal.question.cruddemo.service.QuestionService;

@RestController
@RequestMapping("/masterdata")
public class TopicsCRUDRestController 
{

	private MasterDataService masterDataService;
	
	@Autowired
	public TopicsCRUDRestController(MasterDataService theMasterDataService) 
	{
		masterDataService = theMasterDataService;
	}
	
	@GetMapping("/topics")
	public List<Topics> findAll() 
	{
		return masterDataService.findAllTopics();
	}
	
	
	@PostMapping("/topics")
	public List<String> saveTopics(@RequestBody Topics topics) 
	{
		return masterDataService.saveTopics(topics);	
	}
	
	
	@DeleteMapping("/topics/{topic}")
	public String deleteTag(@PathVariable String topic) 
	{
		Topics tempTopic = masterDataService.findTopic(topic);
		if (topic == null) 
		{
			throw new RuntimeException("Topic not found - " + topic);
		}	
		
		return "Deleted topic id - " + tempTopic.getTopic();
	}
	
}










