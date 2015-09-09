CREATE TABLE players(
	playerID INT NOT NULL,
    numberOfWins INT DEFAULT 0,
    PRIMARY KEY (playerID)
);

CREATE TABLE games(
	gameID INT NOT NULL,
    playerOne INT NOT NULL,
    playerTwo INT NOT NULL,
    nextMove INT NOT NULL,
    winner INT,
    PRIMARY KEY (gameID),
    FOREIGN KEY (playerOne) REFERENCES players(playerID),
    FOREIGN KEY (playerTwo) REFERENCES players(playerID),
    FOREIGN KEY (nextMove) REFERENCES players(playerID)
);

CREATE TABLE gameState(
	moveNumber INT NOT NULL,
	slotValues varchar(9) DEFAULT "EEEEEEEEE",
    game INT,
    FOREIGN KEY (game) REFERENCES games(gameID),
    PRIMARY KEY (moveNumber, game)
);