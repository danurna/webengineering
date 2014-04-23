package at.ac.tuwien.big.we14.lab2.model;

import java.util.HashMap;
import java.util.List;

import at.ac.tuwien.big.we14.lab2.api.Category;
import at.ac.tuwien.big.we14.lab2.api.Question;

public class Round {
	private Category category;
	private List<Question> questions;
	private int currentQuestionIndex;
	private HashMap<Integer,Integer> scorePlayer1; // questionIndex - score (0 false 1 true)
	private HashMap<Integer,Integer> scorePlayer2;
	private int winner;// 1 for player 1, 2 for player 2
	
	public Round() {
		scorePlayer1 = new HashMap<Integer,Integer>();
		scorePlayer2 = new HashMap<Integer,Integer>();	
		winner=0;
	}
	
	
	
	public int getWinner() {
		return winner;
	}



	public void setWinner(int winner) {
		this.winner = winner;
	}



	public HashMap<Integer, Integer> getScorePlayer1() {
		return scorePlayer1;
	}

	public void setScorePlayer1(HashMap<Integer, Integer> scorePlayer1) {
		this.scorePlayer1 = scorePlayer1;
	}

	public HashMap<Integer, Integer> getScorePlayer2() {
		return scorePlayer2;
	}

	public void setScorePlayer2(HashMap<Integer, Integer> scorePlayer2) {
		this.scorePlayer2 = scorePlayer2;
	}


	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}
	
	public int getCurrentQuestionIndex() {
		return currentQuestionIndex;
	}
	
	public void setCurrentQuestionIndex(int currentQuestionIndex) {
		this.currentQuestionIndex = currentQuestionIndex;
	}
	
	public Question getCurrentQuestion() {
		return questions.get(currentQuestionIndex);
	}
}
