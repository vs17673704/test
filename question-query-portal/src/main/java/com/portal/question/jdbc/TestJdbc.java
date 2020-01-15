package com.portal.question.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJdbc 
{
	public static void main(String args[])
	{
		
		//String jdbcUrl = "jdbc:mysql://localhost:3306/hb-01-one-to-one-uni?useSSL=false";
		//String user = "hbstudent";
		//String pass = "hbstudent";
		
		String jdbcUrl = "jdbc:mysql://remotemysql.com:3306/7eVVspQxnT?useSSL=false";
		String user = "7eVVspQxnT";
		String pass = "4stBxeKgK8";
		
		try
		{
			System.out.println("COnnecting to database ; "+ jdbcUrl);
			Connection myConn = DriverManager.getConnection(jdbcUrl, user, pass);
			System.out.println("Connection Successful!!");
		}
		catch(Exception exc)
		{
			exc.printStackTrace();
		}
	}
}
