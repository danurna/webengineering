package at.ac.tuwien.big.we14.lab2.model;

import java.util.List;

public class Game {
	
	private Player player1;
	private Player player2;
	private List<Round> rounds;
	private int currentRoundIndex;
	private int gamesWonPlayer1=0;
	private int gamesWonPlayer2=0;
	

	public int getGamesWonPlayer1() {
		return gamesWonPlayer1;
	}

	public void setGamesWonPlayer1(int gamesWonPlayer1) {
		this.gamesWonPlayer1 = gamesWonPlayer1;
	}

	public int getGamesWonPlayer2() {
		return gamesWonPlayer2;
	}

	public void setGamesWonPlayer2(int gamesWonPlayer2) {
		this.gamesWonPlayer2 = gamesWonPlayer2;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}
	
	public List<Round> getRounds() {
		return rounds;
	}
	
	public void setRounds(List<Round> rounds) {
		this.rounds = rounds;
	}
	
	public int getCurrentRoundIndex() {
		return currentRoundIndex;
	}
	
	public void setCurrentRoundIndex(int currentRoundIndex) {
		this.currentRoundIndex = currentRoundIndex;
	}
	
	public Round getCurrentRound() {
		return rounds.get(currentRoundIndex);
	}
}
