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

public class Player {
    
    @NotNull
    @JsonProperty
    private int id;

    @NotNull
    @JsonProperty
    private int numberOfWins;

    public Integer getId() {
        return id;
    }
    
    public Player setId(Integer id) {
        this.id = id;
        return this;
    }
    
    public Integer getNumberOfWins() {
        return numberOfWins;
    }
    
    public Player setNumberOfWins(Integer numberOfWins) {
        this.numberOfWins = numberOfWins;
        return this;
    }
    
    /*
    * Check to see if two player objects equal each other
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player that = (Player) o;

        if (!getId().equals(that.getId())) return false;
        if (!getNumberOfWins().equals(that.getNumberOfWins())) return false;

        return true;
    }
}