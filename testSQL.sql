CREATE TABLE games(
	gameID INT NOT NULL,
    nextMove INT,
    PRIMARY KEY (gameID)
);

INSERT INTO games (gameID, nextMove)
VALUES (1, 1);