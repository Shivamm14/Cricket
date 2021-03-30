package com.example.cricket.game;


import com.example.cricket.Database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import static com.example.cricket.game.GameHelper.getBuildPlayers;
import static com.example.cricket.game.GameHelper.getTeamName;

// This is the Game controller class. Which will build the game, then start it.
public class Game {
    private String matchDate;
    private int gameId;
    private String location;
    private  int overs;
    private ArrayList<Over> firstInningOvers = new ArrayList<Over>();
    private ArrayList<Over> secondInningOvers = new ArrayList<Over>();
    public Game(int overs) {
        this.overs = overs;
    }
    public Game(int over, String location, String matchDate){
        this.overs = over;
        this.location = location;
        this.matchDate = matchDate;
    }
    // this function will build the teams from teamId and playerId provided.
    public void build(int teamIdOne, int teamIdTwo, int teamOnePlayers[], int teamTwoPlayers[]){
        ArrayList<Player> players = new ArrayList<>();
        players = getBuildPlayers(teamOnePlayers);
        String teamName = getTeamName(teamIdOne);
        Team teamOne = new Team(teamIdOne, teamName, players);
        // build team two
        players = getBuildPlayers(teamTwoPlayers);
        teamName = getTeamName(teamIdTwo);
        Team teamTwo = new Team(teamIdTwo, teamName, players);
//        System.out.println(teamOne);
//        System.out.println(teamTwo);
        // start match.
        initializeGame();// to initialize game.
        initializeGameTeam(teamOne.getTeamId(), teamTwo.getTeamId()); // to initialize game_team.
        initializeGamePlayer(teamOne); //to initialize game_player for teamOne players.
        initializeGamePlayer(teamTwo); // for teamTwo players.
        initializeGameTeamPlayer(teamOne); // to initialize game_team_player.
        initializeGameTeamPlayer(teamTwo);

        // doing toss.
        if(coinToss() == 0){
            // teamOne bats
            startMatch(teamOne, teamTwo);
        }else{
            // teamTwo bats
            startMatch(teamTwo, teamOne);
        }
    }
    // this function will build the game by building the teams, doing the toss and then starting the match.
    public void buildStart(){
        // build teamOne
        ArrayList<Player> players = new ArrayList<Player>(Arrays.asList(
                new Batsman(1, "Sachin"),
                new Batsman(2, "Virat"),
                new Batsman(16,"Rohit"),
                new Batsman(4, "Dhoni"),
                new Bowler(9, "Hardik")
        ));
        Team teamOne = new Team(1,"India", players);
        // build teamTwo
        players = new ArrayList<Player>(Arrays.asList(
                new Batsman(14, "AB de Villiers"),
                new Batsman(13, "Ricky"),
                new Bowler(5, "Bret lee"),
                new Bowler(6, "Micheal Clark"),
                new Bowler(15, "Shane Warne")
        ));
        Team teamTwo = new Team(2, "Australia", players);
        // TODO
        // write initialize methods for game, game_team, game_player, game_team_player.
        // team and player table will not be touched, and will be initialized manually.

//        initializeGame();// to initialize game.
//        initializeGameTeam(teamOne.getTeamId(), teamTwo.getTeamId()); // to initialize game_team.
//        initializeGamePlayer(teamOne); //to initialize game_player for teamOne players.
//        initializeGamePlayer(teamTwo); // for teamTwo players.
//        initializeGameTeamPlayer(teamOne); // to initialize game_team_player.
//        initializeGameTeamPlayer(teamTwo);
//
//        // doing toss.
//        if(coinToss() == 0){
//            // teamOne bats
//            startMatch(teamOne, teamTwo);
//        }else{
//            // teamTwo bats
//            startMatch(teamTwo, teamOne);
//        }

    }
    // to make entry in game_team_player.
    private void initializeGameTeamPlayer(Team team){
        try{
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();

            for(Player player : team.getPlayers()){
                // enter each player in game_player.
//                System.out.println(player.getPlayerId());
                String query = String.format("INSERT INTO game_team_player (game_id, team_id, player_id)" +
                                " VALUES(%d, %d, %d)",
                        this.gameId, team.getTeamId(), player.getPlayerId());

//                System.out.println(query); // debug.
                st.executeUpdate(query);

            }
        }
        catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }

    }
    // this method makes entry of each player in game_player. mapping each player with the game.
    private void initializeGamePlayer(Team team){
        try{
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();

            for(Player player : team.getPlayers()){
                // enter each player in game_player.
                System.out.println(player.getPlayerId());
                String query = String.format("INSERT INTO game_player (game_id, player_id, score, wicketsTaken," +
                                "ballsPlayed, runsGiven, ballsBowled) VALUES(%d, %d, %d, %d, %d, %d, %d)",
                        this.gameId, player.getPlayerId(), 0, 0, 0, 0, 0);

                System.out.println(query); // debug.
                st.executeUpdate(query);

            }
        }
        catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }


    }
    // this method makes an entry in the game_team table with the game and team info.
    public void initializeGameTeam(int firtTeamId, int secondTeamId) {
        // initialize game_team table
        try{
            // query to enter firstTeam;
            String query = String.format("INSERT INTO game_team (game_id, team_id, score, wickets) VALUES(%d, %d, %d, %d)",
                    this.gameId, firtTeamId, 0, 0);

            System.out.println(query);
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();

            st.executeUpdate(query);
            // query for second team entry.
            query = String.format("INSERT INTO game_team (game_id, team_id, score, wickets) VALUES(%d, %d, %d, %d)",
                    this.gameId, secondTeamId, 0, 0);

            System.out.println(query);
            st.executeUpdate(query);
            st.close();
        }
        catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    // this function starts the match, assuming firstTeam is batting first.
    private void startMatch(Team firstTeam, Team secondTeam){

        // first inning
        for(int i = 0; i < overs; i++){
            Over curOver = new Over();
            curOver.start(gameId, firstTeam, secondTeam);
            firstInningOvers.add(curOver);
            // if all out then break.
            if(firstTeam.isAllOut()){
                break;
            }
        }
        // second inning
        for(int i = 0; i < overs; i++){
            Over curOver = new Over();
            curOver.start(gameId, secondTeam, firstTeam, firstTeam.getScore());
            secondInningOvers.add(curOver);
            // if all out or won then break;
            if(secondTeam.isAllOut() || secondTeam.getScore() > firstTeam.getScore()){
                break;
            }
        }
        updateResult(firstTeam, secondTeam);
        printResult(firstTeam, secondTeam);
    }

    private void updateResult(Team firstTeam, Team secondTeam) {
        if(firstTeam.getScore() > secondTeam.getScore()){
            try{
                String query = String.format("UPDATE game_team SET result = 'WON' where team_id = %d and game_id = %d",
                        firstTeam.getTeamId(), gameId);
                System.out.println(query);
                Connection conn = Database.getConnection();
                Statement st = conn.createStatement();

//                System.out.println(query);
                st.executeUpdate(query);
                // get game_id
                query = String.format("UPDATE game_team SET result = 'LOST' where team_id = %d and game_id = %d ",
                        secondTeam.getTeamId(), gameId);
                st.executeUpdate(query);
                st.close();
            }
            catch (Exception e){
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }
        }else if(secondTeam.getScore() > firstTeam.getScore()){
            try{
                String query = String.format("UPDATE game_team SET result = 'WON' where team_id = %d and game_id = %d",
                        secondTeam.getTeamId(), gameId);
                System.out.println(query);
                Connection conn = Database.getConnection();
                Statement st = conn.createStatement();

//                System.out.println(query);
                st.executeUpdate(query);
                // get game_id
                query = String.format("UPDATE game_team SET result = 'LOST' where team_id = %d and game_id = %d",
                        firstTeam.getTeamId(), gameId);
                st.executeUpdate(query);
                st.close();
            }
            catch (Exception e){
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }
        }else{
            try{
                String query = String.format("UPDATE game_team SET result = 'DRAW' where team_id = %d and game_id = %d",
                        firstTeam.getTeamId(), gameId);
                System.out.println(query);
                Connection conn = Database.getConnection();
                Statement st = conn.createStatement();

//                System.out.println(query);
                st.executeUpdate(query);
                // get game_id
                query = String.format("UPDATE game_team SET result = 'DRAW' where team_id = %d and game_id = %d",
                        secondTeam.getTeamId(), gameId);
                st.executeUpdate(query);
                st.close();
            }
            catch (Exception e){
                System.err.println("Got an exception! ");
                System.err.println(e.getMessage());
            }
        }

//        // update batters;
//        for(Player player : firstTeam.getPlayers()){
//            updateGamePlayer(player);
//        }
//        for(Player player : secondTeam.getPlayers()){
//            updateGamePlayer(player);
//        }
//

    }
    void updateGamePlayer(Player batter){
        // debug
        // System.out.println("score: "+ batter.getScore()+ " "  + batter.getBallsBatted());
        // this updates game_player table after each ball.
        try{
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();
            // query to update batting player data in game_player;
            String query = String.format("UPDATE game_player SET score = %d , ballsPlayed = %d where game_id = %d " +
                            "and player_id = %d ",
                    batter.getScore(), batter.getBallsBatted(), gameId, batter.getPlayerId());

            st.executeUpdate(query);

//            st.close();
        }
        catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }

    // initializes game;
    // this function makes an entry in the game table with current match info and get gameId from the table.
    public void  initializeGame(){

        // protect sql injection safe queries.
        String query = String.format("INSERT INTO game (location, matchDate, overs) VALUES('%s', '%s', %d)",
                this.location, this.matchDate, this.overs);
        try{
            System.out.println(query);
            Connection conn = Database.getConnection();
            Statement st = conn.createStatement();

            System.out.println(query);
            st.executeUpdate(query);
            // get game_id
            query = "SELECT MAX(game_id) FROM game";


            // execute the query, and get a java resultset
            ResultSet rs = st.executeQuery(query);
            while(rs.next())
                this.gameId = rs.getInt("max(game_id)");
            st.close();
        }
        catch (Exception e){
            System.err.println("Got an exception! ");
            System.err.println(e.getMessage());
        }
    }
    // generate a function which returns random number between 0 and 7˳.
    public static int getRuns(){
        Random rand = new Random();
        int runs;
        runs = rand.nextInt(8);
        return runs;
    }
    // Returns 0 or 1 randomly.
    public static int coinToss(){
        Random rand = new Random();
        int toss;
        toss = rand.nextInt(2);
        return toss;
    }
    // prints the result based on the scores of the two team.
    private void printResult(Team firstTeam, Team secondTeam){
        System.out.println(firstTeam.getName() + " vs " + secondTeam.getName());
        System.out.println(firstTeam.getName() + " is batting first. ");
        // printing first inning overs details.
        for(Over over : firstInningOvers){
            System.out.println("Over");

            for(Ball ball : over.getBalls()){
                System.out.println("Batsman: " + ball.getBatsman().getName() + " " +  ball.getRuns());
            }
            Player bowler = over.getBowler();
            System.out.println("Bowler: " + bowler.getName() + " runs given: " + over.getTotalRuns());
        }
        System.out.println(secondTeam.getName() + " bats to chase score of " + firstTeam.getScore());
        // printing second inning overs details.
        for(Over over : secondInningOvers){
            System.out.println("Over");

            for(Ball ball : over.getBalls()){
                System.out.println("Batsman: " + ball.getBatsman().getName() + " " +  ball.getRuns());
            }
            Player bowler = over.getBowler();
            System.out.println("Bowler: " + bowler.getName() + " runs given: " + over.getTotalRuns());
        }

        if(firstTeam.getScore() < secondTeam.getScore()){
            System.out.println(secondTeam.getName() + " won!");
        }else if(firstTeam.getScore() > secondTeam.getScore()){
            System.out.println(firstTeam.getName() + " won!");
        }else{
            System.out.println("Draw!");
        }
    }
    // prints the match state
    private void showMatchState(Team firstTeam, int ballsLeft){
        // printing game state after each ball.
        System.out.printf("Balls left: %d, score: %d wickets: %d\n", ballsLeft, firstTeam.getScore(),
                firstTeam.getWickets());
        System.out.println(firstTeam.getBatter() + " " + firstTeam.getRunner());

    }
    // TODO
    // this function takes user input of players.
    private ArrayList<Player> getPlayers(){
        return new ArrayList<Player>();
    }

    public int getGameId() {
        return gameId;
    }
}

