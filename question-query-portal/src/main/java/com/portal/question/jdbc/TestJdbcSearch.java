package com.portal.question.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import com.portal.question.model.Company;
import com.portal.question.model.QuestionCompanyMapping;
import com.portal.question.model.QuestionLike;
import com.portal.question.model.Questions;
import com.portal.question.model.SubTopic;
import com.portal.question.model.Topics;
import com.portal.question.rest.CommentBuffer;

public class TestJdbcSearch 
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
				.buildSessionFactory();

				SortedMap qid_map = new TreeMap();
				Map question_map = new HashMap();
				Map company_map = new HashMap();
				Map qlike_map = new HashMap();
				Map answer_map = new HashMap();
				Map tag_map = new HashMap();
				String comment = null;
				String comment_user = null;
				String comment_date = null;
				List<CommentBuffer> comments = new ArrayList<>();
				
				List qid_list = new ArrayList();
				List answer_list = new ArrayList();
				List comment_list = new ArrayList();
				Set question_list = new HashSet();
				Set company_list = new HashSet();
				Set qlike_list = new HashSet();
				String answer;
				Set tag_list = new HashSet();
				
				
				// create session
				Session session = factory.getCurrentSession();
				
				try {			
				
					// start a transaction
					session.beginTransaction();
					
					//session.saveOrUpdate(new Topics("T1"));
					
					//session.saveOrUpdate(new SubTopic("varun", "sharma", "T1"));
					
					//Topics topics = session.get(QuestionLike.class, "SPRING");
//					String QID = ("Q"+(new SimpleDateFormat("mmddhh")).format(new Date()));
//					//session.delete(session.get(QuestionLike.class, "Q1"));
//				    long millis=System.currentTimeMillis();  
//				    java.sql.Date date=new java.sql.Date(millis);  
//				    System.out.println(date);  
//					session.saveOrUpdate(new Questions(QID, "QUESTION 1", date, "CID1","STID1", "UID1"));
					
									
					Query query = session.createNativeQuery("SELECT a.answer,a.user_id, ac.comment, ac.user_id AS comm_uid, ac.date FROM "+
															" answers a, answer_comment ac WHERE a.aid=ac.aid AND a.qid=\""+"QID1"+"\"");
					
					
					
					List<Object[]> list = query.getResultList();
					String tempans = (String) (Arrays.asList(list.get(0))).get(0);
					for(Object[] arr : list)
					{
						System.out.println(Arrays.toString(arr));
						if(!tempans.equals(arr[0]))
						{
							answer_map.put(tempans, comments);
							tempans = (String)arr[0];
							System.out.println(answer_map);
							comment_list = new ArrayList();
							comments = new ArrayList<CommentBuffer>();
							
						}
						
					//	comments.add(new Comments((String)arr[2],(String)arr[3],(java.sql.Date) arr[4]));
						
//						comment =      "Comment:"+(String)arr[2];
//						comment_user = "User   :"+(String)arr[3];
//						comment_date = "Date   :"+arr[4];
//						comment_list.add(comment);
//						comment_list.add(comment_user);
//						comment_list.add(comment_date);
//						System.out.println(comment);
//						System.out.println(comment_user);
//						System.out.println(comment_date);
//						System.out.println(comment_list);
						
												
					}
					
					comment_list.add(comment);
					comment_list.add(comment_user);
					comment_list.add(comment_date);
					answer_map.put(tempans, comments);
					System.out.println(answer_map);
					
//					answer = (String) session.createNativeQuery("select A.answer from questionportal.answers a " + 
//							"inner join (select aid,count(aid) as alc from questionportal.answer_like " + 
//							"where aid in (select aid from questionportal.answers where qid=\""+ tempqid +"\") group by aid) as AL " + 
//							" on a.aid=AL.aid having max(AL.alc)").uniqueResult();
//					
//					question_map.put("Question",question_list);
//					qid_list.add(question_map);
//					answer_map.put("Answer",answer );
//					qid_list.add(answer_map);
//					company_map.put("Companies", company_list);
//					qid_list.add(company_map);
//					tag_map.put("Tags", tag_list);
//					qid_list.add(tag_map);
//					
//					qid_map.put(tempqid,qid_list);
//					
//					System.out.println(qid_map);
					
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
