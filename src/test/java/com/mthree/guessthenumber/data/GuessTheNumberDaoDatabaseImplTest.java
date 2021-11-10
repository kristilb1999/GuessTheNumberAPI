package com.mthree.guessthenumber.data;

import com.mthree.guessthenumber.TestApplicationConfiguration;
import com.mthree.guessthenumber.model.Game;
import com.mthree.guessthenumber.model.Round;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Kristi
 * @version 11/9/2021
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GuessTheNumberDaoDatabaseImplTest {

    @Autowired
    GuessTheNumberDao testDao;

    @Autowired
    private JdbcTemplate jdbcTest;

    public GuessTheNumberDaoDatabaseImplTest() {

    }

    @Before
    public void setUp() {
        final String DROP_GAME_TABLE = "DROP TABLE game";
        final String DROP_ROUND_TABLE = "DROP TABLE round";
        final String CREATE_GAME_TABLE = "CREATE TABLE game (gameId INT PRIMARY KEY AUTO_INCREMENT, answer CHAR(4), status BOOLEAN)";
        final String CREATE_ROUND_TABLE = "CREATE TABLE round (roundId INT PRIMARY KEY AUTO_INCREMENT, gameId INT, guess CHAR(4), guessTime DATETIME, result CHAR(10), FOREIGN KEY fk_round_game (gameId) REFERENCES game(gameId))";
        jdbcTest.update(DROP_ROUND_TABLE);
        jdbcTest.update(DROP_GAME_TABLE);
        jdbcTest.update(CREATE_GAME_TABLE);
        jdbcTest.update(CREATE_ROUND_TABLE);
    }

    @Test
    public void testAddGetAllGames() {
        //add games to database
        Game game1 = new Game();
        game1.setAnswer("1234");
        game1.setStatus(false);
        testDao.addGame(game1);

        Game game2 = new Game();
        game2.setAnswer("5678");
        game2.setStatus(false);
        testDao.addGame(game2);

        List<Game> allGames = testDao.getAllGames();

        //test general and specific info about list
        assertNotNull(allGames);
        assertEquals(2, allGames.size());

        assertTrue(testDao.getAllGames().contains(game1));
        assertTrue(testDao.getAllGames().contains(game2));

    }

    @Test
    public void testAddGetGame() {
        //add game to database
        Game game1 = new Game();
        game1.setAnswer("1234");
        game1.setStatus(false);

        //add game
        testDao.addGame(game1);

        //retrieve game
        Game retrievedGame = testDao.getGameById(game1.getGameId());

        //check that the game is correct
        assertNotNull(retrievedGame);
        assertEquals(game1.getGameId(), retrievedGame.getGameId());
        assertEquals(game1.getStatus(), retrievedGame.getStatus());
        assertEquals(game1.getAnswer(), retrievedGame.getAnswer());

    }

    @Test
    public void testUpdateGame() {
        //add game to database
        Game game1 = new Game();
        game1.setAnswer("1234");
        game1.setStatus(false);

        //add with test dao
        testDao.addGame(game1);

        //get the game
        Game retrievedGame = testDao.getGameById(game1.getGameId());

        //check that it is correct game
        assertEquals(game1, retrievedGame);

        //change some data and update the game
        game1.setStatus(true);
        testDao.updateGame(game1);

        //make sure the two are different
        assertNotEquals(game1, retrievedGame);

        //retrieve updated game
        retrievedGame = testDao.getGameById(game1.getGameId());

        //check that they are the same again
        assertEquals(game1, retrievedGame);

    }

    @Test
    public void testAddGetAllRoundsById() {
        //add game to database
        Game game1 = new Game();
        game1.setAnswer("1234");
        game1.setStatus(false);

        //add with test dao
        testDao.addGame(game1);

        //add round to game
        Round round1 = new Round();
        round1.setRoundId(1);
        round1.setGuess("5678");
        round1.setResult("e:0:p:0");
        round1.setGameId(game1.getGameId());

        //add to testDao
        testDao.addRound(round1);

        //add second round to game
        Round round2 = new Round();
        round2.setRoundId(2);
        round2.setGuess("1234");
        round2.setResult("e:4:p:0");
        round2.setGameId(game1.getGameId());

        //add to testDao
        testDao.addRound(round2);

        //retrieve list
        List<Round> allRoundsForGameOne = testDao.getAllRoundsByGameId(game1.getGameId());

        //check basic and detailed info
        assertNotNull(allRoundsForGameOne);
        assertEquals(2, allRoundsForGameOne.size());
    }

    @Test
    public void testAddGetRound() {
        //add game to database
        Game game1 = new Game();
        game1.setAnswer("1234");
        game1.setStatus(false);

        //add with test dao
        testDao.addGame(game1);

        //add round to game
        Round round1 = new Round();
        round1.setGuess("5678");
        round1.setResult("e:0:p:0");
        round1.setGameId(game1.getGameId());

        testDao.addRound(round1);

        //retrieve round
        Round retrievedRound = testDao.getRoundById(round1.getRoundId());

        //check info
        assertNotNull(retrievedRound);

        assertEquals(round1.getRoundId(), retrievedRound.getRoundId());
        assertEquals(round1.getGameId(), retrievedRound.getGameId());
        assertEquals(round1.getGuess(), retrievedRound.getGuess());
        assertEquals(round1.getGuessTime(), retrievedRound.getGuessTime());
        assertEquals(round1.getResult(), retrievedRound.getResult());

    }

}
