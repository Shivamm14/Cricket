package com.example.cricket;

import com.example.cricket.model.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static com.example.cricket.DBConnector.openConnection;

// this class will have methods to get Stats about match, team, player
// by fetching data from database and building the corresponding object and returning it.
public class Stat {

    public  static PlayerStat getPlayerStat(int playerId){
        // fetch info from db.
        try {
            PlayerStat playerStat = new PlayerStat(playerId);
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();
            String query = String.format("SELECT name, category FROM player where player_id = %d ", playerId);
            ResultSet rs = st.executeQuery(query);
            rs.next();
            playerStat.setName(rs.getString("name"));
            playerStat.setCategory(rs.getString("category"));
            // calculate total runs.
            query = String.format("select count(*), sum(score), sum(ballsPlayed), sum(runsGiven), sum(ballsBowled)," +
                    " sum(wicketsTaken), sum(score)/(sum(ballsPlayed)+1) as strikeRate, " +
                    "sum(runsGiven)/(sum(ballsBowled)+1) as ballingEconomy from " +
                    "game_player where player_id = %d", playerId);
            rs = st.executeQuery(query);
            rs.next();
            playerStat.setTotalRuns(rs.getInt("sum(score)"));
            playerStat.setBallsPlayed(rs.getInt("sum(ballsPlayed)"));
            playerStat.setGamesPlayed(rs.getInt("count(*)"));
            playerStat.setBallsBowled(rs.getInt("sum(ballsBowled)"));
            playerStat.setTotalRunsGiven(rs.getInt("sum(runsGiven)"));
            playerStat.setWicketsTaken(rs.getInt("sum(wicketsTaken)"));
            playerStat.setStrikeRate(rs.getDouble("strikeRate"));
            playerStat.setBallingEconomy(rs.getDouble("ballingEconomy"));
            st.close();
            return playerStat;
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new PlayerStat();
        // build a PlayerStat object return it.
    }

    public static TeamStat getTeamStat(int teamId){
        try {
            TeamStat teamStat = new TeamStat(teamId);
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();
            String query = String.format("SELECT name FROM team where team_id = %d ", teamId);
            ResultSet rs = st.executeQuery(query);
            rs.next();
            teamStat.setName(rs.getString("name"));
            // calculate total runs.
            query = String.format("select sum(score) from game_team where team_id = %d", teamId);
            rs = st.executeQuery(query);
            rs.next();
            teamStat.setTotalScore(rs.getInt("sum(score)"));
            query = String.format("select count(*) from game_team where team_id = %d and result = 'WON'",
                    teamId);
            rs = st.executeQuery(query); rs.next();
            teamStat.setWins(rs.getInt("count(*)"));
            // get total losses.
            query = String.format("select count(*) from game_team where team_id = %d and result = 'LOST'",
                    teamId);
            rs = st.executeQuery(query); rs.next();
            teamStat.setLoss(rs.getInt("count(*)"));

            // get draws
            query = String.format("select count(*) from game_team where team_id = %d and result = 'DRAW'",
                    teamId);
            rs = st.executeQuery(query); rs.next();
            teamStat.setDraw(rs.getInt("count(*)"));
            st.close();
            return teamStat;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new TeamStat(0);
    }

    public static GamePlayerStat getGamePlayerStat(int playerId, int gameId){
        //
        try{
            GamePlayerStat playerStat = new GamePlayerStat(playerId, gameId);
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();
            String query = String.format("SELECT name, category FROM player where player_id = %d ", playerId);
            ResultSet rs = st.executeQuery(query);
            rs.next();
            playerStat.setName(rs.getString("name"));
            playerStat.setCategory(rs.getString("category"));
            // set teamId
            query = String.format("select team_id from game_team_player where game_id = %d and " +
                    "player_id = %d", gameId, playerId);
            rs = st.executeQuery(query); rs.next();
            playerStat.setTeamId(rs.getInt("team_id"));
            //
            // calculate total runs.
            query = String.format("select score, ballsPlayed, runsGiven, ballsBowled, wicketsTaken" +
                    " from game_player where player_id = %d and game_id = %d", playerId, gameId);
            rs = st.executeQuery(query);
            rs.next();
            playerStat.setRunsScored(rs.getInt("score"));
            playerStat.setBallsPlayed(rs.getInt("ballsPlayed"));
            playerStat.setBallsBowled(rs.getInt("ballsBowled"));
            playerStat.setRunsGiven(rs.getInt("runsGiven"));
            playerStat.setWicketsTaken(rs.getInt("wicketsTaken"));
            st.close();
            return playerStat;

        }
        catch (Exception e){
            e.printStackTrace();
        }
        return new GamePlayerStat();
    }

    // getGameTeamStat
    public static GameTeamStat getGameTeamStat(int teamId, int gameId){

        try {
            GameTeamStat gameTeamStat =  new GameTeamStat(teamId, gameId);
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();
            String query = String.format("SELECT name FROM team where team_id = %d ", teamId);
            ResultSet rs = st.executeQuery(query);
            rs.next();
            gameTeamStat.setName(rs.getString("name"));
            // calculate total runs.
            query = String.format("select score, wickets, result from game_team where team_id = %d and game_id = %d",
                    teamId, gameId);
            rs = st.executeQuery(query);
            rs.next();
            gameTeamStat.setScore(rs.getInt("score"));
            gameTeamStat.setWickets(rs.getInt("wickets"));
            gameTeamStat.setResult(rs.getString("result"));

            //
            st.close();
            return gameTeamStat;
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return new GameTeamStat(0, 0);

    }
    // GameStat
    public static GameStat getGameStat(int gameId){
        // get TeamOne Stat team two stat and players stat.
       int teamOneId = 0, teamTwoId = 0, overs = 0;
       ArrayList<Integer> playerIdList = new ArrayList<>();
        try{
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();
            String query = String.format("SELECT team_id FROM game_team where game_id = %d ", gameId);
            ResultSet rs = st.executeQuery(query);
            rs.next();
            teamOneId = rs.getInt("team_id");
            rs.next();
            teamTwoId = rs.getInt("team_id");
            query = String.format("select player_id from game_player where game_id = %d", gameId);
            rs = st.executeQuery(query);
            while(rs.next()){
                playerIdList.add(rs.getInt("player_id"));
            }
            query = String.format("select overs from game where game_id = %d ", gameId);
            rs = st.executeQuery(query);
            rs.next();
            overs = rs.getInt("overs");
            st.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        GameTeamStat gameTeamStatOne = getGameTeamStat(teamOneId, gameId);
        GameTeamStat gameTeamStatTwo = getGameTeamStat(teamTwoId, gameId);
        ArrayList<GamePlayerStat> gamePlayerStatList = new ArrayList<>();
        for(Integer playerId : playerIdList){
            System.out.println("debug; "+ playerId);
            GamePlayerStat gamePlayerStat = getGamePlayerStat(playerId, gameId);
            gamePlayerStatList.add(gamePlayerStat);
        }
        GameStat gameStat = new GameStat(gameTeamStatOne, gameTeamStatTwo, gamePlayerStatList);
        gameStat.setOvers(overs);
        return gameStat;


    }

    public static TeamGameStat getTeamGameStat(int gameId, int teamId){

        ArrayList<Integer> playerIdList = new ArrayList<>();
        try{
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs;
            String query = String.format("select player_id from game_team_player where game_id = %d and team_id = %d",
                    gameId, teamId);
            rs = st.executeQuery(query);
            while(rs.next()){
                playerIdList.add(rs.getInt("player_id"));
            }
            st.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        GameTeamStat gameTeamStat = getGameTeamStat(teamId, gameId);
        ArrayList<GamePlayerStat> gamePlayerStatList = new ArrayList<>();
        for(Integer playerId : playerIdList){
            System.out.println("debug; "+ playerId);
            GamePlayerStat gamePlayerStat = getGamePlayerStat(playerId, gameId);
            gamePlayerStatList.add(gamePlayerStat);
        }
        TeamGameStat teamGameStat = new TeamGameStat(gameTeamStat, gamePlayerStatList);

        return teamGameStat;


    }

}
