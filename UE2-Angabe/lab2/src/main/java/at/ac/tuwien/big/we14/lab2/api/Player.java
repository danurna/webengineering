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
 * Represents a quiz game round
 */
public interface Player {
    private String name;
    // Stores true, false or null (if unknown).
    private Boolean[] roundAnswers;
    private int wonRounds;
    
}
