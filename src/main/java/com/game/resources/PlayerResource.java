/*
* Created by Cody Farris
* cfarrisutd@gmail.com
* Last Updated 9/8/15
*/

package com.game.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;
import javax.validation.Valid;

import java.util.ArrayList;
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
    public List<Player> getAll(@Context UriInfo uriInfo) {
        List<Player> toReturn = new ArrayList<>();
    	toReturn = playerDAO.getAll();
        
    	for(int x = 0; x < toReturn.size(); x++) {
        	
    		// Build links for HATEOAS standard
        	String uriSelf = getURIForSelf(uriInfo, toReturn.get(x));
        	toReturn.get(x).addLink(uriSelf, "self");

    	}
    	
    	return toReturn;
    }
    
    @GET
    @Path("/{id}")
    public Player get(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
        Player toReturn = playerDAO.findById(id);
        
        // Build links for HATEOAS standard
        String uriSelf = getURIForSelf(uriInfo, toReturn);
        toReturn.addLink(uriSelf, "self");
        
    	return toReturn;
    }
    
    @POST
    public Response add(@Context UriInfo uriInfo) {
    	/*
    	 * Set player ID as one more than the highest ID number present in the
    	 * database's players table.
    	 */
    	Player player = new Player();
        player.setId(playerDAO.playerIndex() + 1);
        playerDAO.insert(player);
        
        // Build links for HATEOAS standard
        String uriSelf = getURIForSelf(uriInfo, player);
        player.addLink(uriSelf, "self");
        
        return Response.status(Status.CREATED)
        		.entity(player)
        		.build();
    }
    
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, @Valid Player player) {
        /*
         * After a player is created there should be no updating of any data
         * by the client directly. Here we return the 405 Method Not Allowed.
         */

        return Response.status(Status.METHOD_NOT_ALLOWED).build();
    }
    
	private String getURIForSelf(UriInfo uriInfo, Player toReturn) {
		String uri = uriInfo.getBaseUriBuilder()
    	.path(GameResource.class)
    	.path(Integer.toString(toReturn.getId()))
    	.build()
    	.toString();
		return uri;
	}
}