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

        return true;
    }
}