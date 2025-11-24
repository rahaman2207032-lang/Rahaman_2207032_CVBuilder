package com.example.dbdemo;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB{
    private static final String DB_URL = "jdbc:sqlite:sample.db";
    private static Connection connection;

    private static final String Query =
            "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "email TEXT NOT NULL," +
                    "phone TEXT NOT NULL," +
                    "address TEXT NOT NULL," +
                    "education TEXT NOT NULL," +
                    "skills TEXT NOT NULL," +
                    "work_experience TEXT NOT NULL," +
                    "projects TEXT NOT NULL" +
                    ");";

    public static void initDatabase() {
        try {
            if(connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(DB_URL);
                createTable();
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void createTable() throws SQLException {
        try {
            PreparedStatement statement = connection.prepareStatement(Query);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public  static Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        return connection;
}

}
