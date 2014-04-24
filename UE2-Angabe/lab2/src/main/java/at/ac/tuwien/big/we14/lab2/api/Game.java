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
public interface Game {
    
    void setPlayers(List<Player> players);
    List<Player> getPlayers();
    Player getPlayer(int index);

    void setRounds(List<Round> rounds);
    List<Round> getRounds();
    
    void increaseRoundNumber();
    int getCurrentRoundNumber();
}
