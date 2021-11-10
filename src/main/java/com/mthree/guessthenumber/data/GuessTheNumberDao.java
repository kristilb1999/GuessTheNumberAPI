package com.mthree.guessthenumber.data;

import com.mthree.guessthenumber.model.Game;
import com.mthree.guessthenumber.model.Round;

import java.util.List;

/**
 * @author Kristi
 * @version 11/9/2021
 */

public interface GuessTheNumberDao {

    /**
     * Returns a list of all the games in the database game table.
     * Returns null if no games exist in database.
     *
     * @return A list of all games found in the database. Or null.
     */
    List<Game> getAllGames();

    /**
     * Returns the game associated with the given gameId.
     * Or null if the game does not exist.
     *
     * @param gameId The Id of the specified game.
     * @return The game associated with the given Id or null.
     */
    Game getGameById(int gameId);

    /**
     * Returns the game added to the database, with all
     * fields completed.
     *
     * @param game The game to be added to the database,
     * no id required.
     *
     * @return The game that was added with all fields
     * completely filled in.
     */
    Game addGame(Game game);

    /**
     * Updates the status of the game. Only used when game is
     * completed and status is changed to finished.
     *
     * @param game The game to have its status updated.
     */
    void updateGame(Game game);

    /**
     * A list of all rounds associated with a specific game,
     * retrieved by the gameId or null if none exist.
     *
     * @param gameId The Id of the game rounds to look for.
     * @return The list of rounds played in a specific game.
     */
    List<Round> getAllRoundsByGameId(int gameId);

    /**
     * The specific round of a game, retrieved by its own id.
     * Otherwise, null if the round doesn't exist.
     *
     * @param roundId The id of the round to look for.
     * @return The round by its id or null.
     */
    Round getRoundById(int roundId);

    /**
     * Adds a round to the round table and associated it
     * with the game it was played for.
     *
     * @param round The round to be added to the database.
     * @return The round with all its information.
     */
    Round addRound(Round round);

}
