package com.example.cricket;

import com.example.cricket.game.Runs;
import com.example.cricket.game.Team;

public class CurGame {
    private int gameId;
    private RawGame rawGame;
    private int battingTeamId, bowlingTeamId;
    private int score, wickets;
    private int strikerId, runnerId,  bowlerId;
    private int inning, ballsPlayed;
    private boolean  complete;
      Team teamOne, teamTwo;

    public RawGame getRawGame() {
        return rawGame;
    }

    public void setRawGame(RawGame rawGame) {
        this.rawGame = rawGame;
    }

    public CurGame(RawGame rawGame) {
        this.rawGame = rawGame;
        // initialize the battingTeam, bowlingTeam, striker, runner, bowler;

    }
    // todo
    public void initializeDB() {
        // update game, game_team, game_player, game_team_player, curGame;
        // set gameId;
    }
    // todo
    public ScoreBoard getScoreBoard() {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.setScore(score);
        scoreBoard.setGameId(gameId);
        scoreBoard.setWickets(wickets);
        scoreBoard.setBattingTeamId(battingTeamId);
        scoreBoard.setStrikerId(strikerId);
        scoreBoard.setRunnerId(runnerId);
        scoreBoard.setBowlerId(bowlerId);
        scoreBoard.setBowlingTeamId(bowlingTeamId);
        return scoreBoard;
    }
    // todo
    public boolean isComplete() {
        return complete;
    }
    // todo
    // Assuming that all the attributes are set already before calling this method.
    public void update(Runs run) {
        if(run != Runs.WICKET){
            // run

            // update striker run, and ballsPlayed in game_player.
//            updateStriker(run.value());
//            // update team score in game_team;
//            updateBattingTeam(run.value());
//            // update bowler runsGiven, ballsBowled in game_player.
//            updateBowler(run.value());
//            // update ballsPlayed in curGame;
//            ballsPlayed++;
//            // if odd run take swap striker and runner.
//            int temp = strikerId;
//            strikerId = runnerId;
//            runnerId = temp;
//
//            // if ballsPlayed == 6 * overs or batting.isAllOut; // inning ends
//            if(ballsPlayed >= 6 * overs || allOut()){
//                //   if inning = 1
//                if(inning == 1){
//                    inning = 2;
//                    // change inning = 2, swap batting and bowling team. select striker and runner.
//                    turn(battingTeamId, bowlingTeamId);
//                    initializeBatsman(); // for new Batting team;
//                }
//                //  else if == 2
//                else{
//                    //  finish match update result in game_team for teams; and status in curGame;
//                    updateResult
//                }// else
//            }

            //  update curGame;

        }else{
            // wicket
            // update striker status in game_team_player;
            // update wicketsTaken for bowler;
            // update battingTeam wicket in game_team;
            // chose nextBatsman if available and change curGame;
            // inning change logic.
//            if ballsPlayed == 6 * overs or batting.isAllOut; // inning ends
            //   if inning = 1
            // change inning = 2, swap batting and bowling team. select striker and runner.
            //  else if == 2
            //  finish match update result in game_team for teams; and status in curGame;
            // else
            //  update curGame;

        }
    }
    // todo
    public void updateDB() {
    }

    public int getBattingTeamId() {
        return battingTeamId;
    }

    public void setBattingTeamId(int battingTeamId) {
        this.battingTeamId = battingTeamId;
    }

    public int getBowlingTeamId() {
        return bowlingTeamId;
    }

    public void setBowlingTeamId(int bowlingTeamId) {
        this.bowlingTeamId = bowlingTeamId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public int getStrikerId() {
        return strikerId;
    }

    public void setStrikerId(int strikerId) {
        this.strikerId = strikerId;
    }

    public int getRunnerId() {
        return runnerId;
    }

    public void setRunnerId(int runnerId) {
        this.runnerId = runnerId;
    }

    public int getBowlerId() {
        return bowlerId;
    }

    public void setBowlerId(int bowlerId) {
        this.bowlerId = bowlerId;
    }

    public int getInning() {
        return inning;
    }

    public void setInning(int inning) {
        this.inning = inning;
    }

    public int getBallsPlayed() {
        return ballsPlayed;
    }

    public void setBallsPlayed(int ballsPlayed) {
        this.ballsPlayed = ballsPlayed;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

}
