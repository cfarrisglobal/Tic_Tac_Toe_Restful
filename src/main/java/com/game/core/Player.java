/*
* Created by Cody Farris
* cfarrisutd@gmail.com
* Last Updated 9/8/15
*/

package com.game.core;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

/*
* Player class creates and holds the player data for the webservice to 
* manipulate.
*/

public class Player {
    
    @NotNull
    @JsonProperty
    private int id;

    @NotNull
    @JsonProperty
    private int numberOfWins;

    @JsonProperty
    private List<Link> links = new ArrayList<>();
    
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