package com.portal.question.cruddemo.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "topics")
public class Topics 
{
	@Id
	@Column(name = "topic")
	private String topic;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "topic_name")
	private List<SubTopic> subTopic;
	
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
