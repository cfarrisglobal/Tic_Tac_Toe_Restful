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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import com.game.core.Game;
import com.game.dao.GameDAO;
import com.game.dao.PlayerDAO;
import com.game.dao.GameStateDAO;
import com.game.core.GameState;

/*
* GameResource class indicates the actions to be applied when the restful
* webservice is called by an http request for a games object.
*/

@Path("/games")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON})
public class GameResource {
    
    GameDAO gameDAO;
    GameStateDAO gameStateDAO;
    PlayerDAO playerDAO;

    public GameResource(GameDAO gameDAO, GameStateDAO gameStateDAO, PlayerDAO playerDAO) {
        this.gameDAO = gameDAO;
        this.gameStateDAO = gameStateDAO;
        this.playerDAO = playerDAO;
    }

    @GET
    public List<Game> getAll(@Context UriInfo uriInfo) {
    	
    	List<Game> toReturn = new ArrayList<>();
    	toReturn = gameDAO.getAll();
    	
    	for(int x = 0; x < toReturn.size(); x++) {
        	
    		// Build links for HATEOAS standard
        	String uriSelf = getURIForSelf(uriInfo, toReturn.get(x));
        	String uriPlayerOne = getURIForPlayerOne(uriInfo, toReturn.get(x));
        	String uriPlayerTwo = getURIForPlayerTwo(uriInfo, toReturn.get(x));
        	String uriGameStates = getURIForGameStates(uriInfo, toReturn.get(x));
        	
        	toReturn.get(x).addLink(uriSelf, "self");
        	toReturn.get(x).addLink(uriPlayerOne, "playerone");
        	toReturn.get(x).addLink(uriPlayerTwo, "playertwo");
        	toReturn.get(x).addLink(uriGameStates, "gamestates");
    	}
    	
        return toReturn;
    }
    
    @GET
    @Path("/{id}")
    public Game get(@PathParam("id") Integer id, @Context UriInfo uriInfo) {
    	Game toReturn = gameDAO.findById(id);
    	
    	// Build links for HATEOAS standard
    	String uriSelf = getURIForSelf(uriInfo, toReturn);
    	String uriPlayerOne = getURIForPlayerOne(uriInfo, toReturn);
    	String uriPlayerTwo = getURIForPlayerTwo(uriInfo, toReturn);
    	String uriGameStates = getURIForGameStates(uriInfo, toReturn);
    	
    	toReturn.addLink(uriSelf, "self");
    	toReturn.addLink(uriPlayerOne, "playerone");
    	toReturn.addLink(uriPlayerTwo, "playertwo");
    	toReturn.addLink(uriGameStates, "gamestates");
    	
        return toReturn;
    }


    
    @POST
    public Response add(@Valid Game game,  @Context UriInfo uriInfo) {
    	/*
    	 *  winner cannot be set in post method so set game winner to -1 to 
    	 *  represent no winner
    	 */
    	game.setWinner(-1);
    	
    	/*
    	 * Check if game object posted has an ID. If no ID is found then assign
    	 *  one by adding 1 to the highest id found in the games table in 
    	 *  database.
    	 */
        if(game.getId() == null || game.getId() == 0) {
        	game.setId(gameDAO.gameIndex() + 1);
        }
        
        /*
         * Check for nextMove present in posted game object. If no nextMove is 
         * present then assign to playerOne. Next, check to see if nextMove is
         * either playerOne or playerTwo.
         */
        if(game.getNextMove() == null || game.getNextMove() == 0) {
        	game.setNextMove(game.getPlayerOne());
        } else if((game.getNextMove() != game.getPlayerOne())
        		&& (game.getNextMove() != game.getPlayerTwo())) {
        	// return a failed response
        	return Response.status(Status.BAD_REQUEST).build();
        }
        
        // add row to games table
    	gameDAO.insert(game);
    	
    	// build the initial gameState for the posted game
    	GameState moveZero = new GameState();
    	moveZero.setGame(game.getId());
    	moveZero.setMoveNumber(0);
    	moveZero.setSlotValues("EEEEEEEEE");
    	gameStateDAO.insert(moveZero);
    	
    	// Build links for HATEOAS standard
    	String uriSelf = getURIForSelf(uriInfo, game);
    	String uriPlayerOne = getURIForPlayerOne(uriInfo, game);
    	String uriPlayerTwo = getURIForPlayerTwo(uriInfo, game);
    	String uriGameStates = getURIForGameStates(uriInfo, game);
    	
    	game.addLink(uriSelf, "self");
    	game.addLink(uriPlayerOne, "playerone");
    	game.addLink(uriPlayerTwo, "playertwo");
    	game.addLink(uriGameStates, "gamestates");
    	
        return Response.status(Status.CREATED)
        		.entity(game)
        		.build();
    }
    
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Integer id, @Valid Game game) {
        /*
         * After a game is created there should be no updating of any data
         * by the client directly. Here we return the 405 Method Not Allowed.
         */

        return Response.status(Status.METHOD_NOT_ALLOWED).build();
    }
    
    @Path("/{gameId}/gamestates")
    public GameStateResource getGameStateResource() {
    	return new GameStateResource(gameStateDAO, gameDAO, playerDAO);
    }
    
    
    /*
     * URI link builder methods
     */
	private String getURIForGameStates(UriInfo uriInfo, Game toReturn) {
		String uri = uriInfo.getBaseUriBuilder()
		    	.path(GameResource.class)
		    	.path(Integer.toString(toReturn.getId()))
		    	.path("gamestates")
		    	.build()
		    	.toString();
				return uri;
	}

	private String getURIForSelf(UriInfo uriInfo, Game toReturn) {
		String uri = uriInfo.getBaseUriBuilder()
    	.path(GameResource.class)
    	.path(Integer.toString(toReturn.getId()))
    	.build()
    	.toString();
		return uri;
	}
	
	private String getURIForPlayerOne(UriInfo uriInfo, Game toReturn) {
		String uri = uriInfo.getBaseUriBuilder()
    	.path(PlayerResource.class)
    	.path(Integer.toString(toReturn.getPlayerOne()))
    	.build()
    	.toString();
		return uri;
	}
	
	private String getURIForPlayerTwo(UriInfo uriInfo, Game toReturn) {
		String uri = uriInfo.getBaseUriBuilder()
    	.path(PlayerResource.class)
    	.path(Integer.toString(toReturn.getPlayerTwo()))
    	.build()
    	.toString();
		return uri;
	}
}