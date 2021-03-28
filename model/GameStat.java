package com.example.cricket.model;

import java.util.ArrayList;

public class GameStat {
    private int overs;
    private GameTeamStat gameTeamStatOne, gameTeamStatTwo;
    private ArrayList<GamePlayerStat> gamePlayerStatsList;

    public GameStat(GameTeamStat gameTeamStatOne, GameTeamStat gameTeamStatTwo, ArrayList<GamePlayerStat> gamePlayerStatList) {
        this.gameTeamStatOne = gameTeamStatOne;
        this.gameTeamStatTwo = gameTeamStatTwo;
        this.gamePlayerStatsList = gamePlayerStatList;
    }

    public int getOvers() {
        return overs;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }

    public GameTeamStat getGameTeamStatOne() {
        return gameTeamStatOne;
    }

    public void setGameTeamStatOne(GameTeamStat gameTeamStatOne) {
        this.gameTeamStatOne = gameTeamStatOne;
    }

    public GameTeamStat getGameTeamStatTwo() {
        return gameTeamStatTwo;
    }

    public void setGameTeamStatTwo(GameTeamStat gameTeamStatTwo) {
        this.gameTeamStatTwo = gameTeamStatTwo;
    }

    public ArrayList<GamePlayerStat> getGamePlayerStatsList() {
        return gamePlayerStatsList;
    }

    public void setGamePlayerStatsList(ArrayList<GamePlayerStat> gamePlayerStatsList) {
        this.gamePlayerStatsList = gamePlayerStatsList;
    }
}
