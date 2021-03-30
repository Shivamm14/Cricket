package com.example.cricket;

public class RawGame {
    private int overs;
    private String location,  date;
    private int  teamOneId, teamTwoId;
    private int teamOnePlayers[], teamTwoPlayers[];

    public int getOvers() {
        return overs;
    }

    public void setOvers(int overs) {
        this.overs = overs;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTeamOneId() {
        return teamOneId;
    }

    public void setTeamOneId(int teamOneId) {
        this.teamOneId = teamOneId;
    }

    public int getTeamTwoId() {
        return teamTwoId;
    }

    public void setTeamTwoId(int teamTwoId) {
        this.teamTwoId = teamTwoId;
    }

    public int[] getTeamOnePlayers() {
        return teamOnePlayers;
    }

    public void setTeamOnePlayers(int[] teamOnePlayers) {
        this.teamOnePlayers = teamOnePlayers;
    }

    public int[] getTeamTwoPlayers() {
        return teamTwoPlayers;
    }

    public void setTeamTwoPlayers(int[] teamTwoPlayers) {
        this.teamTwoPlayers = teamTwoPlayers;
    }
}
