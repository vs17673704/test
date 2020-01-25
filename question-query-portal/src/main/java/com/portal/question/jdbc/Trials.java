package com.portal.question.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trials {

	public static void main(String[] args) 
	{
//		SimpleDateFormat formatter = new SimpleDateFormat("dd");
//		Date date = new Date();
//	    System.out.println((new SimpleDateFormat("dd/hh")).format(new Date()));
//		String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
//		System.out.println(s.length());
		
		Map m1 = new HashMap();
		List l = new ArrayList();
		List l1 = new ArrayList();
		List l2 = new ArrayList();
		Map m2 = new HashMap();
		Map m3 = new HashMap();
	      l.add("8");
	      l.add("31");
	      l1.add("20");
	      l1.add("38");
	      m1.put("Zara",l);
	      m3.put("Mahnad", l1);
	      l2.add(m1);
	      l2.add(m3);
	      m2.put("Ayan",l2);
	      m2.put("Daisy", "14");
	      System.out.println();
	      System.out.println(" Map Elements");
	      System.out.print("\t" + m2);
		
	}

}
