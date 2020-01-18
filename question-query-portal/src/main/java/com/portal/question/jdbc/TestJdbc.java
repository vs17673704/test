package com.portal.question.jdbc;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.portal.question.cruddemo.model.SubTopic;
import com.portal.question.cruddemo.model.Topics;
import com.portal.question.dao.Subtopic;

public class TestJdbc 
{
	public static void main(String args[])
	{
		
		SessionFactory factory = new Configuration()
				.configure("hibernate.cfg.xml")
				.addAnnotatedClass(Topics.class)
				.addAnnotatedClass(SubTopic.class)
				.buildSessionFactory();

				// create session
				Session session = factory.getCurrentSession();
				
				try {			
				
					// start a transaction
					session.beginTransaction();
					
					//session.saveOrUpdate(new Topics("T1"));
					
					//session.saveOrUpdate(new SubTopic("ST4", "SUB-TOPIC 4", "T2"));
					
					//Topics topics = session.get(Topics.class, "SPRING");
					
					session.delete(session.get(Topics.class, "T1"));
					
					
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
