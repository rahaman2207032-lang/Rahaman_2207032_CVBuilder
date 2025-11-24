package com.example.cv_builder;

import java.sql.*;
import java.util.logging.Logger;
public class DB {
    private Connection connection;
    private Logger logger=Logger.getLogger(this.getClass().getName());

    public void getConnection() {
        try{
            if(connection==null || connection.isClosed()){
                connection=DriverManager.getConnection("jdbc:sqlite:cv.db");
                logger.info("Database connection established");
                createTable();
            }
        } catch (SQLException ex) {
            logger.info((ex.toString()));
        }

    }
  private void createTable(){
        getConnection();
        String query = "Create table if not exits cv (id integer not null primary key autoincrement, title text not null,content text not null) ";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.executeUpdate();
            logger.info("Table created");
        } catch (SQLException ex) {
            logger.info((ex.toString()));
        }
  }

}
