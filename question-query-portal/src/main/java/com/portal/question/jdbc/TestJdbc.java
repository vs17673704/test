package com.portal.question.jdbc;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

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
				.addAnnotatedClass(SubTopic.class).
				addAnnotatedClass(QuestionLike.class)
				.addAnnotatedClass(Questions.class)
				.buildSessionFactory();

				// create session
				Session session = factory.getCurrentSession();
				
				try {			
				
					// start a transaction
					session.beginTransaction();
					
					//session.saveOrUpdate(new Topics("T1"));
					
					//session.saveOrUpdate(new SubTopic("varun", "sharma", "T1"));
					
					//Topics topics = session.get(QuestionLike.class, "SPRING");
					String QID = ("Q"+(new SimpleDateFormat("mmddhh")).format(new Date()));
					//session.delete(session.get(QuestionLike.class, "Q1"));
				    long millis=System.currentTimeMillis();  
				    java.sql.Date date=new java.sql.Date(millis);  
				    System.out.println(date);  
					session.saveOrUpdate(new Questions(QID, "QUESTION 1", date, "CID1","STID1", "UID1"));
					
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
