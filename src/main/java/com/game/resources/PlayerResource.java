/*
* Created by Cody Farris
* cfarrisutd@gmail.com
* Last Updated 9/7/15
*/

package com.game.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.validation.Valid;
import java.util.List;

import com.game.core.Player;
import com.game.dao.PlayerDAO;

/*
* GameResource class indicates the actions to be applied when the restful
* webservice is called by an http request for a games object.
*/

@Path("/players")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON})
public class PlayerResource {
    
    PlayerDAO playerDAO;

    public PlayerResource(PlayerDAO playerDAO) {
        this.playerDAO = playerDAO;
    }

    @GET
    public List<Player> getAll() {
        return playerDAO.getAll();
    }
    
    @GET
    @Path("/{id}")
    public Player get(@PathParam("id") Integer id) {
        return playerDAO.findById(id);
    }
    
    @POST
    public Player add(@Valid Player player) {
    	/*
    	 * Check if player object posted has an ID. If no ID is found then assign one by 
    	 * adding 1 to the highest id found in the player table in database
    	 */
        if(player.getId() == null || player.getId() == 0) {
        	player.setId(playerDAO.playerIndex() + 1);
        }
        playerDAO.insert(player);
        return player;
    }
    
    @PUT
    @Path("/{id}")
    public Player update(@PathParam("id") Integer id, @Valid Player player) {
        player = player.setId(id);
        playerDAO.update(player);

        return player;
    }
}