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
import com.portal.question.model.Users;
import com.portal.question.service.MasterDataService;

@RestController
@RequestMapping("/masterdata")
public class UserCRUDRestController 
{

	private MasterDataService masterDataService;
	
	@Autowired
	public UserCRUDRestController(MasterDataService theMasterDataService) 
	{
		masterDataService = theMasterDataService;
	}
	
	
	@PostMapping("/user")
	public Users addUserDetails(@RequestBody Users userdetails) 
	{
		if(userdetails.getUserId()==""||userdetails.getUserName()=="")
			throw new RuntimeException("All paramenters not entered");	
		else
			return masterDataService.saveUser(userdetails);
				
	}
	

	@PutMapping("/user")
	public Users updateUser(@RequestBody Users userdetails) 
	{
		masterDataService.saveUser(userdetails);	
		return userdetails;
	}
	
	
}










