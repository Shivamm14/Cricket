package com.example.cricket;

import java.sql.Connection;

import static com.example.cricket.DBConnector.openConnection;

public class Database {
    private static Connection connection;
    private static final Database INSTANCE = new Database();

    private Database(){
        connection = openConnection();
    }
    public static Database getInstance(){
        return INSTANCE;
    }
    public static Connection getConnection(){
        return connection;
    }

}
