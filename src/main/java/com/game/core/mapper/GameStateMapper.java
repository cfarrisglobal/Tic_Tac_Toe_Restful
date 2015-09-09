/*
* Created by Cody Farris
* cfarrisutd@gmail.com
* Last Updated 9/7/15
*/

package com.game.core.mapper;

import com.game.core.GameState;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
* GameMapper Class
*/
public class GameStateMapper implements ResultSetMapper<GameState> {
    public GameState map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new GameState()
            .setMoveNumber(resultSet.getInt("moveNumber"))
            .setSlotValues(resultSet.getString("slotValues"))
            .setGame(resultSet.getInt("game"));
    }
}