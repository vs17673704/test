package com.portal.question.dao;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tags")
public class Tags 
{
	private String tag;
	
	public Tags() 
	{
	
	}

	public Tags(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "Tags [tag=" + tag + "]";
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
