package com.example.cricket.game;

public class Batsman extends Player {

    public Batsman(String name) {
        super(name);
    }
    public Batsman(int playerId, String name){
        super(playerId, name, "batsman");
    }
}
