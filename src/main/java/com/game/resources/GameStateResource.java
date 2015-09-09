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

import com.game.core.GameState;
import com.game.core.Game;
import com.game.core.Player;
import com.game.dao.GameStateDAO;
import com.game.dao.GameDAO;
import com.game.dao.PlayerDAO;

/*
* GameStateResource class indicates the actions to be applied when the restful
* webservice is called by an http request for a gameState object.
*/

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON})
public class GameStateResource {
    
    GameStateDAO gameStateDAO;
    GameDAO gameDAO;
    PlayerDAO playerDAO;

    public GameStateResource(GameStateDAO gameStateDAO, GameDAO gameDAO, 
    		PlayerDAO playerDAO) {
        this.gameStateDAO = gameStateDAO;
        this.gameDAO = gameDAO;
        this.playerDAO = playerDAO;
    }

    @GET
    public List<GameState> getAll(@PathParam("gameId") Integer game,
    		@Context UriInfo uriInfo) {
        
    	List<GameState> toReturn = new ArrayList<>();
    	toReturn = gameStateDAO.getAll(game);
        
    	for(int x = 0; x < toReturn.size(); x++) {
        	
    		// Build links for HATEOAS standard
        	String uriSelf = getURIForSelf(uriInfo, toReturn.get(x));
        	String uriGame = getURIForGame(uriInfo, toReturn.get(x));
        	toReturn.get(x).addLink(uriSelf, "self");
        	toReturn.get(x).addLink(uriGame, "game");
    	}
    	
    	return toReturn;
    }
    
    @GET
    @Path("/{moveNumber}")
    public GameState get(@PathParam("moveNumber") Integer moveNumber, 
    		@PathParam("gameId") Integer game, @Context UriInfo uriInfo) {
        
    	GameState toReturn = gameStateDAO.findById(moveNumber, game);
    	
    	// Build links for HATEOAS standard
    	String uriSelf = getURIForSelf(uriInfo, toReturn);
    	String uriGame = getURIForGame(uriInfo, toReturn);
    	
    	toReturn.addLink(uriSelf, "self");
    	toReturn.addLink(uriGame, "game");
        
    	return toReturn;
    }
    
    @POST
    public Response add(@Valid GameState gameState,
    		@PathParam("gameId") Integer gameNumber,
    		@Context UriInfo uriInfo) {
    	/*
    	 * Check to see if gameNumber in path parameter matches one presented
    	 * in gameState json. If they don't match then reject the post. If no
    	 * game number is present in gameState json then assign gameNumber
    	 * provided in path parameter.
    	 */
    	if(gameState.getGame() == null || gameState.getGame() == 0) {
    		gameState.setGame(gameNumber);
    	} else if(gameState.getGame() != gameNumber) {
    		return Response.status(Status.NOT_ACCEPTABLE).build();
    	}
    	
    	/*
    	 * Check if gameState object posted has a moveNumber. If no moveNumber
    	 * is found then assign one by adding 1 to the highest moveNumber 
    	 * found in the gamesState with the assigned game id. Else deny the 
    	 * post request.
    	 */
        if(gameState.getMoveNumber() == null || gameState.getMoveNumber() == 0) {
        	gameState.setMoveNumber(gameStateDAO.gameStateIndex
        			(gameState.getGame()) + 1);
        } else {
        	return Response.status(Status.NOT_ACCEPTABLE).build();
        }
        
        /*
         * Gather information on game and gameState
         */
        String currentMove = gameState.getSlotValues();
        GameState previousState = gameStateDAO.findById(
        		gameState.getMoveNumber() - 1, gameState.getGame());
        String previousMove = previousState.getSlotValues();
        Game game = gameDAO.findById(gameState.getGame());
        
        /*
         * Check to see if game already has a winner. If it does then reject
         * the post.
         */
        if(game.getWinner() != -1) {
        	return Response.status(Status.NOT_ACCEPTABLE).build();
        }
        
        /*
         *  Find if the games nextMove is either playerOne or PlayerTwo
         */
        int nextPlayer = 0;
        if(game.getNextMove() == game.getPlayerOne()) {
        	nextPlayer = 1;
        } else if(game.getNextMove() == game.getPlayerTwo()) {
        	nextPlayer = 2;
        } else {
        	// if neither playerOne or playerTwo are the next move than the
        	// game is broken
        	return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }
        
        /*
         * Check if the move posted is a valid move made by the correct player
         */
        if(HelperResource.validMove(currentMove, previousMove, nextPlayer) 
        		!= true) {
        	return Response.status(Status.NOT_ACCEPTABLE).build();
        }
        
    	/*
    	 * Change the next move in current game to the opposite player it is
    	 * currently assigned. Then update the database.
    	 */
    	if(nextPlayer == 1){
    		game.setNextMove(game.getPlayerTwo());
    	} else if(nextPlayer == 2) {
    		game.setNextMove(game.getPlayerOne());
    	}
    	
    	/*
    	 * Check win condition and update game winner if met. If the char
    	 * returned is E no winner exists. If X is return playerOne wins, if
    	 * O is returned playerTwo wins.
    	 */
    	char winner = HelperResource.winnerCondition(currentMove);
    	if(winner == 'X') {
    		game.setWinner(game.getPlayerOne());
    		// Increase players win count and save to database
    		Player winPlayer = playerDAO.findById(game.getPlayerOne());
    		winPlayer.setNumberOfWins(winPlayer.getNumberOfWins() + 1);
    		playerDAO.update(winPlayer);
    	} else if(winner == 'O') {
    		game.setWinner(game.getPlayerTwo());
    		// Increase players win count and save to database
    		Player winPlayer = playerDAO.findById(game.getPlayerTwo());
    		winPlayer.setNumberOfWins(winPlayer.getNumberOfWins() + 1);
    		playerDAO.update(winPlayer);
    	}
    	
    	gameDAO.update(game);
    	gameStateDAO.insert(gameState);
    	
    	// Build links for HATEOAS standard
    	String uriSelf = getURIForSelf(uriInfo, gameState);
    	String uriPlayerOne = getURIForGame(uriInfo, gameState);
    	
    	gameState.addLink(uriSelf, "self");
    	gameState.addLink(uriPlayerOne, "game");
    	
        return Response.status(Status.CREATED)
        		.entity(gameState)
        		.build();
    }
    
    @PUT
    @Path("/{moveNumber}")
    public Response update(@PathParam("moveNumber") Integer moveNumber, @Valid GameState gameState) {
        /*
         * After a game is created there should be no updating of any data
         * by the client directly. Here we return the 405 Method Not Allowed.
         */

    	return Response.status(Status.METHOD_NOT_ALLOWED).build();
    }
    
	private String getURIForGame(UriInfo uriInfo, GameState toReturn) {
		String uri = uriInfo.getBaseUriBuilder()
		    	.path(GameResource.class)
		    	.path(Integer.toString(toReturn.getGame()))
		    	.build()
		    	.toString();
				return uri;
	}

	private String getURIForSelf(UriInfo uriInfo, GameState toReturn) {
		String uri = uriInfo.getBaseUriBuilder()
    	.path(GameResource.class)
    	.path(Integer.toString(toReturn.getGame()))
    	.path(GameStateResource.class)
    	.path(Integer.toString(toReturn.getMoveNumber()))
    	.build()
    	.toString();
		return uri;
	}
}