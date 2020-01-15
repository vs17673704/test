package com.portal.question.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "subtopic")
public class Subtopic 
{
	@Column(name = "subtopic_id")
	private String subtopicId;
	@Column(name = "subtopic")
	private String subtopic;
	@Column(name = "topic")
	private String topic;
	
	public Subtopic() 
	{
		
	}

	public Subtopic(String subtopicId, String subtopic, String topic) {
		this.subtopicId = subtopicId;
		this.subtopic = subtopic;
		this.topic = topic;
	}

	@Override
	public String toString() {
		return "Subtopic [subtopicId=" + subtopicId + ", subtopic=" + subtopic + ", topic=" + topic + "]";
	}

	public String getSubtopicId() {
		return subtopicId;
	}

	public void setSubtopicId(String subtopicId) {
		this.subtopicId = subtopicId;
	}

	public String getSubtopic() {
		return subtopic;
	}

	public void setSubtopic(String subtopic) {
		this.subtopic = subtopic;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
}
