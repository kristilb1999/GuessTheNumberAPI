DROP DATABASE IF EXISTS guessTheNumberDBTest;

CREATE DATABASE guessTheNumberDBTest;

USE guessTheNumberDBTest;

CREATE TABLE game (
	gameId INT PRIMARY KEY AUTO_INCREMENT,
    answer CHAR(4),
    status BOOLEAN
);

CREATE TABLE round (
	roundId INT PRIMARY KEY AUTO_INCREMENT,
    gameId INT,
    guess CHAR(4),
    guessTime DATETIME,
    result CHAR(10),
    FOREIGN KEY fk_round_game (gameId)
		REFERENCES game(gameId)
);