package com.mthree.guessthenumber.service;

import com.mthree.guessthenumber.model.Game;
import com.mthree.guessthenumber.model.Round;

import java.util.List;

/**
 * @author Kristi
 * @version 11/9/2021
 */

public interface GuessTheNumberServiceLayer {

    List<Game> getAllGames();

    List<Round> getAllRoundsByGameId(int gameId);

    Round makeGuess(Round round);

    Game getGameById(int gameId);

    Game addGame(Game game);

    int newGame();

    String determineResult(String guess, String answer);

}
