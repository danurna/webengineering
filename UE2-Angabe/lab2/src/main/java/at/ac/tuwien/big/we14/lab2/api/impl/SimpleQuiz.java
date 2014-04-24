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
        return playerList.get(index);
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
    
}
