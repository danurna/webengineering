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
public interface Round {
       
    public void setCategory(String category);
    public String getCategory();
    
    public void setQuestions(List<Question> questions);
    public List<Question> getQuestions();
    
    public void increaseQuestionNumber();
    public int getCurrentQuestionNumber();
}
