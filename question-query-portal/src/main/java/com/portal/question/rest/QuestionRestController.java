package com.portal.question.rest;

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

import com.portal.question.model.Company;
import com.portal.question.model.Employee;
import com.portal.question.model.Tags;
import com.portal.question.service.QuestionService;

import ch.qos.logback.core.net.SocketConnector.ExceptionHandler;

@RestController
@RequestMapping("/question")
public class QuestionRestController {

	private QuestionService employeeService;
	
	@Autowired
	public QuestionRestController(QuestionService theEmployeeService) {
		employeeService = theEmployeeService;
	}
	
	
	/*@GetMapping("/result")
	public List<Employee> findAll() {
		return employeeService.findAll();
	}*/

	
	@PostMapping("/like")
	public Employee getEmployee(@PathVariable int employeeId) {
		
		Employee theEmployee = employeeService.findById(employeeId);
		
		if (theEmployee == null) {
			throw new RuntimeException("Employee id not found - " + employeeId);
		}
		
		return theEmployee;
	}
	
	// add mapping for POST /employees - add new employee
	
	@PostMapping("/main")
	public Employee addEmployee(@RequestBody Employee theEmployee) {
		
		// also just in case they pass an id in JSON ... set id to 0
		// this is to force a save of new item ... instead of update
		
		theEmployee.setId(0);
		
		employeeService.save(theEmployee);
		
		return theEmployee;
	}
	
	
	
}










