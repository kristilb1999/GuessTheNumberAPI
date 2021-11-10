package com.mthree.guessthenumber.data;

import com.mthree.guessthenumber.model.Game;
import com.mthree.guessthenumber.model.Round;

import java.util.List;

/**
 * @author Kristi
 * @version 11/9/2021
 */

public interface GuessTheNumberDao {

    List<Game> getAllGames();

    Game getGameById(int gameId);

    Game addGame(Game game);

    void updateGame(Game game);

    List<Round> getAllRoundsByGameId(int gameId);

    Round getRoundById(int roundId);

    Round addRound(Round round);

}
