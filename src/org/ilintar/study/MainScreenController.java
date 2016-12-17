package org.ilintar.study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ilintar.study.question.Answer;
import org.ilintar.study.question.IQuestion;
import org.ilintar.study.question.QuestionFactory;
import org.ilintar.study.question.RadioQuestionFactory;
import org.ilintar.study.question.event.QuestionAnsweredEvent;
import org.ilintar.study.question.event.QuestionAnsweredEventListener;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class MainScreenController implements QuestionAnsweredEventListener{
	
	protected static Map<String, QuestionFactory> factoryMap;
	
	static {
		factoryMap = new HashMap<>();
		factoryMap.put("radio", new RadioQuestionFactory());
	}

	@FXML AnchorPane mainStudy;

	@FXML public void startStudy() {
		mainStudy.getChildren().clear();
		Node questionComponent = readQuestionFromFile(0, getClass().getResourceAsStream("StudyDetails.sqf"));
		mainStudy.getChildren().add(questionComponent);
	}

	private Node readQuestionFromFile(int i, InputStream resourceAsStream) {
		BufferedReader br = new BufferedReader(new InputStreamReader(resourceAsStream));
		String currentLine;
		int which = 0;
		List<String> questionLines = new ArrayList<>();
		boolean readingQuestions = false;
		String questionType = null;
		String questionId = null;
		try {
			while ((currentLine = br.readLine()) != null) {
				if (currentLine.startsWith("StartQuestion")) {
					if (readingQuestions) {
						throw new IllegalArgumentException("Invalid file format: StartQuestion without EndQuestion");
					}
					if (which == i) {
						readingQuestions = true;
						String[] split = currentLine.split(" ");
						if (split.length > 2) {
							String[] split2 = split[1].split("=");
							String[] split3 = split[2].split("=");
							if (split2.length > 1) {
								questionType = split2[1];
							}
							if (split3.length > 1) {
								questionId = split3[1];
							}
						}
						if (questionType == null) {
							throw new IllegalArgumentException("Invalid file format: StartQuestion type=<type>");
						}
					} 
					else {
						which++;
					}
				}  else {
					if (readingQuestions) {
						if (currentLine.startsWith("EndQuestion")) {
							if (factoryMap.containsKey(questionType)) {
								IQuestion question = factoryMap.get(questionType).createQuestion(questionLines, questionId);
								question.addQuestionAnsweredListener(this);
								return question.getRenderedQuestion();
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

	@Override
	public void handleEvent(QuestionAnsweredEvent event) {
		IQuestion question = event.getQuestion();
		Answer answer = event.getAnswer();
		System.out.println(question.getId());
		System.out.println(answer.getAnswer());
		
	}
	
}
