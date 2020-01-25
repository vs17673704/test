package com.portal.question.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portal.question.model.AnswerComments;
import com.portal.question.model.AnswerLike;
import com.portal.question.model.Answers;
import com.portal.question.model.Users;
import com.portal.question.service.AnswerService;
import com.portal.question.service.MasterDataService;
import com.portal.question.service.QuestionService;

@RestController
@RequestMapping("/answer")
public class AnswerRestController {

	private QuestionService questionService;
	private AnswerService answerService;
	private MasterDataService masterDataService;
	
	@Autowired
	public AnswerRestController(AnswerService theAnswerService, QuestionService theQuestionService, MasterDataService theMasterDataServic) {
		answerService = theAnswerService;
		questionService = theQuestionService;
		masterDataService = theMasterDataServic;
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
	
	
	@PostMapping("/main")
	public String getQuestionDetails( @RequestBody Answers answer)
	{
		String missing = "";
		if((answer.getQuestionId()==null||answer.getQuestionId()=="")
			||(answer.getUserId()==null||answer.getUserId()=="")
			||(answer.getAnswer()==null||answer.getAnswer()==""))
		{
			throw new RuntimeException("Sub-topic ID, user ID or Question required!");
		}
			//System.out.println(companyCrudDAO.findById(questionBuffer.getCompanyId()));
			if(questionService.findQuestionById(answer.getQuestionId())==null)
			{
				missing+="Question";
			}
			
			if(masterDataService.findUserById(answer.getUserId())==null)
			{
				if(missing!="")	missing+="& ";
				missing+=" User ";
			}
			if(missing!="")
			{
				throw new RuntimeException(missing+" does not exist!");
			}
		
			if(answer.getAnswer().length()<50 && answer.getAnswer().length()>500)
		{
			throw new RuntimeException("Characters in question must be under the range of 50 to 500 characters!");
		}

		return answerService.saveAnswer(answer);
	}
	
	@PostMapping("/comment")
	public String getComments( @RequestBody AnswerComments comment)
	{
		String missing = "";
			if((comment.getAnswerId()==null||comment.getAnswerId()=="")
				||(comment.getUserId()==null||comment.getUserId()=="")
				||(comment.getComment()==null||comment.getComment()==""))
			{
				throw new RuntimeException("Answer ID, user ID or comment required!");
			}
			
			if(answerService.findAnswerById(comment.getAnswerId())==null)
			{
				missing+="Answer";
			}
			
			if(masterDataService.findUserById(comment.getUserId())==null)
			{
				if(missing!="")	missing+="& ";
				missing+=" User ";
			}
			if(missing!="")
			{
				throw new RuntimeException(missing+" does not exist!");
			}
		
			if(comment.getComment().length()<50 && comment.getComment().length()>500)
			{
				throw new RuntimeException("Characters in question must be under the range of 50 to 500 characters!");
			}

		return answerService.saveComment(comment);
	}
	
	@SuppressWarnings("rawtypes")
	@GetMapping("/question-search/{qid}")
	public Map getMap(@PathVariable(value = "qid") String questionId)
	{
		if(questionService.findQuestionById(questionId)==null)
		{
			throw new RuntimeException(questionId+" does not exist!");
		}
		
		return answerService.getSearchResult(questionId);
	}
	
	
}










