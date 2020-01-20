package com.portal.question.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.ObjectBuffer;
import com.portal.question.model.QuestionLike;
import com.portal.question.model.Questions;
import com.portal.question.model.Users;
import com.portal.question.service.MasterDataService;
import com.portal.question.service.QuestionService;

@RestController
@RequestMapping("/question")
public class QuestionRestController {

	private QuestionService questionService;
	private MasterDataService masterDataService;
	
	@Autowired
	public QuestionRestController(QuestionService theEmployeeService,MasterDataService theMasterDataService ) {
		questionService = theEmployeeService;
		masterDataService = theMasterDataService;
	}
	
	
	//@GetMapping("/result")
	

	
	@PostMapping("/like")
	public QuestionLike likeQuestion(@RequestBody QuestionLike questionLike) 
	{
		String exception = "";
		Users theUser = masterDataService.findUserById(questionLike.getUserId());
		Questions theQuestion = questionService.findQuestionById(questionLike.getQuestionId());
		
		if(questionLike.getQuestionId()==""||questionLike.getUserId()=="")
			exception="Both IDs are required.";
		
		if((theUser==null||theQuestion==null)&&(questionLike.getUserId()!=""||questionLike.getQuestionId()!=""))
			exception+="Please check provided IDs for their availability in database.";
		
		if(exception!="")
			throw new RuntimeException(exception);
		
		return questionService.saveQuestionLiked(questionLike);
	}
		
	
	@PostMapping("/main")
	public String getQuestionDetails( @RequestBody QuestionBuffer questionBuffer)
	{
		if((questionBuffer.getSubTopicId()==null||questionBuffer.getSubTopicId()=="")
			||(questionBuffer.getUserId()==null||questionBuffer.getUserId()=="")
			||(questionBuffer.getQuestion()==null||questionBuffer.getQuestion()==""))
		{
			throw new RuntimeException("Sub-topic ID, use ID or Question required!");
		}
		
		if(questionBuffer.getQuestion().length()<50 && questionBuffer.getQuestion().length()>500)
		{
			throw new RuntimeException("Characters in question must be under the range of 50 to 500 characters!");
		}
		return questionService.saveQuestion(questionBuffer);
		
	}
	
	
	
}










