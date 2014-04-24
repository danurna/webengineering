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
       
    
    void setCategory(Category category);
    Category getCategory();
    
    void setQuestions(List<Question> questions);
    List<Question> getQuestions();
    
    void increaseQuestionNumber();
    int getCurrentQuestionNumber();
}
