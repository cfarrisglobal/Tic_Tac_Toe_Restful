/*
* Created by Cody Farris
* cfarrisutd@gmail.com
* Last Updated 9/4/15
*/

package com.game.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.validation.Valid;
import java.util.List;

import com.game.core.Game;
import com.game.dao.GameDAO;

/*
* GameResource class indicates the actions to be applied when the restful
* webservice is called by an http request for a games object.
*/

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON})
public class GameResource {
    
    GameDAO gameDAO;

    public GameResource(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    @GET
    public List<Game> getAll() {
        return gameDAO.getAll();
    }
    
    @GET
    @Path("/{id}")
    public Game get(@PathParam("id") Integer id) {
        return gameDAO.findById(id);
    }
    
    @POST
    public Game add(@Valid Game game) {
    	/*
    	 * Check if game object posted has an ID. If no ID is found then assign one by 
    	 * adding 1 to the highest id found in the games table in database
    	 */
        if(game.getId() == null || game.getId() == 0) {
        	game.setId(gameDAO.gameIndex() + 1);
        }
    	gameDAO.insert(game);
        return game;
    }
    
    @PUT
    @Path("/{id}")
    public Game update(@PathParam("id") Integer id, @Valid Game game) {
        game = game.setId(id);
        gameDAO.update(game);

        return game;
    }
}