package com.example.cricket.game;


import com.example.cricket.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

// Over class has list of balls. Each ball is a Ball object.
public class Over {
    // list of balls.
    private ArrayList<Ball> balls = new ArrayList<Ball>();
    private Player bowler;
    private int totalRuns;
    private int gameId;
    public Player getBowler() {
        return bowler;
    }

    public int getTotalRuns() {
        return totalRuns;
    }
    Connection conn = Database.getConnection();
    Statement st;

    {
        try {
            st = conn.createStatement();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Over() {

    }

    public ArrayList<Ball> getBalls() {
        return balls;
    }

    // for first inning overs.
    public void start(int gameId, Team battingTeam, Team bowlingTeam){
        this.gameId = gameId;
        bowler = bowlingTeam.getBowler();
        for(int i = 0; i < 6; i++){

            Runs runs = Util.getRuns(battingTeam.getBatter());
            balls.add(new Ball(runs, battingTeam.getBatter(), bowlingTeam.getBowler()));

            if(runs != Runs.WICKET){
                battingTeam.increaseScore(runs.value());
                bowlingTeam.increaseGivenRuns(runs.value());
                totalRuns += runs.value();
            }else{
                bowlingTeam.increaseWicketTaken();
                battingTeam.increaseWicket();
            }
            //TODO:
            //Write update methods for game_team, game_player
            updateGameTeam(battingTeam, bowlingTeam);
            updateGamePlayer(battingTeam.getBatter(), bowlingTeam.getBowler());

            if(battingTeam.isAllOut())
                return;

            // take turns on odd runs. Taking turns here as it was creating problem in updating database after e
            // each ball.
            if(runs != Runs.WICKET && runs.value() % 2 != 0){
                battingTeam.takeTurn();
            }
        }
        // take turn after over.
        battingTeam.takeTurn();
        bowlingTeam.changeBowler();
    }
    // overloaded function for second inning overs.
    public void start(int gameId, Team battingTeam, Team bowlingTeam, int targetScore){
        this.gameId = gameId;
        bowler = bowlingTeam.getBowler();
        for(int i = 0; i < 6; i++){
            Runs runs = Util.getRuns();
            balls.add(new Ball(runs, battingTeam.getBatter(), bowlingTeam.getBowler()));
            if(runs != Runs.WICKET){
                battingTeam.increaseScore(runs.value());
                bowlingTeam.increaseGivenRuns(runs.value());
                totalRuns += runs.value();
            }else{
                bowlingTeam.increaseWicketTaken();
                battingTeam.increaseWicket();
            }
            updateGameTeam(battingTeam, bowlingTeam);
            updateGamePlayer(battingTeam.getBatter(), bowlingTeam.getBowler());

            // break if all out or won.
            if(battingTeam.isAllOut() || battingTeam.getScore() > targetScore)
                return;

            // take turns on odd runs. Taking turns here as it was creating problem in updating database after e
            // each ball.
            if(runs != Runs.WICKET && runs.value() % 2 != 0){
                battingTeam.takeTurn();
            }
        }

        // take turn after over.
        battingTeam.takeTurn();
        bowlingTeam.changeBowler();
    }

    @Override
    public String toString() {
        return "Over{" +
                "balls=" + balls +
                '}' + '\n';
    }
    // this method updates game_team table after each ball.
    void updateGameTeam(Team battingTeam, Team bowlingTeam){

        try{
            // query to update battingTeam data in game_team;
            String query = String.format("UPDATE game_team SET score = %d , wickets = %d where game_id = %d and team_id" +
                            " = %d ",
                    battingTeam.getScore(), bowlingTeam.getWicketsTaken(), gameId, battingTeam.getTeamId());

            st.executeUpdate(query);
//            st.close();
        }
        catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
    void updateGamePlayer(Player batter, Player bowler){
        // debug
        // System.out.println("score: "+ batter.getScore()+ " "  + batter.getBallsBatted());
        // this updates game_player table after each ball.
        try{
            // query to update batting player data in game_player;
            String query = String.format("UPDATE game_player SET score = %d , ballsPlayed = %d where game_id = %d " +
                            "and player_id = %d ",
                    batter.getScore(), batter.getBallsBatted(), gameId, batter.getPlayerId());

            st.executeUpdate(query);

            // query to update bowler data in game_player;
            query = String.format("UPDATE game_player SET wicketsTaken = %d , ballsBowled = %d , runsGiven = %d" +
                            " where game_id = %d and player_id = %d ",
                    bowler.getWicketTaken(), bowler.getBallsBowled(), bowler.getRunsGiven(),
                    gameId, bowler.getPlayerId());

            st.executeUpdate(query);

//            st.close();
        }
        catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
}
