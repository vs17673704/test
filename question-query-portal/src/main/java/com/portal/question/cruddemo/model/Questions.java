package com.portal.question.cruddemo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "questions")
public class Questions {
		
			@Id
			@Column(name = "qid")
			private String qustionId;
			@Column(name = "question")
			private String question;
			@Column(name = "date")
			private Date date;
			@Column(name = "company_id")
			private String companyId;
			@Column(name = "subtopic_id")
			private String subtopicId;
			@Column(name = "tag")
			private String tags;
			@Column(name = "user_id")
			private String userId;
			
			@Override
			public String toString() {
				return "Questions [qustionId=" + qustionId + ", question=" + question + ", date=" + date
						+ ", companyId=" + companyId + ", subtopicId=" + subtopicId + ", tags=" + tags + ", userId="
						+ userId + "]";
			}
			
			public Questions() {
				// TODO Auto-generated constructor stub
			}

			public Questions(String qustionId, String question, Date date, String companyId, String subtopicId, String tags, String userId) {
				this.qustionId = qustionId;
				this.question = question;
				this.date = date;
				this.companyId = companyId;
				this.subtopicId = subtopicId;
				this.tags = tags;
				this.userId = userId;
			}

			public String getQustionId() {
				return qustionId;
			}

			public void setQustionId(String qustionId) {
				this.qustionId = qustionId;
			}

			public String getQuestion() {
				return question;
			}

			public void setQuestion(String question) {
				this.question = question;
			}

			public Date getDate() {
				return date;
			}

			public void setDate(Date date) {
				this.date = date;
			}

			public String getCompanyId() {
				return companyId;
			}

			public void setCompanyId(String companyId) {
				this.companyId = companyId;
			}

			public String getSubtopicId() {
				return subtopicId;
			}

			public void setSubtopicId(String subtopicId) {
				this.subtopicId = subtopicId;
			}

			public String getTags() {
				return tags;
			}

			public void setTags(String tags) {
				this.tags = tags;
			}

			public String getUserId() {
				return userId;
			}

			public void setUserId(String userId) {
				this.userId = userId;
			}
			
}
