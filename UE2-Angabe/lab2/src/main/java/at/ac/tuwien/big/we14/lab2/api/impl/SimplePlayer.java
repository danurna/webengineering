package at.ac.tuwien.big.we14.lab2.api.impl;

import at.ac.tuwien.big.we14.lab2.api.Player;

public class SimplePlayer implements Player {
	private String name;
    // Stores true, false or null (if unknown).
    private Boolean[] roundAnswers;
    private int wonRounds;

    public SimplePlayer() {
    	roundAnswers = new Boolean[3];
    }
    
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
    public void setRoundAnswer(int index, boolean correct) {
    	roundAnswers[index] = correct;
    }
    
    public Boolean getRoundAnswer(int index) {
    	return roundAnswers[index];
    }
    
    public Boolean[] getRoundAnswers() {
		return roundAnswers;
	}
	
    public void setRoundAnswers(Boolean[] roundAnswers) {
		this.roundAnswers = roundAnswers;
	}
	
    public int getWonRounds() {
		return wonRounds;
	}
	
    public void setWonRounds(int wonRounds) {
		this.wonRounds = wonRounds;
	}
}
