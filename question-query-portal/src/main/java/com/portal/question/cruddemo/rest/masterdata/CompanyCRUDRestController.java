package com.portal.question.cruddemo.rest.masterdata;

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
import com.portal.question.cruddemo.service.MasterDataService;
import com.portal.question.cruddemo.service.QuestionService;

@RestController
@RequestMapping("/masterdata")
public class CompanyCRUDRestController 
{

	private MasterDataService masterDataService;
	
	@Autowired
	public CompanyCRUDRestController(MasterDataService theMasterDataService) 
	{
		masterDataService = theMasterDataService;
	}
	
	
	@GetMapping("/companies")
	public List<Company> findAll() 
	{
		return masterDataService.findAllCompanies();
	}
	
	
	
	@PostMapping("/companies")
	public Company addEmployee(@RequestBody Company company) 
	{
		if(company.getCompanyId()!=""||company.getCompanyName()!="")
			return masterDataService.saveCompany(company);
		else
			throw new RuntimeException("All paramenters not entered");		
	}
	

	@PutMapping("/companies")
	public Company updateEmployee(@RequestBody Company company) 
	{
		masterDataService.saveCompany(company);	
		return company;
	}
	
	
	@DeleteMapping("/companiess/{companyId}")
	public String deleteCompany(@PathVariable String companyId) 
	{
		Company company = masterDataService.findCompanyById(companyId);
		if (company == null) 
		{
			throw new RuntimeException("Company id not found - " + companyId);
		}	
		masterDataService.deleteCompanyById(companyId);
		return "Deleted employee id - " + companyId;
	}
	
}










