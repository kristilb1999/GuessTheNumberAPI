package com.mthree.guessthenumber.model;

import java.util.Objects;

/**
 * @author Kristi
 * @version 11/9/2021
 */

public class Game {

    private int gameId;
    private String answer;
    private boolean status;

    public Game() {

    }

    public Game(String answer, boolean status) {
        this.answer = answer;
        this.status = status;
    }

    public Game(int gameId, String answer, boolean status) {
        this.gameId = gameId;
        this.answer = answer;
        this.status = status;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return getGameId() == game.getGameId() && getStatus() == game.getStatus() && Objects.equals(getAnswer(), game.getAnswer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGameId(), getAnswer(), getStatus());
    }

    @Override
    public String toString() {
        return "Game{" +
                "id = " + gameId +
                ", answer = '" + answer + '\'' +
                ", status = " + status +
                '}';
    }
}
