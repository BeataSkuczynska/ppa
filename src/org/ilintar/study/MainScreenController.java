package org.ilintar.study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ilintar.study.question.QuestionFactory;
import org.ilintar.study.question.RadioQuestionFactory;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

// MainScreenControler powinien rozszerzać QuestionAnsweredEventListener !!!!!!
// DONE, ale co z funkcj�, kt�ra jest w QAEL tzn. handleEvent?
public class MainScreenController extends QuestionAnsweredEventListener{
	
	protected static Map<String, QuestionFactory> factoryMap;
	
	static {
//		de facto to to jest factoriesMap, bo zawiera fabryki, a nie fabryk�
		factoryMap = new HashMap<>();
		factoryMap.put("radio", new RadioQuestionFactory());
	}
	
	@FXML AnchorPane mainStudy;

	@FXML public void startStudy() {
//		dziecmi sa wszystkie rzeczy (tzn. przyciski np.), które są w tym mainStudy oknie
		mainStudy.getChildren().clear();
		Node questionComponent = readQuestionFromFile(0, getClass().getResourceAsStream("StudyDetails.sqf"));
//		dodajemy pytanie, które też będzie dzieckiem mainStudy
		mainStudy.getChildren().add(questionComponent);
}
//	ta funkcja została wywołana 4 linijki wyżej, int i to, od którego pytania zaczynamy
	private Node readQuestionFromFile(int i, InputStream resourceAsStream) {
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
							if (split2.length > 1 && split2[1] == Integer.toString(i)) {
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
								return factoryMap.get(questionType).createQuestion(questionLines, questionID).getRenderedQuestion();
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
	
}