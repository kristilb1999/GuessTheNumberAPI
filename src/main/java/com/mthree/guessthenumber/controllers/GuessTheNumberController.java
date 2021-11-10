package com.mthree.guessthenumber.controllers;

import com.mthree.guessthenumber.model.Game;
import com.mthree.guessthenumber.model.Round;
import com.mthree.guessthenumber.service.GuessTheNumberServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kristi
 * @version 11/9/2021
 */

@RestController
@RequestMapping("/api/guessthenumber")
public class GuessTheNumberController {

    @Autowired
    GuessTheNumberServiceLayer service;

    @PostMapping("/begin")
    @ResponseStatus(HttpStatus.CREATED)
    public int createGame() {
        return service.newGame();
    }

    @PostMapping("/guess")
    public Round makeGuess(@RequestBody Round round) {
        return service.makeGuess(round);
    }

    @GetMapping("/game")
    public List<Game> getAllGames() {
        return service.getAllGames();
    }

    @GetMapping("/game/{gameId}")
    public Game getGameById(@PathVariable("gameId") int gameId) {
        return service.getGameById(gameId);
    }

    @GetMapping("/rounds/{gameId}")
    public List<Round> getRoundsByGame(@PathVariable("gameId") int gameId) {
        return service.getAllRoundsByGameId(gameId);
    }

}
