package com.portal.question.dao;

import javax.persistence.Column;

public class Topics 
{
	@Column(name = "topic")
	private String topic;
	
	public Topics() 
	{
		
	}

	public Topics(String topic) {
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "Topics [topic=" + topic + "]";
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
}
