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
import com.game.core.mapper.GameMapper;

/*
* GameDAO interface indicates the sql queries to be performed on the database
*/

@RegisterMapper(GameMapper.class)
public interface GameDAO {

    @SqlQuery("select * from games")
    List<Game> getAll();

    @SqlQuery("select * from games where gameID = :id")
    Game findById(@Bind("id") int id);

    @SqlUpdate("delete from games where gameID = :id")
    int deleteById(@Bind("id") int id);

    @SqlUpdate("update games set nextMove = :nextMove, playerOne = :playerOne, "
    		+ "playerTwo = :playerTwo, winner = :winner where gameID = :id")
    int update(@BindBean Game game);

    @SqlUpdate("insert into games (gameID, nextMove, playerOne, playerTwo, "
    		+ "winner) values (:id, :nextMove, :playerOne, :playerTwo, :winner)")
    int insert(@BindBean Game game);
    
    @SqlQuery("select ifnull(max(gameID), 0) from games")
    int gameIndex();
}