/*
* Created by Cody Farris
* cfarrisutd@gmail.com
* Last Updated 9/8/15
*/

package com.game.dao;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

import com.game.core.Game;
import com.game.core.GameState;
import com.game.core.mapper.GameStateMapper;

/*
* GameDAO interface indicates the sql queries to be performed on the database
*/

@RegisterMapper(GameStateMapper.class)
public interface GameStateDAO {

    @SqlQuery("select * from gameState where game = :game")
    List<GameState> getAll(@Bind("game") int game);

    @SqlQuery("select * from gameState where moveNumber = :moveNumber && game = :game")
    GameState findById(@Bind("moveNumber") int moveNumber, @Bind("game") int game);

    @SqlUpdate("delete from gameState where moveNumber = :moveNumber")
    int deleteById(@Bind("moveNumber") int moveNumber);

    @SqlUpdate("update into gameState set slotValues = :slotValues, game = :game"
    		+ " where moveNumber = :moveNumber")
    int update(@BindBean GameState gameState);

    @SqlUpdate("insert into gamestate (moveNumber, slotValues, game) values "
    		+ "(:moveNumber, :slotValues, :game)")
    int insert(@BindBean GameState gameState);
    
    @SqlQuery("select max(moveNumber) from gamestate where game = :game")
    int gameStateIndex(@Bind("game") int game);
    
    @SqlQuery("select * from games where gameID = :id")
    Game getGame(@Bind("id") int id);
}