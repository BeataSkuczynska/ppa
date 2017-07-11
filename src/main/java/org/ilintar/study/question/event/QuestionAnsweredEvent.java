package org.ilintar.study.question.event;

import org.ilintar.study.question.Answer;
import org.ilintar.study.question.ProjectQuestion;

import java.sql.*;

public class QuestionAnsweredEvent {

	protected ProjectQuestion question;
	protected Answer answer;
	
	public QuestionAnsweredEvent(ProjectQuestion question, Answer answer) {
		this.question = question;
		this.answer = answer;
	}

	public ProjectQuestion getQuestion() {
		return question;
	}

	public void setQuestion(ProjectQuestion question) {
		this.question = question;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
	}

	
}
