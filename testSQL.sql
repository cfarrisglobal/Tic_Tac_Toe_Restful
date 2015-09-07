CREATE TABLE games(
	gameID INT NOT NULL,
    nextMove INT,
    PRIMARY KEY (gameID)
);

INSERT INTO players (playerID)
VALUES (1);

INSERT INTO players (playerID)
VALUES (2);

INSERT INTO games (gameID, nextMove, playerOne, playerTwo)
VALUES (1, 1, 1, 2);