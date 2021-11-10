package com.mthree.guessthenumber.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * @author Kristi
 * @version 11/9/2021
 */

public class Round {

    private int roundId;
    private int gameId;
    private LocalDateTime guessTime;
    private String guess;
    private String result;

    public Round() {

    }

    public Round(int gameId, String guess) {
        this.gameId = gameId;
        this.guess = guess;
    }

    public Round(int roundId, int gameId, LocalDateTime guessTime, String guess, String result) {
        this.roundId = roundId;
        this.gameId = gameId;
        this.guessTime = guessTime.truncatedTo(ChronoUnit.SECONDS);
        this.guess = guess;
        this.result = result;
    }

    public int getRoundId() {
        return roundId;
    }

    public void setRoundId(int roundId) {
        this.roundId = roundId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public LocalDateTime getGuessTime() {
        return guessTime.truncatedTo(ChronoUnit.SECONDS);
    }

    public void setGuessTime(LocalDateTime guessTime) {
        this.guessTime = guessTime.truncatedTo(ChronoUnit.SECONDS);
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Round round = (Round) o;
        return getRoundId() == round.getRoundId() && getGameId() == round.getGameId() && Objects.equals(getGuessTime(), round.getGuessTime()) && Objects.equals(getGuess(), round.getGuess()) && Objects.equals(getResult(), round.getResult());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoundId(), getGameId(), getGuessTime(), getGuess(), getResult());
    }

}
