/*
* Created by Cody Farris
* cfarrisutd@gmail.com
* Last Updated 9/4/15
*/

package com.game.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

/*
* Game class creates and holds the game data once the webservice has queried the database
*/

public class Game {
    
    @NotNull
    @JsonProperty
    private int id;

    @NotNull
    @JsonProperty
    private int nextMove;
    
    @NotNull
    @JsonProperty
    private int playerOne;
    
    @NotNull
    @JsonProperty
    private int playerTwo;
    
    @JsonProperty
    private int winner;

    public Integer getId() {
        return id;
    }
    
    public Game setId(Integer id) {
        this.id = id;
        return this;
    }
    
    public Integer getNextMove() {
        return nextMove;
    }
    
    public Game setNextMove(Integer nextMove) {
        this.nextMove = nextMove;
        return this;
    }
    
    public Integer getPlayerOne() {
        return playerOne;
    }
    
    public Game setPlayerOne(Integer playerOne) {
        this.playerOne = playerOne;
        return this;
    }
    
    public Integer getPlayerTwo() {
        return playerTwo;
    }
    
    public Game setPlayerTwo(Integer playerTwo) {
        this.playerTwo = playerTwo;
        return this;
    }
    
    public Integer getWinner() {
        return winner;
    }
    
    public Game setWinner(Integer winner) {
        this.winner = winner;
        return this;
    }
    
    /*
    * Check to see if two game objects equal each other
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;

        Game that = (Game) o;

        if (!getId().equals(that.getId())) return false;
        if (!getNextMove().equals(that.getNextMove())) return false;
        if (!getPlayerOne().equals(that.getPlayerOne())) return false;
        if (!getPlayerTwo().equals(that.getPlayerTwo())) return false;
        if (!getWinner().equals(that.getWinner())) return false;

        return true;
    }
}