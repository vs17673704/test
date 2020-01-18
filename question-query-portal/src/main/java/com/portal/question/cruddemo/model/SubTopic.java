package com.portal.question.cruddemo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subtopic")
public class SubTopic 
{
	@Id
	@Column(name = "subtopic_id")
	private String subtopicId;
	@Column(name = "subtopic")
	private String subtopic;
	@Column(name = "topic_name")
	private String topic;
	
	public SubTopic() 
	{
		
	}

	public SubTopic(String subtopicId, String subtopic, String topicName) {
		this.subtopicId = subtopicId;
		this.subtopic = subtopic;
		this.topic = topicName;
	}

	@Override
	public String toString() {
		return "Subtopic [subtopicId=" + subtopicId + ", subtopic=" + subtopic + ", topic="  + "]";
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
