package com.portal.question.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;

import com.portal.question.model.AnswerComments;
import com.portal.question.model.AnswerLike;
import com.portal.question.model.Answers;
import com.portal.question.model.Company;
import com.portal.question.model.QuestionCompanyMapping;
import com.portal.question.model.QuestionLike;
import com.portal.question.model.QuestionTag;
import com.portal.question.model.Questions;
import com.portal.question.model.SubTopic;
import com.portal.question.model.Topics;
import com.portal.question.model.Users;

public class TestJdbc 
{
	public static void main(String args[])
	{
		
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Topics.class)
				.addAnnotatedClass(SubTopic.class)
				.addAnnotatedClass(QuestionLike.class)
				.addAnnotatedClass(Questions.class)
				.addAnnotatedClass(QuestionCompanyMapping.class)
				.addAnnotatedClass(Company.class)
				.addAnnotatedClass(Answers.class)
				.addAnnotatedClass(AnswerLike.class)
				.addAnnotatedClass(AnswerComments.class)
				.addAnnotatedClass(Users.class)
				.addAnnotatedClass(QuestionTag.class)
				.buildSessionFactory();

				SortedMap qid_map = new TreeMap();
				Map question_map = new HashMap();
				Map company_map = new HashMap();
				Map qlike_map = new HashMap();
				Map answer_map = new HashMap();
				Map tag_map = new HashMap();
				
				List qid_list = new ArrayList();
				Set question_list = new HashSet();
				Set company_list = new HashSet();
				Set qlike_list = new HashSet();
				String answer;
				Set tag_list = new HashSet();
				
				
				// create session
				Session session = factory.getCurrentSession();
				
				try {			
					
					session.beginTransaction();
				
					//String tempqid = "UID226";
					
					Query query = session.createNativeQuery("SELECT DISTINCT q.qid, q.question, cp.company_name, qt.tag  FROM  questions q, question_tag qt, "+
						      " question_company_mapping qc, question_like ql, company cp, subtopic st WHERE q.qid = qt.qid and qt.qid = qc.qid "+
						      " and qc.company_id=cp.company_id AND q.subtopic_id=st.subtopic_id ORDER BY q.qid");
					
					List<Object[]> list = query.list();
					
					
					String tempqid = (String) (Arrays.asList(list.get(0))).get(0);
					for(Object[] arr : list)
					{
						
						if(!tempqid.equals(arr[0]))
						{
							
							tempqid = (String) arr[0];
							System.out.println(tempqid);
						}
						
					}
					
//					Object query = session.createNativeQuery("SELECT a.answer FROM answers a " + 
//							"INNER JOIN (SELECT aid, COUNT(aid) AS alc FROM answer_like " + 
//							"WHERE aid IN (SELECT aid FROM answers WHERE qid=\""+ tempqid +"\") GROUP BY aid) AS AL " + 
//							" ON a.aid=AL.aid HAVING MAX(AL.alc)").uniqueResult();
					
//					List<Object[]> qlist = query.getResultList();
//					String question = (String) (Arrays.asList(qlist.get(0))).get(0);

//					
					//System.out.println(query);
//					for(Object[] arr : qlist)
//					{
//						
//						System.out.println(arr[0]+" : "+arr[1]+" : "+arr[2]);
//					}
					// commit transaction
					session.getTransaction().commit();
					
					System.out.println("Done!");
				}
				finally {
				
					// add clean up code
					session.close();
					
					factory.close();
				}
	}
}
