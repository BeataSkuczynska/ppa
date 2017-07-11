package org.ilintar.study.question;

import java.util.List;


public interface QuestionFactory {
		
	public Question createQuestion(List<String> lines, String questionID) throws ClassNotFoundException;
	
}
