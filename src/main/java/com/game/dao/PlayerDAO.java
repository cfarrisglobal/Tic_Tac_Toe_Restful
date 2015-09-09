/*
* Created by Cody Farris
* cfarrisutd@gmail.com
* Last Updated 9/8/15
*/

package com.game.dao;

import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import java.util.List;

import com.game.core.Player;
import com.game.core.mapper.PlayerMapper;

/*
* GameDAO interface indicates the sql queries to be performed on the database
*/

@RegisterMapper(PlayerMapper.class)
public interface PlayerDAO {

    @SqlQuery("select * from players")
    List<Player> getAll();

    @SqlQuery("select * from players where playerID = :id")
    Player findById(@Bind("id") int id);

    @SqlUpdate("delete from players where playerID = :id")
    int deleteById(@Bind("id") int id);

    @SqlUpdate("update players set numberOfWins = :numberOfWins where playerID = :id")
    int update(@BindBean Player player);

    @SqlUpdate("insert into players (playerID) values (:id)")
    int insert(@BindBean Player player);
    
    @SqlQuery("select ifnull(max(playerID), 0) from players")
    int playerIndex();
}