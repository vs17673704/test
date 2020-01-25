package com.portal.question.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.portal.question.model.AnswerComments;
import com.portal.question.model.AnswerLike;
import com.portal.question.model.Answers;

@Repository
public class AnswerDAOHibernateImpl implements AnswerDAO {

	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public AnswerDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}
	
	@Override
	public Answers findAnswerById(String answerId) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);			
		Answers theAnswer 	=	currentSession.get(Answers.class, answerId);
		return theAnswer;
	}

	@Override
	public AnswerLike likeAnswer(AnswerLike answerLike) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(answerLike);
		return answerLike;
	}
	
	@Override
	public String save(Answers answer) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);
		String AID = "AN" + (new SimpleDateFormat("ddmm")).format(new Date()) + answer.getUserId();  
		currentSession.saveOrUpdate(new Answers(AID, answer.getAnswer(), answer.getUserId(), answer.getQuestionId()));
		return AID;
	}
	
	
	@Override
	public String saveComment(AnswerComments comment) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);
		java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
		String CMID = "CM" +(new SimpleDateFormat("ddmm")).format(new Date()) + comment.getUserId();
		
		currentSession.saveOrUpdate(new AnswerComments(CMID, comment.getComment(), comment.getUserId(), comment.getAnswerId(), date));
		return CMID;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, HashMap<String, List<Object>>> searchResult(String questionId) 
	{
		@SuppressWarnings("rawtypes")
		Map question_map	 					= new LinkedHashMap();
		Map<String, List<Object>> answer_map	= new HashMap<String, List<Object>>();
		Map<String, Object> answer_user 		= new HashMap<String, Object>();
		Map<String, Integer> answer_like 		= new HashMap<String, Integer>();
		
		Set<Object> company_list = new HashSet<Object>();
		Set<Object> tag_list 	 = new HashSet<Object>();
		Set<Object> topic_list 	 = new HashSet<Object>();
		
		List<Object> ans_list   = new ArrayList<Object>();
		List<Comments> comments = new ArrayList<>();
		Integer ans_like        = null;
		
		Session currentSession 	=	entityManager.unwrap(Session.class);
		
		Query<Object[]> query = currentSession.createNativeQuery("SELECT DISTINCT q.question, c.company_name, qt.tag, qs.topic FROM questions q, question_tag qt, " + 
					 " question_company_mapping qc, company c, subtopic qs where q.qid=\"" + questionId + "\"");

		List<Object[]> qlist = query.getResultList();
		String question = (String) (Arrays.asList(qlist.get(0))).get(0);
		
		question_map.put("Question", question);
		
		for(Object[] arr : qlist)
		{
			company_list.add(arr[1]);
			topic_list.add(arr[2]);
			tag_list.add(arr[3]);
		}
		
		
		query   =   currentSession.createNativeQuery("SELECT a.answer,a.user_id, ac.comment, u.user_name, ac.date, a.aid FROM "+
						            " answers a, answer_comment ac, user u WHERE a.aid=ac.aid AND ac.user_id=u.user_id AND a.qid=\""+questionId+"\"");
	
		List<Object[]> list = query.getResultList();
		String tempans = (String) (Arrays.asList(list.get(0))).get(0);
		for(Object[] arr : list)
		{
			if(!tempans.equals(arr[0]))
			{
				
				ans_like = ((BigInteger)currentSession.createNativeQuery("SELECT count(al.aid) FROM answer_like al where al.aid=\""+arr[5]+"\"")
							.uniqueResult()).intValue();
				answer_like.put("Like", ans_like);
				answer_user.put("User ID", arr[1]);
				ans_list.add(answer_like);
				ans_list.add(answer_user);
				ans_list.add(comments);
				answer_map.put(tempans, ans_list);
				tempans = (String)arr[0];
				System.out.println(answer_map);
				comments = new ArrayList<Comments>();
			}
		
		comments.add(new Comments((String)arr[2],(String)arr[3],""+arr[4]));
				
		}
		
		answer_map.put(tempans, ans_list);
		question_map.put("Answer Info",new HashMap<String, List<Object>>(answer_map));
		System.out.println(answer_map);
		
		Integer ques_like = ((BigInteger)currentSession.createNativeQuery("SELECT count(qid) FROM question_like where qid=\""+questionId+"\"")
							.uniqueResult()).intValue();
		
		question_map.put("Company", company_list);
		question_map.put("Topic", topic_list);
		question_map.put("Tag", tag_list);
		question_map.put("Like", ques_like);
				
		return question_map;
	}
	
}







