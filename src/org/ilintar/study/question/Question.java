package org.ilintar.study.question;

import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Question implements ProjectQuestion {
	
	
	VBox answers;
	String questionText;
	String questionID;
	RadioButton finishButton;
	QuestionAnsweredEventListener [] listeners;

	protected Question(VBox answers, String questionText, String questionID, RadioButton finishButton) {
		this.answers = answers;
		this.questionText = questionText;
		this.questionID = questionID;
		this.finishButton = finishButton;
	}

	@Override
	public void addQuestionAnsweredListener(QuestionAnsweredEventListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeQuestionAnsweredListener(QuestionAnsweredEventListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
//	!!!! jak tutaj utworzyæ ten Node? jak go dodaæ do drzewa, ¿eby by³ 
	public javafx.scene.Node getRenderedQuestion() {
		AnchorPane RenderedQuestion = new AnchorPane();
		RenderedQuestion.getChildren().add(answers);
		return RenderedQuestion;
	}

	@Override
	public String getId() {
		return questionID;
	}

	@Override
	public QuestionAnsweredEvent fireEvent() {
		// tutaj nastêpuje zapisanie odpowiedzi i przejœcie do nastêpnego okna
		return null;
	}

}
