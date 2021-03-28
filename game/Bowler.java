package com.example.cricket.game;

public class Bowler extends Player{

    public Bowler(String name) {
        super(name);
    }
    public Bowler(int playerId, String name){
        super(playerId, name, "bowler");
    }
}
