package org.ilintar.study.question;

import javafx.scene.Node;
import javafx.scene.control.ToggleGroup;
import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;

import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class Question extends ProjectQuestion {

    public Question(Node renderedQuestion, String id, ToggleGroup group){
        super(renderedQuestion, id, group);
    }

    public void addQuestionAnsweredListener(QuestionAnsweredEventListener listener){
        listeners.add(listener);
    }
	
//	VBox answers;
//	String questionText;
//	String questionID;
//	RadioButton finishButton;
//	QuestionAnsweredEventListener [] listeners;
//
//	protected Question(VBox answers, String questionText, String questionID, RadioButton finishButton) {
//		this.answers = answers;
//		this.questionText = questionText;
//		this.questionID = questionID;
//		this.finishButton = finishButton;
//	}
//
//	@Override
//	public void addQuestionAnsweredListener(QuestionAnsweredEventListener listener) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void removeQuestionAnsweredListener(QuestionAnsweredEventListener listener) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
////	!!!! jak tutaj utworzy� ten Node? jak go doda� do drzewa, �eby by�
//	public javafx.scene.Node getRenderedQuestion() {
//		AnchorPane RenderedQuestion = new AnchorPane();
//		RenderedQuestion.getChildren().add(answers);
//		return RenderedQuestion;
//	}
//
//	@Override
//	public String getId() {
//		return questionID;
//	}
//
//	@Override
//	public QuestionAnsweredEvent fireEvent() {
//		// tutaj nast�puje zapisanie odpowiedzi i przej�cie do nast�pnego okna
//		return null;
//	}

}
