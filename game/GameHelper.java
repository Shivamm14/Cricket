package com.example.cricket.game;


import com.example.cricket.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// this class has helper methods for Game class.
public class GameHelper {
    public static String getTeamName(int teamId){
        // fetch name from db. .
        try {
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();
            String query = String.format("select name from team where team_id = %d", teamId);
            ResultSet rs = st.executeQuery(query);
            rs.next();
            String teamName = rs.getString("name");
            st.close();
            return teamName;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "NOT found";
    }
    public static ArrayList<Player> getBuildPlayers(int playerIds[]){
        ArrayList<Player> players = new ArrayList<>();
        try{
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();
            String query = new String();

            for(int id : playerIds){
                // fetching players details from db.
                query = String.format("select name, category from player where player_id = %d", id);
                ResultSet rs = st.executeQuery(query);
                rs.next();
                String name = rs.getString("name");
                String category = rs.getString("category");
                Player player = new Player(id, name, category);
                players.add(player);
            }
            st.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return players;
    }
    public static String gameString(int overs, String location, String date, int teamOneId, int teamTwoId,
                                    int teamOnePlayers[], int teamTwoPlayers[]){
        String temp = "Overs: " + overs;
        temp += " location: " + location;
        temp += " date: " + date;
        temp += " teamOneId: " + teamOneId + " teamTwoId: " + teamTwoId;
        for(int i : teamOnePlayers){
            temp += "\n[" +  i + "]";
        }
        temp += " ----- ";
        for(int i : teamTwoPlayers){
            temp += "\n[" +  i + "]";
        }
        return temp;
    }

}
