/*
* Created by Cody Farris
* cfarrisutd@gmail.com
* Last Updated 9/4/15
*/

package com.game.core.mapper;

import com.game.core.Game;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/*
* GameMapper Class
*/
public class GameMapper implements ResultSetMapper<Game> {
    public Game map(int index, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Game()
            .setId(resultSet.getInt("gameID"))
            .setNextMove(resultSet.getInt("nextMove"));
    }
}