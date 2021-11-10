package com.mthree.guessthenumber.service;

import com.mthree.guessthenumber.data.GuessTheNumberDao;
import com.mthree.guessthenumber.model.Game;
import com.mthree.guessthenumber.model.Round;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Kristi
 * @version 11/9/2021
 */

@Service
public class GuessTheNumberServiceLayerDatabaseImpl implements GuessTheNumberServiceLayer {

    @Autowired
    GuessTheNumberDao guessDao;

    @Override
    public List<Game> getAllGames() {
        List<Game> games = guessDao.getAllGames();
        for(Game game : games) {
            if(!game.getStatus()) {
                game.setAnswer("****");
            }
        }
        return games;
    }

    @Override
    public List<Round> getAllRoundsByGameId(int gameId) {
        return guessDao.getAllRoundsByGameId(gameId);
    }

    @Override
    public Round makeGuess(Round round) {
        String answer = guessDao.getGameById(round.getGameId()).getAnswer();
        String guess = round.getGuess();
        String result = determineResult(guess, answer);
        round.setResult(result);

        if(guess.equals(answer)) {
            Game game = getGameById(round.getGameId());
            game.setStatus(true);
            guessDao.updateGame(game);
        }

        return guessDao.addRound(round);
    }

    @Override
    public Game getGameById(int gameId) {
        Game game = guessDao.getGameById(gameId);
        if(!game.getStatus()) {
            game.setAnswer("****");
        }
        return game;
    }

    @Override
    public Game addGame(Game game) {
        return guessDao.addGame(game);
    }

    @Override
    public int newGame() {
        Game game = new Game();
        game.setAnswer(createAnswer());

        Game addedGame = guessDao.addGame(game);
        return addedGame.getGameId();
    }

    //print 4 unique digits in random order
    private String createAnswer() {

        ArrayList<Integer> numberList = new ArrayList<Integer>();
        for(int i = 0; i < 10; i++) {
            numberList.add(i);
        }
        Collections.shuffle(numberList);

        String answer = "";

        for(int j = 0; j < 4; j++) {
            answer += numberList.get(j);
        }

        return answer;
    }

    @Override
    public String determineResult(String guess, String answer) {
        char[] guessNums = guess.toCharArray();
        char[] answerNums = answer.toCharArray();

        int exact = 0;
        int partial = 0;

        for(int i = 0; i < guessNums.length; i++) {

            for(int j = 0; j < answerNums.length; j++) {
                if(Character.compare(guessNums[i], answerNums[j]) == 0) {
                    if(i == j) {
                        exact++;
                    } else {
                        partial++;
                    }
                }
            }

        }

        String result = "e:" + exact + ":p:" + partial;

        return result;
    }
}
