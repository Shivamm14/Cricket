package com.example.cricket.game;


// this stores Player info
public class Player {
    private int playerId;
    private String name;
    private int score, ballsBatted;
    private int runsGiven;
    private int wicketTaken;
    private int ballsBowled;
    private String category; // "batsman" or "bowler"

    public Player(int playerId, String name, String category) {
        this.playerId = playerId;
        this.name = name;
        this.category = category;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setWicketTaken(int wicketTaken) {
        this.wicketTaken = wicketTaken;
    }

    public void setBowlsBatted(int bowlsBatted) {
        this.ballsBatted = bowlsBatted;
    }

    public void setRunsGiven(int runsGiven) {
        this.runsGiven = runsGiven;
    }



    public void setBallsBowled(int ballsBowled) {
        this.ballsBowled = ballsBowled;
    }

    public int getBallsBatted() {
        return ballsBatted;
    }

    public int getRunsGiven() {
        return runsGiven;
    }

    public int getWicketTaken() {
        return wicketTaken;
    }

    public int getBallsBowled() {
        return ballsBowled;
    }

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
