package com.portal.question.rest.masterdata;

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

import com.portal.question.model.Company;
import com.portal.question.model.Employee;
import com.portal.question.model.SubTopic;
import com.portal.question.service.MasterDataService;
import com.portal.question.service.QuestionService;

@RestController
@RequestMapping("/masterdata")
public class SubTopicCRUDRestController 
{

	private MasterDataService masterDataService;
	
	@Autowired
	public SubTopicCRUDRestController(MasterDataService theMasterDataService) 
	{
		masterDataService = theMasterDataService;
	}
	
	
	@GetMapping("/subtopic")
	public List<SubTopic> findAll() 
	{
		return masterDataService.findAllSubTopics();
	}
	
	
	
	@PostMapping("/subtopic")
	public SubTopic addSubtopic(@RequestBody SubTopic subTopic) 
	{
		//if(subTopic.getSubtopicId()==""||subTopic.getSubtopic()==""||subTopic.getTopic()=="")
			//throw new RuntimeException("All paramenters not entered");
		//else
			System.out.println(subTopic);
			return masterDataService.saveSubTopic(subTopic);
	}
	

	@PutMapping("/subtopic")
	public SubTopic updateSubTopic(@RequestBody SubTopic subTopic) 
	{
		return masterDataService.saveSubTopic(subTopic);	
		
	}
	
	
	@DeleteMapping("/subtopics/{subtopic}")
	public String deleteSubTopic(@PathVariable String subTopicId) 
	{
		SubTopic tempSubTopic = masterDataService.findSubTopicById(subTopicId);
		if (tempSubTopic == null) 
		{
			throw new RuntimeException("Sub-topic id not found - " + subTopicId);
		}	
		masterDataService.deleteSubTopicById(subTopicId);
		return "Deleted subtopic id - " + subTopicId;
	}
	
}










