/*
* Created by Cody Farris
* cfarrisutd@gmail.com
* Last Updated 9/4/15
*/

package com.game;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import org.skife.jdbi.v2.DBI;

import com.game.resources.GameResource;
import com.game.dao.GameDAO;
/*
* GameApplication initialized the database indicated in the game.yml file
*/
public class GameApplication extends Application<GameConfiguration> {
    
    public static void main(String[] args) throws Exception {
        new GameApplication().run(args);
    }

    @Override
    public String getName() {
        return "dropwizard-jdbi";
    }

    @Override
    public void initialize(Bootstrap<GameConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
    public void run(GameConfiguration configuration,
                    Environment environment) {

        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), "mysql");
        final GameDAO dao = jdbi.onDemand(GameDAO.class);
        final GameResource gameResource = new GameResource(dao);
        
        environment.jersey().register(new GameResource(dao));
    }

}