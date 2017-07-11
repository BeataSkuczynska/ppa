package org.ilintar.study.question;

import javafx.scene.layout.VBox;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;
import org.ilintar.study.question.event.QuestionAnsweredEventNotifier;
import org.ilintar.study.question.event.QuestionAnsweredEvent;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.Node;
import java.util.ArrayList;
import java.util.List;


public abstract class ProjectQuestion implements QuestionAnsweredEventNotifier {

	protected Node renderedQuestion;
	protected String id;
	protected List<QuestionAnsweredEventListener> listeners;
	protected ToggleGroup group;

	public ProjectQuestion(Node renderedQuestion, String id, ToggleGroup group){
		this.renderedQuestion = renderedQuestion;
		this.id = id;
		this.group = group;
		listeners = new ArrayList<>();
	}

	public Node getRenderedQuestion() { return renderedQuestion; }

	public void setRenderedQuestion(Node renderedQuestion) { this.renderedQuestion = renderedQuestion; }

	public String getId() { return id; }

	public void setId(String id){this.id = id;}

	public void removeQuestionAnsweredListener(QuestionAnsweredEventListener listener){
		listeners.remove(listener);
	}

	public void fireEvent() throws ClassNotFoundException {
		QuestionAnsweredEvent event = new QuestionAnsweredEvent(this, getAnswer());
		for (QuestionAnsweredEventListener listener : listeners) {
			listener.handleEvent(event);
		}
	}

	public Answer getAnswer() {
		Toggle data = group.getSelectedToggle();
		Answer answer = new Answer(data == null ? null : (String) data.getUserData());
		return answer;
	}
	
}
