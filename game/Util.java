package com.example.cricket.game;


import java.security.SecureRandom;
import java.util.Random;

public class Util {
    static Runs [] runs = Runs.values(); // caching Runs.values();
    private static Random rand = new SecureRandom();
    public static Runs getRuns(){
        int len = runs.length;
        int idx = rand.nextInt(len);
        return runs[idx];
    }
    // overloaded getRuns to lessen the probability of wicket for batsman.
    public static Runs getRuns(Player player){
        if(player.getCategory().equals("batsman")){
            Runs runs = getRuns();
            Runs runs1 = getRuns();
            if(runs == Runs.WICKET && runs1.value() < Runs.FOUR.value()){
                return runs;
            }
            if(runs != Runs.WICKET) return runs;
            return runs1;
        }else{
            return getRuns();
        }
    }
}
