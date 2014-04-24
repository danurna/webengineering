package at.ac.tuwien.big.we14.lab2.api.impl;

import java.io.Serializable;

import at.ac.tuwien.big.we14.lab2.api.Player;

public class SimplePlayer implements Player, Serializable {

    private static final long serialVersionUID = 1558555175093514257L;
    private String name;
    // Stores true, false or null (if unknown).
    private Boolean[] roundAnswers;
    // Stores after which amount of time the user answered a question.
    private Integer[] roundAnswerTimes;
    private int wonRounds;

    public SimplePlayer() {
        roundAnswers = new Boolean[3];
        roundAnswerTimes = new Integer[3];
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

    public Integer[] getRoundAnswerTimes() {
        return roundAnswerTimes;
    }

    public void setRoundAnswerTimes(Integer[] roundAnswerTimes) {
        this.roundAnswerTimes = roundAnswerTimes;
    }

    public void setRoundAnswerTime(int index, int time) {
        roundAnswerTimes[index] = time;
    }

    public int getRoundAnswerTime(int index) {
        return roundAnswerTimes[index];
    }

    
}
