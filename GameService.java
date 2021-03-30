package com.example.cricket;

import com.example.cricket.game.Runs;

public class GameService {

//    public ScoreBoard create(RawGame rawGame){
//        // create game
//        CurGame curGame = new CurGame(rawGame);
//        curGame.initializeDB();
//        return curGame.getScoreBoard();
//    }
//    // nextBall
//    public ScoreBoard nextBall(int gameId, Runs run){
//        CurGame curGame = reloadGame(gameId);
//        if(curGame.isComplete()){
//            return curGame.getScoreBoard();
//        }
//        // throw ball and update game as well.
//        curGame.update(run); // this will update the game, after the ball event.
//        curGame.updateDB(); // this updates db.
//        return curGame.getScoreBoard();
//    }
//
//    private CurGame reloadGame(int gameId) {
//        // this method reloads the game from db. to current state.
//        // construct rawGame;
//        return new CurGame();
//    }

    public String show(){
        return "connected";
    }
    // TODO
    // reloads curGame for the give game id.
//    private CurGame reloadGame(int gameId){
        // TODO
//    }

}
