package com.portal.question.cruddemo.rest;

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

import com.portal.question.cruddemo.entity.Employee;
import com.portal.question.cruddemo.service.EmployeeService;

@RestController
@RequestMapping("/api")
public class QuestionRestController {

	private EmployeeService employeeService;
	
	@Autowired
	public QuestionRestController(EmployeeService theEmployeeService) {
		employeeService = theEmployeeService;
	}
	
	@GetMapping("/student")
	public String findAll() 
	{
		
		return "SUCCESS";
	}

	
	
}










