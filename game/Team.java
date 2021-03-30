package com.example.cricket.game;


import java.util.ArrayList;
import java.util.Random;

// Assuming the team will be build one time.
// with players arrayList. Players won't be added after the initial adding.
public class Team {
    private int teamId;
    private String name; // team name
    private int score, runsGiven, wicketsTaken; // team score
    private ArrayList<Player> players = new ArrayList<Player>();// list of all players.
    private ArrayList<Player> inPlayers = new ArrayList<Player>();// list of all players ready to bat next.
    private  ArrayList<Player> outPlayers = new ArrayList<Player>();// list of all out players.
    private Player batter, runner, bowler;// currently batting two players on the field.

    // Assuming at least 2 players.
    // builds a team with given name and list of players.
    public Team(String name, ArrayList<Player> players) {
        this.name = name;
        // adding players.
        for(Player player : players){
            this.players.add(player);
            this.inPlayers.add(player);
        }
        // making the first two players as opening batsman.
        batter = inPlayers.get(0);
        inPlayers.remove(0);
        runner = inPlayers.get(0);
        inPlayers.remove(0);
        bowler = players.get(players.size()-1); // setting the last player as bowler.
    }

    public Team(int teamId, String name, ArrayList<Player> players) {
        this(name, players);
        this.teamId = teamId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getRunsGiven() {
        return runsGiven;
    }

    public void setRunsGiven(int runsGiven) {
        this.runsGiven = runsGiven;
    }

    public int getWicketsTaken() {
        return wicketsTaken;
    }

    public void setWicketsTaken(int wicketsTaken) {
        this.wicketsTaken = wicketsTaken;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getInPlayers() {
        return inPlayers;
    }

    public void setInPlayers(ArrayList<Player> inPlayers) {
        this.inPlayers = inPlayers;
    }

    public ArrayList<Player> getOutPlayers() {
        return outPlayers;
    }

    public void setOutPlayers(ArrayList<Player> outPlayers) {
        this.outPlayers = outPlayers;
    }

    public void setBowler(Player bowler) {
        this.bowler = bowler;
    }

    public Player getBatter() {
        return batter;
    }

    public Player getRunner() {
        return runner;
    }

    private void setBatter(Player batter) {
        this.batter = batter;
    }

    private void setRunner(Player runner) {
        this.runner = runner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    // Returns the next player who is ready to bat from the inPlayers.
    // Assumes hasNextPlayer() is true;
    private Player getNextPlayer(){
        Player removed = inPlayers.get(0); // get the next player in line.
        inPlayers.remove(0); // remove from inPlayers.
        return removed; // return the player.
    }

    // returns true if inPlayers is not empty else returns false.
    public boolean hasNextPlayer() {
        return !inPlayers.isEmpty();
    }

    // This function swaps batter and runner.
    public void takeTurn(){
        // Assuming batter and runner are set.
        Player temp = batter;
        batter = runner;
        runner = temp;
    }
    // this function increases the score of the team as well as batter by given runs.
    public void increaseScore(int runs){
        score += runs; // increasing team score.
        batter.setScore(batter.getScore() + runs); // increasing batter score.
        batter.setBowlsBatted(batter.getBallsBatted()+1); // increasing balls batted.
    }

    // this function puts the current batter in outPlayers and sets new batter from the inPlayers.
    // Does nothing if all players are out.
    public void increaseWicket(){
        batter.setBowlsBatted(batter.getBallsBowled() + 1); // increasing balls played.
        // checking this condition to avoid repeated adding batter in outPlayers by calling increaseWickets.
        if(outPlayers.size() < players.size()-1)
            outPlayers.add(batter); // making the current batter sit with out Players.
        // Assuming hasNext is true
        if(hasNextPlayer()){
            batter = getNextPlayer(); // getting next player in line to bat.
        }
    }
    // returns number of wickets
    public int getWickets(){
        return outPlayers.size();
    }

    // this function reinitialise the inPlayers and outPlayers list.
    public void reset(){
        // rebuilding inPlayers.
        inPlayers = new ArrayList<Player>(players);
        // making the first two players as opening batsman.
        batter = inPlayers.get(0);
        inPlayers.remove(0);
        runner = inPlayers.get(0);
        inPlayers.remove(0);
        outPlayers = new ArrayList<Player>(); // emptying outPlayers.
    }
    // returns next player to bat randomly from available players
    // assumes hasNextPlayer is true, before calling this method.
    public Player getNextPlayerRandom(){
        // select a random index and return the player of that index.
        int len = inPlayers.size();
        Random rand = new Random();
        int idx = rand.nextInt(len);
        Player nextPlayer = inPlayers.get(idx);
        inPlayers.remove(idx);
        return nextPlayer;
    }

    public Player getBowler() {
        return bowler;
    }

    public void increaseGivenRuns(int runs) {
        runsGiven += runs;
        //increase bowler runsGiven,
        bowler.setRunsGiven(bowler.getRunsGiven() + runs);
        bowler.setBallsBowled(bowler.getBallsBowled() + 1);
    }

    public void increaseWicketTaken() {
        wicketsTaken++;
        bowler.setWicketTaken(bowler.getWicketTaken() + 1);
        bowler.setBallsBowled(bowler.getBallsBowled() + 1);

    }

    public boolean isAllOut() {
        return outPlayers.size() == players.size();
    }

    @Override
    public String toString() {
        return "Team{" +
                "teamId=" + teamId +
                ", name='" + name + '\'' +
                ", score=" + score +
                ", runsGiven=" + runsGiven +
                ", wicketsTaken=" + wicketsTaken +
                ", players=" + players +
                ", inPlayers=" + inPlayers +
                ", outPlayers=" + outPlayers +
                ", batter=" + batter +
                ", runner=" + runner +
                ", bowler=" + bowler +
                '}';
    }

    public void changeBowler() {
        int len = players.size();
        Random rand = new Random();
        int idx = rand.nextInt(len);
        bowler = players.get(idx);
    }
}
