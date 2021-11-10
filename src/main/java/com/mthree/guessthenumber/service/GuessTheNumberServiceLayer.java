package com.mthree.guessthenumber.service;

import com.mthree.guessthenumber.model.Game;
import com.mthree.guessthenumber.model.Round;

import java.util.List;

/**
 * @author Kristi
 * @version 11/9/2021
 */

public interface GuessTheNumberServiceLayer {

    /**
     * A list of all the games found in the database. Any
     * games still in progress will not show their answer
     * to the player.
     *
     * @return A list of games in the database.
     */
    List<Game> getAllGames();

    /**
     * All rounds associated with a specific game by its
     * game Id, including the result of that game.
     *
     * @param gameId The game Id each round is associated with.
     * @return The list of rounds for a specific game.
     */
    List<Round> getAllRoundsByGameId(int gameId);

    /**
     * Allows user to make a guess for the answer and return
     * the Round object added for that guess. The result of
     * the round will be shown to the user with this Round.
     * Will set the status of the game to finished when the correct
     * guess is given.
     *
     * @param round The gameId and guess to be made.
     * @return The round that was just added to the database.
     */
    Round makeGuess(Round round);

    /**
     * Returns the game associated with a specific game Id.
     * If the game is in progress, the answer will not be shown.
     *
     * @param gameId The game to be returned.
     * @return The game with or without the answer.
     */
    Game getGameById(int gameId);

    /**
     * Adds a game to the database and returns the game with
     * its own unique id.
     *
     * @param game The game that was just added with its
     * answer and status.
     * @return The game object that was added with its id.
     */
    Game addGame(Game game);

    /**
     * Creates a new game to be added to the database. Game will
     * be created with an answer made up of 4 unique, random digits.
     *
     * @return The id of the game that was added.
     */
    int newGame();

    /**
     * Determines the result of the round. Compares the guess with
     * the actual answer for the game and gives a player a result.
     *
     * @param guess The 4 digit guess from the user.
     * @param answer The 4 digit answer of the game.
     * @return A String to represent how many digits were correctly
     * given in the guess, in the form "e:#:p:#"
     */
    String determineResult(String guess, String answer);

}
