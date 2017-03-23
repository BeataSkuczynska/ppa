package org.ilintar.study.question;

import java.util.List;


public interface QuestionFactory {
		
	public ProjectQuestion createQuestion(List<String> lines, String questionID);
	
}
