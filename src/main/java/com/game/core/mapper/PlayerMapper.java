/*
* Created by Cody Farris
* cfarrisutd@gmail.com
* Last Updated 9/7/15
*/

package com.game.core.mapper;

import com.game.core.Player;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
* GameMapper Class
*/
public class PlayerMapper implements ResultSetMapper<Player> {
    public Player map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Player()
            .setId(resultSet.getInt("playerID"))
            .setNumberOfWins(resultSet.getInt("numberOfWins"));
    }
}