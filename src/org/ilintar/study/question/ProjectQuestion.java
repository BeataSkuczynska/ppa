package org.ilintar.study.question;

import org.ilintar.study.question.event.QuestionAnsweredEventNotifier;

import javafx.scene.Node;

public interface ProjectQuestion extends QuestionAnsweredEventNotifier {

	public Node getRenderedQuestion();
	public String getId();
	
}
