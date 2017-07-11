package org.ilintar.study.question;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class RadioQuestionFactory implements QuestionFactory {

	@Override
	public Question createQuestion(List<String> lines, String questionID) throws ClassNotFoundException {
//		tutaj trzeba kontynuować - przekształcić tak funkcję, żeby zwracała Question z liniami i ID
//		VBox = Vertical Box - jak do odpowiedzi A, B, C, itd.
//		te wszystkie atrybuty(VBox, questions itd.) powinny być dodawane jako atrybuty Question - chyba DONE
//		TO DO: każde pytanie ma mieć jeszcze przycisk na dole, który powoduje zapisanie odpowiedzi 
//		i przejście do następnego pytania
		Question question;
		VBox answers = new VBox();
		String questionText = lines.get(0);
		answers.getChildren().add(new Label(questionText));
//		ToggleGroup jest grupą przycisków, jej cechą jest to,
//		że jak naciśniesz jeden przycisk, to inny sie odznacza
		ToggleGroup group = new ToggleGroup();
		for (int i = 1; i < lines.size(); i+=2) {
			String answer = lines.get(i);
			String answerCode = lines.get(i+1);
			RadioButton button = new RadioButton(answer);
			button.setUserData(answerCode);
			button.setToggleGroup(group);
			answers.getChildren().add(button);
		}
//		linijka poniżej powoduje ustawienie onAction na jakis przycisk
		Button finishButton = new Button("Dalej");
		question = new Question(answers, questionID, group);
		finishButton.setOnAction (event ->
		{	try {
				question.fireEvent();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		});
// nie wiem, czy finishB powinno by� pod answers czy gdzie� wy�ej, narazie przekazuj� jako surowe
		answers.getChildren().add(finishButton);
		answers.onContextMenuRequestedProperty();
		return question;
	}

}
// 1) implementacja Question - ma zwracać Node
// 2) uzupełnić tę klasę żeby zwracał przycisk
// 3) fabryka ma zwracać obiekt typu Question
// 4) struktura danych do zapisywania pytań, która zapisuje po identyfikatorach
