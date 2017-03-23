package org.ilintar.study.question.event;

public interface QuestionAnsweredEventNotifier {
	
	public void addQuestionAnsweredListener(QuestionAnsweredEventListener listener);
	public void removeQuestionAnsweredListener(QuestionAnsweredEventListener listener);
	public QuestionAnsweredEvent fireEvent();
//	tu dopisz mark metody fireEvent - w klasie Question, która dziedziczy po tym interfejsie,
//	ta metoda ma tworzyć obiekt klasy QuestionAnsweredEvent
//	wszystkie metody, które tu są muszą być zaimplementowane w Question
}
