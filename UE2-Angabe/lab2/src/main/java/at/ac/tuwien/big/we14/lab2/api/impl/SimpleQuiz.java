package at.ac.tuwien.big.we14.lab2.api.impl;

import java.io.Serializable;
import java.util.List;

import at.ac.tuwien.big.we14.lab2.api.Quiz;
import at.ac.tuwien.big.we14.lab2.api.Player;
import at.ac.tuwien.big.we14.lab2.api.Round;

public class SimpleQuiz implements Quiz, Serializable {
    private static final long serialVersionUID = -6592136359294992222L;
    private List<Player> playerList;
    private List<Round> roundList;
    private int roundNumber;
    
    public List<Player> getPlayers() {
        return playerList;
    }
   
    public Player getPlayer(int index) {
    	if (index >= 0) {
    		return playerList.get(index);
    	} else {
    		return null;
    	}
    }
    
    public void setPlayers(List<Player> players) {
        this.playerList = players;
    }
    
    public List<Round> getRounds() {
        return roundList;
    }
    
    public void setRounds(List<Round> rounds) {
        this.roundList = rounds;
    }
    
    public int getRoundNumber() {
        return roundNumber;
    }
    
    public void setRoundNumber(int roundNumber) {
        this.roundNumber = roundNumber;
    }

    public void increaseRoundNumber() {
        roundNumber += 1;
    }

    public int getCurrentRoundNumber() {
        return roundNumber;
    }
    
    public Player getQuizWinner(){
        int numberOfRoundsWon = -1;
        Player winner = null;
        
        for( Player player : playerList ){
            if( player.getWonRounds() > numberOfRoundsWon ){
                winner = player;
                numberOfRoundsWon = player.getWonRounds();
            }else if( player.getWonRounds() == numberOfRoundsWon ){
                winner = null;
            }
        }
        
        return winner;
    }

	@Override
	public Player getRoundWinner() {
		
		Player winner = null;
		int correctAnswers = 0, time = Integer.MAX_VALUE;
        
        for( Player player : playerList ){
        	int playerScore = player.getNumberOfCorrectAnswers();
        	int playerTime = player.getCorrectAnswerTimes();
        	
            if( playerScore > correctAnswers ){
                winner = player;
                correctAnswers = playerScore;
                time = playerTime;
            } else if( playerScore == correctAnswers && correctAnswers > 0){
                if(playerTime < time) {
                	winner = player;
                } else if (playerTime == time) {
                	winner = null;
                }
            }
        }
        
		return winner;
	}
    
}
