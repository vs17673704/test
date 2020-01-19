package com.portal.question.rest.masterdata;

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

import com.portal.question.model.Company;
import com.portal.question.model.Employee;
import com.portal.question.model.Tags;
import com.portal.question.service.MasterDataService;
import com.portal.question.service.QuestionService;

@RestController
@RequestMapping("/masterdata")
public class TagsCRUDRestController 
{

	private MasterDataService masterDataService;
	
	@Autowired
	public TagsCRUDRestController(MasterDataService theMasterDataService) 
	{
		masterDataService = theMasterDataService;
	}
	
	@GetMapping("/tags")
	public List<Tags> findAll() 
	{
		return masterDataService.findAllTag();
	}
	
	
	@PostMapping("/tags")
	public List<String> saveTags(@RequestBody Tags tags) 
	{
		return masterDataService.saveTag(tags);	
	}
	
	
	@DeleteMapping("/tagss/{tag}")
	public String deleteTag(@PathVariable String tag) 
	{
		return "Deleted tag id - " + tag;
	}
	
}










