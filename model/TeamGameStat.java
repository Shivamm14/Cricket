package com.example.cricket.model;

import java.util.ArrayList;

public class TeamGameStat {

    private GameTeamStat teamStat;
    private ArrayList<GamePlayerStat> gamePlayerStatsList;

    public TeamGameStat(GameTeamStat teamStat, ArrayList<GamePlayerStat> gamePlayerStatsList) {
        this.teamStat = teamStat;
        this.gamePlayerStatsList = gamePlayerStatsList;
    }

    public GameTeamStat getTeamStat() {
        return teamStat;
    }

    public void setTeamStat(GameTeamStat teamStat) {
        this.teamStat = teamStat;
    }

    public ArrayList<GamePlayerStat> getGamePlayerStatsList() {
        return gamePlayerStatsList;
    }

    public void setGamePlayerStatsList(ArrayList<GamePlayerStat> gamePlayerStatsList) {
        this.gamePlayerStatsList = gamePlayerStatsList;
    }
}
