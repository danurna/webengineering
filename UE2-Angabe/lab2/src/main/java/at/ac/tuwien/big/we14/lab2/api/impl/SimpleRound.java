package at.ac.tuwien.big.we14.lab2.api.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Question;
import at.ac.tuwien.big.we14.lab2.api.Round;

public class SimpleRound implements Round, Serializable {

	private static final long serialVersionUID = -2189728736793103389L;

	private String category;
	private List<Question> questions;
	private int questionNumber;
	
	public SimpleRound() {
		this.questions = new ArrayList<Question>();
		this.questionNumber = 0;
	}
	
	@Override
	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	@Override
	public List<Question> getQuestions() {
		return questions;
	}

	@Override
	public void increaseQuestionNumber() {
		questionNumber++;
	}

	@Override
	public int getCurrentQuestionNumber() {
		return questionNumber;
	}

}
