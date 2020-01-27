package com.portal.question.dao;

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
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.portal.question.model.AnswerComments;
import com.portal.question.model.AnswerLike;
import com.portal.question.model.Answers;
import com.portal.question.rest.CommentBuffer;

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
	public void likeAnswer(AnswerLike answerLike) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(answerLike);
	}
	
	@Override
	public String save(Answers answer) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);
		String AID = "AN" + (new SimpleDateFormat("ddmm")).format(new Date()) + answer.getUserId();  
		currentSession.saveOrUpdate(new Answers(AID, answer.getAnswer(), answer.getUserId(), answer.getQuestionId()));
		return "Answer ID for submitted answer is: " + AID;
	}
	
	
	@Override
	public String saveComment(AnswerComments comment) 
	{
		Session currentSession 	=	entityManager.unwrap(Session.class);
		java.sql.Date date=new java.sql.Date(System.currentTimeMillis());
		String CMID = "CM" +(new SimpleDateFormat("ddmm")).format(new Date()) + comment.getUserId();
		
		currentSession.saveOrUpdate(new AnswerComments(CMID, comment.getComment(), comment.getUserId(), comment.getAnswerId(), date));
		return "Comment ID for submitted comment is: " + CMID;
	}
	

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public Map<String, HashMap<String, List<Object>>> searchResult(String questionId) 
	{
		@SuppressWarnings("rawtypes")
		Map question_map	 					= new LinkedHashMap();
		Map answer_map							= new HashMap();
		Map<String, Object> answer_user 		= new HashMap<String, Object>();
		Map<String, Long> answer_like 			= new HashMap<>();
		
		Set<Object> company_list = new HashSet<Object>();
		Set<Object> tag_list 	 = new HashSet<Object>();
		Set<Object> topic_list 	 = new HashSet<Object>();
		Set<Object> ans_list 	 = new HashSet<Object>();
		
		List<CommentBuffer> comments = new ArrayList<>();
		long ans_like;
		
		String commentSelect = "", commentFrom = "", commentWhere = "", answer = null;
		
		Session currentSession 	=	entityManager.unwrap(Session.class);
		
		Query<Object[]> query = currentSession.createQuery("SELECT DISTINCT q.question, c.companyName, qt.tag, st.topicName FROM Questions q, QuestionTag qt, " + 
				 										   " QuestionCompanyMapping qc, Company c, SubTopic st WHERE q.questionId = :qid AND qc.questionId = :qid "
				 										   + "AND qt.questionId = :qid AND qc.companyId = c.companyId AND q.subtopicId = st.subtopicId")
				  							  .setParameter("qid", questionId);

		List<Object[]> qlist = query.getResultList();
		String question = (String) (Arrays.asList(qlist.get(0))).get(0);
		
		question_map.put("Question", question);
		
		for(Object[] arr : qlist)
		{
			company_list.add(arr[1]);
			topic_list.add(arr[2]);
			tag_list.add(arr[3]);
		}
		
		query   =   currentSession.createQuery("SELECT DISTINCT a.answer,a.userId, a.answerId, ac.comment, u.userName, ac.date FROM "+
	            " Answers a, AnswerComments ac, Users u WHERE a.questionId= :qid AND a.answerId=ac.answerId AND ac.userId=u.userId ORDER BY a.answerId" )
				.setParameter("qid", questionId);
		
		List<Object[]> list = query.getResultList();
		String tempans = (String) (Arrays.asList(list.get(0))).get(2);
		String tempUser = null;
		for(Object[] arr : list)
		{
			tempUser = (String)arr[1];
			if(!tempans.equals(arr[2]))
			{
				ans_like =	(long) currentSession.createQuery("SELECT COUNT(al.answerId) FROM AnswerLike al WHERE al.answerId = :aid")
												 .setParameter("aid", arr[2])
												 .uniqueResult();
				
				answer_like.put("Like", ans_like);
				answer_user.put("User ID", tempUser);
				ans_list.add(answer_like);
				ans_list.add(answer_user);
				ans_list.add(comments);
				answer_map.put(tempans, ans_list);
				tempans = (String)arr[2];
				comments = new ArrayList<CommentBuffer>();
				
			}
			answer = (String)arr[0];
			comments.add(new CommentBuffer((String)arr[3],(String)arr[4], new SimpleDateFormat("dd-MM-yyyy").format(arr[5])));
			
		}
		
		ans_like = (Long) currentSession.createQuery("SELECT COUNT(al.answerId) FROM AnswerLike al WHERE al.answerId = :aid")
											  .setParameter("aid", tempans)	
											  .uniqueResult();
			
		answer_like.put("Like", ans_like);
		answer_user.put("User ID", tempUser);
		ans_list.add(answer_like);
		ans_list.add(answer_user);
		ans_list.add(comments);
		answer_map.put(answer, ans_list);
		
		question_map.put("Answer Info",new HashMap<String, List<Object>>(answer_map));
		
		Long ques_like = (long)currentSession.createQuery("SELECT COUNT(questionId) FROM QuestionLike WHERE questionId = :qid")
											 .setParameter("qid", questionId)
											 .uniqueResult();
		
		question_map.put("Company", company_list);
		question_map.put("Topic", topic_list);
		question_map.put("Tag", tag_list);
		question_map.put("Like", ques_like);
				
		return question_map;
	}
	
}







