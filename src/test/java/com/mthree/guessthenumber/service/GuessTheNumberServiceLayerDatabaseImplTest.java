package com.mthree.guessthenumber.service;

import com.mthree.guessthenumber.TestApplicationConfiguration;
import com.mthree.guessthenumber.model.Game;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

import java.util.List;

/**
 * @author Kristi
 * @version 11/9/2021
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class GuessTheNumberServiceLayerDatabaseImplTest {

    @Autowired
    GuessTheNumberServiceLayer service;

    @Autowired
    private JdbcTemplate jdbcServiceTest;

    public GuessTheNumberServiceLayerDatabaseImplTest() {

    }

    @Before
    public void setUp() {
        final String DROP_GAME_TABLE = "DROP TABLE game";
        final String DROP_ROUND_TABLE = "DROP TABLE round";
        final String CREATE_GAME_TABLE = "CREATE TABLE game (gameId INT PRIMARY KEY AUTO_INCREMENT, answer CHAR(4), status BOOLEAN)";
        final String CREATE_ROUND_TABLE = "CREATE TABLE round (roundId INT PRIMARY KEY AUTO_INCREMENT, gameId INT, guess CHAR(4), guessTime DATETIME, result CHAR(10), FOREIGN KEY fk_round_game (gameId) REFERENCES game(gameId))";
        jdbcServiceTest.update(DROP_ROUND_TABLE);
        jdbcServiceTest.update(DROP_GAME_TABLE);
        jdbcServiceTest.update(CREATE_GAME_TABLE);
        jdbcServiceTest.update(CREATE_ROUND_TABLE);
    }

    @Test
    public void testNotShowingAnswerOfProgressingGameAll() {
        //add games to list
        Game complete = new Game();
        complete.setAnswer("1234");
        complete.setStatus(true);
        service.addGame(complete);

        Game incomplete = new Game();
        incomplete.setAnswer("5678");
        incomplete.setStatus(false);
        service.addGame(incomplete);

        //retrieve list of games
        List<Game> allGames = service.getAllGames();

        //check answers for each game
        assertNotNull(allGames);

        Game retrievedComplete = allGames.get(0);
        assertEquals(complete.getAnswer(), retrievedComplete.getAnswer());

        Game retrievedIncomplete = allGames.get(1);
        assertNotEquals(incomplete.getAnswer(), retrievedIncomplete.getAnswer());

    }

    @Test
    public void testNotShowingAnswerOfProgressingGameSingle() {
        Game incomplete = new Game();
        incomplete.setAnswer("5678");
        incomplete.setStatus(false);
        service.addGame(incomplete);

        Game retrievedIncomplete = service.getGameById(incomplete.getGameId());
        assertNotEquals(incomplete.getAnswer(), retrievedIncomplete.getAnswer());
    }

    @Test
    public void testDetermineResultIsExact() {
        String guess = "1234";
        String answer = "1234";
        String result = service.determineResult(guess,answer);
        assertEquals("e:4:p:0", result);
    }

    @Test
    public void testDetermineResultPartial() {
        String guess = "1243";
        String answer = "1234";
        String result = service.determineResult(guess,answer);
        assertEquals("e:2:p:2", result);
    }

}
