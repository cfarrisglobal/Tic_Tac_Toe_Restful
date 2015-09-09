/*
* Created by Cody Farris
* cfarrisutd@gmail.com
* Last Updated 9/4/15
*/

package com.game.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/*
* GameState class creates GameState objects that are constructed on either data
* entered by the client or fetched from the database.
*/

public class GameState {
    
    @NotNull
    @JsonProperty
    private int moveNumber;

    @NotNull
    @JsonProperty
    private int game;
    
    @NotNull
    @JsonProperty
    private String slotValues;
    
    @JsonProperty
    private List<Link> links = new ArrayList<>();

    public Integer getMoveNumber() {
        return moveNumber;
    }
    
    public GameState setMoveNumber(Integer moveNumber) {
        this.moveNumber = moveNumber;
        return this;
    }
    
    public Integer getGame() {
        return game;
    }
    
    public GameState setGame(Integer game) {
        this.game = game;
        return this;
    }
    
    public String getSlotValues(){
    	return slotValues;
    }
    
    public GameState setSlotValues(String slotValues){
    	this.slotValues = slotValues;
    	return this;
    }
    
    public List<Link> getLinks() {
		return links;
	}

	public void setLinks(List<Link> links) {
		this.links = links;
	}
	
	public void addLink(String url, String rel) {
		Link link = new Link();
		link.setLink(url);
		link.setRel(rel);
		links.add(link);
	}
    
    /*
    * Check to see if two GameState objects equal each other
    */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GameState)) return false;

        GameState that = (GameState) o;

        if (!getMoveNumber().equals(that.getMoveNumber())) return false;
        if (!getGame().equals(that.getGame())) return false;
        if (!getSlotValues().equals(that.getSlotValues())) return false;

        return true;
    }
}