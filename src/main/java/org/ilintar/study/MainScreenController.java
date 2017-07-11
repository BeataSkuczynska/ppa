package org.ilintar.study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import javafx.scene.control.TitledPane;
import org.ilintar.study.question.*;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;

// MainScreenControler powinien rozszerzać QuestionAnsweredEventListener !!!!!!
// DONE, ale co z funkcj�, kt�ra jest w QAEL tzn. handleEvent?
public class MainScreenController implements QuestionAnsweredEventListener {
	
	protected static Map<String, QuestionFactory> factoryMap;
	private int nrQuestion;
	public Question currentQuestion;
	
	static {
//		de facto to to jest factoriesMap, bo zawiera fabryki, a nie fabryk�
		factoryMap = new HashMap<>();
		factoryMap.put("radio", new RadioQuestionFactory());
	}
	
	@FXML AnchorPane mainStudy;
	@FXML TitledPane titledPane;

	public MainScreenController(){
		this.nrQuestion = -1;
	}
//	@FXML public void startStudy() {
//		dziecmi sa wszystkie rzeczy (tzn. przyciski np.), które są w tym mainStudy oknie
//		mainStudy.getChildren().clear();
//		Node questionComponent = readQuestionFromFile(0, getClass().getResourceAsStream("StudyDetails.sqf"));
//		dodajemy pytanie, które też będzie dzieckiem mainStudy
//		mainStudy.getChildren().add(questionComponent);
//}

//	ta funkcja została wywołana 4 linijki wyżej, int i to, od którego pytania zaczynamy
	private Question readQuestionFromFile(int i, InputStream resourceAsStream) throws ClassNotFoundException {
		BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
		String currentLine;
		int which = 0;
		List<String> questionLines = new ArrayList<>();
		boolean readingQuestions = false;
		String questionType = null;
		String questionID = null;
		try {
			while ((currentLine = br.readLine()) != null) {
				if (currentLine.startsWith("StartQuestion")) {
					if (readingQuestions) {
						throw new IllegalArgumentException("Invalid file format: StartQuestion without EndQuestion");
					}
// int i się nie zmienia, pierwszym zadaniem jest sprawić, żeby się zmieniało i co za tym idzie wywoływał
// o kolejne pytania, można to zrobić nowa pytaniem np. changeQuestion (DONE, ale nie zmienia jeszcze pytania)
					if (which == i) {
						String[] split = currentLine.split(" ");
						if (split.length > 1) {
							String[] split2 = split[1].split("=");
							if (split2.length > 1) {
								readingQuestions = true;
								questionID = split2[1];
								String[] split3 = split[2].split("=");
								if (split3.length > 1) {
									questionType = split3[1];
								}
							}
						}
						if (questionID != null && questionType == null) {
							throw new IllegalArgumentException("Invalid file format: StartQuestion type=<type>");
						}
					} else {
						which++;
					}
				} else {
					if (readingQuestions) {
						if (currentLine.startsWith("EndQuestion")) {
							if (factoryMap.containsKey(questionType)) {
								currentQuestion = factoryMap.get(questionType).createQuestion(questionLines, questionID);
								currentQuestion.addQuestionAnsweredListener(this);
								return currentQuestion;
							} else {
								throw new IllegalArgumentException("Do not have a factory for question type: " + questionType);
							}
						} else {						
							questionLines.add(currentLine.trim());
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	@FXML
	public void startStudy() throws ClassNotFoundException {
		displayQuestion();
	}

	public void endStudy(){
		mainStudy.getChildren().clear();
	}

	public void displayQuestion() throws ClassNotFoundException {
		nrQuestion++;
		mainStudy.getChildren().clear();
		Question questionComponent = readQuestionFromFile(nrQuestion, getClass().getResourceAsStream("/StudyDetails.sqf"));
		if (questionComponent != null) {
			String questionTitle = "Pytanie " + String.valueOf(nrQuestion + 1);
			titledPane.setText(questionTitle);
			mainStudy.getChildren().add(questionComponent.getRenderedQuestion());
		} else {
			endStudy();
		}
	}

	public void handleEvent(QuestionAnsweredEvent event) throws ClassNotFoundException {
		displayQuestion();
		Answer answer = event.getAnswer();
		System.out.println("Odpowiedź badanego_ej: " + answer.getAnswer());
	}
	
}