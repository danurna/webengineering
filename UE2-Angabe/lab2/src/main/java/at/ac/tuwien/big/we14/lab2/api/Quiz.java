/**
 * <copyright>
 *
 * Copyright (c) 2014 
 *
 * </copyright>
 */
package at.ac.tuwien.big.we14.lab2.api;

import java.util.List;

/**
 * Represents a quiz game
 */
public interface Quiz {
    
    public void setPlayers(List<Player> players);
    public List<Player> getPlayers();
    public Player getPlayer(int index);

    public void setRounds(List<Round> rounds);
    public List<Round> getRounds();
    
    public void increaseRoundNumber();
    public int getCurrentRoundNumber();
}
