package com.mthree.guessthenumber.data;

import com.mthree.guessthenumber.model.Game;
import com.mthree.guessthenumber.model.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author Kristi
 * @version 11/9/2021
 */

@Repository
public class GuessTheNumberDaoDatabaseImpl implements GuessTheNumberDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Game> getAllGames() {
        final String SQL_SELECT_ALL_GAMES = "SELECT * FROM game";
        return jdbc.query(SQL_SELECT_ALL_GAMES, new GameMapper());
    }

    @Override
    public Game getGameById(int gameId) {
        final String SQL_SELECT_GAME_BY_ID = "SELECT * FROM game WHERE gameId = ?";
        try {
            return jdbc.queryForObject(SQL_SELECT_GAME_BY_ID, new GameMapper(), gameId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Game addGame(Game game) {
        final String SQL_INSERT_GAME = "INSERT INTO game(answer, status) VALUES (?,?)";
        jdbc.update(SQL_INSERT_GAME, game.getAnswer(), game.getStatus());

        int newGameId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        game.setGameId(newGameId);

        return game;
    }

    @Override
    public void updateGame(Game game) {
        final String SQL_UPDATE_GAME = "UPDATE game SET status = ? WHERE gameId = ?";
        jdbc.update(SQL_UPDATE_GAME, game.getStatus(), game.getGameId());
    }

    @Override
    public List<Round> getAllRoundsByGameId(int gameId) {
        final String SQL_SELECT_ROUNDS_BY_GAME_ID = "SELECT * FROM round " + "WHERE gameId = ? ORDER BY guessTime";
        List<Round> rounds = jdbc.query(SQL_SELECT_ROUNDS_BY_GAME_ID, new RoundMapper(), gameId);
        return rounds;
    }

    @Override
    public Round getRoundById(int roundId) {
        final String SQL_SELECT_ROUND_BY_ID = "SELECT * FROM round WHERE roundId = ?";
        try {
            return jdbc.queryForObject(SQL_SELECT_ROUND_BY_ID, new RoundMapper(), roundId);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public Round addRound(Round round) {
        final String SQL_INSERT_ROUND = "INSERT INTO round(gameId, guess, guessTime, result) VALUES (?,?,?,?)";

        LocalDateTime timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        round.setGuessTime(timestamp);

        jdbc.update(SQL_INSERT_ROUND, round.getGameId(), round.getGuess(), round.getGuessTime(), round.getResult());

        int newRoundId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        round.setRoundId(newRoundId);
        return round;
    }

    public static final class GameMapper implements RowMapper<Game> {

        @Override
        public Game mapRow(ResultSet rs, int index) throws SQLException {
            Game game = new Game();
            game.setGameId(rs.getInt("gameId"));
            game.setAnswer(rs.getString("answer"));
            game.setStatus(rs.getBoolean("status"));
            return game;
        }

    }

    public static final class RoundMapper implements RowMapper<Round> {

        @Override
        public Round mapRow(ResultSet rs, int index) throws SQLException {
            Round round = new Round();
            round.setRoundId(rs.getInt("roundId"));
            round.setGameId(rs.getInt("gameId"));

            //get what time the guess happened at
            Timestamp timestamp = rs.getTimestamp("guessTime");
            round.setGuessTime(timestamp.toLocalDateTime().truncatedTo(ChronoUnit.SECONDS));

            round.setGuess(rs.getString("guess"));
            round.setResult(rs.getString("result"));
            return round;
        }

    }


}
