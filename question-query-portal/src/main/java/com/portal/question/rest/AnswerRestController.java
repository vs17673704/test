package com.portal.question.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portal.question.model.AnswerLike;
import com.portal.question.model.Answers;
import com.portal.question.model.QuestionLike;
import com.portal.question.model.Users;
import com.portal.question.service.AnswerService;
import com.portal.question.service.MasterDataService;

@RestController
@RequestMapping("/answer")
public class AnswerRestController {

	private AnswerService answerService;
	private MasterDataService masterDataService;
	
	@Autowired
	public AnswerRestController(AnswerService theAnswerService) {
		answerService = theAnswerService;
	}
	
	@PostMapping("/like")
	public AnswerLike likeQuestion(@RequestBody AnswerLike answerLike) 
	{
		String exception = "";
		Users theUser = masterDataService.findUserById(answerLike.getUserId());
		Answers theAnswer = answerService.findAnswerById(answerLike.getAnswerId());
		
		if(answerLike.getAnswerId()==""||answerLike.getUserId()=="")
			exception="Both IDs are required.";
		
		if((theUser==null||theAnswer==null)&&(answerLike.getUserId()!=""||answerLike.getAnswerId()!=""))
			exception+="Please check provided IDs for their availability in database.";
		
		if(exception!="")
			throw new RuntimeException(exception);
		
		return answerService.saveAnswerLiked(answerLike);
	}
	
	
}










