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
					
					String quesLike = "";
					
					Query query1 = session.createNativeQuery("(SELECT qid FROM question_like GROUP BY qid having COUNT(qid)>?)");
					query1.setParameter(1, 1);
					List<String> q1l = query1.list();
					System.out.println(q1l.get(q1l.size()-1));
					
					quesLike="and q.qid in (";
					
					for(String s:q1l )
					{
						if(s.equals(q1l.get(q1l.size()-1)))
						{
							quesLike=quesLike+"\""+s+"\""+")";
						}
						else
						{
							quesLike=quesLike+"\""+s+"\",";
						}
					}
					
					List<String> companyList = new ArrayList<>();
					companyList.add("COMPANY 1");
					companyList.add("Company 2");
					
					String compList="and cp.company_name in (";
					
					for(String s:companyList )
					{
						if(s.equals(companyList.get(companyList.size()-1)))
						{
							compList+="\""+s+"\""+")";
						}
						else
						{
							compList+="\""+s+"\",";
						}
					}
					
					String ans = (String) session.createNativeQuery("select A.answer from questionportal.answers a " + 
							"inner join (select aid,count(aid) as alc from questionportal.answer_like " + 
							"where aid in (select aid from questionportal.answers where qid=\"QID1\") group by aid) as AL " + 
							" on a.aid=AL.aid having max(AL.alc)").uniqueResult();
					System.out.println(ans);
					
					
					
					System.out.println(quesLike);
					
					Query query = session.createNativeQuery("SELECT DISTINCT q.qid, q.question, cp.company_name, qt.tag  FROM "+ 
															" questions q, question_tag qt, question_company_mapping qc, question_like ql, company cp, subtopic st "+
															" WHERE q.qid = qt.qid and qt.qid = qc.qid and qc.qid = ql.qid and qc.company_id=cp.company_id AND "+
															" q.subtopic_id=st.subtopic_id "+ quesLike+" "+ compList + " ORDER BY q.qid");
					
					//Query query = session.createQuery(" select questionId,question,userId from Questions ");
					
					List<Object[]> list = query.getResultList();
					//List templist = Arrays.asList(list.get(0));
					String tempqid = (String) (Arrays.asList(list.get(0))).get(0);
					for(Object[] arr : list){
						//System.out.println(Arrays.toString(arr));
							if(!tempqid.equals(arr[0]))
							{
								answer = (String) session.createNativeQuery("select A.answer from questionportal.answers a " + 
										"inner join (select aid,count(aid) as alc from questionportal.answer_like " + 
										"where aid in (select aid from questionportal.answers where qid=\""+ tempqid +"\") group by aid) as AL " + 
										" on a.aid=AL.aid having max(AL.alc)").uniqueResult();
								question_map.put("Question",question_list);
								qid_list.add(question_map);
								answer_map.put("Answer",answer );
								qid_list.add(answer_map);
								company_map.put("Companies", company_list);
								qid_list.add(company_map);
								tag_map.put("Tags", tag_list);
								qid_list.add(tag_map);
								
								qid_map.put(tempqid,qid_list);
								tempqid=(String) arr[0];
								
								question_list.clear();
								company_list.clear();
								tag_list.clear();
								
								qid_list.clear();
								question_map.clear();
								company_map.clear();
								answer_map.clear();
								tag_map.clear();
							}
							
							question_list.add(arr[1]);
							company_list.add(arr[2]);
							tag_list.add(arr[3]);
							
								//System.out.println(arr[0]+" : "+arr[1]+" : "+arr[2]+" : "+arr[3]+" : "+arr[4]);
												
					}
					
					answer = (String) session.createNativeQuery("select A.answer from questionportal.answers a " + 
							"inner join (select aid,count(aid) as alc from questionportal.answer_like " + 
							"where aid in (select aid from questionportal.answers where qid=\""+ tempqid +"\") group by aid) as AL " + 
							" on a.aid=AL.aid having max(AL.alc)").uniqueResult();
					
					question_map.put("Question",question_list);
					qid_list.add(question_map);
					answer_map.put("Answer",answer );
					qid_list.add(answer_map);
					company_map.put("Companies", company_list);
					qid_list.add(company_map);
					tag_map.put("Tags", tag_list);
					qid_list.add(tag_map);
					
					qid_map.put(tempqid,qid_list);
					
					System.out.println(qid_map);
					
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
