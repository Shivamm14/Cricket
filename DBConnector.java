package com.example.cricket;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * A Java MySQL SELECT statement example.
 * Demonstrates the use of a SQL SELECT statement against a
 * MySQL database, called from a Java program.
 *
 * Created by Alvin Alexander, http://alvinalexander.com
 */
public class DBConnector
{
    public static Connection openConnection() {

        Properties properties = new Properties();
        Connection connection = null;

        String path = System.getProperty("user.dir");

        path += "/src/database.properties";

        try(FileInputStream fin = new FileInputStream(path);) {

            properties.load(fin);

            try {
                Class.forName(properties.getProperty("JDBC_DRIVER"));

//              opening connection
                connection = (Connection) DriverManager.getConnection(properties.getProperty("DB_URL"),properties.getProperty("USER"),properties.getProperty("PASS"));

            } catch (ClassNotFoundException e) {
                System.out.println("This is from openConnection method");
                e.printStackTrace();
            } catch (SQLException f) {
                System.out.println("This is from openConnection method");
                f.printStackTrace();
            }
        } catch (IOException io) {
            System.out.println("This is from openConnection method");
            io.printStackTrace();
        }

        return connection;
    }
//
//    public static void main(String[] args)
//    {
//        try
//        {
//            // create our mysql database connection
////            String myDriver = "com.mysql.cj.jdbc.Driver";
////            String myUrl = "jdbc:mysql://localhost/mydb";
////            Class.forName(myDriver);
////            Connection conn = DriverManager.getConnection(myUrl, "root", "12345678");
//            Connection conn = openConnection();
//            // our SQL SELECT query.
//            String query = "INSERT INTO player (name) values ('Sehwag')";
//            // if you only need a few columns, specify them by name instead of using "*"
//            Statement st = conn.createStatement();
//            st.executeUpdate(query);
//            query = "SELECT * FROM player";
//
//            // create the java statement
//            st = conn.createStatement();
//
//            // execute the query, and get a java resultset
//            ResultSet rs = st.executeQuery(query);
//
//            // iterate through the java resultset
//            while (rs.next())
//            {
//                int id = rs.getInt("player_id");
//                String firstName = rs.getString("name");
//
//                // print the results
//                System.out.format("%s, %s\n", id, firstName);
//            }
//            st.close();
//        }
//        catch (Exception e)
//        {
//            System.err.println("Got an exception! ");
//            System.err.println(e.getMessage());
//        }
//    }
}