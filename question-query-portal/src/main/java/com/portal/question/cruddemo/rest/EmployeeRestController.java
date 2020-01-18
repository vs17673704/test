package com.portal.question.cruddemo.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
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
import com.portal.question.cruddemo.service.QuestionService;

import ch.qos.logback.core.net.SocketConnector.ExceptionHandler;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

	private QuestionService employeeService;
	
	@Autowired
	public EmployeeRestController(QuestionService theEmployeeService) {
		employeeService = theEmployeeService;
	}
	
	// expose "/employees" and return list of employees
	@GetMapping("/employees")
	public List<Employee> findAll() {
		return employeeService.findAll();
	}

	// add mapping for GET /employees/{employeeId}
	
	@GetMapping("/employees/{employeeId}")
	public Employee getEmployee(@PathVariable int employeeId) {
		
		Employee theEmployee = employeeService.findById(employeeId);
		
		if (theEmployee == null) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
		
		return theEmployee;
	}
	
	// add mapping for POST /employees - add new employee
	
	@PostMapping("/employees")
	public Employee addEmployee(@RequestBody Employee theEmployee) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		theEmployee.setId(0);
		
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	@PostMapping("/company")
	public void addEmployee(@RequestBody() Company company) {
		System.out.println(company);
		
		if(company.getCompanyId()!=""&company.getCompanyName()!="")
				employeeService.saveCompany(company);
		else
			throw new RuntimeException("All paramenters not entered");
		
	}
	
	@PostMapping("/tags")
	public void addEmployee(@RequestBody Tags tags) {
		System.out.println(tags);
		
		String st = tags.getTag();
		
		String[] elements = st.split(",");
		
		// step two : convert String array to list of String
		List<String> listOfTags = Arrays.asList(elements);
		System.out.println(listOfTags);
		
		employeeService.saveTag(listOfTags);
		
	}
	
	// add mapping for PUT /employees - update existing employee
	
	@PutMapping("/employees")
	public Employee updateEmployee(@RequestBody Employee theEmployee) {
		
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	// add mapping for DELETE /employees/{employeeId} - delete employee
	
	@DeleteMapping("/employees/{employeeId}")
	public String deleteEmployee(@PathVariable String employeeId) {
		
		Employee tempEmployee = employeeService.findById(employeeId);
		
		// throw exception if null
		
		if (tempEmployee == null) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
		
		employeeService.deleteById(employeeId);
		
		return "Deleted employee id - " + employeeId;
	}
	
}










