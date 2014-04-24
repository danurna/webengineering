/**
 * <copyright>
 *
 * Copyright (c) 2014
 *
 * </copyright>
 */
package at.ac.tuwien.big.we14.lab2.api;

/**
 * Represents a quiz game round
 */
public interface Player {
    
    public String getName();
    public void setName(String name);
    
    public void setRoundAnswer(int index, boolean correct);
    public Boolean getRoundAnswer(int index);
    public Boolean[] getRoundAnswers();
	public void setRoundAnswers(Boolean[] roundAnswers);
	
	public int getWonRounds();
	public void setWonRounds(int wonRounds);
    
}
